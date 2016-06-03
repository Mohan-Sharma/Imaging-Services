package com.nzion.repository.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.nzion.domain.Enumeration;
import com.nzion.repository.EnumerationRepository;
import com.nzion.util.Infrastructure;

/**
 * @author Sandeep Prusty
 *         Apr 13, 2010
 */
@SuppressWarnings("unchecked")
public class HibernateEnumerationRepository extends HibernateBaseRepository implements EnumerationRepository {

    public List<Enumeration> getGeneralEnumerationsByType(String enumType) {
        Criteria criteria = getSession().createCriteria(Enumeration.class);
        criteria.add(Restrictions.eq("enumType", enumType));
        criteria.addOrder(Order.asc("description"));
        criteria.setCacheable(true);
        return criteria.list();
    }

    public List<Enumeration> getPracticeSpecificEnumerationsByType(String enumType) {
        Criteria criteria = getSession().createCriteria(Enumeration.class);
        criteria.add(Restrictions.eq("enumType", enumType));
        criteria.addOrder(Order.asc("description"));
        criteria.setCacheable(Boolean.TRUE);
        return criteria.list();
    }

    public List<Enumeration> getRelevantEnumerationsByType(String enumType) {
        Criteria criteria = getSession().createCriteria(Enumeration.class);
        criteria.add(Restrictions.eq("enumType", enumType));
        criteria.addOrder(Order.asc("description"));
        criteria.setCacheable(Boolean.TRUE);
        return criteria.list();
    }

    public List<Enumeration> getRelevantEnumerationOrderedByCode(String enumType) {
        Criteria criteria = getSession().createCriteria(Enumeration.class);
        criteria.add(Restrictions.eq("enumType", enumType));
        criteria.addOrder(Order.asc("enumCode"));
        criteria.setCacheable(Boolean.TRUE);
        return criteria.list();
    }

    public Enumeration updateEnumeration(Long enumId, String enumCode, String description) {
        Enumeration enumeration = findByPrimaryKey(Enumeration.class, enumId);
        enumeration.setEnumCode(enumCode);
        enumeration.setDescription(description);
        save(enumeration);
        return enumeration;
    }

    public Enumeration findByEnumCode(String enumCode) {
        Criteria criteria = getSession().createCriteria(Enumeration.class);
        criteria.add(Restrictions.eq("enumCode", enumCode));
        criteria.addOrder(Order.asc("enumCode"));
        criteria.setCacheable(true);
        return (Enumeration) criteria.uniqueResult();
    }

    public Enumeration findByEnumCodeAndEnumType(String enumCode, String enumType) {
        Criteria criteria = getSession().createCriteria(Enumeration.class);
        criteria.add(Restrictions.eq("enumCode", enumCode));
        criteria.add(Restrictions.eq("enumType", enumType));
        criteria.addOrder(Order.asc("description"));
        criteria.setCacheable(true);
        return (Enumeration) criteria.uniqueResult();
    }

    public boolean deleteAllEnumerationByType(String enumType) {
        final String DELETEQUERY = "delete from " + Enumeration.class.toString() + " where enumType = '" + enumType+ "'";
        Query q = getSession().createQuery(DELETEQUERY);
        q.executeUpdate();
        return true;
    }

    @Override
    public List<Enumeration> getAllIncludingInactivesPageWise(String[] enumerationTypes, int firstRecord, int pageSize) {
        getSession().disableFilter("EnabledFilter");
        Criteria criteria = getSession().createCriteria(Enumeration.class);
        criteria.add(Restrictions.in("enumType", enumerationTypes));
        criteria.addOrder(Order.asc("description"));
        criteria.setFirstResult(firstRecord);
        criteria.setMaxResults(pageSize);
        return criteria.list();
    }

    @Override
    public List<Enumeration> getLatestUpdateds(int count, String[] enumerationTypes) {
        getSession().disableFilter("EnabledFilter");
        Criteria criteria = getSession().createCriteria(Enumeration.class);
        criteria.add(Restrictions.in("enumType", enumerationTypes));
        criteria.addOrder(Order.desc("updatedTxTimestamp"));
        criteria.setMaxResults(count);
        return criteria.setCacheable(true).list();
    }

    @Override
    public List<Enumeration> search(String searchString, String[] enumerationTypes) {
        getSession().disableFilter("EnabledFilter");
        Criteria criteria = getSession().createCriteria(Enumeration.class);
        criteria.add(Restrictions.in("enumType", enumerationTypes));
        criteria.add(Restrictions.or(Restrictions.like("enumCode", searchString, MatchMode.ANYWHERE), Restrictions.like("description", searchString, MatchMode.ANYWHERE)));
        criteria.addOrder(Order.asc("enumCode"));
        return criteria.setCacheable(true).list();
    }

    @Override
    public Long getCountForAllIncludingInactives(String[] enumerationTypes) {
        getSession().disableFilter("EnabledFilter");
        Criteria criteria = getSession().createCriteria(Enumeration.class);
        criteria.add(Restrictions.in("enumType", enumerationTypes));
        criteria.setProjection(Projections.count("id"));
        criteria.addOrder(Order.asc("enumCode"));
        return (Long) criteria.setCacheable(true).uniqueResult();
    }

}