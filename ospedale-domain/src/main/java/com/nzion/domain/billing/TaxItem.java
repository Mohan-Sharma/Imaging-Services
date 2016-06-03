package com.nzion.domain.billing;

import com.nzion.domain.base.IdGeneratingBaseEntity;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
@Filters({@Filter(name = "EnabledFilter", condition = "(IS_ACTIVE=1 OR IS_ACTIVE IS NULL)")})
public class TaxItem extends IdGeneratingBaseEntity {

    private static final long serialVersionUID = 1L;

    private BigDecimal taxValue = BigDecimal.ZERO;

    private String billingHead;

    private String taxLedgerName;

    public static enum TaxItemType {

        VAT("VAT"), CESS_ON_VAT("Cess"), SERVICE_TAX("Service Tax");

        private String description;

        private TaxItemType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    }

    public String getBillingHead() {
        return billingHead;
    }

    public void setBillingHead(String billingHead) {
        this.billingHead = billingHead;
    }

    public TaxItemType getTaxType() {
        return taxType;
    }

    public void setTaxType(TaxItemType taxType) {
        this.taxType = taxType;
    }

    private TaxItemType taxType;

    public BigDecimal getTaxValue() {
        return taxValue;
    }

    public void setTaxValue(BigDecimal taxValue) {
        this.taxValue = taxValue;
    }

    public TaxItem cessTaxItem;

    @OneToOne
    public TaxItem getCessTaxItem() {
        return cessTaxItem;
    }

    public void setCessTaxItem(TaxItem cessTaxItem) {
        this.cessTaxItem = cessTaxItem;
    }

    public String getTaxLedgerName() {
        return taxLedgerName;
    }

    public void setTaxLedgerName(String taxLedgerName) {
        this.taxLedgerName = taxLedgerName;
    }
}