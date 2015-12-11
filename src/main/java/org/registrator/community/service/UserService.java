package org.registrator.community.service;

import org.registrator.community.entity.User;


public interface UserService {
	
	public User getUserByLogin(String login);
	
	
}

