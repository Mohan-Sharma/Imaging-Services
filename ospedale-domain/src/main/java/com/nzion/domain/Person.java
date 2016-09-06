/*
 * header file
 */
package com.nzion.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.Formula;

import com.nzion.domain.annot.AccountNumberField;
import com.nzion.domain.annot.SystemAuditable;
import com.nzion.util.UtilDateTime;

@Entity
@Table(name = "PERSON",uniqueConstraints= {@UniqueConstraint(columnNames= {"PARTY_TYPE", "ACCOUNT_NUMBER"})})
@Inheritance(strategy = InheritanceType.JOINED)
@Filters( { @Filter(name = "EnabledFilter",condition="(IS_ACTIVE=1 OR IS_ACTIVE IS NULL)") })
@AccountNumberField
@SystemAuditable
@Cache(usage=CacheConcurrencyStrategy.TRANSACTIONAL,region="com.nzion.domain")
public class Person extends Party implements Salutable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The first name. */
	private String firstName;

	/** The middle name. */
	private String middleName;

	/** The last name. */
	private String lastName;

    /** The endMostName name. */
    private String endMostName;

	/** The personal title. */
	private String personalTitle;

	/** The prefix. */
	private String prefix;

	/** The suffix. */
	private String suffix;

	/** The gender. */
	private Enumeration gender;

	/** The language. */
	private Enumeration language;

	/** The marital status. */
	private Enumeration maritalStatus;

	/** The ssn number. */
	private String ssnNumber;

	/** The alias name. */
	private String aliasName;

	private Date dateOfBirth;

	private String salutation;

	private List<UserLogin> userLogins;

	@Transient
	private UserLogin userLogin;

	private Date dateOfDeath;
	
	private DataResource profilePicture;

	private Set<Location> locations=new HashSet<Location>();
	
	private ContactFields contacts;
	
	private Boolean schedulable;
	
	private Integer currentAge;
	
	@Transient
	private String age;
	
	public Person(Long id,String firstName,String lastName,ContactFields cf){
	super();
	setFirstName(firstName);
	setLastName(lastName);
	setId(id);
	setContacts(contacts);
	}
	
	public Boolean getSchedulable() {
	if(schedulable == null)
		return Boolean.FALSE;
	return schedulable;
	}

	public void setSchedulable(boolean schedulable) {
	this.schedulable = schedulable;
	}

	@Embedded
	public ContactFields getContacts() {
	if(contacts == null)
		contacts = new ContactFields();
	return contacts;
	}

	public void setContacts(ContactFields contacts) {
	this.contacts = contacts;
	}

	@OneToOne(fetch = FetchType.LAZY, targetEntity = DataResource.class)
	@Cascade(value = { CascadeType.ALL })
	@JoinColumn(name = "PROFILE_PICTURE")
	public DataResource getProfilePicture() {
	return profilePicture;
	}

	public void setProfilePicture(DataResource profilePicture) {
	this.profilePicture = profilePicture;
	}

	@Column(name = "DATE_OF_DEATH")
	public Date getDateOfDeath() {
	return this.dateOfDeath;
	}

	public void setDateOfDeath(Date dateOfDeath) {
	this.dateOfDeath = dateOfDeath;
	}

	public Person() {
	super();
	}

	public Person(PartyType personType) {
	super(personType);
	}

	@Column(name = "ALIAS_NAME")
	public String getAliasName() {
	return aliasName;
	}

	@Column(name = "DATE_OF_BIRTH")
	@Temporal(TemporalType.DATE)
	public Date getDateOfBirth() {
	return dateOfBirth;
	}

	@Column(name = "FIRST_NAME")
	public String getFirstName() {
	return firstName;
	}

	@OneToOne(targetEntity = Enumeration.class)
	@JoinColumn(name = "GENDER_CODE")
	public Enumeration getGender() {
	return gender;
	}

	@OneToOne(targetEntity = Enumeration.class)
	@JoinColumn(name = "PARTY_LANGUAGE")
	public Enumeration getLanguage() {
	return language;
	}

	@Column(name = "LAST_NAME")
	public String getLastName() {
	return lastName;
	}

	@OneToOne(targetEntity = Enumeration.class)
	@JoinColumn(name = "MARITAL_CODE")
	public Enumeration getMaritalStatus() {
	return maritalStatus;
	}

	@Column(name = "MIDDLE_NAME")
	public String getMiddleName() {
	return middleName;
	}

    @Column(name = "END_MOST_NAME")
    public String getEndMostName() {
        return endMostName;
    }

    public void setEndMostName(String endMostName) {
        this.endMostName = endMostName;
    }

    @Column(name = "PERSONAL_TITLE")
	public String getPersonalTitle() {
	return personalTitle;
	}

	@Column(name = "PREFIX")
	public String getPrefix() {
	return prefix;
	}

	public String getSalutation() {
	return salutation;
	}

	@Column(name = "SSN_NUMBER")
	public String getSsnNumber() {
	return ssnNumber;
	}

	@Column(name = "SUFFIX")
	public String getSuffix() {
	return suffix;
	}

	public void setAliasName(String aliasName) {
	this.aliasName = aliasName;
	}

	public void setDateOfBirth(Date dateOfBirth) {
	this.dateOfBirth = dateOfBirth;
	}

	public void setFirstName(String firstName) {
	this.firstName = firstName;
	}

	public void setGender(Enumeration gender) {
	this.gender = gender;
	}

	public void setLanguage(Enumeration language) {
	this.language = language;
	}

	public void setLastName(String lastName) {
	this.lastName = lastName;
	}

	public void setMaritalStatus(Enumeration maritalStatus) {
	this.maritalStatus = maritalStatus;
	}

	public void setMiddleName(String middleName) {
	this.middleName = middleName;
	}

	public void setPersonalTitle(String personalTitle) {
	this.personalTitle = personalTitle;
	}

	public void setPrefix(String prefix) {
	this.prefix = prefix;
	}

	public void setSalutation(String salutation) {
	this.salutation = salutation;
	}

	public void setSsnNumber(String ssnNumber) {
	this.ssnNumber = ssnNumber;
	}

	/**
	 * Sets the suffix.
	 *
	 * @param suffix
	 *            the new suffix
	 */
	public void setSuffix(String suffix) {
	this.suffix = suffix;
	}

	@OneToMany(mappedBy = "person")
	@Cascade(value = {CascadeType.ALL})
	public List<UserLogin> getUserLogins() {
		return userLogins;
	}

	public void setUserLogins(List<UserLogin> userLogins) {
		this.userLogins = userLogins;
	}

	@Transient
	public String getAge() {
	if (dateOfBirth != null) 
		return UtilDateTime.calculateAge(dateOfBirth, dateOfDeath);
	return null;
	}
	
	@Transient
	public void setAge(String age){
		this.age = age;
	}
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "PERSON_LOCATIONS")
	@Fetch(FetchMode.SELECT)
	public Set<Location> getLocations() {
	if(locations == null)
		locations = new HashSet<Location>();
	return locations;
	}

	public void setLocations(Set<Location> locations) {
	this.locations = locations;
	}

	public void addLocation(Location location) {
	if (getLocations() == null) setLocations(new HashSet<Location>());
	locations.add(location);
	}
	
	public boolean belongsTo(Location location){
	return locations != null && locations.contains(location);
	}

	@Column(name="TIME_ELAPSED_AFTER_BIRTH")
	@Formula(value="DATE_FORMAT(NOW(), '%Y') - DATE_FORMAT(DATE_OF_BIRTH, '%Y') - (DATE_FORMAT(NOW(), '00-%m-%d') < DATE_FORMAT(DATE_OF_BIRTH, '00-%m-%d'))")
	protected Integer getCurrentAge() {
	return currentAge;
	}

	protected void setCurrentAge(Integer timeElapsedAfterBirth) {
	this.currentAge = timeElapsedAfterBirth;
	}

	@Transient
	public UserLogin getUserLogin() {
		if ((getUserLogins() != null) && (getUserLogins().size() > 0)){
			return getUserLogins().get(0);
		} else {
			return null;
		}
	}

	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}

}
