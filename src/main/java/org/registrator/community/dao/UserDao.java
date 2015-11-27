package org.registrator.community.dao;

import org.registrator.community.dao.implementation.DaoOperationsImp;
import org.registrator.community.entity.User;

public class UserDao extends DaoOperationsImp<User>{

	public UserDao() {
		super(User.class);
		
	}

}
