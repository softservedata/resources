package org.registrator.community.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class MainPageController {
	
	@Autowired
	private UserTestService testService;

	@RequestMapping(value="/",method=RequestMethod.GET)
	public String getMainPage(){
		return "index";
	}



}
