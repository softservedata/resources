package org.registrator.community.dto;

import java.util.Date;

public class WillDocumentDTO {
	private Date accessionDate;
	private String comment;
	
	public WillDocumentDTO() {
    	
    }

	public Date getAccessionDate() {
		return accessionDate;
	}

	public void setAccessionDate(Date accessionDate) {
		this.accessionDate = accessionDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
