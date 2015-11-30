package org.registrator.community.application;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.registrator.community.dao.ResourceDao;
import org.registrator.community.dao.utils.HibernateUtil;
import org.registrator.community.service.implementation.RegistratorServiceImpl;
import org.registrator.community.service.interfaces.RegistratorService;

public class ApplShowAllRes {

	public static void main(String[] args) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		
		List<ResourceDao> listRes = new ArrayList<ResourceDao>();
		RegistratorService rs = new RegistratorServiceImpl();
		listRes = rs.showAllResources();
		for(ResourceDao rtd: listRes){
			System.out.println(rtd);
		}
		transaction.commit();
		session.close();
	}

}
