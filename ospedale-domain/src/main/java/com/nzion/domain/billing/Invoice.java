package com.nzion.domain.billing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.nzion.domain.emr.lab.LabOrderRequest;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;

import com.nzion.domain.Employee;
import com.nzion.domain.Location;
import com.nzion.domain.Patient;
import com.nzion.domain.Referral;
import com.nzion.domain.annot.AccountNumberField;
import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.domain.base.LocationAware;
import com.nzion.domain.emr.lab.LabTestPanel;
import com.nzion.domain.product.common.Money;
import com.nzion.util.UtilValidator;

/**
 * @author Sandeep Prusty
 *         <p/>
 *         16-Sep-2011
 */

@Entity
@Filters({@Filter(name = "EnabledFilter", condition = "(IS_ACTIVE=1 OR IS_ACTIVE IS NULL)"),
          @Filter(name = "LocationFilter", condition = "( :locationId=LOCATION_ID OR LOCATION_ID IS NULL )")
})
@AccountNumberField("invoiceNumber")
public class Invoice extends IdGeneratingBaseEntity implements LocationAware,Comparable<Invoice> {

    private Date invoiceDate;

    private Money totalAmount;

    private Money amountToBePaid = new Money();

    private Money collectedAmount = new Money();

    private String invoiceStatus;

    private String itemId;

    private String itemType;

    private List<InvoiceItem> invoiceItems;

    private Employee consultant;
    
    private Long referralConsultantId;

    private Patient patient;


	public Long getReferralConsultantId() {
		return referralConsultantId;
	}

	public void setReferralConsultantId(Long referralConsultantId) {
		this.referralConsultantId = referralConsultantId;
	}


	private String ipNumber;

    private Location location;

    private Contract contract;

    private Set<InvoicePayment> invoicePayments;

    private InvoiceType invoiceType;

    private String invoiceNumber;
    
    private String externalPatient;
    
    private String externalProvider;
    
    private Date billDate;
    
    private String collectedByUser;
    
    private Date generatedOn;

    private LabOrderRequest labOrderId;
    
    @Temporal(TemporalType.DATE)
    public Date getGeneratedOn() {
		return generatedOn;
	}

	public void setGeneratedOn(Date generatedOn) {
		this.generatedOn = generatedOn;
	}

	public String getCollectedByUser() {
		return collectedByUser;
	}

	public void setCollectedByUser(String collectedByUser) {
		this.collectedByUser = collectedByUser;
	}

	public String getExternalProvider() {
		return externalProvider;
	}

	public void setExternalProvider(String externalProvider) {
		this.externalProvider = externalProvider;
	}

	public String getExternalPatient() {
		return externalPatient;
	}

	public void setExternalPatient(String externalPatient) {
		this.externalPatient = externalPatient;
	}


	/**
     * This represents the userLoginId which updated this entity.
     */
    private String writeOffBy;

    private Date writeOffDate;

    private Money writtenOffAmount;

    private String writtenOffReason;

    /* If this is true then It will shows that
      * payment received as Advance for in-patient while admission.
      */
    private Boolean isAdvance = false;

    /*
      * This will set true when received Advance for a particular in-patient is deducted while billing for that in-patient.
      * Or If the bill balance amount is added to next bill
      */
    private Boolean isDeducted = false;

    public Invoice() {
    }

    public Invoice(String itemId, String itemType, Employee consulatnt, Patient patient, Location location) {
        this.itemId = itemId;
        this.itemType = itemType;
        this.invoiceDate = new Date();
        //this.billDate = new Date();
        this.consultant = consulatnt;
        this.patient = patient;
        this.location = location;
        this.setInvoiceStatus(InvoiceStatusItem.READY.toString());
    }
    @Temporal(value = TemporalType.TIMESTAMP)
    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    @Embedded
    public Money getTotalAmount() {
        return totalAmount = (totalAmount == null ? new Money() : totalAmount);
    }

    public void setTotalAmount(Money totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "COLLECTED_PRICE"))
    @AssociationOverride(name = "currency", joinColumns = {@JoinColumn(name = "COLLECTED_CURRENCY_ID")})
    public Money getCollectedAmount() {
        if (collectedAmount != null) {
            if (collectedAmount.getAmount() == null) {
                collectedAmount.setAmount(BigDecimal.ZERO);
            }
        } else {
            collectedAmount = new Money();
        }
        return collectedAmount;
    }

    public void setCollectedAmount(Money collectedAmount) {
        this.collectedAmount = collectedAmount;
    }

    @Temporal(TemporalType.DATE)
    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    @OneToMany(targetEntity = InvoiceItem.class, mappedBy = "invoice", fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    @OrderBy("itemOrder")
    public List<InvoiceItem> getInvoiceItems() {
        if (invoiceItems == null) return new ArrayList<InvoiceItem>();
        return invoiceItems;
    }

    public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    @Transient
    public void addInvoiceItem(Collection<InvoiceItem> invoiceItems) {
        for (InvoiceItem invoiceItem : invoiceItems)
            addInvoiceItem(invoiceItem);
    }

    @Transient
    public void addInvoiceItem(InvoiceItem invoiceItem) {
        invoiceItem.setInvoice(this);
        if (UtilValidator.isEmpty(getInvoiceItems()))
            this.invoiceItems = new ArrayList<InvoiceItem>();
        this.invoiceItems.add(invoiceItem);
    }

    @ManyToOne
    public Employee getConsultant() {
        return consultant;
    }

    public void setConsultant(Employee consultant) {
        this.consultant = consultant;
    }

    @ManyToOne
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @ManyToOne(targetEntity = Location.class)
    @JoinColumn(name = "LOCATION_ID", nullable = false, updatable = false)
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @OneToMany(targetEntity = InvoicePayment.class, mappedBy = "invoice", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public Set<InvoicePayment> getInvoicePayments() {
        return invoicePayments;
    }

    public void setInvoicePayments(Set<InvoicePayment> invoicePayments) {
        this.invoicePayments = invoicePayments;
    }

    public void addInvoicePayment(InvoicePayment invoicePayment) {
        if (UtilValidator.isEmpty(getInvoicePayments()))
            invoicePayments = new HashSet<InvoicePayment>();
        invoicePayment.setInvoice(this);
        invoicePayments.add(invoicePayment);
    }


    @Transient
    public BigDecimal getBalanceAmount() {
        if (collectedAmount.getAmount() == null)
            return BigDecimal.ZERO;
        return totalAmount.getAmount().subtract(collectedAmount.getAmount());
    }

    public BigDecimal percent(BigDecimal arg) {
        if (totalAmount == null)
            return BigDecimal.ZERO;
        return totalAmount.getAmount().multiply(arg).divide(new BigDecimal(100));
    }

    @ManyToOne
    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public String getIpNumber() {
        return ipNumber;
    }

    public void setIpNumber(String ipNumber) {
        this.ipNumber = ipNumber;
    }

    public String getWriteOffBy() {
        return writeOffBy;
    }

    public void setWriteOffBy(String writeOffBy) {
        this.writeOffBy = writeOffBy;
    }

    @Temporal(value = TemporalType.TIMESTAMP)
    public Date getWriteOffDate() {
        return writeOffDate;
    }

    public void setWriteOffDate(Date writeOffDate) {
        this.writeOffDate = writeOffDate;
    }

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "WRITTENOFF_PRICE"))
    @AssociationOverride(name = "currency", joinColumns = {@JoinColumn(name = "WRITTENOFF_CURRENCY_ID")})
    public Money getWrittenOffAmount() {
        return writtenOffAmount = (writtenOffAmount == null ? new Money() : writtenOffAmount);
    }

    public void setWrittenOffAmount(Money writtenOffAmount) {
        this.writtenOffAmount = writtenOffAmount;
    }

    @Column(length = 1042)
    public String getWrittenOffReason() {
        return writtenOffReason;
    }

    public void setWrittenOffReason(String writtenOffReason) {
        this.writtenOffReason = writtenOffReason;
    }

    public Boolean getIsAdvance() {
        return isAdvance;
    }

    public void setIsAdvance(Boolean isAdvance) {
        this.isAdvance = isAdvance;
    }

    public Boolean getIsDeducted() {
        return isDeducted;
    }

    public void setIsDeducted(Boolean isDeducted) {
        this.isDeducted = isDeducted;
    }

    @Column
    @Enumerated(EnumType.STRING)
    public InvoiceType getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(InvoiceType invoiceType) {
        this.invoiceType = invoiceType;
    }

    @Column(name = "INVOICE_NUMBER")
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }


    private static final long serialVersionUID = 1L;
    
    @Override
    public int compareTo(Invoice other) {
                return other.invoiceDate.compareTo(this.invoiceDate);
    }

    @OneToOne
    @JoinColumn(name="LAB_ORDER_ID")
    public LabOrderRequest getLabOrderId() {
        return labOrderId;
    }

    public void setLabOrderId(LabOrderRequest labOrderId) {
        this.labOrderId = labOrderId;
    }
}