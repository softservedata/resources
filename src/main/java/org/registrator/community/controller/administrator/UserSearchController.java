package org.registrator.community.controller.administrator;

import org.registrator.community.dto.search.PersonDemoDTO;
import org.registrator.community.dto.search.TableSearchRequestDTO;
import org.registrator.community.dto.search.TableSearchResponceDTO;
import org.registrator.community.service.UserSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/administrator/users/")
public class UserSearchController {
	
	@Autowired
	UserSearchService userSearchService;
	
	@RequestMapping(value="search")
	public String getUserBySearchCriteria(){
		return "userDataTable";
	}
	
	@ResponseBody
	@RequestMapping(value = "getData",method = RequestMethod.POST)
	public TableSearchResponceDTO<PersonDemoDTO> getDataFromDataTable(@RequestBody TableSearchRequestDTO dataTableRequest){
		
		TableSearchResponceDTO<PersonDemoDTO> dto = userSearchService.getTableSearchResponce(dataTableRequest);
		return dto;
	}
	
}
