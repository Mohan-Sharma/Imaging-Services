package com.nzion.domain.masterDataLIS;

import com.nzion.domain.annot.AccountNumberField;
import com.nzion.domain.base.BaseEntity;
import com.nzion.domain.base.IdGeneratingBaseEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;

import javax.persistence.*;

/**
 * Created by Saikiran Chepuri on 03-Jun-16.
 */
@Entity
@Table(name = "ACCREDITATION")
/*@AccountNumberField
@Cache(usage= CacheConcurrencyStrategy.TRANSACTIONAL,region="com.nzion.domain")*/
public class Accreditation extends IdGeneratingBaseEntity {

    private static final long serialVersionUID = 1L;

    /*@Id
    @Column(name = "ACR_CODE")
    @GeneratedValue*/
    private Integer acrCode;
    private String acrName;
    private String acrDescription;
    private String accountNumber;

    public Integer getAcrCode() {
        return acrCode;
    }

    public void setAcrCode(Integer acrCode) {
        this.acrCode = acrCode;
    }

    public String getAcrName() {
        return acrName;
    }

    public void setAcrName(String acrName) {
        this.acrName = acrName;
    }

    public String getAcrDescription() {
        return acrDescription;
    }

    public void setAcrDescription(String acrDescription) {
        this.acrDescription = acrDescription;
    }

   /* @Transient
    @Column(name = "ACCOUNT_NUMBER", nullable = false)
    public String getAccountNumber() {
        return accountNumber;
    }*/
}
