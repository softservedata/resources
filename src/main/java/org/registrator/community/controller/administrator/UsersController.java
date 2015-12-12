package org.registrator.community.controller.administrator;

import java.util.List;

import org.registrator.community.entity.User;
import org.registrator.community.enumeration.UserStatus;
import org.registrator.community.service.ResourceService;
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

	@RequestMapping(value = "/changestatus/", method = RequestMethod.GET)
	public String changeStatus() {
		userService.changeUserStatus("qqq", UserStatus.UNBLOCK);
		return "index";
	}
	
	@RequestMapping(value="/{page},{size}",method = RequestMethod.GET)
	public String showUser(Model map) {
		List<User> users=(List<User>) userService.getAllUnregisteredUsers(0, 10);
		map.addAttribute("list", users);
		return "index1";
		
	}
}