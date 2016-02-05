package org.registrator.community.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.dto.UserDTO;
import org.registrator.community.dto.JSON.PolygonJSON;
import org.registrator.community.dto.JSON.ResourseSearchJson;
import org.registrator.community.entity.DiscreteParameter;
import org.registrator.community.entity.LinearParameter;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.entity.User;
import org.registrator.community.service.ResourceDeleteService;
import org.registrator.community.service.ResourceService;
import org.registrator.community.service.ResourceTypeService;
import org.registrator.community.service.UserService;
import org.registrator.community.service.impl.DiscreteParameterServiceImpl;
import org.registrator.community.service.impl.LinearParameterServiceImpl;
import org.registrator.community.service.impl.ResourceDiscreteValueServiceImpl;
import org.registrator.community.service.impl.ResourceLinearValueServiceImpl;
import org.registrator.community.validator.ResourceDTOValidator;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/registrator/resource")
public class ResourceController {

	@Autowired
	Logger logger;

	@Autowired
	ResourceDTOValidator validator;

	@Autowired
	ResourceService resourceService;
	
	@Autowired
	private ResourceDeleteService resourceDeleteService;

	@Autowired
	ResourceTypeService resourceTypeService;

	@Autowired
	DiscreteParameterServiceImpl discreteParameterService;

	@Autowired
	LinearParameterServiceImpl linearParameterService;

	@Autowired
	ResourceDiscreteValueServiceImpl resourceDiscreteValueService;

	@Autowired
	ResourceLinearValueServiceImpl resourceLinearValueService;

	@Autowired
	UserService userService;

	/**
	 * Method for loading form for input the parameter of resource (with
	 * existing resource types)
	 * 
	 * @param model
	 * @return addResource.jsp
	 */
	@PreAuthorize("hasRole('ROLE_REGISTRATOR')")
	@RequestMapping(value = "/addresource", method = RequestMethod.GET)
	public String addResourceForm(Model model) {

		/*
		 * Authentication auth =
		 * SecurityContextHolder.getContext().getAuthentication(); User
		 * registrator = userService.getUserByLogin(auth.getName()); Set<User>
		 * owners = registrator.getOwners(); model.addAttribute("owners",
		 * owners);
		 */

		/* load list of resource types on UI form */
		List<ResourceType> listOfResourceType = resourceTypeService.findAll();
		logger.info(listOfResourceType.size() + " resource types was found");
		model.addAttribute("listOfResourceType", listOfResourceType);
		ResourceDTO newresource = new ResourceDTO();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        newresource.setIdentifier(resourceService.getRegistrationNumber(auth.getName()));
		model.addAttribute("newresource", newresource);
		return "addResource";
	}

	/**
	 * Method save the resource with all parameters from UI in database
	 * 
	 * @param resourceDTO
	 * @param result
	 * @param model
	 * @return showResource.jsp (addResource.jsp page if resource not valid)
	 */
	//add parameter ownerLogin for adding input inquiry
	@PreAuthorize("hasRole('ROLE_REGISTRATOR')")
	@RequestMapping(value = "/addresource", method = RequestMethod.POST)
	public String addResource(@Valid @ModelAttribute("newresource") ResourceDTO resourceDTO, BindingResult result,
			Model model, String ownerLogin) {
 
		logger.info("The ownerLogin is " + ownerLogin);
		
		/* check if given resourceDTO is valid */
		validator.validate(resourceDTO, result);
		if (result.hasErrors()) {
			logger.info("The resoursrDTO is not valid");
			model.addAttribute("newresource", resourceDTO);
			model.addAttribute("ownerLogin", ownerLogin);
			return "addResource";
		} else {

			/* get the logged registrar by login */
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User registrator = userService.getUserByLogin(auth.getName());
			logger.info("The logged register is" + registrator.getLastName() + " " + registrator.getFirstName());

			/* save resourceDTO on servicelayer with status ACTIVE */
			resourceDTO = resourceService.addNewResource(resourceDTO, ownerLogin, registrator);
			logger.info("Resource was successfully saved");
			model.addAttribute("resource", resourceDTO);
			return "showResource";
		}
	}

	/**
	 * Show the information about resource by identifier
	 * 
	 * @param identifier
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/get/{identifier}", method = RequestMethod.GET)
	public String getResourceByIdentifier(@PathVariable("identifier") String identifier, Model model) {

		ResourceDTO resourceDTO = resourceService.findByIdentifier(identifier);
		model.addAttribute("resource", resourceDTO);
		return "showResource";
	}

	/**
	 * Load the list of all resource parameters of selected resource type
	 * 
	 * @param typeName
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getParameters", method = RequestMethod.POST)
	public String add(@RequestParam("resourceTypeName") String typeName, Model model) {

		/* find the resource type in database by type name */
		ResourceType resType = resourceTypeService.findByName(typeName);

		/* find the resource parameters on resType */
		List<DiscreteParameter> discreteParameters = discreteParameterService.findAllByResourceType(resType);
		List<LinearParameter> linearParameters = linearParameterService.findAllByResourceType(resType);

		model.addAttribute("discreteParameters", discreteParameters);
		model.addAttribute("linearParameters", linearParameters);
		return "resourceValues";
	}

    /**
     * Send values to resource type select at the page Resource search by parameters
     * @param model
     * @return
     */
	@PreAuthorize("hasRole('ROLE_REGISTRATOR') or hasRole('ROLE_USER')")
	@RequestMapping(value = "/showAllResources", method = RequestMethod.GET)
	public String showAllResources(Model model) {
		List<ResourceType> resourceTypes = resourceTypeService.findAll();
		model.addAttribute("resourceTypes", resourceTypes);
		return "showAllResources";
	}

    /**
     * Depending on chosen resource type store all parameters and send them to view
     * at the page Resource search by parameters
     * @param i - Resource type, received from view
     * @param model
     * @return
     */
	@RequestMapping(value = "/getResourcesByTypeId", method = RequestMethod.POST)
	public String showAllResourcesByTypeId(@RequestParam("resourceTypeId") Integer i, Model model) {
		ResourceType type = resourceTypeService.findById(i);

		List<DiscreteParameter> discreteParameters = discreteParameterService.findAllByResourceType(type);
		List<LinearParameter> linearParameters = linearParameterService.findAllByResourceType(type);

		model.addAttribute("discreteParameters", discreteParameters);
		model.addAttribute("linearParameters", linearParameters);
		return "parameters";
	}

    /**
     * Depending on received parameters create List of resourceDTO and send them to view
     * @param json - search parameters in JSON format
     * @param model
     * @return
     */
    @ResponseBody
	@PreAuthorize("hasRole('ROLE_REGISTRATOR') or hasRole('ROLE_USER')")
    @RequestMapping(value = "/resourceSearch", method = RequestMethod.POST)
    public String resourceSearch(@RequestBody ResourseSearchJson json, Model model) {

        Set<String> identifiers = resourceService.getAllByParameters(json);
        List<PolygonJSON> polygons = new ArrayList<>();

		int countPolygons = 0;
        for (String identifier : identifiers) {
            polygons.addAll(resourceService.createPolygonJSON(identifier, countPolygons));
			countPolygons = polygons.size();
        }

        Gson gson = new Gson();
        return gson.toJson(polygons);
    }

	@ResponseBody
	@RequestMapping(value = "/decs", method = RequestMethod.GET)
	public Map<String, Set<String>> getDescriptionProposition(@RequestParam("descTag") String descTag) {
		Map<String, Set<String>> suggestions = new HashMap<String, Set<String>>();
		// suggestions.put("query", new TreeSet<String>().add("unit"));
		suggestions.put("suggestions", resourceService.getDescriptionBySearchTag(descTag));
		return suggestions;
	}

    /**
     * Create the Set of resources identifiers depending on received parameters
     * and generate JSON response
     * @param minLat - minimum latitude
     * @param maxLat - maximum latitude
     * @param minLng - minimum longitude
     * @param maxLng - maximum longitude
     * @param resType - resource type id
     * @param model
     * @return JSON with information about polygons which can be located
     * between received coordinates
     */
	@ResponseBody
	@RequestMapping(value = "/getResourcesByAreaLimits", method = RequestMethod.POST)
	public String showAllResourcesByAreaLimits(@RequestParam("minLat") Double minLat,
			@RequestParam("maxLat") Double maxLat, @RequestParam("minLng") Double minLng,
			@RequestParam("maxLng") Double maxLng, @RequestParam("resType") String resType, Model model) {
        Set<String> identifiers = resourceService.getAllByAreaLimits(minLat, maxLat, minLng, maxLng, resType);
		List<PolygonJSON> polygons = new ArrayList<>();

		int countPolygons = 0;
		for (String identifier : identifiers) {
			polygons.addAll(resourceService.createPolygonJSON(identifier, countPolygons));
			countPolygons = polygons.size();
		}

		Gson gson = new Gson();
		return gson.toJson(polygons);
	}

    /**
     * Search on map by point
     * Create set of resource identifiers depending on received point coordinates
     * @param lat - point latitude
     * @param lng - point longitude
     * @param model
     * @return JSON with information about polygons where received point can be located
     */
	@ResponseBody
	@RequestMapping(value = "/getResourcesByPoint", method = RequestMethod.POST)
	public String showAllResourcesByAreaLimits(@RequestParam("lat") Double lat, @RequestParam("lng") Double lng,
			Model model) {
		Set<String> identifiers = resourceService.getAllByPoint(lat, lng);
		List<PolygonJSON> polygons = new ArrayList<>();

		int countPolygons = 0;
		for (String identifier : identifiers) {
			polygons.addAll(resourceService.createPolygonJSON(identifier, countPolygons));
			countPolygons = polygons.size();
		}

		Gson gson = new Gson();
		return gson.toJson(polygons);
	}

    /**
     * View for the Search on map page
     * @param model
     * @return
     */
	@PreAuthorize("hasRole('ROLE_REGISTRATOR') or hasRole('ROLE_USER')")
	@RequestMapping(value = "/searchOnMap", method = RequestMethod.GET)
	public String searchOnMap(Model model) {
        List<ResourceType> resourceTypes = resourceTypeService.findAll();
        model.addAttribute("resourceTypes", resourceTypes);
        return "searchOnMap";
	}

	@ResponseBody
	@RequestMapping(value = "/owners", method = RequestMethod.POST)
	public List<UserDTO> getOwnersSuggestions(@RequestParam("ownerDesc") String ownerDesc) {
		List<UserDTO> userList = userService.getUserBySearchTag(ownerDesc);
		return userList;
	}

	/**
	 * Find the selected owner by login
	 * 
	 * @param ownerLogin
	 * @return owner
	 */
	@ResponseBody
	@RequestMapping(value = "/getOwnerInfo", method = RequestMethod.GET)
	public UserDTO getOwnerInfo(@RequestParam("ownerLogin") String ownerLogin) {
		return userService.getUserDto(ownerLogin);
	}

	@ResponseBody
	@RequestMapping(value = "/showAllResources", method = RequestMethod.POST)
	public String showAllResources(@RequestParam("resType") Integer resType) {
		ResourceType resourceType = resourceTypeService.findById(resType);
		List<Resource> resources = resourceService.findByType(resourceType);
		List<PolygonJSON> polygons = new ArrayList<>();

		int countPolygons = 0;
		for (Resource resource: resources) {
			polygons.addAll(resourceService.createPolygonJSON(resource.getIdentifier(), countPolygons));
			countPolygons = polygons.size();
		}

		Gson gson = new Gson();
		return gson.toJson(polygons);
	}
	
	@PreAuthorize("hasRole('ROLE_REGISTRATOR')")
	@RequestMapping(value = "/delete/{resourceIdentifier}")
	public String deleteResource(@PathVariable String resourceIdentifier) {
		logger.info("begin deleteResource, param resourceIdentifier = " + resourceIdentifier);
		resourceDeleteService.deleteResource(resourceIdentifier);
		logger.info("end deleteResource");
		return "redirect:/registrator/resource/searchOnMap";
	}
	
}