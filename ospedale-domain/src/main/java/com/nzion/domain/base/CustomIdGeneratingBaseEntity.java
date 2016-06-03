package com.nzion.domain.base;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public class CustomIdGeneratingBaseEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GenericGenerator(name = "SEQ_ID", strategy = "com.nzion.repository.impl.HibernateCustomPrimaryKeyGenerator")
	@GeneratedValue(generator = "SEQ_ID")
	@Override
	public String getId() {
	return (String) super.getId();
	}

	@Transient
	private transient Integer hash = null;

	@Override
	public boolean equals(Object obj) {
	if (this == obj) return true;
	if (obj == null || !getClass().equals(obj.getClass())) return false;
	return getId() == null ? false : getId().equals(((CustomIdGeneratingBaseEntity) obj).getId().toString());
	}

	@Override
	public int hashCode() {
	if (hash != null) return hash;
	if (getId() == null) {
		hash = 0;
		return hash;
	}
	return getId().hashCode();
	}
}
