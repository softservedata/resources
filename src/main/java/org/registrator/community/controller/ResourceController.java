package org.registrator.community.controller;

import java.util.Date;
import java.util.List;

import org.hibernate.dialect.PointbaseDialect;
import org.registrator.community.dao.TomeRepository;
import org.registrator.community.dto.PointAreaDTO;
import org.registrator.community.dto.PoligonAreaDTO;
import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.dto.ResourceTypeDTO;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.entity.Tome;
import org.registrator.community.service.ResourceService;
import org.registrator.community.service.ResourceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/registrator/resource")
public class ResourceController {

	@Autowired
	ResourceService resourceService;

	// to delete
	@Autowired
	ResourceTypeService resourceTypeService;
	
	// to delete
	@Autowired
	TomeRepository tomeRepository;
	
	/**
	 * Method for loading form for input the parameter of resource (with existing resource
	 * types and registrator)
	 */
	@RequestMapping(value = "/addresource", method = RequestMethod.GET)
	public String addResourceForm(Model model) {
		List<ResourceType> listOfResourceType = resourceTypeService.findAll();
		model.addAttribute("listOfResourceType", listOfResourceType);
		List<Tome> tomes = tomeRepository.findAll();
		model.addAttribute("tomes", tomes);
		ResourceDTO newresource = new ResourceDTO();
		model.addAttribute("newresource", newresource);
		return "addResource";
	}

	/**
	 * Method save the resource in table list_of resources
	 */
	// TODO fill the tables: area, linearValues, discreteValues
	// TODO remove the RequestParameters
	@RequestMapping(value = "/addresource", method = RequestMethod.POST)
	public String addResource(@ModelAttribute("newresource") ResourceDTO resourceDTO, 
			@RequestParam("inputDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date date,
			@RequestParam("resourceTypeEntity") String resTypeName,
			Model model) {
		ResourceTypeDTO resTypeDTO = new ResourceTypeDTO();
		resTypeDTO.setTypeName(resTypeName);
		resourceDTO.setDate(date);
		resourceDTO.setResourceType(resTypeDTO);
		resourceService.addNewResource(resourceDTO);
		model.addAttribute("resource", resourceDTO);
		return "showResource";
	}

}