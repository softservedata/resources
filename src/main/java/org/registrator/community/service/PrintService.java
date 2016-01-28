package org.registrator.community.service;

import org.springframework.security.access.prepost.PreAuthorize;

import com.itextpdf.text.Document;

public interface PrintService {
	
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_REGISTRATOR')")
	Document printProcuration(Integer inquiryId);
	
	@PreAuthorize("hasRole('ROLE_REGISTRATOR')")
	Document printExtract(Integer inquiryId);

	@PreAuthorize("hasRole('ROLE_USER')")
	Document printProcurationOnSubmitInfo(Integer inquiryId);
	
}
