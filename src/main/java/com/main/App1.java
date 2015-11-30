package com.main;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.registrator.community.dao.utils.HibernateUtil;
import org.registrator.community.dto.UserDTO;
import org.registrator.community.service.implementation.AdminServiceImpl;
import org.registrator.community.service.interfaces.AdminService;

public class App1 {

	public static void main(String[] args) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

//		// Save new User in Database
//		Role role = new Role("Registrator", "description");
//		DaoFactory.get().getRoleDao().add(role);
//		// session.save(role);
//		System.out.println(role.getRoleId());
//
//		List<AddressDTO> addressList = new ArrayList<AddressDTO>();
//		AddressDTO address = new AddressDTO();
//		List<PassportDTO> passportList = new ArrayList<PassportDTO>();
//		PassportDTO passport = new PassportDTO();
//
//		address.setBuilding("35");
//		address.setCity("Lviv");
//		address.setDistrict("District");
//		address.setFlat("44");
//		address.setPostcode("79026");
//		address.setRegion("Lviv");
//		address.setStreet("Street");
//
//		addressList.add(address);
//
//		passport.setPublished_by_data("Publisasdasdhed_aasdaby_data");
//		passport.setSeria("Serasasdsdadasdia");
//		passport.setNumber(2234);
//		passportList.add(passport);
//
//		UserService user = new UserServiceImpl();
//		UserDTO userDTO = new UserDTO();
//		userDTO.setFirstName("FirsadsasdasdaasdasdsdasddstName");
//		userDTO.setLastName("LastasdasasdasddasdasdName");
//		userDTO.setMiddleName("MiddsadsdasdleName");
//		userDTO.setLogin("loginasasdasddasd");
//		userDTO.setPassword("asdaasdasdadssd");
//		userDTO.setStatus("block");
//		userDTO.setEmail("emaiaasdaasdadsdsdasdl.com");
//		userDTO.setAddress(addressList);
//		userDTO.setPassport(passportList);
//		userDTO.setRole(role);
//
//		user.addUser(userDTO);
		List<UserDTO> userList = new ArrayList<UserDTO>();
		AdminService admin= new AdminServiceImpl();
		userList=admin.getAllUsers();

		for (UserDTO user1 : userList) {
			System.out.println(user1);
		}
		
		transaction.commit();
		session.close();
	}
}
