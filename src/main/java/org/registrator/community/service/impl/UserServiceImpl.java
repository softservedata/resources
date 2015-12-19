package org.registrator.community.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.registrator.community.dao.UserRepository;
import org.registrator.community.dto.AddressDTO;
import org.registrator.community.dto.PassportDTO;
import org.registrator.community.dto.UserDTO;
import org.registrator.community.entity.Address;
import org.registrator.community.entity.PassportInfo;
import org.registrator.community.entity.Role;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.UserStatus;
import org.registrator.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Transactional
	@Override
	public User getUserByLogin(String login) {
		return userRepository.findUserByLogin(login);
	}

	@Transactional
	@Override
	public void changeUserStatus(String login, UserStatus userStatus) {
		User user = getUserByLogin(login);
		user.setStatus(userStatus);
		userRepository.save(user);
	}

	@Transactional
	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Transactional
	@Override
	public List<User> getAllInACtiveUsers() {
		List<User> userList = new ArrayList<User>();
		List<User> unregistratedUserList = new ArrayList<User>();
		userList = userRepository.findAll();

		for (User user : userList) {
			if (user.getStatus() == UserStatus.INACTIVE) {
				unregistratedUserList.add(user);
			}
		}

		return unregistratedUserList;
	}

	@Transactional
	@Override
	public void changeUserRole(String login, Role role) {
		User user = getUserByLogin(login);
		user.setRole(role);
		userRepository.save(user);
	}

	@Transactional
	@Override
	public void editUserInformation(UserDTO userDto) {

	}

	@Transactional
	@Override
	public List<UserStatus> fillInUserStatus() {
		List<UserStatus> userStatusList = new ArrayList<UserStatus>();
		userStatusList.add(UserStatus.INACTIVE);
		userStatusList.add(UserStatus.BLOCK);
		userStatusList.add(UserStatus.UNBLOCK);
		return userStatusList;
	}

	@Transactional
	@Override
	public List<UserDTO> getUserDtoList() {
		List<UserDTO> userDtoList = new ArrayList<UserDTO>();
		List<User> userList = getAllUsers();
		for (User user : userList) {
			PassportInfo passportInfo = user.getPassport().get(user.getPassport().size() - 1); // Take
																								// the
																								// last
																								// element
																								// of
																								// the
																								// list
			PassportDTO passportDto = new PassportDTO(passportInfo.getSeria(), passportInfo.getNumber(),
					passportInfo.getPublishedByData());
			Address address = user.getAddress().get(user.getAddress().size() - 1); // Take
																					// the
																					// last
																					// element
																					// of
																					// the
																					// list
			AddressDTO addressDto = new AddressDTO(address.getPostCode(), address.getRegion(), address.getDistrict(),
					address.getCity(), address.getStreet(), address.getBuilding(), address.getFlat());
			UserDTO userDto = new UserDTO(user.getFirstName(), user.getLastName(), user.getMiddleName(), user.getRole(),
					user.getLogin(), user.getPassword(), user.getEmail(), user.getStatus().toString(), addressDto,
					passportDto);
			userDtoList.add(userDto);
		}
		return userDtoList;
	}

	@Override
	public UserDTO getUserDto(String login) {
		User user = userRepository.findUserByLogin(login);
		PassportInfo passportInfo = user.getPassport().get(user.getPassport().size() - 1);
		PassportDTO passportDto = new PassportDTO(passportInfo.getSeria(), passportInfo.getNumber(),
				passportInfo.getPublishedByData());
		Address address = user.getAddress().get(user.getAddress().size() - 1);
		AddressDTO addressDto = new AddressDTO(address.getPostCode(), address.getRegion(), address.getDistrict(),
				address.getCity(), address.getStreet(), address.getBuilding(), address.getFlat());
		UserDTO userdto = new UserDTO(user.getFirstName(), user.getLastName(), user.getMiddleName(), user.getRole(),
				user.getLogin(), user.getPassword(), user.getEmail(), user.getStatus().toString(), addressDto,
				passportDto);
		return userdto;
	}
}
