package com.nzion.view.component;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.nzion.domain.Employee;
import com.nzion.service.common.CommonCrudService;
import com.nzion.util.Infrastructure;

public class EmployeeCompletionService implements AutoCompletionService {
	
	private final CommonCrudService ccs = Infrastructure.getSpringBean("commonCrudService");

	@Override
	public List<AutoCompletionItem> search(String query) {
	Criteria c = Infrastructure.getSessionFactory().getCurrentSession().createCriteria(Employee.class);
	List<Employee> employees = c.add(Restrictions.ilike("firstName", query, MatchMode.START)).addOrder(Order.desc("firstName")).list();
	List<AutoCompletionItem> cis = new ArrayList<AutoCompletionItem>(employees.size());
	for (Employee e : employees) {
		cis.add(new AutoCompletionItem(e.getId(), ccs.getFormattedName(e)));
	}
	return cis;
	}

	@Override
	public Object convertToObject(AutoCompletionItem selItem) {
	Employee e = ccs.getById(Employee.class, selItem.getId());
	return e;
	}
}
