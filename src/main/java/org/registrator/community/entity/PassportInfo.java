package org.registrator.community.entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "passport_data")
public class PassportInfo implements Serializable{
	
    @Id
    @Column(name = "passport_data_id")
    @GeneratedValue
    private Integer passportId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", nullable = false)  
    private User user;

    @Column(name = "seria", nullable = false)
    private String seria;
    
    @Column(name = "number", nullable = false)
    private Integer number;
    
    @Column(name = "published_by_data", nullable = false)
    private String published_by_data;
    
    public PassportInfo() {
    	
    }
    
    public PassportInfo(User user, String seria, Integer number, String published_by_data) {
		this.user = user;
		this.seria = seria;
		this.number = number;
		this.published_by_data = published_by_data;
	}
    
/*    public PassportInfo(Integer passportId, User user, String seria, Integer number, String published_by_data) {
		this.passportId = passportId;
		this.user = user;
		this.seria = seria;
		this.number = number;
		this.published_by_data = published_by_data;
	}*/

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

	public String getPublished_by_data() {
		return published_by_data;
	}

	public void setPublished_by_data(String published_by_data) {
		this.published_by_data = published_by_data;
	}
    
}
