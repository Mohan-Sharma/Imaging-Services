package com.nzion.domain;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class Condition {

	private String lhs;
	private Object rhs;
	private SearchOperator searchOperator;

	public Condition() {
	}

	public Condition(String lhs, SearchOperator searchOperator, Object rhs) {
	this.lhs = lhs;
	this.searchOperator = searchOperator;
	this.rhs = rhs;
	}

	public static Condition makeCondition(String lhs, SearchOperator searchOperator, Object rhs) {
	return new Condition(lhs, searchOperator, rhs);
	}

	public Criteria createCriteria(Criteria criteria) {
	switch (searchOperator) {
	case EQUALS:
		criteria = criteria.add(Restrictions.eq(lhs, rhs));
		break;
	default:
		criteria = criteria.add(Restrictions.eq(lhs, rhs));
		break;
	}
	return criteria;
	}

	public String getLhs() {
	return lhs;
	}

	public void setLhs(String lhs) {
	this.lhs = lhs;
	}

	public Object getRhs() {
	return rhs;
	}

	public void setRhs(Object rhs) {
	this.rhs = rhs;
	}

	public SearchOperator getSearchOperator() {
	return searchOperator;
	}

	public void setSearchOperator(SearchOperator searchOperator) {
	this.searchOperator = searchOperator;
	}
}