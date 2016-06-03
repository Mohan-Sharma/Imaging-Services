package com.nzion.domain.pms;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class PolicyLimit implements Serializable{

	private static final long serialVersionUID = 1L;

	public Float getYtd() {
	return ytd;
	}
	public Float getAnnual() {
	return annual;
	}
	public Float getLifetime() {
	return lifetime;
	}
	public Float getInsPayment() {
	return insPayment;
	}
	public void setYtd(Float ytd) {
	this.ytd = ytd;
	}
	public void setAnnual(Float annual) {
	this.annual = annual;
	}
	public void setLifetime(Float lifetime) {
	this.lifetime = lifetime;
	}
	public void setInsPayment(Float insPayment) {
	this.insPayment = insPayment;
	}
	private Float ytd;
	private Float annual;
	private Float lifetime;
	private Float insPayment;
}
