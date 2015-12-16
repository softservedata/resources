package org.registrator.community.controller;

import org.registrator.community.service.ResourceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value ="/registrator/unknown")
public class UnknownController {

	@Autowired
	ResourceTypeService resourceTypeService;
	
	@ResponseBody
	@RequestMapping(value ="/un",method = RequestMethod.GET)
	public String res(){
		return "redirect:/registrator/";
	}
/*	@RequestMapping(value="/{typeId}", method = RequestMethod.GET)
	@ResponseBody
	public void delete(@PathVariable int typeId) {
	  resourceTypeService.delete(typeId);
	}*/
	
	
	
	
	
	
}
