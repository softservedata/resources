package org.registrator.community.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.registrator.community.dao.InquiryRepository;
import org.registrator.community.dao.ResourceRepository;
import org.registrator.community.dao.TomeRepository;
import org.registrator.community.dao.UserRepository;
import org.registrator.community.dto.InquiryDTO;
import org.registrator.community.dto.InquiryListDTO;
import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.dto.TomeDTO;
import org.registrator.community.dto.UserNameDTO;
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

/**
 * Service class implements InquiryService interface.
 * Works with procurations of entering data into the register
 *(input inquiry) and with procurations for an extract from register (output inquiry).
 * @author Ann
 *
 */
@Service
public class InquiryServiceImpl implements InquiryService{
	
	@Autowired
	Logger logger;
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
	public Inquiry addOutputInquiry(String resourceIdentifier, String registratorLogin, String userLogin){
		User user = userRepository.findUserByLogin(userLogin);
		User registrator = userRepository.findUserByLogin(registratorLogin);		
		Resource resource = resourceRepository.findByIdentifier(resourceIdentifier);
		logger.info("try write new line to inquiry_list table");
		Inquiry inquiry = new Inquiry("OUTPUT", new Date(), user, registrator, resource);
		logger.info("wrote line to inquiry_list table");
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
	 * Method for showing form on UI to input the parameters 
	 * for inquiry to get the certificate aboute the resource 
	 * forms List<UserNameDTO> - all registrators linked to logged user to fill inquiryAddOut.jsp.
	 */	
	@Override
	public List<UserNameDTO> listUserNameDTO(String userLogin){	
		logger.info("begin");
		User user = userRepository.findUserByLogin(userLogin);
		Set<User> registrators = user.getRegistrators();
		logger.info("set of registrators is empty " + registrators.isEmpty());
		List<UserNameDTO> aListUserNameDTO = new ArrayList<>();
		UserNameDTO userNameDTO;
		for (User registrator : registrators){
			userNameDTO = new UserNameDTO(registrator.getFirstName(), registrator.getLastName(),
					registrator.getMiddleName(), registrator.getLogin());
			aListUserNameDTO.add(userNameDTO);
			logger.info(userNameDTO.toString());
		}	
		logger.info("end");
		return aListUserNameDTO;
	}
	

	/**
	 * Method for showing all output inquiries from logged user on UI 
	 * forms List<InquiryListDTO> to fill listInqUserOut.jsp.
	 */
	@Transactional
	@Override
	public List<InquiryListDTO> listInquiryUser(String userLogin, InquiryType inquiryType){
		logger.info("begin");
		List<InquiryListDTO> listInquiryDTO = new ArrayList<InquiryListDTO>();
		InquiryListDTO inquiryListDTO;
		User user = userRepository.findUserByLogin(userLogin);
		
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
		logger.info("end");
		return listInquiryDTO;
	}
		
	/**
	 * Method for deleting chosen inquiry by Id.
	 */
	@Transactional
	@Override
	public void removeInquiry (Integer inquiryId){
		inquiryRepository.delete(inquiryId);
		logger.info("delete line from inquiry_list table with inqury_id = " + inquiryId);
	}
	
	
	
//	/**
//	 * Method saves the data in the table inquiry_list.
//	 */
//	@Transactional
//	@Override
//	public ResourceDTO addInputInquiry(ResourceDTO resourceDTO, String userLogin){
//		resourceDTO = resourceService.addNewResource(resourceDTO, ResourceStatus.UNCHECKED);
//		User user = userRepository.findUserByLogin(userLogin);
//		String tomeIdentifier = resourceDTO.getTomeIdentifier();
//		Tome tome = tomeRepository.findTomeByIdentifier(tomeIdentifier);
//		User registrator = tome.getRegistrator();
//		String resourceIdentifier = resourceDTO.getIdentifier();
//		Resource resource = resourceRepository.findByIdentifier(resourceIdentifier);		
//		Inquiry inquiry = new Inquiry("INPUT", new Date(), user, registrator, resource);
//		inquiryRepository.saveAndFlush(inquiry);
//		
//		return resourceDTO;
//	    return null;
//	}
//	
	/*@Transactional
	@Override
	public Inquiry addOutputInquiry(InquiryDTO inquiryDTO, String userLogin){
		User user = userRepository.findUserByLogin(userLogin);
		String tomeIdentifier = inquiryDTO.getTomeIdentifier();
		Tome tome = tomeRepository.findTomeByIdentifier(tomeIdentifier);
		User registrator = tome.getRegistrator();
		String resourceIdentifier = inquiryDTO.getResourceIdentifier();
		Resource resource = resourceRepository.findByIdentifier(resourceIdentifier);
		logger.info("try write new line to inquiry_list table");
		Inquiry inquiry = new Inquiry("OUTPUT", new Date(), user, registrator, resource);
		logger.info("wrote line to inquiry_list table");
		inquiryRepository.saveAndFlush(inquiry);	
		return inquiry;
	}*/
		

}


