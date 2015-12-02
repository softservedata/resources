package org.registrator.community.dto;

import java.util.List;

public class ResourceAreaDTO {
	private List<PoligonAreaDTO> poligons;

	public ResourceAreaDTO() {
		
	}

	public List<PoligonAreaDTO> getPoligons() {
		return poligons;
	}

	public void setPoligons(List<PoligonAreaDTO> poligons) {
		this.poligons = poligons;
	}

	public String toString() {
		System.out.println("ResourceAreaDTO");
		for (PoligonAreaDTO poligon : poligons) {
			poligon.toString();
		}
		System.out.println();
		return null;
	}
	
}
