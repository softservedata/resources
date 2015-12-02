package org.registrator.community.dao;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.registrator.community.dao.implementation.DaoOperationsImp;
import org.registrator.community.dao.utils.HibernateUtil;
import org.registrator.community.entity.LineSize;
import org.registrator.community.entity.StoreOfLineSizes;

import java.util.ArrayList;
import java.util.List;

public class StoreOfLineSizesDao extends DaoOperationsImp<StoreOfLineSizes>{

	public StoreOfLineSizesDao() {
		super(StoreOfLineSizes.class);
	
	}

	public List<StoreOfLineSizes> getAllbyLineSizeId (LineSize lineSize) {
		Session session=null;
		List<StoreOfLineSizes> elements = new ArrayList<>();

		try{
			session= HibernateUtil.getSessionFactory().openSession();
			Criteria cr = session.createCriteria(StoreOfLineSizes.class);
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
