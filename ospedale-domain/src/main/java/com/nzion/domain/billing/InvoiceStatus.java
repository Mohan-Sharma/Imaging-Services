package com.nzion.domain.billing;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;

import com.nzion.domain.base.IdGeneratingBaseEntity;

@Entity
public class InvoiceStatus extends IdGeneratingBaseEntity {

	private Invoice invoice;

	private InvoiceStatusItem status;

	private Date date;

	public InvoiceStatus() {
	}

	public InvoiceStatus(Invoice invoice, InvoiceStatusItem status, Date date) {
	this.invoice = invoice;
	this.status = status;
	this.date = date;
	}

	@OneToOne
	public Invoice getInvoice() {
	return invoice;
	}

	public void setInvoice(Invoice invoice) {
	this.invoice = invoice;
	}

	@Enumerated(EnumType.STRING)
	public InvoiceStatusItem getStatus() {
	return status;
	}

	public void setStatus(InvoiceStatusItem status) {
	this.status = status;
	}

	public Date getDate() {
	return date;
	}

	public void setDate(Date date) {
	this.date = date;
	}

	private static final long serialVersionUID = 1L;
}
