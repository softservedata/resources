package org.registrator.community.application;

import java.util.ArrayList;
import java.util.List;
import org.registrator.community.dto.UserDTO;
import org.registrator.community.service.implementation.AdminServiceImpl;
import org.registrator.community.service.interfaces.AdminService;

/*
 * This is the application, which demonstrate us how administrator works.
 */

public class App {

	public static void main(String[] args) {

		// Some Users wants to be a part of our community
		// So let`s add them to all

			CreateTableInDB create = new CreateTableInDB();
			create.Fill();
			create.addSeveralUsers();
		
		AdminService adminService = new AdminServiceImpl();

		List<UserDTO> userDtoList = new ArrayList<UserDTO>();
		userDtoList = adminService.getAllUsers(); // Get all users from data
													// base
		adminService.showAllUsers(userDtoList); // Print all users on the screen

		// Change all user status
		int i = 0;
		for (UserDTO userDto : userDtoList) {
			userDtoList.get(i).setStatus(
					adminService.changeUserStatus(userDto.getLogin()));
			i++;
		}
		adminService.showAllUsers(userDtoList);

		// Change all user Role
		i = 0;
		for (UserDTO userDto : userDtoList) {
			userDtoList.get(i).setRole(
					adminService.changeRole(userDto.getLogin()));
			i++;
		}
		adminService.showAllUsers(userDtoList);

	}
}