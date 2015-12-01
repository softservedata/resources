package org.registrator.community.controller;

import org.registrator.community.dto.UserDTO;
import org.registrator.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class UsersController{

	@Autowired
	UserTestService testService;
	
	public void addNewUser(UserDTO user){
		
	}

	@RequestMapping(value ="/users",method = RequestMethod.GET)
	@ResponseBody
	public List<User> getAllUser(){
		return testService.getAllUsers();
	}

	@ResponseBody
	@RequestMapping(value ="/users/{id}",method = RequestMethod.GET)
	public User getUser(@PathVariable("id")int userId){
		return testService.getUserById(userId);


	}
}