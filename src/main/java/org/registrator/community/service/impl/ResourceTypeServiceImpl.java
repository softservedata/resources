package org.registrator.community.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.registrator.community.dao.DiscreteParameterRepository;
import org.registrator.community.dao.LinearParameterRepository;
import org.registrator.community.dao.ResourceTypeRepository;
import org.registrator.community.dto.ResourceTypeDTO;
import org.registrator.community.dto.TypeParameterDTO;
import org.registrator.community.entity.DiscreteParameter;
import org.registrator.community.entity.LinearParameter;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.service.ResourceService;
import org.registrator.community.service.ResourceTypeService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceTypeServiceImpl implements ResourceTypeService {

    @Autowired
    Logger logger;
    @Autowired
    private ResourceTypeRepository resourceTypeRepository;
    @Autowired
    private LinearParameterRepository linearParameterRepository;
    @Autowired
    private DiscreteParameterRepository discreteParameterRepository; 
    @Autowired
    private ResourceService resourceService;

    
    @Override
    public ResourceType addResourceType(ResourceType resourceType) {
        ResourceType savedResourceType = resourceTypeRepository.saveAndFlush(resourceType);
        return savedResourceType;
    }

    @Override
    public ResourceType findById(Integer id) {
        return resourceTypeRepository.findOne(id);
    }
    /**
     * Find type of resource by name
     * @param name
     * @return type of resource 
     * or 0: if we can delete type of resource from DB
     */
    @Override
    public ResourceType findByName(String name) {
        return resourceTypeRepository.findByName(name);
    }

    @Override
    public ResourceTypeDTO editResourceType(ResourceTypeDTO resourceTypeDTO) {
        return resourceTypeDTO;
    }
    /**
     * Delete type of resource if that type doesn't have any resources
     * @param resourceType
     * @return -1: if type of resources exists in the database 
     * or 0: if we can delete type of resource from DB
     */
    @Override
    public int delete(ResourceType resourceType) {
        logger.info("begin");
        List<Resource> list = resourceService.findByType(resourceType);
        if(list.isEmpty()){
            resourceTypeRepository.delete(resourceType);
            logger.info("end: return 0 if list is empty");
            return 0;
        }
        logger.info("end: return -1 if list isn't empty");
        return -1;
    }
    /**
     * Find all types of resources 
     * @return list of resources 
     */
    @Override
    public List<ResourceType> findAll() {
        logger.info("begin");
        List<ResourceType> listOfResourceType = resourceTypeRepository.findAll();
        logger.info("end");
        return listOfResourceType;
    }

    /**
     * Save new type of resource with its discrete and linear parameters in a database
     * @param resourceType
     * @return DTO 
     */
    @Override
    public ResourceTypeDTO addResourceTypeDTO(ResourceTypeDTO resourceTypeDTO) {
        logger.info("begin method for adding new type of resource");
        ResourceType resourceTypeEntity = new ResourceType();
        resourceTypeEntity.setTypeName(resourceTypeDTO.getTypeName());
        resourceTypeEntity = resourceTypeRepository.saveAndFlush(resourceTypeEntity);
        logger.info("save new type of resource in the table resource_types in a database");
        List<TypeParameterDTO> ltp = resourceTypeDTO.getParameters();
        List<LinearParameter> lp = new ArrayList<LinearParameter>();
        List<DiscreteParameter> dp = new ArrayList<DiscreteParameter>();
        /*
         * Here we add linear and discrete parameters to lists
         */
        for (TypeParameterDTO tpar : ltp) {
            if (("linearParameters").equals(tpar.getParametersType())) {
                lp.add(new LinearParameter(resourceTypeEntity, tpar.getDescription(), tpar.getUnitName()));
            }
            if (("discreteParameters").equals(tpar.getParametersType())) {
                dp.add(new DiscreteParameter(resourceTypeEntity, tpar.getDescription(), tpar.getUnitName()));
            }
        }
        linearParameterRepository.save(lp);
        logger.info("save linear parameters to table");
        discreteParameterRepository.save(dp);
        logger.info("save discrete parameters to table");
        return resourceTypeDTO;
    }
}
