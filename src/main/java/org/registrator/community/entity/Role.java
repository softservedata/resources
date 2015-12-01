package org.registrator.community.entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role implements Serializable {

    @Id
    @Column(name = "role_id")
    @GeneratedValue
    private Integer roleId;

	
    @Column(name = "name", unique = true, nullable = false, columnDefinition = "ENUM('USER','REGISTRATOR','ADMIN')")
	@Enumerated(EnumType.STRING) 
	private Name name;
    
    @Column(name = "description", nullable = false)
    private String description;

    public Role(){
    }
    

    public Role(String name, String description) {
		this.name = Name.valueOf(name.toUpperCase());
		this.description = description;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
}


