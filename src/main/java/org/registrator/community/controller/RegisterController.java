package org.registrator.community.controller;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.registrator.community.components.AdminSettings;
import org.registrator.community.forms.RegistrationForm;
import org.registrator.community.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class RegisterController {

    private static final Logger log = LoggerFactory.getLogger(RegisterController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private AdminSettings adminSettings;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showNewUserRegisterForm(Model model, HttpServletRequest request) {
        model.addAttribute("registrationForm", new RegistrationForm());
        log.info("Loaded 'New user registration form' " + request.getRemoteAddr());

        if ((adminSettings.getRegistrationMethod().toString() == "MANUAL")
                && (SecurityContextHolder.getContext().getAuthentication().getName() == "anonymousUser")) {
            return "redirect:/";
        }
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processNewUserData(@Valid RegistrationForm registrationForm, Errors result) {
        if (result.hasErrors()) {
            log.warn("Registration form sent to server with following errors: \n" + result.getFieldErrors()
                    + "\n Error messages displayed to user.");
            return "register";
        }
        userService.registerUser(registrationForm);

        log.info("Successfully registered new user: " + registrationForm.getLogin());
        return "thanks-for-registration";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginForm(Model model) {
        model.addAttribute("registrationMethod", adminSettings.getRegistrationMethod().toString()); 
        return "login";
    }

    @RequestMapping(value = "/logout")
    public String logout(Model map, HttpServletRequest req) {
        req.getSession().invalidate();
        SecurityContextHolder.clearContext();
        return "redirect:/login";
    }

    // Password Recovery
    @RequestMapping(value = "/send-password", method = RequestMethod.GET)
    public String showPasswordRecoveryForm() {
        return "password_recovery";
    }

    // Frequently Asked Questions (FAQ)
    @RequestMapping(value = "/faq", method = RequestMethod.GET)
    public String showFAQpage() {
        return "faq";
    }

    @RequestMapping(value = "/help", method = RequestMethod.GET)
    public String showContactAdminPage() {
        return "help";
    }

}
