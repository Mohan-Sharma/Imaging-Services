package com.nzion.zkoss.composer;

import org.springframework.dao.DataIntegrityViolationException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Messagebox;

import com.nzion.domain.Enumeration;
import com.nzion.service.common.EnumerationService;
import com.nzion.util.UtilValidator;

public class EnumerationComposer extends OspedaleAutowirableComposer {

	private static final long serialVersionUID = 1L;

	private EnumerationService enumerationService;
	private Enumeration enumeration;


	public void onClick$save(Event event) throws InterruptedException {
	try {
		enumerationService.addEnumeration(enumeration);
	} catch (DataIntegrityViolationException e) {
		Messagebox.show("Enum type and Enum code already exist.");
	}
	event.getTarget().detach();
	}

	public EnumerationService getEnumerationService() {
	return enumerationService;
	}

	public void setEnumerationService(EnumerationService enumerationService) {
	this.enumerationService = enumerationService;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
	super.doAfterCompose(comp);
	enumeration = UtilValidator.isNotEmpty(Executions.getCurrent().getArg()) ? (Enumeration)Executions.getCurrent().getArg().get("entity"): new Enumeration();
	}
	
	public boolean isNew(){
	return enumeration.getId() == null;
	}

	public Enumeration getEnumeration() {
	return enumeration;
	}

	public void setEnumeration(Enumeration enumeration) {
	this.enumeration = enumeration;
	}
}