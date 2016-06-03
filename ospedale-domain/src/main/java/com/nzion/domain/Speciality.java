/*
 * header file
 */
package com.nzion.domain;

import com.nzion.domain.base.BaseEntity;
import org.hibernate.annotations.Filter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * The Class Speciality.
 */

/**
 * @author Sandeep Prusty
 *         Apr 26, 2010
 */
@Entity
@Table(name = "SPECIALITY", uniqueConstraints = {@UniqueConstraint(name = "UNIQUE_PRACTICE_SPECIALITY", columnNames = {"SPECIALITY_CODE"})})
@Filter(name = "EnabledFilter", condition = "(IS_ACTIVE=1 OR IS_ACTIVE IS NULL)")
public class Speciality extends BaseEntity implements Comparable<Speciality> {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    private String code;
    private String description;


    @Column(name = "SPECIALITY_CODE")
    @Id
    public String getCode() {
        return code;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

   
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Speciality other = (Speciality) obj;
        if (this.code == null) {
            if (other.code != null) return false;
        } else {
            if (!this.code.equals(other.code)) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 0;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        return result;
    }

    @Override
    public int compareTo(Speciality s) {
        return this.description.compareToIgnoreCase(s.description);
    }

}