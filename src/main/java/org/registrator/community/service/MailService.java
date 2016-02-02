package org.registrator.community.service;

public interface MailService {
	
	public void sendRecoveryPasswordMail(String recepientEmail, String token);


}
