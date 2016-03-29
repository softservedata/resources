package org.registrator.community.controller.administrator;

import org.registrator.community.service.SettingsService;
import org.registrator.community.entity.ApplicationProperty;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/administrator/")
public class SettingsController {

	@Autowired
	private Logger logger;

	@Autowired
	private SettingsService settingsService;


	/**
	 * Method for showing administrator settings in order to change registration
	 * method
	 * 
	 * @param model
	 * @return adminSettings.jsp
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/settings", method = RequestMethod.GET)
	public String showSettings(Model model) {
		logger.info("begin: show admin settings");
		model.addAttribute("regMethod", settingsService.getPropertyValue(ApplicationProperty.REGISTRATION_METHOD));
		model.addAttribute("timeZone", settingsService.getPropertyValue(ApplicationProperty.TIME_ZONE));
		logger.info("end: admin settings are shown");
		return "adminSettings";
	}

	/**
	 * Method for changing administrator settings for one of the possible
	 * options
	 * 
	 * @param optradio
	 *            - one of three possible option for changing registration
	 *            method
	 * @return adminSettings.jsp
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/settings", method = RequestMethod.POST)
	public String changeSettings(@RequestParam String optradio) {
		logger.info("start changing settings");
//		adminSettings.changeRegMethod(optradio);
		logger.info("settings are successfully changed");
		return "adminSettings";
	}

	
}