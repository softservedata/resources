package org.registrator.community.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.registrator.community.dao.InquiryRepository;
import org.registrator.community.dao.ResourceRepository;
import org.registrator.community.dao.TomeRepository;
import org.registrator.community.dao.UserRepository;
import org.registrator.community.dto.InquiryDTO;
import org.registrator.community.dto.InquiryListDTO;
import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.dto.TomeDTO;
import org.registrator.community.entity.Inquiry;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.Tome;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.InquiryType;
import org.registrator.community.enumeration.ResourceStatus;
import org.registrator.community.service.InquiryService;
import org.registrator.community.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InquiryServiceImpl implements InquiryService{
	public static final Logger logger = LoggerFactory.getLogger(InquiryServiceImpl.class);	
	@Autowired
	InquiryRepository inquiryRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ResourceRepository resourceRepository;
	@Autowired
	TomeRepository tomeRepository;
	@Autowired
    ResourceService resourceService;

	
	/**
	 * Method saves the data in the table inquiry_list.
	 */
	@Transactional
	@Override
	public Inquiry addOutputInquiry(InquiryDTO inquiryDTO, String userLogin){
		User user = userRepository.findUserByLogin(userLogin);
		String tomeIdentifier = inquiryDTO.getTomeIdentifier();
		Tome tome = tomeRepository.findTomeByIdentifier(tomeIdentifier);
		User registrator = tome.getRegistrator();
		String resourceIdentifier = inquiryDTO.getResourceIdentifier();
		Resource resource = resourceRepository.findByIdentifier(resourceIdentifier);		
		Inquiry inquiry = new Inquiry("OUTPUT", new Date(), user, registrator, resource);
		inquiryRepository.saveAndFlush(inquiry);	
		return inquiry;
	}
	
	/**
	 * Method for showing form on UI to input the parameters 
	 * for inquiry to get the certificate aboute the resource 
	 * forms List<TomeDTO> to fill inquiryAddOut.jsp.
	 */	
	@Override
	public List<TomeDTO> listTomeDTO(){
		List<TomeDTO> aListTomeDTO = new ArrayList<>();
		TomeDTO tomeDTO;
		List<Tome> tomes = tomeRepository.findAll();
		for (Tome tome : tomes){
			tomeDTO = new TomeDTO(tome.getIdentifier(), tome.getRegistrator().getFirstName(), tome.getRegistrator().getLastName(), tome.getRegistrator().getMiddleName());
			aListTomeDTO.add(tomeDTO);
		}		
		return aListTomeDTO;
	}

	/**
	 * Method for showing all output inquiries from logged user on UI 
	 * forms List<InquiryListDTO> to fill listInqUserOut.jsp.
	 */
	@Transactional
	@Override
	public List<InquiryListDTO> listInquiryUser(String userLogin, InquiryType inquiryType){
		logger.info("begin listInquiryUser");
		List<InquiryListDTO> listInquiryDTO = new ArrayList<InquiryListDTO>();
		InquiryListDTO inquiryListDTO;
		User user = userRepository.findUserByLogin(userLogin);
		//SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		List<Inquiry> listInquiry;
		if (user.getRole().getType().toString().equals("USER")){
			listInquiry = inquiryRepository.findByUserAndInquiryType(user, inquiryType);
		} else {
			listInquiry = inquiryRepository.findByRegistratorAndInquiryType(user, inquiryType);
		}
		for (Inquiry inquiry : listInquiry){
			inquiryListDTO = new InquiryListDTO(inquiry.getInquiry_list_id(), inquiry.getInquiryType().toString(), 
						inquiry.getDate(), null, null, inquiry.getResource().getIdentifier(), inquiry.getResource().getStatus());
			User userFrom =inquiry.getUser();
			inquiryListDTO.setUserName(userFrom.getLastName()+ " " +userFrom.getFirstName()+ " " +userFrom.getMiddleName());
			User registrator = inquiry.getRegistrator();
			inquiryListDTO.setRegistratorName(registrator.getLastName()+ " " +registrator.getFirstName()+ " " +registrator.getMiddleName());
			listInquiryDTO.add(inquiryListDTO);
		}
		logger.info("end listInquiryUser");
		return listInquiryDTO;
	}
		
	/**
	 * Method for deleting chosen inquiry by Id.
	 */
	@Transactional
	@Override
	public void removeInquiry (Integer inquiryId){
		inquiryRepository.delete(inquiryId);
	}
	
	/**
	 * Method saves the data in the table inquiry_list.
	 */
	@Transactional
	@Override
	public ResourceDTO addInputInquiry(ResourceDTO resourceDTO, String userLogin){
		resourceDTO = resourceService.addNewResource(resourceDTO, ResourceStatus.UNCHECKED);
		User user = userRepository.findUserByLogin(userLogin);
		String tomeIdentifier = resourceDTO.getTomeIdentifier();
		Tome tome = tomeRepository.findTomeByIdentifier(tomeIdentifier);
		User registrator = tome.getRegistrator();
		String resourceIdentifier = resourceDTO.getIdentifier();
		Resource resource = resourceRepository.findByIdentifier(resourceIdentifier);		
		Inquiry inquiry = new Inquiry("INPUT", new Date(), user, registrator, resource);
		inquiryRepository.saveAndFlush(inquiry);
		
		return resourceDTO;
	}
	
	
	
	
	
	
/*	@Transactional
	@Override
	public Inquiry addOutputInquiry(InquiryListDTO inquiryListDTO){
		User user = userRepository.findUserByLogin(inquiryListDTO.getUserLogin());
		User registrator = userRepository.findUserByLogin(inquiryListDTO.getRegistratorLogin());
		String identifier = inquiryListDTO.getResource().getIdentifier();
		Resource resource = resourceRepository.findResourceByIdentifier(identifier); // findByIdentifier
		Inquiry inquiry = new Inquiry(inquiryListDTO.getInquiryType(), inquiryListDTO.getDate(), user, registrator, resource);
		inquiryRepository.save(inquiry);	
		return inquiry;
	}
	
	public Inquiry testAddOutputInquiry(String resourceIdentifier){
			//Resource resource = resourceRepository.findOne(resourceIdentifier); // findByIdentifier
		ResourceDTO resourceDTO = new ResourceDTO();
		resourceDTO.setIdentifier(resourceIdentifier);		
		InquiryListDTO inquiryListDTO = new InquiryListDTO("OUTPUT", new Date(), "ivan", "petro", resourceDTO);
		return addOutputInquiry(inquiryListDTO);				
	}*/
	
	
}


