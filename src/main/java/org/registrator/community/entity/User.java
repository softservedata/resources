package org.registrator.community.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.registrator.community.enumeration.UserStatus;

import com.fasterxml.jackson.annotation.JsonManagedReference;
@Entity
@Table(name = "users")
public class User implements Serializable {


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "user_id")
	@GeneratedValue
	private Integer userId;

	@Column(name = "login",unique = true, nullable = false)
	private String login;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "role_id", nullable = false)
	private Integer role_id;
	/* Kindly ask - no to change 'role_id' of Integer type to Role type. The reason is in "user" table 'role_id' column can store only "int" values.
     In the opposite case - it'll be impossible register user with 'Role role_id' :(
     So, in case, you need to get role (ADMIN, USER, etc.) of the user, use "getRoleById" method - defined in the bottom of this class.
     Example of use:
     String usersRole = user.getRoleById(user.getRoleId()); */

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "middle_name", nullable = false)
	private String middleName;

	@Column(name = "email",unique = true, nullable = false)
	private String email;

	@Column(name = "status", nullable = false, columnDefinition = "ENUM('BLOCK','UNBLOCK','INACTIVE')")
	@Enumerated(EnumType.STRING)
	private UserStatus status;

	@OneToMany(mappedBy="user",fetch=FetchType.EAGER)
	@JsonManagedReference
	private List<Address> address = new ArrayList<Address>();

	@OneToMany(mappedBy="user",fetch=FetchType.EAGER)
	@JsonManagedReference
	private List<PassportInfo> passport = new ArrayList<PassportInfo>();

	@OneToMany(mappedBy="user",fetch=FetchType.EAGER)
	@JsonManagedReference
	private List<WillDocument> willDocument = new ArrayList<WillDocument>();

	public User() {

	}

	public User(String login, String password, Integer role_id, String firstName, String lastName, String middleName,
			String email, String status) {
		this.login = login;
		this.password = password;
		this.role_id = role_id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.email = email;
		this.status = UserStatus.valueOf(status.toUpperCase());
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public Integer getRoleId() {
		return role_id;
	}

	public void setRoleId(Integer role_id) {
		this.role_id = role_id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address=address;
	}

	public List<PassportInfo> getPassport() {
		return passport;
	}

	public void setPassport(List<PassportInfo> passport) {
		this.passport=passport;
	}

	public List<WillDocument> getWillDocument() {
		return willDocument;
	}

	public void setWillDocument(List<WillDocument> willDocument) {
		this.willDocument = willDocument;
	}

	public void setPasswordHash(String passwordHash){
		this.password = passwordHash;
	}

	// use below method instead of previous "getRole()":
	// public Role getRole() {
	// 		return role;
	//	}
	public String getRoleById(int param){
		String role;
		switch(param){
			case 1: role = "ADMIN";
				break;
			case 2: role = "REGISTRATOR";
				break;
			case 3: role = "USER";
				break;
			default: role = "Invalid role_id";
				break;
		}
		return role;
	}
}