package com.nzion.domain.billing;

import com.nzion.domain.base.IdGeneratingBaseEntity;
import org.joda.money.CurrencyUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Nth
 * Date: 12/24/12
 * Time: 3:12 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class AcctgTransactionEntry extends IdGeneratingBaseEntity {

    	
	private String description;

    private BigDecimal amount;

    private String currency = "INR";

    private DebitCreditEnum debitOrCredit;

    private int transEntrySeq;

    private String acctgTransId;

    private String ledgerId;

    private String labTestCode;

    private String roomNumber;

    private String procedureCode;

    private String clinicalServiceCode;

    private String drugName;

    private String ipNumber;

    private String patientId;

    private String doctorId;

    private String specialityCode;

    private String additionalBedCharge;

    private String paymentId;

    private Date transactionDate;	
    
    public String getReferralId() {
		return referralId;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public void setReferralId(String referralId) {
		this.referralId = referralId;
	}

	private AcctgTransTypeEnum transactionType;

    private String parentTransactionTypeId;
    
    private String referralId;
    
    AcctgTransactionEntry() {
    }

    private AcctgTransactionEntry(String ledgerId, BigDecimal amount, DebitCreditEnum debitOrCredit, int transEntrySeq, AcctgTransTypeEnum transactionType, String transactionDescription,String acctgTransId) {
        this.amount = amount;
        this.currency = CurrencyUnit.getInstance("INR").getCurrencyCode();
        this.debitOrCredit = debitOrCredit;
        this.transEntrySeq = transEntrySeq;
        this.transactionType = transactionType;
        this.description = transactionDescription;
        this.ledgerId = ledgerId;
        this.acctgTransId=acctgTransId;
        this.transactionDate=new Date();
    }

    public static AcctgTransactionEntry createWithProcedure(String ledgerId, String procedureCode, BigDecimal amount, DebitCreditEnum debitOrCredit, int transEntrySeq, AcctgTransTypeEnum transactionType, String transactionDescription,String acctgTransId) {
        AcctgTransactionEntry acctgTransactionEntry = new AcctgTransactionEntry(ledgerId, amount, debitOrCredit, transEntrySeq, transactionType, transactionDescription,acctgTransId);
        acctgTransactionEntry.procedureCode = procedureCode;
        return acctgTransactionEntry;
    }

    public static AcctgTransactionEntry createWithLabTest(String ledgerId, String labTestCode, BigDecimal amount, DebitCreditEnum debitOrCredit, int transEntrySeq, AcctgTransTypeEnum transactionType, String transactionDescription,String acctgTransId) {
        AcctgTransactionEntry acctgTransactionEntry = new AcctgTransactionEntry(ledgerId, amount, debitOrCredit, transEntrySeq, transactionType, transactionDescription,acctgTransId);
        acctgTransactionEntry.labTestCode = labTestCode;
        return acctgTransactionEntry;
    }
    
    
    public static AcctgTransactionEntry createWithConsultation(String ledgerId, String id, BigDecimal amount, DebitCreditEnum debitOrCredit, int transEntrySeq, AcctgTransTypeEnum transactionType, String transactionDescription,String acctgTransId) {
        AcctgTransactionEntry acctgTransactionEntry = new AcctgTransactionEntry(ledgerId, amount, debitOrCredit, transEntrySeq, transactionType, transactionDescription,acctgTransId);
        return acctgTransactionEntry;
    }

    public static AcctgTransactionEntry createWithRegistration(String ledgerId, String patientId, BigDecimal amount, DebitCreditEnum debitOrCredit, int transEntrySeq, AcctgTransTypeEnum transactionType, String transactionDescription,String acctgTransId) {
        AcctgTransactionEntry acctgTransactionEntry = new AcctgTransactionEntry(ledgerId, amount, debitOrCredit, transEntrySeq, transactionType, transactionDescription,acctgTransId);
        acctgTransactionEntry.patientId = patientId;
        return acctgTransactionEntry;
    }
    
    public static AcctgTransactionEntry createWithRoomNumber(String ledgerId, String roomNumber, BigDecimal amount, DebitCreditEnum debitOrCredit, int transEntrySeq, AcctgTransTypeEnum transactionType, String transactionDescription,String acctgTransId) {
        AcctgTransactionEntry acctgTransactionEntry = new AcctgTransactionEntry(ledgerId, amount, debitOrCredit, transEntrySeq, transactionType, transactionDescription,acctgTransId);
        acctgTransactionEntry.roomNumber = roomNumber;
        return acctgTransactionEntry;
    }

    public static AcctgTransactionEntry createWithClinicalService(String ledgerId, String clinicalServiceCode, BigDecimal amount, DebitCreditEnum debitOrCredit, int transEntrySeq, AcctgTransTypeEnum transactionType, String transactionDescription,String acctgTransId) {
        AcctgTransactionEntry acctgTransactionEntry = new AcctgTransactionEntry(ledgerId, amount, debitOrCredit, transEntrySeq, transactionType, transactionDescription,acctgTransId);
        acctgTransactionEntry.clinicalServiceCode = clinicalServiceCode;
        return acctgTransactionEntry;
    }

    public static AcctgTransactionEntry createWithDrugName(String ledgerId, String drugName, BigDecimal amount, DebitCreditEnum debitOrCredit, int transEntrySeq, AcctgTransTypeEnum transactionType, String transactionDescription,String acctgTransId) {
        AcctgTransactionEntry acctgTransactionEntry = new AcctgTransactionEntry(ledgerId, amount, debitOrCredit, transEntrySeq, transactionType, transactionDescription,acctgTransId);
        acctgTransactionEntry.drugName = drugName;
        return acctgTransactionEntry;
    }

    public static AcctgTransactionEntry createWithAdditionalRoomCharges(String ledgerId, String additionalBedCharge, BigDecimal amount, DebitCreditEnum debitOrCredit, int transEntrySeq, AcctgTransTypeEnum transactionType, String transactionDescription,String acctgTransId) {
        AcctgTransactionEntry acctgTransactionEntry = new AcctgTransactionEntry(ledgerId, amount, debitOrCredit, transEntrySeq, transactionType, transactionDescription,acctgTransId);
        acctgTransactionEntry.additionalBedCharge = additionalBedCharge;
        return acctgTransactionEntry;
    }

   
    
    public static AcctgTransactionEntry createWithPaymentDetail(String ledgerId, String paymentId, BigDecimal amount, DebitCreditEnum debitOrCredit, int transEntrySeq, AcctgTransTypeEnum transactionType, String transactionDescription,String acctgTransId) {
        AcctgTransactionEntry acctgTransactionEntry = new AcctgTransactionEntry(ledgerId, amount, debitOrCredit, transEntrySeq, transactionType, transactionDescription,acctgTransId);
        acctgTransactionEntry.paymentId = paymentId;
        return acctgTransactionEntry;
    }

    public static AcctgTransactionEntry createWithWriteoffDetail(String ledgerId, String paymentId, BigDecimal amount, DebitCreditEnum debitOrCredit, int transEntrySeq, AcctgTransTypeEnum transactionType, String transactionDescription,String acctgTransId) {
        AcctgTransactionEntry acctgTransactionEntry = new AcctgTransactionEntry(ledgerId, amount, debitOrCredit, transEntrySeq, transactionType, transactionDescription,acctgTransId);
        return acctgTransactionEntry;
    }

    
    
    public static AcctgTransactionEntry createWithCasualty(String ledgerId, String id, BigDecimal amount, DebitCreditEnum debitOrCredit, int transEntrySeq, AcctgTransTypeEnum transactionType, String transactionDescription,String acctgTransId) {
        AcctgTransactionEntry acctgTransactionEntry = new AcctgTransactionEntry(ledgerId, amount, debitOrCredit, transEntrySeq, transactionType, transactionDescription,acctgTransId);
        return acctgTransactionEntry;
    }
    
    public static AcctgTransactionEntry createWithAdmission(String ledgerId, String patientId, BigDecimal amount, DebitCreditEnum debitOrCredit, int transEntrySeq, AcctgTransTypeEnum transactionType, String transactionDescription,String acctgTransId) {
        AcctgTransactionEntry acctgTransactionEntry = new AcctgTransactionEntry(ledgerId, amount, debitOrCredit, transEntrySeq, transactionType, transactionDescription,acctgTransId);
        acctgTransactionEntry.patientId = patientId;
        return acctgTransactionEntry;
    }
    
    public static AcctgTransactionEntry createWithPharmacy(String ledgerId, String patientId, BigDecimal amount, DebitCreditEnum debitOrCredit, int transEntrySeq, AcctgTransTypeEnum transactionType, String transactionDescription,String acctgTransId) {
        AcctgTransactionEntry acctgTransactionEntry = new AcctgTransactionEntry(ledgerId, amount, debitOrCredit, transEntrySeq, transactionType, transactionDescription,acctgTransId);
        acctgTransactionEntry.patientId = patientId;
        return acctgTransactionEntry;
    }
    
    public static AcctgTransactionEntry createWithTax(String ledgerId, String code, BigDecimal amount, DebitCreditEnum debitOrCredit, int transEntrySeq, AcctgTransTypeEnum transactionType, String transactionDescription,String acctgTransId) {
        AcctgTransactionEntry acctgTransactionEntry = new AcctgTransactionEntry(ledgerId, amount, debitOrCredit, transEntrySeq, transactionType, transactionDescription,acctgTransId);
        return acctgTransactionEntry;
    }
 
   
    public static AcctgTransactionEntry createWithRadiology(String ledgerId, String id, BigDecimal amount, DebitCreditEnum debitOrCredit, int transEntrySeq, AcctgTransTypeEnum transactionType, String transactionDescription,String acctgTransId) {
        AcctgTransactionEntry acctgTransactionEntry = new AcctgTransactionEntry(ledgerId, amount, debitOrCredit, transEntrySeq, transactionType, transactionDescription,acctgTransId);
        return acctgTransactionEntry;
    }
   
    
    
    
    public AcctgTransactionEntry updateWithPatientDoctorAndSpeciality(String patientId, String doctorId, String specialityCode) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.specialityCode = specialityCode;
        return this;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "AMOUNT")
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Column(name = "CURRENCY")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Column(name = "DEBIT_CREDIT_FLAG")
    @Enumerated(EnumType.STRING)
    public DebitCreditEnum getDebitOrCredit() {
        return debitOrCredit;
    }

    public void setDebitOrCredit(DebitCreditEnum debitOrCredit) {
        this.debitOrCredit = debitOrCredit;
    }

    @Column(name = "TRANS_ENTRY_SEQ")
    public int getTransEntrySeq() {
        return transEntrySeq;
    }

    public void setTransEntrySeq(int transEntrySeq) {
        this.transEntrySeq = transEntrySeq;
    }

    public String getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(String ledgerId) {
        this.ledgerId = ledgerId;
    }

    public String getAcctgTransId() {
        return acctgTransId;
    }

    public void setAcctgTransId(String acctgTransId) {
        this.acctgTransId = acctgTransId;
    }

    public String getLabTestCode() {
        return labTestCode;
    }

    public void setLabTestCode(String labTestCode) {
        this.labTestCode = labTestCode;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getProcedureCode() {
        return procedureCode;
    }

    public void setProcedureCode(String procedureCode) {
        this.procedureCode = procedureCode;
    }

    public String getClinicalServiceCode() {
        return clinicalServiceCode;
    }

    public void setClinicalServiceCode(String clinicalServiceCode) {
        this.clinicalServiceCode = clinicalServiceCode;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getIpNumber() {
        return ipNumber;
    }

    public void setIpNumber(String ipNumber) {
        this.ipNumber = ipNumber;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getSpecialityCode() {
        return specialityCode;
    }

    public void setSpecialityCode(String specialityCode) {
        this.specialityCode = specialityCode;
    }

    public String getAdditionalBedCharge() {
        return additionalBedCharge;
    }

    public void setAdditionalBedCharge(String additionalBedCharge) {
        this.additionalBedCharge = additionalBedCharge;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    @Enumerated(EnumType.STRING)
    public AcctgTransTypeEnum getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(AcctgTransTypeEnum transactionType) {
        this.transactionType = transactionType;
    }

    public String getParentTransactionTypeId() {
        return parentTransactionTypeId;
    }

    public void setParentTransactionTypeId(String parentTransactionTypeId) {
        this.parentTransactionTypeId = parentTransactionTypeId;
    }
}