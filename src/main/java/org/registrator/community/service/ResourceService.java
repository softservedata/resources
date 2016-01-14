package org.registrator.community.service;

import java.util.List;
import java.util.Set;

import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.dto.JSON.PolygonJSON;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.ResourceStatus;


public interface ResourceService {
	
	ResourceDTO addNewResource(ResourceDTO resourceDTO, ResourceStatus resourceStatus, User registrator);
	
	ResourceDTO findByIdentifier(String identifier);

    List<Resource> findByType (ResourceType type);

    long count();
    
    Set<String> getDescriptionBySearchTag(String searchTag);

    Set<String> getAllByAreaLimits(Double minLat, Double maxLat, Double minLng, Double maxLng, String resType);

    Set<String> getAllByPoint(Double lat, Double lng);

    List<PolygonJSON> createPolygonJSON (String identifier);
    
}

