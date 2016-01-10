package org.registrator.community.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.registrator.community.dao.AddressRepository;
import org.registrator.community.dao.PassportRepository;
import org.registrator.community.dao.RoleRepository;
import org.registrator.community.dao.UserRepository;
import org.registrator.community.dto.AddressDTO;
import org.registrator.community.dto.PassportDTO;
import org.registrator.community.dto.UserDTO;
import org.registrator.community.dto.UserStatusDTO;
import org.registrator.community.entity.Address;
import org.registrator.community.entity.PassportInfo;
import org.registrator.community.entity.Role;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.RoleType;
import org.registrator.community.enumeration.UserStatus;
import org.registrator.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PassportRepository passportRepository;

	@Autowired
	AddressRepository addressRepository;

	@Transactional
	@Override
	public User getUserByLogin(String login) {
		return userRepository.findUserByLogin(login);
	}

	@Transactional
	@Override
	public void changeUserStatus(UserStatusDTO userStatusDTO) {
		User user = getUserByLogin(userStatusDTO.getLogin());
		if (userStatusDTO.getStatus().equals(UserStatus.BLOCK.toString())) {
			user.setStatus(UserStatus.BLOCK);
		} else {
			if (userStatusDTO.getStatus().equals(UserStatus.UNBLOCK.toString())) {
				user.setStatus(UserStatus.UNBLOCK);
			} else {
				if (userStatusDTO.getStatus().equals(UserStatus.INACTIVE.toString())) {
					user.setStatus(UserStatus.INACTIVE);
				}
			}
		}
		userRepository.save(user);
	}

	@Transactional
	@Override
	public List<UserDTO> getAllRegistratedUsers() {
		List<UserDTO> userList = getUserDtoList();
		List<UserDTO> registratedUsers = new ArrayList<UserDTO>();

		for (UserDTO user : userList) {
			if (user.getStatus().toString() != UserStatus.INACTIVE.toString()) {
				registratedUsers.add(user);
			}
		}
		return registratedUsers;
	}

	@Transactional
	@Override
	public void changeUserRole(String login, int role_id) {
		User user = getUserByLogin(login);
		user.setRoleId(role_id);
		userRepository.save(user);
	}

	@Transactional
	@Override
	public UserDTO editUserInformation(UserDTO userDto) {
		User user = getUserByLogin(userDto.getLogin());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setMiddleName(userDto.getMiddleName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setRoleId(user.getRoleId());
		user.setStatus(checkUserStatus(userDto.getStatus()));
		PassportInfo passport = new PassportInfo(user, userDto.getPassport().getSeria(),
				userDto.getPassport().getNumber(), userDto.getPassport().getPublished_by_data());
		Address address = new Address(userDto.getAddress().getPostcode(), userDto.getAddress().getRegion(),
				userDto.getAddress().getDistrict(), userDto.getAddress().getCity(), userDto.getAddress().getStreet(),
				userDto.getAddress().getBuilding(), userDto.getAddress().getFlat());
		int result = user.getAddress().get(user.getAddress().size() - 1).compareTo(address);
		if (result != 0) {
			addressRepository.save(address);
		}
		result = user.getPassport().get(user.getPassport().size() - 1).compareTo(passport);
		if (result != 0) {
			passportRepository.save(passport);
		}
		userRepository.save(user);

		return userDto;
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
		List<User> userList = userRepository.findAll();
		for (User user : userList) {
			PassportInfo passportInfo = user.getPassport().get(user.getPassport().size() - 1);
			PassportDTO passportDto = new PassportDTO(passportInfo.getSeria(), passportInfo.getNumber(),
					passportInfo.getPublishedByData());
			Address address = user.getAddress().get(user.getAddress().size() - 1);
			AddressDTO addressDto = new AddressDTO(address.getPostCode(), address.getRegion(), address.getDistrict(),
					address.getCity(), address.getStreet(), address.getBuilding(), address.getFlat());
			UserDTO userDto = new UserDTO(user.getFirstName(), user.getLastName(), user.getMiddleName(),
					user.getRoleById(user.getRoleId()), user.getLogin(), user.getPassword(), user.getEmail(),
					user.getStatus().toString(), addressDto, passportDto);
			userDtoList.add(userDto);
		}
		return userDtoList;
	}

	@Transactional
	@Override
	public UserDTO getUserDto(String login) {
		User user = getUserByLogin(login);
		PassportInfo passportInfo = user.getPassport().get(user.getPassport().size() - 1);
		PassportDTO passportDto = new PassportDTO(passportInfo.getSeria(), passportInfo.getNumber(),
				passportInfo.getPublishedByData());
		Address address = user.getAddress().get(user.getAddress().size() - 1);
		AddressDTO addressDto = new AddressDTO(address.getPostCode(), address.getRegion(), address.getDistrict(),
				address.getCity(), address.getStreet(), address.getBuilding(), address.getFlat());
		UserDTO userdto = new UserDTO(user.getFirstName(), user.getLastName(), user.getMiddleName(),
				user.getRoleById(user.getRoleId()), user.getLogin(), user.getPassword(), user.getEmail(),
				user.getStatus().toString(), addressDto, passportDto);
		return userdto;
	}

	@Transactional
	@Override
	public List<UserDTO> getAllInactiveUsers() {
		List<UserDTO> userDtoList = new ArrayList<UserDTO>();
		userDtoList = getUserDtoList();
		List<UserDTO> inactiveUserDtoList = new ArrayList<UserDTO>();
		for (UserDTO userDto : userDtoList) {
			if (userDto.getStatus() == UserStatus.INACTIVE.toString()) {
				userDto.setRole("USER");
				inactiveUserDtoList.add(userDto);
			}
		}
		return inactiveUserDtoList;
	}

	@Override
	@Transactional
	public void registerUser(User user, Address address) {
//	public void registerUser(User user, PassportInfo passport, Address address) {
	//public void registerUser(User user) {
		// by default, every new user is given role "User" and status "Inactive" until it's changed by Admin
		// Roles map: Admin - 1, Registrator - 2, User - 3
		user.setRoleId(3);
		user.setStatus(UserStatus.INACTIVE);
		user.setPasswordHash(DigestUtils.md5Hex(user.getUserId() + user.getPassword()));

		userRepository.saveAndFlush(user);
		if(userRepository.findUserByLogin(user.getLogin()) != null){
			address.setUser(user);
//			address.setUserId(user.getUserId());
			addressRepository.saveAndFlush(address);
		}
		//passportRepository.saveAndFlush(passport);
		//addressRepository.saveAndFlush(address);
	}

	@Transactional
	@Override
	public int updateUser(User user) {
		return 0;
	}

	@Transactional
	@Override
	public boolean login(String login, String password) {

		if(userRepository.findUserByLogin(login) != null &&
				userRepository.getUsersPasswordHash(login) == DigestUtils.md5Hex(password)){
			return true;
		}
		else {
			return false;
		}
	}

	@Transactional
	@Override
	public boolean checkUsernameNotExistInDB(String login) {
		if(userRepository.findUserByLogin(login) != null){
			// when username exists in DB
			return false;
		}
		// if username isn't found in DB
		return true;
	}

//	@Override
//	public boolean recoverUsersPassword(String email, String usersCaptchaAnswer, String captchaFileName) {
//		if(validateUsersEmail(email) && validateCaptchaCode(captchaFileName)){
//			return true;
//		}
//		return false; //- "There is no such email in the DB" or "Entered captcha code is incorrect"
//	}



	//captchaMap.putAll(captchaProperties);
	//public String getProperty(String key) {
//			return (String) captchaMap.get(key);
//		}

//	public boolean validateUsersEmail (String email){
//		userRepository.getUserByEmail(email);
//		//TBD !!!!!!!!!!!!
//		return true;
//	}


	private UserStatus checkUserStatus(String status) {
		if (status.equals(UserStatus.BLOCK.name())) {
			return UserStatus.BLOCK;
		} else if (status.equals(UserStatus.INACTIVE.name())) {
			return UserStatus.INACTIVE;
		} else {
			return UserStatus.UNBLOCK;
		}
	}
}