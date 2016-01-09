package org.registrator.community.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "other_document")
public class OtherDocuments implements Serializable {

	private static final long serialVersionUID = 1L;
	

	@Id
    @Column(name = "other_document_id")
    @GeneratedValue
    private Integer otherDocumentId;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_id", nullable = false)  
    private User user;
	
    @Column(name = "comment")
    private String comment;
    
    public OtherDocuments() {
    	
    }
}
