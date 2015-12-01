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
import org.registrator.community.entity.User;
import org.registrator.community.service.implementation.AdminServiceImpl;
import org.registrator.community.service.implementation.UserServiceImpl;
import org.registrator.community.service.interfaces.AdminService;
import org.registrator.community.service.interfaces.UserService;

public class App1 {

	public static void main(String[] args) {

<<<<<<< HEAD
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		//
		// // Save new User in Database
		// List<AddressDTO> addressList = new ArrayList<AddressDTO>();
		// AddressDTO address = new AddressDTO();
		// List<PassportDTO> passportList = new ArrayList<PassportDTO>();
		// PassportDTO passport = new PassportDTO();
		// //User userEntity= new User();
		// List<Role> roleList = DaoFactory.get().getRoleDao().getAll();
		//
		//
		// address.setBuilding("35");
		// address.setCity("Lviv");
		// address.setDistrict("District");
		// address.setFlat("44");
		// address.setPostcode("79026");
		// address.setRegion("Lviv");
		// address.setStreet("Street");
		//
		// addressList.add(address);
		//
		// passport.setPublished_by_data("Publisaasdasdsdasdhed_aasdaby_data");
		// passport.setSeria("Serasasdsdasdasdadasdia");
		// passport.setNumber(2222234);
		// passportList.add(passport);
		//
		// UserService user = new UserServiceImpl();
		// UserDTO userDTO = new UserDTO();
		// userDTO.setFirstName("FirsadsasdasasdasddaaasdasdsdasdsdasddstName");
		// userDTO.setLastName("LastasdasaasdasdsdasdasdasddasdasdName");
		// userDTO.setMiddleName("asdasdasd");
		// userDTO.setLogin("loginasasasdasdasdasdaasddasddasd");
		// userDTO.setPassword("asdasdaasd");
		// userDTO.setStatus("block");
		// userDTO.setEmail("emaiaasdaasdaasasdasdadasddsdsdasdl.com");
		// userDTO.setAddress(addressList);
		// userDTO.setPassport(passportList);
		// userDTO.setRole(roleList.get(2));
		//
		// user.addUser(userDTO);

=======
		 
	/*	
		 // Save new User in Database
		 Role role = new Role("Registrator", "description");
		 DaoFactory.get().getRoleDao().add(role);
		 // session.save(role);
		 System.out.println(role.getRoleId());
		
		 List<AddressDTO> addressList = new ArrayList<AddressDTO>();
		 AddressDTO address = new AddressDTO();
		 List<PassportDTO> passportList = new ArrayList<PassportDTO>();
		 PassportDTO passport = new PassportDTO();
		
		 address.setBuilding("35");
		 address.setCity("Lviv");
		 address.setDistrict("District");
		 address.setFlat("44");
		 address.setPostcode("79026");
		 address.setRegion("Lviv");
		 address.setStreet("Street");
		
		 addressList.add(address);
		
		 passport.setPublished_by_data("Publisasdasdhed_aasdaby_data");
		 passport.setSeria("Serasasdsdadasdia");
		 passport.setNumber(2234);
		 passportList.add(passport);
		
		 UserService user = new UserServiceImpl();
		 UserDTO userDTO = new UserDTO();
		 userDTO.setFirstName("FirsadsasdasdaaasdasdsdasdsdasddstName");
		 userDTO.setLastName("LastasdasasdasdasdasddasdasdName");
		 userDTO.setMiddleName("MiddsadsdasdleName");
		 userDTO.setLogin("loginasasasdasddasddasd");
		 userDTO.setPassword("asdaasdaasdasdasdassdadssd");
		 userDTO.setStatus("unblock");
		 userDTO.setEmail("emaiaasdaasdaasdasddsdsdasdl.com");
		 userDTO.setAddress(addressList);
		 userDTO.setPassport(passportList);
		 userDTO.setRole(role);
		
		user.addUser(userDTO);*/
>>>>>>> ab408ff564090a4dba045f09813d5d99026e3dac
		List<UserDTO> userList = new ArrayList<UserDTO>();
		AdminService admin = new AdminServiceImpl();
		userList = admin.getAllUsers();

		for (UserDTO user1 : userList) {
			UserDTO user2 = new UserDTO();
			user2 = admin.changeUserStatus(user1);
			user2 = admin.changeRole(user1);
			System.out.println(user2);
		}

		// List<Role> roleList = null;
		// roleList = DaoFactory.get().getRoleDao().getAll();
		// if (DaoFactory.get().getRoleDao().isEmpty(roleList) == true) {
		// roleList.add(new Role("USER", "do something"));
		// roleList.add(new Role("REGISTRATOR", "do something"));
		// roleList.add(new Role("ADMIN", "do something"));
		// DaoFactory.get().getRoleDao().add(new Role("USER", "do something"));
		// DaoFactory.get().getRoleDao()
		// .add(new Role("REGISTRATOR", "do something"));
		// DaoFactory.get().getRoleDao()
		// .add(new Role("ADMIN", "do something"));
		// }

		//
		transaction.commit();
		session.close();

	}
}
