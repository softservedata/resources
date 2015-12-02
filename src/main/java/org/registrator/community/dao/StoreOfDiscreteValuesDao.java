package org.registrator.community.dao;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.registrator.community.dao.implementation.DaoOperationsImp;
import org.registrator.community.dao.utils.HibernateUtil;
import org.registrator.community.entity.StoreOfDiscreteValues;

import java.util.ArrayList;
import java.util.List;

public class StoreOfDiscreteValuesDao extends DaoOperationsImp<StoreOfDiscreteValues> {

	public StoreOfDiscreteValuesDao() {
		super(StoreOfDiscreteValues.class);
	}

	public List<StoreOfDiscreteValues> getAllBydiscreteValuesId(Integer discreteValuesId) {
		Session session=null;
		List<StoreOfDiscreteValues> elements=new ArrayList<>();

		try{
			session= HibernateUtil.getSessionFactory().openSession();
			Criteria cr = session.createCriteria(StoreOfDiscreteValues.class);
			cr.add(Restrictions.eq("discrete_values_id", discreteValuesId));
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
