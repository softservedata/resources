package org.registrator.community.service;

import java.util.Date;

import org.registrator.community.entity.User;
import org.registrator.community.entity.VerificationToken;

public interface VerificationTokenService {
	
	public boolean deletePasswordVerificationTokenByEmail(String email);
	
	public VerificationToken savePasswordVerificationToken(String userEmail, Date nowTime);
	
	public String createHashForPasswordToken();
	
	public User findUserByToken(String token);

}
