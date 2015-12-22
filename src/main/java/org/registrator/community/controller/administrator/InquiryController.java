package org.registrator.community.controller.administrator;

import org.registrator.community.entity.Inquiry;
import org.registrator.community.service.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value ="/inquiry/add/")
public class InquiryController {
	
	@Autowired
	InquiryService inquiryService;
	
	@ResponseBody
	@RequestMapping(value ="/output/{inquiryListDTO}",method = RequestMethod.GET)
	public Inquiry testAddOutputInquiry(@PathVariable("inquiryListDTO") String resourceIdentifier){
		 return inquiryService.testAddOutputInquiry(resourceIdentifier);
	}
	
	@RequestMapping(value="/page/",method=RequestMethod.GET)
	public String getOutputInquiryPage(){
		return "inquiryAddOutput";
	}
	
	
}
