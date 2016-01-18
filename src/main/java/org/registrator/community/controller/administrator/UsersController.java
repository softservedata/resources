package org.registrator.community.controller.administrator;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.registrator.community.components.AdminSettings;
import org.registrator.community.dto.UserDTO;
import org.registrator.community.dto.JSON.ResourceNumberDTOJSON;
import org.registrator.community.dto.JSON.UserStatusDTOJSON;
import org.registrator.community.entity.Role;
import org.registrator.community.enumeration.UserStatus;
import org.registrator.community.service.RoleService;
import org.registrator.community.service.UserService;
//import org.registrator.community.validator.UserValidator;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/administrator/users/")
public class UsersController {

	@Autowired
	Logger logger;

	@Autowired
	UserService userService;

	@Autowired
	AdminSettings adminSettings;

	@Autowired
	RoleService roleService;

//	@Autowired
//	UserValidator userValidator;

	
	/**
	 * Controller for showing information about user
	 *
	 */
	@RequestMapping(value = "/edit-registrated-user", method = RequestMethod.GET)
	public String fillInEditWindow(@RequestParam("login") String login, Model model) {
		logger.info("begin");
		UserDTO userDto = userService.getUserDto(login);
		model.addAttribute("userDto", userDto);
		List<Role> roleList = roleService.getAllRole();
		model.addAttribute("roleList", roleList);
		List<UserStatus> userStatusList = userService.fillInUserStatusforRegistratedUsers();
		model.addAttribute("userStatusList", userStatusList);
		logger.info("end");
		return "editWindow";
	}
	
	/**
	 * Controller for editing user information
	 *
	 */
	@RequestMapping(value = "/edit-registrated-user", method = RequestMethod.POST)
	public String editRegistratedUser(@Valid @ModelAttribute("userDTO") UserDTO userDto, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			return fillInEditWindow(userDto.getLogin(), model);
		} else {
			logger.info("begin");
			UserDTO editUserDto = userService.editUserInformation(userDto);
			model.addAttribute("userDto", editUserDto);
			List<Role> roleList = roleService.getAllRole();
			model.addAttribute("roleList", roleList);
			List<UserStatus> userStatusList = userService.fillInUserStatusforRegistratedUsers();
			model.addAttribute("userStatusList", userStatusList);
			logger.info("end");
			return "redirect:/administrator/users/get-all-users";
		}

	}

	/**
	 * Controller for showing all inactive user
	 *
	 */
	@RequestMapping(value = "/get-all-inactive-users", method = RequestMethod.GET)
	public String getAllInactiveUsers(Model model) {
		logger.info("begin");
		List<UserDTO> inactiveUsers = userService.getAllInactiveUsers();
		model.addAttribute("unregistatedUsers", inactiveUsers);
		List<UserStatus> userStatusList = userService.fillInUserStatusforInactiveUsers();
		model.addAttribute("userStatusList", userStatusList);
		List<Role> roleList = roleService.getAllRole();
		model.addAttribute("roleList", roleList);
		logger.info("end");
		return "InActiveUsers";
	}
	
	
	/**
	 * Controller for changing user statur for inactive users
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "/get-all-inactive-users", method = RequestMethod.POST)
	public String changeStatus(@RequestBody UserStatusDTOJSON userStatusDto) {
		logger.info("begin");
		userService.changeUserStatus(userStatusDto);
		logger.info("end");
		return "InActiveUsers";
	}


	/**
	 * ???
	 *
	 */
	@RequestMapping(value = "/edit-registrated-user/modal-window", method = RequestMethod.GET)
	public ResponseEntity<String> fillModalWindow(Model model) {
		logger.info("begin");
		ResourceNumberDTOJSON resourceNumberDtoJson = new ResourceNumberDTOJSON();
		model.addAttribute("resourceNumberDtoJson", resourceNumberDtoJson);
		logger.info("end");
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * Controller for showing modal window
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "/edit-registrated-user/modal-window", method = RequestMethod.POST)
	public ResponseEntity<String> showModalWindow(@RequestBody ResourceNumberDTOJSON resourceNumberDtoJson,
			BindingResult result) {
		//userValidator.validate(resourceNumberDtoJson, result);
//		if(result.hasErrors()){
//			return new ResponseEntity<String>(HttpStatus.OK);
//		} else {
			logger.info("begin");
			userService.createResourceNumber(resourceNumberDtoJson);
			userService.createTome(resourceNumberDtoJson);
			logger.info("end");
			return new ResponseEntity<String>(HttpStatus.OK);
	//	}
	}

	/**
	 * Controller for get all registrated users
	 * 
	 */
	@RequestMapping(value = "/get-all-users", method = RequestMethod.GET)
	public String getAllUsers(Model model) {
		logger.info("begin");
		List<UserDTO> userDtoList = new ArrayList<UserDTO>();
		userDtoList = userService.getAllRegistratedUsers();
		model.addAttribute("userDtoList", userDtoList);
		logger.info("end");
		return "RegistratedUsers";
	}

	/**
	 * Method for showing administrator settings in order to change registration
	 * method
	 */
	@RequestMapping(value = "/settings", method = RequestMethod.GET)
	public String showSettings(Model model) {
		logger.info("begin");
		model.addAttribute("regMethod", adminSettings.getRegistrationMethod().toString());
		logger.info("end");
		return "adminSettings";
	}

	/**
	 * Method for changing administrator settings for one of the possible
	 * options
	 */
	@RequestMapping(value = "/settings", method = RequestMethod.POST)
	public String changeSettings(@RequestParam String optradio) {
		logger.info("begin");
		adminSettings.changeRegMethod(optradio);
		logger.info("end");
		return "adminSettings";
	}

}