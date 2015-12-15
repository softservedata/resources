package org.registrator.community.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "discrete_parameters")
public class DiscreteParameter  implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	@Id
    @Column(name = "discrete_parameter_id")
    @GeneratedValue
    private Integer discreteParameterId;

	
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)

	@JoinColumn(name = "resource_type_id", nullable = false)
	@JsonBackReference
	private ResourceType resourceType;
	
    @Column(name = "description", nullable = false)
    private String description;
    
    @Column(name = "unit_name", nullable = false)
	private String unitName ;
    
    public DiscreteParameter() {
    	
    }
    
	public DiscreteParameter(ResourceType resourceType, String description,
			String unitName) {
		this.resourceType = resourceType;
		this.description = description;
		this.unitName = unitName;
	}

	public Integer getDiscreteParameterId() {
		return discreteParameterId;
	}

	public void setDiscreteParameterId(Integer discreteParameterId) {
		this.discreteParameterId = discreteParameterId;
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
	
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

}
