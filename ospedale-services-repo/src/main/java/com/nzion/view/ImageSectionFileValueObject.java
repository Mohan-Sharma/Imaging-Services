package com.nzion.view;

import java.util.Date;

import com.nzion.domain.File;

public class ImageSectionFileValueObject {
	
	private File file;
	
	private Date visitDate;
	
	public ImageSectionFileValueObject(){
	}

	public ImageSectionFileValueObject(File file,Date visitDate){
	this.file = file;
	this.visitDate = visitDate;
	}
	
	public File getFile() {
	return file;
	}

	public void setFile(File file) {
	this.file = file;
	}

	public Date getVisitDate() {
	return visitDate;
	}

	public void setVisitDate(Date visitDate) {
	this.visitDate = visitDate;
	}
}
