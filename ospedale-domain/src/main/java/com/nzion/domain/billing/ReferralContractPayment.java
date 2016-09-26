package com.nzion.domain.billing;

import com.nzion.domain.ReferralContract;
import com.nzion.domain.base.IdGeneratingBaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Nthdimenzion on 23-Sep-16.
 */
@Entity
public class ReferralContractPayment extends IdGeneratingBaseEntity {
    private Invoice invoice;

    private ReferralContract referralContract;

    private BigDecimal amountPaid;

    private String modeOfPaymentType;

    private String notes;

    private Date paymentDate;

    @OneToOne
    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    @OneToOne
    public ReferralContract getReferralContract() {
        return referralContract;
    }

    public void setReferralContract(ReferralContract referralContract) {
        this.referralContract = referralContract;
    }

    @Column(precision = 19, scale = 3 ,columnDefinition="DECIMAL(19,3)")
    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getModeOfPaymentType() {
        return modeOfPaymentType;
    }

    public void setModeOfPaymentType(String modeOfPaymentType) {
        this.modeOfPaymentType = modeOfPaymentType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Temporal(value = TemporalType.TIMESTAMP)
    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
}
