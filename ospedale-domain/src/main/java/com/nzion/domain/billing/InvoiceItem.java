package com.nzion.domain.billing;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.domain.product.common.Money;

/**
 * @author Sandeep Prusty
 *         <p/>
 *         16-Sep-2011
 */

@Entity
public class InvoiceItem extends IdGeneratingBaseEntity {

    private static final long serialVersionUID = 1L;

    private Invoice invoice;
    private String description;
    private String itemId;
    private InvoiceType itemType;
    private BigDecimal factor;
    private String factorDescription;
    private Money price;
    private BigDecimal unitPrice;
    private Long providerId;
    private Long referralId;


    public Long getReferralId() {
		return referralId;
	}

	public void setReferralId(Long referralId) {
		this.referralId = referralId;
	}

	public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    private String taxLedgerName;
    private BigDecimal taxAmount;
    private BigDecimal cessAmount;

    private BigDecimal quantity = BigDecimal.ONE;
    private String quanityDesc;
    private Integer itemOrder = 0;
    private Date billedDate;
    private String batchNo;

    private String roomCharge;
    private String source;


    public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getRoomCharge() {
        return roomCharge;
    }

    public void setRoomCharge(String roomCharge) {
        this.roomCharge = roomCharge;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    InvoiceItem() {
    }

    public InvoiceItem(Invoice invoice, String itemId, InvoiceType itemType, String description, Integer quantity, String quantityDescription,String source) {
        this.invoice = invoice;
        this.itemId = itemId;
        this.itemType = itemType;
        this.description = description;
        if (quantity != null)
            this.quantity = new BigDecimal(quantity);
        this.quanityDesc = quantityDescription;
        this.source=source;
    }

    public void init(BigDecimal factor, String factorDesc, Money price, Integer itemOrder) {
        this.factor = factor;
        this.factorDescription = factorDesc;
        this.price = price;
        this.itemOrder = itemOrder;
    }

    public BigDecimal getCessAmount() {
        return cessAmount;
    }

    public void setCessAmount(BigDecimal cessAmount) {
        this.cessAmount = cessAmount;
    }

    public String getTaxLedgerName() {
        return taxLedgerName;
    }

    public void setTaxLedgerName(String taxLedgerName) {
        this.taxLedgerName = taxLedgerName;
    }

    public BigDecimal getFactor() {
        return factor;
    }

    public void setFactor(BigDecimal factor) {
        this.factor = factor;
    }

    public String getFactorDescription() {
        return factorDescription;
    }

    public void setFactorDescription(String factorDescription) {
        this.factorDescription = factorDescription;
    }

    @ManyToOne
    @JoinColumn(name = "INVOICE_ID")
    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    @Transient
    public BigDecimal getPriceValue() {
        if (price == null)
            price = new Money();
        return price.getAmount();
    }

    public void setPriceValue(BigDecimal priceValue) {
        this.price = new Money(priceValue, price.getCurrency());
    }

    @Transient
    public BigDecimal getUnitPrice() {
        return price.div(new Double(quantity.doubleValue())).getAmount();
    }

    @Lob
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Enumerated(EnumType.STRING)
    public InvoiceType getItemType() {
        return itemType;
    }

    public void setItemType(InvoiceType itemType) {
        this.itemType = itemType;
    }

    public Integer getItemOrder() {
        return itemOrder;
    }

    public void setItemOrder(Integer itemOrder) {
        this.itemOrder = itemOrder;
    }

    @Embedded
    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

	/*public Integer getQuantity() {
    return quantity;
	}

	public void setQuantity(Integer quantity) {
	this.quantity = quantity;
	}
*/

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getQuanityDesc() {
        return quanityDesc;
    }

    public void setQuanityDesc(String quanityDesc) {
        this.quanityDesc = quanityDesc;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getBilledDate() {
        return billedDate;
    }

    public void setBilledDate(Date billedDate) {
        this.billedDate = billedDate;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }


}