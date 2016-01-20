package org.registrator.community.components;

import java.util.Date;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.registrator.community.entity.Inquiry;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.entity.Role;
import org.registrator.community.entity.Tome;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.ResourceStatus;
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
        
        User user = new User("user","user",roleUser,"Іван","Головатий","Сергійович","ivan@gmail.com","UNBLOCK");
        session.persist(user);
        session.persist(new User("admin","admin",roleAdmin,"Сергій","Головатий","Сергійович","sergey@gmail.com","UNBLOCK"));
        
        User registrator = new User("registrator","registrator",roleRegistrator,"Євген","Михалкевич","Сергійович","evgen@gmail.com","UNBLOCK");
        session.persist(registrator);
        
        ResourceType land = new ResourceType("земельний");
        session.persist(land);  
        
        Tome tome = new Tome(registrator, "12345");
        session.persist(tome);
        
        Resource resource = new Resource(land, "111111", "ліс", registrator, new Date(), "active", tome, "підстава на внесення");
        session.persist(resource);
        
        Inquiry inquiry = new Inquiry("OUTPUT", new Date(), user, registrator, resource);
        session.persist(inquiry);
        
        transaction.commit();
	}

}
