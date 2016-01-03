package org.registrator.community.service;

import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.entity.Area;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceType;

import java.util.List;
import java.util.Set;


public interface ResourceService {
	
	ResourceDTO addNewResource(ResourceDTO resourceDTO);
	
	ResourceDTO findByIdentifier(String identifier);

    List<Resource> findByType (ResourceType type);

    long count();
    
    Set<String> getDescriptionBySearchTag(String searchTag);

    List<ResourceDTO> getAllByAreaLimits(Double minLat, Double maxLat, Double minLng, Double maxLng);
    
}

