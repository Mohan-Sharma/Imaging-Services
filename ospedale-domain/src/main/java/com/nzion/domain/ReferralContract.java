package com.nzion.domain;

import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.util.UtilValidator;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Blob;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Saikiran Chepuri on 02-May-16.
 */
@Entity
@Table(name = "REFERRAL_CONTRACT")
@Filters({@Filter(name = "EnabledFilter", condition = "(IS_ACTIVE=1 OR IS_ACTIVE IS NULL)")})
public class ReferralContract extends IdGeneratingBaseEntity {

    private Referral referral;

    private String referralType;

    private Date expiryDate;

    private Date contractDate;

    private String paymentMode;

    private String contractStatus = "IN-PROGRESS";

    private String paypoint = "ON_BILL";

    private Double percentageOnBill;

    private Blob document;

    private String documentName;

    private String referralClinicId;

    private String refereeClinicId;

    private String contractStatusNewLabel;
    private Set<ReferralContractService> referralContractServices;

    @OneToMany(targetEntity = ReferralContractService.class, fetch = FetchType.EAGER, mappedBy = "referralContract")
    @Cascade(CascadeType.ALL)
    public Set<ReferralContractService> getReferralContractServices() {
        if(referralContractServices == null )
            return new HashSet<ReferralContractService>();
        return referralContractServices;
    }

    public void setReferralContractServices(Set<ReferralContractService> referralContractServices) {
        this.referralContractServices = referralContractServices;
    }

    @ManyToOne(targetEntity = Referral.class)
    @JoinColumn(name = "REFERRAL_ID")
    public Referral getReferral() {
        return referral;
    }

    public void setReferral(Referral referral) {
        this.referral = referral;
    }

    @Column(name = "REFERRAL_TYPE")
    public String getReferralType() {
        return referralType;
    }

    public void setReferralType(String referralType) {
        this.referralType = referralType;
    }

    @Column(name = "EXPIRY_DATE")
    @Temporal(TemporalType.DATE)
    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Column(name = "CONTRACT_DATE")
    @Temporal(TemporalType.DATE)
    public Date getContractDate() {
        return contractDate;
    }

    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getPaypoint() {
        if(UtilValidator.isEmpty(paypoint)){
            paypoint = "ON_BILL";
        }
        return paypoint;
    }

    public void setPaypoint(String paypoint) {
        this.paypoint = paypoint;
    }

    @Column(precision = 8, scale = 3, columnDefinition = "DECIMAL(8,3)")
    public Double getPercentageOnBill() {
        return percentageOnBill;
    }

    public void setPercentageOnBill(Double percentageOnBill) {
        this.percentageOnBill = percentageOnBill;
    }

    @Lob
    public Blob getDocument() {
        return document;
    }

    public void setDocument(Blob document) {
        this.document = document;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getReferralClinicId() {
        return referralClinicId;
    }

    public void setReferralClinicId(String referralClinicId) {
        this.referralClinicId = referralClinicId;
    }

    public String getRefereeClinicId() {
        return refereeClinicId;
    }

    public void setRefereeClinicId(String refereeClinicId) {
        this.refereeClinicId = refereeClinicId;
    }

    @Transient
    public String getContractStatusNewLabel() {
        return contractStatusNewLabel;
    }

    public void setContractStatusNewLabel(String contractStatusNewLabel) {
        this.contractStatusNewLabel = contractStatusNewLabel;
    }

    public static enum REFERRAL_TYPE_ENUM{
        CONSULTANT,PHARMACY,LAB
    }

    public static enum REFEREE_TYPE_ENUM {
        CONSULTANT,PHARMACY,LAB
    }

    public static enum PAYMENT_MODE_ENUM {
        PERCENTAGE_OF_BILL,PERCENTAGE_SERVICE_ITEM,FIX_AMOUNT_PER_SERVICE
    }

    public static enum CONTRACT_STATUS_ENUM{
        INACTIVE,ACTIVE,EXPIRED
    }

    public static enum PAYPOINT_ENUM{
        ON_BILL,ON_PARTIAL_RECEIPT,ON_FULL_RECEIPT
    }
}
