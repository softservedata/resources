package org.registrator.community.dto;

import java.io.Serializable;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class AddressDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String ONLY_LITERALS = "[а,б,в,г,ґ,д,е,є,ж,з,и,і,ї,й,к,л,м,н,о,п,р,с,т,у,ф,х,ц,ч,ш,щ,ь,ю,я,А,Б,В,Г,Ґ,Д,Е,Є,Ж,З,И,І,Ї,Й,К,Л,М,Н,О,П,Р,С,Т,У,Ф,Х,Ц,Ч,Ш,Щ,Ь,Ю,Я,\\s,\\.]+";
	public static final String ONLY_DIGITS = "[0-9]+";
	
	@NotEmpty(message = "Поле є обов'язковим для введення")
	@Size(min = 5, max = 5, message = "Поштовий індекс повиннен містити 5 цифер")
	@Pattern(regexp = ONLY_DIGITS, message = "Цифри лише від 0 до 9")
	private String postcode;
	
	@NotEmpty(message = "Поле є обов'язковим для введення")
	@Pattern(regexp =  ONLY_LITERALS, message = "Літери лише від А до Я")
	private String region;
	
	@NotEmpty(message = "Поле є обов'язковим для введення")
	@Pattern(regexp =  ONLY_LITERALS, message = "Літери лише від А до Я")
	private String district;
	
	@NotEmpty(message = "Поле є обов'язковим для введення")
	@Pattern(regexp =  ONLY_LITERALS, message = "Літери лише від А до Я")
	private String city;
	
	@NotEmpty(message = "Поле є обов'язковим для введення")
	@Pattern(regexp = ONLY_LITERALS, message = "Літери лише від А до Я")
	private String street;
	
	@NotEmpty(message = "Поле є обов'язковим для введення")
	@Pattern(regexp = ONLY_DIGITS+"|"+ONLY_DIGITS + ONLY_LITERALS, message = "Приклад: 5 або 5а")
	private String building;
	
	@NotEmpty(message = "Поле є обов'язковим для введення")
	@Pattern(regexp = ONLY_DIGITS+"|"+ONLY_DIGITS + ONLY_LITERALS, message = "Приклад: 2 або 2б")
	private String flat;

	public AddressDTO(String postcode, String region, String district, String city, String street, String building,
			String flat) {
		this.postcode = postcode;
		this.region = region;
		this.district = district;
		this.city = city;
		this.street = street;
		this.building = building;
		this.flat = flat;
	}

	public AddressDTO() {

	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getFlat() {
		return flat;
	}

	public void setFlat(String flat) {
		this.flat = flat;
	}

	@Override
	public String toString() {
		return "\n" + "Postcode " + postcode + "\n" + "Region " + region + "\n" + "District " + district + "\n"
				+ "City " + city + "\n" + "Street " + street + "\n" + "Building " + building + "\n" + "Flat " + flat
				+ "\n";
	}
}
