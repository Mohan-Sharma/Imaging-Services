package com.nzion.domain.emr;

import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.util.UtilReflection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Nthdimenzion on 25-Aug-16.
 */
@Entity
@Table(name = "spoken_language")
public class SpokenLanguage extends IdGeneratingBaseEntity {
    private static final long serialVersionUID = 1L;
    private String name;
    private String description;

    @Column(name = "NAME",nullable=false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        return UtilReflection.areEqual(this, obj, new String[]{"name"});
    }

    @Override
    public String toString() {
        return name;
    }
}
