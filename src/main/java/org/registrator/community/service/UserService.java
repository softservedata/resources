package org.registrator.community.service;

import java.util.List;

import org.registrator.community.dto.UserDTO;
import org.registrator.community.dto.UserStatusDTO;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.UserStatus;

public interface UserService {

	public void changeUserStatus(UserStatusDTO userStatusDto);

	List<UserDTO> getAllRegistratedUsers();

	User getUserByLogin(String login);

	void changeUserRole(String login, Integer role_id);

	UserDTO editUserInformation(UserDTO userDto);

	List<UserStatus> fillInUserStatus(List<UserDTO> userDtoList);

	List<UserDTO> getUserDtoList();

	UserDTO getUserDto(String login);

	List<UserDTO> getAllInactiveUsers();

	void registerUser(User user);

	int updateUser(User user);

	boolean login(String username, String password);
}
