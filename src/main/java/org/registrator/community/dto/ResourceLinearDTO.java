package org.registrator.community.dto;

import java.util.List;

public class ResourceLinearDTO {
	private LinearParameterDTO linearParameterDTO;
	private List<SegmentLinearDTO> segments;
	
	
	public ResourceLinearDTO() {
	
	}

	public List<SegmentLinearDTO> getSegments() {
		return segments;
	}

	public void setSegments(List<SegmentLinearDTO> segments) {
		this.segments = segments;
	}

	public LinearParameterDTO getLinearParameterDTO() {
		return linearParameterDTO;
	}

	public void setLinearParameterDTO(LinearParameterDTO linearParameterDTO) {
		this.linearParameterDTO = linearParameterDTO;
	}
	
	/*public String toString() {
		System.out.println("ResourceLinearDTO: ");
		System.out.println("LinearParameterDTO: " + linearParameterDTO.toString());
		for (SegmentLinearDTO segment : segments) {
			segment.toString();
		}
		return null;
	}*/
	
	/*public String toString() {
		System.out.println("ResourceLinearDTO: ");
		System.out.println("LinearParameterDTO: " + linearParameterDTO.toString());
		for (SegmentLinearDTO segment : segments) {
			segment.toString();
		}
		return ;
	}*/
	
}
