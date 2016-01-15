package org.registrator.community.components;

import java.util.Map;

import org.registrator.community.service.search.DataTableSettingsHolder;
import org.registrator.community.service.search.TableSetting;
import org.registrator.community.util.XmlAnalyzerUtil;
import org.springframework.stereotype.Component;

@Component
public class TableSettingsFactory {
	
	private final String TABLE_SETTINGS_FILE_NAME="/searchTable.xml";
	
	public TableSettingsFactory() {
		init();
	}

	private Map<String,TableSetting> tableSettingsMap;
	
	private void init(){
		tableSettingsMap=new XmlAnalyzerUtil().unmarshal(DataTableSettingsHolder.class, TABLE_SETTINGS_FILE_NAME)
				.getTables();
	}
	
	public TableSetting getTableSetting(String tableName){
		return tableSettingsMap.get(tableName);
	}
}
