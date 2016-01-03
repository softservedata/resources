package org.registrator.community.controller.administrator;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.registrator.community.dao.ResourceRepository;
import org.registrator.community.dao.TomeRepository;
import org.registrator.community.dto.InquiryDTO;
import org.registrator.community.dto.InquiryListDTO;
import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.dto.TomeDTO;
import org.registrator.community.entity.DiscreteParameter;
import org.registrator.community.entity.LinearParameter;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.entity.Tome;
import org.registrator.community.service.DiscreteParameterService;
import org.registrator.community.service.InquiryService;
import org.registrator.community.service.LinearParameterService;
import org.registrator.community.service.ResourceService;
import org.registrator.community.service.ResourceTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.FlashMap;


@Controller
@RequestMapping(value ="/inquiry/add/")
public class InquiryController {
	public static final Logger logger = LoggerFactory.getLogger(InquiryController.class);
	
	@Autowired
	InquiryService inquiryService;
		
	@Autowired
	ResourceRepository resourceRepository;
	
	@Autowired
	ResourceTypeService resourceTypeService;
	
	@Autowired
	ResourceService resourceService;
	
	@Autowired
	DiscreteParameterService discreteParameterService;
	
	@Autowired
	LinearParameterService linearParameterService;
	
	 // to delete
    @Autowired
    TomeRepository tomeRepository;
	
	/**
	 * Method for showing form on UI to input the parameters 
	 * for inquiry to get the certificate aboute the resource 
	 * (with existing registrators and resources).
	 */	
	@RequestMapping(value = "/outputInquiry", method = RequestMethod.GET)
	public String showOutputInquiry(Model model) {
		logger.info("begin showOutputInquiry");
		List<TomeDTO>  listTomeDTO = inquiryService.listTomeDTO();
		model.addAttribute("tomes", listTomeDTO);
		Iterable<Resource> resources = resourceRepository.findAll();
		model.addAttribute("resources", resources);
		logger.info("end showOutputInquiry");
		return "inquiryAddOut";
	}
	
	/**
	 * Method saves the data in the table inquiry_list.
	 */
	@RequestMapping(value = "/addOutputInquiry", method = RequestMethod.POST)
	public String addOutputInquiry(InquiryDTO inquiryDTO, HttpSession session) {  			
		logger.info("begin addOutputInquiry");
		String userLogin =(String) session.getAttribute("userLogin");	
		logger.info("userLogin = " + userLogin);
		inquiryService.addOutputInquiry(inquiryDTO, userLogin);
		logger.info("end addOutputInquiry");
		return  "redirect:/inquiry/add/listInqUserOut";	
	}
	
	/**
	 * Method for showing all inquiries from logged user on UI.
	 */
	@RequestMapping(value = "/listInqUserOut", method = RequestMethod.GET)
	public String listInqUserOut(Model model, HttpSession session) {
		logger.info("begin listInqUserOut");
		String userLogin =(String) session.getAttribute("userLogin");
		List<InquiryListDTO> listInquiryUserOut = inquiryService.listInquiryUserOut(userLogin);
		model.addAttribute("listInquiryUserOut", listInquiryUserOut);
		logger.info("end listInqUserOut");
		return "listInqUserOut";
	}
	
	/**
	 * Method for deleting chosen inquiry by Id.
	 */
	@RequestMapping(value = "/delete/{inquiryId}")
	public String deleteInquiry(@PathVariable Integer inquiryId) {
		inquiryService.removeInquiry(inquiryId);
		return "redirect:/inquiry/add/listInqUserOut";
	}
	
	
	
	
	/**
     * Method for loading form for input the parameter of resource (with
     * existing resource types and registrator)
     * !copy from ResourceController
     */
		@RequestMapping(value = "/addresource", method = RequestMethod.GET)
	public String addResourceForm(Model model) {
		List<ResourceType> listOfResourceType = resourceTypeService.findAll();
		List<Tome> tomes = tomeRepository.findAll();
		ResourceDTO newresource = new ResourceDTO();
		model.addAttribute("listOfResourceType", listOfResourceType);
		model.addAttribute("tomes", tomes);
		model.addAttribute("newresource", newresource);
		return "addResource";
	}
	
		/** 
	     * !copy from ResourceController
	     */
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
     * similar to ResourceController
     */
    @RequestMapping(value = "/addresource", method = RequestMethod.POST)
    public String addResource(@Valid @ModelAttribute("newresource") ResourceDTO resourceDTO,
                              BindingResult result, Model model, HttpSession session) {
    	String userLogin =(String) session.getAttribute("userLogin");
    	resourceDTO = inquiryService.addInputInquiry(resourceDTO, userLogin);
         model.addAttribute("resource", resourceDTO);
         return "showResource";
    }
	
    /**
     * Show the information about resource by identifier
     * !copy from ResourceController
     */
    @RequestMapping(value = "/get/{identifier}", method = RequestMethod.GET)
    public String getResourceByIdentifier(@PathVariable("identifier") String identifier, Model model) {
        ResourceDTO resourceDTO = resourceService.findByIdentifier(identifier);
        model.addAttribute("resource", resourceDTO);
        return "showResource";
    }
    
    
    
    
	
	/* proba old
	@RequestMapping(value = "/addresource", method = RequestMethod.GET)
	public String addResourceForm(Model model, @RequestParam("selectedTomeIdentifier") String selectedTomeIdentifier) {				
		model.addAttribute("TomeIdentifier", selectedTomeIdentifier);
		List<ResourceType> listOfResourceType = resourceTypeService.findAll();
		List<Tome> tomes = tomeRepository.findAll();
		ResourceDTO newresource = new ResourceDTO();
		model.addAttribute("listOfResourceType", listOfResourceType);
		model.addAttribute("tomes", tomes);
		model.addAttribute("newresource", newresource);
		//
		if (model.containsAttribute("TomeIdentifier")) {
		logger.info("contains");}
		else logger.info("don't");
		return "addResource";
		
		
		//logger.info("tomeIdentifier = " + selectedTomeIdentifier);
		//return "redirect:/registrator/resource/addResource";
	}
	*/
		
	/**
	 * Method saves the data in the table inquiry_list.
	 */
	/*@RequestMapping(value = "/addInputInquiry", method = RequestMethod.POST)
	public String addInputInquiry(Model model, HttpSession session) {  			
		logger.info("begin addInputInquiry");
		String userLogin =(String) session.getAttribute("userLogin");	
		logger.info("userLogin = " + userLogin);
		
		//inquiryService.addOutputInquiry(inquiryDTO, userLogin);
		logger.info("end addInputInquiry");
		return  "redirect:/registrator/resource/addResource";	
	}*/
	
	/**
	 * Method for showing form on UI to input the parameters 
	 * for inquiry to input the resource 
	 * (with existing registrators and resources).
	 */	
	/*@RequestMapping(value = "/inputInquiry", method = RequestMethod.GET)
	public String showInputInquiry(Model model) {
		logger.info("begin showInputInquiry");
		List<TomeDTO>  listTomeDTO = inquiryService.listTomeDTO();
		model.addAttribute("tomes", listTomeDTO);
		//model.addAttribute("selectedTomeIdentifier","");
		//List<ResourceType> listOfResourceType = resourceTypeService.findAll();
		//model.addAttribute("listOfResourceType", listOfResourceType);
		logger.info("end showInputInquiry");
		return "inquiryAddInput";
	}
	*/
	
	/*
	@ResponseBody
	@RequestMapping(value ="/output/{inquiryListDTO}",method = RequestMethod.GET)
	public Inquiry testAddOutputInquiry(@PathVariable("inquiryListDTO") String resourceIdentifier){
		 return inquiryService.testAddOutputInquiry(resourceIdentifier);
	}
	
	@RequestMapping(value="/page/",method=RequestMethod.GET)
	public String getOutputInquiryPage(){
		return "inquiryAddOutput";
	}*/
	
	
}
