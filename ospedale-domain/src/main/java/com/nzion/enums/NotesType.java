package com.nzion.enums;

public enum NotesType {

	STANDARD("STANDARD_NOTE_TYPE", "Standard", "Standard");
	
	private String enumCode, description, toStr;

	private NotesType(String enumCode, String description, String toStr) {
	this.enumCode = enumCode;
	this.description = description;
	this.toStr = toStr;
	}

	public String getEnumCode() {
	return enumCode;
	}
	
	public String getDescription() {
	return description;
	}
	
	public String toString() {
	return toStr;
	}
	
}
