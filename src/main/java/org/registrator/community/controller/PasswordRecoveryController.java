package org.registrator.community.controller;

import org.registrator.community.dao.UserRepository;
import org.registrator.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PasswordRecoveryController {

    @Autowired
    UserService userService;

//    @RequestMapping(value = "/send-password", method = RequestMethod.GET)
//    public String showPasswordRecoveryForm(){
//        return "password_recovery";
//    }

    @RequestMapping(value = "/send-password", method = RequestMethod.POST)
    public String processPasswordRecoveryForm(@RequestParam String email){
//        if(userService.recoverUsersPassword(email)){
//            return "success:password_sent_to_email";
//        }

        return "failure:email or captcha is incorrect";
    }
}
