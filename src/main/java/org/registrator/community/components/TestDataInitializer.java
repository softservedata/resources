package org.registrator.community.components;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.registrator.community.entity.Role;
import org.registrator.community.entity.RoleTypeEx;
import org.registrator.community.entity.User;
import org.registrator.community.entity.UserStatusEx;
import org.registrator.community.enumeration.RoleType;
import org.registrator.community.enumeration.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestDataInitializer {
	
	@Autowired
	EntityManagerFactory entityManagerFactory;
	
	public void init(){
		
		SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
		Session session = sessionFactory.openSession();
        Transaction transaction1 = session.beginTransaction();
        
        UserStatusEx userStatusEx1 = new UserStatusEx();
        userStatusEx1.setCode("INACTIVE");
        
        UserStatusEx userStatusEx2 = new UserStatusEx();
        userStatusEx2.setCode("BLOCK");
        UserStatusEx userStatusEx3 = new UserStatusEx();
        userStatusEx3.setCode("UNBLOCK");
      
        
        session.persist(userStatusEx1);
        session.persist(userStatusEx2);
        session.persist(userStatusEx3);
        transaction1.commit();

        
        Transaction transaction2 = session.beginTransaction();
        RoleTypeEx roleTypeEx1 = new RoleTypeEx();
        roleTypeEx1.setCode("ADMIN");
        RoleTypeEx roleTypeEx2 = new RoleTypeEx();
        roleTypeEx2.setCode("REGISTRATOR");
        RoleTypeEx roleTypeEx3 = new RoleTypeEx();
        roleTypeEx3.setCode("USER");
     
      session.persist(roleTypeEx1);
        session.persist(roleTypeEx2);
        session.persist(roleTypeEx3);
        transaction2.commit();
       
        Transaction transaction3 = session.beginTransaction();
        Role r1 = new Role(RoleType.ADMIN, "this is admin role");
        
        Role r2 = new Role(RoleType.REGISTRATOR, "this is registrator role");
                Role r3 = new Role(RoleType.USER, "this is user role");

        session.persist(r1);
        session.persist(r2);
        session.persist(r3);
        transaction3.commit();
        
        Transaction transaction4 = session.beginTransaction();
        session.persist(new User("user","user",r3,"Іван","Головатий","Сергійович","ivan@gmail.com",UserStatus.UNBLOCK));
        session.persist(new User("admin","admin",r1,"Сергій","Головатий","Сергійович","sergey@gmail.com",UserStatus.UNBLOCK));
        session.persist(new User("registrator","registrator",r2,"Євген","Михалкевич","Сергійович","evgen@gmail.com",UserStatus.UNBLOCK));
        transaction4.commit();
       
        
        
        
        
        
        
        
        
        
        
        /*session.persist(new User("user","user",roleUser,"Іван","Головатий","Сергійович","ivan@gmail.com","UNBLOCK"));
        session.persist(new User("admin","admin",roleAdmin,"Сергій","Головатий","Сергійович","sergey@gmail.com","UNBLOCK"));
        session.persist(new User("registrator","registrator",roleRegistrator,"Євген","Михалкевич","Сергійович","evgen@gmail.com","UNBLOCK"));*/
        
        
	}

}
