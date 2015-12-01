package com.main;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.registrator.community.dao.daofactory.DaoFactory;
import org.registrator.community.dao.utils.HibernateUtil;
import org.registrator.community.dto.AddressDTO;
import org.registrator.community.dto.PassportDTO;
import org.registrator.community.dto.UserDTO;
import org.registrator.community.entity.Role;
import org.registrator.community.service.implementation.AdminServiceImpl;
import org.registrator.community.service.implementation.UserServiceImpl;
import org.registrator.community.service.interfaces.AdminService;
import org.registrator.community.service.interfaces.UserService;

public class App1 {

	public static void main(String[] args) {

		 
		
		 // Save new User in Database
		 Role role = new Role("User", "description");
		 DaoFactory.get().getRoleDao().add(role);
		 // session.save(role);
		 System.out.println(role.getRoleId());
		
		 List<AddressDTO> addressList = new ArrayList<AddressDTO>();
		 AddressDTO address = new AddressDTO();
		 List<PassportDTO> passportList = new ArrayList<PassportDTO>();
		 PassportDTO passport = new PassportDTO();
		
		 address.setBuilding("10");
		 address.setCity("Lvirrrv");
		 address.setDistrict("Distrrrrict");
		 address.setFlat("15");
		 address.setPostcode("79036");
		 address.setRegion("Lvivrr");
		 address.setStreet("Streert");
		
		 addressList.add(address);
		
		 passport.setPublished_by_data("Publisasdasdhed_aasdaby_data");
		 passport.setSeria("Serasasdsdadasdia");
		 passport.setNumber(2234);
		 passportList.add(passport);
		
		 UserService user = new UserServiceImpl();
		 UserDTO userDTO = new UserDTO();
		 userDTO.setFirstName("ee4ee");
		 userDTO.setLastName("iui4u");
		 userDTO.setMiddleName("trata4ta");
		 userDTO.setLogin("loginasasa4sdasddasddasd");
		 userDTO.setPassword("55555");
		 userDTO.setStatus("unblock");
		 userDTO.setEmail("c.com");
		 userDTO.setAddress(addressList);
		 userDTO.setPassport(passportList);
		 userDTO.setRole(role);
		
		user.addUser(userDTO);
		List<UserDTO> userList = new ArrayList<UserDTO>();
		AdminService admin = new AdminServiceImpl();
		userList = admin.getAllUsers();

		for (UserDTO user1 : userList) {
			UserDTO user2 = new UserDTO();
//			user2 = admin.blockUser(user1);
			System.out.println(user1);
		}

		//
		// transaction.commit();
		// session.close();

	}
}
