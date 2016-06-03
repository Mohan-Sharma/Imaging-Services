package com.nzion.domain.emr;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;

import com.nzion.domain.base.IdGeneratingBaseEntity;

@Entity
@Table(name = "FAMILY_MEMBER", uniqueConstraints = { @UniqueConstraint(name="UNIQUE_PRACTICE_FAMILY_MEMBER", columnNames = { "RELATIONSHIP_NAME" }) })
@Filters( {
		@Filter(name = "EnabledFilter", condition = "(IS_ACTIVE=1 OR IS_ACTIVE IS NULL)") })
@Cache(usage=CacheConcurrencyStrategy.TRANSACTIONAL,region="com.nzion.domain")
public class FamilyMember extends IdGeneratingBaseEntity {

	private static final long serialVersionUID = 1L;

	private String relationshipName;
	
	@Column(name = "RELATIONSHIP_NAME")
	public String getRelationshipName() {
	return relationshipName;
	}

	public void setRelationshipName(String relationshipName) {
	this.relationshipName = relationshipName;
	}

	@Override
	public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((relationshipName == null) ? 0 : relationshipName.hashCode());
	return result;
	}

	@Override
	public boolean equals(Object obj) {
	if (this == obj) return true;
	if (obj == null) return false;
	if (getClass() != obj.getClass()) return false;
	FamilyMember other = (FamilyMember) obj;
	if (relationshipName == null) {
		if (other.relationshipName != null) return false;
	} else
		if (!relationshipName.equals(other.relationshipName)) return false;
	return true;
	}
	
	public void addFamilyMember(FamilyMember familyMember){
	
	}
}