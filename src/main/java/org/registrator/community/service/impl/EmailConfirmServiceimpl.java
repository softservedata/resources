package org.registrator.community.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.registrator.community.dao.UserRepository;
import org.registrator.community.dto.json.UsersDataNotConfJson;
import org.registrator.community.entity.User;
import org.registrator.community.entity.VerificationToken;
import org.registrator.community.enumeration.TokenType;
import org.registrator.community.enumeration.UserStatus;
import org.registrator.community.service.EmailConfirmService;
import org.registrator.community.service.MailService;
import org.registrator.community.service.UserService;
import org.registrator.community.service.VerificationTokenService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmailConfirmServiceimpl implements EmailConfirmService {
	
    @Autowired
    private Logger logger;
    
    @Autowired
	UserService userService;
	
	@Autowired
	private MailService mailService;

	@Autowired
	private VerificationTokenService verificationTokenService;
	
	
	
	/*@Autowired
	private PasswordEncoder  userPasswordEncoder;*/
	
	@Override
	public void sendConfirmEmailFirstTime(String login, String baseLink) {
		User user = userService.findUserByLogin(login);
		if(user != null){
			VerificationToken verifacationToken = verificationTokenService.saveEmailConfirmationToken(user.getLogin(), user.getEmail(), new Date(), baseLink);
			mailService.sendComfirmEMail(user.getEmail(), user.getFirstName(),verifacationToken.getToken(),baseLink);
		}	
	}
	
	@Override
    public void sendConfirmEmailAgain(String login) {
        User user = userService.findUserByLogin(login);
        if(user != null){
            VerificationToken verifacationToken = verificationTokenService.findVerificationTokenByLoginAndTokenType(login, TokenType.CONFIRM_EMAIL);
            mailService.sendComfirmEMail(user.getEmail(), user.getFirstName(),verifacationToken.getToken(),verifacationToken.getBaseLink());
        }   
    }
	
	@Transactional
	@Override
	public String actionsWithNotConfirmedUsers(UsersDataNotConfJson usersDataNotConfJson){
	    logger.info("Recieved data: " + usersDataNotConfJson);
	    if (usersDataNotConfJson.getActions()==null || usersDataNotConfJson.getLogins()==null) {
            logger.warn("Empty usersDataNotConfJson file");
            return "msg.batchops.wrongInput";
        }
        
        switch(usersDataNotConfJson.getActions()){
        case DELETE:
            logger.info("Run Action DELETE");
            return userService.deleteNotConfirmedUser(usersDataNotConfJson.getLogins());
        case SENDEMAILAGAIN:
            sendConfirmEmailAgain(usersDataNotConfJson.getLogins());
        default:
            break;
        
        }
	        
	    return null;
	}
	
	@Transactional
	@Override
	public Boolean confirmEmail(String token){
		
			VerificationToken verToken = verificationTokenService.findVerificationTokenByTokenAndTokenType(token, TokenType.CONFIRM_EMAIL);
			if (verToken == null) return false;
			User user = userService.findUserByEmail(verToken.getUserEmail());
			if (user == null) return false;
			user.setStatus(UserStatus.INACTIVE);
			userService.updateUser(user);
			verificationTokenService.deleteVerificationToken(verToken);
			return true;

	}

}
