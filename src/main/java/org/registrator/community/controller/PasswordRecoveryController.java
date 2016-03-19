package org.registrator.community.controller;

import javax.servlet.http.HttpServletRequest;

import org.registrator.community.dto.PasswordRecoveryDTO;
import org.registrator.community.entity.User;
import org.registrator.community.service.PasswordRecoveryService;
import org.registrator.community.service.UserService;
import org.registrator.community.service.VerificationTokenService;
import org.registrator.community.validator.PasswordRecoveryValidator;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PasswordRecoveryController {

    @Autowired
    Logger logger;

    @Autowired
    private PasswordRecoveryService passwordRecoveryService;
    
    @Autowired
    private VerificationTokenService verificationTokenService;
    
    @Autowired
    private PasswordRecoveryValidator passwordRecoveryValidator;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder userPasswordEncoder;

    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
    @RequestMapping(value = "/forgot_password", method = RequestMethod.GET)
    public String getForgotPasswordPage() {
        return "forgot_password";
    }
    
    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
    @RequestMapping(value = "/forgot_password", method = RequestMethod.POST)
    public String handleForgotPasswordEmail(@RequestParam("email") String email, HttpServletRequest request, Model model) {
    	String baseLink = (request.getRequestURL()).toString().split("forgot_password")[0];
    	passwordRecoveryService.sendRecoverPasswordEmail(email,baseLink);
    	model.addAttribute("msg",true);
    	return "forgot_password";
    }   
   
    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
    @RequestMapping(value = "/password_recovery/{hash}", method = RequestMethod.GET)
    public String getPasswordRecoveryPage(@PathVariable("hash")String hash,Model model){
    	if(verificationTokenService.isExistValidVerificationToken(hash)){
    		model.addAttribute("hash", hash);
    		if (!model.containsAttribute("passwordRecoveryDTO")) {
    	        model.addAttribute("passwordRecoveryDTO", new PasswordRecoveryDTO());
    	    }
    		return "password_recovery";
    	}
    	return "redirect:/";
    }
    
    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
    @RequestMapping(value = "/password_recovery", method = RequestMethod.POST)
    public String handlePasswordRecoveryForm(@ModelAttribute("passwordRecoveryDTO") PasswordRecoveryDTO passwordRecoveryDTO, BindingResult bindingResult,Model model,
    		RedirectAttributes attr,HttpServletRequest request){
    	passwordRecoveryValidator.validate(passwordRecoveryDTO, bindingResult);
    	if(bindingResult.hasErrors()){
    		attr.addFlashAttribute("org.springframework.validation.BindingResult.passwordRecoveryDTO", bindingResult);
    		attr.addFlashAttribute("passwordRecoveryDTO", passwordRecoveryDTO);
    		return "redirect:"+request.getHeader("Referer");
    	}
    	boolean changePasswordResult=passwordRecoveryService.recoverPasswordByEmailLink(passwordRecoveryDTO.getHash(), passwordRecoveryDTO.getPassword());
    	if(changePasswordResult){
    		model.addAttribute("msg",true);
    		return "password_recovery";
    	}
    	return "redirect:"+request.getHeader("Referer");
    }

	@RequestMapping(value = "/change_password", method = RequestMethod.GET)
	public String getChangePasswordPage() {
		return "change_password";
	}

	@RequestMapping(value = "/change_password", method = RequestMethod.POST)
	public String handleChangePassword(@RequestParam("password") String password, HttpServletRequest request, Model model) {
		//String baseLink = (request.getRequestURL()).toString().split("forgot_password")[0];
		//passwordRecoveryService.sendRecoverPasswordEmail(email,baseLink);
		//model.addAttribute("msg",true);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.getUserByLogin(auth.getCredentials().toString());
		logger.info("user.password " + user.getPassword());

		logger.info( "passwword.matches " + userPasswordEncoder.matches(password, user.getPassword()));

        model.addAttribute("msg",true);
		return "change_password";
	}

	/*@RequestMapping(value = "/change_password", method = RequestMethod.GET)
	public String changePassword(Model model, HttpSession session) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = userService.getUserByLogin(auth.getCredentials().toString());
		logger.info("user.password " + user.getPassword());
		String actual = "amykyttc";
		logger.info( "passwword.matches " + userPasswordEncoder.matches(actual, user.getPassword()));

		if (!model.containsAttribute("passwordRecoveryDTO")) {
			model.addAttribute("passwordRecoveryDTO", new PasswordRecoveryDTO());
		}


		/*if (("anonymousUser").equals(auth.getName())) {
			logger.info("end: incorrect credentials");
			return "redirect:/login";
		}
		session.setAttribute("registrationMethod", adminSettings.getRegistrationMethod());
		logger.info("end: correct credentials");
		return "change_password";
	}*/


}
