package org.registrator.community.service.implementation;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.registrator.community.dao.daofactory.DaoFactory;
import org.registrator.community.dao.utils.HibernateUtil;
import org.registrator.community.dto.DiscreteParameterDTO;
import org.registrator.community.dto.LinearParameterDTO;
import org.registrator.community.dto.ResourceTypeDTO;
import org.registrator.community.entity.DiscreteValue;
import org.registrator.community.entity.LineSize;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.service.interfaces.RegistratorService;;

public class RegistratorServiceImpl implements RegistratorService{

	@Override
	public void addResourseType(ResourceTypeDTO resourceTypeDTO) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		ResourceType resourceEntity = new ResourceType();
		resourceEntity.setTypeName(resourceTypeDTO.getTypeName());
		DaoFactory.get().getResourceTypeDao().add(resourceEntity);
		
		List<LinearParameterDTO> linearParameters = resourceTypeDTO.getLinearParameters();
		List<DiscreteParameterDTO> discreteParameters = resourceTypeDTO.getDiscreteParameters();
		

		for (int i = 0; i < linearParameters.size(); i++) {
			LineSize lineSizeEntyty = new LineSize();
			LinearParameterDTO linearParameterDTO = linearParameters.get(i);
			lineSizeEntyty.setResourceType(resourceEntity);
			lineSizeEntyty.setDescription(linearParameterDTO.getDescription());
			DaoFactory.get().getLineSizeDao().add(lineSizeEntyty);			
		}
		
		for (int i = 0; i < discreteParameters.size(); i++) {
			DiscreteValue discreteValueEntity = new DiscreteValue();
			DiscreteParameterDTO discreteParameterDTO = discreteParameters.get(i);
			discreteValueEntity.setResourceType(resourceEntity);
			discreteValueEntity.setDescription(discreteParameterDTO.getDescription());
			DaoFactory.get().getDiscreteValueDao().add(discreteValueEntity);			
		}
 
		/*	for (String linearParameterDTO : linearParameters) {
			LineSize lineSizeEntyty = new LineSize();
			lineSizeEntyty.setResourceType(resourceEntity);
			lineSizeEntyty.setDescription(linearParameterDTO.getDescription());
			DaoFactory.get().getLineSizeDao().add(lineSizeEntyty);	
			
		}*/
		
		/*
		User userEntity = new User(user.getLogin(),user.getPassword(),user.getRole(),
		user.getFirstName(),user.getLastName(),user.getMiddleName(),user.getEmail(),user.getStatus());

		DaoFactory.get().getUserDao().add(userEntity);	
		
//		Integer id = DaoFactory.get().getUserDao().add(userEntity);
//		userEntity.setUserId(id);
		
		PassportDTO passDTO = user.getPassport().get(user.getPassport().size()-1); 
		AddressDTO addressDTO = user.getAddress().get(user.getAddress().size()-1); 
		
		PassportInfo pass = new PassportInfo(userEntity, passDTO.getSeria(), 
				passDTO.getNumber(), passDTO.getPublished_by_data());
		DaoFactory.get().getPassportInfoDao().add(pass);
		
		Address address = new Address(userEntity, addressDTO.getPostcode(), addressDTO.getRegion(), addressDTO.getDistrict(),
				addressDTO.getCity(), addressDTO.getStreet(), addressDTO.getBuilding(), addressDTO.getFlat());
		
		DaoFactory.get().getAddressDao().add(address);*/
		
		transaction.commit();
		session.close();
		
		
	}

}
