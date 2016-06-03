package com.nzion.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.nzion.util.UtilValidator;

@Embeddable
public class SlotTypeFeatures {
	private String reportHeader;
	private Integer apptLength;
	private Enumeration defaultFormat;
	private String colorCode = "#ffffff";
	private Boolean isDefaultNote;
	private Boolean isFollowUpNote;

	public SlotTypeFeatures() {}
	
	public SlotTypeFeatures(SlotTypeFeatures features) {
	this.reportHeader = features.reportHeader;
	this.apptLength = features.apptLength;
	this.defaultFormat = features.defaultFormat;
	this.colorCode = features.colorCode;
	this.isDefaultNote = features.isDefaultNote;
	this.isFollowUpNote = features.isFollowUpNote;
	}

	@Column(name="APPT_LENGTH")
	public Integer getApptLength() {
	return apptLength;
	}

	public void setApptLength(Integer apptLength) {
	this.apptLength = apptLength;
	}

	@Column(name="COLOR_CODE")
	public String getColorCode() {
	return colorCode;
	}

	public void setColorCode(String colorCode) {
	if(UtilValidator.isNotEmpty(colorCode))
		this.colorCode = colorCode.startsWith("#") ? colorCode : "#"+Integer.toHexString(Integer.parseInt(colorCode));
	}

	@Column(name="DEFAULT_NOTE")
	public void setDefaultNote(Boolean isDefaultNote) {
	this.isDefaultNote = isDefaultNote;
	}

	public void setFollowUpNote(Boolean isFollowUpNote ) {
	this.isFollowUpNote = isFollowUpNote ;
	}

	@ManyToOne
	@JoinColumn(name="DEFAULT_FORMAT_ID")
	public Enumeration getDefaultFormat() {
	return defaultFormat;
	}

	public void setDefaultFormat(Enumeration defaultFormat) {
	this.defaultFormat = defaultFormat;
	}

	@Column(name="DEFAULT_NOTE")
	public Boolean isDefaultNote() {
	return isDefaultNote;
	}

	@Column(name="FOLLOWUP_NOTE")
	public Boolean isFollowUpNote() {
	return isFollowUpNote;
	}

	@Column(name="REPORT_HEADER")
	public String getReportHeader() {
	return reportHeader;
	}

	public void setReportHeader(String reportHeader) {
	this.reportHeader = reportHeader;
	}
}
