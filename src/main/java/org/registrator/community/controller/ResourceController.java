package org.registrator.community.controller;

import java.util.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.google.gson.Gson;
import org.registrator.community.dao.AreaRepository;
import org.registrator.community.dao.ResourceRepository;
import org.registrator.community.dao.TomeRepository;
import org.registrator.community.dto.JSON.PointJSON;
import org.registrator.community.dto.JSON.PolygonJSON;
import org.registrator.community.dto.PointAreaDTO;
import org.registrator.community.dto.PoligonAreaDTO;
import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.dto.UserDTO;
import org.registrator.community.entity.*;
import org.registrator.community.enumeration.ResourceStatus;
import org.registrator.community.dto.validator.ResourceDTOValidator;
import org.registrator.community.service.ResourceService;
import org.registrator.community.service.ResourceTypeService;
import org.registrator.community.service.UserService;
import org.registrator.community.service.impl.DiscreteParameterServiceImpl;
import org.registrator.community.service.impl.LinearParameterServiceImpl;
import org.registrator.community.service.impl.ResourceDiscreteValueServiceImpl;
import org.registrator.community.service.impl.ResourceLinearValueServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/registrator/resource")
public class ResourceController {

    @Autowired
    ResourceDTOValidator validator;

    @Autowired
    ResourceService resourceService;

    @Autowired
    ResourceTypeService resourceTypeService;

    // to delete
    @Autowired
    TomeRepository tomeRepository;

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
     * existing resource types and registrator)
     */
    @RequestMapping(value = "/addresource", method = RequestMethod.GET)
    public String addResourceForm(Model model, HttpSession session) {
    	UserDTO user = userService.getUserDto("oleks");
    	session.setAttribute("user", user);
        List<ResourceType> listOfResourceType = resourceTypeService.findAll();
        List<Tome> tomes = tomeRepository.findAll();
        ResourceDTO newresource = new ResourceDTO();
        model.addAttribute("listOfResourceType", listOfResourceType);
        model.addAttribute("tomes", tomes);
        model.addAttribute("newresource", newresource);
        return "addResource";
    }
    
    
    
    @RequestMapping(value = "/getParameters", method = RequestMethod.POST)
    public String add(@RequestParam("resourceTypeName") String typeName, Model model) {
        ResourceType resType = resourceTypeService.findByName(typeName);
        
        List<DiscreteParameter> discreteParameters = discreteParameterService.findAllByResourceType(resType);
        List<LinearParameter> linearParameters = linearParameterService.findAllByResourceType(resType);

        model.addAttribute("discreteParameters", discreteParameters);
        model.addAttribute("linearParameters", linearParameters);
        return "resourceValues";
    }
    

    /**
     * Method save the resource in table list_of resources
     */
    @RequestMapping(value = "/addresource", method = RequestMethod.POST)
    public String addResource(@Valid @ModelAttribute("newresource") ResourceDTO resourceDTO,
                              BindingResult result, Model model) {

        validator.validate(resourceDTO, result);
        if (result.hasErrors()) {
            model.addAttribute("newresource", resourceDTO);
            return "addResource";
        } else {
            resourceService.addNewResource(resourceDTO, ResourceStatus.ACTIVE);
            model.addAttribute("resource", resourceDTO);
            return "showResource";
        }
    }

    /**
     * Show the information about resource by identifier
     */
    @RequestMapping(value = "/get/{identifier}", method = RequestMethod.GET)
    public String getResourceByIdentifier(@PathVariable("identifier") String identifier, Model model) {
        ResourceDTO resourceDTO = resourceService.findByIdentifier(identifier);
        model.addAttribute("resource", resourceDTO);
        return "showResource";
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
//    	suggestions.put("query", new TreeSet<String>().add("unit"));
    	suggestions.put("suggestions", resourceService.getDescriptionBySearchTag(descTag));
        return suggestions;
    }

    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private ResourceRepository resourceRepository;

    @ResponseBody
    @RequestMapping(value = "/getResourcesByAreaLimits", method = RequestMethod.POST)
    public String showAllResourcesByAreaLimits(@RequestParam("minLat") Double minLat,
                                           @RequestParam("maxLat") Double maxLat,
                                           @RequestParam("minLng") Double minLng,
                                           @RequestParam("maxLng") Double maxLng,
                                           Model model) {
        List<ResourceDTO> resourceDTOs = resourceService.getAllByAreaLimits(minLat, maxLat, minLng, maxLng);

        List<PolygonJSON> polygons = new ArrayList<>();
        for (ResourceDTO resourceDTO : resourceDTOs) {
            PolygonJSON polygon = new PolygonJSON();
            List<Area> areas = areaRepository.findByResource(resourceRepository.findByIdentifier(resourceDTO.getIdentifier()));
            List<PointJSON> points = new ArrayList<>();

            for (Area area : areas) {
                PointJSON point = new PointJSON();
                point.setLatitude(area.getLatitude());
                point.setLongitude(area.getLongitude());
                point.setPoint_order(area.getOrderNumber());
                points.add(point);
            }

            polygon.setResourceDescription(resourceDTO.getDescription());
            polygon.setPoints(points);

            polygons.add(polygon);
        }
        Gson gson = new Gson();
        return gson.toJson(polygons);
    }
}