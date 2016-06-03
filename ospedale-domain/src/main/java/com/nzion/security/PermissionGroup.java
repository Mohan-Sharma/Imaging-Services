package com.nzion.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.util.ObjectUtils;

import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.util.UtilReflection;
import com.nzion.util.UtilValidator;


// It represents a menu in the UI. Inside the corresponding menu screen the permission granularity will be used
@Entity
@Table
public class PermissionGroup extends IdGeneratingBaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String Description;
	private PermissionGroup parent;
	/*private int orderBy;*/
	private String type;
	
	private Set<PermissionGroup> childPermissionGroups;
	private Set<SecurityPermission> grantedSecurityPermissions;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}	
	@ManyToMany(targetEntity= SecurityPermission.class, fetch = FetchType.EAGER)
	@JoinTable(name = "permissiongroup_granted_security_permissions", joinColumns = {@JoinColumn(name = "PERMISSIONGROUP")}, inverseJoinColumns = {@JoinColumn(name = "GRANTEDSECURITYPERMISSIONS")})
	public Set<SecurityPermission> getGrantedSecurityPermissions() {
		return grantedSecurityPermissions;
	}
	public void setGrantedSecurityPermissions(Set<SecurityPermission> grantedSecurityPermissions) {
		this.grantedSecurityPermissions = grantedSecurityPermissions;
	}
	
	@ManyToOne(targetEntity=PermissionGroup.class)
	@JoinColumn(name="PARENT_ID")
	public PermissionGroup getParent() {
		return parent;
	}
	public void setParent(PermissionGroup parent) {
		this.parent = parent;
	}
	
	@OneToMany(targetEntity=PermissionGroup.class,mappedBy="parent",fetch=FetchType.EAGER)	
	public Set<PermissionGroup> getChildPermissionGroups() {		
		return childPermissionGroups;
	}
	public void setChildPermissionGroups(Set<PermissionGroup> childPermissionGroups) {
		this.childPermissionGroups = childPermissionGroups;
	}
	
	@Transient
	public List<PermissionGroup> getChildren()	{
		return childPermissionGroups == null ? Collections.EMPTY_LIST : new ArrayList<PermissionGroup>(childPermissionGroups);
	}
	
	@Transient
	public Set<SecurityPermission> getAllPermissions()	{
		Set<SecurityPermission> permissions = new HashSet<SecurityPermission>();
		if(UtilValidator.isNotEmpty(childPermissionGroups)){
			 for(PermissionGroup group : childPermissionGroups)
			    	permissions.addAll(group.getGrantedSecurityPermissions());		
		}
		permissions.addAll(grantedSecurityPermissions);
		return permissions;
	}
		
	@Override
	public String toString(){
		return name;
	}	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public int hashCode() {	    
		return ObjectUtils.nullSafeHashCode(name);		
	}
	
	@Override
	public boolean equals(Object obj) {
	return UtilReflection.areEqual(obj, this, "name");    
	
	}
	
}
