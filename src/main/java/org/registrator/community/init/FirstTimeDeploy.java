package org.registrator.community.init;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.registrator.community.entity.Role;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FirstTimeDeploy {
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	public void init(){
		
		SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
		Session session = sessionFactory.openSession();
		long userCount = (long)session.createQuery("select count(u) from User u").uniqueResult();
		if(userCount==0){
			
	        Transaction roleTransaction = session.beginTransaction();
	        System.out.println("In the init and if");
	        Role roleUser = new Role(RoleType.USER,"description");
	        session.persist(roleUser);
	        
	        Role roleAdmin = new Role(RoleType.ADMIN,"description");
	        session.persist(roleAdmin);
	        
	        Role roleRegistrator = new Role(RoleType.REGISTRATOR,"description");
	        session.persist(roleRegistrator);
	        
	        Role roleCommissioner = new Role(RoleType.COMMISSIONER,"description");
	        session.persist(roleCommissioner);
	        
	        roleTransaction.commit();
	        Transaction userTransaction = session.beginTransaction();
	        
	        User admin = new User("admin","$2a$10$tkROwYPOXyBmKjarHW1rbuOOez2Z5gfkFCbUXUbOv1OY2wgekbZNC",
	        		roleAdmin,"Admin","Admin","Admin","admin@admin.com","UNBLOCK");
	        session.persist(admin);
	        
	        userTransaction.commit();
		}
	}
}
