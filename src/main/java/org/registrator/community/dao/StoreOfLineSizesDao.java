package org.registrator.community.dao;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.registrator.community.dao.implementation.DaoOperationsImp;
import org.registrator.community.dao.utils.HibernateUtil;
import org.registrator.community.entity.LinearParameter;
import org.registrator.community.entity.ResourceLinearValue;

import java.util.ArrayList;
import java.util.List;

public class StoreOfLineSizesDao extends DaoOperationsImp<ResourceLinearValue>{

	public StoreOfLineSizesDao() {
		super(ResourceLinearValue.class);
	
	}

	public List<ResourceLinearValue> getAllbyLineSizeId (LinearParameter lineSize) {
		Session session=null;
		List<ResourceLinearValue> elements = new ArrayList<>();

		try{
			session= HibernateUtil.getSessionFactory().openSession();
			Criteria cr = session.createCriteria(ResourceLinearValue.class);
			cr.add(Restrictions.eq("lineSize", lineSize));
			elements = cr.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((session != null) && (session.isOpen())) {
				session.close();
			}
		}

		return elements;

	}

	
	
}
