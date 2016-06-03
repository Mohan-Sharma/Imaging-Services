package com.nzion.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import com.nzion.domain.Location;
import com.nzion.domain.Practice;
import com.nzion.domain.PracticeUserAgreement;
import com.nzion.service.exceptions.ServiceException;
import com.nzion.view.PracticeViewObject;
public interface PracticeService {

	public void save(PracticeViewObject practice) throws ServiceException;
	
	public Practice getPractice(Long practiceId);
	
	public Location getLocation(Long locationId);

	public Location getLocation(String locationCode);
	
	public List<Practice> getAllPractice();
	
	void updatePractice(PracticeViewObject practiceVo) throws ServiceException;

	List<TimeZone> getTimeZones();
	
	PracticeUserAgreement getTermsAndConditionsForPractice(Practice practice);
	
	List<Location> getLocationsFor(Practice practice);

}
