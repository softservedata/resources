package org.registrator.community.dao.utils;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public final class HibernateUtil {
	private static SessionFactory sessionFactory;
	private static final Configuration CONFIGURATION = new Configuration();

	static {
		try {
			CONFIGURATION.configure();
			StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(CONFIGURATION.getProperties()).build();
			sessionFactory = CONFIGURATION.buildSessionFactory(serviceRegistry);
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private HibernateUtil() {
	}
	
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	} 
	

}
