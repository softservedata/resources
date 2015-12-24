package org.registrator.community.controller.administrator;

import java.util.ArrayList;
import java.util.List;

import org.registrator.community.dto.UserDTO;
import org.registrator.community.entity.Role;
import org.registrator.community.enumeration.UserStatus;
import org.registrator.community.service.RoleService;
import org.registrator.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping(value = "/administrator/users/")
public class UsersController {

	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;
	
	@RequestMapping(value = "/edit-registrated-user", method = RequestMethod.GET)
	public String fillEditWindow(@RequestParam("login") String login, Model model) {
		UserDTO userDto = userService.getUserDto(login);
		model.addAttribute("userDto", userDto);
		List<Role> roleList = roleService.getAllRole();
		model.addAttribute("roleList", roleList);
		List<UserStatus> userStatusList = userService.fillInUserStatus();
		model.addAttribute("userStatusList", userStatusList);
		return "editWindow";
	}
	
	@RequestMapping(value = "/edit-registrated-user", method = RequestMethod.POST)
	public String editRegistratedUser(@ModelAttribute("userDTO") UserDTO userDto, Model model) {
		UserDTO editUserDto = userService.editUserInformation(userDto);
		model.addAttribute("userDto", editUserDto);
		List<Role> roleList = roleService.getAllRole();
		model.addAttribute("roleList", roleList);
		List<UserStatus> userStatusList = userService.fillInUserStatus();
		model.addAttribute("userStatusList", userStatusList);
		return "editWindow";
	}
	
	@RequestMapping(value = "/get-all-users", method = RequestMethod.GET)
	public String getAllUsers(Model model) {
		List<UserDTO> userDtoList = new ArrayList<UserDTO>();
		userDtoList = userService.getAllRegistratedUsers();
		model.addAttribute("userDtoList", userDtoList);
		return "RegistratedUsers";
	}

	@RequestMapping(value = "/get-all-inactive-users", method = RequestMethod.GET)
	public String getAllInactiveUsers(Model model) {
		List<UserDTO> inactiveUsers = userService.getAllInactiveUsers();
		model.addAttribute("unregistatedUsers", inactiveUsers);
		List<UserStatus> userStatusList = userService.fillInUserStatus();
		model.addAttribute("userStatusList", userStatusList);
		List<Role> roleList = roleService.getAllRole();
		model.addAttribute("roleList", roleList);
		return "InActiveUsers";
	}
	
//	@RequestMapping(value = "/get-all-inactive-users", method = RequestMethod.POST)
//	public String getAllInactiveUsers(@ModelAttribute("UserDTO") List<UserDTO> userLisDto,Model model) {
//		System.out.println("hello");
////		List<UserDTO> inactiveUsers = userService.getAllInactiveUsers();
////		model.addAttribute("unregistatedUsers", inactiveUsers);
////		List<UserStatus> userStatusList = userService.fillInUserStatus();
////		model.addAttribute("userStatusList", userStatusList);
////		List<Role> roleList = roleService.getAllRole();
////		model.addAttribute("roleList", roleList);
//		return "InActiveUsers";
//	}

}