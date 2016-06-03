/*
 * header file
 */
package com.nzion.domain.base;

import java.util.List;

import javax.persistence.AssociationOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;

// TODO: Auto-generated Javadoc
/**
 *  
 * The sequencing of id would be independent to the tenant. Each tenant can
 * configure the sequence generation.
 * 
 * @author Sandeep Prusty Apr 1, 2010
 * @version 1.0
 * @since 1.0
 * 
 */

@Entity
@Table(name="ENTITY_SEQUENCES")
public class IdSequence extends IdGeneratingBaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The entity name. */
	private String entityName;

	/** The start index. */
	private long startIndex;

	/** The increment. */
	private long increment = 1;

	/** The current index. */
	private Long currentIndex;

	/** The prefix. */
	private String prefix = "";

	/** The suffix. */
	private String suffix = "";
	
	private String description;

	/**
	 * Instantiates a new id sequence.
	 */
	public IdSequence() {
	}

	/**
	 * Builds the id.
	 * 
	 * @return the string
	 */
	public String buildId() {
	StringBuilder id = new StringBuilder();
	if (prefix != null) id.append(prefix);
	currentIndex = (currentIndex == null) ? startIndex : currentIndex + increment;
	id.append(currentIndex);
	if (suffix != null) id.append(suffix);
	return id.toString();
	}
	
	/**
	 * Builds the id.
	 * 
	 * @return the string
	 */
	public String buildId(List<String> usedAccountNumbers) {
	if (currentIndex == null) {
		currentIndex = startIndex;
	}
	String tempAcNumber = null;
	if (usedAccountNumbers.size() == currentIndex) {
		tempAcNumber = generateAcNumber(currentIndex);
	} else {
		for (long i = startIndex; i <= currentIndex; i += increment) {
			tempAcNumber = generateAcNumber(i);
			if (!usedAccountNumbers.contains(tempAcNumber)){
				currentIndex = i;
				break;
			}
		}
	}
	setCurrentIndex(currentIndex += increment);
	return tempAcNumber;
	}
	
	private String generateAcNumber(Long currentIndex) {
	StringBuilder id = new StringBuilder();
	if (prefix != null) {
		id.append(prefix);
	}
	id.append(currentIndex);
	if (suffix != null) {
		id.append(suffix);
	}
	return id.toString();
	}
	
	public String getDescription() {
	return description;
	}

	public void setDescription(String description) {
	this.description = description;
	}

	/**
	 * Gets the current index.
	 * 
	 * @return the current index
	 */
	@Column(name = "CURRENT_INDEX")
	public Long getCurrentIndex() {
	return currentIndex;
	}

	/**
	 * Gets the entity name.
	 * 
	 * @return the entity name
	 */
	@Column(name = "ENTITY_NAME")
	public String getEntityName() {
	return entityName;
	}

	/**
	 * Gets the increment.
	 * 
	 * @return the increment
	 */
	@Column(name = "INCREMENT_BY")
	public Long getIncrement() {
	return increment;
	}

	/**
	 * Gets the prefix.
	 * 
	 * @return the prefix
	 */
	@Column(name = "PREFIX")
	public String getPrefix() {
	return prefix;
	}

	/**
	 * Gets the start index.
	 * 
	 * @return the start index
	 */
	@Column(name = "START_INDEX")
	public Long getStartIndex() {
	return startIndex;
	}

	/**
	 * Gets the suffix.
	 * 
	 * @return the suffix
	 */
	@Column(name = "SUFFIX")
	public String getSuffix() {
	return suffix;
	}

	/**
	 * Sets the current index.
	 * 
	 * @param currentIndex
	 *            the new current index
	 */
	public void setCurrentIndex(Long currentIndex) {
	this.currentIndex = currentIndex;
	}

	/**
	 * Sets the entity name.
	 * 
	 * @param entityName
	 *            the new entity name
	 */
	public void setEntityName(String entityName) {
	this.entityName = entityName;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(Long id) {
	this.id = id;
	}

	/**
	 * Sets the increment.
	 * 
	 * @param increment
	 *            the new increment
	 */
	public void setIncrement(Long increment) {
	this.increment = increment;
	}

	/**
	 * Sets the prefix.
	 * 
	 * @param prefix
	 *            the new prefix
	 */
	public void setPrefix(String prefix) {
	this.prefix = prefix;
	}

	/**
	 * Sets the start index.
	 * 
	 * @param startIndex
	 *            the new start index
	 */
	public void setStartIndex(Long startIndex) {
	this.startIndex = startIndex;
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

}