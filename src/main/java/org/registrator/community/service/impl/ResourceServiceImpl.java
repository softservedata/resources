package org.registrator.community.service.impl;

import java.util.Date;
import java.util.List;

import org.registrator.community.dao.ResourceRepository;
import org.registrator.community.dao.ResourceTypeRepository;
import org.registrator.community.dao.TomeRepository;
import org.registrator.community.dto.ResourceDTO;
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

    @Override
    public ResourceDTO addNewResource(ResourceDTO resourceDTO) {
        Resource resourceEntity = new Resource();
        resourceEntity.setIdentifier(resourceDTO.getIdentifier());
        resourceEntity.setDescription(resourceDTO.getDescription());
        resourceEntity.setReasonInclusion(resourceDTO.getReasonInclusion());
        resourceEntity.setTome(tomeRepository.findTomeByIdentifier(resourceDTO.getTomeIdentifier()));
        resourceEntity.setRegistrator(tomeRepository.findTomeByIdentifier(resourceDTO.getTomeIdentifier()).getRegistrator());
        resourceEntity.setStatus(ResourceStatus.ACTIVE);


        //resourceEntity.setType(resourceTypeRepository.findOne(1));
        resourceEntity.setType(resourceTypeRepository.findByName(resourceDTO.getResourceType().getTypeName()));


        resourceEntity.setDate(resourceDTO.getDate());
        resourceEntity = resourceRepository.save(resourceEntity);
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
