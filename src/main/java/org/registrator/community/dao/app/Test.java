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
import org.registrator.community.dto.InquiryListDTO;
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
			Role role = DaoFactory.get().getRoleDao().findById(3);
			DaoFactory.get().getUserDao().add(new User("login11", "password11", role, "Taras", "Rogalya", "Ivanovich", "tar@gmail.com", "UNBLOCK"));
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
	
		public void addInquiryInpupResouce(){
		
		ResourceTypeDTO resourceTypeDTO = new RegistratorServiceImpl().showAllTypeOfResources().get(0);
		PointAreaDTO pointAreaDTO1 = new PointAreaDTO(1, 45, 10, 10, 46, 10, 10);
		PointAreaDTO pointAreaDTO2 = new PointAreaDTO(2, 60, 10, 10, 46, 10, 10);
		PointAreaDTO pointAreaDTO3 = new PointAreaDTO(3, 60, 10, 10, 70, 10, 10);
		PointAreaDTO pointAreaDTO4 = new PointAreaDTO(4, 45, 10, 10, 70, 10, 10);
		List<PointAreaDTO> listPoints = new ArrayList<PointAreaDTO>();
		listPoints.add(pointAreaDTO1);
		listPoints.add(pointAreaDTO2);
		listPoints.add(pointAreaDTO3);
		listPoints.add(pointAreaDTO4);		
		PoligonAreaDTO poligonAreaDTO = new PoligonAreaDTO();
		poligonAreaDTO.setPoints(listPoints);
		ResourceAreaDTO resourceAreaDTO = new ResourceAreaDTO();
		List<PoligonAreaDTO> listPoligons = new ArrayList<PoligonAreaDTO>();
		resourceAreaDTO.setPoligons(listPoligons);
		
		List<ResourceDiscreteDTO> perimiter = new ArrayList<>();
		
		List<ResourceDiscreteDTO> ploshcha = new ArrayList<>();
		
		ResourceDTO resource = new ResourceDTO(resourceTypeDTO, "111111", "land", "Петро", new Date(), ResourceStatus.UNCHECKED, "passport AA65123", "12345", resourceAreaDTO, null, null);
		
		InquiryListDTO inquiryListDTO = new InquiryListDTO("INPUT", new Date(), "ivan", "petro", resource);
		new UserServiceImpl().InquiryGetSertificate(inquiryListDTO);
	}
	
	public void addInquiryGetSertificate(){
		ResourceDTO resource = new ResourceDTO();
		resource.setIdentifier("123567");
		InquiryListDTO inquiryListDTO = new InquiryListDTO("OUTPUT", new Date(), "ivan", "petro", resource);
		new UserServiceImpl().InquiryGetSertificate(inquiryListDTO);
	}
	public static void main(String[] args) {
		Test test = new Test();
		test.addInquiryGetSertificate();
		//test.addUser();
		//test.addInquiryInpupResouce();
	}

}
