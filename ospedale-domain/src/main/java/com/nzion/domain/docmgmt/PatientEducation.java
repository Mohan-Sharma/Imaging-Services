package com.nzion.domain.docmgmt;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;

import com.nzion.domain.Enumeration;
import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.enums.MATERIALCATEGORY;

@Entity
@Filters( {
	@Filter(name = "EnabledFilter", condition = "(IS_ACTIVE=1 OR IS_ACTIVE IS NULL)") })
@Cache(usage=CacheConcurrencyStrategy.TRANSACTIONAL,region="com.nzion.domain")
public class PatientEducation extends IdGeneratingBaseEntity {

	private static final long serialVersionUID = 1L;
	
	private String documentDescription;
	
	private Enumeration gender;
	
	private Integer fromRange;
	
	private Integer toRange;
	
	private List<AttachedItem> attachedItems;
	
	private MATERIALCATEGORY materialCategory;
	
	private List<PatientEducationDocument> patientEducationDocuments;
	
	@OneToMany(targetEntity = PatientEducationDocument.class)
	@JoinColumn(name = "PATIENT_EDUCATION_ID")
	@Cascade(value = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	public List<PatientEducationDocument> getPatientEducationDocuments() {
	return patientEducationDocuments;
	}

	public void setPatientEducationDocuments(List<PatientEducationDocument> patientEducationDocuments) {
	this.patientEducationDocuments = patientEducationDocuments;
	}

	@Enumerated(EnumType.STRING)
	public MATERIALCATEGORY getMaterialCategory() {
	return materialCategory;
	}

	public void setMaterialCategory(MATERIALCATEGORY materialCategory) {
	this.materialCategory = materialCategory;
	}

	public String getDocumentDescription() {
	return documentDescription;
	}

	public void setDocumentDescription(String documentDescription) {
	this.documentDescription = documentDescription;
	}

	@OneToOne(targetEntity = Enumeration.class)
	@JoinColumn(name = "GENDER_ID")
	public Enumeration getGender() {
	return gender;
	}

	public void setGender(Enumeration gender) {
	this.gender = gender;
	}

	public Integer getFromRange() {
	return fromRange == null ? 0: fromRange;
	}

	public void setFromRange(Integer fromRange) {
	this.fromRange = fromRange;
	}

	public Integer getToRange() {
	return toRange == null ? 0 : toRange;
	}

	public void setToRange(Integer toRange) {
	this.toRange = toRange;
	}

	@OneToMany(targetEntity = AttachedItem.class,fetch=FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = "PATIENT_EDUCATION_ID")
	@Fetch(FetchMode.SELECT)
	public List<AttachedItem> getAttachedItems() {
	return attachedItems;
	}

	public void setAttachedItems(List<AttachedItem> attachedItems) {
	this.attachedItems = attachedItems;
	}
	
	public void addAttachedItem(AttachedItem attachedItem){
	getAttachedItems().add(attachedItem);
	}
}
