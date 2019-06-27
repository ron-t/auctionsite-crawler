package Crawler.Persistence;

import java.util.regex.Pattern;

public class ValueObject {
	private String value;
	private Pattern regex;
	private String lineBefore;
	private int numLinesBefore;
	;
	public final int getNumLinesBefore() {
		return numLinesBefore;
	}

	private AccessDataType dataType;
	
	public ValueObject(AccessDataType dataType) {
		this.dataType = dataType;
	}

	public ValueObject(Pattern regex) {
		super();
		this.regex = regex;
		this.lineBefore = null;
		this.dataType = AccessDataType.STRING;
	}
	
	public ValueObject(Pattern regex, String lineBefore, int numLinesBefore) {
		super();
		this.regex = regex;
		this.lineBefore = lineBefore;
		this.numLinesBefore = numLinesBefore;
		this.dataType = AccessDataType.STRING;
	}

	public ValueObject(Pattern regex, String lineBefore, int numLinesBefore, AccessDataType dataType) {
		super();
		this.regex = regex;
		this.lineBefore = lineBefore;
		this.numLinesBefore = numLinesBefore;
		this.dataType = dataType;
	}
	
	public ValueObject(Pattern regex, AccessDataType dataType) {
		super();
		this.regex = regex;
		this.lineBefore = null;
		this.dataType = dataType;
	}

	public final String getValue() {
		return value;
	}

	public final void setValue(String value) {
		this.value = value;
	}

	public final Pattern getRegex() {
		return regex;
	}

	public final String getLineBefore() {
		return lineBefore;
	}

	public final AccessDataType getAccessDataType() {
		return dataType;
	}
	
}
