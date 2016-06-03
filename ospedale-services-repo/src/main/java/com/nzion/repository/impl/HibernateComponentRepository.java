/**
 * @author shwetha
 * Dec 1, 2010 
 */
package com.nzion.repository.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.nzion.domain.Employee;
import com.nzion.domain.Party;
import com.nzion.domain.Person;
import com.nzion.domain.Provider;
import com.nzion.repository.ComponentRepository;
import com.nzion.repository.common.CommonCrudRepository;

@SuppressWarnings("unchecked")
public class HibernateComponentRepository extends HibernateBaseRepository implements ComponentRepository {

	private CommonCrudRepository commonCrudRepository;
	
	public void setCommonCrudRepository(CommonCrudRepository commonCrudRepository) {
	this.commonCrudRepository = commonCrudRepository;
	}

	@Override
	public <T> List<T> searchEntities(String searchString,Class<T> klass,String[] searchColumns) {
	return commonCrudRepository.search(searchString, klass, true,0 ,10 ,null, false,searchColumns);
	}
	
	public <T> List<T> findByParamsMap(Class<T> klass, Map<String, Object> searchParams) {
	Criteria criteria = getSession().createCriteria(klass);
	criteria.add(Restrictions.or(Restrictions.isNull("active"), Restrictions.eq("active", Boolean.TRUE)));
	if(Party.class.isAssignableFrom(klass))
		criteria.add(Restrictions.eq("partyType", Party.getPartyType(klass)));
	criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	for (Map.Entry<String, Object> entry : searchParams.entrySet()) {
		if (entry.getValue() == null) continue;
		if (entry.getValue() instanceof String) {
			criteria.add(Restrictions.ilike(entry.getKey(), (String) entry.getValue(), MatchMode.ANYWHERE));
			continue;
		}
		criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
	}
	return criteria.setCacheable(true).list();
	}

	public <T> List<T> getMasterDataStartingWith(Class<T> klass,String value,String attribute) {
	Criteria criteria = getSession().createCriteria(klass);
	criteria.add(Restrictions.like(attribute, value, MatchMode.START));
	criteria.addOrder(Order.asc(attribute));
	criteria.setCacheable(true);
	return criteria.setCacheable(true).list();
	}
	
	public <T> List<T> getMasterDataStartingWith(Class<T> klass,String value,String attribute,int pageNo) {
	Criteria criteria = getSession().createCriteria(klass);
	criteria.add(Restrictions.like(attribute, value, MatchMode.START));
	criteria.setFirstResult(pageNo*30);
	criteria.setMaxResults(30);
	criteria.addOrder(Order.asc(attribute));
	criteria.setCacheable(true);
	return criteria.setCacheable(true).list();
	}
	
	public int getTotalSizeStartingWith(Class klass,String value,String attribute) {
	Query q = getSession().createQuery("SELECT COUNT(*) FROM "+klass.getName()+" WHERE "+attribute +" like '"+value+"%'");
	return ((Long)q.setCacheable(true).uniqueResult()).intValue();
	}
	
	
	public List<? extends Person> getEmployee(String value){
	Query q = getSession().createQuery("SELECT new com.nzion.domain.Employee(id,firstName,lastName,contacts) from "+Employee.class.getName()+" WHERE firstName like '"+value+"%' or lastName like '"+value+"%'");
	List<Person> l = q.list();
	q = getSession().createQuery("SELECT new com.nzion.domain.Provider(id,firstName,lastName,contacts) from "+Provider.class.getName()+" WHERE firstName like '"+value+"%' or lastName like '"+value+"%'");
	l.addAll(q.list());
	Collections.sort(l,new Comparator<Person>() {
	@Override
	public int compare(Person o1, Person o2) {
	return o1.getFirstName().compareTo(o2.getFirstName());
	}
	});
	return l;
	}

}