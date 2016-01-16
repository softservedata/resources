package org.registrator.community.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	@NotEmpty(message = "Поле є обов'язковим для введення")
	@Pattern(regexp = AddressDTO.ONLY_LITERALS, message="Літери лише від А до Я")
	private String firstName;
	
	@NotEmpty(message = "Поле є обов'язковим для введення")
	@Pattern(regexp = AddressDTO.ONLY_LITERALS,message="Літери лише від А до Я")
	private String lastName;
	
	@NotEmpty(message = "Поле є обов'язковим для введення")
	@Pattern(regexp = AddressDTO.ONLY_LITERALS,message="Літери лише від А до Я")
	private String middleName;
	
	private String role;
	
	private String login;
	
	@NotEmpty(message = "Поле є обов'язковим для введення")
	@Size(min = 5, max = 32, message = "Пароль повинен містити від 5 до 32 символів")
	@Pattern(regexp = "[a-zA-z,0-9]+",message="Літери тільки латиною")
	private String password;
	
	@NotEmpty(message = "Поле є обов'язковим для введення")
	@Email(message = "Емейл введено не коректно")
	private String email;
	
	private String status;
	
	@Valid
	private AddressDTO address;
	
	@Valid
	private PassportDTO passport;
	
	private WillDocumentDTO willDocument;
	private List<String> otherDocuments;

	public UserDTO(String firstName, String lastName, String middleName, String role, String login, String password,
			String email, String status, AddressDTO address, PassportDTO passport) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.role = role;
		this.login = login;
		this.password = password;
		this.email = email;
		this.status = status;
		this.address = address;
		this.passport = passport;
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

	// public Try getRole() {
	// return role;
	// }
	//
	// public void setRole(Try role) {
	// this.role = role;
	// }

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
				+ "\n" + "Логін: " + login + "\n" + "Пароль: " + password + "\n" + "Пошта: " + email + "\n" + "Статус: "
				+ status + "\n" + "Паспортні дані:" + passport.toString() + "\n" + "Адреса: " + address.toString()
				+ "\n");
		return result;
	}
}
