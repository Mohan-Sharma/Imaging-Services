package com.nzion.repository.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.nzion.repository.VaccineRepository;
import com.nzion.util.UtilValidator;

@SuppressWarnings("unchecked")
public class HibernateVaccineRepository extends HibernateBaseRepository implements VaccineRepository {

	@Override
	public <T> List<T> getAllIncludingInactivesPageWise(Class<T> klass, int pageSize, int firstRecord,String... lazyLoadAssocPaths) {
	Criteria criteria = getSession().createCriteria(klass);
	criteria.setFirstResult(firstRecord);
	criteria.setMaxResults(pageSize);
	return getAllIncludingInactives(klass, criteria, lazyLoadAssocPaths);
	}

	@Override
	public Long getCountForAllIncludingInactives(Class<?> klass) {
	Session session = getSession();
	session.disableFilter("EnabledFilter");
	Criteria criteria = session.createCriteria(klass);
	//criteria.add(Restrictions.gt("dosesRemaining", 0));
	criteria.setProjection(Projections.count("id"));
	return (Long) criteria.setCacheable(true).uniqueResult();
	}

	@Override
	public <T> List<T> search(String searchString, Class<?> entityClass, String... fields) {
	Criteria criteria = getSession().createCriteria(entityClass);
	//criteria.add(Restrictions.gt("dosesRemaining", 0));
	Disjunction disjunction = Restrictions.disjunction();
	for (String field : fields)
		disjunction.add(Restrictions.like(field, searchString, MatchMode.START));
	criteria.add(disjunction);
	return criteria.setCacheable(true).list();
	}

	private <T> List<T> getAllIncludingInactives(Class<T> klass, Criteria criteria, String... lazyLoadAssocPaths) {
	Session session = getSession();
	session.disableFilter("EnabledFilter");
	criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	if (UtilValidator.isNotEmpty(lazyLoadAssocPaths)) {
		for (String lazyLoadAssocPath : lazyLoadAssocPaths) {
			criteria.setFetchMode(lazyLoadAssocPath, FetchMode.SELECT);
		}
	}
	//criteria.add(Restrictions.ge("dosesRemaining", 0));
	return criteria.setCacheable(true).list();
	}

	

}
