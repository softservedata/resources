package org.registrator.community.dto;

import java.util.List;

public class PoligonAreaDTO {
	private List<PointAreaDTO> points;
	
	public PoligonAreaDTO() {
		
	}

	public List<PointAreaDTO> getPoints() {
		return points;
	}

	public void setPoints(List<PointAreaDTO> points) {
		this.points = points;
	}
}
