package org.registrator.community.entity;

public enum Name{
	USER("USER"),
	REGISTRATOR("REGISTRATOR"),
	ADMIN("ADMIN");
	
	private final String NAME;
	
	private Name(final String name) {
		this.NAME = name;
	}
	
	@Override
	public String toString() {
		return NAME;
	}
}