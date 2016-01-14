package org.registrator.community.service.search;

import java.util.List;

public class TableColumnSetting {
	
	private String type;
	
	private String title;
	
	private String data;
	
	private List<String> searchType;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getSearchType() {
		return searchType;
	}

	public void setSearchType(List<String> searchType) {
		this.searchType = searchType;
	}
	
}