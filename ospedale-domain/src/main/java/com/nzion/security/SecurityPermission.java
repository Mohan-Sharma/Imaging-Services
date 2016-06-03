package com.nzion.security;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.ObjectUtils;

import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.util.UtilReflection;

@Entity
@Table
public class SecurityPermission extends IdGeneratingBaseEntity implements GrantedAuthority, Serializable {
	private static final long serialVersionUID = 1L;
	private String role;
	
	public SecurityPermission(){		
	}
	public SecurityPermission(String role) {
	this.role = role;
	}

	public String getRole() {
	return role;
	}

	public void setRole(String role) {
	this.role = role;
	}

	@Override
	@Transient
	public String getAuthority() {
	return role;
	}

	@Override
	public int hashCode() {
	return ObjectUtils.nullSafeHashCode(role);
	}

	@Override
	public boolean equals(Object obj) {
	return UtilReflection.areEqual(obj, this, "role");
	}

	@Override
	public String toString() {
	return role;
	}
}