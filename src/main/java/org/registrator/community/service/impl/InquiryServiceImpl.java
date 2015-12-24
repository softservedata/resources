package org.registrator.community.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.registrator.community.controller.administrator.InquiryController;
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
import org.registrator.community.service.InquiryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Transactional
	@Override
	public Inquiry addOutputInquiry(InquiryDTO inquiryDTO, String userLogin){
		User user = userRepository.findUserByLogin(userLogin);
		String tomeIdentifier = inquiryDTO.getTomeIdentifier();
		Tome tome = tomeRepository.findTomeByIdentifier(tomeIdentifier);
		User registrator =tome.getRegistrator();
		String resourceIdentifier = inquiryDTO.getResourceIdentifier();
		Resource resource = resourceRepository.findByIdentifier(resourceIdentifier);		
		Inquiry inquiry = new Inquiry("OUTPUT", new Date(), user, registrator, resource);
		inquiryRepository.saveAndFlush(inquiry);	
		return inquiry;
	}
	
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

	
	@Transactional
	@Override
	public List<InquiryListDTO> listInquiryUserOut(String userLogin){
		logger.info("begin listInquiryUserOut");
		List<InquiryListDTO> listInquiryDTO = new ArrayList<InquiryListDTO>();
		InquiryListDTO inquiryListDTO;
		User user = userRepository.findUserByLogin(userLogin);
		List<Inquiry> listInquiry = inquiryRepository.findByUserAndInquiryType(user);
		for (Inquiry inquiry : listInquiry){
			inquiryListDTO = new InquiryListDTO(inquiry.getInquiry_list_id(), inquiry.getInquiryType().toString(), 
						inquiry.getDate(), null, null, inquiry.getResource().getIdentifier());
			inquiryListDTO.setUserName(user.getLastName()+ " " +user.getFirstName()+ " " +user.getMiddleName());
			User registrator = inquiry.getRegistrator();
			inquiryListDTO.setRegistratorName(registrator.getLastName()+ " " +registrator.getFirstName()+ " " +registrator.getMiddleName());
			listInquiryDTO.add(inquiryListDTO);
		}
		logger.info("end listInquiryUserOut");
		return listInquiryDTO;
	}
	
	@Transactional
	@Override
	public void removeInquiry (Integer inquiryId){
		inquiryRepository.delete(inquiryId);
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


