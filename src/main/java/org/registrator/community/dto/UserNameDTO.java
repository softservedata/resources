package org.registrator.community.dto;

import java.io.Serializable;

public class UserNameDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
    private String firstName;
    private String lastName;
    private String middleName;
    private String login;
        
	public UserNameDTO() {
	}

	public UserNameDTO(String firstName, String lastName, String middleName, String login) {		
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.login = login;
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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Override
	public String toString() {
		return "UserNameDTO [" + firstName + " " + lastName + " " + middleName + " " + login + "]";
	}
    	
}
