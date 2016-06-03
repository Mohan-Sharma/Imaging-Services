package com.nzion.domain;

import javax.persistence.AssociationOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;

import com.nzion.domain.base.IdGeneratingBaseEntity;

@Entity
@Table(name = "PASSWORD_POLICY")
@Filters( {@Filter(name = "EnabledFilter", condition = "(IS_ACTIVE=1 OR IS_ACTIVE IS NULL)") })
@Cache(usage=CacheConcurrencyStrategy.TRANSACTIONAL,region="com.nzion.domain")
public class PasswordPolicy extends IdGeneratingBaseEntity {

	private static final long serialVersionUID = 1L;

	// Warn users when a password expires,
	private Boolean pwdExpireWarning = false;

	// Allow a configurable number of grace logins after the user's password has expired. A password policy aware
	// application will be notified of the number of remaining grace logins. If no grace logins are allowed, a user
	// cannot authenticate or change their own password once it has expired.
	private Integer pwdGraceLoginLimit = 0;

	private Integer passwordMaxRepeatedChars = 0;

	// A configurable password history size, which tells the server to keep a history of the last N passwords
	// and reject passwords that have been previously used.
	private Integer pwdinhistory = null;
	private Boolean pwdchecksyntax = false;
	private Integer pwdminlength;

	// passwords expire after a configurable number of days after they were last changed
	private Integer pwdmaxage = null;
	// A maximum number of failed login attempts before the account is locked.
	private Integer pwdmaxfailure = 0;

	// A minimum time allowed between password changes
	private Long pwdfailurecountinterval = 0L;

	private Boolean pwdmustchange = false;
	
	private String selectedPattern;

	private Integer sessionTimeOut;
	
	public PasswordPolicy(){}
	
	public PasswordPolicy(PasswordPolicy that){
	this.passwordMaxRepeatedChars = that.passwordMaxRepeatedChars;
	this.pwdchecksyntax = that.pwdchecksyntax;
	this.pwdExpireWarning = that.pwdExpireWarning;
	this.pwdfailurecountinterval = that.pwdfailurecountinterval;
	this.pwdGraceLoginLimit = that.pwdGraceLoginLimit;
	this.pwdinhistory = that.pwdinhistory;
	this.pwdmaxage = that.pwdmaxage;
	this.pwdmaxfailure = that.pwdmaxfailure;
	this.pwdminlength = that.pwdminlength;
	this.pwdmustchange = that.pwdmustchange;
	this.selectedPattern = that.selectedPattern;
	this.sessionTimeOut = that.sessionTimeOut;
	}

	@Column(name = "SELECTED_PATTERN")
	@Lob
	public String getSelectedPattern() {
	return selectedPattern;
	}

	public void setSelectedPattern(String selectedPattern) {
	this.selectedPattern = selectedPattern;
	}

	@Column(name = "PWD_EXPIRE_WARNING")
	public Boolean getPwdExpireWarning() {
	return pwdExpireWarning;
	}

	@Column(name = "PWD_GRACE_LOGIN_LIMIT")
	public Integer getPwdGraceLoginLimit() {
	return pwdGraceLoginLimit;
	}

	@Column(name = "PWD_MAX_REPEATED_CHARS")
	public Integer getPasswordMaxRepeatedChars() {
	return passwordMaxRepeatedChars;
	}

	@Column(name = "PWD_IN_HISTORY")
	public Integer getPwdinhistory() {
	return pwdinhistory;
	}

	@Column(name = "PWD_CHECK_SYNTAX")
	public Boolean getPwdchecksyntax() {
	return pwdchecksyntax;
	}

	@Column(name = "PWD_MIN_LENGTH")
	public Integer getPwdminlength() {
	return pwdminlength;
	}

	@Column(name = "PWD_MAX_AGE")
	public Integer getPwdmaxage() {
	return pwdmaxage;
	}

	@Column(name = "PWD_MAX_FAILURE")
	public Integer getPwdmaxfailure() {
	return pwdmaxfailure;
	}

	@Column(name = "PWD_FAILURE_COUNT_INTERVAL")
	public Long getPwdfailurecountinterval() {
	return pwdfailurecountinterval;
	}

	@Column(name = "PWD_MUST_CHANGE")
	public Boolean getPwdmustchange() {
	return pwdmustchange;
	}

	public void setPwdExpireWarning(Boolean pwdExpireWarning) {
	this.pwdExpireWarning = pwdExpireWarning;
	}

	public void setPwdGraceLoginLimit(Integer pwdGraceLoginLimit) {
	this.pwdGraceLoginLimit = pwdGraceLoginLimit;
	}

	public void setPasswordMaxRepeatedChars(Integer passwordMaxRepeatedChars) {
	this.passwordMaxRepeatedChars = passwordMaxRepeatedChars;
	}

	public void setPwdinhistory(Integer pwdinhistory) {
	this.pwdinhistory = pwdinhistory;
	}

	public void setPwdchecksyntax(Boolean pwdchecksyntax) {
	this.pwdchecksyntax = pwdchecksyntax;
	}

	public void setPwdminlength(Integer pwdminlength) {
	this.pwdminlength = pwdminlength;
	}

	public void setPwdmaxage(Integer pwdmaxage) {
	this.pwdmaxage = pwdmaxage;
	}

	public void setPwdmaxfailure(Integer pwdmaxfailure) {
	this.pwdmaxfailure = pwdmaxfailure;
	}

	public void setPwdfailurecountinterval(Long pwdfailurecountinterval) {
	this.pwdfailurecountinterval = pwdfailurecountinterval;
	}

	public void setPwdmustchange(Boolean pwdmustchange) {
	this.pwdmustchange = pwdmustchange;
	}

	public Integer getSessionTimeOut() {
	return this.sessionTimeOut;
	}

	public void setSessionTimeOut(Integer sessionTimeOut) {
	this.sessionTimeOut = sessionTimeOut;
	}
	
	@Transient
	public String getSessionTimeOutConverted(){
	if(sessionTimeOut == -1)
		return "Never";
	else
		sessionTimeOut = sessionTimeOut/60;
	return sessionTimeOut.toString();
	}
	
	public void setSessionTimeOutConverted(String sessionTimeOut){
	if("Never".equalsIgnoreCase(sessionTimeOut))
		this.sessionTimeOut = -1;
	else
		this.sessionTimeOut = Integer.parseInt(sessionTimeOut) * 60;
	}
}