package org.registrator.community.service;

import org.registrator.community.dto.InquiryListDTO;
import org.registrator.community.entity.Inquiry;

public interface InquiryService {
	public Inquiry addOutputInquiry(InquiryListDTO inquiryListDTO);
	
	public Inquiry testAddOutputInquiry(String resourceIdentifier);

}
