package Crawler.Persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Seller {
	private Seller() {
	}

	public static final String FIELD_FULFILLEDBY = "FulfilledBy";
	public static final String FIELD_SELLERNAME = "SellerName";
	public static final String LIQUIDATION_DOT_COM = "Liquidation.com";

	public static final String FIELD_PREF_COMPLETEDTRANS = "CompletedTrans";
	public static final String FIELD_PREF_AVGDAYSTOSHIP = "AvgDaysToShip";
	public static final String FIELD_PREF_BUYERDISPUTERATE = "BuyerDisputeRate";
	public static final String FIELD_PREF_REPEATBUYERRATE = "RepeatBuyerRate";
	public static final String FIELD_PREF_SELLERCANCELRATE = "SellerCancelRate";

	public static final String[] PERIODS = { "30", "90", "365" };

	public static Map<String, ValueObject> initResultMap() {
		Map<String, ValueObject> result = new HashMap<String, ValueObject>();

		// "inline" values
		//[2012-10-29] HTML changed so need to match another regex to get the seller's name from an auction listing page  
		//[OLD regex] result.put(FIELD_SELLERNAME, new ValueObject(Pattern.compile(".*<a href=\"/auction/search\\?seller=(.+)\">.*"),
		//		AccessDataType.STRING)); 
		
		//<a class="seller" id="4833676-onlinereturns" href="/auction/search?query=TQCaLa2ik2CA0Qmh2Ge.9Qe5TQjM9-R23Tk29yb.I-b50oJh3TmW0aCAuTeU2N12TQeU922x22VV&flag=new&username_seller=onlinereturns">onlinereturns</a>
		
		result.put(FIELD_SELLERNAME, new ValueObject(Pattern.compile(".*<a class=\"seller\".+\">(.+)</a>.*"),
				AccessDataType.STRING));
		result.put("SellingFor", new ValueObject(Pattern.compile(".*time_since_creation_text\":\"([\\w\\s]+)\".*"),
				AccessDataType.STRING));
		result.put(FIELD_FULFILLEDBY, new ValueObject(Pattern.compile(".*warehouse_seller_flag_denorm\":(\\d).*"),
				AccessDataType.STRING));


		// "manual" values
		Pattern completedTransRegex = Pattern.compile(".*approx_completed_transactions\":\"(.+?)\",\"time_since_creation.*");
		result.put(FIELD_PREF_COMPLETEDTRANS + PERIODS[0], new ValueObject(completedTransRegex, null, -1, AccessDataType.STRING));
		result.put(FIELD_PREF_COMPLETEDTRANS + PERIODS[1], new ValueObject(completedTransRegex, null, -1, AccessDataType.STRING));
		result.put(FIELD_PREF_COMPLETEDTRANS + PERIODS[2], new ValueObject(completedTransRegex, null, -1, AccessDataType.STRING));

		Pattern avgDaysToShipRegex = Pattern.compile(".*avg_days_to_ship\":\"?(.+?)\"?\\,\"top_seller_flag.*");
		result.put(FIELD_PREF_AVGDAYSTOSHIP + PERIODS[0], new ValueObject(avgDaysToShipRegex, null, -1, AccessDataType.NUMBER));
		result.put(FIELD_PREF_AVGDAYSTOSHIP + PERIODS[1], new ValueObject(avgDaysToShipRegex, null, -1, AccessDataType.NUMBER));
		result.put(FIELD_PREF_AVGDAYSTOSHIP + PERIODS[2], new ValueObject(avgDaysToShipRegex, null, -1, AccessDataType.NUMBER));

		Pattern buyersDisputeRateRegex = Pattern.compile(".*pct_honored_disputes\":\"?(.+?)\"?\\,\"avg_days_to_ship_rank.*");
		result.put(FIELD_PREF_BUYERDISPUTERATE + PERIODS[0], new ValueObject(buyersDisputeRateRegex, null, -1, AccessDataType.NUMBER));
		result.put(FIELD_PREF_BUYERDISPUTERATE + PERIODS[1], new ValueObject(buyersDisputeRateRegex, null, -1, AccessDataType.NUMBER));
		result.put(FIELD_PREF_BUYERDISPUTERATE + PERIODS[2], new ValueObject(buyersDisputeRateRegex, null, -1, AccessDataType.NUMBER));

		Pattern repeatBuyerRateRegex = Pattern.compile(".*pct_repeat_buyers\":\"?(.+?)\"?\\,\"seller_kpi.*");
		result.put(FIELD_PREF_REPEATBUYERRATE + PERIODS[0], new ValueObject(repeatBuyerRateRegex, null, -1, AccessDataType.NUMBER));
		result.put(FIELD_PREF_REPEATBUYERRATE + PERIODS[1], new ValueObject(repeatBuyerRateRegex, null, -1, AccessDataType.NUMBER));
		result.put(FIELD_PREF_REPEATBUYERRATE + PERIODS[2], new ValueObject(repeatBuyerRateRegex, null, -1, AccessDataType.NUMBER));
		
		Pattern sellerCancelRateRegex = Pattern.compile(".*pct_canceled_transactions\":\"?(.+?)\"?\\,\"pct_honored_disputes_rank.*");
		result.put(FIELD_PREF_SELLERCANCELRATE + PERIODS[0], new ValueObject(sellerCancelRateRegex, null, -1, AccessDataType.NUMBER));
		result.put(FIELD_PREF_SELLERCANCELRATE + PERIODS[1], new ValueObject(sellerCancelRateRegex, null, -1, AccessDataType.NUMBER));
		result.put(FIELD_PREF_SELLERCANCELRATE + PERIODS[2], new ValueObject(sellerCancelRateRegex, null, -1, AccessDataType.NUMBER));
		
		return result;
	}
}
