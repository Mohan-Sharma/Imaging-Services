/*
 * header file
 */
package com.nzion.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

// TODO: Auto-generated Javadoc
/**
 * The Class HistoricalModel.
 */
@Embeddable
public class HistoricalModel implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    private Date fromDate;

    /** The thru date. */
    private Date thruDate;

    /** The expiry reason. */
    private String expiryReason;

    /**
     * Gets the expriry reason.
     * 
     * @return the expriry reason
     */
    public String getExpiryReason() {
	return expiryReason;
    }

    /**
     * Gets the from date.
     * 
     * @return the from date
     */
    @Column(name = "FROM_DATETIME")
     @Temporal(value = TemporalType.TIMESTAMP)
    public Date getFromDate() {
	return fromDate;
    }

    /**
     * Gets the thru date.
     * 
     * @return the thru date
     */
    @Column(name = "THRU_DATETIME")
    @Temporal(value = TemporalType.TIMESTAMP)
    public Date getThruDate() {
	return thruDate;
    }

    /**
     * Sets the expriry reason.
     * 
     * @param expiryReason
     *            the new expriry reason
     */
    public void setExpiryReason(String expiryReason) {
	this.expiryReason = expiryReason;
    }

    /**
     * Sets the from date.
     * 
     * @param fromDate
     *            the new from date
     */
    public void setFromDate(Date fromDate) {
	this.fromDate = fromDate;
    }

    /**
     * Sets the thru date.
     * 
     * @param thruDate
     *            the new thru date
     */
    public void setThruDate(Date thruDate) {
	this.thruDate = thruDate;
    }
    
    @Transient
    public boolean isExpired(Date date) {
    return thruDate!=null ? thruDate.before(date) : false;
    }
}
