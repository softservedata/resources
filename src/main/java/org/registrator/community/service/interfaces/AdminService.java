package org.registrator.community.service.interfaces;

import java.util.List;

import org.registrator.community.dto.UserDTO;
import org.registrator.community.entity.Role;

public interface AdminService {

	public List<UserDTO> getAllUsers();

	public String changeUserStatus(String login);

	public Role changeRole(String login);

	public void showAllUsers(List<UserDTO> userDtoList);

	// register new tom

}
