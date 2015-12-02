package org.registrator.community.entity;


import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "tomes")
public class Tome implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "tome_id")
	@GeneratedValue
	private Integer tomeId;

	@Column(name = "identifier", nullable = false)
	private String identifier;

	@ManyToOne
	@JoinColumn(name = "registrator_id", nullable = false)
	private User user;

	public Tome() {
		
	}
	
	public Tome(User user, String identifier) {
		this.identifier = identifier;
		this.user = user;
	}



	public Integer getTomeId() {
		return tomeId;
	}

	public void setTomeId(Integer tome_id) {
		this.tomeId = tome_id;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
