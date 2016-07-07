package com.nzion.domain.masterDataLIS;

import com.nzion.domain.base.IdGeneratingBaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Saikiran Chepuri on 06-Jul-16.
 */
@Entity
@Table(name = "CERTIFICATION")
public class Certification extends IdGeneratingBaseEntity {

    private static final long serialVersionUID = 1L;
    private Integer certificationCode;
    private String certificationName;
    private String certificationDescription;

    public Integer getCertificationCode() {
        return certificationCode;
    }

    public void setCertificationCode(Integer certificationCode) {
        this.certificationCode = certificationCode;
    }

    public String getCertificationName() {
        return certificationName;
    }

    public void setCertificationName(String certificationName) {
        this.certificationName = certificationName;
    }

    public String getCertificationDescription() {
        return certificationDescription;
    }

    public void setCertificationDescription(String certificationDescription) {
        this.certificationDescription = certificationDescription;
    }

    @Override
    public String toString(){
        return certificationDescription;
    }

}
