package com.nzion.enums;

public enum MATERIALCATEGORY {
	ICD("DIAGNOSIS") {
		@Override
		public String getAttachedItemType() {
		return ENTITY;
		}
	}, CPT("PROCEDURE"){
		@Override
		public String getAttachedItemType() {
		return ENTITY;
		}
	},MEDICATION("MEDICATION") {
		@Override
		public String getAttachedItemType() {
		return STRING;
		}
	},LABORDER("LABORDER") {
		@Override
		public String getAttachedItemType() {
		return STRING;
		}
	},BMIFOLLOWUP("BMIFOLLOWUP") {
		@Override
		public String getAttachedItemType() {
		return NONE;
		}
	},TOBACCOCESSATION("TOBACCOCESSATION") {
		@Override
		public String getAttachedItemType() {
		return NONE;
		}
	};
	
	MATERIALCATEGORY(String description){
		this.description = description;
		
	}
	
	private String description;
	
	public String getDescription() {
		return description;
	}

	public abstract String getAttachedItemType();
	
	public static final String ENTITY = "entity";

	public static final String STRING = "string";
	
	public static final String NONE = "none";
}
