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
import org.registrator.community.entity.ResourceType;
import org.registrator.community.service.ResourceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceTypeServiceImpl implements ResourceTypeService{

	
	@Autowired
	private ResourceTypeRepository resourceTypeRepository;
    @Autowired
    private LinearParameterRepository linearParameterRepository;
    @Autowired 
    private DiscreteParameterRepository discreteParameterRepository;
	
	@Override
	public ResourceType addResourceType(ResourceType resourceType) {
		ResourceType savedResourceType = resourceTypeRepository.saveAndFlush(resourceType);
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
		
		ResourceType rt = resourceTypeRepository.findByName(typeName);
		rt.setTypeName(typeName);
		return resourceTypeRepository.save(rt);
	}
	@Override
	public void delete(ResourceType resourceType) {
		resourceTypeRepository.delete(resourceType);
	}

	@Override
	public List<ResourceType> findAll(){
		List<ResourceType> listOfResourceType = resourceTypeRepository.findAll();
		return listOfResourceType;
	}

	@Override
	public ResourceTypeDTO addResourceTypeDTO(ResourceTypeDTO newRTDTO) {
		ResourceType rt = new ResourceType(newRTDTO.getTypeName());
		List<TypeParameterDTO> ltp = newRTDTO.getParameters();
		
		List<LinearParameter> lp = new ArrayList<LinearParameter>();
		List<DiscreteParameter> dp = new ArrayList<DiscreteParameter>();
		
		
		for(TypeParameterDTO tpar: ltp){
			if(tpar.getParametersType()=="linearParameters"){
				lp.add(new LinearParameter(rt, tpar.getDescription(), tpar.getUnitName()));
			}
			if(tpar.getParametersType()=="discreteParaters"){
				dp.add(new DiscreteParameter(rt, tpar.getDescription(), tpar.getUnitName()));
			}
			
		}		
		resourceTypeRepository.save(rt);
		linearParameterRepository.save(lp);
		discreteParameterRepository.save(dp);
		return newRTDTO;
	}
}
