package com.nzion.repository.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.nzion.domain.base.BaseEntity;
import com.nzion.repository.BaseRepository;
import com.nzion.util.Infrastructure;
import com.nzion.util.UtilDateTime;
import com.nzion.util.UtilReflection;
import com.nzion.util.UtilValidator;

/**
 * $Date: 2011-09-08 14:19:51 +0530 (Thu, 08 Sep 2011) $
 * $Rev: 2058 $
 *
 * @author PRADYUMNA
 */

@SuppressWarnings("unchecked")
public class HibernateBaseRepository extends HibernateDaoSupport implements BaseRepository {

    @Override
    protected HibernateTemplate createHibernateTemplate(SessionFactory sessionFactory) {
        HibernateTemplate hibernateTemplate = super.createHibernateTemplate(sessionFactory);
        hibernateTemplate.setCacheQueries(true);
        hibernateTemplate.setFilterNames(new String[]{"DateFilterDef", "EnabledFilter"});
        return hibernateTemplate;
    }

    public <T> void save(T t) {
        getHibernateTemplate().saveOrUpdate(t);
    }

    public <T> List<T> findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults) {
        return getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
    }

    public <T> List<T> findByCriteria(DetachedCriteria criteria) {
        return getHibernateTemplate().findByCriteria(criteria);
    }

    public <T> T findByPrimaryKey(Class<T> klass, Serializable id, LockMode lockMode) {
        return getHibernateTemplate().get(klass, id, lockMode);
    }

    public <T> T findByPrimaryKey(Class<T> klass, Serializable id) {
        return findByPrimaryKey(klass, id, LockMode.NONE);
    }

    public <T> T findByUniqueKey(DetachedCriteria criteria) {
        return (T) getHibernateTemplate().findByCriteria(criteria).get(0);
    }

    /**
     * It will and up all the properties in the where condition.
     * For complicated Criteria please help your self by not calling this :)
     */
    protected <T> T findUniqueByCriteria(Class<T> persistentClass, String[] fileds, Object[] values) {
        Session session = getSession();
        Criteria criteria = session.createCriteria(persistentClass);
        for (int i = 0; i < fileds.length; ++i) {
            criteria.add(Restrictions.eq(fileds[i], values[i]));
        }
        criteria.setCacheable(true);
        if(UtilValidator.isNotEmpty(criteria.list()) && criteria.list().size() == 1)
            return (T) criteria.uniqueResult();
        else if(UtilValidator.isNotEmpty(criteria.list()))
            return (T) criteria.list().get(criteria.list().size() - 1);
        else
            return null;
    }

    /**
     * It will and up all the properties in the where condition.
     * For complicated Criteria please help your self by not calling this :)
     */
    protected <T> List<T> findByCriteria(Class<T> persistentClass, String[] fileds, Object[] values) {
        Session session = getSession();
        Criteria criteria = session.createCriteria(persistentClass);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        for (int i = 0; i < fileds.length; ++i) {
            if (Collection.class.isAssignableFrom(values[i].getClass())) {
                criteria.add(Restrictions.in(fileds[i], (Collection<Object>) values[i]));
                continue;
            }
            criteria.add(Restrictions.eq(fileds[i], values[i]));
        }
        return criteria.list();
    }

    public <T> T remove(Serializable pk, Class<?> klass) {
        T t = (T) findByPrimaryKey(klass, pk);
        return remove(t);
    }

    public <T> T remove(T t) {
        getHibernateTemplate().delete(t);
        return t;
    }

    @Override
    public void remove(Collection<?> collection) {
        if (UtilValidator.isEmpty(collection))
            return;
        for (Object entity : collection)
            remove(entity);
    }

    /**
     * This method returns all the entities irrespective of
     * its active or not.
     */
    public <T> List<T> getAll(Class<T> klass, boolean onlyEnabled) {
        if (!onlyEnabled) {
            getHibernateTemplate().setFilterNames(new String[]{});
            getSession().disableFilter("EnabledFilter");
        }
        Criteria criteria = getSession().createCriteria(klass);
//	criteria.setCacheable(true);
        List<T> list = unify(criteria.list());
        return list;
    }

    public <T> List<T> getAll(Class<T> klass) {
        return getAll(klass, true);
    }

    public void save(Collection<?> collection) {
        save(collection.toArray());
    }

    public <T> void save(T... ts) {
        for (Object element : ts) {
            save(element);
        }
    }

    /**
     * This method is just to remove duplicates resulted by hibernate because of collection mappings.
     */
    public <T extends BaseEntity> List<T> unify(List<T> list) {
        if (UtilValidator.isEmpty(list)) return list;
        Set<T> unifier = new HashSet<T>();
        unifier.addAll(list);
        return new ArrayList<T>(unifier);
    }

    /**
     * @author Sandeep Prusty
     * This has similar behaviour as Hibernate QBE. In case of QBE hibernate only considers Strings.
     * In contrast this api consider other obects but uses equality for other types.
     */
    public <T> List<T> simulateExampleSearch(String[] searchFields, Object exampleObject) {
        Criteria criteria = getSession().createCriteria(exampleObject.getClass());
        for (String searchField : searchFields) {
            Object fieldValue = UtilReflection.getNestedFieldValue(exampleObject, searchField);
            if (fieldValue == null) continue;
            criteria.add(fieldValue instanceof String ? Restrictions.ilike(searchField, (String) fieldValue,
                    MatchMode.START) : Restrictions.eq(searchField, fieldValue));
        }
        return criteria.list();
    }

    public <T> T merge(T entity) {
        return (T) getSession().merge(entity);
    }

    public void evict(Object entity) {
        getSession().evict(entity);
    }

    public static void addFromThruToCriteria(Criteria criteria, Date from, Date thru) {
        if (from != null)
            criteria.add(Restrictions.ge("createdTxTimestamp", UtilDateTime.getDayStart(from)));
        if (thru != null)
            criteria.add(Restrictions.le("createdTxTimestamp", UtilDateTime.getDayEnd(thru)));
    }

    @Override
    public void refresh(Object entity) {
        getSession().refresh(entity);
    }

    protected void addSoapNoteDateCriteria(Criteria criteria, Date fromDate, Date thruDate) {
        if (fromDate != null)
            criteria.add(Restrictions.ge("date", UtilDateTime.getDayStart(fromDate)));
        if (thruDate != null)
            criteria.add(Restrictions.le("date", UtilDateTime.getDayEnd(thruDate)));
    }
}