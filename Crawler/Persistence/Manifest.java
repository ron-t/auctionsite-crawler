package Crawler.Persistence;

import java.util.HashMap;
import java.util.Map;

public class Manifest {

	private Manifest() {
	}

	public static final String FIELD_AUCTIONID = "AuctionID";
	public static final String FIELD_RETAILPRICE = "RetailPrice";
	public static final String FIELD_DESCRIPTION = "Description";
	public static final String FIELD_QUANTITY = "Quantity";
	public static final String FIELD_MODEL = "Model";
	public static final String FIELD_CONDITION = "Condition";
	public static final String FIELD_MAKE = "Make";
	public static final String FIELD_NOTES = "Notes";
	public static final String FIELD_GRADE = "Grade";
	public static final String FIELD_SIZE = "Size";
	
	public static final String HEADING_IGNORE = "XXX";

	public static Map<String, ValueObject> initResultMap() {
		Map<String, ValueObject> result = new HashMap<String, ValueObject>();

		result.put(FIELD_AUCTIONID, new ValueObject(AccessDataType.NUMBER));
		result.put(FIELD_RETAILPRICE, new ValueObject(AccessDataType.CURRENCY));
		result.put(FIELD_DESCRIPTION, new ValueObject(AccessDataType.STRING));
		result.put(FIELD_QUANTITY, new ValueObject(AccessDataType.NUMBER));
		result.put(FIELD_MODEL, new ValueObject(AccessDataType.STRING));
		result.put(FIELD_CONDITION, new ValueObject(AccessDataType.STRING));
		result.put(FIELD_MAKE, new ValueObject(AccessDataType.STRING));
		result.put(FIELD_NOTES, new ValueObject(AccessDataType.STRING));
		result.put(FIELD_GRADE, new ValueObject(AccessDataType.STRING));
		result.put(FIELD_SIZE, new ValueObject(AccessDataType.STRING));

		return result;
	}
}
