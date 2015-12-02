package org.registrator.community.controller;

import org.registrator.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserTestService {

	@Autowired
	private UserDaoTest userDao;

	@Transactional(readOnly = true)
	public List<User> getAllUsers(){
		return userDao.getAllEntity();
	}

	public User getUserById(int id){
		return userDao.getEntityById(id);
	}

	public List<User> getPortionOfUser(int from,int quantity){
		return userDao.getPortionOfUser(from, quantity);
	}

	public User updateUser(User user){
		return userDao.update(user);
	}

}

