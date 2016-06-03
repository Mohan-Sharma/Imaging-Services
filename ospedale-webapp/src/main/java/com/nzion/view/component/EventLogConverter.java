package com.nzion.view.component;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.Listcell;

import com.nzion.domain.base.EventLog;
import com.nzion.enums.EventType;
import com.nzion.service.common.CommonCrudService;
import com.nzion.util.Infrastructure;

public class EventLogConverter implements TypeConverter {

	private CommonCrudService commonCrudService = Infrastructure.getSpringBean("commonCrudService");

	@Override
	public Object coerceToBean(Object val, Component comp) {
	return null;
	}

	@Override
	public Object coerceToUi(Object object, Component comp) {
	EventLog eventLog = (EventLog) object;
	EventType eventType = eventLog.getEventType();
	Listcell listcell = (Listcell) comp;
	if (EventType.APPLICATION_LOGIN.equals(eventType) || EventType.APPLICATION_LOGOUT.equals(eventType)
			|| EventType.SESSION_TIMEOUT.equals(eventType) || EventType.PATIENT_SEARCH.equals(eventType))
		listcell.setLabel("N.A");
	else
		listcell.setLabel(commonCrudService.getFormattedName(eventLog.getPatient())+"- Account No#"+eventLog.getPatient().getAccountNumber());

	return null;
	}

}
