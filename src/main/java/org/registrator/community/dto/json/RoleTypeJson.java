package org.registrator.community.dto.json;

public class RoleTypeJson {
	private String login;
	private String resource_number;
	private String registrator_number;
	private String identifier;
	private String role;

	public RoleTypeJson(String login, String resource_number, String registrator_number, String identifier,
			String role) {
		this.login = login;
		this.resource_number = resource_number;
		this.registrator_number = registrator_number;
		this.identifier = identifier;
		this.role = role;
	}
	public RoleTypeJson(){
		
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getResource_number() {
		return resource_number;
	}
	public void setResource_number(String resource_number) {
		this.resource_number = resource_number;
	}
	public String getRegistrator_number() {
		return registrator_number;
	}
	public void setRegistrator_number(String registrator_number) {
		this.registrator_number = registrator_number;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "RoleTypeJson [login=" + login + ", resource_number=" + resource_number + ", registrator_number="
				+ registrator_number + ", identifier=" + identifier + ", role=" + role + "]";
	}
}
