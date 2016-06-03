package com.nzion.domain;

import java.io.InputStream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.nzion.domain.base.IdGeneratingBaseEntity;

@Entity
@Table(uniqueConstraints= {@UniqueConstraint(columnNames= {"FOLDER_ID","FILE_NAME"})})
public class File extends IdGeneratingBaseEntity {
	
	private String fileName;
	private String fileType;
	private String description;
	private String filePath;
	private Folder folder;
	private InputStream inputStream;
	private String documentType;
	private String reportName;

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	private boolean imported;

	@Column(name="FILE_NAME")
	public String getFileName() {
	return fileName;
	}

	public void setFileName(String fileName) {
	this.fileName = fileName;
	}

	public String getFileType() {
	return fileType;
	}

	public void setFileType(String fileType) {
	this.fileType = fileType;
	}

	public String getDescription() {
	return description;
	}

	public void setDescription(String description) {
	this.description = description;
	}

	public String getFilePath() {
	return filePath;
	}

	public void setFilePath(String filePath) {
	this.filePath = filePath;
	}

	@ManyToOne
	@JoinColumn(name="FOLDER_ID")
	public Folder getFolder() {
	return folder;
	}

	public void setFolder(Folder folder) {
	this.folder = folder;
	}

	@Transient
	public InputStream getInputStream() {
	return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
	this.inputStream = inputStream;
	}

	public String getDocumentType() {
	return documentType;
	}

	public void setDocumentType(String documentType) {
	this.documentType = documentType;
	}

	public boolean isImported() {
	return imported;
	}

	public void setImported(boolean imported) {
	this.imported = imported;
	}
	
	private static final long serialVersionUID = 1L;
}
