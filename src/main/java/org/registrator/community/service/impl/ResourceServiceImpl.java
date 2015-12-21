package org.registrator.community.service.impl;


import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.registrator.community.dao.AreaRepository;
import org.registrator.community.dao.ResourceRepository;
import org.registrator.community.dao.ResourceTypeRepository;
import org.registrator.community.dao.TomeRepository;
import org.registrator.community.dto.PointAreaDTO;
import org.registrator.community.dto.PoligonAreaDTO;
import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.entity.Area;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.enumeration.ResourceStatus;
import org.registrator.community.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	ResourceRepository resourceRepository;

	@Autowired
	TomeRepository tomeRepository;

	@Autowired
	ResourceTypeRepository resourceTypeRepository;

	@Autowired
	AreaRepository areaRepository;

	@Override
	public ResourceDTO addNewResource(ResourceDTO resourceDTO) {
		Resource resourceEntity = new Resource();
		resourceEntity.setIdentifier(resourceDTO.getIdentifier());
		resourceEntity.setDescription(resourceDTO.getDescription());
		resourceEntity.setReasonInclusion(resourceDTO.getReasonInclusion());
		resourceEntity.setTome(tomeRepository.findTomeByIdentifier(resourceDTO.getTomeIdentifier()));
		resourceEntity
				.setRegistrator(tomeRepository.findTomeByIdentifier(resourceDTO.getTomeIdentifier()).getRegistrator());
		resourceEntity.setStatus(ResourceStatus.ACTIVE);
		resourceEntity.setType(resourceTypeRepository.findByName(resourceDTO.getResourceType().getTypeName()));
		resourceEntity.setDate(resourceDTO.getDate());
		resourceEntity = resourceRepository.save(resourceEntity);
		List<Area> areas = new ArrayList<Area>();		
		for (PoligonAreaDTO poligonAreaDTO : resourceDTO.getResourceArea().getPoligons()) {
			for (PointAreaDTO point : poligonAreaDTO.getPoints()) {
				Area area = new Area();
				area.setResource(resourceEntity);
 				area.setOrderNumber(point.getOrderNumber());
				area.setLatitude(point.getDecimalLatitude());
				area.setLongitude(point.getDecimalLongitude());
				areas.add(area);
			}
		}
		areaRepository.save(areas);
		return resourceDTO;
	}

    @Override
    public List<Resource> findByType(ResourceType type) {
        return resourceRepository.findByType(type);
    }

    @Override
    public long count() {
        return resourceRepository.count();
    }
}
