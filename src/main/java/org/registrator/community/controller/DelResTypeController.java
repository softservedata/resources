package org.registrator.community.controller;


import org.registrator.community.service.ResourceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


	@Controller
	@RequestMapping(value ="/registrator-man/")
	public class DelResTypeController {

		@Autowired
		ResourceTypeService resourceTypeService;

	   
		@RequestMapping("/delete-resource-types/{typeId}")
	    public String deleteResourceType(@PathVariable("typeId") Integer typeId) {
			resourceTypeService.delete(typeId);
	        
			String redirectUrl = "http://www.yahoo.com";
	        return "redirect:" + redirectUrl;
	    }
		
		
	

}