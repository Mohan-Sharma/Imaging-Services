package com.nzion.domain.docmgmt;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.enums.MATERIALCATEGORY;

@Entity
public class AttachedItem extends IdGeneratingBaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String code;
	
	private String description;
	
	private String attachedItemId;
	
	private MATERIALCATEGORY materialcategory;

	public AttachedItem(){
	}
	
	public AttachedItem(String code,String description,String id,MATERIALCATEGORY materialcategory){
	this.code = code;
	this.description = description;
	this.attachedItemId = id;
	this.materialcategory = materialcategory;
	}

	@Enumerated(EnumType.STRING)
	public MATERIALCATEGORY getMaterialcategory() {
	return materialcategory;
	}

	public void setMaterialcategory(MATERIALCATEGORY materialcategory) {
	this.materialcategory = materialcategory;
	}

	public String getAttachedItemId() {
	return attachedItemId;
	}

	public void setAttachedItemId(String attachedItemId) {
	this.attachedItemId = attachedItemId;
	}

	public String getCode() {
	return code;
	}

	public void setCode(String code) {
	this.code = code;
	}

	public String getDescription() {
	return description;
	}

	public void setDescription(String description) {
	this.description = description;
	}
}
