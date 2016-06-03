package com.nzion.service.dto;

/**
 * Created by Saikiran Chepuri on 03-May-16.
 */
public class ServiceMasterDto {

    private int serviceCode;
    private String serviceName;
    private String serviceSubGroupDescription;
    private int serviceSubGroupId;
    private String serviceMainGroup;
    private int serviceMainGroupId;

    public int getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(int serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceSubGroupDescription() {
        return serviceSubGroupDescription;
    }

    public void setServiceSubGroupDescription(String serviceSubGroupDescription) {
        this.serviceSubGroupDescription = serviceSubGroupDescription;
    }

    public int getServiceSubGroupId() {
        return serviceSubGroupId;
    }

    public void setServiceSubGroupId(int serviceSubGroupId) {
        this.serviceSubGroupId = serviceSubGroupId;
    }

    public String getServiceMainGroup() {
        return serviceMainGroup;
    }

    public void setServiceMainGroup(String serviceMainGroup) {
        this.serviceMainGroup = serviceMainGroup;
    }

    public int getServiceMainGroupId() {
        return serviceMainGroupId;
    }

    public void setServiceMainGroupId(int serviceMainGroupId) {
        this.serviceMainGroupId = serviceMainGroupId;
    }
}
