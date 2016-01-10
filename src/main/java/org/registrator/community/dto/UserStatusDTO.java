package org.registrator.community.dto;

public class UserStatusDTO {
	private String login;
	private String status;

	public UserStatusDTO(String login,String status) {
		this.login=login;
		this.status=status;
	}
	
	public UserStatusDTO() {
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
