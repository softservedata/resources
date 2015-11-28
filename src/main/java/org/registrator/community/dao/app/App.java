package org.registrator.community.dao.app;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.registrator.community.dao.daofactory.DaoFactory;
import org.registrator.community.dao.utils.HibernateUtil;
import org.registrator.community.dto.AddressDTO;
import org.registrator.community.dto.DiscreteParameterDTO;
import org.registrator.community.dto.LinearParameterDTO;
import org.registrator.community.dto.PassportDTO;
import org.registrator.community.dto.ResourceTypeDTO;
import org.registrator.community.dto.UserDTO;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.entity.Role;
import org.registrator.community.service.implementation.RegistratorServiceImpl;
import org.registrator.community.service.implementation.UserServiceImpl;
import org.registrator.community.service.interfaces.RegistratorService;
import org.registrator.community.service.interfaces.UserService;

public class App {
	public static void main(String[] args) {

		//DaoFactory.get().getResourceTypeDao().add(new ResourceType("�����"));
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		
		// Save new User in Database
/*		Role role = new Role("Admin","description");
		DaoFactory.get().getRoleDao().add(role);
		//session.save(role);
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

		passport.setPublished_by_data("Published_by_data");
		passport.setSeria("Seria");
		passport.setNumber(2234);
		passportList.add(passport);
		
		UserService user = new UserServiceImpl();
		UserDTO userDTO = new UserDTO();
		userDTO.setFirstName("FirstName");
		userDTO.setLastName("LastName");
		userDTO.setMiddleName("MiddleName");
		userDTO.setLogin("login");
		userDTO.setPassword("password");
		userDTO.setStatus("block");
		userDTO.setEmail("2email.com");
		userDTO.setAddress(addressList);
		userDTO.setPassport(passportList);
		userDTO.setRole(role);
		
		user.addUser(userDTO);*/
		
		// Save new ResourceType in Database******
		
		RegistratorService registrator = new RegistratorServiceImpl();
		ResourceTypeDTO resourceTypeDTO = new ResourceTypeDTO();
		resourceTypeDTO.setTypeName("радіочастота");
		List<LinearParameterDTO> linearParameterDTOs = new ArrayList<LinearParameterDTO>();
		linearParameterDTOs.add(new LinearParameterDTO("cмуга радіочастот", "МГц"));
		List<DiscreteParameterDTO> discreteParameterDTOs = new ArrayList<DiscreteParameterDTO>();
		discreteParameterDTOs.add(new DiscreteParameterDTO("потужність", "мВт"));
		discreteParameterDTOs.add(new DiscreteParameterDTO("напруженість", "мВт"));
		resourceTypeDTO.setLinearParameters(linearParameterDTOs);
		resourceTypeDTO.setDiscreteParameters(discreteParameterDTOs);
		registrator.addResourseType(resourceTypeDTO);
		
		
		transaction.commit();
		session.close();
		
	
	}
}
