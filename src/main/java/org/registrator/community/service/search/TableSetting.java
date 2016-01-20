package org.registrator.community.service.search;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="table")
@XmlAccessorType(XmlAccessType.FIELD)
public class TableSetting {
	
	@XmlAttribute(required=true)
	private String tableName;
	
	@XmlElement(required=true)
	private String url;
	
	@XmlElement(required=false)
	private String script;
	
	@XmlElement(required=false)
	private String tableTitle;
	
	@XmlElement(name="column")
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

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableName() {
		return tableName;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getTableTitle() {
		return tableTitle;
	}

	public void setTableTitle(String tableTitle) {
		this.tableTitle = tableTitle;
	}
}