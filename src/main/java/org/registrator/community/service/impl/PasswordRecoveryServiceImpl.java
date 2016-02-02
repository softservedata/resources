package org.registrator.community.service.impl;

import java.util.Date;

import org.registrator.community.entity.User;
import org.registrator.community.entity.VerificationToken;
import org.registrator.community.service.MailService;
import org.registrator.community.service.PasswordRecoveryService;
import org.registrator.community.service.UserService;
import org.registrator.community.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordRecoveryServiceImpl implements PasswordRecoveryService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private VerificationTokenService verificationTokenService;

	@Override
	public boolean recoverPasswordByEmailLink(String token) {
		return false;
	}

	@Override
	public void sendRecoverPasswordEmail(String userEmail) {
		User user = userService.findUserByEmail(userEmail);
		if(user != null){
			VerificationToken verifacationToken= verificationTokenService.savePasswordVerificationToken(userEmail, new Date());
			mailService.sendRecoveryPasswordMail(userEmail, verifacationToken.getToken());
		}	
	}
}
