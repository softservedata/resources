package org.registrator.community.controller;

import java.util.*;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.registrator.community.dao.TomeRepository;
import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.dto.ResourceDiscreteValueDTO;
import org.registrator.community.dto.ResourceLinearValueDTO;
import org.registrator.community.entity.*;
import org.registrator.community.service.ResourceService;
import org.registrator.community.service.ResourceTypeService;
import org.registrator.community.service.impl.DiscreteParameterServiceImpl;
import org.registrator.community.service.impl.LinearParameterServiceImpl;
import org.registrator.community.service.impl.ResourceDiscreteValueServiceImpl;
import org.registrator.community.service.impl.ResourceLinearValueServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    DiscreteParameterServiceImpl discreteParameterService;

    @Autowired
    LinearParameterServiceImpl linearParameterService;

    @Autowired
    ResourceLinearValueServiceImpl resourceLinearValueService;

    @Autowired
    ResourceDiscreteValueServiceImpl resourceDiscreteValueService;

    /**
     * Method for loading form for input the parameter of resource (with
     * existing resource types and registrator)
     */
/*	@RequestMapping(value = "/addresource", method = RequestMethod.GET)
    public String addResourceForm(Model model) {
		List<ResourceType> listOfResourceType = resourceTypeService.findAll();
		model.addAttribute("listOfResourceType", listOfResourceType);
		List<Tome> tomes = tomeRepository.findAll();
		model.addAttribute("tomes", tomes);
		ResourceDTO newresource = new ResourceDTO();
		model.addAttribute("newresource", newresource);
		return "addResource";
	}*/
    @RequestMapping(value = "/addresource", method = RequestMethod.GET)
    public String addResourceForm(Model model) {
        List<ResourceType> listOfResourceType = resourceTypeService.findAll();
        model.addAttribute("listOfResourceType", listOfResourceType);
        return "allTypes";
    }

    @RequestMapping(value = "/add/{typeName}", method = RequestMethod.GET)
    public String add(@PathVariable String typeName, Model model) {
        ResourceType resType = resourceTypeService.findByName(typeName);
        model.addAttribute("resType", resType);
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
    // TODO remove the RequestParameter inputDate
    @RequestMapping(value = "/add/addresource", method = RequestMethod.POST)
    public String addResource(@ModelAttribute("newresource") ResourceDTO resourceDTO,
                              @RequestParam("inputDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, Model model) {
        resourceDTO.setDate(date);
        resourceService.addNewResource(resourceDTO);
        model.addAttribute("resource", resourceDTO);
        return "showResource";
    }

    /**
     * Show the information about resource by identifier
     */
    @RequestMapping(value = "/get/{identifier}", method = RequestMethod.GET)
    public String getResourceByIdentifier(@PathVariable("identifier") String identifier, Model model) {
        System.out.println("here");
        ResourceDTO resourceDTO = resourceService.getResourceByIdentifier(identifier);
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
//        List<Resource> resources = resourceService.findByType(type);

//        List<ResourcesJson> list = new ArrayList<>();

//        for (Resource resource : resources) {
//            ResourcesJson resourceJson = new ResourcesJson();
//
//            resourceJson.setId(resource.getResourcesId());
//            resourceJson.setTypeId(resource.getType().getTypeId());
//            resourceJson.setIdentifier(resource.getIdentifier());
//            resourceJson.setDescription(resource.getDescription());
//            resourceJson.setRegistratorId(resource.getRegistrator().getUserId());
//            resourceJson.setDate(resource.getDate());
//            resourceJson.setStatus(resource.getStatus());
//            resourceJson.setTomeId(resource.getTome().getTomeId());
//            resourceJson.setReasonInclusion(resource.getReasonInclusion());
//
//            list.add(resourceJson);
//        }
        List<DiscreteParameter> discreteParameters = discreteParameterService.findAllByResourceType(type);
        List<LinearParameter> linearParameters = linearParameterService.findAllByResourceType(type);
//        List<DiscreteParametersJSON> discreteParametersJSONs = new ArrayList<>();
//
//        for (DiscreteParameter discreteParameter : discreteParameters) {
//            DiscreteParametersJSON discreteParametersJSON = new DiscreteParametersJSON();
//            discreteParametersJSON.setDiscreteParameterId(discreteParameter.getDiscreteParameterId());
//            discreteParametersJSON.setDescription(discreteParameter.getDescription());
//            discreteParametersJSON.setUnitName(discreteParameter.getUnitName());
//
//            discreteParametersJSONs.add(discreteParametersJSON);
//        }
//
//        Gson gson = new GsonBuilder()
//                .setPrettyPrinting()
//                .create();
//        String json = gson.toJson(discreteParametersJSONs);
//        return json;
        model.addAttribute("discreteParameters", discreteParameters);
        model.addAttribute("linearParameters", linearParameters);
        return "parameters";
    }

    //    @ResponseBody
    @RequestMapping(value = "/resourceSearch", method = RequestMethod.POST)
    public String resourceSearch(
            @RequestParam("discreteParametersId") List<Integer> discreteParamsIds,
            @RequestParam("discreteParametersCompare") List<String> discreteParamsCompares,
            @RequestParam("discreteParametersValue") List<Double> discreteParamsValues,
            @RequestParam("linearParametersId") List<Integer> linearParamsIds,
            @RequestParam("linearParametersValue") List<Double> linearParamsValues,
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

        List<Resource> resourceList = new ArrayList<>();
        List<Resource> resultResourceList = new ArrayList<>();

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

        if(countValues > 1) {
            for (int i = 0; i < resourceList.size() - 1; i++) {
                int k = 0;
                for (int j = i + 1; j < resourceList.size(); j++) {
                    if (resourceList.get(i).getResourcesId() == resourceList.get(j).getResourcesId()) {
                        k++;
                    }
                    if (k == (countValues-1)) {
                        resultResourceList.add(resourceList.get(i));
                    }
                }
            }
        }
        else {
            resultResourceList.addAll(resourceList);
        }

        /*
        Creating ReaourceDTO
         */


        List<ResourceDTO> resourceDTOs = new ArrayList<>();

        for (Resource resource : resultResourceList) {
//            List<ResourceLinearValue> linearValues = resourceLinearValueService.findByResource(resource);
//            List<ResourceDiscreteValue> discreteValues = resourceDiscreteValueService.findByResource(resource);
//            List<ResourceLinearValueDTO> resourceLinear = new ArrayList<>();
//            List<ResourceDiscreteValueDTO> resourceDiscrete = new ArrayList<>();
//
//            for (ResourceLinearValue linearValue : linearValues) {
//                ResourceLinearValueDTO linearValueDTO = new ResourceLinearValueDTO();
//                linearValueDTO.setLinearParameterDescription(linearValue.getLinearParameter().getDescription());
//                linearValueDTO.setLinearParameterUnit(linearValue.getLinearParameter().getUnitName());
//
//            }
            ResourceDTO resourceDTO = resourceService.getResourceByIdentifier(resource.getIdentifier());
            resourceDTOs.add(resourceDTO);
        }

        model.addAttribute("Resources", resourceDTOs);


//        Gson gson = new GsonBuilder()
//                .setPrettyPrinting()
//                .create();
//        String json = gson.toJson();

//        System.out.println();
//        System.out.println("---------------------------");
//        System.out.println("Count: " + countValues);
//        System.out.println();
//        for (Resource resource : resourceList) {
//            System.out.print(" id: " + resource.getResourcesId());
//        }
//        System.out.println("---------------------------");
//        System.out.println();
//        return "Post Received! DiscreteID:" + discreteParamsIds + "\n"
//                + "\n Compare: " + discreteParamsCompares
//                + "\n Value: " + discreteParamsValues
//                + "\n Linear parameters:"
//                + "\n Id: " + linearParamsIds
//                + "\n Value: " + linearParamsValues;
        return "resourceSearch";
    }

    @ResponseBody
    @RequestMapping(value = "/countResources", method = RequestMethod.POST)
    public Long countResources() {
        return resourceService.count();
    }

}