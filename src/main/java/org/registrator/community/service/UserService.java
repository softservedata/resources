package org.registrator.community.service;

import org.registrator.community.entity.User;
import org.registrator.community.enumeration.UserStatus;

public interface UserService {

	public void changeUserStatus(String login, UserStatus userStatus);

	public Iterable<User> getAllUnregisteredUsers(int page, int size);
	
	public User getUserByLogin(String login);

}

