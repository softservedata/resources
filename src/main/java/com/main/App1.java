package com.main;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.registrator.community.dao.daofactory.DaoFactory;
import org.registrator.community.dao.utils.HibernateUtil;
import org.registrator.community.dto.AddressDTO;
import org.registrator.community.dto.PassportDTO;
import org.registrator.community.dto.UserDTO;
import org.registrator.community.entity.Role;
import org.registrator.community.service.implementation.AdminServiceImpl;
import org.registrator.community.service.implementation.UserServiceImpl;
import org.registrator.community.service.interfaces.AdminService;
import org.registrator.community.service.interfaces.UserService;


public class App1 {

	public static void main(String[] args) {
		
		AdminService adminService = new AdminServiceImpl();
		List<UserDTO> userDtoList = new ArrayList<UserDTO>();
		userDtoList = adminService.getAllUsers();
		adminService.showAllUsers(userDtoList);
		userDtoList.get(0).setStatus(adminService.changeUserStatus("login"));
		userDtoList.get(0).setRole(adminService.changeRole("login"));
		adminService.showAllUsers(userDtoList);
	}
}