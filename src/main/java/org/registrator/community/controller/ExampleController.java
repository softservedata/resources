package org.registrator.community.controller;

import org.registrator.community.dto.ResourceTypeDTO;
import org.registrator.community.entity.Inquiry;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.service.ResourceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/registrator/resourcetypes/")
public class ExampleController {

	@Autowired
	ResourceTypeService resourceTypeService;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addResourceTypeForm(Model model) {
		/*
		 * resourceTypeService.addResourceType(new
		 * ResourceType("SuperResourceTypeOhhh"));
		 */
		/* model.addAttribute("newRT", new ResourceType()); */
		return "addExample";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addResourceType(@ModelAttribute("newRTDTO") ResourceTypeDTO newRTDTO, Model model) {

		resourceTypeService.addResourceTypeDTO(newRTDTO);

		newRTDTO = resourceTypeService.addResourceTypeDTO(newRTDTO);
		model.addAttribute("typeName", newRTDTO);

		return "redirect:/registrator/resourcetypes1/add/";
	}

}
