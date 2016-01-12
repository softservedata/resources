package org.registrator.community.dto.search;

public class Search {

	private String value;
	
	private Boolean regex;
	
	private String compareSign;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Boolean getRegex() {
		return regex;
	}

	public void setRegex(Boolean regex) {
		this.regex = regex;
	}

	public String getCompareSign() {
		return compareSign;
	}

	public void setCompareSign(String compareSign) {
		this.compareSign = compareSign;
	}

}
