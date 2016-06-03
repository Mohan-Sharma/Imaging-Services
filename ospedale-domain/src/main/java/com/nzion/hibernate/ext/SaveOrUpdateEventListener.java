package com.nzion.hibernate.ext;

import org.hibernate.cfg.Configuration;
import org.hibernate.event.Initializable;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.def.DefaultSaveOrUpdateEventListener;

import com.nzion.domain.annot.AccountNumberField;
import com.nzion.domain.base.LocationAware;
import com.nzion.repository.AccountNumberGenerator;
import com.nzion.util.Infrastructure;
import com.nzion.util.UtilReflection;
import com.nzion.util.UtilValidator;

public class SaveOrUpdateEventListener extends DefaultSaveOrUpdateEventListener implements Initializable {

	private static final long serialVersionUID = -2848635808453868272L;
	private AccountNumberGenerator generator;
	
	@Override
	public void onSaveOrUpdate(SaveOrUpdateEvent event) {
	Object entityObj = event.getObject();

	if (LocationAware.class.isAssignableFrom(entityObj.getClass())) {
		if (Infrastructure.getSelectedLocation() != null) {
			((LocationAware) entityObj).setLocation(Infrastructure.getSelectedLocation());
		}
	}

	AccountNumberField accountNumberField = entityObj.getClass().getAnnotation(AccountNumberField.class);
	if (accountNumberField != null && (UtilReflection.getFieldValue(entityObj, accountNumberField.value()) == null 
			|| UtilValidator.isEmpty((String) UtilReflection.getFieldValue(entityObj, accountNumberField.value())))) {
		generator.generateAndSetAccountNumber(entityObj, accountNumberField.value());
	}
	super.onSaveOrUpdate(event);
	}

	public void setGenerator(AccountNumberGenerator generator) {
	this.generator = generator;
	}
	
	
	public void initialize(Configuration cfg) {	}
}