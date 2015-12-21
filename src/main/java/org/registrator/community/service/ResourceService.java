package org.registrator.community.service;

import org.registrator.community.dto.ResourceDTO;

public interface ResourceService {
	ResourceDTO addNewResource(ResourceDTO resourceDTO);
	ResourceDTO getResourceByIdentifier(String identifier);

}
