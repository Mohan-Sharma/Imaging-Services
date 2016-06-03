package com.nzion.repository.impl;

import com.nzion.domain.*;
import com.nzion.domain.Party.PartyType;
import com.nzion.domain.emr.lab.LabDepartment;
import com.nzion.domain.emr.lab.Laboratories;
import com.nzion.repository.UserLoginRepository;
import com.nzion.util.UtilDateTime;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.*;

/**
 * @author Sandeep Prusty Apr 6, 2010
 */

public class HibernateUserLoginRepository extends HibernateBaseRepository implements UserLoginRepository {

    public UserLogin loadUserByUsername(final String username) {
        Session s = getSession();
        s.disableFilter("PracticeFilter");
        Criteria c = s.createCriteria(UserLogin.class);
        Disjunction disjunction = (Disjunction) Restrictions.disjunction()
                .add(Restrictions.isNull("passwordValidThruDate"))
                .add(Restrictions.ge("passwordValidThruDate", new Date()));
        c.add(Restrictions.eq("username", username)).add(disjunction);
        UserLogin ul = (UserLogin) c.setCacheable(true).uniqueResult();
        return ul;
    }

    public List<? extends Person> relevantEmployeeLookup(Map<String, Object> searchData) {
        Criteria employeeCriteria = buildLookupCriteria(Employee.class, searchData);
        employeeCriteria.add(Restrictions.isNotNull("partyType"));
        List<Employee> employees = new LinkedList<Employee>();
        for (Object p : employeeCriteria.list()) {
            Employee emp = (Employee) p;
            if (emp.getUserLogin() == null) employees.add(emp);
        }
        return unify(employees);
    }

    public List<? extends Person> relevantPersonLookup(Map<String, Object> searchData) {
        Criteria personCriteria = buildLookupCriteria(Person.class, searchData);
        personCriteria.add(Restrictions.isNotNull("partyType"));
        List<Person> persons = new LinkedList<Person>();
        for (Object p : personCriteria.list()) {
            Person person = (Person) p;
            if (person.getUserLogin() == null) persons.add(person);
        }
        return unify(persons);
    }

    private Criteria buildLookupCriteria(Class<?> klass, Map<String, Object> searchData) {
        Criteria criteria = getSession().createCriteria(klass);
        for (Map.Entry<String, Object> entry : searchData.entrySet()) {
            if (entry.getValue() == null) continue;
            if (entry.getValue() instanceof String) {
                criteria.add(Restrictions.ilike(entry.getKey(), (String) entry.getValue(), MatchMode.ANYWHERE));
                continue;
            }
            criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
        }
        return criteria.setCacheable(true);

    }

    @Override
    public void updateLockedFlag(UserLogin login) {
        Query query = getSession().createQuery("update UserLogin set locked=:locked where id=:id");
        query.setParameter("locked", login.isLocked());
        query.setParameter("id", login.getId());
        query.executeUpdate();
    }

    public void updatePassword(UserLogin login) {
        Query query = getSession()
                .createQuery(
                        "update UserLogin set password=:password, pwdChangedTime=:pwdChangedTime, requirePasswordChange=:requirePasswordChange, accountNonExpired=:accountNonExpired "
                                + ", accountNonLocked=:accountNonLocked, active=:active, credentialsNonExpired=:credentialsNonExpired, locked=:locked, successiveFailedLogins=:successiveFailedLogins, secretQuestion=:secretQuestion, secretQuestionAnswer=:secretQuestionAnswer, acceptedTermsAndConditions=:acceptedTermsAndConditions where id=:id");
        query.setParameter("id", login.getId());
        query.setParameter("password", login.getPassword());
        query.setParameter("pwdChangedTime", login.getPwdChangedTime());
        query.setParameter("requirePasswordChange", login.isRequirePasswordChange());
        query.setParameter("accountNonExpired", login.isAccountNonExpired());
        query.setParameter("accountNonLocked", login.isAccountNonLocked());
        query.setParameter("active", login.isActive());
        query.setParameter("credentialsNonExpired", login.isCredentialsNonExpired());
        query.setParameter("locked", login.isLocked());
        query.setParameter("successiveFailedLogins", login.getSuccessiveFailedLogins());
        query.setParameter("secretQuestion", login.getSecretQuestion());
        query.setParameter("secretQuestionAnswer", login.getSecretQuestionAnswer());
        query.setParameter("acceptedTermsAndConditions", login.isAcceptedTermsAndConditions());
        query.executeUpdate();
    }

    @Override
    public void resetFailedLoginCount(UserLogin userLogin) {
        Query query = getSession().createQuery(
                "update UserLogin set successiveFailedLogins=:successiveFailedLogins where id=:id");
        query.setParameter("id", userLogin.getId());
        query.setParameter("successiveFailedLogins", 0);
        query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Person> getImpersonatedPersonFor(Person logedInPerson) {
        Date currentDate = UtilDateTime.getDayStart(new Date());
        Criteria criteria = getSession().createCriteria(PersonDelegation.class);
        criteria.add(Restrictions.eq("personToBeDelegated", logedInPerson));
        criteria.add(Restrictions.le("fromDate", currentDate));
        criteria.add(Restrictions.ge("thruDate", currentDate));
        criteria.setProjection(Projections.property("person"));
        return criteria.list();
    }

    @Override
    public UserLogin getUserLoginForPerson(Person person) {
        return (UserLogin) getSession().createCriteria(UserLogin.class).add(Restrictions.eq("person", person))
                .setCacheable(true).uniqueResult();
    }

    @Override
    public List<UserLogin> getEmergencyLogins() {
        return getSession().createCriteria(UserLogin.class).add(Restrictions.isNotNull("passwordValidThruDate"))
                .setCacheable(true).list();
    }

    @Override
    public Long noOfUserLoginFor(Patient patient) {
        return (Long) getSession().createQuery("SELECT COUNT(*) FROM UserLogin where person.id = " + patient.getId())
                .uniqueResult();
    }

    @Override
    public <T> List<T> getAllEmergenyAcessUserLoginsPageWise(int pageSize, int firstRecord) {
        Session session = getSession();
        session.disableFilter("EnabledFilter");
        Criteria criteria = session.createCriteria(UserLogin.class);
        criteria.setFirstResult(firstRecord);
        criteria.setMaxResults(pageSize);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.add(Restrictions.isNotNull("patient"));
        return criteria.setCacheable(true).list();
    }

    @Override
    public Long getCountForEmergenyAcessUserLogins() {
        Session session = getSession();
        session.disableFilter("EnabledFilter");
        Criteria criteria = session.createCriteria(UserLogin.class);
        criteria.add(Restrictions.isNotNull("patient"));
        criteria.setProjection(Projections.count("id"));
        return (Long) criteria.setCacheable(true).uniqueResult();
    }

    @Override
    public List<?> searchEmergenyAccessUserLogins(String searchString, String[] fields) {
        Session session = getSession();
        session.disableFilter("EnabledFilter");
        Criteria criteria = session.createCriteria(UserLogin.class);
        Disjunction oredCriterions = Restrictions.disjunction();
        criteria.add(oredCriterions);
        criteria.createAlias("patient", "patient");
        criteria.createAlias("person", "person");
        for (String field : fields)
            oredCriterions.add(Restrictions.like(field, searchString, MatchMode.ANYWHERE));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.setCacheable(true).list();
    }

    @Override
    public List<UserLogin> getUserLoginsFor(Collection<PartyType> partyTypes) {
        Criteria criteria = getSession().createCriteria(UserLogin.class);
        criteria.createCriteria("person").add(Restrictions.in("partyType", partyTypes));
        criteria.add(Restrictions.isNull("patient"));
        return criteria.list();
    }

    @Override
	public List<LabDepartment> getLabDepartments() {
	Criteria criteria = getSession().createCriteria(LabDepartment.class);
	return criteria.list();
	}

	@Override
	public List<Laboratories> getLaboratories() {
	Criteria criteria = getSession().createCriteria(Laboratories.class);
	return criteria.list();
	}
    
}