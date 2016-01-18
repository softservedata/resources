package org.registrator.community.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.dto.UserDTO;
import org.registrator.community.dto.JSON.PolygonJSON;
import org.registrator.community.entity.DiscreteParameter;
import org.registrator.community.entity.LinearParameter;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceDiscreteValue;
import org.registrator.community.entity.ResourceLinearValue;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.ResourceStatus;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.context.Theme;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
     * @param model
     * @return addResource.jsp
     */
    @RequestMapping(value = "/addresource", method = RequestMethod.GET)
    public String addResourceForm(Model model) {

        /* 
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User registrator = userService.getUserByLogin(auth.getName());
        Set<User> owners = registrator.getOwners();
        model.addAttribute("owners", owners);*/
        
        /*load list of resource types on UI form*/
        List<ResourceType> listOfResourceType = resourceTypeService.findAll();
        logger.info(listOfResourceType.size() + " resource types was found");
        model.addAttribute("listOfResourceType", listOfResourceType);
        ResourceDTO newresource = new ResourceDTO();
        model.addAttribute("newresource", newresource);
        return "addResource";
    }
    
   
    /**
     * Method save the resource with all parameters from UI in database
     * @param resourceDTO
     * @param result
     * @param model
     * @return showResource.jsp (addResource.jsp page if resource not valid)
     */
    @RequestMapping(value = "/addresource", method = RequestMethod.POST)
    public String addResource(@Valid @ModelAttribute("newresource") ResourceDTO resourceDTO,
                              BindingResult result, Model model) {

        /* check if given resourceDTO is valid*/
        validator.validate(resourceDTO, result);
        if (result.hasErrors()) { 
            logger.info("The resoursrDTO is not valid");
            model.addAttribute("newresource", resourceDTO);
            return "addResource";
        } else {
            
            /*get the logged registrar by login */
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User registrator = userService.getUserByLogin(auth.getName());
            logger.info("The logged register is" + registrator.getLastName() + " " + registrator.getFirstName());
            
            /*save resourceDTO on servicelayer with status ACTIVE */
            resourceDTO = resourceService.addNewResource(resourceDTO, ResourceStatus.ACTIVE, registrator);
            logger.info("Resource was successfully saved");
            model.addAttribute("resource", resourceDTO);
            return "showResource";
        }
    }

   
    /**
     * Show the information about resource by identifier
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
     * @param typeName
     * @param model
     * @return
     */   
    @RequestMapping(value = "/getParameters", method = RequestMethod.POST)
    public String add(@RequestParam("resourceTypeName") String typeName, Model model) {
        
        /*find the resource type in database by type name*/
        ResourceType resType = resourceTypeService.findByName(typeName);
        
        /*find the resource parameters on resType*/
        List<DiscreteParameter> discreteParameters = discreteParameterService.findAllByResourceType(resType);
        List<LinearParameter> linearParameters = linearParameterService.findAllByResourceType(resType);

        model.addAttribute("discreteParameters", discreteParameters);
        model.addAttribute("linearParameters", linearParameters);
        return "resourceValues";
    }

    @RequestMapping(value = "/showAllResources", method = RequestMethod.GET)
    public String showAllResources(Model model) {
        List<ResourceType> resourceTypes = resourceTypeService.findAll();
        model.addAttribute("resourceTypes", resourceTypes);
        return "showAllResources";
    }

    //    @ResponseBody
    @RequestMapping(value = "/getResourcesByTypeId", method = RequestMethod.POST)
    public String showAllResourcesByTypeId(@RequestParam("resourceTypeId") Integer i, Model model) {
        ResourceType type = resourceTypeService.findById(i);

        List<DiscreteParameter> discreteParameters = discreteParameterService.findAllByResourceType(type);
        List<LinearParameter> linearParameters = linearParameterService.findAllByResourceType(type);

        model.addAttribute("discreteParameters", discreteParameters);
        model.addAttribute("linearParameters", linearParameters);
        return "parameters";
    }


    @RequestMapping(value = "/resourceSearch", method = RequestMethod.POST)
    public String resourceSearch(
            @RequestParam("discreteParametersId") List<Integer> discreteParamsIds,
            @RequestParam("discreteParametersCompare") List<String> discreteParamsCompares,
            @RequestParam("discreteParametersValue") List<Double> discreteParamsValues,
            @RequestParam("linearParametersId") List<Integer> linearParamsIds,
            @RequestParam("linearParametersValue") List<Double> linearParamsValues,
            @RequestParam("resourceTypeId") Integer resourceTypeId,
            Model model) {

        /*
        To send the array from JavaScript we have to put at least 1 element in the array.
        The next code delete this first element.
         */
        discreteParamsIds.remove(0);
        discreteParamsCompares.remove(0);
        discreteParamsValues.remove(0);
        linearParamsIds.remove(0);
        linearParamsValues.remove(0);

        int countValues = 0;
        for (Double discreteParamsValue : discreteParamsValues) {
            if (discreteParamsValue != null) {
                countValues++;
            }
        }
        for (Double linearParamsValue : linearParamsValues) {
            if (linearParamsValue != null) {
                countValues++;
            }
        }

        List<Resource> resultResourceList = new ArrayList<>();

        if (countValues > 0) {
            List<Resource> resourceList = new ArrayList<>();

            List<DiscreteParameter> discreteParameters = new ArrayList<>();
            for (Integer discreteParamsId : discreteParamsIds) {
                discreteParameters.add(discreteParameterService.findById(discreteParamsId));
            }

            List<LinearParameter> linearParameters = new ArrayList<>();
            for (Integer linearParamsId : linearParamsIds) {
                linearParameters.add(linearParameterService.findById(linearParamsId));
            }

            List<ResourceDiscreteValue> resourceDiscreteValues = new ArrayList<>();
            for (int i = 0; i < discreteParamsValues.size(); i++) {
                if ("less".equals(discreteParamsCompares.get(i))) {
                    resourceDiscreteValues.addAll(resourceDiscreteValueService.findAllBySmallerValueAndDiscreteParameter(
                            discreteParamsValues.get(i), discreteParameters.get(i)
                    ));
                } else if ("greater".equals(discreteParamsCompares.get(i))) {
                    resourceDiscreteValues.addAll(resourceDiscreteValueService.findAllByBiggerValueAndDiscreteParameter(
                            discreteParamsValues.get(i), discreteParameters.get(i)
                    ));
                } else {
                    resourceDiscreteValues.addAll(resourceDiscreteValueService.findAllByValueAndDiscreteParameter(
                            discreteParamsValues.get(i), discreteParameters.get(i)
                    ));
                }
            }

            List<ResourceLinearValue> resourceLinearValues = new ArrayList<>();
            for (int i = 0; i < linearParamsValues.size(); i++) {
                resourceLinearValues.addAll(resourceLinearValueService.findAllByValueAndLinearParameter(linearParamsValues.get(i), linearParameters.get(i)));
            }

            for (ResourceDiscreteValue resourceDiscreteValue : resourceDiscreteValues) {
                resourceList.add(resourceDiscreteValue.getResource());
            }

            for (ResourceLinearValue resourceLinearValue : resourceLinearValues) {
                resourceList.add(resourceLinearValue.getResource());
            }

        /*
        Search which Resources are the same for all search parameters
         */
            if (countValues > 1) {
                for (int i = 0; i < resourceList.size() - 1; i++) {
                    int k = 0;
                    for (int j = i + 1; j < resourceList.size(); j++) {
                        if (resourceList.get(i).getResourcesId() == resourceList.get(j).getResourcesId()) {
                            k++;
                        }
                        if (k == (countValues - 1)) {
                            resultResourceList.add(resourceList.get(i));
                        }
                    }
                }
            } else {
                resultResourceList.addAll(resourceList);
            }

        /*
        Remove duplicates of Resources
         */

            for (int i = 0; i < resultResourceList.size() - 1; i++) {
                for (int j = i + 1; j < resultResourceList.size(); j++) {
                    if (resultResourceList.get(i).getResourcesId() == resultResourceList.get(j).getResourcesId()) {
                        resultResourceList.remove(j);
                        j--;

                    }
                }
            }
        }
        else {
            ResourceType resourceType = resourceTypeService.findById(resourceTypeId);
            resultResourceList = resourceService.findByType(resourceType);
        }
        /*
        Creating List of ResourceDTO
         */
        List<ResourceDTO> resourceDTOs = new ArrayList<>();

        for (Resource resource : resultResourceList) {
            ResourceDTO resourceDTO = resourceService.findByIdentifier(resource.getIdentifier());
            resourceDTOs.add(resourceDTO);
        }

        model.addAttribute("Resources", resourceDTOs);

        return "resourceSearch";
    }

    @ResponseBody
    @RequestMapping(value = "/countResources", method = RequestMethod.POST)
    public Long countResources() {
        return resourceService.count();
    }

    @ResponseBody
    @RequestMapping(value = "/decs", method = RequestMethod.GET)
    public Map<String,Set<String>> getDescriptionProposition(@RequestParam("descTag")String descTag) {
        Map<String,Set<String>> suggestions=new HashMap<String, Set<String>>();
//        suggestions.put("query", new TreeSet<String>().add("unit"));
        suggestions.put("suggestions", resourceService.getDescriptionBySearchTag(descTag));
        return suggestions;
    }

    @ResponseBody
    @RequestMapping(value = "/getResourcesByAreaLimits", method = RequestMethod.POST)
    public String showAllResourcesByAreaLimits(@RequestParam("minLat") Double minLat,
                                           @RequestParam("maxLat") Double maxLat,
                                           @RequestParam("minLng") Double minLng,
                                           @RequestParam("maxLng") Double maxLng,
                                           @RequestParam("resType") String resType,
                                           Model model) {
        Set<String> identifiers = resourceService.getAllByAreaLimits(minLat, maxLat, minLng, maxLng, resType);
        List<PolygonJSON> polygons = new ArrayList<>();

        for (String identifier : identifiers) {
            polygons.addAll(resourceService.createPolygonJSON(identifier));
        }

        Gson gson = new Gson();
        return gson.toJson(polygons);
    }

    @ResponseBody
    @RequestMapping(value = "/getResourcesByPoint", method = RequestMethod.POST)
    public String showAllResourcesByAreaLimits(@RequestParam("lat") Double lat,
                                               @RequestParam("lng") Double lng,
                                               Model model) {
        Set<String> identifiers = resourceService.getAllByPoint(lat, lng);
        List<PolygonJSON> polygons = new ArrayList<>();

        for (String identifier : identifiers) {
            polygons.addAll(resourceService.createPolygonJSON(identifier));
        }

        Gson gson = new Gson();
        return gson.toJson(polygons);
    }

    @RequestMapping(value = "/searchOnMap", method = RequestMethod.GET)
    public String searchOnMap(Model model) {
        return "searchOnMap";
    }
    

    @ResponseBody
    @RequestMapping(value = "/owners", method = RequestMethod.GET)
    public List<UserDTO> getOwnersSuggestions(@RequestParam("ownerDesc")String ownerDesc) {
        List<UserDTO> userList = userService.getUserBySearchTag(ownerDesc);
        return userList;
    }
    
    /**
     * Find the selected owner by login
     * @param ownerLogin
     * @return owner
     */
    @ResponseBody
    @RequestMapping(value = "/getOwnerInfo", method = RequestMethod.GET)
    public UserDTO getOwnerInfo(@RequestParam("ownerLogin")String ownerLogin) {
        return userService.getUserDto(ownerLogin);
    }
    


}