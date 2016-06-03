insert  into entity_sequences(VERSION,ENTITY_NAME,INCREMENT_BY,START_INDEX,DESCRIPTION) values (0,'com.nzion.domain.Patient',				1,0,'Patient Account #');
insert  into entity_sequences(VERSION,ENTITY_NAME,INCREMENT_BY,START_INDEX,DESCRIPTION) values (0,'com.nzion.domain.pms.InsuranceProvider',			1,0,'Insurance Code');
insert  into entity_sequences(VERSION,ENTITY_NAME,INCREMENT_BY,START_INDEX,DESCRIPTION) values (0,'com.nzion.domain.Provider',				1,0,'Provider Account #');
insert  into entity_sequences(VERSION,ENTITY_NAME,INCREMENT_BY,START_INDEX,DESCRIPTION) values (0,'com.nzion.domain.Location',				1,0,'Location Code');
insert  into entity_sequences(VERSION,ENTITY_NAME,INCREMENT_BY,START_INDEX,DESCRIPTION) values (0,'com.nzion.domain.Referral',				1,0,'Referral Code');
insert  into entity_sequences(VERSION,ENTITY_NAME,INCREMENT_BY,START_INDEX,DESCRIPTION) values (0,'com.nzion.domain.Employee',				1,0,'Employee Code');
insert  into entity_sequences(VERSION,ENTITY_NAME,INCREMENT_BY,START_INDEX,DESCRIPTION) values (0,'com.nzion.domain.emr.lab.LabTest',			1,0,'Lab Test Code');
insert  into entity_sequences(VERSION,ENTITY_NAME,INCREMENT_BY,START_INDEX,DESCRIPTION) values (0,'com.nzion.domain.emr.lab.LabTestPanel',			1,0,'Lab Test Panel Code');
-- insert  into entity_sequences(VERSION,ENTITY_NAME,INCREMENT_BY,START_INDEX,DESCRIPTION) values (0,'com.nzion.domain.inpatient.PatientAdmission',		1,0,'Inpatient Number');
insert  into entity_sequences(VERSION,ENTITY_NAME,INCREMENT_BY,START_INDEX,DESCRIPTION) values (0,"com.nzion.domain.billing.Invoice",1,0,'0PD Billing');
insert  into entity_sequences(VERSION,ENTITY_NAME,INCREMENT_BY,START_INDEX,DESCRIPTION) values (0,"com.nzion.domain.billing.Invoice",1,0,'IPD Billing');
insert  into entity_sequences(VERSION,ENTITY_NAME,INCREMENT_BY,START_INDEX,DESCRIPTION) values (0,"",1,0,'Pharmacy');
insert  into entity_sequences(VERSION,ENTITY_NAME,INCREMENT_BY,START_INDEX,DESCRIPTION) values (0,"com.nzion.domain.billing.InvoicePayment",1,0,'Invoice Payment');

insert  into naming_display_config(VERSION,POSITION1,POSITION2,POSITION3,POSITION4,POSITION5,POSITION6) values (0,'salutation','prefix','firstName','middleName','lastName','suffix');

insert  into field_restriction(VERSION,DISPLAY_NAME,ENTITY_NAME,FIELD,RESTRICTION_TYPE,SORT_ORDER,VALUE,DEACTIVATEDBY) values (1,NULL,'Patient','contacts.postalAddress.city','DEFAULT_VALUE',1,'',NULL);
insert  into field_restriction(VERSION,DISPLAY_NAME,ENTITY_NAME,FIELD,RESTRICTION_TYPE,SORT_ORDER,VALUE,DEACTIVATEDBY) values (1,NULL,'Patient','contacts.postalAddress.stateProvinceGeo','DEFAULT_VALUE',2,'',NULL);
insert  into field_restriction(VERSION,DISPLAY_NAME,ENTITY_NAME,FIELD,RESTRICTION_TYPE,SORT_ORDER,VALUE,DEACTIVATEDBY) values (1,NULL,'Patient','contacts.postalAddress.postalCode','DEFAULT_VALUE',3,'',NULL);
insert  into field_restriction(VERSION,DISPLAY_NAME,ENTITY_NAME,FIELD,RESTRICTION_TYPE,SORT_ORDER,VALUE,DEACTIVATEDBY) values (1,'Middle Name','Patient','middleName','MANDATORY',1,'false',NULL);
insert  into field_restriction(VERSION,DISPLAY_NAME,ENTITY_NAME,FIELD,RESTRICTION_TYPE,SORT_ORDER,VALUE,DEACTIVATEDBY) values (1,'Ethnicity','Patient','ethnicity','MANDATORY',2,'false',NULL);

insert  into `billing_display_config`(`ID`,`IS_ACTIVE`,`CREATED_BY`,`CREATE_TX_TIMESTAMP`,`DEACTIVATION_REASON`,`UPDATE_BY`,`UPDATED_TX_TIMESTAMP`,`VERSION`,`DECIMAL_POINTS`,`IN_VOICE_NUMBER`,`NO_SHOW_FEE`,`REGISTRATION_FEE`,`ROUNDINGMODE`,`DEACTIVATEDBY`,`CURRENCY_ID`,`IS_CONSULTATION_PRICE_TRIGGERED`,`IS_PROMPT_RECEPTIONIST_TO_COLLECT_CONSULTATION`) values (1,'','admin','2013-12-20 17:31:57',NULL,'admin','2013-12-26 18:00:23',1,2,NULL,NULL,'10.00','CEIL',NULL,39,'patientVisit','yes');















