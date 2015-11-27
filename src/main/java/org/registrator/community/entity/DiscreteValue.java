package org.registrator.community.entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "discrete_values")
public class DiscreteValue implements Serializable {
	
	@Id
    @Column(name = "discrete_values_id")
    @GeneratedValue
    private Integer discreteValueId;

	@ManyToOne
	@JoinColumn(name = "resource_type_id", nullable = false)
	private ResourceType resourceType;
	
    @Column(name = "description", nullable = false)
    private String description;
    
    public DiscreteValue() {
    	
    }

	public Integer getDiscreteValueId() {
		return discreteValueId;
	}

	public void setDiscreteValueId(Integer discreteValueId) {
		this.discreteValueId = discreteValueId;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}

	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
