
package org.registrator.community.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class TestController {
	
	@Autowired
	//private TestService testService;
	
	@RequestMapping(value="/aaa",method=RequestMethod.GET)
	public String getOtherPage(){
		return "index";
	}

	@RequestMapping(value="/",method=RequestMethod.GET)
	public String getMainPage(){
		return "index";
	}



}

