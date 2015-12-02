package org.registrator.community.application;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.registrator.community.dao.ResourceTypeDao;
import org.registrator.community.dao.utils.HibernateUtil;
import org.registrator.community.dto.ResourceTypeDTO;
import org.registrator.community.service.implementation.RegistratorServiceImpl;
import org.registrator.community.service.interfaces.RegistratorService;

public class AppShowAllTypeofRes {

public static void main(String[] args) {
	
	List<ResourceTypeDTO> listResType = new ArrayList<ResourceTypeDTO>();
	RegistratorService rs = new RegistratorServiceImpl();
	listResType = rs.showAllTypeOfResources();
	listResType.forEach(rtype -> System.out.println(rtype));
}
}



