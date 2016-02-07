package org.registrator.community.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.registrator.community.dto.JSON.ResourceNumberDTOJSON;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	@NotEmpty
	@Size(min = 1, max = 30, message = "Ім\'я повинно містити від {min} до {max} символів")
	@Pattern(regexp = AddressDTO.ONLY_LITERALS, message = "Некоректне введення")
	private String firstName;

	@NotEmpty
	@Size(min = 1, max = 30, message = "Прізвище повинне містити від {min} до {max} символів")
	@Pattern(regexp = AddressDTO.ONLY_LITERALS, message = "Некоректне введення")
	private String lastName;

	@Pattern(regexp = "[(?=.*[а-яіїєА-ЯІЇЄa-zA-Z,\\s,\\.,\\-]).{1,30}]*", message = "Некоректне введення")
	private String middleName;

	private String role;

	private String login;

	@NotEmpty
	@Email(message = "Введіть коректну адресу")
	private String email;

	private String status;

	@Valid
	private AddressDTO address;

	@Valid
	private PassportDTO passport;

	private ResourceNumberDTOJSON resourceNumberDTOJSON;

	private String territorialCommunity;

	private WillDocumentDTO willDocument;
	private List<String> otherDocuments;

	public UserDTO(String firstName, String lastName, String middleName, String role, String login, String email,
			String status, AddressDTO address, PassportDTO passport) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.role = role;
		this.login = login;
		this.email = email;
		this.status = status;
		this.address = address;
		this.passport = passport;
	}
	
	public UserDTO(String firstName, String lastName, String middleName, String role, String login, String email,
			String status, AddressDTO address, PassportDTO passport,String territorialCommunity) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.role = role;
		this.login = login;
		this.email = email;
		this.status = status;
		this.address = address;
		this.passport = passport;
		this.territorialCommunity = territorialCommunity;
	}

	public UserDTO() {

	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public PassportDTO getPassport() {
		return passport;
	}

	public void setPassport(PassportDTO passport) {
		this.passport = passport;
	}

	public WillDocumentDTO getWillDocument() {
		return willDocument;
	}

	public void setWillDocument(WillDocumentDTO willDocument) {
		this.willDocument = willDocument;
	}

	public List<String> getOtherDocuments() {
		return otherDocuments;
	}

	public void setOtherDocuments(List<String> otherDocuments) {
		this.otherDocuments = otherDocuments;
	}

	@Override
	public String toString() {
		String result;
		result = String.valueOf("ПІБ: " + lastName + " " + firstName + " " + middleName + "\n" + "Роль:  " + getRole()
				+ "\n" + "Логін: " + login + "\n" + "Пошта: " + email + "\n" + "Статус: " + status + "\n"
				+ "Паспортні дані:" + passport.toString() + "\n" + "Адреса: " + address.toString() + "\n");
		return result;
	}

	public ResourceNumberDTOJSON getResourceNumberDTOJSON() {
		return resourceNumberDTOJSON;
	}

	public void setResourceNumberDTOJSON(ResourceNumberDTOJSON resourceNumberDTOJSON) {
		this.resourceNumberDTOJSON = resourceNumberDTOJSON;
	}

	public String getTerritorialCommunity() {
		return territorialCommunity;
	}

	public void setTerritorialCommunity(String territorialCommunity) {
		this.territorialCommunity = territorialCommunity;
	}
}