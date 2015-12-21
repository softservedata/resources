package org.registrator.community.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.registrator.community.dao.TomeRepository;
import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.dto.ResourceTypeDTO;
import org.registrator.community.dto.ResourcesJson;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.entity.Tome;
import org.registrator.community.service.ResourceService;
import org.registrator.community.service.ResourceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
		model.addAttribute("newresource", new ResourceDTO());
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

    @RequestMapping(value = "/showAllResources", method = RequestMethod.GET)
    public String showAllResources (Model model) {
        List<ResourceType> resourceTypes = resourceTypeService.findAll();
        model.addAttribute("resourceTypes", resourceTypes);
        return "showAllResources";
    }

    @ResponseBody
    @RequestMapping(value = "/getResourcesByTypeId", method = RequestMethod.POST)
    public String showAllResourcesByTypeId (@RequestParam("resourceTypeId") Integer i) {
        ResourceType type = resourceTypeService.findById(i);
        List<Resource> resources = resourceService.findByType(type);

        List<ResourcesJson> list= new ArrayList<>();
        ResourcesJson resourceJson = new ResourcesJson();

        for (Resource resource : resources) {
            resourceJson.setId(resource.getResourcesId());
            resourceJson.setTypeId(resource.getType().getTypeId());
            resourceJson.setIdentifier(resource.getIdentifier());
            resourceJson.setDescription(resource.getDescription());
            resourceJson.setRegistratorId(resource.getRegistrator().getUserId());
            resourceJson.setDate(resource.getDate());
            resourceJson.setStatus(resource.getStatus());
            resourceJson.setTomeId(resource.getTome().getTomeId());
            resourceJson.setReasonInclusion(resource.getReasonInclusion());

            list.add(resourceJson);
        }
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String json = gson.toJson(list);
        return json;
    }

    @ResponseBody
    @RequestMapping(value = "/countResources", method = RequestMethod.POST)
    public Long countResources () {
        return resourceService.count();
    }

}