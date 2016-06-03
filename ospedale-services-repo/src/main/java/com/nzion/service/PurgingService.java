package com.nzion.service;

import java.text.ParseException;

import org.quartz.SchedulerException;

import com.nzion.domain.PurgingPolicy;

/**
 * @author Sandeep Prusty
 * Jul 6, 2010
 */
public interface PurgingService {

	PurgingPolicy getPurgingPolicy();
	
	PurgingPolicy save(PurgingPolicy purgingPolicy) throws SchedulerException, ParseException;
}
