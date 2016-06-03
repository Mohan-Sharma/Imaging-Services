package com.nzion.domain;

import com.nzion.domain.base.IdGeneratingBaseEntity;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Saikiran Chepuri on 03-May-16.
 */
@Entity
@Table(name = "REFERRAL_CONTRACT_SERVICE")
@org.hibernate.annotations.Cache(usage=CacheConcurrencyStrategy.TRANSACTIONAL,region="com.nzion.domain")
public class ReferralContractService extends IdGeneratingBaseEntity {

    private static final long serialVersionUID = 1L;

    private double paymentPercentage = 0;
    private double paymentAmount = 0;
    private Integer serviceCode = 0;
    private String serviceName;
    private String serviceSubGroupDescription;
    private Integer serviceSubGroupId = 0;
    private String serviceMainGroup;
    private Integer serviceMainGroupId = 0;
    private ReferralContract referralContract;

    public ReferralContractService(){

    }

    public ReferralContractService(double paymentPercentage,double paymentAmount,int serviceCode,String serviceName,String serviceSubGroupDescription, int serviceSubGroupId,
         String serviceMainGroup, int serviceMainGroupId, ReferralContract referralContract){
        this.paymentPercentage = paymentPercentage;
        this.paymentAmount = paymentAmount;
        this.serviceCode = serviceCode;
        this.serviceName = serviceName;
        this.serviceSubGroupDescription = serviceSubGroupDescription;
        this.serviceSubGroupId = serviceSubGroupId;
        this.serviceMainGroup = serviceMainGroup;
        this.serviceMainGroupId = serviceMainGroupId;
        this.referralContract = referralContract;

    }

    @ManyToOne
    @JoinColumn(name = "CONTRACT_ID")
    public ReferralContract getReferralContract() {
        return referralContract;
    }

    public void setReferralContract(ReferralContract referralContract) {
        this.referralContract = referralContract;
    }

    @Column(precision = 8, scale = 3, columnDefinition = "DECIMAL(8,3)")
    public double getPaymentPercentage() {
        return paymentPercentage;
    }

    public void setPaymentPercentage(double paymentPercentage) {
        this.paymentPercentage = paymentPercentage;
    }

    @Column(precision = 19,scale = 3,columnDefinition = "DECIMAL(19,3)")
    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

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

    public String getServiceSubGroupDescription() {
        return serviceSubGroupDescription;
    }

    public void setServiceSubGroupDescription(String serviceSubGroupDescription) {
        this.serviceSubGroupDescription = serviceSubGroupDescription;
    }

    public Integer getServiceSubGroupId() {
        return serviceSubGroupId;
    }

    public void setServiceSubGroupId(Integer serviceSubGroupId) {
        this.serviceSubGroupId = serviceSubGroupId;
    }

    public String getServiceMainGroup() {
        return serviceMainGroup;
    }

    public void setServiceMainGroup(String serviceMainGroup) {
        this.serviceMainGroup = serviceMainGroup;
    }

    public Integer getServiceMainGroupId() {
        return serviceMainGroupId;
    }

    public void setServiceMainGroupId(Integer serviceMainGroupId) {
        this.serviceMainGroupId = serviceMainGroupId;
    }
}
