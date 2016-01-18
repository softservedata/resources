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
        
        Role roleUser = new Role("USER","description");
        session.persist(roleUser);
        
        Role roleAdmin = new Role("ADMIN","description");
        session.persist(roleAdmin);
        
        Role roleRegistrator = new Role("REGISTRATOR","description");
        session.persist(roleRegistrator);
        
        session.persist(new User("user","user",roleUser,"Іван","Головатий","Сергійович","ivan@gmail.com","UNBLOCK"));
        session.persist(new User("admin","admin",roleAdmin,"Сергій","Головатий","Сергійович","sergey@gmail.com","UNBLOCK"));
        session.persist(new User("registrator","registrator",roleRegistrator,"Євген","Михалкевич","Сергійович","evgen@gmail.com","UNBLOCK"));
        
        transaction.commit();
	}

}
