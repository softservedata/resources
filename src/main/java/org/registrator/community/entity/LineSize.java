package org.registrator.community.entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "lines_size")
public class LineSize implements Serializable {


	@Id
	@Column(name = "lines_size_id")
	@GeneratedValue
	private Integer linesSizeId;
	
	@ManyToOne
	@JoinColumn(name = "resource_type_id", nullable = false)
	private ResourceType resourceType;

	@Column(name = "description", nullable = false)
	private String description;
	
	public LineSize() {
		
	}

	public Integer getLinesSizeId() {
		return linesSizeId;
	}

	public void setLinesSizeId(Integer linesSizeId) {
		this.linesSizeId = linesSizeId;
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
