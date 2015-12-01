package org.registrator.community.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.registrator.community.dao.implementation.DaoOperationsImp;
import org.registrator.community.dao.interfaces.IUserDao;
import org.registrator.community.dao.utils.HibernateUtil;
import org.registrator.community.entity.User;

public class UserDao extends DaoOperationsImp<User> implements IUserDao {

	public UserDao() {
		super(User.class);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsersListByPortions(int from, int quantity) {
		Session session = null;

		try {

			session = HibernateUtil.getSessionFactory().openSession();
			@SuppressWarnings("unused")
			Transaction transaction = session.beginTransaction();
			Query query = session.createQuery("from User");
			query.setFetchSize(from);
			query.setMaxResults(quantity);
			return query.list();

		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((session != null) && (session.isOpen())) {
				session.close();
			}
		}
		return null;
	}

}
