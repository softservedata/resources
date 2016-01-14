package org.registrator.community.service;

import org.registrator.community.dto.InquiryDTO;
import org.registrator.community.dto.InquiryListDTO;
import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.dto.TomeDTO;
import org.registrator.community.entity.Inquiry;
import org.registrator.community.enumeration.InquiryType;

import java.util.List;

public interface InquiryService {
	
	Inquiry addOutputInquiry(InquiryDTO inquiryDTO, String userLogin);
	
	List<TomeDTO> listTomeDTO();
	
	List<InquiryListDTO> listInquiryUser(String userLogin, InquiryType inquiryType);
		
	void removeInquiry (Integer inquiryId);
	
	ResourceDTO addInputInquiry(ResourceDTO resourceDTO, String userLogin);

}


