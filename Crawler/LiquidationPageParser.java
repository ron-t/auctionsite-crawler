package Crawler;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Crawler.Persistence.*;

import com.healthmarketscience.jackcess.*;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.PageFetcher;
import edu.uci.ics.crawler4j.url.WebURL;

public class LiquidationPageParser extends PageParser {

	private static final String AUCTION = "AUCTION_ITEM";
	private static final String BID = "AUCTION_BID";
	private static final String MANIFEST = "AUCTION_MANIFEST";
	private static final String SELLER = "SELLER";
	
	private static final String AUCTION_AUCTIONID = "AuctionID";

	private static final DateFormat DATE_FORMAT_US1 = new SimpleDateFormat(
			"MM/dd/yyyyhh:mmaz");

	private static Database mdb;
	private static ArrayList<Integer> auctionIdsList = new ArrayList<Integer>(400000);
	
	//temp
	public Database getDatabase() {
		return mdb;
	}

	public static void initDB(String pathAndFileName, boolean flush)
			throws IOException {

		File mdbFile = new File(pathAndFileName);
		mdb = Database.open(mdbFile, false, false);

		if (flush) {
			System.out.println("Flushing db...");
			for (Iterator<Table> tableIter = mdb.iterator(); tableIter
					.hasNext();) {
				Table table = tableIter.next();

				Cursor c = Cursor.createCursor(table);

				while (c.getNextRow() != null) {
					c.deleteCurrentRow();
				}
			}
			mdb.flush();
			System.out.println("Database " + mdbFile.getName() + " flushed.");
		}
		
		initAuctionIdList();
	}

	public static boolean closeDB() {

		try {
			if (mdb != null) {
				mdb.close();
				return true;
			}
		} catch (IOException e) {
			return false;
		}

		return false;
	}

	public LiquidationPageParser() {
	}

	public LiquidationPageParser(File mdbFile) throws IOException {
		mdb = Database.open(mdbFile);
	}

	public void parse(Page page, LiquidationPageType type) throws IOException {
		if (type == LiquidationPageType.AUCTION) {
			parseAuction(page);
		}
		else if (type == LiquidationPageType.BIDS) {
			parseBid(page);
		}
		else if (type == LiquidationPageType.MANIFEST) {
			parseManifest(page);
		}
		else if (type == LiquidationPageType.SELLER) {
			//if (page.getWebURL().getURL().endsWith("page=1")) { // hacky way to
																// only parse
																// the seller
																// page once
				parseSeller(page);
			//}
		}
	}

	private void parseAuction(Page page) throws IOException {
		PageUtils.addValidParentDocID(page.getWebURL().getParentDocid());

		Map<String, ValueObject> auctionRow = Auction.initResultMap();

		PageUtils.getInlineValuesFromHTML(page, auctionRow);
		PageUtils.getLinesBeforeValuesFromHTML(page, auctionRow);

		// set manual values
		String auctionID = PageUtils.getAuctionIDFromPage(page);
		auctionRow.get(Auction.FIELD_AUCTIONID).setValue(auctionID);

		Document doc = Jsoup.parse(page.getHTML());
		Element e = doc.getElementById("description_table");
		auctionRow.get(Auction.FIELD_DESCRIPTION).setValue(e.text());

		insert(AUCTION, auctionRow);
		// System.out.println("[AUCTION] inserted: " + auctionID);
	}

	private void parseBid(Page page) {
		String auctionID = PageUtils.getAuctionIDFromPage(page);
		String origPageURL = page.getWebURL().getURL();
		Page nextBidPage = page;

		// System.out.println("[debug] parsing bid page " + origPageURL);
		int maxPageNum = PageUtils.getBidMaxPageNumber(page);
		int currPageNum = 1;

		do {
			String pageHTML = nextBidPage.getHTML();
			String[] html = pageHTML
					.substring(pageHTML.indexOf("<div class=\"tableContents"),
							pageHTML.lastIndexOf("<div class=\"pagination\">"))
					.replaceAll("\\s", "")
					.split("<divclass=\"tableContents(even|odd)Row\">");

			for (String bidHTML : html) {
				try {
					parseBidPageHTML(bidHTML.split("</div>"), auctionID);

				} catch (IOException e) {
					System.err.println("Error parsing a bid on page "
							+ currPageNum + " of [" + origPageURL + "]");
					e.printStackTrace();
				}
			}

			String nextPageURL = origPageURL + PageUtils.BID_PAGINATE_SUFFIX
					+ (++currPageNum);
			WebURL nextURL = new WebURL();
			nextURL.setURL(nextPageURL);
			nextBidPage = new Page(nextURL);

			PageFetcher.fetch(nextBidPage, true);

		} while (currPageNum <= maxPageNum);
	}

	private void parseBidPageHTML(String[] html, String auctionID)
			throws IOException {

		Map<String, ValueObject> bidRow = Bid.initResultMap();
		PageUtils.getInlineValuesFromHTML(html, bidRow);

		if (bidRow.get(Bid.FIELD_BIDNUMBER).getValue() == null) { // only insert
																	// where a
																	// bid was
																	// found
			return;
		}

		bidRow.get(Bid.FIELD_AUCTIONID).setValue(auctionID);

		insert(BID, bidRow);
		// System.out.println("[BID] inserted: " + auctionID + "," +
		// bidRow.get(Bid.FIELD_BIDNUMBER).getValue());
	}

	private void parseManifest(Page page) {
		String auctionId = PageUtils.getAuctionIDFromPage(page);

		Document doc = Jsoup.parse(page.getHTML());

		Elements trs = doc.select("tr");

		Iterator<Element> trIter = trs.iterator();

		Element trCurrent;
		String[] mapping = {};

		if (trIter.hasNext()) {
			try {
				// get the first non-blank tr, which is assumed to contain the
				// headings
				do {
					trCurrent = trIter.next();
				} while (trCurrent.text().isEmpty() && trIter.hasNext());

				mapping = getMapping(trCurrent);

				if (mapping == null || mapping.length == 0) {
					System.err.println("Manifest headers not found for "
							+ page.getWebURL().getURL());
					return;
				}

			} catch (Exception e) {
				System.err.println("Unable to parse the manifest headers for "
						+ page.getWebURL().getURL());
				// e.printStackTrace();
				return;
			}
		}

		// parse the remaining trs
		tr: while (trIter.hasNext()) {
			try {
				trCurrent = trIter.next();
				Map<String, ValueObject> manifestRow = Manifest.initResultMap();

				manifestRow.get(Manifest.FIELD_AUCTIONID).setValue(auctionId);

				Elements cells = trCurrent.getElementsByTag("td");
				cells.addAll(trCurrent.getElementsByTag("th")); // get all td
																// and th cells

				Iterator<Element> tdIter = cells.iterator();
				String currentText = null;
				int numBlankCells = 0; // if more than 3 cells are blank then
										// assume invalid row and exclude from
										// manifest.
				boolean allBlank = true;

				for (int i = 0; tdIter.hasNext() && i < mapping.length; i++) {
					Element tdCurrent = tdIter.next();

					if (mapping[i] != Manifest.HEADING_IGNORE) {
						currentText = tdCurrent.text();

						if (currentText != null) {
							if (currentText.isEmpty()) {
								numBlankCells++;

								if (numBlankCells >= 3) {
									// System.out.println("ignoring this row due to >= 3 blank cells");
									continue tr;
								}
							} else {
								manifestRow.get(mapping[i]).setValue(
										currentText);
								allBlank = false;
								// System.out.println("***debug***| " +
								// mapping[i] + "::" + tdCurrent.text());
							}
						}
					}
				}

				if (!allBlank) {
					if (!insert(MANIFEST, manifestRow)) { // insert() returns true if successful
						System.err.println("Unable to insert a manifest entry for "
								+ page.getWebURL().getURL());
						System.err.println("Not continuing with remaining entries.");
						return;
					}
					// System.out.println("[manifest] inserted: auctionid " +
					// auctionId);
				}
			} catch (Exception e) {
				System.err.println("Unable to parse a manifest entry for "
						+ page.getWebURL().getURL());
				// e.printStackTrace();
				System.err.println("Not continuing with remaining entries.");
				return;
			}
		}

	}

	private String[] getMapping(Element firstRow) throws Exception {
		// get all td or th cells - we assume this to contain header text
		Elements headings = firstRow.select("th");
		headings.addAll(firstRow.select("td")); 

		ArrayList<String> mapping = new ArrayList<String>(10);

		boolean foundHeading = false;
		String currText;

//		Iterator<Element> headingsIter = headings.iterator();
//		
//		while (headingsIter.hasNext()) {
//			Element tdCurrent = (Element) headingsIter.next();
		
		for (Element tdCurrent : headings) {
			currText = tdCurrent.text();

			if (currText != null && !currText.isEmpty()) {
				currText = currText.toLowerCase().replaceAll("\\s", "");

				if (currText.contains("price") || currText.contains("retail")
						|| currText.contains("msrp")) {
					mapping.add(Manifest.FIELD_RETAILPRICE);
					foundHeading = true;
				} else if (currText.contains("desc")) {
					mapping.add(Manifest.FIELD_DESCRIPTION);
					foundHeading = true;
				} else if (currText.contains("quantity")
						|| currText.contains("qty")) {
					mapping.add(Manifest.FIELD_QUANTITY);
					foundHeading = true;
				} else if (currText.contains("model")) {
					mapping.add(Manifest.FIELD_MODEL);
					foundHeading = true;
				} else if (currText.contains("cond")) {
					mapping.add(Manifest.FIELD_CONDITION);
					foundHeading = true;
				} else if (currText.contains("make")) {
					mapping.add(Manifest.FIELD_MAKE);
					foundHeading = true;
				} else if (currText.contains("note")) {
					mapping.add(Manifest.FIELD_NOTES);
					foundHeading = true;
				} else if (currText.contains("grade")) {
					mapping.add(Manifest.FIELD_GRADE);
					foundHeading = true;
				} else if (currText.contains("size")) {
					mapping.add(Manifest.FIELD_SIZE);
					foundHeading = true;
				} else {
					mapping.add(Manifest.HEADING_IGNORE);
				}
			}
		}
		return foundHeading ? mapping.toArray(new String[0]) : null;
	}

	private void parseSeller(Page page) throws IOException {

		Map<String, ValueObject> sellerRow = Seller.initResultMap();

		PageUtils.getInlineValuesFromHTML(page, sellerRow);

		// Manually set the remaining values by processing the "sellerData"
		String sellerDataString = PageUtils.getSellerDataString(page);
		sellerDataString = sellerDataString.substring(
				sellerDataString.indexOf("[") + 1,
				sellerDataString.lastIndexOf("]"));

		String[] sellerData = sellerDataString.split("\\{\"new_seller_flag\"");

		PageUtils.getValuesFromSellerData(sellerData, sellerRow);

		// Seller.FulfilledBy field is derived from a flag which will now be
		// converted to its correct value.
		ValueObject fulfilledByVO = sellerRow.get(Seller.FIELD_FULFILLEDBY);
		if (fulfilledByVO.getValue().equals("0")) {
			fulfilledByVO.setValue(sellerRow.get(Seller.FIELD_SELLERNAME)
					.getValue());
		} else {
			fulfilledByVO.setValue(Seller.LIQUIDATION_DOT_COM);
		}

		insert(SELLER, sellerRow);
		// System.out.println("[SELLER] inserted: " +
		// sellerRow.get(Seller.FIELD_SELLERNAME).getValue());
	}

	private static Table getTable(String tableName) throws IOException {
		return mdb.getTable(tableName);
	}

	private boolean insert(String tableName, Map<String, ValueObject> rowData)
			throws IOException {

		if (rowData == null || rowData.isEmpty()) {
			return true;
		}
		
		boolean result = false;

		Map<String, Object> toInsert = convertToInsertable(rowData);

//		debugging logging disabled to improve performance.
//		System.out.print(PageUtils.TIME_STAMP.format(new Date()) + " attempting to insert into " + tableName + " || ");
//		for (String key : toInsert.keySet()) {
//			System.out.print(key + "=" + toInsert.get(key) + "|");
//		}

		
		try {
			// ***uncomment below to insert into mdb
			getTable(tableName).addRow(getTable(tableName).asRow(toInsert));
			System.out.println(" SUCCESSFUL");
			result = true;
			
		} catch(Exception e) {
			System.out.println(" FAIL (" + e.getMessage() + ")");
			e.printStackTrace();
			result = false;
		}

		mdb.flush();
		
		return result;
	}

	private Map<String, Object> convertToInsertable(
			Map<String, ValueObject> input) {
		Map<String, Object> output = new HashMap<String, Object>();

		for (String key : input.keySet()) {
			Object currentValue = input.get(key).getValue();
			AccessDataType currentType = input.get(key).getAccessDataType();

			if (currentValue != null) {
				currentValue = ((String) currentValue).trim();
				if (((String) currentValue).isEmpty()) {
					currentValue = null;

				} else {
					// replace "N/A" and "null" value for non-strings
					if (currentType != AccessDataType.STRING
							&& (currentValue.toString().toLowerCase()
									.equals("n/a") || currentValue.toString()
									.toLowerCase().equals("null"))) {
						currentValue = null;

						// format dollar values
					} else if (currentType == AccessDataType.CURRENCY) {
						// currentValue = ((String)
						// currentValue).replaceAll("(\\$)",
						// "").replaceAll("(,)", "");
						currentValue = ((String) currentValue).replaceAll(
								"[^\\d\\.]", "");

						// format dates
					} else if (currentType == AccessDataType.DATETIME) {

						try {
							String val = (String) currentValue;
							val = val
									.trim()
									.replaceAll(/* not a regular space */" ", /*
																			 * regular
																			 * space
																			 */
											" ").replaceAll(" ", ""); // mega
																		// hack
							if (!val.endsWith("EST"))
								val += "EST";

							Date d = DATE_FORMAT_US1.parse(val);
							currentValue = d; // inserted into mdb in NZT

						} catch (ParseException e) {
							currentValue = null;
							e.printStackTrace();
						}
					}
				}
			}
			output.put(key, currentValue);
		}

		return output;
	}
	
	private static void initAuctionIdList() throws IOException {
		Table t = getTable(AUCTION);
		
		Collection<String> columnList = new ArrayList<String>(1);
		columnList.add(AUCTION_AUCTIONID);
		
		Iterator<Map<String, Object>> i = t.iterator(columnList);
		
		while (i.hasNext()) {
			Map<java.lang.String, java.lang.Object> map = (Map<java.lang.String, java.lang.Object>) i
					.next();
			auctionIdsList.add((Integer) map.get(AUCTION_AUCTIONID));
		}
		
		Collections.sort(auctionIdsList);
	}
	
	//the mdb methods should really be refactored to another class :(
	public boolean isAuctionIdExists(int auctionId) {
		return auctionIdsList.contains(new Integer(auctionId));
	}

}
