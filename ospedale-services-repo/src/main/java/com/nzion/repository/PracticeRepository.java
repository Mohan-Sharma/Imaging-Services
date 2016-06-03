package com.nzion.repository;

import java.util.List;

import com.nzion.domain.Location;
import com.nzion.domain.Practice;
import com.nzion.domain.PracticeUserAgreement;


public interface PracticeRepository extends BaseRepository{
	Location getLocation(String locationCode);

	PracticeUserAgreement getTermsAndConditionsForPractice(Practice practice);
	
	List<Location> getLocationsFor(Practice practice);

}
