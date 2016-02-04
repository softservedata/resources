package org.registrator.community.controller;

import javax.servlet.http.HttpServletRequest;

import org.registrator.community.dto.PasswordRecoveryDTO;
import org.registrator.community.service.PasswordRecoveryService;
import org.registrator.community.service.VerificationTokenService;
import org.registrator.community.validator.PasswordRecoveryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PasswordRecoveryController {

    @Autowired
    private PasswordRecoveryService passwordRecoveryService;
    
    @Autowired
    private VerificationTokenService verificationTokenService;
    
    @Autowired
    private PasswordRecoveryValidator passwordRecoveryValidator;

    @RequestMapping(value = "/forgot_password", method = RequestMethod.GET)
    public String getForgotPasswordPage() {
        return "forgot_password";
    }
    
    @RequestMapping(value = "/forgot_password", method = RequestMethod.POST)
    public String handleForgotPasswordEmail(@RequestParam("email") String email, HttpServletRequest request, Model model) {
    	String baseLink = (request.getRequestURL()).toString().split("forgot_password")[0];
    	passwordRecoveryService.sendRecoverPasswordEmail(email,baseLink);
    	model.addAttribute("msg",true);
    	return "forgot_password";
    }   
   
    @RequestMapping(value = "/password_recovery", method = RequestMethod.GET)
    public String getPasswordRecoveryPage(@RequestParam("hash")String hash,Model model){
    	if(verificationTokenService.isExistValidVerificationToken(hash)){
    		model.addAttribute("hash", hash);
    		model.addAttribute("passwordRecoveryDTO", new PasswordRecoveryDTO());
    		return "password_recovery";
    	}
    	return "redirect:/";
    }
    
    @RequestMapping(value = "/password_recovery", method = RequestMethod.POST)
    public String handlePasswordRecoveryForm(PasswordRecoveryDTO passwordRecover, BindingResult bindingResult,Model model){
    	passwordRecoveryValidator.validate(passwordRecover, bindingResult);
    	if(bindingResult.hasErrors()){
    		return "password_recovery";
    	}
    	boolean changePasswordResult=passwordRecoveryService.recoverPasswordByEmailLink(passwordRecover.getHash(), passwordRecover.getPassword());
    	if(changePasswordResult){
    		model.addAttribute("msg",true);
    	}
    	model.addAttribute("hash", passwordRecover.getHash());
    	return "password_recovery";
    }
}
