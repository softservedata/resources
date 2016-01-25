package org.registrator.community.service;

import java.util.List;
import java.util.Set;

import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.dto.JSON.PolygonJSON;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.ResourceStatus;
import org.springframework.security.access.prepost.PreAuthorize;


public interface ResourceService {
	
	ResourceDTO addNewResource(ResourceDTO resourceDTO, String ownerLogin, User registrator);
	
	ResourceDTO findByIdentifier(String identifier);

    List<Resource> findByType (ResourceType type);
    @PreAuthorize("hasRole('ROLE_REGISTRATOR') or hasRole('ROLE_ADMIN') or hasRole('ROLE_COMMISSIONER')")
    long count();
    
    Set<String> getDescriptionBySearchTag(String searchTag);

    Set<String> getAllByAreaLimits(Double minLat, Double maxLat, Double minLng, Double maxLng, String resType);

    Set<String> getAllByPoint(Double lat, Double lng);

    List<PolygonJSON> createPolygonJSON (String identifier);
    
}

