package org.registrator.community.dao.implementation;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.registrator.community.dao.interfaces.IDaoOperations;
import org.registrator.community.dao.utils.HibernateUtil;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceType;

public class DaoOperationsImp<T> implements IDaoOperations<T> {

	private Class<T> elementClass;

	public DaoOperationsImp(Class<T> elementClass) {
		this.elementClass = elementClass;
	}

	@Override
	public void add(T entity) {
		Session session = null;

		Integer id = null;
		try {

			session = HibernateUtil.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			id = (Integer) session.save(entity);
			transaction.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((session != null) && (session.isOpen())) {
				session.close();
			}
		}

	}

	@Override
	public void update(T entity) {
		Session session = null;

		try {

			session = HibernateUtil.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			session.update(entity);
			transaction.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((session != null) && (session.isOpen())) {
				session.close();
			}
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public T findById(Integer entityId) {

		Session session = null;
		T element = null;
		try {

			session = HibernateUtil.getSessionFactory().openSession();
			element = (T) session.get(elementClass, entityId);

		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((session != null) && (session.isOpen())) {
				session.close();
			}
		}
		return element;
	}

	@Override
	public void delete(T entity) {
		Session session = null;

		try {

			session = HibernateUtil.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			session.delete(entity);
			transaction.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((session != null) && (session.isOpen())) {
				session.close();
			}
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll() {
		Session session = null;
		List<T> elements = new ArrayList<T>();

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			elements = session.createCriteria(elementClass).list();
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

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAllByResourceId(Resource resource) {
		Session session=null;
		List<T> elements=new ArrayList<>();

		try{
			session=HibernateUtil.getSessionFactory().openSession();
			Criteria cr = session.createCriteria(elementClass);
			cr.add(Restrictions.eq("resource", resource));
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

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAllByResourceTypeId(ResourceType resourceType) {
		Session session=null;
		List<T> elements=new ArrayList<>();

		try{
			session=HibernateUtil.getSessionFactory().openSession();
			Criteria cr = session.createCriteria(elementClass);
			cr.add(Restrictions.eq("resourceType",resourceType));
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

	@Override
	public void deleteAll() {
		List<T> elements = getAll();
		for (T t : elements) {
			delete(t);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public T findByLogin(String entityLogin) {
		Session session = null;
		T element = null;
		try {

			session = HibernateUtil.getSessionFactory().openSession();
			String query = "from User u where u.login=:entityLogin1";
			Query que = session.createQuery(query);
			que.setParameter("entityLogin1", entityLogin);
			element = (T) que.uniqueResult();

		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((session != null) && (session.isOpen())) {
				session.close();
			}
		}
		return element;
	}


	@SuppressWarnings("unchecked")
	@Override
	public T findByIdentifier(String entityIdentifier) {
		Session session = null;
		T element = null;
		try {

			session = HibernateUtil.getSessionFactory().openSession();
			String query = "from Resource r where r.identifier=:identifier1";
			Query que = session.createQuery(query);
			que.setParameter("identifier1", entityIdentifier);
			element = (T) que.uniqueResult();

		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((session != null) && (session.isOpen())) {
				session.close();
			}
		}
		return element;
	}

	@Override
	public int isEmpty() {
		Session session = null;
		Integer element = null;
		try {

			session = HibernateUtil.getSessionFactory().openSession();
			String query = "select count(*) from User";
			Query que = session.createQuery(query);
			//que.setParameter("entityLogin1");
			element = ((Long) que.uniqueResult()).intValue();
			

		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((session != null) && (session.isOpen())) {
				session.close();
			}
		}
		
		return element;
	}


}
