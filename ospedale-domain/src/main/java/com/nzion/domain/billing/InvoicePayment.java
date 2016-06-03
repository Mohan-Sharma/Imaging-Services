package com.nzion.domain.billing;

import java.util.Date;

import javax.persistence.*;

import com.nzion.domain.Enumeration;
import com.nzion.domain.annot.AccountNumberField;
import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.domain.product.common.Money;

/**
 * @author Sandeep Prusty
 *         <p/>
 *         16-Sep-2011
 *         <p/>
 *         Updated By Dharanesha.K
 *         18-Jan-2012
 */

@Entity
@AccountNumberField("receiptNumber")
public class InvoicePayment extends IdGeneratingBaseEntity {

    private static final long serialVersionUID = 1L;

    private Enumeration paymentMethod;

    private Money amount;

    private Invoice invoice;

    private Date paymentDate;

    private String transactionNumb;

    private String chequeOrDdNo;

    private Date chequeOrDdDate;

    private String bankName;

    private String receiptNumber;

    private String insuranceScheme;

    private String insuranceName;

    private String policyNo;

    private String authorizedId;

    private boolean isAdvance;

    private PaymentType paymentType;


    public InvoicePayment() {
        this.paymentDate = new Date();
    }

    public InvoicePayment(Enumeration paymentMethod, Invoice invoice, Money amount, PaymentType paymentType) {
        this.paymentMethod = paymentMethod;
        this.invoice = invoice;
        this.amount = amount;
        this.paymentType = paymentType;
        this.paymentDate = new Date();
    }


    @Column
    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }


    public boolean isAdvance() {
        return isAdvance;
    }

    public void setAdvance(boolean isAdvance) {
        this.isAdvance = isAdvance;
    }

    public String getAuthorizedId() {
        return authorizedId;
    }

    public void setAuthorizedId(String authorizedId) {
        this.authorizedId = authorizedId;
    }

    public String getInsuranceScheme() {
        return insuranceScheme;
    }

    public void setInsuranceScheme(String insuranceScheme) {
        this.insuranceScheme = insuranceScheme;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    @ManyToOne
    @JoinColumn(name = "PAYMENT_METHOD_ID")
    public Enumeration getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Enumeration paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Embedded
    public Money getAmount() {
        return amount = (amount == null ? new Money() : amount);
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    @ManyToOne
    @JoinColumn(name = "INVOICE_ID")
    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }


    public String getTransactionNumb() {
        return transactionNumb;
    }

    public void setTransactionNumb(String transactionNumb) {
        this.transactionNumb = transactionNumb;
    }

    public String getChequeOrDdNo() {
        return chequeOrDdNo;
    }

    public void setChequeOrDdNo(String chequeOrDdNo) {
        this.chequeOrDdNo = chequeOrDdNo;
    }

    public Date getChequeOrDdDate() {
        return chequeOrDdDate;
    }

    public void setChequeOrDdDate(Date chequeOrDdDate) {
        this.chequeOrDdDate = chequeOrDdDate;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((paymentMethod == null) ? 0 : paymentMethod.hashCode());
        result = prime * result + ((paymentDate == null) ? 0 : paymentDate.hashCode());
        return result;
    }

    /*@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (getClass() != obj.getClass()) return false;
        InvoicePayment other = (InvoicePayment) obj;
        if (paymentMethod == null) {
            if (other.paymentMethod != null) return false;
        } else if (!paymentMethod.getEnumCode().equals(other.paymentMethod.getEnumCode())) return false;
        if (paymentDate == null) {
            if (other.paymentDate != null) return false;
        } else if (!paymentDate.equals(other.paymentDate)) return false;
        return true;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvoicePayment)) return false;
        if (!super.equals(o)) return false;

        InvoicePayment that = (InvoicePayment) o;

        if (!paymentDate.equals(that.paymentDate)) return false;
        if (!paymentMethod.equals(that.paymentMethod)) return false;

        return true;
    }

    public String getInsuranceName() {
        return insuranceName;
    }

    public void setInsuranceName(String insuranceName) {
        this.insuranceName = insuranceName;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    @Enumerated(EnumType.STRING)
    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }


}
