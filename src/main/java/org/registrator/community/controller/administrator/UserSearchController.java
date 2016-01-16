package org.registrator.community.controller.administrator;

import javax.validation.Valid;

import org.registrator.community.components.TableSettingsFactory;
import org.registrator.community.dto.search.TableSearchRequestDTO;
import org.registrator.community.dto.search.TableSearchResponseDTO;
import org.registrator.community.entity.User;
import org.registrator.community.service.search.BaseSearchService;
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
	
	@Autowired
	TableSettingsFactory tableSettingsFactory;
	
	@RequestMapping(value="search")
	public String getUserBySearchCriteria(Model model){
		model.addAttribute("tableSetting", tableSettingsFactory.getTableSetting("userSearch"));
		return "searchTableTemplate";
	}
	
	@ResponseBody
	@RequestMapping(value = "getData",method = RequestMethod.POST)
	public TableSearchResponseDTO getDataFromDataTable(@Valid @RequestBody TableSearchRequestDTO dataTableRequest){
		
		TableSearchResponseDTO dto = userSearchService.executeSearchRequest(dataTableRequest);
		return dto;
	}
	
}