package com.nzion.dto;

import com.nzion.domain.Practice;
import com.nzion.domain.masterDataLIS.Accreditation;
import com.nzion.domain.masterDataLIS.Certification;
import com.nzion.domain.masterDataLIS.ServiceType;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class RadiologyInfDto {
    private BigDecimal turnaroundTime;
    private Set<AccreditationDto> accreditations = new HashSet<AccreditationDto>();
    private Set<LabCertificationDto> labcertifications = new HashSet<LabCertificationDto>();
    private Set<LabServiceTypeDto> labServiceTypeDtos = new HashSet<LabServiceTypeDto>();
    private String tenantId;
    private String labAvailabilityTime;

    public Set<AccreditationDto> getAccreditations() {
        return accreditations;
    }

    public void setAccreditations(Set<AccreditationDto> specialities) {
        this.accreditations = accreditations;
    }

    public Set<LabCertificationDto> getLabCertifications() {
        return labcertifications;
    }

    public void setCertifications(Set<LabCertificationDto> labcertifications) {
        this.labcertifications = labcertifications;
    }

    public Set<LabServiceTypeDto> getLabServiceTypeDtos() {
        return labServiceTypeDtos;
    }

    public void setLabServiceTypeDtos(Set<LabServiceTypeDto> labServiceTypeDtos) {
        this.labServiceTypeDtos = labServiceTypeDtos;
    }

    public BigDecimal getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(BigDecimal turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getLabAvailabilityTime() {
        return labAvailabilityTime;
    }

    public void setLabAvailabilityTime(String labAvailabilityTime) {
        this.labAvailabilityTime = labAvailabilityTime;
    }

    public void setPropertiesToLabInfDto(Practice practice) {
        Set<AccreditationDto> accreditationDtos = new HashSet<AccreditationDto>();
        AccreditationDto accreditationDto = null;
        for (Accreditation accreditation : practice.getAccreditationSet()) {
            accreditationDto = new AccreditationDto(accreditation.getAcrCode().toString(), accreditation.getAcrDescription());
            accreditationDtos.add(accreditationDto);
        }
        Set<LabCertificationDto> certificationDtos = new HashSet<LabCertificationDto>();
        LabCertificationDto labcertificationDto = null;
        for(Certification labcertification : practice.getCertificationSet()){
            labcertificationDto = new LabCertificationDto(labcertification.getCertificationCode().toString(),labcertification.getCertificationDescription());
            certificationDtos.add(labcertificationDto);

        }
        Set<LabServiceTypeDto> labServiceTypeDtos1 = new HashSet<LabServiceTypeDto>();
        LabServiceTypeDto labServiceTypeDto = null;
        for(ServiceType serviceType : practice.getServiceTypeSet()){
            labServiceTypeDto = new LabServiceTypeDto(serviceType.getServiceCode().toString(),serviceType.getServiceDescription());
            labServiceTypeDtos1.add(labServiceTypeDto);
        }
        this.accreditations = accreditationDtos;
        this.labcertifications = certificationDtos;
        this.labServiceTypeDtos = labServiceTypeDtos1;
        this.turnaroundTime = practice.getTurnaroundTime();
        this.tenantId = practice.getTenantId();
        this.labAvailabilityTime = practice.getLabAvailabilityTime();
    }

    private class AccreditationDto {
        String acrCode;
        String description;

        public AccreditationDto(String acrCode, String description) {
            this.acrCode = acrCode;
            this.description = description;
        }
    }

    private class LabCertificationDto {
        String certificationCode;
        String description;
        public LabCertificationDto(String certificationCode, String description){
            this.certificationCode = certificationCode;
            this.description = description;
        }

    }

    private class LabServiceTypeDto {
        String serviceCode;
        String serviceDescription;
        public LabServiceTypeDto(String serviceCode,String serviceDescription){
            this.serviceCode = serviceCode;
            this.serviceDescription = serviceDescription;
        }
    }

}
