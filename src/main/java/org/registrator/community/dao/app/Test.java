package org.registrator.community.dao.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.registrator.community.dao.RoleDao;
import org.registrator.community.dao.UserDao;
import org.registrator.community.dao.daofactory.DaoFactory;
import org.registrator.community.dao.utils.HibernateUtil;
import org.registrator.community.dto.AddressDTO;
import org.registrator.community.dto.DiscreteParameterDTO;
import org.registrator.community.dto.LinearParameterDTO;
import org.registrator.community.dto.PassportDTO;
import org.registrator.community.dto.PointAreaDTO;
import org.registrator.community.dto.PoligonAreaDTO;
import org.registrator.community.dto.ResourceAreaDTO;
import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.entity.*;
import org.registrator.community.dto.ResourceDiscreteDTO;
import org.registrator.community.dto.ResourceLinearDTO;
import org.registrator.community.dto.ResourceTypeDTO;
import org.registrator.community.dto.SegmentLinearDTO;
import org.registrator.community.dto.UserDTO;
import org.registrator.community.service.implementation.RegistratorServiceImpl;
import org.registrator.community.service.implementation.UserServiceImpl;
import org.registrator.community.service.interfaces.RegistratorService;
import org.registrator.community.service.interfaces.UserService;

public class Test {

	public void addRole(){
		Session session = null;
		Transaction tr = null;
		
		try{
			RoleDao  roleDao = DaoFactory.get().getRoleDao();
			Role role = new Role("USER","can do inquiries");			
			roleDao.add(role);
			role = new Role("REGISTRATOR","can add resource");			
			roleDao.add(role);
			role = new Role("ADMIN","can add user");			
			roleDao.add(role);

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
	
	public void addUser(){
		Session session = null;
		Transaction tr = null;
		
		try{
			UserDao userDao = DaoFactory.get().getUserDao();
			

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
	
	public void addResouceType(){
		
	}
	
	public void addResource(){
		
	}
	
	public void addInquiryInpupResouce(){
		
	}
	
	public void addInquiryGetSertificate(){
		
	}
	public static void main(String[] args) {
		

	}

}
