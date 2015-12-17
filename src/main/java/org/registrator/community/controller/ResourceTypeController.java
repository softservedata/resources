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
@RequestMapping(value ="/registrator/resourcetypes/")
public class ResourceTypeController {

	@Autowired
	ResourceTypeService resourceTypeService;
	
/*	@ResponseBody
	@RequestMapping(value ="/{typeName}",method = RequestMethod.GET)
	public ResourceType findByName(@PathVariable("typeName") String typeName){
		return resourceTypeService.findByName(typeName);
	}*/
	@Transactional
	@RequestMapping(value = "/show-res-types", method = RequestMethod.GET)
    public String showResourceType(Model model) {
		List<ResourceType> listOfResourceType = resourceTypeService.findAll();
		model.addAttribute("listOfResourceType", listOfResourceType);
       /* return "addRT";*/
        return "allResourcesTypes";
    }
	
	@Transactional
	@ResponseBody
	@RequestMapping(value ="/show-all-type",method = RequestMethod.GET)
	public List<ResourceType> findAll(){
		List<ResourceType> listOfResourceType = resourceTypeService.findAll();
		
		return listOfResourceType;
	}
	// Here is bag - {typeId} Maybe I need to add something to method parameter
	@RequestMapping(value = "/edit-resource-type/{typeId}", method = RequestMethod.GET)
	public String editResourceType() {
		resourceTypeService.editResourceType(3, "ne44wsss");
		return "showResourceType";
	}
/*	@Transactional
	@RequestMapping(value = "/add-resource-type123/", method = RequestMethod.GET)
    public String addResourceType() {
        resourceTypeService.addResourceType(new ResourceType("Ресурс,який нікому не потрібен"));
        return "addRT";
    }*/
	
	@Transactional
	@RequestMapping(value="/delete/{typeName}", method=RequestMethod.DELETE,
	        produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void deleteResourceType(@PathVariable String typeName) {
	    resourceTypeService.delete(resourceTypeService.findByName(typeName));
	}
	
	

/*// this method will be changed 
	@RequestMapping(value = "/delete-resource-type", method = RequestMethod.GET)
		public String deleteResourceType() {
			resourceTypeService.delete(7);
			return "delResType";
		}*/

	
	
	
	
}







