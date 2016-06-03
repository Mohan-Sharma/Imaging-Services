package com.nzion.domain.billing;

/**
 * Created with IntelliJ IDEA.
 * User: Nth
 * Date: 1/2/13
 * Time: 5:25 PM
 * To change this template use File | Settings | File Templates.
 */
public enum InvoiceType {

    OPD_VISIT("Visit") {
        @Override
        public AcctgTransTypeEnum getAccountingTransType() {
            return AcctgTransTypeEnum.OPD_VISIT;
        }
    },
    OPD_PROCEDURE("Procedure") {
        @Override
        public AcctgTransTypeEnum getAccountingTransType() {
            return AcctgTransTypeEnum.OPD_PROCEDURE;
        }
    }, OPD_LAB_CHARGES("Lab Charges") {
        @Override
        public AcctgTransTypeEnum getAccountingTransType() {
            return AcctgTransTypeEnum.OPD_LAB_CHARGES;
        }
    }, OPD_CONSULTATION("Consultation") {
        @Override
        public AcctgTransTypeEnum getAccountingTransType() {
            return AcctgTransTypeEnum.OPD_CONSULTATION;
        }
    }, OPD_REGISTRATION("Registration") {
        @Override
        public AcctgTransTypeEnum getAccountingTransType() {
            return AcctgTransTypeEnum.OPD_REGISTRATION;
        }
    }, OPD_PAYMENT("Payment") {
        @Override
        public AcctgTransTypeEnum getAccountingTransType() {
            return AcctgTransTypeEnum.OPD_PAYMENT;
        }


    }, CASUALTY("Casualty") {
        @Override
        public AcctgTransTypeEnum getAccountingTransType() {
            return AcctgTransTypeEnum.CASUALTY;
        }
    },
    VAT("VAT") {
        @Override
        public AcctgTransTypeEnum getAccountingTransType() {
            return AcctgTransTypeEnum.VAT;
        }
    }, SERVICE_TAX("Service Tax") {
        @Override
        public AcctgTransTypeEnum getAccountingTransType() {
            return AcctgTransTypeEnum.SERVICE_TAX;
        }
    }, OPD("OPD") {
        @Override
        public AcctgTransTypeEnum getAccountingTransType() {
            return AcctgTransTypeEnum.OPD;
        }

    }, DEDUCTIBLE("Less") {
        @Override
        public AcctgTransTypeEnum getAccountingTransType() {
            return null;
        }

    }, IPD_DIAGNOSIS("Diagnosis Procedure") {
        @Override
        public AcctgTransTypeEnum getAccountingTransType() {
            return null;
        }
    };

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    InvoiceType(String description) {
        this.description = description;
    }

    public abstract AcctgTransTypeEnum getAccountingTransType();
}
