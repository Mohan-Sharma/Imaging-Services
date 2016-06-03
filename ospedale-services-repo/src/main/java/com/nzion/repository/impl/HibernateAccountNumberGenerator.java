package com.nzion.repository.impl;

import com.nzion.domain.Person;
import com.nzion.domain.annot.AccountNumberField;
import com.nzion.domain.base.IdSequence;
import com.nzion.domain.billing.Invoice;
import com.nzion.domain.billing.InvoiceType;
import com.nzion.repository.AccountNumberGenerator;
import com.nzion.util.Infrastructure;
import com.nzion.util.UtilReflection;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Sandeep Prusty
 *         Apr 14, 2010
 */
@Transactional
public class HibernateAccountNumberGenerator implements AccountNumberGenerator {

    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(HibernateAccountNumberGenerator.class);

    public String generate(Object accountNumberObject) {
        String accountNumber = null;
        Session newSession = null;
        Criteria c = null;
        newSession = Infrastructure.getSessionFactory().openSession();
        if (Person.class.equals(accountNumberObject.getClass())) {
            newSession.disableFilter("PracticeFilter");
            c = newSession.createCriteria(IdSequence.class).setFetchMode("practice", FetchMode.SELECT);
        } else {
            c = newSession.createCriteria(IdSequence.class);
        }
        if (accountNumberObject instanceof Invoice) {
            Invoice inv = (Invoice) accountNumberObject;
            if (InvoiceType.OPD.equals(inv.getInvoiceType()))
                c.add(Restrictions.eq("description", "0PD Billing"));
            else if ((InvoiceType.CASUALTY.equals(inv.getInvoiceType())))
                c.add(Restrictions.eq("description", InvoiceType.CASUALTY.name()));

        }
        c.add(Restrictions.eq("entityName", accountNumberObject.getClass().getName()));
        synchronized (this) {
            IdSequence sequence = (IdSequence) c.setCacheable(true).uniqueResult();
            if (sequence == null)
                throw new RuntimeException(accountNumberObject.getClass().getName()
                        + " is not associated with any Sequence.");

            accountNumber = sequence.buildId();
            if (log.isEnabledFor(Level.DEBUG)) log.debug(" Generated account number = " + accountNumber);
            newSession.saveOrUpdate(sequence);
        }
        newSession.flush();
        newSession.clear();
        newSession.close();
        return accountNumber;
    }

    public void generateAndSetAccountNumber(Object accountNumberObject, String accountNumberField) {
        String accountNumber = generate(accountNumberObject);
        UtilReflection.setFieldValue(accountNumberObject, accountNumberField, accountNumber, String.class);
    }

    @SuppressWarnings("unchecked")
    private List<String> getAccountNumbers(Object accountNumberObject) {
        Session session = Infrastructure.getSessionFactory().openSession();
        Class klass = accountNumberObject.getClass();
        AccountNumberField accountNumberField = (AccountNumberField) klass.getAnnotation(AccountNumberField.class);
        String queryStr = "select " + accountNumberField.value() + " from " + klass.getSimpleName();
        return session.createQuery(queryStr).setCacheable(true).list();
    }
}