package org.registrator.community.controller.administrator;

import org.registrator.community.controller.UserTestService;
import org.registrator.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping(value ="/administrator/users/")
public class UsersController{

	@Autowired
	UserTestService testService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<User> getAllUser(){
		return testService.getAllUsers();
	}

	@ResponseBody
	@RequestMapping(value ="/{id}",method = RequestMethod.GET)
	public User getUser(@PathVariable("id")int userId){
		return testService.getUserById(userId);
	}

	@RequestMapping(value ="/{from}/{quantity}",method = RequestMethod.GET)
	@ResponseBody
	public List<User> getPortionOfUser(@PathVariable("from") int from,@PathVariable("quantity") int quantity){
		return testService.getPortionOfUser(from, quantity);
	}

	@RequestMapping(value ="waiting/{from}/{quantity}",method = RequestMethod.GET)
	@ResponseBody
	public List<User> getWaitingUser(@PathVariable("from") int from,@PathVariable("quantity") int quantity){
		return testService.getPortionOfUser(from, quantity);
	}

	@RequestMapping(value ="/{id}",method = RequestMethod.PUT)
	@ResponseBody
	public User updateUserProfile(User user){
		return testService.getUserById(user.getUserId());
	}





}