package com.nzion.domain.billing;

public enum PaymentType {

     CASUALTY_CASH("Cash") {
        @Override
        public AcctgTransTypeEnum getAccountingTransactionType() {
            return AcctgTransTypeEnum.CASUALTY_PAYMENT;
        }

        @Override
        public String getPaymentMethodCode() {
            return "CASH";  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public AcctgTransactionEntry createAccountingTransactionEntry(InvoicePayment invoicePayment, int invoiceItemSequence, String doctorId, String patientId, String specialityCode, String acctgTransId) {
        	AcctgTransactionEntry acctgTransactionEntry = AcctgTransactionEntry.createWithPaymentDetail(this.getDescription(),invoicePayment.getId().toString(), 
          			 invoicePayment.getAmount().getAmount(), DebitCreditEnum.DEBIT, invoiceItemSequence,
          			 AcctgTransTypeEnum.CASUALTY_PAYMENT,AcctgTransTypeEnum.CASUALTY_PAYMENT.getDescription(), acctgTransId);
        	acctgTransactionEntry.setDoctorId(doctorId);
       	 acctgTransactionEntry.setPatientId(patientId);
       	 acctgTransactionEntry.setSpecialityCode(specialityCode);
          	  return acctgTransactionEntry;
        }
    }, CASUALTY_DEBIT_CARD("Debit Card") {
        @Override
        public AcctgTransTypeEnum getAccountingTransactionType() {
            return AcctgTransTypeEnum.CASUALTY_PAYMENT;
        }

        @Override
        public String getPaymentMethodCode() {
            return "DEBIT_CARD";  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public AcctgTransactionEntry createAccountingTransactionEntry(InvoicePayment invoicePayment, int invoiceItemSequence, String doctorId, String patientId, String specialityCode, String acctgTransId) {
        	AcctgTransactionEntry acctgTransactionEntry = AcctgTransactionEntry.createWithPaymentDetail(this.getDescription(),invoicePayment.getId().toString(), 
         			 invoicePayment.getAmount().getAmount(), DebitCreditEnum.DEBIT, invoiceItemSequence,
         			 AcctgTransTypeEnum.CASUALTY_PAYMENT,AcctgTransTypeEnum.CASUALTY_PAYMENT.getDescription(), acctgTransId);
        	acctgTransactionEntry.setDoctorId(doctorId);
       	 acctgTransactionEntry.setPatientId(patientId);
       	 acctgTransactionEntry.setSpecialityCode(specialityCode);
         	  return acctgTransactionEntry;
        }
    }, CASUALTY_CREDIT_CARD("Credit Card") {
        @Override
        public AcctgTransTypeEnum getAccountingTransactionType() {
            return AcctgTransTypeEnum.CASUALTY_PAYMENT;
        }

        @Override
        public String getPaymentMethodCode() {
            return "CREDIT_CARD";  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public AcctgTransactionEntry createAccountingTransactionEntry(InvoicePayment invoicePayment, int invoiceItemSequence, String doctorId, String patientId, String specialityCode, String acctgTransId) {
        	AcctgTransactionEntry acctgTransactionEntry = AcctgTransactionEntry.createWithPaymentDetail(this.getDescription(),invoicePayment.getId().toString(), 
         			 invoicePayment.getAmount().getAmount(), DebitCreditEnum.DEBIT, invoiceItemSequence,
         			 AcctgTransTypeEnum.CASUALTY_PAYMENT,AcctgTransTypeEnum.CASUALTY_PAYMENT.getDescription(), acctgTransId);
        	acctgTransactionEntry.setDoctorId(doctorId);
       	 acctgTransactionEntry.setPatientId(patientId);
       	 acctgTransactionEntry.setSpecialityCode(specialityCode);
         	  return acctgTransactionEntry;
        }
    }, CASUALTY_PERSONAL_CHEQUE("Cheque") {
        @Override
        public AcctgTransTypeEnum getAccountingTransactionType() {
            return AcctgTransTypeEnum.CASUALTY_PAYMENT;
        }

        @Override
        public String getPaymentMethodCode() {
            return "PERSONAL_CHEQUE";  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public AcctgTransactionEntry createAccountingTransactionEntry(InvoicePayment invoicePayment, int invoiceItemSequence, String doctorId, String patientId, String specialityCode, String acctgTransId) {
        	AcctgTransactionEntry acctgTransactionEntry = AcctgTransactionEntry.createWithPaymentDetail(this.getDescription(),invoicePayment.getId().toString(), 
         			 invoicePayment.getAmount().getAmount(), DebitCreditEnum.DEBIT, invoiceItemSequence,
         			 AcctgTransTypeEnum.CASUALTY_PAYMENT,AcctgTransTypeEnum.CASUALTY_PAYMENT.getDescription(), acctgTransId);
        	acctgTransactionEntry.setDoctorId(doctorId);
       	 acctgTransactionEntry.setPatientId(patientId);
       	 acctgTransactionEntry.setSpecialityCode(specialityCode);
         	  return acctgTransactionEntry;
        }
    }, CASUALTY_INSURANCE_CARD("Insurance Card") {
        @Override
        public AcctgTransTypeEnum getAccountingTransactionType() {
            return AcctgTransTypeEnum.CASUALTY_PAYMENT;
        }

        @Override
        public String getPaymentMethodCode() {
            return "INSURANCE_CARD";  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public AcctgTransactionEntry createAccountingTransactionEntry(InvoicePayment invoicePayment, int invoiceItemSequence, String doctorId, String patientId, String specialityCode, String acctgTransId) {
        	AcctgTransactionEntry acctgTransactionEntry = AcctgTransactionEntry.createWithPaymentDetail(this.getDescription(),invoicePayment.getId().toString(), 
        			 invoicePayment.getAmount().getAmount(), DebitCreditEnum.DEBIT, invoiceItemSequence,
        			 AcctgTransTypeEnum.CASUALTY_PAYMENT,AcctgTransTypeEnum.CASUALTY_PAYMENT.getDescription(), acctgTransId);
        	acctgTransactionEntry.setDoctorId(doctorId);
       	 acctgTransactionEntry.setPatientId(patientId);
       	 acctgTransactionEntry.setSpecialityCode(specialityCode);
        	  return acctgTransactionEntry;
        }
    }, CASUALTY_WRITE_OFF("Write Off") {
        @Override
        public AcctgTransTypeEnum getAccountingTransactionType() {
            return AcctgTransTypeEnum.CASUALTY_PAYMENT;
        }

        @Override
        public String getPaymentMethodCode() {
            return "WRITE_OFF";  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public AcctgTransactionEntry createAccountingTransactionEntry(InvoicePayment invoicePayment, int invoiceItemSequence, String doctorId, String patientId, String specialityCode, String acctgTransId) {
        	     	AcctgTransactionEntry acctgTransactionEntry = AcctgTransactionEntry.createWithWriteoffDetail(this.getDescription(),null,
        			 invoicePayment.getAmount().getAmount(), DebitCreditEnum.DEBIT, invoiceItemSequence,
        			 AcctgTransTypeEnum.CASUALTY_PAYMENT,AcctgTransTypeEnum.CASUALTY_PAYMENT.getDescription(), acctgTransId);
         	acctgTransactionEntry.setDoctorId(doctorId);
       	 acctgTransactionEntry.setPatientId(patientId);
       	 acctgTransactionEntry.setSpecialityCode(specialityCode);
        	  return acctgTransactionEntry;
        }
    }, OPD_CASH("Cash") {
        @Override
        public AcctgTransTypeEnum getAccountingTransactionType() {
            return AcctgTransTypeEnum.OPD_CASH;
        }

        @Override
        public String getPaymentMethodCode() {
            return "CASH";
        }

        @Override
        public AcctgTransactionEntry createAccountingTransactionEntry(InvoicePayment invoicePayment, int invoiceItemSequence, String doctorId, String patientId, String specialityCode, String acctgTransId) {
        	AcctgTransactionEntry acctgTransactionEntry = AcctgTransactionEntry.createWithPaymentDetail(this.getDescription(),invoicePayment.getId().toString(),
        			 invoicePayment.getAmount().getAmount(), DebitCreditEnum.DEBIT, invoiceItemSequence,
        			 AcctgTransTypeEnum.OPD_CASH,AcctgTransTypeEnum.OPD_CASH.getDescription(), acctgTransId);
        	acctgTransactionEntry.setDoctorId(doctorId);
       	 acctgTransactionEntry.setPatientId(patientId);
       	 acctgTransactionEntry.setSpecialityCode(specialityCode);
        	  return acctgTransactionEntry;
        }
    }, OPD_DEBIT_CARD("Debit Card") {
        @Override
        public AcctgTransTypeEnum getAccountingTransactionType() {
            return AcctgTransTypeEnum.OPD_DEBIT_CARD;
        }

        @Override
        public String getPaymentMethodCode() {
            return "DEBIT_CARD";
        }

        @Override
        public AcctgTransactionEntry createAccountingTransactionEntry(InvoicePayment invoicePayment, int invoiceItemSequence, String doctorId, String patientId, String specialityCode, String acctgTransId) {
        	AcctgTransactionEntry acctgTransactionEntry = AcctgTransactionEntry.createWithPaymentDetail(this.getDescription(),invoicePayment.getId().toString(), 
       			 invoicePayment.getAmount().getAmount(), DebitCreditEnum.DEBIT, invoiceItemSequence,
       			 AcctgTransTypeEnum.OPD_DEBIT_CARD,AcctgTransTypeEnum.OPD_DEBIT_CARD.getDescription(), acctgTransId);
        	acctgTransactionEntry.setDoctorId(doctorId);
       	 acctgTransactionEntry.setPatientId(patientId);
       	 acctgTransactionEntry.setSpecialityCode(specialityCode);
       	  return acctgTransactionEntry;
        }
    }, OPD_CREDIT_CARD("Credit Card") {
        @Override
        public AcctgTransTypeEnum getAccountingTransactionType() {
            return AcctgTransTypeEnum.OPD_CREDIT_CARD;
        }

        @Override
        public String getPaymentMethodCode() {
            return "CREDIT_CARD";
        }

        @Override
        public AcctgTransactionEntry createAccountingTransactionEntry(InvoicePayment invoicePayment, int invoiceItemSequence, String doctorId, String patientId, String specialityCode, String acctgTransId) {
        	AcctgTransactionEntry acctgTransactionEntry = AcctgTransactionEntry.createWithPaymentDetail(this.getDescription(),invoicePayment.getId().toString(), 
       			 invoicePayment.getAmount().getAmount(), DebitCreditEnum.DEBIT, invoiceItemSequence,
       			 AcctgTransTypeEnum.OPD_CREDIT_CARD,AcctgTransTypeEnum.OPD_CREDIT_CARD.getDescription(), acctgTransId);
        	acctgTransactionEntry.setDoctorId(doctorId);
       	 acctgTransactionEntry.setPatientId(patientId);
       	 acctgTransactionEntry.setSpecialityCode(specialityCode);
       	  return acctgTransactionEntry;
        }
    }, OPD_PERSONAL_CHEQUE("Cheque") {
        @Override
        public AcctgTransTypeEnum getAccountingTransactionType() {
            return AcctgTransTypeEnum.OPD_PERSONAL_CHEQUE;
        }

        @Override
        public String getPaymentMethodCode() {
            return "PERSONAL_CHEQUE";
        }

        @Override
        public AcctgTransactionEntry createAccountingTransactionEntry(InvoicePayment invoicePayment, int invoiceItemSequence, String doctorId, String patientId, String specialityCode, String acctgTransId) {
        	AcctgTransactionEntry acctgTransactionEntry = AcctgTransactionEntry.createWithPaymentDetail(this.getDescription(),invoicePayment.getId().toString(), 
       			 invoicePayment.getAmount().getAmount(), DebitCreditEnum.DEBIT, invoiceItemSequence,
       			 AcctgTransTypeEnum.OPD_PERSONAL_CHEQUE,AcctgTransTypeEnum.OPD_PERSONAL_CHEQUE.getDescription(), acctgTransId);
        	acctgTransactionEntry.setDoctorId(doctorId);
       	 acctgTransactionEntry.setPatientId(patientId);
       	 acctgTransactionEntry.setSpecialityCode(specialityCode);
       	  return acctgTransactionEntry;
        }
    }, OPD_INSURANCE_CARD("Insurance Card") {
        @Override
        public AcctgTransTypeEnum getAccountingTransactionType() {
            return AcctgTransTypeEnum.OPD_INSURANCE_CARD;
        }

        @Override
        public String getPaymentMethodCode() {
            return "INSURANCE_CARD";
        }

        @Override
        public AcctgTransactionEntry createAccountingTransactionEntry(InvoicePayment invoicePayment, int invoiceItemSequence, String doctorId, String patientId, String specialityCode, String acctgTransId) {
        	AcctgTransactionEntry acctgTransactionEntry = AcctgTransactionEntry.createWithPaymentDetail(this.getDescription(),invoicePayment.getId().toString(), 
       			 invoicePayment.getAmount().getAmount(), DebitCreditEnum.DEBIT, invoiceItemSequence,
       			 AcctgTransTypeEnum.OPD_INSURANCE_CARD,AcctgTransTypeEnum.OPD_INSURANCE_CARD.getDescription(), acctgTransId);
        	acctgTransactionEntry.setDoctorId(doctorId);
       	 acctgTransactionEntry.setPatientId(patientId);
       	 acctgTransactionEntry.setSpecialityCode(specialityCode);
       	  return acctgTransactionEntry;
        }
    }, OPD_WRITE_OFF("Write Off") {
        @Override
        public AcctgTransTypeEnum getAccountingTransactionType() {
            return AcctgTransTypeEnum.OPD_WRITE_OFF;
        }

        @Override
        public String getPaymentMethodCode() {
            return "WRITE_OFF";  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public AcctgTransactionEntry createAccountingTransactionEntry(InvoicePayment invoicePayment, int invoiceItemSequence, String doctorId, String patientId, String specialityCode, String acctgTransId) {
           	AcctgTransactionEntry acctgTransactionEntry = AcctgTransactionEntry.createWithWriteoffDetail(this.getDescription(),null,
       			 invoicePayment.getAmount().getAmount(), DebitCreditEnum.DEBIT, invoiceItemSequence,
       			 AcctgTransTypeEnum.OPD_WRITE_OFF,AcctgTransTypeEnum.OPD_WRITE_OFF.getDescription(), acctgTransId);
      	 acctgTransactionEntry.setDoctorId(doctorId);
       	 acctgTransactionEntry.setPatientId(patientId);
       	 acctgTransactionEntry.setSpecialityCode(specialityCode);
       	  return acctgTransactionEntry;
        }
    }, ONLINE_PAYMENT("Online Payment") {
        @Override
        public AcctgTransTypeEnum getAccountingTransactionType() {
            return AcctgTransTypeEnum.OPD_PERSONAL_CHEQUE;
        }

        @Override
        public String getPaymentMethodCode() {
            return "PERSONAL_CHEQUE";
        }

        @Override
        public AcctgTransactionEntry createAccountingTransactionEntry(InvoicePayment invoicePayment, int invoiceItemSequence, String doctorId, String patientId, String specialityCode, String acctgTransId) {
            AcctgTransactionEntry acctgTransactionEntry = AcctgTransactionEntry.createWithPaymentDetail(this.getDescription(), invoicePayment.getId().toString(),
                    invoicePayment.getAmount().getAmount(), DebitCreditEnum.DEBIT, invoiceItemSequence,
                    AcctgTransTypeEnum.OPD_PERSONAL_CHEQUE, AcctgTransTypeEnum.OPD_PERSONAL_CHEQUE.getDescription(), acctgTransId);
            acctgTransactionEntry.setDoctorId(doctorId);
            acctgTransactionEntry.setPatientId(patientId);
            acctgTransactionEntry.setSpecialityCode(specialityCode);
            return acctgTransactionEntry;
        }
    };

    private String description;

    PaymentType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public abstract AcctgTransTypeEnum getAccountingTransactionType();

    public abstract String getPaymentMethodCode();

    public abstract AcctgTransactionEntry createAccountingTransactionEntry(InvoicePayment invoicePayment, int invoiceItemSequence, String doctorId, String patientId, String specialityCode, String acctgTransId);
}
