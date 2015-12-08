package org.registrator.community.application;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.registrator.community.dao.daofactory.DaoFactory;
import org.registrator.community.dao.utils.HibernateUtil;
import org.registrator.community.entity.Address;
import org.registrator.community.entity.Area;
import org.registrator.community.entity.DiscreteParameter;
import org.registrator.community.entity.LinearParameter;
import org.registrator.community.entity.PassportInfo;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.entity.Role;
import org.registrator.community.entity.ResourceDiscreteValue;
import org.registrator.community.entity.ResourceLinearValue;
import org.registrator.community.entity.Tome;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.RoleType;

public class CreateTableInDB {

	public void createTables() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		// Check if table roles is empty (database is empty)
		if (DaoFactory.get().getRoleDao().isEmpty() == 0) {
			DaoFactory.get().getRoleDao().add(new Role("Admin", "description"));
			DaoFactory.get().getRoleDao()
					.add(new Role("Registrator", "description"));
			DaoFactory.get().getRoleDao().add(new Role("User", "description"));
		}
		importData();
		transaction.commit();
		session.close();

	}

	public static void importData() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		// creating three User (with adress and passrort_data) with different roles
		Role role = (Role) session.createCriteria(Role.class)
                .add(Restrictions.eq("type", RoleType.ADMIN)).uniqueResult();	
		User admin = new User("oleks", "pass1", role, "Олександр", 
				"Архилюк", "Олександрович", "oless.@gmail.com", "UNBLOCK");
		DaoFactory.get().getUserDao().add(admin);
		DaoFactory.get().getAddressDao().add(new Address(admin, "79026", "Львівська", "Львів", "Пастернака", "35")); 
		DaoFactory.get().getPassportInfoDao().add(new PassportInfo(admin, 
				"КС", 2234, "Published by...data ..."));

	
		role = (Role) session.createCriteria(Role.class)
				.add(Restrictions.eq("type", RoleType.REGISTRATOR)).uniqueResult();
		User registrator = new User("petro", "pass2", role, "Петро", 
				"Петренко", "Петрович", "petro.@gmail.com", "UNBLOCK");
		DaoFactory.get().getUserDao().add(registrator);
		DaoFactory.get().getAddressDao().add(new Address(registrator, "29000", "Хмельницька", null, 
				"Хмельницький", "Героїв Майдану", "17", "17")); 
		DaoFactory.get().getPassportInfoDao().add(new PassportInfo(registrator, "КК",
				123456, "Хмельницьким МВ УМВС України в Хмельницький області 01 січня 1997 року"));
		// adding the tome for registrator
		Tome tome = new Tome(registrator, "12345");
		DaoFactory.get().getTomeDao().add(tome);
		
		
					  
		role = (Role) session.createCriteria(Role.class)
                .add(Restrictions.eq("type", RoleType.USER)).uniqueResult();
		User user = new User("ivan", "pass3", role, "Юрій", 
				"Іванов", "Іванович", "ivan.@gmail.com", "UNBLOCK");
		DaoFactory.get().getUserDao().add(user);
		DaoFactory.get().getAddressDao().add(new Address(user, "353567", "Львівська", 
				"Стрийський", "Стрий", "Героїв Майдану", "30", "0"));
		DaoFactory.get().getPassportInfoDao().add(new PassportInfo(user, "КК", 
				123456, "Стрийський МВ УМВС України в Львівській області 01 січня 1965 року"));
		
		

		// adding default resource types: land resource, radiofrequency and parameters
		ResourceType land = new ResourceType("земельний");
		DaoFactory.get().getResourceTypeDao().add(land);
		DiscreteParameter perimeter = new DiscreteParameter(land,"периметер","м");
		DiscreteParameter square = new DiscreteParameter(land,"площа","га");
		DaoFactory.get().getDiscreteValueDao().add(perimeter);
		DaoFactory.get().getDiscreteValueDao().add(square);
		
		ResourceType radiofrequency = new ResourceType("радіочастотний");
		DaoFactory.get().getResourceTypeDao().add(radiofrequency);
		LinearParameter bandwidth1 = new LinearParameter(radiofrequency,"cмуга радіочастот", "МГц");
		LinearParameter bandwidth2 = new LinearParameter(radiofrequency,"широта діапазону", "кГц");
		LinearParameter bandwidth3 = new LinearParameter(radiofrequency,"радіус дії", "км");
		
		
		
		DiscreteParameter power = new DiscreteParameter(radiofrequency,"потужність", "мВт");
		DiscreteParameter tension = new DiscreteParameter(radiofrequency,"напруженість", "мВт");
		DaoFactory.get().getLineSizeDao().add(bandwidth1);
		DaoFactory.get().getLineSizeDao().add(bandwidth2);
		DaoFactory.get().getLineSizeDao().add(bandwidth3);
		DaoFactory.get().getDiscreteValueDao().add(power);
		DaoFactory.get().getDiscreteValueDao().add(tension);
		
		// adding default resources types: land resource and radiofrequency
			
		Resource forest =  new Resource(land, "123567", "ліс", registrator, 
				new Date(), "ACTIVE", tome, "паспорт...");
		DaoFactory.get().getResourceDao().add(forest);
	
		DaoFactory.get().getAreaDao().add(new Area(forest, 1, 49.86378, 24.02591));		
		DaoFactory.get().getAreaDao().add(new Area(forest, 2, 49.86372, 24.02599));		
		DaoFactory.get().getAreaDao().add(new Area(forest, 3, 49.86362, 25.02599));	
		DaoFactory.get().getAreaDao().add(new Area(forest, 4, 50.86362, 24.07699));
		DaoFactory.get().getStoreOfDiscreteValuesDao().add(new ResourceDiscreteValue(forest, perimeter, 0.0868));		
		DaoFactory.get().getStoreOfDiscreteValuesDao().add(new ResourceDiscreteValue(forest, square, 127.7));		
				
		
		Resource radiofreq =  new Resource(radiofrequency, "123555", "радіочастоти", registrator,
		new Date(), "ACTIVE", tome, "Паспорт громадянина україни...");
		DaoFactory.get().getResourceDao().add(radiofreq);
	
		DaoFactory.get().getAreaDao().add(new Area(radiofreq, 1, 53.876, 30.01));		
		DaoFactory.get().getAreaDao().add(new Area(radiofreq, 2, 63.55, 33.76));		
		DaoFactory.get().getAreaDao().add(new Area(radiofreq, 3, 49.3552, 43.54));	
		DaoFactory.get().getAreaDao().add(new Area(radiofreq, 4, 50.345, 24.07699));
		DaoFactory.get().getStoreOfLineSizesDao().add(new ResourceLinearValue(radiofreq, bandwidth1, 2400d, 2483.5));
		DaoFactory.get().getStoreOfLineSizesDao().add(new ResourceLinearValue(radiofreq, bandwidth2, 5150d, 5350d));
		DaoFactory.get().getStoreOfLineSizesDao().add(new ResourceLinearValue(radiofreq, bandwidth1, 2500d ,2700d));
		DaoFactory.get().getStoreOfLineSizesDao().add(new ResourceLinearValue(radiofreq, bandwidth3, 1100d, 2483.5));
		DaoFactory.get().getStoreOfLineSizesDao().add(new ResourceLinearValue(radiofreq, bandwidth2, 1110d, 5350d));
		DaoFactory.get().getStoreOfLineSizesDao().add(new ResourceLinearValue(radiofreq, bandwidth1, 9999d ,2100d));
		
		DaoFactory.get().getStoreOfDiscreteValuesDao().add(new ResourceDiscreteValue(radiofreq, power, 100d));		
		DaoFactory.get().getStoreOfDiscreteValuesDao().add(new ResourceDiscreteValue(radiofreq, power, 500.55));		
		DaoFactory.get().getStoreOfDiscreteValuesDao().add(new ResourceDiscreteValue(radiofreq, power, 23.54));		
		DaoFactory.get().getStoreOfDiscreteValuesDao().add(new ResourceDiscreteValue(radiofreq, tension, 200d));

		Resource radiofreq2 =  new Resource(radiofrequency, "111111", "суперрадіочастоти", registrator,
				new Date(), "ACTIVE", tome, "Посвідчення водія ...");
				DaoFactory.get().getResourceDao().add(radiofreq2);
			
				DaoFactory.get().getAreaDao().add(new Area(radiofreq2, 1, 53.876, 30.01));		
				DaoFactory.get().getAreaDao().add(new Area(radiofreq2, 2, 63.55, 33.76));		
				DaoFactory.get().getAreaDao().add(new Area(radiofreq2, 3, 49.3552, 43.54));	
				DaoFactory.get().getAreaDao().add(new Area(radiofreq2, 4, 50.345, 24.07699));
				DaoFactory.get().getStoreOfLineSizesDao().add(new ResourceLinearValue(radiofreq2, bandwidth1, 20d, 55.5));
				DaoFactory.get().getStoreOfLineSizesDao().add(new ResourceLinearValue(radiofreq2, bandwidth2, 11d, 55d));
				DaoFactory.get().getStoreOfLineSizesDao().add(new ResourceLinearValue(radiofreq2, bandwidth3, 99d ,55d));
				DaoFactory.get().getStoreOfLineSizesDao().add(new ResourceLinearValue(radiofreq2, bandwidth1, 1212d, 5483.5));
				DaoFactory.get().getStoreOfLineSizesDao().add(new ResourceLinearValue(radiofreq2, bandwidth2, 1000d, 5550d));
				DaoFactory.get().getStoreOfLineSizesDao().add(new ResourceLinearValue(radiofreq2, bandwidth1, 1111d ,200d));
				
				DaoFactory.get().getStoreOfDiscreteValuesDao().add(new ResourceDiscreteValue(radiofreq2, power, 91d));		
				DaoFactory.get().getStoreOfDiscreteValuesDao().add(new ResourceDiscreteValue(radiofreq2, power, 51.55));		
				DaoFactory.get().getStoreOfDiscreteValuesDao().add(new ResourceDiscreteValue(radiofreq2, power, 230.54));		
				DaoFactory.get().getStoreOfDiscreteValuesDao().add(new ResourceDiscreteValue(radiofreq2, tension, 1d));
				DaoFactory.get().getStoreOfDiscreteValuesDao().add(new ResourceDiscreteValue(radiofreq2, tension, 1000d));

	}

	// Plaese don't use
	public void addSeveralUsers() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		@SuppressWarnings("unused")
		Transaction transaction = session.beginTransaction();

		// creating three User (with adress and passrort_data) with different
		// roles
		Role role = (Role) session.createCriteria(Role.class)
				.add(Restrictions.eq("name", RoleType.ADMIN)).uniqueResult();
		User admin = new User("achyp14", "password", role, "Андрій", "Чипурко",
				"Андрійович", "achyp14@gmail.com", "UNBLOCK");
		DaoFactory.get().getUserDao().add(admin);
		DaoFactory
				.get()
				.getAddressDao()
				.add(new Address(admin, "29000", "Львівська область",
						"Личаківський район", "Львівська область",
						"вул. Некрасова", "57", "75"));
		DaoFactory.get().getPassportInfoDao()
				.add(new PassportInfo(admin, "КС", 1111, "Видано кимось"));

		role = (Role) session.createCriteria(Role.class)
				.add(Restrictions.eq("name", RoleType.REGISTRATOR)).uniqueResult();
		User registrator = new User("roman", "password1", role, "Роман",
				"Дмитренко", "Юрійович", "roman.@gmail.com", "UNBLOCK");
		DaoFactory.get().getUserDao().add(registrator);
		DaoFactory
				.get()
				.getAddressDao()
				.add(new Address(admin, "29756", "Львівська область",
						"Франківський район", "Львівська область",
						"вул. Тарнавського", "112", "21"));
		DaoFactory
				.get()
				.getPassportInfoDao()
				.add(new PassportInfo(registrator, "КК", 2222, "Видано кимось"));
		// adding the tome for registrator
		Tome tome = new Tome(registrator, "12345");
		DaoFactory.get().getTomeDao().add(tome);

		role = (Role) session.createCriteria(Role.class)
				.add(Restrictions.eq("name", RoleType.USER)).uniqueResult();
		User user = new User("yura", "password2", role, "Юрій", "Убийвовк",
				"Степанович", "yura.@gmail.com", "UNBLOCK");
		DaoFactory.get().getUserDao().add(user);
		DaoFactory
				.get()
				.getAddressDao()
				.add(new Address(user, "291231", "Львівська область",
						"Залізничний район", "Львівська область",
						"вул. Залізнична", "43", "54"));
		DaoFactory.get().getPassportInfoDao()
				.add(new PassportInfo(user, "КК", 3333, "Видано кимось"));

	}

	// For myself
	public void Fill() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		// Check if table roles is empty (database is empty)
		if (DaoFactory.get().getRoleDao().isEmpty() == 0) {
			DaoFactory.get().getRoleDao().add(new Role("Admin", "description"));
			DaoFactory.get().getRoleDao()
					.add(new Role("Registrator", "description"));
			DaoFactory.get().getRoleDao().add(new Role("User", "description"));
		}
		transaction.commit();
		session.close();

	}

}
