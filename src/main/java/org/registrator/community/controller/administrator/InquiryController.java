package org.registrator.community.controller.administrator;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.registrator.community.dao.ResourceRepository;
import org.registrator.community.dto.InquiryDTO;
import org.registrator.community.dto.InquiryListDTO;
import org.registrator.community.dto.TomeDTO;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.service.InquiryService;
import org.registrator.community.service.ResourceTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


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
	 * Method for showing form on UI to input the parameters 
	 * for inquiry to input the resource 
	 * (with existing registrators and resources).
	 */	
	@RequestMapping(value = "/inputInquiry", method = RequestMethod.GET)
	public String showInputInquiry(Model model) {
		logger.info("begin showInputInquiry");
		List<TomeDTO>  listTomeDTO = inquiryService.listTomeDTO();
		model.addAttribute("tomes", listTomeDTO);
		List<ResourceType> listOfResourceType = resourceTypeService.findAll();
		model.addAttribute("listOfResourceType", listOfResourceType);
		logger.info("end showInputInquiry");
		return "inquiryAddInput";
	}
	
	/**
	 * Method saves the data in the table inquiry_list.
	 */
	@RequestMapping(value = "/addInputInquiry", method = RequestMethod.POST)
	public String addInputInquiry(Model model, HttpSession session) {  			
		logger.info("begin addInputInquiry");
		String userLogin =(String) session.getAttribute("userLogin");	
		logger.info("userLogin = " + userLogin);
		
		//inquiryService.addOutputInquiry(inquiryDTO, userLogin);
		logger.info("end addInputInquiry");
		return  "redirect:/registrator/resource/addResource";	
	}
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
