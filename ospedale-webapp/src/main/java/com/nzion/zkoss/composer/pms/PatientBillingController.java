package com.nzion.zkoss.composer.pms;

import com.nzion.zkoss.composer.OspedaleAutowirableComposer;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.metainfo.ComponentInfo;

import com.nzion.domain.pms.PMSPatientInfo;
import com.nzion.service.PatientService;
import com.nzion.util.UtilMessagesAndPopups;

public class PatientBillingController extends OspedaleAutowirableComposer {

	private static final long serialVersionUID = 1L;
	private PMSPatientInfo pmsPatientInfo;
	private PatientService patientService;
	@Override
	public void doAfterCompose(Component component) throws Exception {
	Long patientId = (Long)Executions.getCurrent().getArg().get("patientId");
	pmsPatientInfo = patientService.getPmsPatient(patientId);
	if(pmsPatientInfo==null)
		pmsPatientInfo = new PMSPatientInfo();
	component.setAttribute("vo", pmsPatientInfo);
	super.doAfterCompose(component);
	}

	@Override
	public ComponentInfo doBeforeCompose(Page page, Component parent, ComponentInfo compInfo) {
	pmsPatientInfo = new PMSPatientInfo();
	return super.doBeforeCompose(page, parent, compInfo);
	}
	
	public void onClick$Save(Event event){
	patientService.saveOrUpdate(pmsPatientInfo);
	UtilMessagesAndPopups.showSuccess();
	}
	
	public PMSPatientInfo getPmsPatientInfo() {
	return pmsPatientInfo;
	}

	public void setPmsPatientInfo(PMSPatientInfo PMSPatientInfo) {
	this.pmsPatientInfo = PMSPatientInfo;
	}

	public PatientService getPatientService() {
	return patientService;
	}

	public void setPatientService(PatientService patientService) {
	this.patientService = patientService;
	}

}
