package com.nzion.domain;

import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class ReminderSendBox {

	private ReminderSendBoxIdClass reminderSendBoxIdClass;

	private String reminderContent;

	public ReminderSendBox() {
	}

	public ReminderSendBox(Patient patient, String reminderContent) {
	reminderSendBoxIdClass = new ReminderSendBoxIdClass(patient, new Date());
	this.reminderContent = reminderContent;
	}

	@EmbeddedId
	public ReminderSendBoxIdClass getReminderSendBoxIdClass() {
	return reminderSendBoxIdClass;
	}

	public void setReminderSendBoxIdClass(ReminderSendBoxIdClass reminderSendBoxIdClass) {
	this.reminderSendBoxIdClass = reminderSendBoxIdClass;
	}

	public String getReminderContent() {
	return reminderContent;
	}

	public void setReminderContent(String reminderContent) {
	this.reminderContent = reminderContent;
	}

}
