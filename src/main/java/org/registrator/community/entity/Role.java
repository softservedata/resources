package org.registrator.community.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.registrator.community.enumeration.RoleType;

@Entity
@Table(name = "roles")
@SequenceGenerator(name = "entityIdGenerator", sequenceName = "roles_id")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "entityIdGenerator")
	@Column(name = "id", nullable = false, unique = true)
    private Integer roleId;
	

    @Column(name = "type_id", unique = false, nullable = false)
	@Enumerated(EnumType.ORDINAL) 
	private RoleType type;
    
    @Column(name = "description", nullable = false)
    private String description;

    public Role(){
    }
    
    public Role(RoleType type, String description) {
		this.type = type;
		this.description = description;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public RoleType getType() {
		return type;
	}

	public void setType(RoleType type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
	/*@Override 
	public String toString() {
		return  type.toString();
	}*/
}


