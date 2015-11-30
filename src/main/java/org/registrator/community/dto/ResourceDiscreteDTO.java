package org.registrator.community.dto;

import java.util.List;

public class ResourceDiscreteDTO {
	private DiscreteParameterDTO discreteParameterDTO;
	private List<Double> values;

	public ResourceDiscreteDTO() {
		
	}

	public List<Double> getValues() {
		return values;
	}

	public void setValues(List<Double> values) {
		this.values = values;
	}

	public DiscreteParameterDTO getDiscreteParameterDTO() {
		return discreteParameterDTO;
	}

	public void setDiscreteParameterDTO(DiscreteParameterDTO discreteParameterDTO) {
		this.discreteParameterDTO = discreteParameterDTO;
	}

		
	
	
}
