package org.registrator.community.dto;

import java.io.Serializable;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class PassportDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "Поле є обов'язковим для введення")
	@Size(min = 2, max = 2, message = "Серія повинна містити лише 2")
	@Pattern(regexp = AddressDTO.ONLY_LITERALS, message = "Серія повинна містити лише літери від А до Я")
	private String seria;
	
	@NotEmpty(message = "Поле є обов'язковим для введення")
	@Size(min = 6, max = 6, message = "Номер повинен містити лише 6 цифер")
	private String number;
	
	@NotEmpty(message = "Поле є обов'язковим для введення")
	@Pattern(regexp = AddressDTO.ONLY_LITERALS, message = "Літери лише від А до Я")
	private String published_by_data;
	
	private String comment;

	public PassportDTO(String seria, String number, String published_by_data) {
		this.seria = seria;
		this.number = number;
		this.published_by_data = published_by_data;
	}

	public PassportDTO() {
	}

	public String getSeria() {
		return seria;
	}

	public void setSeria(String seria) {
		this.seria = seria;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPublished_by_data() {
		return published_by_data;
	}

	public void setPublished_by_data(String published_by_data) {
		this.published_by_data = published_by_data;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	/*
	 * @Override public String toString() { return "Серія паспорту: " + seria +
	 * "\n" + "Номер паспорту: " + number + "\n" + "Виданий: " +
	 * published_by_data + "\n"; }
	 */

}