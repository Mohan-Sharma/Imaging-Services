package com.nzion.zkoss.composer;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Required;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Listheader;

import com.nzion.domain.Patient;
import com.nzion.domain.UserLogin;
import com.nzion.domain.base.EventLog;
import com.nzion.enums.EventType;
import com.nzion.service.common.CommonCrudService;
import com.nzion.util.UtilDateTime;
import com.nzion.util.UtilMessagesAndPopups;
import com.nzion.view.component.GenericAscendingComparator;
import com.nzion.view.component.GenericDescendingCompartor;

public class EventLogSearchController extends OspedaleAutowirableComposer {

	private UserLogin userLogin;

	private Patient patient;

	private EventType eventType;

	private Date fromDate;

	private Date thruDate;

	private Date fromTime;

	private Date thruTime;

	private CommonCrudService commonCrudService;

	public List<EventLog> searchEventLog() {
	if (checkDirty()) {
		UtilMessagesAndPopups.showError(Labels.getLabel("nosearchcondition"));
		return null;
	}
	return commonCrudService.searchEventLogs(eventType, fromDate != null ? UtilDateTime.dateOnly(fromDate) : null,
			thruDate != null ? UtilDateTime.dateOnly(thruDate) : null, userLogin, patient,
			fromTime != null ? UtilDateTime.timeOnly(fromTime) : null, thruTime != null ? UtilDateTime
					.timeOnly(thruTime) : null);
	}

	private boolean checkDirty() {
	if (eventType == null && patient == null && userLogin == null && fromDate == null && thruDate == null
			&& fromTime == null && thruTime == null) return true;
	return false;
	}

	public boolean checkEventLogType() {
	if (eventType.equals(EventType.APPLICATION_LOGIN) || eventType.equals(EventType.APPLICATION_LOGOUT)
			|| eventType.equals(EventType.SESSION_TIMEOUT)) return true;
	return false;
	}

	public void enableAscendingSort(Listheader listheader, String fieldName) {
	GenericAscendingComparator comparator = new GenericAscendingComparator(fieldName);
	listheader.setSortAscending(comparator);
	}

	public void enableDescendingSort(Listheader listheader, String fieldName) {
	GenericDescendingCompartor compartor = new GenericDescendingCompartor(fieldName);
	listheader.setSortDescending(compartor);
	}

	public UserLogin getUserLogin() {
	return userLogin;
	}

	public void setUserLogin(UserLogin userLogin) {
	this.userLogin = userLogin;
	}

	public Patient getPatient() {
	return patient;
	}

	public void setPatient(Patient patient) {
	this.patient = patient;
	}

	public EventType getEventType() {
	return eventType;
	}

	public void setEventType(EventType eventType) {
	this.eventType = eventType;
	}

	public Date getFromDate() {
	return fromDate;
	}

	public void setFromDate(Date fromDate) {
	this.fromDate = fromDate;
	}

	public Date getThruDate() {
	return thruDate;
	}

	public void setThruDate(Date thruDate) {
	this.thruDate = thruDate;
	}

	public CommonCrudService getCommonCrudService() {
	return commonCrudService;
	}

	public Date getFromTime() {
	return fromTime;
	}

	public void setFromTime(Date fromTime) {
	this.fromTime = fromTime;
	}

	public Date getThruTime() {
	return thruTime;
	}

	public void setThruTime(Date thruTime) {
	this.thruTime = thruTime;
	}

	@Resource
	@Required
	public void setCommonCrudService(CommonCrudService commonCrudService) {
	this.commonCrudService = commonCrudService;
	}

	private static final long serialVersionUID = 1L;
}
