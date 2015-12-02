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
		StringBuilder result = new StringBuilder();
		result.append("----- Площі одного ресурсу ----- \n");
		for (PoligonAreaDTO poligon : poligons) {
			result.append(poligon.toString());
		}

		return result.toString();
	}
	
}
