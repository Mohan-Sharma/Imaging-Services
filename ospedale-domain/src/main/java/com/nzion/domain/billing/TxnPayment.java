package com.nzion.domain.billing;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.nzion.domain.Enumeration;
import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.domain.product.common.Money;

/**
 * @author Sandeep Prusty
 * 
 *         16-Sep-2011
 */

@Entity
public class TxnPayment extends IdGeneratingBaseEntity {

	private static final long serialVersionUID = 1L;

	private Enumeration paymentMethod;

	private Money amount;

	private BillingTransaction txn;

	public TxnPayment() {

	}

	public TxnPayment(Enumeration paymentMethod, BillingTransaction billingTransaction, Money amount) {
	this.paymentMethod = paymentMethod;
	this.txn = billingTransaction;
	this.amount = amount;
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
	@JoinColumn(name = "TXN_ID")
	public BillingTransaction getTxn() {
	return txn;
	}

	public void setTxn(BillingTransaction txn) {
	this.txn = txn;
	}

	@Override
	public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((paymentMethod == null) ? 0 : paymentMethod.hashCode());
	return result;
	}

	@Override
	public boolean equals(Object obj) {
	if (this == obj) return true;
	if (getClass() != obj.getClass()) return false;
	TxnPayment other = (TxnPayment) obj;
	if (paymentMethod == null) {
		if (other.paymentMethod != null) return false;
	} else
		if (!paymentMethod.getEnumCode().equals(other.paymentMethod.getEnumCode())) return false;
	return true;
	}
	
	
}
