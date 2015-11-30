package org.registrator.community.service.interfaces;

import org.registrator.community.dto.InquiryListDTO;
import org.registrator.community.dto.UserDTO;

public interface UserService {

	public void InquiryGetSertificate(InquiryListDTO inquiryListDTO);		
	public void InquiryInputResource(InquiryListDTO inquiryListDTO);
	public void addUser(UserDTO user);
		
}
