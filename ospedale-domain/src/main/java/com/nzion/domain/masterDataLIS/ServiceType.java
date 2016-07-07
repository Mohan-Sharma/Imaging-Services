package com.nzion.domain.masterDataLIS;

import com.nzion.domain.base.IdGeneratingBaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Saikiran Chepuri on 06-Jul-16.
 */
@Entity
@Table(name = "SERVICE_TYPE")
public class ServiceType extends IdGeneratingBaseEntity {

    private static final long serialVersionUID = 1L;
    private Integer serviceCode;
    private String serviceName;
    private String serviceDescription;

    public Integer getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(Integer serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    @Override
    public String toString(){
        return serviceDescription;
    }
}
