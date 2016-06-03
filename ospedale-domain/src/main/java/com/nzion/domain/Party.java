/*
 * header file
 */
package com.nzion.domain;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.domain.base.IdGeneratingBaseEntity;

@MappedSuperclass
public abstract class Party extends IdGeneratingBaseEntity {

	private static final long serialVersionUID = 1L;

	protected static final Map<Class<? extends Party>, PartyType> CLASS_PARTY_TYPE_MAP = new HashMap<Class<? extends Party>, PartyType>();

	protected static final Map<PartyType, Class<? extends Party>> PARTY_TYPE_CLASS_MAP = new HashMap<PartyType, Class<? extends Party>>();

	private String accountNumber;

	protected PartyType partyType;

	public Party(){	}
	
	public Party(PartyType partyType){
	this.partyType = partyType;
	CLASS_PARTY_TYPE_MAP.put(getClass(), partyType);
	PARTY_TYPE_CLASS_MAP.put(partyType, getClass());
	}

	@Column(name = "PARTY_TYPE")
	@Enumerated(EnumType.STRING)
	public PartyType getPartyType() {
	return partyType;
	}

	public void setPartyType(PartyType partyType) {
	this.partyType = partyType;
	}

	@Column(name = "ACCOUNT_NUMBER", nullable = false)
	public String getAccountNumber() {
	return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
	this.accountNumber = accountNumber;
	}

	public static enum PartyType {
		CASEMANAGER, PATIENT, PROVIDER, PERSON, PCP, REFERRAL,EMPLOYEE,TECHNICIAN,NURSE;
		
		public static PartyType[] getUiPersons() {
		return new PartyType[] { CASEMANAGER, PCP, REFERRAL, EMPLOYEE };
		}
	}

	public static PartyType getPartyType(Class<?> klass){
	return CLASS_PARTY_TYPE_MAP.get(klass);
	}

	public static Class<? extends Party> getClassForPartyType(PartyType type){
	return PARTY_TYPE_CLASS_MAP.get(type);
	}

	public static Class<? extends Party> getClassForPartyType(String type){
	return getClassForPartyType(PartyType.valueOf(type));
	}

	public static void setPartyMap(Class<? extends Party> klass, PartyType partyTye) {
	CLASS_PARTY_TYPE_MAP.put(klass, partyTye);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((accountNumber == null) ? 0 : accountNumber.hashCode());
		result = prime * result
				+ ((partyType == null) ? 0 : partyType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
	if (this == obj) {
		return true;
	}
	if (!super.equals(obj)) {
		return false;
	}
	if (getClass() != obj.getClass()) {
		return false;
	}
	Party other = (Party) obj;
	if (accountNumber == null) {
		if (other.accountNumber != null) {
			return false;
		}
	} else if (!accountNumber.equals(other.accountNumber)) {
		return false;
	}
	if (partyType == null) {
		if (other.partyType != null) {
			return false;
		}
	} else if (!partyType.equals(other.partyType)) {
		return false;
	}
	return true;
	}
}