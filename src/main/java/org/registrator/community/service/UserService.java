package org.registrator.community.service;

import java.util.List;

import org.registrator.community.dto.UserDTO;
import org.registrator.community.dto.JSON.ResourceNumberDTOJSON;
import org.registrator.community.dto.JSON.UserStatusDTOJSON;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.UserStatus;
import org.registrator.community.forms.RegistrationForm;
import org.springframework.security.access.prepost.PreAuthorize;

public interface UserService {

	void changeUserStatus(UserStatusDTOJSON userStatusDto);
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMMISSIONER')")
	List<UserDTO> getAllRegistratedUsers();

	User getUserByLogin(String login);

	void changeUserRole(String login, Integer role_id);

	List<UserStatus> fillInUserStatusforRegistratedUsers();

	List<UserStatus> fillInUserStatusforInactiveUsers();

	List<UserDTO> getUserDtoList();

	UserDTO getUserDto(String login);
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMMISSIONER')")
	List<UserDTO> getAllInactiveUsers();

	void registerUser(RegistrationForm registrationForm);

	int updateUser(User user);

	boolean login(String username, String password);

	boolean checkUsernameNotExistInDB(String username);

	UserDTO editUserInformation(UserDTO userDto);

	void createTome(ResourceNumberDTOJSON resourseNumberDtoJson);
	
	void createResourceNumber(ResourceNumberDTOJSON resourseNumberDtoJson);

	List<UserDTO> getUserBySearchTag(String searchTag);
	
	public User findUserByEmail(String email);

}
