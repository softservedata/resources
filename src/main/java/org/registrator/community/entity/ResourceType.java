package org.registrator.community.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "resource_types")
public class ResourceType implements Serializable {

    

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "resource_type_id")
    @GeneratedValue
    private Integer typeId;

    @Column(name = "type_name", unique = true, nullable = false)
    private String typeName;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "resourceType", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<DiscreteParameter> discreteParameters = new ArrayList<DiscreteParameter>();
   
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "resourceType", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<LinearParameter> linearParameters = new ArrayList<LinearParameter>();
    
    public ResourceType() {
    }

    public ResourceType(String typeName) {
        this.typeName = typeName;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    
    public List<DiscreteParameter> getDiscreteParameters() {
        return discreteParameters;
    }

    public void setDiscreteParameters(List<DiscreteParameter> discreteParameters) {
        this.discreteParameters = discreteParameters;
    }
    

    public List<LinearParameter> getLinearParameters() {
		return linearParameters;
	}

	public void setLinearParameters(List<LinearParameter> linearParameters) {
		this.linearParameters = linearParameters;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((typeName == null) ? 0 : typeName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ResourceType other = (ResourceType) obj;
        if (typeName == null) {
            if (other.typeName != null)
                return false;
        } else if (!typeName.equals(other.typeName))
            return false;
        return true;
    }
   
    
    

}
