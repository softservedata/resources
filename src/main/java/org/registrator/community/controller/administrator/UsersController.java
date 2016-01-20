package org.registrator.community.controller.administrator;

import java.util.List;

import javax.validation.Valid;

import org.registrator.community.components.AdminSettings;
import org.registrator.community.components.TableSettingsFactory;
import org.registrator.community.dto.UserDTO;
import org.registrator.community.dto.JSON.ResourceNumberDTOJSON;
import org.registrator.community.dto.JSON.UserStatusDTOJSON;
import org.registrator.community.dto.search.TableSearchRequestDTO;
import org.registrator.community.dto.search.TableSearchResponseDTO;
import org.registrator.community.entity.Role;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.UserStatus;
import org.registrator.community.service.RoleService;
import org.registrator.community.service.UserService;
import org.registrator.community.service.search.BaseSearchService;
//import org.registrator.community.validator.UserValidator;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
	
	@Autowired
	@Qualifier("registerUserSearchService")
	BaseSearchService<User> userSearchService;
	
	@Autowired
	TableSettingsFactory tableSettingsFactory;

	/**
	 * Controller for showing information about user
	 *
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_REGISTRATOR')")
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
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_REGISTRATOR')")
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
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_REGISTRATOR')")
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
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_REGISTRATOR')")
	@ResponseBody
	@RequestMapping(value = "/get-all-inactive-users", method = RequestMethod.POST)
	public String changeStatus(@RequestBody UserStatusDTOJSON userStatusDto) {
		logger.info("begin");
		userService.changeUserStatus(userStatusDto);
		logger.info("end");
		return "InActiveUsers";
	}

	/**
	 * Controller for showing modal window
	 *
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_REGISTRATOR')")
	@ResponseBody
	@RequestMapping(value = "/edit-registrated-user/modal-window", method = RequestMethod.POST)
	public ResponseEntity<String> showModalWindow(@RequestBody ResourceNumberDTOJSON resourceNumberDtoJson) {
		logger.info("begin");
		userService.createResourceNumber(resourceNumberDtoJson);
		userService.createTome(resourceNumberDtoJson);
		logger.info("end");
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * Controller for get all registrated users
	 * 
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_REGISTRATOR')")
	@RequestMapping(value = "/get-all-users", method = RequestMethod.GET)
	public String getAllUsers(Model model) {
		logger.info("begin");
		model.addAttribute("tableSetting", tableSettingsFactory.getTableSetting("registerUser"));
		logger.info("end");
		return "searchTableTemplate";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_REGISTRATOR')")
	@ResponseBody
	@RequestMapping(value = "registerUser",method = RequestMethod.POST)
	public TableSearchResponseDTO getDataFromDataTable(@Valid @RequestBody TableSearchRequestDTO dataTableRequest){
		TableSearchResponseDTO dto = userSearchService.executeSearchRequest(dataTableRequest);
		return dto;
	}

	/**
     * Method for showing administrator settings in order to change registration
     * method
     * @param model
     * @return adminSettings.jsp
     */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/settings", method = RequestMethod.GET)
    public String showSettings(Model model) {
        logger.info("begin: show admin settings");
        model.addAttribute("regMethod", adminSettings.getRegistrationMethod().toString());
        logger.info("end: admin settings are shown");
        return "adminSettings";
    }

	/**
     * Method for changing administrator settings for one of the possible
     * options
     * @param optratio - one of three possible option for changing registration method
     * @return adminSettings.jsp
     */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/settings", method = RequestMethod.POST)
    public String changeSettings(@RequestParam String optradio) {
        logger.info("start changing settings");
        adminSettings.changeRegMethod(optradio);
        logger.info("settings are successfully changed");
        return "adminSettings";
    }

}