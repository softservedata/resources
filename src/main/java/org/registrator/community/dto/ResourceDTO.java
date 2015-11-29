package org.registrator.community.dto;

import java.util.Date;

public class ResourceDTO {
	
	public enum ResourceStatus {ACTIVE, UNCHECKED, DENIED, OBSOLETE}
	
	private ResourceTypeDTO resourceType;
	private String identifier;
	private String registratorName;
	private Date date;
	private ResourceStatus status;
	private String reasonInclusion;
	private ResourceAreaDTO resourceArea;
	private ResourceLinearDTO resourceLinear;
	private ResourceDiscreteDTO resourceDiscrete;
	
	public ResourceTypeDTO getResourceType() {
		return resourceType;
	}
	public void setResourceType(ResourceTypeDTO resourceType) {
		this.resourceType = resourceType;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getRegistratorName() {
		return registratorName;
	}
	public void setRegistratorName(String registratorName) {
		this.registratorName = registratorName;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public ResourceStatus getStatus() {
		return status;
	}
	public void setStatus(ResourceStatus status) {
		this.status = status;
	}
	public String getReasonInclusion() {
		return reasonInclusion;
	}
	public void setReasonInclusion(String reasonInclusion) {
		this.reasonInclusion = reasonInclusion;
	}
	public ResourceAreaDTO getResourceArea() {
		return resourceArea;
	}
	public void setResourceArea(ResourceAreaDTO resourceArea) {
		this.resourceArea = resourceArea;
	}
	public ResourceLinearDTO getResourceLinear() {
		return resourceLinear;
	}
	public void setResourceLinear(ResourceLinearDTO resourceLinear) {
		this.resourceLinear = resourceLinear;
	}
	public ResourceDiscreteDTO getResourceDiscrete() {
		return resourceDiscrete;
	}
	public void setResourceDiscrete(ResourceDiscreteDTO resourceDiscrete) {
		this.resourceDiscrete = resourceDiscrete;
	}
	
	
	
}
