package org.registrator.community.service.implementation;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.registrator.community.dao.daofactory.DaoFactory;
import org.registrator.community.dao.utils.HibernateUtil;
import org.registrator.community.dto.AddressDTO;
import org.registrator.community.dto.InquiryListDTO;
import org.registrator.community.dto.PassportDTO;
import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.dto.UserDTO;
import org.registrator.community.entity.Address;
import org.registrator.community.entity.Inquiry;
import org.registrator.community.entity.PassportInfo;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.User;
import org.registrator.community.service.interfaces.SearchService;
import org.registrator.community.service.interfaces.UserService;

public class UserServiceImpl implements UserService, SearchService {

	/**
	 * A method for registered user to make inquiry for getting sertificate 
	 * about the resource with known resource id in the database.
	 */
	public void InquiryGetSertificate(InquiryListDTO inquiryListDTO){
		Session session = null;
		Transaction tr = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			tr = session.beginTransaction();
			
			Inquiry inquiryEntity = new Inquiry();
			inquiryEntity.setInquiryType(inquiryListDTO.getInquiryType());
			inquiryEntity.setDate(inquiryListDTO.getDate());			
			Integer userId = inquiryListDTO.getFromUserId();
			User user = DaoFactory.get().getUserDao().findById(userId);
			inquiryEntity.setUser(user);
			Integer registratorId = inquiryListDTO.getToUserId();
			User registrator = DaoFactory.get().getUserDao().findById(registratorId);
			inquiryEntity.setRegistrator(registrator);
			Integer resourceId = inquiryListDTO.getResourceId();
			Resource resource = DaoFactory.get().getResourceDao().findById(resourceId);
			inquiryEntity.setResource(resource);
			tr.commit();
			
		} catch(HibernateException he){
			if (tr != null){
				tr.rollback();
			}
		} finally {
			if ((session != null) && (session.isOpen())){
				session.close();
			}
		}		
	}
	
	
	
	
	public void InquiryInputResource(InquiryListDTO inquiryListDTO){
		Session session = null;
		Transaction tr = null;
		
		try{
			
			
			
		} catch(HibernateException he){
			if (tr != null){
				tr.rollback();
			}
		} finally {
			if ((session != null) && (session.isOpen())){
				session.close();
			}
		}		
	}


}
