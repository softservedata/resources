package org.registrator.community.service;

import java.util.List;

import org.registrator.community.dto.UserDTO;
import org.registrator.community.entity.Address;
import org.registrator.community.entity.PassportInfo;
import org.registrator.community.entity.Role;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.UserStatus;

public interface UserService {

	void changeUserStatus(String login, UserStatus userStatus);

	List<UserDTO> getAllRegistratedUsers();
	
	User getUserByLogin(String login);

	void changeUserRole(String login, int role);
	
	UserDTO editUserInformation(UserDTO userDto);
	
	List<UserStatus> fillInUserStatus();
	
	List<UserDTO> getUserDtoList();
	
	UserDTO getUserDto(String login);
	
	List<UserDTO> getAllInactiveUsers();// set Role of inactive user to "USER"

	//void registerUser(User user, PassportInfo passport, Address address);
	void registerUser(User user);

	int updateUser(User user);

	boolean login(String username, String password);
}

