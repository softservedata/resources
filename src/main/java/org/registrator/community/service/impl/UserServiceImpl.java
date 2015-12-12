package org.registrator.community.service.impl;

import java.util.List;

import org.registrator.community.dao.UserRepository;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.UserStatus;
import org.registrator.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public User getUserByLogin(String login) {
		return userRepository.findUserByLogin(login);
	}
	
	@Override
	public void changeUserStatus(String login, UserStatus userStatus) {
		User user = userRepository.findUserByLogin(login);
		user.setStatus(userStatus);
		userRepository.save(user);
	}

	@Override
	public List<User> getAllUnregisteredUsers(int page, int size) {
		Page<User> userPage = userRepository.findAll(new PageRequest(page, size));
		return userPage.getContent();
	}

}
