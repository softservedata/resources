package org.registrator.community.dto;

public class ResourceNumberDTO {
	private Integer number;
	private String resourceNumber;

	public ResourceNumberDTO() {
	}

	public ResourceNumberDTO(Integer number, String resourceNumber) {
		this.number = number;
		this.resourceNumber = resourceNumber;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getResourceNumber() {
		return resourceNumber;
	}

	public void setResourceNumber(String resourceNumber) {
		this.resourceNumber = resourceNumber;
	}

}
