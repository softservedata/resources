package org.registrator.community.service.search;

import java.util.ArrayList;
import java.util.List;

public class TableSetting {
	
	private String url;
	
	private List<TableColumnSetting> columnsSetting = new ArrayList<TableColumnSetting>();

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<TableColumnSetting> getColumnsSetting() {
		return columnsSetting;
	}

	public void setColumnsSetting(List<TableColumnSetting> columnsSetting) {
		this.columnsSetting = columnsSetting;
	}
	
	public void addColumnSetting(TableColumnSetting tableColumnSetting){
		columnsSetting.add(tableColumnSetting);
	}
	
}
