package org.registrator.community.service.implementation;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.registrator.community.dao.daofactory.DaoFactory;
import org.registrator.community.dao.utils.HibernateUtil;
import org.registrator.community.dto.AddressDTO;
import org.registrator.community.dto.PassportDTO;
import org.registrator.community.dto.UserDTO;
import org.registrator.community.entity.Address;
import org.registrator.community.entity.PassportInfo;
import org.registrator.community.entity.User;
import org.registrator.community.service.interfaces.UnregisteredUserService;

public class UnregisteredUserServiceImpl implements UnregisteredUserService {

	
	//@Override
	public void addUser(UserDTO user) {
		
	//public void addUser(UserDTO user) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		User userEntity = new User(user.getLogin(),user.getPassword(),user.getRole(),
				user.getFirstName(),user.getLastName(),user.getMiddleName(),user.getEmail(),user.getStatus());

		DaoFactory.get().getUserDao().add(userEntity);	
		
//		Integer id = DaoFactory.get().getUserDao().add(userEntity);
//		userEntity.setUserId(id);
		
		PassportDTO passDTO = user.getPassport(); 
		AddressDTO addressDTO = user.getAddress(); 
		
		PassportInfo pass = new PassportInfo(userEntity, passDTO.getSeria(), 
				passDTO.getNumber(), passDTO.getPublished_by_data());
		DaoFactory.get().getPassportInfoDao().add(pass);
		
		Address address = new Address(userEntity, addressDTO.getPostcode(), addressDTO.getRegion(), addressDTO.getDistrict(),
				addressDTO.getCity(), addressDTO.getStreet(), addressDTO.getBuilding(), addressDTO.getFlat());
		
		DaoFactory.get().getAddressDao().add(address);
		
		transaction.commit();
		session.close();

		
	}
}
