package com.nzion.zkoss.composer;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.metainfo.ComponentInfo;

import com.nzion.domain.Notes;
import com.nzion.domain.Patient;
import com.nzion.service.PatientService;
import com.nzion.util.UtilMessagesAndPopups;

public class PatientNotesController extends OspedaleAutowirableComposer {

	private static final long serialVersionUID = 1L;
	private Notes note = null;
	private Patient patient = null;
	private PatientService patientService;
	@Override
	public void doAfterCompose(Component component) throws Exception {
	component.setAttribute("vo", note);
	super.doAfterCompose(component);
	}

	@Override
	public ComponentInfo doBeforeCompose(Page page, Component parent, ComponentInfo compInfo) {
	patient = (Patient) Executions.getCurrent().getArg().get("patient");
	note = (Notes) Executions.getCurrent().getArg().get("note");
	if(note==null){
		note = new Notes();
	}
	return super.doBeforeCompose(page, parent, compInfo);
	}
	
	public void onClick$Save(Event event){
	patient.addNote(note);
	patientService.saveOrUpdate(patient,null);
	UtilMessagesAndPopups.showSuccess();
	}


	
	public Patient getPatient() {
	return patient;
	}

	public void setPatient(Patient patient) {
	this.patient = patient;
	}

	public void setPatientService(PatientService patientService) {
	this.patientService = patientService;
	}

	public void setNote(Notes note) {
	this.note = note;
	}

}
