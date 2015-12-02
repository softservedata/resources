package org.registrator.community.dto;

import java.io.Serializable;
import java.util.List;

import org.registrator.community.entity.Role;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	private String middleName;
	private Role role;
	private String login;
	private String password;
	private String email;
	private String status;
	private AddressDTO address;
	private PassportDTO passport;

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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

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

	@Override
	public String toString() {
		String result;
        result = String.valueOf("ПІБ: " + lastName + " " + firstName + " " + middleName + "\n"
                + "Роль:  " + getRole().getName() + "\n"
                + "Логін: " + login + "\n"
                + "Пароль: " + password + "\n"
                + "Пошта: " + email + "\n"
                + "Статус: " + status + "\n"
                + "Паспортні дані:" + passport.toString() + "\n"
                + "Адреса: " + address.toString() + "\n");
		return result;
	}
}
