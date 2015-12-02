package org.registrator.community.service.interfaces;

import java.util.List;

import org.registrator.community.dto.UserDTO;

public interface AdminService {

		public List<UserDTO> getAllUsers();

		public UserDTO changeUserStatus(UserDTO userDto);
		
		public UserDTO changeRole(UserDTO userDto);
		
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