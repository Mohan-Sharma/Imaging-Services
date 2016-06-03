package com.nzion.domain.billing;

import com.nzion.domain.Location;
import com.nzion.domain.Speciality;
import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.domain.base.LocationAware;
import com.nzion.util.UtilValidator;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Nth
 * Date: 12/24/12
 * Time: 3:12 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Filter(name = "LocationFilter", condition = "( :locationId=LOCATION_ID OR LOCATION_ID IS NULL )")
public class AcctgTransaction extends IdGeneratingBaseEntity implements LocationAware {

    private AcctgTransTypeEnum acctgTransTypeEnum;

    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;

    private Location location;

    private List<AcctgTransactionEntry> acctgTransactionEntries;

    private Long invoiceId;

    private Long paymentId;

    AcctgTransaction() {

    }

    private AcctgTransaction(String description, Date transactionDate, Location location, AcctgTransTypeEnum acctgTransTypeEnum) {
        this.description = description;
        this.transactionDate = transactionDate;
        this.location = location;
        this.acctgTransTypeEnum = acctgTransTypeEnum;
    }


    public static AcctgTransaction createWithInvoice(Long invoiceId, String description, Date transactionDate, Location location, AcctgTransTypeEnum acctgTransTypeEnum) {
        AcctgTransaction acctgTransaction = new AcctgTransaction(description, transactionDate, location, acctgTransTypeEnum);
        acctgTransaction.invoiceId = invoiceId;
        return acctgTransaction;
    }

    public static AcctgTransaction createWithInvoicePayment(Long paymentId, String description, Date transactionDate, Location location, AcctgTransTypeEnum acctgTransTypeEnum) {
        AcctgTransaction acctgTransaction = new AcctgTransaction(description, transactionDate, location, acctgTransTypeEnum);
        acctgTransaction.paymentId = paymentId;
        return acctgTransaction;
    }


    public void addAllAccountingTransactionEntries(List<AcctgTransactionEntry> acctgTransactionEntries) {
        if (UtilValidator.isEmpty(this.acctgTransactionEntries)) {
            this.acctgTransactionEntries = new ArrayList<AcctgTransactionEntry>();
        }
        this.acctgTransactionEntries.addAll(acctgTransactionEntries);
    }

    public void validateAccountingTransactionEntry(List<AcctgTransactionEntry> creditEntries, List<AcctgTransactionEntry> debitEntries) {
        BigDecimal creditTotal = BigDecimal.ZERO;
        BigDecimal debitTotal = BigDecimal.ZERO;
        for (AcctgTransactionEntry creditEntry : creditEntries) {
            creditTotal = creditTotal.add(creditEntry.getAmount());
        }
        for (AcctgTransactionEntry debitEntry : debitEntries) {
            debitTotal = debitTotal.add(debitEntry.getAmount());
        }
        if (!(creditTotal.compareTo(debitTotal) == 0)) {
        }
    }

    @Enumerated(value = EnumType.STRING)
    public AcctgTransTypeEnum getAcctgTransTypeEnum() {
        return acctgTransTypeEnum;
    }

    public void setAcctgTransTypeEnum(AcctgTransTypeEnum acctgTransTypeEnum) {
        this.acctgTransTypeEnum = acctgTransTypeEnum;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    @OneToMany(targetEntity = AcctgTransactionEntry.class)
    @Cascade(CascadeType.ALL)
    public List<AcctgTransactionEntry> getAcctgTransactionEntries() {
        return acctgTransactionEntries;
    }

    public void setAcctgTransactionEntries(List<AcctgTransactionEntry> acctgTransactionEntries) {
        this.acctgTransactionEntries = acctgTransactionEntries;
    }


    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    @ManyToOne(targetEntity = Location.class)
    @JoinColumn(name = "LOCATION_ID", nullable = false, updatable = false)
    public Location getLocation() {
        return this.location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }


}