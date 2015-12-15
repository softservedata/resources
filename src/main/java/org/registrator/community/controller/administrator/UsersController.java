package org.registrator.community.controller.administrator;

import java.util.List;

import org.registrator.community.entity.Role;
import org.registrator.community.entity.User;
import org.registrator.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/administrator/users/")
public class UsersController {

	@Autowired
	UserService userService;

	@ResponseBody
	@RequestMapping(value = "/{login}", method = RequestMethod.GET)
	public User getUserbyLogin(@PathVariable("login") String login) {
		return userService.getUserByLogin(login);
	}

//	@RequestMapping(value = "/get-all-users/change-status", method = RequestMethod.POST)
//	public String changeStatus(@ModelAttribute("login") String login) {
//		userService.changeUserStatus(login,UserStatus.UNBLOCK);
//		return "index1";
//	}

	@RequestMapping(value= "/changeUserRole",method = RequestMethod.POST)
	public String changeRole(String login) {
		userService.changeUserRole("qqq", new Role("ADMIN","do something"));
		return "getUsers";
	}
	
	@RequestMapping(value = "/get-all-users", method = RequestMethod.GET)
	public String getAllUsers(Model model) {
		List<User> userList = userService.getAllUsers();
		model.addAttribute("userList", userList);
		return "getUsers";
	}
	
	@RequestMapping(value="/get-all-unregistated-users",method=RequestMethod.GET)
	public String getAllUnregistatedUsers(Model model) {
		List<User> unregistatedUsers = userService.getAllInACtiveUsers();
		model.addAttribute("unregistatedUsers", unregistatedUsers);
		return "getAllInactive";
	}
}