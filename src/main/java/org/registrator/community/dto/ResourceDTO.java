package org.registrator.community.dto;

import java.util.Date;

public class ResourceDTO {
	public enum ResourceStatus {ACTIVE, UNCHECKED, DENIED, OBSOLETE}
	//
	private ResourceTypeDTO resourceType;
	//
	private String identifier;
	// private UserDTO registrator;
	private String registratorName;
	private Date date;
	private ResourceStatus status;
	//private TomResourceDTO tom;
	private String reasonInclusion;
	//
	private ResourceAreaDTO resourceArea;
	private ResourceLinearDTO resourceLinear;
	private ResourceDiscreteDTO resourceDiscrete;
}
