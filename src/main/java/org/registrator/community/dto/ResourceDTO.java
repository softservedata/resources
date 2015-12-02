package org.registrator.community.dto;

import org.registrator.community.entity.ResourceStatus;

import java.util.Date;
import java.util.List;

public class ResourceDTO {
	
	private ResourceTypeDTO resourceType;
	private String identifier;
	private String description;
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
	
	
	
	public ResourceDTO() {
	
	}
	public ResourceDTO(ResourceTypeDTO resourceType, String identifier, String description, String registratorName, Date date,
			ResourceStatus status, String reasonInclusion, String tomeIdentifier, ResourceAreaDTO resourceArea,
			List<ResourceLinearDTO> resourceLinear, List<ResourceDiscreteDTO> resourceDiscrete) {
		this.resourceType = resourceType;
		this.identifier = identifier;
		this.description = description;
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
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
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
	
	public String toString(){
		return "\n\n\n" + "**************************************" + "\n" +
	"Ідентифікатор ресурсу: " + identifier + "\n"+
	"Тип ресурсу: " + resourceType.getTypeName() + "\n"+
	"Опис ресурсу: " + description + "\n" + 
	"Причина внесення в базу:  " + reasonInclusion + "\n" +
	"Ім'я та прізвище реєстратора   " + registratorName + "\n"+
	"Номер тому: " + tomeIdentifier + "\n"+
	"Дата внесення: " + date + "\n"+
	"Статус: " + status.toString() + "\n"+
	"Том: " + tomeIdentifier + "\n" +
	"Територія: " + resourceArea + "\n" +
	"Лінійні параметри: " + resourceLinear + "\n" +
 	"Дискретні параметри: "	+ resourceDiscrete;
		
	}
	
//	public String toString(){
//		return "\n\n\n" + "**************************************" + "\n" +
//	"Ідентифікатор ресурсу: " + identifier + "\n" +
//	"Лінійні параметри: " + resourceLinear;	
//	}
	
}
