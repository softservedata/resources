package org.registrator.community.service.implementation;

import java.util.List;

import org.registrator.community.dao.UserDao;
import org.registrator.community.dao.daofactory.DaoFactory;
import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.entity.User;
import org.registrator.community.service.interfaces.AdminService;
import org.registrator.community.service.interfaces.SearchService;

public class AdminServiceImpl implements AdminService, SearchService  {

	
	private UserDao userDao = DaoFactory.get().getUserDao();
	
	@Override
	public List<User> getAllUsers() {
		return userDao.getAll();
	}

	@Override
	public List<User> getUserByRoleName(String role) {
		
		return null;
	}

	@Override
	public List<User> getUserByPortion(int from, int quantity) {
		return userDao.getUsersListByPortions(from, quantity);
	}


	@Override
	public ResourceDTO searchResourcesbyId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}