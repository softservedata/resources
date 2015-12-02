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
	

	public String toString(){
		return "\n\n\n" + "**************************************" + "\n" +
	"Лінійні параметри ресурсу: " + linearParameterDTO.toString() +
	"Лінійна величина параметру: " + segments.toString();
		

	/*public String toString() {
        StringBuilder result = new StringBuilder();
		result.append("---- Лінійні параметри -----\n");
		result.append("Лінійний параметр: " + linearParameterDTO.toString() + "\n");
		for (SegmentLinearDTO segment : segments) {
			result.append(segment.toString());
		}
		return result.toString();*/

	}
	
}
