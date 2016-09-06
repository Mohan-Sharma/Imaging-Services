/*
 * header file
 */
package com.nzion.domain;

import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.domain.emr.lab.LabDepartment;
import com.nzion.domain.emr.lab.Laboratories;
import com.nzion.security.SecurityGroup;
import com.nzion.security.SecurityPermission;
import com.nzion.util.UtilValidator;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.ForeignKey;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * The Class UserLogin.
 */
@Entity
@Table(name = "USER_LOGIN", uniqueConstraints = { @UniqueConstraint(columnNames = { "PERSON_ID" }) })
@Filter(name = "EnabledFilter", condition = "(IS_ACTIVE=1 OR IS_ACTIVE IS NULL)")
public class UserLogin 	extends IdGeneratingBaseEntity implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The login id. */
	private String username;

	/** The password. */
	private String password;

	/** The account non locked. */
	private boolean accountNonLocked = true;

	/** The account non expired. */
	private boolean accountNonExpired = true;

	/** The credentials non expired. */
	private boolean credentialsNonExpired = true;
	/** The successive failed logins. */
	private int successiveFailedLogins = 0;

	/** The require password change. */
	private Boolean requirePasswordChange = Boolean.FALSE;

	private Date pwdChangedTime;

	/** The party. */
	private Person person;

	private Date pwdFailureTime;

	private String secretQuestion;

	private String secretQuestionAnswer;

	private Authorization authorization;

	private Boolean locked;

	private boolean acceptedTermsAndConditions = false;

	private Person originalPerson;

	private boolean impersonated = false;

	private Patient patient;

	private Date passwordValidThruDate;
	
	private String emergencyAccessReason;
    
    private Map<String, Boolean> menuIdToVisibility;

    private Set<SecurityGroup> grantedSecurityPermissionGroups;
    
    private Set<LabDepartment> labDepartments = new HashSet<LabDepartment>();
    
    private Set<Laboratories> laboratories = new HashSet<Laboratories>();

	private Long selectedDefaultRole;
    

    @ManyToMany
	@Cascade(CascadeType.ALL)
    public Set<LabDepartment> getLabDepartments() {
		return labDepartments;
	}

	public void setLabDepartments(Set<LabDepartment> labDepartments) {
		this.labDepartments = labDepartments;
	}
	
	@ManyToMany
	@Cascade(CascadeType.ALL)
	public Set<Laboratories> getLaboratories() {
		return laboratories;
	}

	public void setLaboratories(Set<Laboratories> laboratories) {
		this.laboratories = laboratories;
	}

	private final Set<SecurityPermission> grantedSecurityPermissions = new HashSet<SecurityPermission>();

	public UserLogin() {
	}
	
	public boolean isAcceptedTermsAndConditions() {
	return acceptedTermsAndConditions;
	}

	public void setAcceptedTermsAndConditions(boolean acceptedTermsAndConditions) {
	this.acceptedTermsAndConditions = acceptedTermsAndConditions;
	}

	public Boolean isLocked() {
	return Boolean.TRUE.equals(locked);
	}

	public void setLocked(Boolean locked) {
	this.locked = locked;
	}

	@Embedded
	public Authorization getAuthorization() {
	if (authorization == null) authorization = new Authorization();
	return authorization;
	}

	public void setAuthorization(Authorization authorization) {
	this.authorization = authorization;
	}

	@Column(name = "SECRET_QUESTION")
	public String getSecretQuestion() {
	return secretQuestion;
	}

	public void setSecretQuestion(String secretQuestion) {
	this.secretQuestion = secretQuestion;
	}

	@Column(name = "SECRET_QUESTION_ANSWER")
	public String getSecretQuestionAnswer() {
	return secretQuestionAnswer;
	}

	public void setSecretQuestionAnswer(String secretQuestionAnswer) {
	this.secretQuestionAnswer = secretQuestionAnswer;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	@Column(name = "PASSWORD")
	public String getPassword() {
	return password;
	}

	@Column(name = "PWD_FAILURE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getPwdFailureTime() {
	return pwdFailureTime;
	}

	/**
	 * Gets the successive failed logins.
	 *
	 * @return the successive failed logins
	 */
	@Column(name = "SUCCESSIVE_FAILED_LOGINS")
	public int getSuccessiveFailedLogins() {
	return successiveFailedLogins;
	}

	/**
	 * Gets the login id.
	 *
	 * @return the login id
	 */
	@Column(unique = true, name = "USERNAME", nullable = false)
	public String getUsername() {
	return username;
	}

	/**
	 * Checks if is account non expired.
	 *
	 * @return true, if is account non expired
	 */
	@Column(name = "ACCOUNT_NON_EXPIRED")
	public boolean isAccountNonExpired() {
	return accountNonExpired;
	}

	/**
	 * Checks if is account non locked.
	 *
	 * @return true, if is account non locked
	 */
	@Column(name = "ACCOUNT_NON_LOCKED")
	public boolean isAccountNonLocked() {
	return accountNonLocked;
	}

	/**
	 * Checks if is credentials non expired.
	 *
	 * @return true, if is credentials non expired
	 */
	@Column(name = "CREDENTIALS_NON_EXPIRED")
	public boolean isCredentialsNonExpired() {
	return credentialsNonExpired;
	}

	/**
	 * When a admin user is saved while creating the practice, the person is saved along with the UserLogin.
	 * Gets the party.
	 *
	 * @return the party
	 */
	@ManyToOne(targetEntity = Person.class)
	@JoinColumn(name = "PERSON_ID")
	@Cascade(value = {CascadeType.SAVE_UPDATE,CascadeType.MERGE})
	public Person getPerson() {
	return person;
	}

	
	/**
	 * Sets the party.
	 *
	 * @param party
	 *            the new party
	 */
	public void setPerson(Person party) {
	this.person = party;
	}

	/**
	 * Gets the require password change.
	 *
	 * @return the require password change
	 */
	@Column(name = "IS_REQUIRE_PASSWORD_CHANGE")
	public Boolean isRequirePasswordChange() {
	return requirePasswordChange;
	}

	/**
	 * Sets the account non expired.
	 *
	 * @param accountNonExpired
	 *            the new account non expired
	 */
	public void setAccountNonExpired(boolean accountNonExpired) {
	this.accountNonExpired = accountNonExpired;
	}

	/**
	 * Sets the account non locked.
	 *
	 * @param accountNonLocked
	 *            the new account non locked
	 */
	public void setAccountNonLocked(boolean accountNonLocked) {
	this.accountNonLocked = accountNonLocked;
	}

	/**
	 * Sets the credentials non expired.
	 *
	 * @param credentialsNonExpired
	 *            the new credentials non expired
	 */
	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
	this.credentialsNonExpired = credentialsNonExpired;
	}

	/**
	 * Sets the password.
	 *
	 * @param password
	 *            the new password
	 */
	public void setPassword(String password) {
	this.password = password;
	}

	/**
	 * Sets the require password change.
	 *
	 * @param requirePasswordChange
	 *            the new require password change
	 */
	public void setRequirePasswordChange(Boolean requirePasswordChange) {
	this.requirePasswordChange = requirePasswordChange;
	}

	/**
	 * Sets the successive failed logins.
	 *
	 * @param successiveFailedLogins
	 *            the new successive failed logins
	 */
	public void setSuccessiveFailedLogins(int successiveFailedLogins) {
	this.successiveFailedLogins = successiveFailedLogins;
	}

	public void setUsername(String username) {
	this.username = username;
	}


	@Column(name = "PWD_CHANGED_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getPwdChangedTime() {
	return pwdChangedTime;
	}

	public void setPwdChangedTime(Date pwdChangedTime) {
	this.pwdChangedTime = pwdChangedTime;
	}

	public void setPwdFailureTime(Date pwdFailureTime) {
	this.pwdFailureTime = pwdFailureTime;
	}

	public void addRole(long role) {
	getAuthorization().addRole(role);
	}

	public void removeRole(long role) {
	getAuthorization().removeRole(role);
	}

	public boolean hasAllRoles(long... roles) {
	return getAuthorization().hasAllRoles(roles);
	}

	public boolean hasRole(long role) {
	return getAuthorization().hasRole(role);
	}

	public boolean hasRoleOrMore(long role) {
	return getAuthorization().hasRoleOrMore(role);
	}

	public boolean hasAnyRole(long... roles) {
	return getAuthorization().hasAnyRole(roles);
	}

	@Transient
	public Set<Location> getLocations() {
	return getPerson().getLocations();
	}

	public void changePassword(String newPassword) {
	this.password = newPassword;
	setPassword(newPassword);
	setPwdChangedTime(new Timestamp(new Date().getTime()));
	setRequirePasswordChange(false);
	setAccountNonExpired(true);
	setAccountNonLocked(true);
	setActive(true);
	setCredentialsNonExpired(true);
	setLocked(false);
	setSuccessiveFailedLogins(0);
	setAcceptedTermsAndConditions(true);
	}

	@Transient
	public boolean isAccountExpired() {
	return !isAccountNonExpired();
	}

	@Transient
	public boolean isAccountLocked() {
	return !isAccountNonLocked();
	}

	@Transient
	public boolean isCredentialsExpired() {
	return !isCredentialsNonExpired();
	}

	public void setAccountExpired(boolean accountExpired) {
	setAccountNonExpired(!accountExpired);
	}

	public void setAccountLocked(boolean accountLocked) {
	setAccountNonLocked(!accountLocked);
	}

	public void setCredentialsExpired(boolean credentialsExpired) {
	setCredentialsNonExpired(!credentialsExpired);
	}

	@OneToOne
	@JoinColumn(name = "ORIGINAL_PERSON_ID")
	public Person getOriginalPerson() {
	return originalPerson;
	}

	public void setOriginalPerson(Person originalPerson) {
	this.originalPerson = originalPerson;
	}

	public boolean isImpersonated() {
	return impersonated;
	}

	public void setImpersonated(boolean impersonated) {
	this.impersonated = impersonated;
	}

	@OneToOne
	@JoinColumn(nullable = true)
	public Patient getPatient() {
	return patient;
	}

	public void setPatient(Patient patient) {
	this.patient = patient;
	}

	@Temporal(TemporalType.DATE)
	public Date getPasswordValidThruDate() {
	return passwordValidThruDate;
	}

	public void setPasswordValidThruDate(Date passwordValidThruDate) {
	this.passwordValidThruDate = passwordValidThruDate;
	}
	
	public String getEmergencyAccessReason() {
	return emergencyAccessReason;
	}

	public void setEmergencyAccessReason(String emergencyAccessReason) {
	this.emergencyAccessReason = emergencyAccessReason;
	}
	
	 @ElementCollection
     @JoinTable(name = "USERLOGIN_MENUVISIBIITY", joinColumns = @JoinColumn(name = "USERLOGIN_ID"))
     @org.hibernate.annotations.MapKey(columns = @Column(name = "MENUID"))
     @Column(name = "VISIBILITY")
     @Cascade(CascadeType.ALL)
     public Map<String, Boolean> getMenuIdToVisibility() {
        return menuIdToVisibility;
     }
	 
	 public void setMenuIdToVisibility(Map<String, Boolean> menuIdToVisibility) {
     this.menuIdToVisibility = menuIdToVisibility;
	 }
	 
	 @Transient
     public Collection<? extends GrantedAuthority> getAllGrantedSecurityPermissions() {
        if (UtilValidator.isEmpty(grantedSecurityPermissions) && grantedSecurityPermissionGroups != null) {
            for (SecurityGroup grantedSecurityPermissionGroup : grantedSecurityPermissionGroups) {
                grantedSecurityPermissions.addAll(grantedSecurityPermissionGroup.getAllPermissions());
            }
        }
        return grantedSecurityPermissions;
     }

	@ManyToMany
	@Cascade(CascadeType.ALL)
	public Set<SecurityGroup> getGrantedSecurityPermissionGroups() {
	return grantedSecurityPermissionGroups;
	}

	public void setGrantedSecurityPermissionGroups(Set<SecurityGroup> grantedSecurityPermissionGroups) {
	this.grantedSecurityPermissionGroups = grantedSecurityPermissionGroups;
	}

	public void addGrantedSecurityPermissionGroup(SecurityGroup grantedSecurityPermissionGroup) {
	if (grantedSecurityPermissionGroup != null) grantedSecurityPermissionGroups.add(grantedSecurityPermissionGroup);
	}

	public void addGrantedSecurityPermissionGroups(Set<SecurityGroup> grantedSecurityPermissionGroups) {
	if (UtilValidator.isNotEmpty(grantedSecurityPermissionGroups)) {
		for (SecurityGroup grantedSecurityPermissionGroup : grantedSecurityPermissionGroups) {
			addGrantedSecurityPermissionGroup(grantedSecurityPermissionGroup);
		}
	}
	}

	public void removeGrantedSecurityPermissionGroup(SecurityGroup grantedSecurityPermissionGroup) {
	if (grantedSecurityPermissionGroup != null) grantedSecurityPermissionGroups.remove(grantedSecurityPermissionGroup);
	}

	public void removeGrantedSecurityPermissionGroups(Set<SecurityGroup> grantedSecurityPermissionGroups) {
	if (UtilValidator.isNotEmpty(grantedSecurityPermissionGroups)) {
		for (SecurityGroup grantedSecurityPermissionGroup : grantedSecurityPermissionGroups) {
			removeGrantedSecurityPermissionGroup(grantedSecurityPermissionGroup);
		}
	}
	}

	public boolean hasAllSecurityPermissionGroups(SecurityGroup... securityPermissionGroups) {
	return grantedSecurityPermissionGroups.containsAll(Arrays.asList(securityPermissionGroups));
	}

	public boolean hasAllSecurityPermissions(SecurityPermission... securityPermissions) {
	Collection<? extends GrantedAuthority> grantedSecurityPermissions = getAllGrantedSecurityPermissions();
	return grantedSecurityPermissions.containsAll(Arrays.asList(securityPermissions));
	}

	public Long getSelectedDefaultRole() {
		return selectedDefaultRole;
	}

	public void setSelectedDefaultRole(Long selectedDefaultRole) {
		this.selectedDefaultRole = selectedDefaultRole;
	}

}