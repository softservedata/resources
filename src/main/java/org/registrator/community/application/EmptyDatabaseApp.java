package org.registrator.community.application;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.registrator.community.dao.daofactory.DaoFactory;
import org.registrator.community.dao.utils.HibernateUtil;
import org.registrator.community.entity.Address;
import org.registrator.community.entity.Area;
import org.registrator.community.entity.DiscreteValue;
import org.registrator.community.entity.LineSize;
import org.registrator.community.entity.Name;
import org.registrator.community.entity.PassportInfo;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceStatus;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.entity.Role;
import org.registrator.community.entity.StoreOfDiscreteValues;
import org.registrator.community.entity.StoreOfLineSizes;
import org.registrator.community.entity.Tome;
import org.registrator.community.entity.User;

public class EmptyDatabaseApp {
	
	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		
		// Check if table roles is empty (database is empty)
		if (DaoFactory.get().getRoleDao().isEmpty()==0) {
			DaoFactory.get().getRoleDao().add(new Role("Admin","description"));
			DaoFactory.get().getRoleDao().add(new Role("Registrator","description"));
			DaoFactory.get().getRoleDao().add(new Role("User","description"));		
		}		
		importData();
		transaction.commit();
		session.close();		
		
		
	}
	
	static void importData() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		// creating three User (with adress and passrort_data) with different roles
		Role role = (Role) session.createCriteria(Role.class)
                .add(Restrictions.eq("name", Name.ADMIN)).uniqueResult();	
		User admin = new User("oleks", "pass1", role, "Олександр", 
				"Архилюк", "Олександрович", "oless.@gmail.com", "UNBLOCK");
		DaoFactory.get().getUserDao().add(admin);
		DaoFactory.get().getAddressDao().add(new Address(admin, "79026", "Львівська", "Львів", "Пастернака", "35")); 
		DaoFactory.get().getPassportInfoDao().add(new PassportInfo(admin, 
				"КС", 2234, "Published by...data ..."));

	
		role = (Role) session.createCriteria(Role.class)
				.add(Restrictions.eq("name", Name.REGISTRATOR)).uniqueResult();
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
                .add(Restrictions.eq("name", Name.USER)).uniqueResult();
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
		DiscreteValue perimeter = new DiscreteValue(land,"периметер","м");
		DiscreteValue square = new DiscreteValue(land,"площа","га");
		DaoFactory.get().getDiscreteValueDao().add(perimeter);
		DaoFactory.get().getDiscreteValueDao().add(square);
		
		ResourceType radiofrequency = new ResourceType("радіочастотний");
		DaoFactory.get().getResourceTypeDao().add(radiofrequency);
		LineSize bandwidth1 = new LineSize(radiofrequency,"cмуга радіочастот", "МГц");
		LineSize bandwidth2 = new LineSize(radiofrequency,"широта діапазону", "кГц");
		LineSize bandwidth3 = new LineSize(radiofrequency,"радіус дії", "км");
		
		
		
		DiscreteValue power = new DiscreteValue(radiofrequency,"потужність", "мВт");
		DiscreteValue tension = new DiscreteValue(radiofrequency,"напруженість", "мВт");
		DaoFactory.get().getLineSizeDao().add(bandwidth1);
		DaoFactory.get().getLineSizeDao().add(bandwidth2);
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
		DaoFactory.get().getStoreOfDiscreteValuesDao().add(new StoreOfDiscreteValues(forest, perimeter, 0.0868));		
		DaoFactory.get().getStoreOfDiscreteValuesDao().add(new StoreOfDiscreteValues(forest, square, 127.7));		
				
		
		Resource radiofreq =  new Resource(radiofrequency, "123555", "радіочастоти", registrator,
		new Date(), "ACTIVE", tome, "Паспорт громадянина україни...");
		DaoFactory.get().getResourceDao().add(radiofreq);
	
		DaoFactory.get().getAreaDao().add(new Area(radiofreq, 1, 53.876, 30.01));		
		DaoFactory.get().getAreaDao().add(new Area(radiofreq, 2, 63.55, 33.76));		
		DaoFactory.get().getAreaDao().add(new Area(radiofreq, 3, 49.3552, 43.54));	
		DaoFactory.get().getAreaDao().add(new Area(radiofreq, 4, 50.345, 24.07699));
		DaoFactory.get().getStoreOfLineSizesDao().add(new StoreOfLineSizes(radiofreq, bandwidth1, 2400d, 2483.5));
		DaoFactory.get().getStoreOfLineSizesDao().add(new StoreOfLineSizes(radiofreq, bandwidth2, 5150d, 5350d));
		DaoFactory.get().getStoreOfLineSizesDao().add(new StoreOfLineSizes(radiofreq, bandwidth1, 2500d ,2700d));
		DaoFactory.get().getStoreOfLineSizesDao().add(new StoreOfLineSizes(radiofreq, bandwidth3, 1100d, 2483.5));
		DaoFactory.get().getStoreOfLineSizesDao().add(new StoreOfLineSizes(radiofreq, bandwidth2, 1110d, 5350d));
		DaoFactory.get().getStoreOfLineSizesDao().add(new StoreOfLineSizes(radiofreq, bandwidth1, 9999d ,2100d));
		
		DaoFactory.get().getStoreOfDiscreteValuesDao().add(new StoreOfDiscreteValues(radiofreq, power, 100d));		
		DaoFactory.get().getStoreOfDiscreteValuesDao().add(new StoreOfDiscreteValues(radiofreq, power, 500.55));		
		DaoFactory.get().getStoreOfDiscreteValuesDao().add(new StoreOfDiscreteValues(radiofreq, power, 23.54));		
		DaoFactory.get().getStoreOfDiscreteValuesDao().add(new StoreOfDiscreteValues(radiofreq, tension, 200d));

		Resource radiofreq2 =  new Resource(radiofrequency, "111111", "суперрадіочастоти", registrator,
				new Date(), "ACTIVE", tome, "Посвідчення водія ...");
				DaoFactory.get().getResourceDao().add(radiofreq);
			
				DaoFactory.get().getAreaDao().add(new Area(radiofreq2, 1, 53.876, 30.01));		
				DaoFactory.get().getAreaDao().add(new Area(radiofreq2, 2, 63.55, 33.76));		
				DaoFactory.get().getAreaDao().add(new Area(radiofreq2, 3, 49.3552, 43.54));	
				DaoFactory.get().getAreaDao().add(new Area(radiofreq2, 4, 50.345, 24.07699));
				DaoFactory.get().getStoreOfLineSizesDao().add(new StoreOfLineSizes(radiofreq2, bandwidth1, 20d, 55.5));
				DaoFactory.get().getStoreOfLineSizesDao().add(new StoreOfLineSizes(radiofreq2, bandwidth2, 11d, 55d));
				DaoFactory.get().getStoreOfLineSizesDao().add(new StoreOfLineSizes(radiofreq2, bandwidth3, 99d ,55d));
				DaoFactory.get().getStoreOfLineSizesDao().add(new StoreOfLineSizes(radiofreq2, bandwidth1, 1212d, 5483.5));
				DaoFactory.get().getStoreOfLineSizesDao().add(new StoreOfLineSizes(radiofreq2, bandwidth2, 1000d, 5550d));
				DaoFactory.get().getStoreOfLineSizesDao().add(new StoreOfLineSizes(radiofreq2, bandwidth1, 1111d ,200d));
				
				DaoFactory.get().getStoreOfDiscreteValuesDao().add(new StoreOfDiscreteValues(radiofreq2, power, 91d));		
				DaoFactory.get().getStoreOfDiscreteValuesDao().add(new StoreOfDiscreteValues(radiofreq2, power, 51.55));		
				DaoFactory.get().getStoreOfDiscreteValuesDao().add(new StoreOfDiscreteValues(radiofreq2, power, 230.54));		
				DaoFactory.get().getStoreOfDiscreteValuesDao().add(new StoreOfDiscreteValues(radiofreq2, tension, 1d));
				DaoFactory.get().getStoreOfDiscreteValuesDao().add(new StoreOfDiscreteValues(radiofreq2, tension, 1000d));
		
	}	
	
}
