package org.registrator.community.service;

import java.util.List;

import org.registrator.community.dto.UserDTO;
import org.registrator.community.dto.UserStatusDTO;
import org.registrator.community.entity.Role;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.UserStatus;

public interface UserService {

	public void changeUserStatus(UserStatusDTO userStatusDto);

	public List<UserDTO> getAllRegistratedUsers();
	
	public User getUserByLogin(String login);

	public void changeUserRole(String login, Role role);
	
	public UserDTO editUserInformation(UserDTO userDto);
	
	public List<UserStatus> fillInUserStatus();
	
	public List<UserDTO> getUserDtoList();
	
	public UserDTO getUserDto(String login);
	
	public List<UserDTO> getAllInactiveUsers();

}

