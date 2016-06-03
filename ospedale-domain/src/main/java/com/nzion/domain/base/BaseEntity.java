/*
 * header file
 */
package com.nzion.domain.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.AccessType;

import com.nzion.domain.Person;

// TODO: Auto-generated Javadoc
/**
 * The Class BaseEntity.
 * 
 * @author Sandeep Prusty Apr 1, 2010
 */
@MappedSuperclass
@AccessType("property")
public abstract class BaseEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1243238331128591062L;

	/** The version. */
	private Long version = 1l;

	/**
	 * The represents the userLoginId which created this entity.
	 */
	private String createdBy;

	/**
	 * This represents the timestamp when this entity was created.
	 */
	private Date createdTxTimestamp;

	/**
	 * This represents the userLoginId which updated this entity.
	 */
	private String updatedBy;

	/**
	 * This represents the timestamp when this entity was updated.
	 */
	private Date updatedTxTimestamp;
	
	/**
	 * The represents the userLoginId which created this entity.
	 */
	private Person deactivatedBy;

	protected Serializable id;
	
	private Boolean active = Boolean.TRUE;
	
	private String deactivationReason;

	@Column(name="DEACTIVATION_REASON")
	public String getDeactivationReason() {
	return deactivationReason;
	}

	public void setDeactivationReason(String deactivationReason) {
	this.deactivationReason = deactivationReason;
	}

	@Column(name="IS_ACTIVE")
	public Boolean isActive() {
	return active;
	}

	public void setActive(Boolean active) {
	if(active == null)
		active = Boolean.TRUE;
	this.active = active;
	}
	
	public void activate(){
	setActive(true);
	}
	
	public void deActivate(String reason,Person p){
	setActive(false);
	setDeactivationReason(reason);
	setDeactivatedBy(p);
	}
	

	/**
	 * Gets the created by.
	 * 
	 * @return the created by
	 */
	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
	return createdBy;
	}

	/**
	 * Gets the created tx timestamp.
	 * 
	 * @return the created tx timestamp
	 */
	@Column(name = "CREATE_TX_TIMESTAMP")
	@Temporal(value = TemporalType.TIMESTAMP)
	public Date getCreatedTxTimestamp() {
	return createdTxTimestamp;
	}

	/**
	 * Gets the updated by.
	 * 
	 * @return the updated by
	 */
	@Column(name = "UPDATE_BY")
	public String getUpdatedBy() {
	return updatedBy;
	}

	/**
	 * Gets the updated tx timestamp.
	 * 
	 * @return the updated tx timestamp
	 */
	@Column(name = "UPDATED_TX_TIMESTAMP")
	@Temporal(value = TemporalType.TIMESTAMP)
	public Date getUpdatedTxTimestamp() {
	return updatedTxTimestamp;
	}

	/**
	 * Gets the version.
	 * 
	 * @return the version
	 */
//	@Version
	@Column(name = "VERSION")
	public Long getVersion() {
	return version;
	}

	/**
	 * Sets the created by.
	 * 
	 * @param createdBy
	 *            the new created by
	 */
	public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
	}

	/**
	 * Sets the created tx timestamp.
	 * 
	 * @param createdTxTimestamp
	 *            the new created tx timestamp
	 */
	public void setCreatedTxTimestamp(Date createdTxTimestamp) {
	this.createdTxTimestamp = createdTxTimestamp;
	}

	/**
	 * Sets the updated by.
	 * 
	 * @param updatedBy
	 *            the new updated by
	 */
	public void setUpdatedBy(String updatedBy) {
	this.updatedBy = updatedBy;
	}

	/**
	 * Sets the updated tx timestamp.
	 * 
	 * @param updatedTxTimestamp
	 *            the new updated tx timestamp
	 */
	public void setUpdatedTxTimestamp(Date updatedTxTimestamp) {
	this.updatedTxTimestamp = updatedTxTimestamp;
	}

	/**
	 * Sets the version.
	 * 
	 * @param version
	 *            the new version
	 */
	public void setVersion(Long version) {
	this.version = version;
	}

	@Transient
	public Serializable getId() {
	return id;
	}

	public void setId(Serializable id) {
	this.id = id;
	}

	@OneToOne(fetch=FetchType.LAZY)
	public Person getDeactivatedBy() {
	return deactivatedBy;
	}

	public void setDeactivatedBy(Person deactivatedBy) {
	this.deactivatedBy = deactivatedBy;
	}
	
//	public void decreaseVersion(){
//	if(version != null)
//		--version;
//	}
}