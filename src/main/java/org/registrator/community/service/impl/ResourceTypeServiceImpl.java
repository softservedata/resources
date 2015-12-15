package org.registrator.community.service.impl;

import java.util.List;

import org.registrator.community.dao.ResourceTypeRepository;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.service.ResourceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceTypeServiceImpl implements ResourceTypeService{

	
	@Autowired
	private ResourceTypeRepository resourceTypeRepository;
	
	@Override
	public ResourceType addResourceType(ResourceType resourceType) {
		// TODO Auto-generated method stub
		ResourceType savedResourceType = resourceTypeRepository.saveAndFlush(resourceType);
		//ResourceType savedResourceType = resourceTypeRepository.saveAndFlush(resourceType);
		return savedResourceType;
	}

	@Override
	public ResourceType findById(Integer id) {
		return resourceTypeRepository.findOne(id);
	}
	@Override
	public ResourceType findByName(String name) {
		return resourceTypeRepository.findByName(name);
	}

	@Override
	public ResourceType editResourceType(Integer typeId, String typeName) {
		
		ResourceType rt = resourceTypeRepository.findOne(typeId);
		rt.setTypeName(typeName);
		return resourceTypeRepository.save(rt);
		//return resourceTypeRepository.saveAndFlush(resourceType);
	}

	

	@Override
	public void delete(Integer id) {
		resourceTypeRepository.delete(id);
	}

	@Override
	public List<ResourceType> findAll(){
		// TODO Auto-generated method stub
		List<ResourceType> listOfResourceType = resourceTypeRepository.findAll();
		
		
		return listOfResourceType;
	}




	

}
