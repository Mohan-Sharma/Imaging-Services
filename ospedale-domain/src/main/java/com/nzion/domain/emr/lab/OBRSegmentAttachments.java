package com.nzion.domain.emr.lab;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.nzion.domain.Enumeration;
import com.nzion.domain.File;
import com.nzion.domain.Patient;
import com.nzion.domain.base.BaseEntity;

@Entity
public class OBRSegmentAttachments extends BaseEntity {


	private OBRSegment obrSegment;
	private Long id;

	private File file;

	@OneToOne
	@Cascade(CascadeType.ALL)
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	@Override
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "OBR_ID", referencedColumnName = "ID")
	public OBRSegment getObrSegment() {
		return obrSegment;
	}

	public void setObrSegment(OBRSegment obrSegment) {
		this.obrSegment = obrSegment;
	}
}
