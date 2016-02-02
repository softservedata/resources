package org.registrator.community.controller;

import org.registrator.community.service.PasswordRecoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PasswordRecoveryController {

    @Autowired
    private PasswordRecoveryService passwordRecoveryService;

    @RequestMapping(value = "/forgot_password", method = RequestMethod.GET)
    public String getForgotPasswordPage() {
        return "undefined Page";
    }
    
    @ResponseBody
    @RequestMapping(value = "/forgot_password", method = RequestMethod.POST)
    public String handleForgotPasswordEmail(@RequestParam("email") String email) {
    	passwordRecoveryService.sendRecoverPasswordEmail(email);
        return email;
    }   
    
    @RequestMapping(value = "/recovery_password", method = RequestMethod.GET)
    public String getPasswordRecoveryPage(@RequestParam("hash") String hash){
        return "undefined Page";
    }
    
    @RequestMapping(value = "/recovery_password", method = RequestMethod.POST)
    public String handlePasswordRecoveryForm(@RequestParam("email") String email){    	
    	passwordRecoveryService.recoverPasswordByEmailLink(email);
        return "failure:email or captcha is incorrect";
    }
}
