package org.registrator.community.controller;


import org.registrator.community.service.ResourceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class EditResTypeController {

	@Autowired
	ResourceTypeService resourceTypeService;

   
	@RequestMapping("/edit-resource-types/{typeId}/{typeName}")
    public String editResourceType(@PathVariable("typeId") Integer typeId, @PathVariable("typeName")String typeName) {
		resourceTypeService.editResourceType(typeId, typeName);
        
		
		
		String redirectUrl = "http://www.yahoo.com";
        return "redirect:" + redirectUrl;
    }
	
	


}

