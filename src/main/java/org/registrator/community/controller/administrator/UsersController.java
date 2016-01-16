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

	@RequestMapping(value = "/edit-registrated-user", method = RequestMethod.GET)
	public String fillInEditWindow(@RequestParam("login") String login, Model model) {
		UserDTO userDto = userService.getUserDto(login);
		model.addAttribute("userDto", userDto);
		List<Role> roleList = roleService.getAllRole();
		model.addAttribute("roleList", roleList);
		List<UserStatus> userStatusList = userService.fillInUserStatusforRegistratedUsers();
		model.addAttribute("userStatusList", userStatusList);
		return "editWindow";
	}

	@RequestMapping(value = "/edit-registrated-user", method = RequestMethod.POST)
	public String editRegistratedUser(@Valid @ModelAttribute("userDTO") UserDTO userDto, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			return fillInEditWindow(userDto.getLogin(), model);
		} else {
			UserDTO editUserDto = userService.editUserInformation(userDto);
			model.addAttribute("userDto", editUserDto);
			List<Role> roleList = roleService.getAllRole();
			model.addAttribute("roleList", roleList);
			List<UserStatus> userStatusList = userService.fillInUserStatusforRegistratedUsers();
			model.addAttribute("userStatusList", userStatusList);
			return "redirect:/administrator/users/get-all-users";
		}

	}

  @RequestMapping(value = "/get-all-inactive-users", method = RequestMethod.GET)
	public String getAllInactiveUsers(Model model) {
		List<UserDTO> inactiveUsers = userService.getAllInactiveUsers();
		model.addAttribute("unregistatedUsers", inactiveUsers);
		List<UserStatus> userStatusList = userService.fillInUserStatusforInactiveUsers();
		model.addAttribute("userStatusList", userStatusList);
		List<Role> roleList = roleService.getAllRole();
		model.addAttribute("roleList", roleList);
		return "InActiveUsers";
	}

	@ResponseBody
	@RequestMapping(value = "/get-all-inactive-users", method = RequestMethod.POST)
	public String changeStatus(@RequestBody UserStatusDTOJSON userStatusDto) {
		userService.changeUserStatus(userStatusDto);
		return "InActiveUsers";
	}

	@RequestMapping(value = "/settings", method = RequestMethod.GET)
	public String showSettings(Model model) {
		return "adminSettings";
	}

	@RequestMapping(value = "/edit-registrated-user/modal-window", method = RequestMethod.GET)
	public ResponseEntity<String> fillModalWindow(Model model) {
		ResourceNumberDTOJSON resourceNumberDtoJson = new ResourceNumberDTOJSON();
		model.addAttribute("resourceNumberDtoJson", resourceNumberDtoJson);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/edit-registrated-user/modal-window", method = RequestMethod.POST)
	public ResponseEntity<String> showModalWindow(@RequestBody ResourceNumberDTOJSON resourceNumberDtoJson) {
		userService.createResourceNumber(resourceNumberDtoJson);
		userService.createTome(resourceNumberDtoJson);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

    @RequestMapping(value = "/get-all-users", method = RequestMethod.GET)
    public String getAllUsers(Model model) {
        List<UserDTO> userDtoList = new ArrayList<UserDTO>();
        userDtoList = userService.getAllRegistratedUsers();
        model.addAttribute("userDtoList", userDtoList);
        return "RegistratedUsers";
    }
}
   