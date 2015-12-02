package com.main;

import java.util.ArrayList;
import java.util.List;

import org.registrator.community.dto.UserDTO;
import org.registrator.community.service.implementation.AdminServiceImpl;
import org.registrator.community.service.interfaces.AdminService;

public class App1 {
	
	public static void main(String[] args) {
		AdminService admin = new AdminServiceImpl();
		List<UserDTO> userDtoList = new ArrayList<UserDTO>();
		userDtoList = admin.getAllUsers();
		admin.showAllUsers(userDtoList);
		
	}
}