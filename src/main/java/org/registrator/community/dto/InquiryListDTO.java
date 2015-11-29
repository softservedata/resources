package org.registrator.community.dto;

/**
 * A class that offers Data transfer object
 *  - an object that carries data between processes.
 */

import java.io.Serializable;
import java.util.Date;


public class InquiryListDTO implements Serializable {
	private String inquiryType;
	private Date date;
	private Integer fromUserId;
	private Integer toUserId;
	private Integer resourceId;
	private ResourceDTO resource;	 // == null if (inquiryType == "OUTPUT")
	
	public InquiryListDTO() {
		super();
	}

	public InquiryListDTO(String inquiryType, Date date, Integer fromUserId,
			Integer toUserId, Integer resourceId, ResourceDTO resource) {
		super();
		this.inquiryType = inquiryType;
		this.date = date;
		this.fromUserId = fromUserId;
		this.toUserId = toUserId;
		this.resourceId = resourceId;
		this.resource = resource;
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

	public Integer getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(Integer fromUserId) {
		this.fromUserId = fromUserId;
	}

	public Integer getToUserId() {
		return toUserId;
	}

	public void setToUserId(Integer toUserId) {
		this.toUserId = toUserId;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public ResourceDTO getResource() {
		return resource;
	}

	public void setResource(ResourceDTO resource) {
		this.resource = resource;
	}  
	
		
}
