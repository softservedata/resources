package org.registrator.community.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.registrator.community.dao.daofactory.DaoFactory;
import org.registrator.community.dto.AddressDTO;
import org.registrator.community.dto.PassportDTO;
import org.registrator.community.dto.UserDTO;
import org.registrator.community.entity.Address;
import org.registrator.community.entity.PassportInfo;
import org.registrator.community.entity.Role;
import org.registrator.community.entity.User;
import org.registrator.community.entity.UserStatus;
import org.registrator.community.service.interfaces.AdminService;
import org.registrator.community.service.interfaces.SearchService;

public class AdminServiceImpl implements AdminService, SearchService {

	@Override
	public List<UserDTO> getAllUsers() {

		List<UserDTO> userDtoList = new ArrayList<UserDTO>();
		List<User> userList = DaoFactory.get().getUserDao().getAll();

		List<PassportInfo> passportInfoList = DaoFactory.get()
				.getPassportInfoDao().getAll();
		List<PassportDTO> passportDtoList = new ArrayList<PassportDTO>();

		List<AddressDTO> addressDtoList = new ArrayList<AddressDTO>();
		List<Address> addressList = DaoFactory.get().getAddressDao().getAll();

		// Role role = new Role("Admin");

		// Add passport info to DTO class
		for (PassportInfo passportEntity : passportInfoList) {
			PassportDTO passportDto = new PassportDTO();
			passportDto.setNumber(passportEntity.getNumber());
			passportDto.setPublished_by_data(passportEntity
					.getPublished_by_data());
			passportDto.setSeria(passportEntity.getSeria());
			passportDtoList.add(passportDto);
		}
		// Add address info to DTO class
		for (Address addressEntity : addressList) {
			AddressDTO addressDto = new AddressDTO();
			addressDto.setBuilding(addressEntity.getBuilding());
			addressDto.setCity(addressEntity.getCity());
			addressDto.setDistrict(addressEntity.getDistrict());
			addressDto.setFlat(addressEntity.getFlat());
			addressDto.setPostcode(addressEntity.getPostCode());
			addressDto.setRegion(addressEntity.getRegion());
			addressDto.setStreet(addressEntity.getStreet());
			addressDtoList.add(addressDto);
		}
		// Add User Iinfo to DTO class
		for (User userEntity : userList) {
			UserDTO userDto = new UserDTO();
			userDto.setAddress(addressDtoList);
			userDto.setEmail(userEntity.getEmail());
			userDto.setFirstName(userEntity.getFirstName());
			userDto.setLastName(userEntity.getLastName());
			userDto.setLogin(userEntity.getLogin());
			userDto.setMiddleName(userEntity.getMiddleName());
			userDto.setPassport(passportDtoList);
			userDto.setPassword(userEntity.getPassword());
			userDto.setRole(userEntity.getRole());
			userDto.setStatus(userEntity.getStatus().toString());
			userDtoList.add(userDto);

		}

		return userDtoList;
	}

	@Override
	public UserDTO changeUserStatus(UserDTO userDto) {

		User user = DaoFactory.get().getUserDao()
				.findByLogin(userDto.getLogin());

		if (userDto.getStatus() == UserStatus.UNBLOCK.toString()) {
			user.setStatus(UserStatus.BLOCK);
			DaoFactory.get().getUserDao().update(user);
			userDto.setStatus("block");
		} else {
			user.setStatus(UserStatus.UNBLOCK);
			DaoFactory.get().getUserDao().update(user);
			userDto.setStatus("unblock");
		}

		return userDto;
	}

	@Override
	public UserDTO changeRole(UserDTO userDto) {
		User user = DaoFactory.get().getUserDao()
				.findByLogin(userDto.getLogin());
		List<Role> roleList = DaoFactory.getDaoFactory().getRoleDao().getAll();
		
		
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter key");
		int key = sc.nextInt();

		if (userDto.getRole().getName().toString() == "USER") {
			if (key == 1) {
				user.setRole(roleList.get(1));
				DaoFactory.get().getUserDao().update(user);
				userDto.setRole(roleList.get(1));
				key = 0;
			} else {
				if (key == 2) {
					user.setRole(roleList.get(2));
					DaoFactory.get().getUserDao().update(user);
					userDto.setRole(roleList.get(2));
					key = 0;
				}
			}

		}

		if (userDto.getRole().getName().toString() == "REGISTRATOR") {
			if (key == 1) {
				user.setRole(roleList.get(0));
				DaoFactory.get().getUserDao().update(user);
				userDto.setRole(roleList.get(0));
				key = 0;
			} else {
				if (key == 2) {
					user.setRole(roleList.get(2));
					DaoFactory.get().getUserDao().update(user);
					userDto.setRole(roleList.get(2));
					key = 0;
				}
			}
		}

		if (userDto.getRole().getName().toString() == "ADMIN") {
			if (key == 1) {
				user.setRole(roleList.get(0));
				DaoFactory.get().getUserDao().update(user);
				userDto.setRole(roleList.get(0));
				key = 0;
			} else {
				if (key == 2) {
					user.setRole(roleList.get(1));
					DaoFactory.get().getUserDao().update(user);
					userDto.setRole(roleList.get(1));
					key = 0;
				}
			}
		}

		return userDto;
	}

}