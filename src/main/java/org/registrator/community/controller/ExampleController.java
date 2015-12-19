package org.registrator.community.controller;

import org.registrator.community.dto.DiscreteParameterDTO;
import org.registrator.community.dto.LinearParameterDTO;
import org.registrator.community.dto.ResourceTypeDTO;
import org.registrator.community.dto.TypeParameterDTO;
import org.registrator.community.entity.Inquiry;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.service.ResourceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

	@RequestMapping(value = "/addrestype", method = RequestMethod.GET)
	public String addResourceTypeForm(Model model) {
		/*model.addAttribute("newlinepar", new LinearParameterDTO());
		model.addAttribute("newdiscretepar", new DiscreteParameterDTO());
		model.addAttribute("newtypeparam", new TypeParameterDTO());*/
		model.addAttribute("newrestype", new ResourceTypeDTO());
		return "addResType";
	}

	@RequestMapping(value = "/addrestype", method = RequestMethod.POST)
	public String addResource(@ModelAttribute("newrestype") ResourceTypeDTO resourceTypeDTO, 
		/*	@RequestParam("inputDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date date,
			@RequestParam("resourceTypeEntity") String resTypeName,*/
			
			Model model) {
		
		System.out.println(resourceTypeDTO.getParameters().get(0).getParametersType());
		System.out.println(resourceTypeDTO.getParameters().get(0).getDescription());
		System.out.println(resourceTypeDTO.getParameters().get(0).getUnitName());
		resourceTypeService.addResourceTypeDTO(resourceTypeDTO);
		model.addAttribute("resourceType", resourceTypeDTO);
		return "addResType";
	}
	
	
	
}
