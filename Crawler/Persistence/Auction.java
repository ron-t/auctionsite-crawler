package Crawler.Persistence;

import java.util.*;
import java.util.regex.Pattern;

public class Auction {

	public static final String FIELD_AUCTIONID = "AuctionID";
	public static final String FIELD_DESCRIPTION = "Description";

	private Auction() {
	}

	public static Map<String, ValueObject> initResultMap() {
		// Regex used assumes the HTML is split() on "\n"
		Map<String, ValueObject> result = new HashMap<String, ValueObject>();

		
		// manual values
		result.put(FIELD_AUCTIONID, new ValueObject(AccessDataType.NUMBER));
		result.put("Description", new ValueObject(AccessDataType.STRING));
		
		
		result.put("ClosingDateTime", new ValueObject(Pattern.compile(".*<label id=\"fullTime\".*>(.+)</label>.*"), AccessDataType.DATETIME));

		
		// "inline" values
		result.put("SellerName", new ValueObject(Pattern.compile(".*class=\"sellerName\".+>(.+)</.*"),
				AccessDataType.STRING));
		result.put("AuctionName", new ValueObject(Pattern.compile(".*<title>(.+)</title>.*"), AccessDataType.STRING));
		result.put("TotalBids", new ValueObject(Pattern.compile(".*<span id=\"totalbids\".+>(\\d+)</span>.*"),
				AccessDataType.NUMBER));
		result.put("CurrentHighBid",
				new ValueObject(Pattern.compile(".*<label class=\"value currentBidUpdate\">([^<].+)</label>.*"),
						AccessDataType.CURRENCY));
		result.put("CurrentHighBidder", new ValueObject(
				Pattern.compile(".*<label class=\"value\">(.+\\*.+)</label>.*"), AccessDataType.STRING)); // hacky
		result.put("PerUnitPrice", new ValueObject(Pattern.compile(".*<label class=\"value ppUpdate\">(.+)</label>.*"),
				AccessDataType.CURRENCY));
		result.put("WinningBid",
				new ValueObject(Pattern.compile(".*<label class=\"value currentBidUpdate\"><b>(.+)</b></label>.*"),
						AccessDataType.CURRENCY));

		// "lines before" values
		result.put("TotalLotsAvail", new ValueObject(Pattern.compile(".*<label>(\\d+)"),
				"<div  id=\"total_lot\" class=\"sectionRight\">", 1, AccessDataType.NUMBER));
		result.put("AuctionType", new ValueObject(Pattern.compile(".*<label>(.+)</label>.*"),
				"<div id=\"auction_type\" class=\"sectionRight\">", 1, AccessDataType.STRING));
		result.put("Condition", new ValueObject(Pattern.compile(".*<label>(.+)</label>.*"),
				"<div id=\"auction_condition\" class=\"sectionRight\">", 1, AccessDataType.STRING));
		result.put("QuantityInLot", new ValueObject(Pattern.compile("(\\d+)$"), "<label>Quantity in Lot:</label>", 4,
				AccessDataType.NUMBER));
		result.put("TotalWeight", new ValueObject(Pattern.compile("<label>([\\d\\.]+).*</label>"),
				"<label>Total Weight: </label>", 3, AccessDataType.NUMBER));
		result.put("AssetLocation", new ValueObject(Pattern.compile(".*<label>(.+)</label>.*"),
				"<label>Asset Location:</label>", 3, AccessDataType.STRING));
		result.put("ZipCode", new ValueObject(Pattern.compile(".*<label>(.+)</label>.*"), "<label>Zip Code:</label>",
				3, AccessDataType.STRING));
		result.put("BuyersPremium", new ValueObject(Pattern.compile(".*<label>(\\d+)\\D*</label>.*"),
				"<div id=\"buyers_premium\" class=\"sectionRight\">", 1, AccessDataType.NUMBER));
		result.put("QuantityVariance", new ValueObject(Pattern.compile(".*<label>(\\d+)\\D*</label>.*"),
				"<div id=\"quantity_in_variance\" class=\"sectionRight\">", 1, AccessDataType.STRING));
		result.put("ShippingTerms", new ValueObject(Pattern.compile(".*<label>(.+)</label>.*"),
				"<div id=\"auctionView_shipping_terms_summary\" class=\"sectionRight\">", 1, AccessDataType.STRING));
		result.put("SizeClassification", new ValueObject(Pattern.compile(".*<label>(.+)</label>.*"),
				"<div id=\"auctionView_size_class\" class=\"sectionRight\">", 1, AccessDataType.STRING));
		 result.put("ShippingRestrictions", new ValueObject(Pattern.compile(".*<label>(.+)</label>.*"), "<label>Shipping Restrictions:</label>", 3, AccessDataType.STRING));
		 result.put("DimensionalWeight", new ValueObject(Pattern.compile("<label>([\\d\\.]+).*</label>"), "<label>Dimensional Weight:</label>", 3, AccessDataType.NUMBER));
		 result.put("Class", new ValueObject(Pattern.compile(".*<label>(.+)</label>.*"), "<label>Class:</label>", 3, AccessDataType.STRING));

		// excluded for now
		// result.put("TermsAndConditions", new ValueObject(Pattern.compile(".*.*")));
		// result.put("FirstBid", new ValueObject(Pattern.compile(".*.*")));
		// result.put("CurrentBid", new ValueObject(Pattern.compile(".*.*")));
		// result.put("NextWinningBid", new ValueObject(Pattern.compile(".*.*")));

		return result;
	}
}
