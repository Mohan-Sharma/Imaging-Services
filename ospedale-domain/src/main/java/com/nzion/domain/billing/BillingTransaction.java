package com.nzion.domain.billing;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;

import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.domain.product.common.Money;

/**
 * @author Sandeep Prusty
 * 
 *         16-Sep-2011
 */

@Entity
@Filters( {
		@Filter(name = "EnabledFilter", condition = "(IS_ACTIVE=1 OR IS_ACTIVE IS NULL)") })
public class BillingTransaction extends IdGeneratingBaseEntity {

	private Date billDate;

	private Money totalAmount;

	private Money collectedAmount;
	
	private Money totalAmountToPay;

	private Money consultationCharge;



	public BillingTransaction() {

	}

	/*public BillingTransaction(PatientSoapNote soapNote) {
	this.soapNote = soapNote;
	}
*/
	public Date getBillDate() {
	return billDate;
	}

	public void setBillDate(Date billDate) {
	this.billDate = billDate;
	}

	@Embedded
	@AttributeOverride(name = "amount", column = @Column(name = "CONSULTATION_PRICE"))
	@AssociationOverride(name = "currency", joinColumns = { @JoinColumn(name = "COSULATION_AMOUNT_CURRENCY_ID") })
	public Money getConsultationCharge() {
	return consultationCharge = (consultationCharge == null) ? new Money() : consultationCharge;
	}

	public void setConsultationCharge(Money consultationCharge) {
	this.consultationCharge = consultationCharge;
	}

	
	@Embedded
	public Money getTotalAmount() {
	return totalAmount = (totalAmount == null ? new Money() : totalAmount);
	}

	public void setTotalAmount(Money totalAmount) {
	this.totalAmount = totalAmount;
	remainingAmount = getTotalAmount().minus(getCollectedAmount()).getAmount();
	}

	@Embedded
	@AttributeOverride(name = "amount", column = @Column(name = "COLLECTED_PRICE"))
	@AssociationOverride(name = "currency", joinColumns = { @JoinColumn(name = "COLLECTED_CURRENCY_ID") })
	public Money getCollectedAmount() {
	return collectedAmount = (collectedAmount == null ? new Money() : collectedAmount);
	}

	public void setCollectedAmount(Money collectedAmount) {
	this.collectedAmount = collectedAmount;
	remainingAmount = getTotalAmount().minus(getCollectedAmount()).getAmount();
	}

	@Embedded
	@AttributeOverride(name = "amount", column = @Column(name = "PAYABLE_PRICE"))
	@AssociationOverride(name = "currency", joinColumns = { @JoinColumn(name = "PAYABLE_CURRENCY_ID") })
	public Money getTotalAmountToPay() {
	return totalAmountToPay = (totalAmountToPay == null ? new Money() : totalAmountToPay);
	}

	public void setTotalAmountToPay(Money totalAmountToPay) {
	this.totalAmountToPay = totalAmountToPay;
	}

	private BigDecimal remainingAmount;

	@Transient
	public BigDecimal getRemainingAmount() {
	return remainingAmount;
	}


	private static final long serialVersionUID = 1L;
}
