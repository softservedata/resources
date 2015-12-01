package org.registrator.community.dto;

import java.util.Date;
import java.util.List;

public class ResourceDTO {
	
	public enum ResourceStatus {ACTIVE, UNCHECKED, DENIED, OBSOLETE}
	
	private ResourceTypeDTO resourceType;
	private String identifier;
	
	private String registratorName;
	//private UserDTO registrator;
	private Date date;
	private ResourceStatus status;
	private String reasonInclusion;
	//private Integer tomeIdentifier;
	private String tomeIdentifier;
	
	private ResourceAreaDTO resourceArea;
//	private ResourceLinearDTO resourceLinear;
//	private ResourceDiscreteDTO resourceDiscrete;
	private List<ResourceLinearDTO> resourceLinear;
	private List<ResourceDiscreteDTO> resourceDiscrete;
	
	
	
	public ResourceDTO(ResourceTypeDTO resourceType, String identifier, String registratorName, Date date,
			ResourceStatus status, String reasonInclusion, String tomeIdentifier, ResourceAreaDTO resourceArea,
			List<ResourceLinearDTO> resourceLinear, List<ResourceDiscreteDTO> resourceDiscrete) {
		this.resourceType = resourceType;
		this.identifier = identifier;
		this.registratorName = registratorName;
		this.date = date;
		this.status = status;
		this.reasonInclusion = reasonInclusion;
		this.tomeIdentifier = tomeIdentifier;
		this.resourceArea = resourceArea;
		this.resourceLinear = resourceLinear;
		this.resourceDiscrete = resourceDiscrete;
	}
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
	
	/*public UserDTO getRegistrator() {
		return registrator;
	}
	public void setRegistrator(UserDTO registrator) {
		this.registrator = registrator;
	}*/
	
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
	
/*	public Integer getTomeIdentifier() {
		return tomeIdentifier;
	}
	public void setTomeIdentifier(Integer tomeIdentifier) {
		this.tomeIdentifier = tomeIdentifier;
	}*/
	
	public ResourceAreaDTO getResourceArea() {
		return resourceArea;
	}
	public String getTomeIdentifier() {
		return tomeIdentifier;
	}
	public void setTomeIdentifier(String tomeIdentifier) {
		this.tomeIdentifier = tomeIdentifier;
	}
	public void setResourceArea(ResourceAreaDTO resourceArea) {
		this.resourceArea = resourceArea;
	}
/*	public ResourceLinearDTO getResourceLinear() {
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
	*/
	public List<ResourceLinearDTO> getResourceLinear() {
		return resourceLinear;
	}
	public void setResourceLinear(List<ResourceLinearDTO> resourceLinear) {
		this.resourceLinear = resourceLinear;
	}
	public List<ResourceDiscreteDTO> getResourceDiscrete() {
		return resourceDiscrete;
	}
	public void setResourceDiscrete(List<ResourceDiscreteDTO> resourceDiscrete) {
		this.resourceDiscrete = resourceDiscrete;
	}	
	
}
