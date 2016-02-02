package org.registrator.community.service;

public interface PasswordRecoveryService {
	
	public boolean recoverPasswordByEmailLink(String email);
	
	public void sendRecoverPasswordEmail(String email);

}
