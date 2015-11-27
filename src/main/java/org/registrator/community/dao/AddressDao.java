package org.registrator.community.dao;

import org.registrator.community.dao.implementation.DaoOperationsImp;
import org.registrator.community.entity.Address;

public class AddressDao extends DaoOperationsImp<Address>{

	public AddressDao() {
		super(Address.class);
		
	}

}
