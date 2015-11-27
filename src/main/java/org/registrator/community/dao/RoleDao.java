package org.registrator.community.dao;

import org.registrator.community.dao.implementation.DaoOperationsImp;
import org.registrator.community.entity.Role;

public class RoleDao extends DaoOperationsImp<Role>{

	public RoleDao() {
		super(Role.class);
		
	}

}
