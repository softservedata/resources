package org.registrator.community.controller.administrator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.registrator.community.dto.search.TableSearchRequestDTO;
import org.registrator.community.dto.search.TableSearchResponseDTO;
import org.registrator.community.entity.User;
import org.registrator.community.service.search.BaseSearchService;
import org.registrator.community.service.search.TableColumnSetting;
import org.registrator.community.service.search.TableSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/administrator/users/")
public class UserSearchController {
	
	@Autowired
	@Qualifier("userSearchService")
	BaseSearchService<User> userSearchService;
	
	@RequestMapping(value="search")
	public String getUserBySearchCriteria(Model model){
		
		TableSetting tableSetting = new TableSetting();
		tableSetting.setUrl("getData");
		
		TableColumnSetting columnSetting1 = new TableColumnSetting();
		columnSetting1.setTitle("Ім'я");
		columnSetting1.setData("firstName");
		columnSetting1.setType("search");
		columnSetting1.setSearchType(Arrays.asList("equals","greater","less"));
		
		TableColumnSetting columnSetting2 = new TableColumnSetting();
		columnSetting2.setTitle("Прізвище");
		columnSetting2.setData("lastName");
		columnSetting2.setType("search");
		columnSetting2.setSearchType(Arrays.asList("equals","greater","less"));
		
		TableColumnSetting columnSetting3 = new TableColumnSetting();
		columnSetting3.setTitle("По батькові");
		columnSetting3.setData("middleName");
		columnSetting3.setType("search");
		columnSetting3.setSearchType(Arrays.asList("equals","greater","less"));
		
		TableColumnSetting columnSetting4 = new TableColumnSetting();
		columnSetting4.setTitle("Електронна пошта");
		columnSetting4.setData("email");
		columnSetting4.setType("search");
		columnSetting4.setSearchType(Arrays.asList("equals","greater","less"));
		
		TableColumnSetting columnSetting5 = new TableColumnSetting();
		columnSetting5.setTitle("Місто");
		columnSetting5.setData("address_city");
		columnSetting5.setType("search");
		columnSetting5.setSearchType(Arrays.asList("equals","greater","less"));
		
		TableColumnSetting columnSetting6 = new TableColumnSetting();
		columnSetting6.setTitle("Дії");
		columnSetting6.setData("email");
		columnSetting6.setType("button");
		
		List<TableColumnSetting> columnsSettings = new ArrayList<TableColumnSetting>();
		columnsSettings.add(columnSetting1);
		columnsSettings.add(columnSetting2);
		columnsSettings.add(columnSetting3);
		columnsSettings.add(columnSetting4);
		columnsSettings.add(columnSetting5);
		columnsSettings.add(columnSetting6);
		
		tableSetting.setColumnsSetting(columnsSettings);
		
		model.addAttribute("tableSetting", tableSetting);
		return "searchTableTemplate";
	}
	
	@ResponseBody
	@RequestMapping(value = "getData",method = RequestMethod.POST)
	public TableSearchResponseDTO getDataFromDataTable(@Valid @RequestBody TableSearchRequestDTO dataTableRequest){
		
		TableSearchResponseDTO dto = userSearchService.executeSearchRequest(dataTableRequest);
		return dto;
	}
	
}