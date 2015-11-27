package org.registrator.community.dao.app;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.registrator.community.dao.daofactory.DaoFactory;
import org.registrator.community.dao.utils.HibernateUtil;
import org.registrator.community.entity.ResourceType;

public class App {
	public static void main(String[] args) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		DaoFactory.get().getResourceTypeDao().add(new ResourceType("клімат"));
		tx.commit();

	}
}
