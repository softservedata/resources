package org.registrator.community.service.interfaces;

import java.util.List;

import org.registrator.community.dto.UserDTO;
import org.registrator.community.entity.Role;
import org.registrator.community.entity.UserStatus;

public interface AdminService {

		public List<UserDTO> getAllUsers();

		public String changeUserStatus(String login);
		
		public Role changeRole(String login);
		
		public void showAllUsers(List<UserDTO> userDtoList);

//		public List<User> getUserByRoleName(String role);
//		
//		public List<User> getUserByPortion(int from, int quantity);

		// show inactive users???

		// show all registers

		// find by last name

		// register new user

		// register new tom



}

