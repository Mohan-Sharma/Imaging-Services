package com.nzion.repository.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.nzion.domain.PasswordPolicy;
import com.nzion.domain.Practice;
import com.nzion.repository.PasswordPolicyRepository;

public class HibernatePasswordPolicyRepositoryImpl extends HibernateBaseRepository implements PasswordPolicyRepository {

	public PasswordPolicy getPasswordPolicy() {
	Criteria criteria = getSession().createCriteria(PasswordPolicy.class);
	return (PasswordPolicy)criteria.uniqueResult();
	}

	public PasswordPolicy getDefaultPasswordPolicy() {
	PasswordPolicy pwdPolicy = (PasswordPolicy) getSession().createCriteria(PasswordPolicy.class).setCacheable(true).uniqueResult();
	return pwdPolicy;
	}
}
