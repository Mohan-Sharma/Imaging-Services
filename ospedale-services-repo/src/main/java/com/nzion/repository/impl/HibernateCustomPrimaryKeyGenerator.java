package com.nzion.repository.impl;

import java.io.Serializable;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nzion.domain.base.PKGenerator;
import com.nzion.util.UtilReflection;

@Repository("customPrimaryKeyGenerator")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class HibernateCustomPrimaryKeyGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SessionImplementor sessionImplementor, Object object) throws HibernateException {
	Session newSession = null;
	try {
		Object id = UtilReflection.getValue(object, "id");
		if (id != null) return (Serializable) id;
		Session session = (Session) sessionImplementor;
		newSession = session.getSessionFactory().openSession();
		Criteria criteria = newSession.createCriteria(PKGenerator.class);
		criteria.add(Restrictions.eq("entityClassName", object.getClass().getName()));
		PKGenerator pkGenerator = (PKGenerator) criteria.uniqueResult();
		if (pkGenerator == null)
			throw new RuntimeException(object.getClass().getName() + " is not associated with any Primary Key.");

		id = pkGenerator.getNewPK();
		newSession.saveOrUpdate(pkGenerator);
		newSession.flush();
		return id.toString();
	} finally {
		if (newSession != null) {
			newSession.clear();
			newSession.close();
		}
	}
	}

}
