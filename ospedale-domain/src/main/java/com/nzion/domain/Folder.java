package com.nzion.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.nzion.domain.base.IdGeneratingBaseEntity;

@Entity
@Table(uniqueConstraints= {@UniqueConstraint(columnNames= {"PARENT_FOLDER_ID","FOLDER_NAME","PATIENT_ID"})})
public class Folder	extends IdGeneratingBaseEntity {
	
	private Folder parentFolder;
	private String description;
	private String name;
	private Set<Folder> childFolders=new HashSet<Folder>();
	private Set<File> files=new HashSet<File>();
	private Patient patient;
	private String path;
	private boolean systemFolder;
	
	@ManyToOne
	@JoinColumn(name="PARENT_FOLDER_ID")
	public Folder getParentFolder() {
	return parentFolder;
	}

	public void setParentFolder(Folder parentFolder) {
	this.parentFolder = parentFolder;
	}

	public String getDescription() {
	return description;
	}

	public void setDescription(String description) {
	this.description = description;
	}

	@Column(name="FOLDER_NAME")
	public String getName() {
	return name;
	}

	public void setName(String name) {
	this.name = name;
	}

	@OneToMany(mappedBy="parentFolder")
	public Set<Folder> getChildFolders() {
	return childFolders;
	}

	public void setChildFolders(Set<Folder> childFolders) {
	this.childFolders = childFolders;
	}

	@OneToMany(mappedBy="folder",orphanRemoval=true,cascade=CascadeType.ALL)
	public Set<File> getFiles() {
	return files;
	}

	public void setFiles(Set<File> files) {
	this.files = files;
	}

	@OneToOne
	@JoinColumn(name="PATIENT_ID")
	public Patient getPatient() {
	return patient;
	}

	public void setPatient(Patient patient) {
	this.patient = patient;
	}

	public String getPath() {
	return path;
	}

	public void setPath(String path) {
	this.path = path;
	}

	public boolean isSystemFolder() {
	return systemFolder;
	}

	public void setSystemFolder(boolean systemFolder) {
	this.systemFolder = systemFolder;
	}

	private static final long serialVersionUID = 1L;
}
