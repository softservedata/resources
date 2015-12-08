package org.registrator.community.dao;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.registrator.community.dao.implementation.DaoOperationsImp;
import org.registrator.community.dao.utils.HibernateUtil;
import org.registrator.community.entity.DiscreteParameter;
import org.registrator.community.entity.ResourceDiscreteValue;

import java.util.ArrayList;
import java.util.List;

public class StoreOfDiscreteValuesDao extends DaoOperationsImp<ResourceDiscreteValue> {

	public StoreOfDiscreteValuesDao() {
		super(ResourceDiscreteValue.class);
	}

	public List<ResourceDiscreteValue> getAllBydiscreteValuesId(DiscreteParameter discreteValue) {
		Session session=null;
		List<ResourceDiscreteValue> elements=new ArrayList<>();

		try{
			session= HibernateUtil.getSessionFactory().openSession();
			Criteria cr = session.createCriteria(ResourceDiscreteValue.class);
			cr.add(Restrictions.eq("discreteValue", discreteValue));
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
