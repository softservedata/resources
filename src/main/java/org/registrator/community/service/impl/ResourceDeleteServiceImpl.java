package org.registrator.community.service.impl;

import org.registrator.community.service.ResourceDeleteService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceDeleteServiceImpl implements ResourceDeleteService {

	@Autowired
	private Logger logger;
	
	@Override
	public void deleteResource(String resourceIdentifier) {
		logger.info("begin deleteResource, param resourceIdentifier = " + resourceIdentifier);
		//to do delete
		logger.info("end deleteResource");

	}

}
