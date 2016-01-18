package org.registrator.community.service;

import com.itextpdf.text.Document;

public interface PrintService {
	
	Document printProcuration(Integer inquiryId);
	
	Document printExtract(Integer inquiryId);
	
}
