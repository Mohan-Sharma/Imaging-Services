package com.nzion.repository.common.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Embedded;

import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;

import com.nzion.domain.Folder;
import com.nzion.domain.Party;
import com.nzion.domain.Patient;
import com.nzion.domain.Person;
import com.nzion.domain.UserLogin;
import com.nzion.domain.Party.PartyType;
import com.nzion.domain.base.EventLog;
import com.nzion.domain.emr.lab.LabTestPanel;
import com.nzion.enums.EventType;
import com.nzion.repository.common.CommonCrudRepository;
import com.nzion.repository.impl.HibernateBaseRepository;
import com.nzion.util.UtilDateTime;
import com.nzion.util.UtilReflection;

/**
 * @author Sandeep Prusty
 * May 13, 2010
 */
@SuppressWarnings("unchecked")
public class HibernateCommonCrudRepository extends HibernateBaseRepository implements CommonCrudRepository {

	public <T> T getByAccountNumber(Class<T> klass, String columnName, String accountNumber) {
	PartyType partyType = null;
	if (Party.class.isAssignableFrom(klass)) {
		partyType = Party.getPartyType(klass);
		return findUniqueByCriteria(klass, new String[] { columnName, "partyType" }, new Object[] { accountNumber,partyType });
	}
	return findUniqueByCriteria(klass, new String[] { columnName }, new Object[] { accountNumber });
	}

	public <T> T getByUniqueValue(Class<T> klass, String columnName, Object columnValue) {
	return findUniqueByCriteria(klass, new String[] { columnName }, new Object[] { columnValue });
	}

	public <T> List<T> searchByExample(T t) {
	return getHibernateTemplate().findByExample(t);
	}

	public <T> List<T> findByQuery(String queryString) {
	return findByQuery(queryString, 0, 10);
	}

	public <T> List<T> findByQuery(String queryString, int firstResult, int maxResults) {
	return getSession().createQuery(queryString).setFirstResult(firstResult).setMaxResults(maxResults).list();
	}

	public Long findCount(String entityName) {
	return (Long) getSession().createQuery(" select count(o) from " + entityName + " o ").uniqueResult();
	}

	public <T> List<T> findByEquality(Class<T> klass, String[] fields, Object[] values) {
	return findByCriteria(klass, fields, values);
	}

	public <T> T findUniqueByEquality(Class<T> klass, String[] fields, Object[] values) {
	return findUniqueByCriteria(klass, fields, values);
	}

	@Override
	public <T> T findByNullValue(Class<T> klass, String field) {
	Criteria criteria = getSession().createCriteria(klass).setCacheable(true);
	criteria.add(Restrictions.isNull(field));
	return (T) criteria.uniqueResult();
	}

	@Override
	public <T> List<T> getLatestUpdateds(Class<T> klass, int count) {
	Criteria criteria = getSession().createCriteria(klass).setCacheable(true);
	criteria.addOrder(Order.desc("updatedTxTimestamp"));
	criteria.setMaxResults(count);
	return criteria.list();
	}

	public <T> void refreshEntity(T entity) {
	getSession().refresh(entity);
	}

	public <T> List<T> search(String searchString, Class<?> entityClass, String sortColumn, boolean desc,
			String... fields) {
	return search(searchString, entityClass, false, -1, -1, sortColumn, desc, fields);
	}

	public <T> List<T> search(String searchString, Class<?> entityClass, boolean onlyStart, int start, int pageSIze,String sortColumn, boolean desc, String... fields) {
	Session session = getSession();
	session.disableFilter("EnabledFilter");
	Criteria criteria = session.createCriteria(entityClass).setCacheable(true);
	
	criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	if (sortColumn != null) criteria.addOrder(desc ? Order.desc(sortColumn) : Order.asc(sortColumn));
	if (start >= 0 && pageSIze >= 0) {
		criteria.setFirstResult(start);
		criteria.setMaxResults(pageSIze);
	}
	if (Party.class.isAssignableFrom(entityClass))
		criteria.add(Restrictions.eq("partyType", Party.getPartyType(entityClass)));
	if(LabTestPanel.class.isAssignableFrom(entityClass))
		criteria.add(Restrictions.eq("active",Boolean.TRUE));
	Map<String, AliasAndField> aliasFieldMap = new HashMap<String, AliasAndField>();
	Set<String> alreadyAddedAliases = new HashSet<String>();
	Disjunction oredCriterions = Restrictions.disjunction();
	MatchMode mode = onlyStart ? MatchMode.ANYWHERE : MatchMode.ANYWHERE;
	for (String field : fields) {
		if (!String.class.equals(UtilReflection.getNestedFieldType(field, entityClass)) && !Serializable.class.equals(UtilReflection.getNestedFieldType(field, entityClass))) continue;
		AliasAndField aliasAndField = new AliasAndField(field, entityClass);
		if (!aliasAndField.isEmbedded(entityClass) && aliasAndField.hasAlias()
				&& !alreadyAddedAliases.contains(aliasAndField.alias)) {
			criteria.createAlias(aliasAndField.alias, aliasAndField.alias, Criteria.LEFT_JOIN);
			alreadyAddedAliases.add(aliasAndField.alias);
		}
		aliasFieldMap.put(field, aliasAndField);
	}
	for (AliasAndField aliasAndField : aliasFieldMap.values())
		oredCriterions.add(Restrictions.ilike(aliasAndField.fieldString(), searchString, mode));
	criteria.add(oredCriterions);
	criteria.addOrder(Order.asc(fields[0]));
	return criteria.list();
	}

	static class AliasAndField {
		String alias;
		String firstNesting;
		String input;
		boolean embedded;

		public AliasAndField(String compositeField, Class<?> entityClass) {
		input = firstNesting = compositeField;
		if (compositeField.indexOf('.') != -1) {
			String[] splittedFields = compositeField.split("\\.");
			alias = compositeField.substring(0, compositeField.lastIndexOf('.'));
			firstNesting = splittedFields[0];
		}
		embedded = UtilReflection.getAnnotationOnGetter(entityClass, Embedded.class, firstNesting) != null;
		}

		public String fieldString() {
		return input;
		}

		public boolean hasAlias() {
		return alias != null;
		}

		public boolean isEmbedded(Class<?> entityClass) {
		return embedded;
		}
	}

	public <T> List<T> getAllIncludingInactives(Class<T> klass, String... lazyLoadAssocPaths) {
	return getAllIncludingInactives(klass, lazyLoadAssocPaths);
	}

	@Override
	public <T> List<T> getAllIncludingInactivesPageWise(Class<T> klass, int pageSize, int firstRecord,String defaultSortColumn, boolean defaultSortDesc) {
	Criteria criteria = getSession().createCriteria(klass);
	criteria.setFirstResult(firstRecord);
	criteria.setMaxResults(pageSize);
	criteria.setCacheable(true);
	criteria.setCacheMode(CacheMode.NORMAL);
	criteria.addOrder(defaultSortDesc ? Order.desc(defaultSortColumn) : Order.asc(defaultSortColumn));
	return getAllIncludingInactives(klass, criteria);
	}

	private <T> List<T> getAllIncludingInactives(Class<T> klass, Criteria criteria) {
	Session session = getSession();
	session.disableFilter("EnabledFilter");
	criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	if (Party.class.isAssignableFrom(klass)) criteria.add(Restrictions.eq("partyType", Party.getPartyType(klass)));
	return criteria.setCacheable(true).list();
	}

	@Override
	public Long getCountForAllIncludingInactives(Class<?> klass) {
	Session session = getSession();
	session.disableFilter("EnabledFilter");
	Criteria criteria = session.createCriteria(klass);
	if (!Party.class.equals(klass) && Party.class.isAssignableFrom(klass))
		criteria.add(Restrictions.eq("partyType", Party.getPartyType(klass)));
	criteria.setProjection(Projections.count("id"));
	return (Long) criteria.uniqueResult();
	}

	@Override
	public List<EventLog> searchEventLogs(EventType eventType, Date fromDate, Date thruDate, UserLogin createdBy,Patient patient, Date fromTime, Date thruTime) {
	Criteria criteria = getSession().createCriteria(EventLog.class);
	if (eventType != null) criteria.add(Restrictions.eq("eventType", eventType));
	if (createdBy != null) criteria.add(Restrictions.eq("userLogin", createdBy));
	if (patient != null) criteria.add(Restrictions.eq("patient", patient));
	if (fromDate != null) criteria.add(Restrictions.ge("createdDate", fromDate));
	if (thruDate != null) criteria.add(Restrictions.le("createdDate", thruDate));
	if (fromTime != null) criteria.add(Restrictions.ge("createdTime", fromTime));
	if (thruTime != null) criteria.add(Restrictions.le("createdTime", thruTime));
	return criteria.list();
	}

	public List<EventLog> getEventLogsForCurrentDate() {
	Criteria criteria = getSession().createCriteria(EventLog.class);
	addFromThruToCriteria(criteria, UtilDateTime.getDayStart(new Date()), new Date());
	return criteria.setCacheable(true).list();
	}

	@Override
	public List getObjects(Class persistentClass, List<String> distinctTargetIds) {
	List<Long> ids = new ArrayList<Long>();
	for (String targetId : distinctTargetIds) {
		ids.add(new Long(targetId));
	}
	return getSession().createCriteria(persistentClass).setCacheable(true).add(Restrictions.in("id", ids)).list();
	}



	@Override
	public Folder getRootFolder(Patient patient) {
	return (Folder) getSession().createCriteria(Folder.class).setCacheable(true).add(Restrictions.eq("patient", patient)).add(
			Restrictions.isNull("parentFolder")).uniqueResult();
	}

	
	
	public UserLogin getDeactivatedUserLogin(Person deactivatedUser){
	getSession().disableFilter("EnabledFilter");
	UserLogin userLogin = getByUniqueValue(UserLogin.class, "person", deactivatedUser);
	getSession().enableFilter("EnabledFilter");
	return userLogin;
	}

    @Override
    public <T> T load(Class<T> klass, Long id) {
        return (T)getSession().load(klass,id);
    }


}