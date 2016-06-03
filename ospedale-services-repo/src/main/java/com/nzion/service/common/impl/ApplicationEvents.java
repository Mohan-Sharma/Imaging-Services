package com.nzion.service.common.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nzion.domain.Patient;
import com.nzion.domain.UserLogin;
import com.nzion.domain.base.EventLog;
import com.nzion.enums.EventType;
import com.nzion.repository.common.CommonCrudRepository;
import com.nzion.util.Infrastructure;

/**
 * @author Sandeep Prusty
 * Mar 11, 2011
 */

@Service("applicationEvents")
public class ApplicationEvents {

	private CommonCrudRepository repository;
	
	private static class ApplicationEventsHolder {
		final static ApplicationEvents EVENTS = Infrastructure.getSpringBean("applicationEvents");
	}
	
	@Required
	@Resource(name="commonCrudRepository")
	public void setRepository(CommonCrudRepository repository) {
	this.repository = repository;
	}
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void postEvent0(EventLog log){
	repository.save(log);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void postEvent0(EventType eventType, String detail){
	postEvent0(new EventLog(eventType, detail));
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public static void postEvent(EventType eventType, String detail){
	ApplicationEventsHolder.EVENTS.postEvent0(new EventLog(eventType, detail));
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public static void postEvent(EventLog log){
	ApplicationEventsHolder.EVENTS.postEvent0(log);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public static void postEvent(EventType eventType,Patient patient,UserLogin userLogin,String detail){
	ApplicationEventsHolder.EVENTS.postEvent0(new EventLog(eventType,detail,patient,userLogin));
	}
}