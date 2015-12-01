package org.registrator.community.service.interfaces;

import org.registrator.community.dto.InquiryListDTO;
import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.dto.UserDTO;

public interface UserService {

	public void InquiryGetSertificate(InquiryListDTO inquiryListDTO);		
	public void InquiryInputResource(InquiryListDTO inquiryListDTO);

    // get information about resource from DB;
	ResourceDTO getResource(Integer resourceId);

		
}
