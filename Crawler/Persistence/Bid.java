package Crawler.Persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Bid {
	private Bid() {
	}
	
	public static final String FIELD_BIDNUMBER = "BidNumber";
	public static final String FIELD_AUCTIONID = "AuctionID";

	public static Map<String, ValueObject> initResultMap() {
		Map<String, ValueObject> result = new HashMap<String, ValueObject>();

		// Manual
		result.put(FIELD_AUCTIONID, new ValueObject(AccessDataType.NUMBER));
		
		//"inline" values (all parsed lines have had white space removed)
		result.put(FIELD_BIDNUMBER, new ValueObject(Pattern.compile(".*class=\"bid-Sno.+>(\\d+)<.+?"), AccessDataType.NUMBER));
		result.put("BidAmount", new ValueObject(Pattern.compile(".*class=\"cur-bid.+>(.+?)<.+?"), AccessDataType.CURRENCY));
		result.put("Bidder", new ValueObject(Pattern.compile(".*class=\"bidder.+>(.+?)<.+?"), AccessDataType.STRING));
		result.put("Status", new ValueObject(Pattern.compile(".*class=\"status.+>(.+?)<.+?"), AccessDataType.STRING));
		result.put("BidDate", new ValueObject(Pattern.compile(".*class=\"bid\\-date\"><b>(.+?)(<font.+/font>)?</b>.*"), AccessDataType.DATETIME));
		result.put("BidType", new ValueObject(Pattern.compile(".*class=\"type.+>(.+?)<.+?"), AccessDataType.STRING));
		
		return result;
	}
}
