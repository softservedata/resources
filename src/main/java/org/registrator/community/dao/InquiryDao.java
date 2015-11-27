package org.registrator.community.dao;

import org.registrator.community.dao.implementation.DaoOperationsImp;
import org.registrator.community.entity.Inquiry;

public class InquiryDao extends DaoOperationsImp<Inquiry>{

	public InquiryDao() {
		super(Inquiry.class);
		
	}

}
