package org.registrator.community.entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "resource_types")
public class ResourceType implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "resource_type_id")
	@GeneratedValue
	private Integer typeId;

	@Column(name = "type_name", unique = true, nullable = false)
	private String typeName;

	public ResourceType() {

	}

	public ResourceType(String typeName) {
		this.typeName = typeName;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
