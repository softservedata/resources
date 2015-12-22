package org.registrator.community.controller;

import java.util.List;

import org.registrator.community.entity.ResourceType;
import org.registrator.community.service.ResourceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/registrator/resourcetypes/")
public class ResourceTypeController {

	@Autowired
	ResourceTypeService resourceTypeService;


	@RequestMapping(value = "/show-res-types", method = RequestMethod.GET)
	public String showResourceType(Model model) {
		List<ResourceType> listOfResourceType = resourceTypeService.findAll();
		model.addAttribute("listOfResourceType", listOfResourceType);
		return "allResourcesTypes";
	}

	@RequestMapping(value = "/show-one-res-types/{typeName}", method = RequestMethod.GET)
	public String showOneResourceType(@PathVariable String typeName, Model model) {
		ResourceType oneResType = resourceTypeService.findByName(typeName);
		model.addAttribute("oneResType", oneResType);
		return "findOneResType";
	}
/*	@Transactional
	@RequestMapping(value = "/show-resourse-types", method = RequestMethod.GET)
	public String showAllResourceType(@PathVariable String typeName, Model model) {
		ResourceTypeDTO resTDTO = resourceTypeService.findByName(typeName);
		model.addAttribute("oneResType", oneResType);
		return "findOneResType";
	}*/

/*	@ResponseBody
	@RequestMapping(value = "/show-all-type", method = RequestMethod.GET)
	public List<ResourceType> findAll() {
		List<ResourceType> listOfResourceType = resourceTypeService.findAll();

		return listOfResourceType;
	}*/

	@RequestMapping(value = "/delete/{typeName}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteResourceType(@PathVariable String typeName) {
		resourceTypeService.delete(resourceTypeService.findByName(typeName));
	}
}
