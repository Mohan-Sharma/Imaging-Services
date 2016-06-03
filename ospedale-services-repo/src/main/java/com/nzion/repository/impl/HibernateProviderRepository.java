package com.nzion.repository.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.nzion.domain.Employee;
import com.nzion.domain.Enumeration;
import com.nzion.domain.Location;
import com.nzion.domain.Party;
import com.nzion.domain.Provider;
import com.nzion.domain.Referral;
import com.nzion.domain.Speciality;
import com.nzion.domain.Party.PartyType;
import com.nzion.repository.ProviderRepository;
import com.nzion.util.UtilValidator;

/**
 * @author Sandeep Prusty
 * Apr 16, 2010
 */
@SuppressWarnings("unchecked")
public class HibernateProviderRepository
		extends HibernateBaseRepository implements ProviderRepository {

	public Provider getProvider(String accountNumber) {
	return findUniqueByCriteria(Provider.class, new String[] { "accountNumber" }, new Object[] { accountNumber });
	}

	

	public List<Provider> doLookUp(String accountNumber, String firstName, String lastName, Speciality speciality,
			Location location) {
	Criteria criteria = getSession().createCriteria(Provider.class);
	if (UtilValidator.isNotEmpty(accountNumber))
		criteria.add(Restrictions.like("accountNumber", accountNumber, MatchMode.START));
	if (UtilValidator.isNotEmpty(firstName)) criteria.add(Restrictions.like("firstName", firstName, MatchMode.START));
	if (UtilValidator.isNotEmpty(lastName)) criteria.add(Restrictions.like("lastName", lastName, MatchMode.START));
	if (speciality != null) criteria.createCriteria("specialities").add(Restrictions.idEq(speciality.getCode()));
	if (location != null) criteria.createCriteria("locations").add(Restrictions.idEq(location.getId()));
	return criteria.setCacheable(true).list();
	}

	public List<Provider> getAllProviders() {
	return findByCriteria(Provider.class, new String[] { "partyType" }, new Object[] { PartyType.PROVIDER });
	}

	
	@Override
	public List<Referral> searchReferral(String firstName, String lastName,String speciality) {
	Criteria criteria = getSession().createCriteria(Referral.class);
	criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	if (!firstName.isEmpty() && !lastName.isEmpty())
		criteria.add(Restrictions.like("firstName", firstName, MatchMode.START)).add(
				Restrictions.like("lastName", lastName, MatchMode.START));
	if (!firstName.isEmpty() && lastName.isEmpty())
		criteria.add(Restrictions.like("firstName", firstName, MatchMode.START));
	if (firstName.isEmpty() && !lastName.isEmpty())
		criteria.add(Restrictions.like("lastName", lastName, MatchMode.START));
	if(UtilValidator.isNotEmpty(speciality))
		criteria.createCriteria("specialities").add(Restrictions.like("description", speciality, MatchMode.START));
	return criteria.list();
	}

	@Override
	public List<Provider> getAllProvidersForLocation(Location location) {
	Criteria criteria = getSession().createCriteria(Provider.class);
	if (location != null) criteria.createCriteria("locations").add(Restrictions.idEq(location.getId()));
	return criteria.setCacheable(true).list();
	}

	
	public List<Provider> searchProvider(String searchField, String value, Set<Location> locations) {
	List<Long> locationIds = new ArrayList<Long>();
	for (Location l : locations)
		locationIds.add(l.getId());
	Criteria criteria = getSession().createCriteria(Provider.class);
	criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	if ("specialities".equals(searchField)) {
		criteria.createCriteria("specialities").add(Restrictions.ilike("description", value, MatchMode.START));
	} else {
		criteria.add(Restrictions.ilike(searchField, value, MatchMode.START));
	}
	if (UtilValidator.isNotEmpty(locations)) {
		criteria.createCriteria("locations").add(Restrictions.in("id", locationIds));
	}
	criteria.add(Restrictions.eq("providerAssistant", false));
	return criteria.setCacheable(true).list();
	}

	

	@Override
	public List<Speciality> searchSpecialiesBy(String code, String description) {
	Criteria criteria = getSession().createCriteria(Speciality.class);
	if(code!=null)
		criteria.add(Restrictions.like("code", code,MatchMode.START));
	if(description!=null)
		criteria.add(Restrictions.like("description", description,MatchMode.START));
	return criteria.list();
	}

	@Override
	public List<Provider> getProvidersForSpeciality(Speciality speciality) {
	Criteria criteria = getSession().createCriteria(Provider.class);
	if(speciality!=null)criteria.createCriteria("specialities").add(Restrictions.idEq(speciality.getCode()));
	return criteria.list();
	}
	@Override
	public List<Provider> searchProviderBy(String firstName, String lastName,
			Enumeration gender, Collection<Speciality> speciality,Location location) {
		 Criteria criteria = getSession().createCriteria(Provider.class);
	        if (UtilValidator.isNotEmpty(firstName))
	            criteria.add(Restrictions.like("firstName", firstName, MatchMode.ANYWHERE));
	        if (UtilValidator.isNotEmpty(lastName)) criteria.add(Restrictions.like("lastName", lastName, MatchMode.ANYWHERE));
	        if (gender != null)criteria.add(Restrictions.eq("gender", gender)); 
	        if (UtilValidator.isNotEmpty(speciality)){
		 		Iterator<Speciality> iterator = speciality.iterator();
				 Set<String> specialityIds =  new HashSet<String>(); 
			    while (iterator.hasNext())
			    {
			    	specialityIds.add(iterator.next().getCode());
			    }
		 		criteria.createCriteria("specialities").add(Restrictions.in("id", specialityIds));
		 	}	 
	        if (location != null) criteria.createCriteria("locations").add(Restrictions.idEq(location.getId()));
	        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	        return criteria.list();
	}

	@Override
	public List<Employee> searchEmployeeBy(String firstName, String lastName,
			Enumeration gender, Location location) {
		 Criteria criteria = getSession().createCriteria(Employee.class);
		 if (UtilValidator.isNotEmpty(firstName))
	       criteria.add(Restrictions.like("firstName", firstName, MatchMode.ANYWHERE));
	     if (UtilValidator.isNotEmpty(lastName)) criteria.add(Restrictions.like("lastName", lastName, MatchMode.ANYWHERE));
	     if (gender != null)criteria.add(Restrictions.eq("gender", gender)); 
	     if (location != null) criteria.createCriteria("locations").add(Restrictions.idEq(location.getId()));
	      criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	     return criteria.list();
	}
	
	@Override
	public List<Referral> searchReferralBy(String firstName, String lastName,
			Enumeration gender, Collection<Speciality> speciality) {
		 Criteria criteria = getSession().createCriteria(Referral.class);
	        if (UtilValidator.isNotEmpty(firstName))
	            criteria.add(Restrictions.like("firstName", firstName, MatchMode.ANYWHERE));
	        if (UtilValidator.isNotEmpty(lastName)) criteria.add(Restrictions.like("lastName", lastName, MatchMode.ANYWHERE));
	        if (gender != null)criteria.add(Restrictions.eq("gender", gender)); 
	        if (UtilValidator.isNotEmpty(speciality)){
		 		Iterator<Speciality> iterator = speciality.iterator();
				 Set<String> specialityIds =  new HashSet<String>(); 
			    while (iterator.hasNext())
			    {
			    	specialityIds.add(iterator.next().getCode());
			    }
		 		criteria.createCriteria("specialities").add(Restrictions.in("id", specialityIds));
		 	}	 
	        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	        return criteria.list();
	}

	
}