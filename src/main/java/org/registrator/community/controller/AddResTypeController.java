package org.registrator.community.controller;

import org.registrator.community.entity.ResourceType;
import org.registrator.community.service.ResourceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value ="/registrator/resourcetypes/")
public class AddResTypeController {

	@Autowired
	ResourceTypeService resourceTypeService;
	
	

@RequestMapping(value = "/add-resource-types", method = RequestMethod.GET)
   public String addResourceTypeForm(Model model) {
       return "addResourceType";
   }
	@RequestMapping(value = "/add-resource-types", method = RequestMethod.POST)
    public String addResourceType(@ModelAttribute("newRT")ResourceType newRT, Model model) {

		newRT = resourceTypeService.addResourceType(newRT);
        model.addAttribute("newRT", newRT);

        return "addResourceType";
    }
	
	
}












