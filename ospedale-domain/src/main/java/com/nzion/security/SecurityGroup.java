package com.nzion.security;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.util.UtilReflection;
import com.nzion.util.UtilValidator;


@Entity
@Table
public class SecurityGroup extends IdGeneratingBaseEntity {
	private static final long serialVersionUID = 1L;
	private Set<PermissionGroup> grantedSecurityPermissions;
    private String securityGroupName;

    public SecurityGroup() {
    }

    public SecurityGroup(Set<PermissionGroup> grantedSecurityPermissions) {
    this.grantedSecurityPermissions = grantedSecurityPermissions;
    }

    public SecurityGroup(String securityGroupName,
                                          Set<PermissionGroup> grantedSecurityPermissions) {
        this.securityGroupName = securityGroupName;
        this.grantedSecurityPermissions = grantedSecurityPermissions;
    }

    public String getSecurityGroupName() {
        return securityGroupName;
    }

    public void setSecurityGroupName(String securityGroupName) {
        this.securityGroupName = securityGroupName;
    }


    @ManyToMany(targetEntity=PermissionGroup.class, fetch = FetchType.EAGER)
    @JoinTable(name = "SECURITYGROUP_PERMISSIONGROUP", joinColumns = {@JoinColumn(name = "SECURITYGROUP_ID")}, inverseJoinColumns = {@JoinColumn(name = "PERMISSIONGROUP")})
    public Set<PermissionGroup> getGrantedSecurityPermissions() {
        return grantedSecurityPermissions;
    }

    public void setGrantedSecurityPermissions(Set<PermissionGroup> grantedSecurityPermissions) {
        this.grantedSecurityPermissions = grantedSecurityPermissions;
    }
    
    public void add(PermissionGroup group){
	    if(grantedSecurityPermissions == null)
	    	grantedSecurityPermissions = new HashSet<PermissionGroup>();
	    grantedSecurityPermissions.add(group);
    }
    
    @Transient
    public Set<SecurityPermission> getAllPermissions(){
	    Set<SecurityPermission> permissions = new HashSet<SecurityPermission>();
	    if(UtilValidator.isEmpty(grantedSecurityPermissions))
	    	return permissions;
	    for(PermissionGroup group : grantedSecurityPermissions)
	    	permissions.addAll(group.getGrantedSecurityPermissions());
	    return permissions;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(grantedSecurityPermissions);
    }

    @Override
    public boolean equals(Object obj) {
        return UtilReflection.areEqual(obj, this, "grantedSecurityPermissions");
    }

    @Override
    public String toString() {
        return securityGroupName;
    }
}
