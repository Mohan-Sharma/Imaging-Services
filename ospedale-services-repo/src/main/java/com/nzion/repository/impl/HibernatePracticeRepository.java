package com.nzion.repository.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.nzion.domain.Location;
import com.nzion.domain.Practice;
import com.nzion.domain.PracticeUserAgreement;
import com.nzion.repository.PracticeRepository;
import com.nzion.util.Infrastructure;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

public class HibernatePracticeRepository  extends HibernateBaseRepository implements PracticeRepository {

	public Location getLocation(String locationCode) {
	return findUniqueByCriteria(Location.class, new String[]{"locationCode"}, new Object[]{locationCode});
	}

	@Override
	public PracticeUserAgreement getTermsAndConditionsForPractice(Practice practice) {
	Criteria criteria = getSession().createCriteria(PracticeUserAgreement.class);
	return (PracticeUserAgreement) criteria.setCacheable(true).uniqueResult();
	}

	@Override
	public List<Location> getLocationsFor(Practice practice) {
	Criteria criteria = getSession().createCriteria(Location.class);
	criteria.add(Restrictions.eq("practice", practice));
	return criteria.setCacheable(true).list();
	}
}
