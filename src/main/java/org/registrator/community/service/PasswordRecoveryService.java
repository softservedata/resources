package org.registrator.community.service;

public interface PasswordRecoveryService {
	
	public boolean recoverPasswordByEmailLink(String token,String password);
	
	public void sendRecoverPasswordEmail(String email, String baseLink);

    boolean changePasswordByLogin(String password);

}
