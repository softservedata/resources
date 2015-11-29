package org.registrator.community.dto;

import java.util.List;

public class ResourceTypeDTO {
	private String typeName;
	private List<LinearParameterDTO> linearParameters;
	private List<DiscreteParameterDTO> discreteParameters;
	
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public List<LinearParameterDTO> getLinearParameters() {
		return linearParameters;
	}
	public void setLinearParameters(List<LinearParameterDTO> linearParameters) {
		this.linearParameters = linearParameters;
	}
	public List<DiscreteParameterDTO> getDiscreteParameters() {
		return discreteParameters;
	}
	public void setDiscreteParameters(List<DiscreteParameterDTO> discreteParameters) {
		this.discreteParameters = discreteParameters;
	}
	
	
}
