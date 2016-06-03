/*
 * header file
 */
package com.nzion.domain.base;

import java.io.Serializable;
import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.nzion.domain.annot.SystemAuditable;
// TODO: Auto-generated Javadoc
/**
 * The Class IdGeneratingBaseEntity.
 * 
 * @author Sandeep Prusty Apr 1, 2010
 */

@MappedSuperclass
@SystemAuditable
public class IdGeneratingBaseEntity extends BaseEntity {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2848233224713623376L;

    /*
     * (non-Javadoc)
     * 
     * @see com.nzion.domain.base.BaseEntity#getId()
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue
    @Override
    public Long getId() {
	return (Long)super.getId();
    }

    /**
     * Sets the id.
     * 
     * @param id
     *            the new id
     */
    @Override
	public void setId(Serializable id) {
	super.setId(id);
    }
    @Transient
    private transient Integer hash = null;
    
	@Override
	public boolean equals(Object obj) {
	if(this==obj)
		return true;
	if(obj == null || !getClass().equals(obj.getClass()))
		return false;
	return getId() == null ? false : getId().equals(((IdGeneratingBaseEntity)obj).getId());
	}

	@Override
	public int hashCode() {
	return practiceIndependentHashCode();
	}
	
	public int practiceIndependentHashCode(){
	if(hash!=null) 
		return hash;
	if(getId()==null){
		hash = 0;
		return hash;
	}
	return getId().intValue();
	}
	
	public static final Comparator<IdGeneratingBaseEntity> ID_COMPARATOR = new Comparator<IdGeneratingBaseEntity> (){
	
		@Override
		public int compare(IdGeneratingBaseEntity o1, IdGeneratingBaseEntity o2) {
		return o1.getId().compareTo(o2.getId());
		}
	};
}