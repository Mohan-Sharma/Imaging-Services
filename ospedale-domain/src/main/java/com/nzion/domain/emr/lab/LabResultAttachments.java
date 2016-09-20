package com.nzion.domain.emr.lab;

import com.nzion.domain.*;
import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.domain.emr.soap.PatientLabOrder;
import com.nzion.util.UtilValidator;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class LabResultAttachments extends IdGeneratingBaseEntity{

	private static final long serialVersionUID = 1L;

	private LabOrderRequest labOrderRequest;
	private File file;
	private LabTest labTest;
	private LabTestPanel labTestPanel;
	private String resultType;

	@OneToOne
	@JoinColumn(name = "Lab_Order_request_ID")
	public LabOrderRequest getLabOrderRequest() {
		return labOrderRequest;
	}

	public void setLabOrderRequest(LabOrderRequest labOrderRequest) {
		this.labOrderRequest = labOrderRequest;
	}

	@OneToOne
	@JoinColumn(name = "FILE")
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	@OneToOne
	public LabTest getLabTest() {
		return labTest;
	}

	public void setLabTest(LabTest labTest) {
		this.labTest = labTest;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	@OneToOne
	public LabTestPanel getLabTestPanel() {
		return labTestPanel;
	}

	public void setLabTestPanel(LabTestPanel labTestPanel) {
		this.labTestPanel = labTestPanel;
	}
}
