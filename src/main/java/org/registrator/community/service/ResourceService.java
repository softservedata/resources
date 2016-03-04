package org.registrator.community.service;

import java.util.List;
import java.util.Set;

import org.registrator.community.dto.JSON.ResourseSearchJson;
import org.registrator.community.dto.ParameterSearchResultDTO;
import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.dto.JSON.PolygonJSON;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.entity.User;
import org.springframework.security.access.prepost.PreAuthorize;


public interface ResourceService {
	
	ResourceDTO addNewResource(ResourceDTO resourceDTO, String ownerLogin, User registrator);
	
	ResourceDTO findByIdentifier(String identifier);

    List<Resource> findByType (ResourceType type);
    @PreAuthorize("hasRole('ROLE_REGISTRATOR') or hasRole('ROLE_ADMIN') or hasRole('ROLE_COMMISSIONER') or hasRole('ROLE_USER')")
    long count();
    
    Set<String> getDescriptionBySearchTag(String searchTag);

    Set<Resource> getAllByAreaLimits(Double minLat, Double maxLat, Double minLng, Double maxLng, String resType, Integer page);

    Set<Resource> getAllByPoint(Double lat, Double lng, Integer page);

    ParameterSearchResultDTO getAllByParameters(ResourseSearchJson parameters);

    List<PolygonJSON> createPolygonJSON (Resource resource, int i);
    
    String getRegistrationNumber(String login);

    Integer countAllByAreaLimits(Double minLat, Double maxLat, Double minLng, Double maxLng);

    Integer countAllByPoint(Double lat,Double lng);
    
}

