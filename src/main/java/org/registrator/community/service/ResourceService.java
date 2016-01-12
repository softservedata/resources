package org.registrator.community.service;

import org.registrator.community.dto.JSON.PolygonJSON;
import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.entity.Area;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.enumeration.ResourceStatus;

import java.util.List;
import java.util.Set;


public interface ResourceService {
	
	ResourceDTO addNewResource(ResourceDTO resourceDTO, ResourceStatus resourceStatus);
	
	ResourceDTO findByIdentifier(String identifier);

    List<Resource> findByType (ResourceType type);

    long count();
    
    Set<String> getDescriptionBySearchTag(String searchTag);

    Set<String> getAllByAreaLimits(Double minLat, Double maxLat, Double minLng, Double maxLng, String resType);

    Set<String> getAllByPoint(Double lat, Double lng);

    List<PolygonJSON> createPolygonJSON (String identifier);
    
}

