package Crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import Crawler.Persistence.ValueObject;
import edu.uci.ics.crawler4j.crawler.Page;

public class PageUtils {

	private static ArrayList<Integer> validParentDocIDs = new ArrayList<Integer>(100);

	public static final String AUCTION_LIST_PAGINATE_SUFFIX = "&sort=close_time&ascending=1&_per_page=30&_page=";
	public static final String BID_PAGINATE_SUFFIX = "&page=";
	public static final DateFormat TIME_STAMP = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	private static final Pattern manifestUrlRegex = Pattern.compile(".+aucimg/\\d+/m(\\d+)\\..+");

	public static boolean isOfType(Page page, LiquidationPageType type) {
		return isOfType(page.getWebURL().getURL(), type);
	}

	public static boolean isOfType(String url, LiquidationPageType type) {
		return type == getPageType(url);
	}

	public static LiquidationPageType getPageType(Page page) {
		return getPageType(page.getWebURL().getURL());
	}

	public static LiquidationPageType getPageType(String url) {
		if (url.matches("http://www\\.liquidation\\.com/auction/view\\?id=\\d+")) {
			return LiquidationPageType.AUCTION;
		}
		if (url.startsWith("http://www.liquidation.com/auction/bidhistory?")) {
			return LiquidationPageType.BIDS;
		}
		if (url.startsWith("http://www.liquidation.com/aucimg/")) {
			return LiquidationPageType.MANIFEST;
		}
		//http://www.liquidation.com/auction/search?query=0Q222Gex9z2.0yJ.9GJxTypp9o35L...k22Gkc222V&sort=close_time&ascending=1&_per_page=30&_page=1
		if (url.matches("http://www\\.liquidation\\.com/auction/.+" + AUCTION_LIST_PAGINATE_SUFFIX + "1$")) {
			// previous seller match condition: if
			// (url.getURL().startsWith("http://www.liquidation.com/auction/search?seller=") && url.getURL().length() >
			// 49) {
			return LiquidationPageType.SELLER;
		}
		if (url.startsWith("http://www.liquidation.com/auction/search?query=")) {
			return LiquidationPageType.SEARCH_QUERY;
		}

		return null;
	}

	public static String getAuctionIDFromPage(Page page) {
		String url = page.getWebURL().getURL();
		
		return getAuctionIDFromURL(url);
	}
	
	public static String getAuctionIDFromURL(String url) {
		if (isOfType(url, LiquidationPageType.AUCTION) || isOfType(url, LiquidationPageType.BIDS)) {
			return url.substring(url.toLowerCase().indexOf("id=") + 3); // hacky
			
		} else if (isOfType(url, LiquidationPageType.MANIFEST)) {
			try {
				Matcher m = manifestUrlRegex.matcher(url);
				return (m.matches()) ? m.group(1) : null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public static String getSellerNameFromURL(Page page) {
		if (isOfType(page, LiquidationPageType.SELLER)) {
			String url = page.getWebURL().getURL();

			return url.substring(url.indexOf("=") + 1);
		}

		return null;
	}

	public static String getInlineValuesFromHTML(String[] html, Map<String, ValueObject> result) {

		for (String line : html) {
			for (String field : result.keySet()) {

				if (result.get(field).getRegex() != null && result.get(field).getLineBefore() == null) { // only process
																											// "inline"
																											// values.
					Matcher m = result.get(field).getRegex().matcher(line);
					if (m.matches()) {
						// System.out.println("*** pattern:" +
						// result.get(field).getRegex().pattern());
						// System.out.println("*** line: " + line);
						// System.out.println("*** group 0: " + m.group());

						result.get(field).setValue(StringEscapeUtils.unescapeHtml(m.group(1).trim()));
					}
				}
			}
		}

		return null;
	}

	public static String getInlineValuesFromHTML(Page page, Map<String, ValueObject> result, String splitOn) {
		String[] html = page.getHTML().split(splitOn);

		return getInlineValuesFromHTML(html, result);
	}

	public static String getInlineValuesFromHTML(Page page, Map<String, ValueObject> result) {
		return getInlineValuesFromHTML(page, result, "\n");
	}

	public static String getLinesBeforeValuesFromHTML(Page page, Map<String, ValueObject> result) {
		String[] html = page.getHTML().split("\n");

		for (int lineNum = 0; lineNum < html.length; lineNum++) {
			String currentLine = html[lineNum].trim();

			if (currentLine.isEmpty())
				continue;

			for (String field : result.keySet()) {

				Pattern regex = result.get(field).getRegex();
				String lineBeforeToMatch = result.get(field).getLineBefore();
				int numLinesBeforeToMatch = result.get(field).getNumLinesBefore();

				if (regex != null && lineBeforeToMatch != null) { // only process "lines before" values.
					lineBeforeToMatch = lineBeforeToMatch.toLowerCase().replaceAll("\\s", "");

					String htmlLineBefore = "";

					try { // hack to ignore errors when checking first few lines
						htmlLineBefore = html[lineNum - numLinesBeforeToMatch].toLowerCase().replaceAll("\\s", "");
					} catch (ArrayIndexOutOfBoundsException e) {
					}

					Matcher m = regex.matcher(currentLine);
					if (m.matches()) {

						if (lineBeforeToMatch.equals(htmlLineBefore)) {
							// System.out.println("" + field + " | " + result.get(field).getRegex().pattern() + " | " +
							// m.group());
							String value = StringEscapeUtils.unescapeHtml(m.group(1).trim());

							result.get(field).setValue(value);
						}

					}
				}
			}

		}

		return null;
	}

	public static String getValuesFromSellerData(String[] sellerData, Map<String, ValueObject> result) {

		for (int i = 1; i < sellerData.length; i++) { // ignore the first element which is a blank string.
			String line = sellerData[i];

			for (String field : result.keySet()) {

				ValueObject vo = result.get(field);
				if (vo.getRegex() != null && vo.getLineBefore() == null && vo.getNumLinesBefore() == -1) { // only
																											// process
																											// "manual"
																											// seller
																											// values.

					if (field.endsWith(getSellerDataLinePeriod(line))) {

						Matcher m = result.get(field).getRegex().matcher(line);
						if (m.matches()) {
							// System.out.println("*** pattern:" +
							// result.get(field).getRegex().pattern());
							// System.out.println("*** line: " + line);
							// System.out.println("*** group 0: " + m.group());

							result.get(field).setValue(StringEscapeUtils.unescapeHtml(m.group(1).trim()));
						}
					}
				}
			}
		}

		return null;
	}

	public static String getSellerDataString(Page page) {
		String toReturn = null;

		try {
			toReturn = page.getHTML().split("(sellerData\\s+=\\s+)|(sellerCredibility\\.init)", 3)[1];

		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}

		return toReturn;
	}

	public static String getSellerDataLinePeriod(String line) {
		return line.substring(line.indexOf("period_days\":") + "period_days\":".length(), line.lastIndexOf("}"));
	}

	public static int getAuctionListMaxPageNumber(String url) {

		Pattern paginationRegex = Pattern.compile(".+" + AUCTION_LIST_PAGINATE_SUFFIX + "(\\d+)\".+>\\d+.+");
		// System.out.println(paginationRegex.pattern());
		OutputStreamWriter wr = null;
		BufferedReader rd = null;
		int page = 1;
		try {

			URLConnection conn;

			url += AUCTION_LIST_PAGINATE_SUFFIX + page;
			// Send data
			URL u = new URL(url);
			conn = u.openConnection();
			conn.setDoOutput(true);

			wr = new OutputStreamWriter(conn.getOutputStream());
			wr.flush();

			// Get the response
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line;
			Matcher m;

			while ((line = rd.readLine()) != null) {
				m = paginationRegex.matcher(line);

				if (m.matches()) {
					int currentPage = Integer.parseInt(m.group(1));

					if (currentPage > page) {
						page = currentPage;
					}
				}
			}
			wr.close();
			rd.close();

		} catch (Exception e) {
			System.err.println("Something went wrong for " + url + " :(");
			e.printStackTrace();
			page = -1;
		} finally {

			try {
				if (wr != null) {
					wr.close();
				}
				if (rd != null) {
					rd.close();
				}
			} catch (IOException e) {
				page = 1;
			}
		}

		return page;
	}

	// TODO change this so it gets the maxPageNumber from the bid Page - use pagefetcher and jsoup
	public static int getBidMaxPageNumber(Page page) {
		int maxPageNum = 1;

		try {
			Document doc = Jsoup.parse(page.getHTML());
			Element div = doc.getElementsByClass("pagination").first();

			maxPageNum = Integer.parseInt(div.getElementsMatchingText("\\d+").last().text());
		} catch (Exception e) {
			//System.err.println("Error retrieving max bid page number. Using 1.");
		}

		return maxPageNum;
	}

	public static String addPageSuffix(String url, int page) {
		return url += AUCTION_LIST_PAGINATE_SUFFIX + page;
	}

	public static void addValidParentDocID(int docID) {
		validParentDocIDs.add(docID);
	}

	public static boolean isValidParentPage(int docID) {
		return validParentDocIDs.contains(docID);
	}

}
