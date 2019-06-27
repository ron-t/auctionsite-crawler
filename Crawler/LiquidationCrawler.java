package Crawler;

import java.io.IOException;
import java.util.Date;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * 
 */

public class LiquidationCrawler extends WebCrawler {

	Pattern filters = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" + "|png|tiff?|mid|mp2|mp3|mp4"
			+ "|wav|avi|mov|mpeg|ram|m4v|pdf" + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

	Pattern excludes = Pattern.compile(".*&tab=.*");

	private static final LiquidationPageParser PARSER = new LiquidationPageParser();
	
	public LiquidationCrawler() {
	}

	public boolean shouldVisit(WebURL url) {

		String href = url.getURL().toLowerCase();
		String urlString = url.getURL();

		// ignore non 'page' pages
		if (filters.matcher(href).matches()) {
			return false;
		}
		// exclude 'sub-pages'
		if (excludes.matcher(href).matches()) {
			return false;
		}
		// Auction page
		if (PageUtils.getPageType(urlString) == LiquidationPageType.AUCTION) {
			
			Integer auctionId = Integer.parseInt(PageUtils.getAuctionIDFromURL(urlString));
			
			if(PARSER.isAuctionIdExists(auctionId)) {
				System.out.println("AUCTION ALREADY EXISTS : " + urlString);
			}
			
			return !PARSER.isAuctionIdExists(auctionId); //only visit if auction doesn't already exist in db
		}
		// Auction Bids
		if (PageUtils.getPageType(urlString) == LiquidationPageType.BIDS) {
			if (url.getURL().contains(PageUtils.BID_PAGINATE_SUFFIX)) {
				return false;
			}
			return true;
		}
		// Auction Manifest
		if (PageUtils.getPageType(urlString) == LiquidationPageType.MANIFEST) {
			return true;
		}
		// Seller page
		if (PageUtils.getPageType(urlString) == LiquidationPageType.SELLER) {
			return true;
		}
		// // Auction list page - synonymous with the "seller" page
		// if (PageUtils.getPageType(url) == LiquidationPageType.AUCTION_LIST) {
		// return true;
		// }

		return false;
	}

	public void visit(Page page) {
		try {
			// HACK: the criterion for Auction pages matches
			// auctions by other sellers linked from the current seller's
			// auction pages. The if statement below excludes such auctions from
			// being parsed.
			if (PageUtils.isOfType(page, LiquidationPageType.AUCTION) && !PageUtils.isValidParentPage(page.getWebURL().getParentDocid())) {
//				System.out.println("(ignoring " + page.getWebURL() + " )");
				return;
			}
			
			System.out.println(PageUtils.TIME_STAMP.format(new Date()) + ": visiting " + page.getWebURL());
			PARSER.parse(page, PageUtils.getPageType(page));

		} catch (IOException e) {
			System.err.println("Unable to parse page: " + page.getWebURL());
			e.printStackTrace();
		}

	}
}