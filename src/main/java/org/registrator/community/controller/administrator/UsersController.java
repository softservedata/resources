package org.registrator.community.controller.administrator;

import java.util.List;

import org.registrator.community.entity.User;
import org.registrator.community.service.ResourceService;
import org.registrator.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value ="/administrator/users/")
public class UsersController{

	@Autowired
	UserService userService;
	
	@ResponseBody
	@RequestMapping(value ="/{login}",method = RequestMethod.GET)
	public User getUserbyLogin(@PathVariable("login") String login){
		return userService.getUserByLogin(login);
	}

}