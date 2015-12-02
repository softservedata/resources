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

	public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("---- Полігон -----\n");
        for (PointAreaDTO point : points) {
			result.append(point.toString());
		}

		return result.toString();
	}
}
