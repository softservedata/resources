package org.registrator.community.enumeration;

public enum UserStatus {
	BLOCK("BLOCK"),
	UNBLOCK("UNBLOCK"),
	INACTIVE("INACTIVE");
	
	private final String STATUS;
	
	private UserStatus(final String status) {
		this.STATUS = status;
	}
	
	@Override
	public String toString() {
		return STATUS;
	}
}