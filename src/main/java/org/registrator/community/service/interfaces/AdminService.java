package org.registrator.community.service.interfaces;

import java.util.List;

import org.registrator.community.dto.UserDTO;
import org.registrator.community.entity.User;

public interface AdminService {
	
	
		
		public List<UserDTO> getAllUsers();
		
		public UserDTO blockUser(UserDTO userDto);
		
		public UserDTO changeRole(UserDTO userDto) ;
		
//		public List<User> getUserByRoleName(String role);
//		
//		public List<User> getUserByPortion(int from, int quantity);
		
		// show all (users+registers+admin?)
		
		// show all users
		
		// show inactive users???
		
		// show all registers
		
		// find by last name
		
		// change role
		
		// change status
		
		// register new user
		
		// register new tom

	

}
