package org.registrator.community.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.registrator.community.dao.UserRepository;
import org.registrator.community.dto.UserDTO;
import org.registrator.community.entity.Role;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.UserStatus;
import org.registrator.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Transactional
	@Override
	public User getUserByLogin(String login) {
		return userRepository.findUserByLogin(login);
	}

	@Transactional
	@Override
	public void changeUserStatus(String login, UserStatus userStatus) {
		User user = getUserByLogin(login);
		user.setStatus(userStatus);
		userRepository.save(user);
	}

	@Transactional
	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Transactional
	@Override
	public List<User> getAllInACtiveUsers() {
		List<User> userList = new ArrayList<User>();
		List<User> unregistratedUserList = new ArrayList<User>();
		userList = userRepository.findAll();

		for (User user : userList) {
			if (user.getStatus() == UserStatus.INACTIVE) {
				unregistratedUserList.add(user);
			}
		}

		return unregistratedUserList;
	}

	@Transactional
	@Override
	public void changeUserRole(String login, Role role) {
		User user = getUserByLogin(login);
		user.setRole(role);
		userRepository.save(user);
	}

	@Transactional
	@Override
	public void editUserInformation(UserDTO userDto) {
		
	}

	@Override
	public List<UserStatus> fillInUserStatus() {
		List<UserStatus> userStatusList = new ArrayList<UserStatus>();
		userStatusList.add(UserStatus.INACTIVE);
		userStatusList.add(UserStatus.BLOCK);
		userStatusList.add(UserStatus.UNBLOCK);
		return userStatusList;
	}

}

