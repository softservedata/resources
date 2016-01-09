package org.registrator.community.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "passport_data")
public class PassportInfo implements Serializable,Comparable<PassportInfo>{

	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "passport_data_id")
    @GeneratedValue
    private Integer passportId;

	@ManyToOne(fetch=FetchType.EAGER)
	@JsonBackReference
    @JoinColumn(name="user_id", nullable = false)  
    private User user;

    @Column(name = "seria", nullable = false)
    private String seria;
    
    @Column(name = "number", nullable = false)
    private Integer number;
    
    @Column(name = "published_by_data", nullable = false)
    private String published_by_data;
    
    @Column(name = "comment")
    private String comment;
    
    public PassportInfo() {
    	
    }
    
    public PassportInfo(User user, String seria, Integer number, String published_by_data) {
		this.user = user;
		this.seria = seria;
		this.number = number;
		this.published_by_data = published_by_data;
	}
    
	public Integer getPassportId() {
		return passportId;
	}

	public void setPassportId(Integer passportId) {
		this.passportId = passportId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getSeria() {
		return seria;
	}

	public void setSeria(String seria) {
		this.seria = seria;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getPublishedByData() {
		return published_by_data;
	}

	public void setPublishedByData(String published_by_data) {
		this.published_by_data = published_by_data;
	}
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public int compareTo(PassportInfo passport) {
		if(this.seria.equals(passport.seria) && (this.number.equals(passport.number) && (this.published_by_data.equals(passport.published_by_data)))) {
			return 0;
		} else {
			return 1;
		}
		
	}   
}
