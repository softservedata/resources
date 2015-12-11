package org.registrator.community.service.impl;

import java.util.List;

import org.registrator.community.dao.UserRepository;
import org.registrator.community.entity.User;
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
	/*//Ann
	@Override 
	public void deleteUserByLogin(String login){
		userRepository.delete(login);
	};
	//Ann
	@Override 
	public Integer addUser(User user){
		return 1;
	};*/
	
}
