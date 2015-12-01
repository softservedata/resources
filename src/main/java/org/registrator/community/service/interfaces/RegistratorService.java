package org.registrator.community.service.interfaces;

import java.util.List;

import org.registrator.community.dao.ResourceDao;
import org.registrator.community.dao.ResourceTypeDao;
import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.dto.ResourceTypeDTO;

public interface RegistratorService {
	
	void addResourseType(ResourceTypeDTO resourceTypeDTO);
	
	void addResource(ResourceDTO resourceDTO);
	
	// generate doc for info/output request
	
	// generate doc for input request
	List<ResourceTypeDTO> showAllTypeOfResources();
	
	List<ResourceDTO> showAllResources();
	
	ResourceDTO showResourceByIdentifier(String identifier);
	

}
