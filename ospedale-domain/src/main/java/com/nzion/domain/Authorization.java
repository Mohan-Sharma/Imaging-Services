package com.nzion.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

/**
 * @author Sandeep Prusty
 * Dec 20, 2010
 */

@Embeddable
public class Authorization implements Serializable, Authorizable{

	private long roles;
	
	public Authorization(){}
	
	public Authorization(long roles){
	this.roles = roles;
	}
	
	public Authorization(Authorization authorization){
	this.roles = authorization.roles;
	}

	@Column
	public long getRoles() {
	return roles;
	}

	public void setRoles(long roles) {
	this.roles = roles;
	}
	
	public void addRole(long role){
	this.roles = this.roles | role; 
	}
	
	public Long getRoles1(long role){
	return this.roles = this.roles | role; 	
	}
	
	public void removeRole(long role){
	this.roles = (this.roles & (~role));
	}
	
	public boolean hasAllRoles(long... roles){
	for(long role : roles)
		if(!hasRole(role))
			return false;
	return true;
	}

	public boolean hasRole(long role){
	return (this.roles & role) > 0;
	}
	
	public boolean hasAnyRole(long... roles){
	for(long role : roles)
		if(hasRole(role))
			return true;
	return false;
	}
	
	@Transient
	public long getMostPriorRole(){
	long highestRole = Roles.SUPER_ADMIN;
	while((highestRole & roles) == 0)
		highestRole = highestRole >> 1;
	return highestRole;
	}
	
	@Transient
	public boolean isAuthorizedToNone(){
	return roles == 0;
	}
	
	public boolean check(Authorization authorization){
	return (( authorization != null) && ((roles & authorization.roles) != 0));
	}
	
	@Override
	public boolean equals(Object obj) {
	return obj instanceof Authorization && roles == ((Authorization)obj).roles;
	}

	@Override
	public int hashCode() {
	return (int)roles;
	}

	private static final long serialVersionUID = 1L;

	public boolean hasRoleOrMore(long role) {
	return this.roles > role;
	}
}