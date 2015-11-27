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

    @Column(name = "name", nullable = false, columnDefinition = "ENUM('USER','REGISTRATOR','ADMIN')")
	@Enumerated 
	private Name name;
    
    @Column(name = "description", nullable = false)
    private String description;

    public Role(){
    }
    
    public Role(String name, String description) {
		this.name = checkName(name);
		this.description = description;
	}

    private Name checkName(String name){
		Name roleName = null;
		if (name.equalsIgnoreCase(Name.USER.toString())){
			roleName = Name.USER;
		}
		else if(name.equalsIgnoreCase(Name.ADMIN.toString())){
			roleName = Name.ADMIN;
		}
		else if(name.equalsIgnoreCase(Name.REGISTRATOR.toString())){
			roleName = Name.REGISTRATOR;
		}		
		return roleName;		
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

/*enum Name{
	USER("����������"),
	REGISTRATOR("���������"),
	ADMIN("�����������");
	
	private final String text;
	
	private Name(final String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return text;
	}
*/
enum Name{USER, REGISTRATOR,ADMIN}
