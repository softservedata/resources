package org.registrator.community.components;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.registrator.community.entity.Role;
import org.registrator.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestDataInitializer {
	
	@Autowired
	EntityManagerFactory entityManagerFactory;
	
	public void init(){
		
		SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
		Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        Role role = new Role("USER","description");
        session.persist(role);
        
        session.persist(new User("user","pass",role,"Іван","Головатий","Сергійович","ivan@gmail.com","UNBLOCK"));
        
        transaction.commit();
	}

}
