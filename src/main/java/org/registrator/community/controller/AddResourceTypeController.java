package org.registrator.community.controller;

import javax.validation.Valid;

import org.registrator.community.dto.ResourceTypeDTO;
import org.registrator.community.service.ResourceTypeService;
import org.registrator.community.validator.ResTypeDTOValidator;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/registrator/resourcetypes/")
public class AddResourceTypeController {

    @Autowired
    Logger logger;  
    @Autowired
    ResTypeDTOValidator validator;
    @Autowired
    ResourceTypeService resourceTypeService;

    /**
     * Method for loading form for input the parameters and resource type name
     */
    @RequestMapping(value = "/addrestype", method = RequestMethod.GET)
    public String addResourceTypeForm(Model model) {
        logger.info("begin");
        model.addAttribute("newrestype", new ResourceTypeDTO());
        logger.info("end");
        return "addResType";
    }

    /**
     * Method save the resource type in resource_types. Also there is validation for checking whether 
     * inputing resource type already exist in database or not.
     */
    @RequestMapping(value = "/addrestype", method = RequestMethod.POST)
    public String addResourceType(@Valid @ModelAttribute("newrestype") ResourceTypeDTO resourceTypeDTO,
            BindingResult result, Model model) {
        logger.info("begin");
        validator.validate(resourceTypeDTO, result);
        if (result.hasErrors()) {
            logger.info("end method: bad credentials, return to page for adding all resource types");
            return "addResType";
            
        } else {
            resourceTypeService.addResourceTypeDTO(resourceTypeDTO);
            model.addAttribute("resourceType", resourceTypeDTO);
            logger.info("end method: return to page for showing all resource types");
            return "redirect:/registrator/resourcetypes/show-res-types";
        }
        
    }
}
