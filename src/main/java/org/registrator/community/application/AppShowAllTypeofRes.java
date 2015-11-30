package org.registrator.community.application;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.registrator.community.dao.ResourceTypeDao;
import org.registrator.community.dao.utils.HibernateUtil;
import org.registrator.community.service.implementation.RegistratorServiceImpl;
import org.registrator.community.service.interfaces.RegistratorService;

public class AppShowAllTypeofRes {

public static void main(String[] args) {
	
	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction transaction = session.beginTransaction();
	
	List<ResourceTypeDao> listResType = new ArrayList<ResourceTypeDao>();
	RegistratorService rs = new RegistratorServiceImpl();
	listResType = rs.showAllTypeOfResources();
	for(ResourceTypeDao rtd: listResType){
		System.out.println(rtd);
	}
	transaction.commit();
	session.close();
}

}



