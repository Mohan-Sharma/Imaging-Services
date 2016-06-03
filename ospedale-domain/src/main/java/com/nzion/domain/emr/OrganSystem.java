package com.nzion.domain.emr;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.Type;

import com.nzion.domain.Enumeration;
import com.nzion.domain.base.IdGeneratingBaseEntity;

@Entity
@Table(name = "ORGAN_SYSTEMS", uniqueConstraints = { @UniqueConstraint(columnNames = { "ORGAN_SYSTEM_DESC"}) })
@Filters( {
@Filter(name = "EnabledFilter", condition = "(IS_ACTIVE=1 OR IS_ACTIVE IS NULL)") })
@Cache(usage=CacheConcurrencyStrategy.TRANSACTIONAL,region="com.nzion.domain")
public class OrganSystem extends IdGeneratingBaseEntity {

	private static final long serialVersionUID = 1L;
	private Integer sortOrder;
	private String desc;
//	private Set<Question> questions;
	private Enumeration gender;
	private Boolean isMultiple;
	private Boolean isYesNo;
	private String normalSentence;

	public OrganSystem() {
	};

	public OrganSystem(String organSystemId) {
	this.id = organSystemId;
	}

	@Column(name = "SORT_ORDER")
	public Integer getSortOrder() {
	return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
	this.sortOrder = sortOrder;
	}

	/*@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "organSystem",fetch=FetchType.EAGER)
	@OrderBy("sortOrder")
	@Fetch(FetchMode.SELECT)
	public Set<Question> getQuestions() {
	return questions;
	}

	public void setQuestions(Set<Question> questions) {
	this.questions = questions;
	}*/

	@Column(name = "IS_MULTIPLE")
	@Type(type = "yes_no")
	public Boolean getIsMultiple() {
	return isMultiple;
	}

	public void setIsMultiple(Boolean isMultiple) {
	this.isMultiple = isMultiple;
	}

	@Column(name = "IS_YESNO")
	@Type(type = "yes_no")
	public Boolean getIsYesNo() {
	return isYesNo;
	}

	public void setIsYesNo(Boolean isYesNo) {
	this.isYesNo = isYesNo;
	}

	@Column(name = "ORGAN_SYSTEM_DESC")
	public String getDesc() {
	return desc;
	}

	public void setDesc(String organSystemDesc) {
	this.desc = organSystemDesc;
	}

	@Override
	public int hashCode() {
	final int prime = 31;
	int result = 0;
	result = prime * result + ((desc == null) ? 0 : desc.hashCode());
	return result;
	}

	@Override
	public boolean equals(Object obj) {
	if (this == obj) return true;
	if (obj == null) return false;
	if (getClass() != obj.getClass()) return false;
	OrganSystem other = (OrganSystem) obj;
	if (desc == null) {
		if (other.desc != null) return false;
	} else
		if (!desc.equalsIgnoreCase(other.desc)) return false;
	return true;
	}

	public String getNormalSentence() {
	return normalSentence;
	}

	public void setNormalSentence(String normalSentence) {
	this.normalSentence = normalSentence;
	}

	@OneToOne
	public Enumeration getGender() {
	return gender;
	}

	public void setGender(Enumeration gender) {
	this.gender = gender;
	}

	/*public void addQuestion(Question q) {
	if(this.getQuestions()==null) {
		setQuestions(new HashSet<Question>());
	}
	q.setOrganSystem(this);
	getQuestions().add(q);
	}
*/
}
