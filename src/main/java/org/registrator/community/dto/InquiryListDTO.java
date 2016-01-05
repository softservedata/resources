package org.registrator.community.dto;

/**
 * A class that offers Data transfer object
 *  - an object that carries data between processes.
 */

import java.io.Serializable;
import java.util.Date;

import org.registrator.community.enumeration.ResourceStatus;


public class InquiryListDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer inquiry_list_id;
	private String inquiryType;
	private Date date;
	private String userName;
	private String registratorName;
	private String resourceIdentifier;
	private ResourceStatus resourceStatus;
	
		
	public InquiryListDTO() {
	}

	public InquiryListDTO(Integer inquiry_list_id, String inquiryType, Date date, String userName,
			String registratorName, String resourceIdentifier, ResourceStatus resourceStatus) {
		this.inquiry_list_id = inquiry_list_id;
		this.inquiryType = inquiryType;
		this.date = date;
		this.userName = userName;
		this.registratorName = registratorName;
		this.resourceIdentifier = resourceIdentifier;
		this.resourceStatus = resourceStatus;
	}

	public Integer getInquiry_list_id() {
		return inquiry_list_id;
	}

	public void setInquiry_list_id(Integer inquiry_list_id) {
		this.inquiry_list_id = inquiry_list_id;
	}

	public String getInquiryType() {
		return inquiryType;
	}

	public void setInquiryType(String inquiryType) {
		this.inquiryType = inquiryType;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRegistratorName() {
		return registratorName;
	}

	public void setRegistratorName(String registratorName) {
		this.registratorName = registratorName;
	}

	public String getResourceIdentifier() {
		return resourceIdentifier;
	}

	public void setResourceIdentifier(String resourceIdentifier) {
		this.resourceIdentifier = resourceIdentifier;
	}

	public ResourceStatus getResourceStatus() {
		return resourceStatus;
	}

	public void setResourceStatus(ResourceStatus resourceStatus) {
		this.resourceStatus = resourceStatus;
	}
	
}
