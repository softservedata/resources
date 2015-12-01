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
        System.out.println("PoligonAreaDTO");
        for (PointAreaDTO point : points) {
			point.toString();
		}
		System.out.println();
		return null;
	}
}
