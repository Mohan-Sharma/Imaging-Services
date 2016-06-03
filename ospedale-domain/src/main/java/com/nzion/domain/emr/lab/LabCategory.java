package com.nzion.domain.emr.lab;

import com.nzion.domain.base.IdGeneratingBaseEntity;
import org.hibernate.annotations.Filter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The different groups of tests Biochemistry, Hematology, Viral Hepatitis
 *
 * Biochemistry would have Lab Test Panels like Lipid Profile and
 * Lab Test Panels would have tests like HDL , LDL
 *
 */
@Entity
@Table
@Filter(name = "EnabledFilter", condition = "(IS_ACTIVE=1 OR IS_ACTIVE IS NULL)")
public class LabCategory extends IdGeneratingBaseEntity {

    private String name;

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private static final long serialVersionUID = 1L;

}
