package org.registrator.community.application;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.registrator.community.dao.ResourceDao;
import org.registrator.community.dao.utils.HibernateUtil;
import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.service.implementation.RegistratorServiceImpl;
import org.registrator.community.service.interfaces.RegistratorService;

public class ApplShowAllRes {

	public static void main(String[] args) {
		
		List<ResourceDTO> listRes = new ArrayList<ResourceDTO>();
		RegistratorService rs = new RegistratorServiceImpl();
		listRes = rs.showAllResources();
		for(ResourceDTO resourceDTO: listRes){
			System.out.println(resourceDTO);
		}
		//listRes.forEach(x -> System.out.println(x));
	/*	transaction.commit();
		session.close();*/
		
		/*listRes
		.stream()
		.sorted()
	    .forEach(System.out::println);*/
	}

}
