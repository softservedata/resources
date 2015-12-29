package org.registrator.community.controller;


import javax.validation.Valid;

import org.registrator.community.entity.Address;
import org.registrator.community.entity.PassportInfo;
import org.registrator.community.entity.User;
import org.registrator.community.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
public class RegisterController {

    @Autowired
    UserService userService;

    private static final Logger log = LoggerFactory.getLogger(RegisterController.class);

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showNewUserRegisterForm(){
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
//    public String processNewUserData(@Valid User user, @Valid PassportInfo passport, @Valid Address address, BindingResult result) {
    public String processNewUserData(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
        	System.out.println("***** Oops, unable to process new user register data: looks like one (or more) fields are filled in incorrectly - TEMPORARY STUB");
            return "register";
        }
        //userService.registerUser(user, passport, address);
        userService.registerUser(user);
        log.info("Successfully registered new user: " + user.getUserId());
        return "redirect:thanks-for-registration";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginForm() {
        return "login";
    }

    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String showUserProfile(@RequestParam String login, @RequestParam String password) {
        if(!userService.login(login, password)){
            return "The username or password is incorrect";
        }
        return "profile";
    }
}
