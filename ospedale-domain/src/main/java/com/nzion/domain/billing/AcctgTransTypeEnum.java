package com.nzion.domain.billing;

import com.nzion.domain.Person;
import com.nzion.domain.Provider;
import com.nzion.domain.emr.soap.PatientLabOrder;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: Nth
 * Date: 12/24/12
 * Time: 3:15 PM
 * To change this template use File | Settings | File Templates.
 */
public enum AcctgTransTypeEnum {


    OPD_VISIT("OPD Visit") {
        @Override
        public AcctgTransactionEntry createAccountingTransactionEntry(InvoiceItem invoiceItem, int invoiceItemSequence, String doctorId, String patientId, String specialityCode, String acctgTransId) {
            AcctgTransactionEntry acctgTransactionEntry = AcctgTransactionEntry.createWithAdmission("Patient_" + patientId, patientId,
                    BigDecimal.ZERO, DebitCreditEnum.DEBIT, invoiceItemSequence, this, this.getDescription(), acctgTransId);
            acctgTransactionEntry.setDoctorId(doctorId);
            acctgTransactionEntry.setPatientId(patientId);
            acctgTransactionEntry.setSpecialityCode(specialityCode);
            return acctgTransactionEntry;
        }

        @Override
        public AcctgTransTypeEnum[] getChildTypes() {
            return new AcctgTransTypeEnum[0];
        }

        @Override
        public Class getClassName() {
            return null;
        }

        public String getPropery() {
            return null;
        }

        public String getReportField() {
            return null;
        }

        public DebitCreditEnum getDebitCreditType() {
            return DebitCreditEnum.DEBIT;
        }

    }, OPD_PROCEDURE("OPD Procedure") {
        @Override
        public AcctgTransactionEntry createAccountingTransactionEntry(InvoiceItem invoiceItem, int invoiceItemSequence, String doctorId, String patientId, String specialityCode, String acctgTransId) {
            AcctgTransactionEntry acctgTransactionEntry = AcctgTransactionEntry.createWithProcedure(GlAccountEnum.Procedure.getDescription(), invoiceItem.getItemId(),
                    invoiceItem.getPrice().getAmount(), DebitCreditEnum.CREDIT, invoiceItemSequence, this, invoiceItem.getDescription(), acctgTransId);
            acctgTransactionEntry.setDoctorId(doctorId);
            acctgTransactionEntry.setPatientId(patientId);
            acctgTransactionEntry.setSpecialityCode(specialityCode);
            return acctgTransactionEntry;
        }

        @Override
        public AcctgTransTypeEnum[] getChildTypes() {
            return new AcctgTransTypeEnum[0];
        }

        @Override
        public Class getClassName() {
            return null;
        }

        public String getPropery() {
            return null;
        }

        public String getReportField() {
            return null;
        }

        public DebitCreditEnum getDebitCreditType() {
            return DebitCreditEnum.CREDIT;
        }

    }, OPD_LAB_CHARGES("Lab Charges") {
        @Override
        public AcctgTransactionEntry createAccountingTransactionEntry(InvoiceItem invoiceItem, int invoiceItemSequence, String doctorId, String patientId, String specialityCode, String acctgTransId) {
            AcctgTransactionEntry acctgTransactionEntry = AcctgTransactionEntry.createWithLabTest(GlAccountEnum.LabCharges.getDescription(), invoiceItem.getItemId(),
                    invoiceItem.getPrice().getAmount(), DebitCreditEnum.CREDIT, invoiceItemSequence, this, invoiceItem.getDescription(), acctgTransId);
            acctgTransactionEntry.setPatientId(patientId);
            acctgTransactionEntry.setSpecialityCode(specialityCode);
            return acctgTransactionEntry;
        }

        @Override
        public AcctgTransTypeEnum[] getChildTypes() {
            return new AcctgTransTypeEnum[0];
        }

        @Override
        public Class getClassName() {
            return PatientLabOrder.class;
        }

        public String getPropery() {
            return null;
        }

        public String getReportField() {
            return null;
        }

        public DebitCreditEnum getDebitCreditType() {
            return DebitCreditEnum.CREDIT;
        }

    }, OPD_CONSULTATION("OPD Consultation") {
        @Override
        public AcctgTransactionEntry createAccountingTransactionEntry(InvoiceItem invoiceItem, int invoiceItemSequence, String doctorId, String patientId, String specialityCode, String acctgTransId) {
            AcctgTransactionEntry acctgTransactionEntry = AcctgTransactionEntry.createWithConsultation(GlAccountEnum.Consultation.getDescription(), invoiceItem.getItemId(),
                    invoiceItem.getPrice().getAmount(), DebitCreditEnum.CREDIT, invoiceItemSequence, this, invoiceItem.getDescription(), acctgTransId);
            acctgTransactionEntry.setDoctorId(doctorId);
            acctgTransactionEntry.setPatientId(patientId);
            acctgTransactionEntry.setSpecialityCode(specialityCode);
            return acctgTransactionEntry;
        }

        @Override
        public AcctgTransTypeEnum[] getChildTypes() {
            return new AcctgTransTypeEnum[0];
        }

        @Override
        public Class getClassName() {
            return null;
        }

        public String getPropery() {
            return null;
        }

        public String getReportField() {
            return null;
        }

        public DebitCreditEnum getDebitCreditType() {
            return DebitCreditEnum.CREDIT;
        }

    }, OPD_REGISTRATION("OPD Registration") {
        @Override
        public AcctgTransactionEntry createAccountingTransactionEntry(InvoiceItem invoiceItem, int invoiceItemSequence, String doctorId, String patientId, String specialityCode, String acctgTransId) {
            AcctgTransactionEntry acctgTransactionEntry = AcctgTransactionEntry.createWithRegistration(GlAccountEnum.Registration.getDescription(), invoiceItem.getItemId(),
                    invoiceItem.getPrice().getAmount(), DebitCreditEnum.CREDIT, invoiceItemSequence, this, invoiceItem.getDescription(), acctgTransId);
            acctgTransactionEntry.setDoctorId(doctorId);
            acctgTransactionEntry.setPatientId(patientId);
            acctgTransactionEntry.setSpecialityCode(specialityCode);
            return acctgTransactionEntry;
        }

        @Override
        public AcctgTransTypeEnum[] getChildTypes() {
            return new AcctgTransTypeEnum[0];
        }

        @Override
        public Class getClassName() {
            return null;
        }

        public String getPropery() {
            return null;
        }

        public String getReportField() {
            return null;
        }

        public DebitCreditEnum getDebitCreditType() {
            return DebitCreditEnum.CREDIT;
        }
    }, OPD_PAYMENT("OPD Payment") {
        @Override
        public AcctgTransactionEntry createAccountingTransactionEntry(InvoiceItem invoiceItem, int invoiceItemSequence, String doctorId, String patientId, String specialityCode, String acctgTransId) {
            AcctgTransactionEntry acctgTransactionEntry = AcctgTransactionEntry.createWithPaymentDetail("Patient_" + patientId, null,
                    BigDecimal.ZERO, DebitCreditEnum.CREDIT, invoiceItemSequence, this, this.getDescription(), acctgTransId);
            acctgTransactionEntry.setDoctorId(doctorId);
            acctgTransactionEntry.setPatientId(patientId);
            acctgTransactionEntry.setSpecialityCode(specialityCode);
            return acctgTransactionEntry;
        }

        @Override
        public AcctgTransTypeEnum[] getChildTypes() {
            return new AcctgTransTypeEnum[0];
        }

        @Override
        public Class getClassName() {
            return null;
        }

        public String getPropery() {
            return null;
        }

        public String getReportField() {
            return null;
        }

        public DebitCreditEnum getDebitCreditType() {
            return DebitCreditEnum.CREDIT;
        }
    }, CASUALTY("Casualty") {
        @Override
        public AcctgTransactionEntry createAccountingTransactionEntry(InvoiceItem invoiceItem, int invoiceItemSequence, String doctorId, String patientId, String specialityCode, String acctgTransId) {

            AcctgTransactionEntry acctgTransactionEntry = AcctgTransactionEntry.createWithCasualty(GlAccountEnum.Casualty.getDescription(), patientId,
                    invoiceItem.getPrice().getAmount(), DebitCreditEnum.CREDIT, invoiceItemSequence, this, this.getDescription(), acctgTransId);
            acctgTransactionEntry.setDoctorId(doctorId);
            acctgTransactionEntry.setPatientId(patientId);
            acctgTransactionEntry.setSpecialityCode(specialityCode);
            return acctgTransactionEntry;
        }

        @Override
        public AcctgTransTypeEnum[] getChildTypes() {
            return new AcctgTransTypeEnum[0];
        }

        @Override
        public Class getClassName() {
            return null;
        }

        public String getPropery() {
            return null;
        }

        public String getReportField() {
            return null;
        }

        public DebitCreditEnum getDebitCreditType() {
            return DebitCreditEnum.CREDIT;
        }
    }, CASUALTY_PAYMENT("Casualty Payment") {
        @Override
        public AcctgTransactionEntry createAccountingTransactionEntry(InvoiceItem invoiceItem, int invoiceItemSequence, String doctorId, String patientId, String specialityCode, String acctgTransId) {
            AcctgTransactionEntry acctgTransactionEntry = AcctgTransactionEntry.createWithPaymentDetail("Patient_" + patientId, null,
                    BigDecimal.ZERO, DebitCreditEnum.CREDIT, invoiceItemSequence, this, this.getDescription(), acctgTransId);
            acctgTransactionEntry.setDoctorId(doctorId);
            acctgTransactionEntry.setPatientId(patientId);
            acctgTransactionEntry.setSpecialityCode(specialityCode);
            return acctgTransactionEntry;
        }

        @Override
        public AcctgTransTypeEnum[] getChildTypes() {
            return new AcctgTransTypeEnum[0];
        }

        @Override
        public Class getClassName() {
            return null;
        }

        public String getPropery() {
            return null;
        }

        public String getReportField() {
            return null;
        }

        public DebitCreditEnum getDebitCreditType() {
            return DebitCreditEnum.CREDIT;
        }
    }, VAT("VAT") {
        @Override
        public AcctgTransactionEntry createAccountingTransactionEntry(InvoiceItem invoiceItem, int invoiceItemSequence, String doctorId, String patientId, String specialityCode, String acctgTransId) {
            AcctgTransactionEntry acctgTransactionEntry = AcctgTransactionEntry.createWithProcedure(GlAccountEnum.Consultation.getDescription(), invoiceItem.getItemId(),
                    invoiceItem.getPrice().getAmount(), DebitCreditEnum.CREDIT, invoiceItemSequence, this, invoiceItem.getDescription(), acctgTransId);
            acctgTransactionEntry.setDoctorId(doctorId);
            acctgTransactionEntry.setPatientId(patientId);
            acctgTransactionEntry.setSpecialityCode(specialityCode);
            return acctgTransactionEntry;
        }

        @Override
        public AcctgTransTypeEnum[] getChildTypes() {
            return new AcctgTransTypeEnum[0];
        }

        @Override
        public Class getClassName() {
            return null;
        }

        public String getPropery() {
            return null;
        }

        public String getReportField() {
            return null;
        }

        public DebitCreditEnum getDebitCreditType() {
            return DebitCreditEnum.CREDIT;
        }

    },  SERVICE_TAX("Service Tax") {
        @Override
        public AcctgTransactionEntry createAccountingTransactionEntry(InvoiceItem invoiceItem, int invoiceItemSequence, String doctorId, String patientId, String specialityCode, String acctgTransId) {
            AcctgTransactionEntry acctgTransactionEntry = AcctgTransactionEntry.createWithTax(GlAccountEnum.Tax.getDescription(), invoiceItem.getItemId(),
                    invoiceItem.getPrice().getAmount(), DebitCreditEnum.CREDIT, invoiceItemSequence, this, invoiceItem.getDescription(), acctgTransId);
            acctgTransactionEntry.setDoctorId(doctorId);
            acctgTransactionEntry.setPatientId(patientId);
            acctgTransactionEntry.setSpecialityCode(specialityCode);
            return acctgTransactionEntry;
        }

        @Override
        public AcctgTransTypeEnum[] getChildTypes() {
            return new AcctgTransTypeEnum[0];
        }

        @Override
        public Class getClassName() {
            return null;
        }

        public String getPropery() {
            return null;
        }

        public String getReportField() {
            return null;
        }

        public DebitCreditEnum getDebitCreditType() {
            return DebitCreditEnum.CREDIT;
        }
    }, OPD("OPD Charges") {
        @Override
        public AcctgTransactionEntry createAccountingTransactionEntry(InvoiceItem invoiceItem, int invoiceItemSequence, String doctorId, String patientId, String specialityCode, String acctgTransId) {
            return null;
        }

        @Override
        public AcctgTransTypeEnum[] getChildTypes() {
            return new AcctgTransTypeEnum[]{OPD_VISIT, OPD_PROCEDURE, OPD_LAB_CHARGES, OPD_CONSULTATION, OPD_PAYMENT};
        }

        @Override
        public Class getClassName() {
            return null;
        }

        public String getPropery() {
            return "labTestCode";
        }

        public String getReportField() {
            return "labTestPanel.labCategory.name";
        }

        public DebitCreditEnum getDebitCreditType() {
            return null;
        }
    }, CASUALTY_CHARGES("Casualty") {
        @Override
        public AcctgTransactionEntry createAccountingTransactionEntry(InvoiceItem invoiceItem, int invoiceItemSequence, String doctorId, String patientId, String specialityCode, String acctgTransId) {
            AcctgTransactionEntry acctgTransactionEntry = AcctgTransactionEntry.createWithAdmission("Patient_" + patientId, patientId,
                    BigDecimal.ZERO, DebitCreditEnum.DEBIT, invoiceItemSequence, this, this.getDescription(), acctgTransId);
            acctgTransactionEntry.setDoctorId(doctorId);
            acctgTransactionEntry.setPatientId(patientId);
            acctgTransactionEntry.setSpecialityCode(specialityCode);
            return acctgTransactionEntry;
        }

        @Override
        public AcctgTransTypeEnum[] getChildTypes() {
            return new AcctgTransTypeEnum[]{CASUALTY, CASUALTY_PAYMENT};
        }

        @Override
        public Class getClassName() {
            return null;
        }

        public String getPropery() {
            return null;
        }

        public String getReportField() {
            return null;
        }

        public DebitCreditEnum getDebitCreditType() {
            return DebitCreditEnum.DEBIT;
        }
    }, OPD_CASH("Cash") {
        @Override
        public AcctgTransactionEntry createAccountingTransactionEntry(InvoiceItem invoiceItem, int invoiceItemSequence, String doctorId, String patientId, String specialityCode, String acctgTransId) {
            return null;
        }

        @Override
        public AcctgTransTypeEnum[] getChildTypes() {
            return new AcctgTransTypeEnum[0];
        }

        @Override
        public Class getClassName() {
            return null;
        }

        public String getPropery() {
            return null;
        }

        public String getReportField() {
            return null;
        }

        public DebitCreditEnum getDebitCreditType() {
            return DebitCreditEnum.DEBIT;
        }
    }, OPD_DEBIT_CARD("Debit Card") {
        @Override
        public AcctgTransactionEntry createAccountingTransactionEntry(InvoiceItem invoiceItem, int invoiceItemSequence, String doctorId, String patientId, String specialityCode, String acctgTransId) {
            return null;
        }

        @Override
        public AcctgTransTypeEnum[] getChildTypes() {
            return new AcctgTransTypeEnum[0];
        }

        @Override
        public Class getClassName() {
            return null;
        }

        public String getPropery() {
            return null;
        }

        public String getReportField() {
            return null;
        }

        public DebitCreditEnum getDebitCreditType() {
            return DebitCreditEnum.DEBIT;
        }
    }, OPD_CREDIT_CARD("Credit Card") {
        @Override
        public AcctgTransactionEntry createAccountingTransactionEntry(InvoiceItem invoiceItem, int invoiceItemSequence, String doctorId, String patientId, String specialityCode, String acctgTransId) {
            return null;
        }

        @Override
        public AcctgTransTypeEnum[] getChildTypes() {
            return new AcctgTransTypeEnum[0];
        }

        @Override
        public Class getClassName() {
            return null;
        }

        public String getPropery() {
            return null;
        }

        public String getReportField() {
            return null;
        }

        public DebitCreditEnum getDebitCreditType() {
            return DebitCreditEnum.DEBIT;
        }
    }, OPD_PERSONAL_CHEQUE("Cheque") {
        @Override
        public AcctgTransactionEntry createAccountingTransactionEntry(InvoiceItem invoiceItem, int invoiceItemSequence, String doctorId, String patientId, String specialityCode, String acctgTransId) {
            return null;
        }

        @Override
        public AcctgTransTypeEnum[] getChildTypes() {
            return new AcctgTransTypeEnum[0];
        }

        @Override
        public Class getClassName() {
            return null;
        }

        public String getPropery() {
            return null;
        }

        public String getReportField() {
            return null;
        }

        public DebitCreditEnum getDebitCreditType() {
            return DebitCreditEnum.DEBIT;
        }
    }, OPD_INSURANCE_CARD("Insurance Card") {
        @Override
        public AcctgTransactionEntry createAccountingTransactionEntry(InvoiceItem invoiceItem, int invoiceItemSequence, String doctorId, String patientId, String specialityCode, String acctgTransId) {
            return null;
        }

        @Override
        public AcctgTransTypeEnum[] getChildTypes() {
            return new AcctgTransTypeEnum[0];
        }

        @Override
        public Class getClassName() {
            return null;
        }

        public String getPropery() {
            return null;
        }

        public String getReportField() {
            return null;
        }

        public DebitCreditEnum getDebitCreditType() {
            return DebitCreditEnum.DEBIT;
        }
    }, OPD_WRITE_OFF("Write Off") {
        @Override
        public AcctgTransactionEntry createAccountingTransactionEntry(InvoiceItem invoiceItem, int invoiceItemSequence, String doctorId, String patientId, String specialityCode, String acctgTransId) {
            return null;
        }

        @Override
        public AcctgTransTypeEnum[] getChildTypes() {
            return new AcctgTransTypeEnum[0];
        }

        @Override
        public Class getClassName() {
            return null;
        }

        public String getPropery() {
            return null;
        }

        public String getReportField() {
            return null;
        }

        public DebitCreditEnum getDebitCreditType() {
            return DebitCreditEnum.DEBIT;
        }
    }, ONLINE_PAYMENT("Online Payment") {
        @Override
        public AcctgTransactionEntry createAccountingTransactionEntry(InvoiceItem invoiceItem, int invoiceItemSequence, String doctorId, String patientId, String specialityCode, String acctgTransId) {
            return null;
        }

        @Override
        public AcctgTransTypeEnum[] getChildTypes() {
            return new AcctgTransTypeEnum[0];
        }

        @Override
        public Class getClassName() {
            return null;
        }

        public String getPropery() {
            return null;
        }

        public String getReportField() {
            return null;
        }

        public DebitCreditEnum getDebitCreditType() {
            return DebitCreditEnum.DEBIT;
        }
    };

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    AcctgTransTypeEnum(String description) {
        this.description = description;
    }

    public abstract AcctgTransactionEntry createAccountingTransactionEntry(InvoiceItem invoiceItem, int invoiceItemSequence, String doctorId, String patientId, String specialityCode, String acctgTransId);

    public abstract AcctgTransTypeEnum[] getChildTypes();

    public abstract Class getClassName();

    public abstract String getPropery();

    public abstract String getReportField();

    public abstract DebitCreditEnum getDebitCreditType();

}
