package com.nzion.report.search.view;

import java.util.HashSet;
import java.util.Set;

import com.nzion.domain.Patient;
import com.nzion.domain.ReminderSendBox;
import com.nzion.service.common.CommonCrudService;
import com.nzion.service.common.MailService;
import com.nzion.util.Infrastructure;
import com.nzion.util.UtilValidator;

public class ReminderMailVo {

	private String subject;

	private String mailContent;

	private Set<Patient> receivingPatients = new HashSet<Patient>();

	private Set<Patient> ignoredPatients = new HashSet<Patient>();

	private Set<Patient> selectedPatients;

	private Patient mailingPatient;

	public Patient getMailingPatient() {
	return mailingPatient;
	}

	public void setMailingPatient(Patient mailingPatient) {
	this.mailingPatient = mailingPatient;
	}

	public String getSubject() {
	return subject;
	}

	public void setSubject(String subject) {
	this.subject = subject;
	}

	public String getMailContent() {
	return mailContent;
	}

	public void setMailContent(String mailContent) {
	this.mailContent = mailContent;
	}

	public Set<Patient> getReceivingPatients() {
	return receivingPatients;
	}

	public void setReceivingPatients(Set<Patient> receivingPatients) {
	this.receivingPatients = receivingPatients;
	}

	public Set<Patient> getIgnoredPatients() {
	return ignoredPatients;
	}

	public void setIgnoredPatients(Set<Patient> ignoredPatients) {
	this.ignoredPatients = ignoredPatients;
	}

	public Set<Patient> getSelectedPatients() {
	return selectedPatients;
	}

	public void setSelectedPatients(Set<Patient> selectedPatients) {
	for (Patient patient : selectedPatients) {
		if (UtilValidator.isEmpty(patient.getContacts().getEmail())) {
			ignoredPatients.add(patient);
		} else {
			receivingPatients.add(patient);
		}
	}
	this.selectedPatients = selectedPatients;
	}

	public void sendReminder() {
	CommonCrudService commonCrudService = Infrastructure.getSpringBean("commonCrudService");
	MailService mailService = Infrastructure.getSpringBean("mailService");
	for (Patient patient : receivingPatients) {
		/*if (CommunicationPreference.PATIENT_PORTAL.equals(patient.getRemainderPreference())) {
			PatientRemainder patientRemainder = new PatientRemainder();
			patientRemainder.setPatient(patient);
			patientRemainder.setExpectedFollowUpDate(UtilDateTime.addDaysToDate(new Date(),7));
			patientRemainder.setRemainderText(mailContent);
			commonCrudService.save(patientRemainder);
		} else {*/
			mailingPatient = patient;
			mailService.sendPatientReminder(this);
		//}
		ReminderSendBox reminderSendBox = new ReminderSendBox(patient, mailContent);
		commonCrudService.save(reminderSendBox);
	}
	}
}
