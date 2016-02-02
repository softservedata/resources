package org.registrator.community.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.registrator.community.enumeration.TokenType;

@Entity
public class VerificationToken {
	
	@Id
	@Column(name = "token_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
	@Column(name = "token", nullable = false)
    private String token;
    
	@Column(name = "user_email", nullable = false)
    private String userEmail;
    
	@Column(name = "expiry_date", nullable = false)
    private Date expiryDate;
	
	@Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
	private TokenType tokenType;

	public VerificationToken(String token, String userEmail, Date expiryDate) {
		super();
		this.token = token;
		this.userEmail = userEmail;
		this.expiryDate = expiryDate;
	}
	
	public VerificationToken(String token, String userEmail, Date expiryDate,
			TokenType tokenType) {
		super();
		this.token = token;
		this.userEmail = userEmail;
		this.expiryDate = expiryDate;
		this.tokenType = tokenType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	public TokenType getVerificationType() {
		return tokenType;
	}

	public void setVerificationType(TokenType tokenType) {
		this.tokenType = tokenType;
	}

}
