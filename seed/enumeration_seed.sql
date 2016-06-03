df
FROM enumeration;
INSERT INTO enumeration (`ENUM_ID`,VERSION,DESCRIPTION,IS_ACTIVE,ENUM_CODE,ENUM_TYPE)VALUES (10001,1,'Both',TRUE,'BOTH','QUESTION_GENDER')
,(10002,1,'Female',TRUE,'FEMALE','QUESTION_GENDER')
,(10003,1,'Male',TRUE,'MALE','QUESTION_GENDER');

INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'YES',
        TRUE,
        'YES',
        'YES_NO');
INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'NO',
        TRUE,
        'NO',
        'YES_NO');
INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'Primary',
        TRUE,
        'PRIMARY',
        'CONTACT_MECHANISM');
INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'Postal Address',
        TRUE,
        'POSTAL ADDRESS',
        'CONTACT_MECH_TYPE');
INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'Mobile',
        TRUE,
        'MOBILE',
        'CONTACT_MECH_TYPE');
INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'Fax',
        TRUE,
        'FAX',
        'CONTACT_MECH_TYPE');
INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'Telephone',
        TRUE,
        'TELEPHONE',
        'CONTACT_MECH_TYPE');
INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'Page',
        TRUE,
        'PAGE',
        'CONTACT_MECH_TYPE');
INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'Email',
        TRUE,
        'EMAIL',
        'CONTACT_MECH_TYPE');
        
INSERT INTO enumeration(VERSION,DESCRIPTION,IS_ACTIVE,ENUM_CODE,ENUM_TYPE) VALUES (1,'Female',TRUE,'F','GENDER')
,(1,'Ambiguous',TRUE,'A','GENDER')
,(1,'Male',TRUE,'M','GENDER')
,(1,'Not applicable',TRUE,'N','GENDER')
,(1,'Other',TRUE,'O','GENDER')
,(1,'Unknown',TRUE,'U','GENDER');

INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'Complete',
        TRUE,
        'COMPLETE',
        'FIXED_ASSEST_STATUS');
INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'Incomplete',
        TRUE,
        'INCOMPLETE',
        'FIXED_ASSEST_STATUS');
INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'Service Date',
        TRUE,
        'SERVICE DATE',
        'AGEING_BASED_ON');
INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'Entry Date',
        TRUE,
        'ENTRY DATE',
        'AGEING_BASED_ON');
INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'Bill Date',
        TRUE,
        'BILL DATE',
        'AGEING_BASED_ON');
INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'Month To Month ',
        TRUE,
        'MONTH TO MONTH',
        'AGEING_FORMAT');
INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'Real Time',
        TRUE,
        'REAL TIME',
        'AGEING_FORMAT');
INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'HCFA 1500',
        TRUE,
        'HCFA 1500',
        'PRIMARY_BILLING_METHOD');
INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'Clearing house',
        TRUE,
        'CLEARING HOUSE',
        'PRIMARY_BILLING_METHOD');
INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'Not Doc',
        TRUE,
        'NOT DOC',
        'INSURANCE_PROVIDER_ELIGIBILITY_METHOD');
INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'Not Eligible',
        TRUE,
        'NOT ELLIGIBLE',
        'INSURANCE_PROVIDER_ELIGIBILITY_METHOD');
INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'MediCare',
        TRUE,
        'HFCA_PAYER_TYPE_MEDICARE',
        'HFCA_PAYER_TYPE');
INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'Commercial',
        TRUE,
        'COMMERCIAL',
        'HFCA_PAYER_TYPE');
INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'Cheque',
        TRUE,
        'CHEQUE',
        'INSURANCE_PROVIDER_PAYMENT');
INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'Direct Debit',
        TRUE,
        'DIRECT DEBIT',
        'INSURANCE_PROVIDER_PAYMENT');
INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'Insurance Adj',
        TRUE,
        'INSURANCE ADJ',
        'INSURANCE_PROVIDER_ADJUSTMENT');
INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'Standard',
        TRUE,
        'INSURANCE_FEE_TABLE_STANDARD',
        'INSURANCE_FEE_TABLE');
INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'Master Code',
        TRUE,
        'MasterCode',
        'DIAGNOSIS_ID');
INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'HMO',
        TRUE,
        'HMO',
        'INS_TYPE');
INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'Indemnity',
        TRUE,
        'INDEMINITY',
        'INS_TYPE');
INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'PPO',
        TRUE,
        'PPO',
        'INS_TYPE');



INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'Normal Display',
        TRUE,
        'NORMAL DISPLAY',
        'DEFAULT_SOAP_NOTE_FORMAT');
INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'Tabular Display',
        TRUE,
        'TABULAR DISPLAY',
        'DEFAULT_SOAP_NOTE_FORMAT');
INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'First Name',
        TRUE,
        'firstName',
        'NAME_COMPONENTS');
INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'Middle Name',
        TRUE,
        'middleName',
        'NAME_COMPONENTS');
INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'Last Name',
        TRUE,
        'lastName',
        'NAME_COMPONENTS');
INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'Provider',
        TRUE,
        'CHART_FOLDER_PROVIDER',
        'PROVIDER_CHART_FOLDER');
INSERT INTO enumeration
            (VERSION,
             CREATED_BY,
             CREATE_TX_TIMESTAMP,
             DESCRIPTION,
             IS_ACTIVE,
             ENUM_CODE,
             ENUM_TYPE)
VALUES (1,
        'ADMIN',
        NOW(),
        'Patient',
        TRUE,
        'PATIENT',
        'PATIENT_CHART_FOLDER');

        
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE) VALUES (1,'Office','Office','LOCATION_TYPE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE) VALUES (1,'Hospital','Hospital','LOCATION_TYPE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE) VALUES (1,'Nursing Home','Nursing Home','LOCATION_TYPE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE) VALUES (1,	'Pharmacy','Pharmacy','LOCATION_TYPE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE) VALUES (1,	'Laboratory','Laboratory','LOCATION_TYPE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE) VALUES (1,	'Others','Others','LOCATION_TYPE',TRUE);


INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Pharmacy','01','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'School','03','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Homeless Shelter','04','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Indian Health Service Free-standing Facility','05','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Tribal 638 Free-Standing Facility','07','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Tribal 638 Provider-Based Facility','08','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Office','11','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Home','12','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Assisted Living Facility','13','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Group Home','14','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Mobile Unit','15','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Urgent Care Facility','20','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Inpatient Hospital','21','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Outpatient Hospital','22','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Emergency Room - Hospital','23','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Ambulatory Surgical Center','24','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Birthing Center','25','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Military Treatment Facility','26','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Skilled Nursing Facility','31','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Nursing Facility','32','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Custodial Care Facility','33','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Hospice','34','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Residential Care Facility','35','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Ambulance - Land','41','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Ambulance - Air or Water','42','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Independent Clinic','49','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Federally Qualified Health Center','50','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Inpatient Psychiatric Facility','51','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Psych Facility Partial Hospitalization','52','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Community Mental Health Center','53','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Intermediate Care Fac/Mentally Retarded','54','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Residential Sub Abuse Treatment Center','55','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Psych Residential Treatment Center','56','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Non-residential Substance Abuse Treatment Facility','57','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Mass Immunization Center','60','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Comprehensive Inpatient Rehab','61','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Comprehensive Outpatient Rehab','62','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'End Stage Renal Disease Treatment','65','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'State or Local Public Health Clinic','71','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Rural Health Clinic','72','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Independent Laboratory','81','HCFA_PLACE_OF_SERVICE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Other Unlisted Facility','99','HCFA_PLACE_OF_SERVICE',TRUE);

INSERT INTO enumeration (VERSION,ENUM_CODE,DESCRIPTION,ENUM_TYPE,IS_ACTIVE)VALUES (0,'6','CHAMPUS REGION','HFCA_PAYER_TYPE',TRUE);
INSERT INTO enumeration (VERSION,ENUM_CODE,DESCRIPTION,ENUM_TYPE,IS_ACTIVE)VALUES (0,'A','SelfPay','HFCA_PAYER_TYPE',TRUE);
INSERT INTO enumeration (VERSION,ENUM_CODE,DESCRIPTION,ENUM_TYPE,IS_ACTIVE)VALUES (0,'B','Workers Compensation','HFCA_PAYER_TYPE',TRUE);
INSERT INTO enumeration (VERSION,ENUM_CODE,DESCRIPTION,ENUM_TYPE,IS_ACTIVE)VALUES (0,'C','Medicare','HFCA_PAYER_TYPE',TRUE);
INSERT INTO enumeration (VERSION,ENUM_CODE,DESCRIPTION,ENUM_TYPE,IS_ACTIVE)VALUES (0,'D','Medicaid','HFCA_PAYER_TYPE',TRUE);
INSERT INTO enumeration (VERSION,ENUM_CODE,DESCRIPTION,ENUM_TYPE,IS_ACTIVE)VALUES (0,'E','Other Federal Program','HFCA_PAYER_TYPE',TRUE);
INSERT INTO enumeration (VERSION,ENUM_CODE,DESCRIPTION,ENUM_TYPE,IS_ACTIVE)VALUES (0,'F','Commercial Insurance','HFCA_PAYER_TYPE',TRUE);
INSERT INTO enumeration (VERSION,ENUM_CODE,DESCRIPTION,ENUM_TYPE,IS_ACTIVE)VALUES (0,'G','Blue Cross/Blue Shield','HFCA_PAYER_TYPE',TRUE);
INSERT INTO enumeration (VERSION,ENUM_CODE,DESCRIPTION,ENUM_TYPE,IS_ACTIVE)VALUES (0,'H','CHAMPUS','HFCA_PAYER_TYPE',TRUE);
INSERT INTO enumeration (VERSION,ENUM_CODE,DESCRIPTION,ENUM_TYPE,IS_ACTIVE)VALUES (0,'I','HMO','HFCA_PAYER_TYPE',TRUE);
INSERT INTO enumeration (VERSION,ENUM_CODE,DESCRIPTION,ENUM_TYPE,IS_ACTIVE)VALUES (0,'J','Federal Employees Program','HFCA_PAYER_TYPE',TRUE);
INSERT INTO enumeration (VERSION,ENUM_CODE,DESCRIPTION,ENUM_TYPE,IS_ACTIVE)VALUES (0,'K','Central Certification','HFCA_PAYER_TYPE',TRUE);
INSERT INTO enumeration (VERSION,ENUM_CODE,DESCRIPTION,ENUM_TYPE,IS_ACTIVE)VALUES (0,'L','Self Administered','HFCA_PAYER_TYPE',TRUE);
INSERT INTO enumeration (VERSION,ENUM_CODE,DESCRIPTION,ENUM_TYPE,IS_ACTIVE)VALUES (0,'M','Nursing Facility','HFCA_PAYER_TYPE',TRUE);
INSERT INTO enumeration (VERSION,ENUM_CODE,DESCRIPTION,ENUM_TYPE,IS_ACTIVE)VALUES (0,'N','Managed Care - Non HMO','HFCA_PAYER_TYPE',TRUE);
INSERT INTO enumeration (VERSION,ENUM_CODE,DESCRIPTION,ENUM_TYPE,IS_ACTIVE)VALUES (0,'P','Blue Cross','HFCA_PAYER_TYPE',TRUE);
INSERT INTO enumeration (VERSION,ENUM_CODE,DESCRIPTION,ENUM_TYPE,IS_ACTIVE)VALUES (0,'T','Title V','HFCA_PAYER_TYPE',TRUE);
INSERT INTO enumeration (VERSION,ENUM_CODE,DESCRIPTION,ENUM_TYPE,IS_ACTIVE)VALUES (0,'V','Veterans Administration','HFCA_PAYER_TYPE',TRUE);
INSERT INTO enumeration (VERSION,ENUM_CODE,DESCRIPTION,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Z','Other','HFCA_PAYER_TYPE',TRUE);


INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'STANDARD','Standard','PATIENT_RESPONSIBLE_FEE_TABLE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Homeless Shelter','HOMELESS_SHELTER','SOCIO_ECONOMIC',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Transitional','TRANSITIONAL','SOCIO_ECONOMIC',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Doubling Up','DOUBLING_UP','SOCIO_ECONOMIC',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Street','STREET','SOCIO_ECONOMIC',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Other','OTHER','SOCIO_ECONOMIC',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Customer','CUSTOMER','BILL_TYPE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Patient','BILL_TYPE_PATIENT','BILL_TYPE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Medicare','BILL_TYPE_MEDICARE','BILL_TYPE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Medicaid','MEDICAID','BILL_TYPE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Insurance','INSURANCE','BILL_TYPE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'HMO Carrier Mode','HMO_CARRIER_MODE','BILL_TYPE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'PPO Carrier Mode','PPO_CARRIER_MODE','BILL_TYPE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Standard Statement','STANDARD_STAT','FORM_NAME',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Paper Forms','BILLING_METHOD_PAPER_FORMS','BILLING_METHOD',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Electronic','BILLING_METHOD_ELECTRONIC','BILLING_METHOD',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'E-mail','BILLING_METHOD_EMAIL','BILLING_METHOD',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Do not send','BILLING_METHOD_DNS','BILLING_METHOD',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Paper Forms','PATIENT_RECALL_METHOD_PAPER_FORMS','PATIENT_RECALL_METHOD',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Electronic','PATIENT_RECALL_METHOD_ELECTRONIC','PATIENT_RECALL_METHOD',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'E-mail','PATIENT_RECALL_METHOD_EMAIL','PATIENT_RECALL_METHOD',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Do not send','PATIENT_RECALL_METHOD_DNS','PATIENT_RECALL_METHOD',TRUE);



INSERT INTO enumeration
(VERSION,
CREATED_BY,
CREATE_TX_TIMESTAMP,
DESCRIPTION,
IS_ACTIVE,
ENUM_CODE,
ENUM_TYPE) VALUES (1,'SYSTEM',NOW(),'PROVIDER',TRUE,'PROVIDER','RESOURCE_TYPE');

INSERT INTO enumeration
(VERSION,
CREATED_BY,
CREATE_TX_TIMESTAMP,
DESCRIPTION,
IS_ACTIVE,
ENUM_CODE,
ENUM_TYPE) VALUES (1,'SYSTEM',NOW(),'RESOURCE',TRUE,'RESOURCE','RESOURCE_TYPE');

INSERT INTO enumeration
(VERSION,
CREATED_BY,
CREATE_TX_TIMESTAMP,
DESCRIPTION,
IS_ACTIVE,
ENUM_CODE,
ENUM_TYPE) VALUES (1,'SYSTEM',NOW(),'Normal',TRUE,'NORMAL','HPI');

INSERT INTO enumeration
(VERSION,
CREATED_BY,
CREATE_TX_TIMESTAMP,
DESCRIPTION,
IS_ACTIVE,
ENUM_CODE,
ENUM_TYPE) VALUES (1,'SYSTEM',NOW(),'Canned',TRUE,'CANNED','HPI');

INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Standard','STANDARD_NOTE_TYPE','NOTE_TYPE',TRUE);


INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Guarantor Relation','GUARANTOR_REL','RELATIONSHIP_TYPE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Provider Relation','PROVIDER_REL','RELATIONSHIP_TYPE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'School Relation','SCHOOL_REL','RELATIONSHIP_TYPE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Attorney Relation','ATTORNEY_REL','RELATIONSHIP_TYPE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'PCP Relation','PCP_REL','RELATIONSHIP_TYPE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Referral Relation','REFERRAL_REL','RELATIONSHIP_TYPE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'CaseManager Relation','CASEMANAGER_REL','RELATIONSHIP_TYPE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Adjuster Relation','ADJUSTER_REL','RELATIONSHIP_TYPE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Employer Relation','EMPLOYER_REL','RELATIONSHIP_TYPE',TRUE);


INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Subjective','SUBJECTIVE','SOAP',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Objective','OBJECTIVE','SOAP',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Assessment','ASSESEMENT','SOAP',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Plan','PLAN','SOAP',TRUE);

INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Question and Answer','QA','SOAPTYPE',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Text','TEXT','SOAPTYPE',TRUE);

INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Both','BOTH','MODIFIER_MGMT',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Allowed Only','ALLOWED_ONLY','MODIFIER_MGMT',TRUE);
INSERT INTO enumeration (VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE,IS_ACTIVE)VALUES (0,'Charge Only','CHARGE_ONLY','MODIFIER_MGMT',TRUE);

INSERT INTO enumeration (VERSION,DESCRIPTION,IS_ACTIVE,ENUM_CODE,ENUM_TYPE)VALUES (1,'Animal Allergy',TRUE,'AA','ALLERGY_TYPE')
,(1,'Environmental Allergy',TRUE,'EA','ALLERGY_TYPE')
,(1,'Drug Allergy',TRUE,'DA','ALLERGY_TYPE')
,(1,'Food Allergy',TRUE,'FA','ALLERGY_TYPE')
,(1,'Pollen Allergy',TRUE,'LA','ALLERGY_TYPE')
,(1,'Miscellaneous Allergy',TRUE,'MA','ALLERGY_TYPE')
,(1,'Miscellaneous contraindication',TRUE,'MC','ALLERGY_TYPE')
,(1,'Plant Allergy',TRUE,'PA','ALLERGY_TYPE'),(1,'Adverse reaction',TRUE,'ADVERSEREACTION','ALLERGY_TYPE');

INSERT INTO enumeration (VERSION,DESCRIPTION,IS_ACTIVE,ENUM_CODE,ENUM_TYPE,CODE_SYSTEM) VALUES
 (1,'Separated',TRUE,'Separated','MARITAL_STATUS','2.16.840.1.113883.5.2')
,(1,'Unmarried',TRUE,'Unmarried','MARITAL_STATUS','2.16.840.1.113883.5.2')
,(1,'Divorced',TRUE,'Divorced','MARITAL_STATUS','2.16.840.1.113883.5.2')
,(1,'Legally Separated',TRUE,'Legally Separated','MARITAL_STATUS','2.16.840.1.113883.5.2')
,(1,'Living together',TRUE,'Living Togather','MARITAL_STATUS','2.16.840.1.113883.5.2')
,(1,'Married',TRUE,'Married','MARITAL_STATUS','2.16.840.1.113883.5.2')
,(1,'Annulled',TRUE,'Annulled','MARITAL_STATUS','2.16.840.1.113883.5.2')
,(1,'Other',TRUE,'Other-Martital','MARITAL_STATUS','2.16.840.1.113883.5.2')
,(1,'Domestic partner',TRUE,'Domestic partner','MARITAL_STATUS','2.16.840.1.113883.5.2')
,(1,'Single',TRUE,'Single','MARITAL_STATUS','2.16.840.1.113883.5.2')
,(1,'Unreported',TRUE,'Unreported','MARITAL_STATUS','2.16.840.1.113883.5.2')
,(1,'Unknown',TRUE,'Unknown-Marital','MARITAL_STATUS','2.16.840.1.113883.5.2')
,(1,'Widowed',TRUE,'Widowed','MARITAL_STATUS','2.16.840.1.113883.5.2');
	
/*
insert into `enumeration`(`ENUM_ID`,`IS_ACTIVE`,`VERSION`,`CODE_SYSTEM`,`CODE_SYSTEM_NAME`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (349,1,1,'2.16.840.1.113883.6.238',null,'Asian','2028-9','RACE');
insert into `enumeration`(`ENUM_ID`,`IS_ACTIVE`,`VERSION`,`CODE_SYSTEM`,`CODE_SYSTEM_NAME`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (350,1,1,'2.16.840.1.113883.6.238',null,'Black or African American','2054-5','RACE');
insert into `enumeration`(`ENUM_ID`,`IS_ACTIVE`,`VERSION`,`CODE_SYSTEM`,`CODE_SYSTEM_NAME`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (351,1,1,'2.16.840.1.113883.6.238',null,'Native Hawaiian or Other Pacific Islander','2076-8','RACE');
insert into `enumeration`(`ENUM_ID`,`IS_ACTIVE`,`VERSION`,`CODE_SYSTEM`,`CODE_SYSTEM_NAME`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (352,1,1,'2.16.840.1.113883.6.238',null,'White','2106-3','RACE);
insert into `enumeration`(`ENUM_ID`,`IS_ACTIVE`,`VERSION`,`CODE_SYSTEM`,`CODE_SYSTEM_NAME`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (353,1,1,'2.16.840.1.113883.6.238',null,'Other Race','2131-1','RACE');
insert into `enumeration`(`ENUM_ID`,`IS_ACTIVE`,`VERSION`,`CODE_SYSTEM`,`CODE_SYSTEM_NAME`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (354,1,1,'2.16.840.1.113883.6.238',null,'Asian or Pacific Islander','1234-5','RACE');
insert into `enumeration`(`ENUM_ID`,`IS_ACTIVE`,`VERSION`,`CODE_SYSTEM`,`CODE_SYSTEM_NAME`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (40,1,1,'2.16.840.1.113883.6.238',null,'Unreported','UNREPORTED','RACE');
*/


INSERT INTO enumeration (VERSION,DESCRIPTION,IS_ACTIVE,ENUM_CODE,ENUM_TYPE)VALUES (1,'Christian:American Baptist Church',TRUE,'ABC','RELIGION')
,(1,'Agnostic',TRUE,'AGN','RELIGION')
,(1,'Christian: African Methodist Episcopal Zion',TRUE,'AME','RELIGION')
,(1,'Christian: African Methodist Episcopal',TRUE,'AMT','RELIGION')
,(1,'Christian: Anglican',TRUE,'ANG','RELIGION')
,(1,'Christian: Assembly of God',TRUE,'AOG','RELIGION')
,(1,'Atheist',TRUE,'ATH','RELIGION')
,(1,"Baha'i",TRUE,'BAH','RELIGION')
,(1,'Christian: Baptist',TRUE,'BAP','RELIGION')
,(1,'Buddhist: Mahayana',TRUE,'BMA','RELIGION')
,(1,'Buddhist: Other',TRUE,'BOT','RELIGION')
,(1,'Buddhist: Tantrayana',TRUE,'BTA','RELIGION')
,(1,'Buddhist: Theravada',TRUE,' BTH','RELIGION')
,(1,'Buddhist',TRUE,'BUD','RELIGION')
,(1,'Christian: Roman Catholic',TRUE,'CAT','RELIGION')
,(1,'Chinese Folk Religionist',TRUE,'CFR','RELIGION')
,(1,'Christian',TRUE,'CHR','RELIGION')
,(1,'Christian: Christian Science',TRUE,'CHS','RELIGION')
,(1,'Christian: Christian Missionary Alliance',TRUE,'CMA','RELIGION')
,(1,'Confucian',TRUE,'CNF','RELIGION')
,(1,'Christian: Church of Christ',TRUE,'COC','RELIGION')
,(1,'Christian: Church of God',TRUE,'COG','RELIGION')
,(1,'Christian: Church of God in Christ',TRUE,'COI','RELIGION')
,(1,'Christian: Congregational',TRUE,'COL','RELIGION')
,(1,'Christian: Community',TRUE,'COM','RELIGION')
,(1,'Christian: Other Pentecostal',TRUE,'COP','RELIGION')
,(1,'Christian: Other',TRUE,'COT','RELIGION')
,(1,'Christian: Christian Reformed',TRUE,'CRR','RELIGION')
,(1,'Christian: Eastern Orthodox',TRUE,'EOT','RELIGION')
,(1,'Christian: Episcopalian',TRUE,'EPI','RELIGION')
,(1,'Ethnic Religionist',TRUE,'ERL','RELIGION')
,(1,'Christian: Evangelical Church',TRUE,'EVC','RELIGION')
,(1,'Christian: Friends',TRUE,'FRQ','RELIGION')
,(1,'Christian: Free Will Baptist',TRUE,'FWB','RELIGION')
,(1,'Christian: Greek Orthodox',TRUE,'GRE','RELIGION')
,(1,'Hindu',TRUE,'HIN','RELIGION')
,(1,'Hindu: Other',TRUE,'HOT','RELIGION')
,(1,'Hindu: Shaivites',TRUE,'HSH','RELIGION')
,(1,'Hindu: Vaishnavites',TRUE,'HVA','RELIGION')
,(1,'Jain',TRUE,'JAI','RELIGION')
,(1,'Jewish: Conservative',TRUE,'JCO','RELIGION')
,(1,'Jewish',TRUE,'JEW','RELIGION')
,(1,'Jewish: Orthodox',TRUE,'JOR','RELIGION')
,(1,'Jewish: Other',TRUE,'JOT','RELIGION')
,(1,'Jewish: Reconstructionist',TRUE,'JRC','RELIGION')
,(1,'Jewish: Reform',TRUE,'JRF','RELIGION')
,(1,'Jewish: Renewal',TRUE,'JRN','RELIGION')
,(1,"Christian: Jehovah's Witness",TRUE,'JWN','RELIGION')
,(1,'Christian: Lutheran Missouri Synod',TRUE,'LMS','RELIGION')
,(1,'Christian: Lutheran',TRUE,'LUT','RELIGION')
,(1,'Christian: Mennonite',TRUE,'MEN','RELIGION')
,(1,'Christian: Methodist',TRUE,'MET','RELIGION')
,(1,'Christian: Latter-day Saints',TRUE,'MOM','RELIGION')
,(1,'Muslim',TRUE,'MOS','RELIGION')
,(1,'Muslim: Other',TRUE,'MOT','RELIGION')
,(1,'Muslim: Shiite',TRUE,'MSH','RELIGION')
,(1,'Muslim: Sunni',TRUE,'MSU','RELIGION')
,(1,'Native American',TRUE,'NAM','RELIGION')
,(1,'Christian: Church of the Nazarene',TRUE,'NAZ','RELIGION')
,(1,'Nonreligious',TRUE,'NOE','RELIGION')
,(1,'New Religionist',TRUE,'NRL','RELIGION')
,(1,'Christian: Orthodox',TRUE,'ORT','RELIGION')
,(1,'Christian: Pentecostal',TRUE,'OTH','RELIGION')
,(1,'Christian: Other Protestant',TRUE,'PRC','RELIGION')
,(1,'Christian: Presbyterian',TRUE,'PRE','RELIGION')
,(1,'Christian: Protestant',TRUE,'PRO','RELIGION')
,(1,'Christian: Friends',TRUE,'QUA','RELIGION')
,(1,'Christian: Reformed Church',TRUE,'REC','RELIGION')
,(1,'Christian: Reorganized Church of Jesus Christ-LDS',TRUE,'REO','RELIGION')
,(1,'Christian: Salvation Army',TRUE,'SAA','RELIGION')
,(1,'Christian: Seventh Day Adventist',TRUE,'SEV','RELIGION')
,(1,'Shintoist',TRUE,'SHN','RELIGION')
,(1,'Christian: Southern Baptist',TRUE,'SOU','RELIGION')
,(1,'Spiritist',TRUE,'SPI','RELIGION')
,(1,'Christian: United Church of Christ',TRUE,'UCC','RELIGION')
,(1,'Christian: United Methodist',TRUE,'UMD','RELIGION')
,(1,'Christian: Unitarian',TRUE,'UNI','RELIGION')
,(1,'Christian: Unitarian Universalist',TRUE,'UNU','RELIGION')
,(1,'VAR',TRUE,'Unknown','RELIGION')
,(1,'Christian: Wesleyan',TRUE,'WES','RELIGION')
,(1,'Christian: Wesleyan Methodist',TRUE,'WMC','RELIGION');

INSERT INTO enumeration (VERSION,DESCRIPTION,IS_ACTIVE,ENUM_CODE,ENUM_TYPE)VALUES (1,'As soon as possible (a priority lower than stat)',TRUE,'A','LAB_ORDER_PRIORITY')
,(1,'Preoperative (to be done prior to surgery)',TRUE,'P','LAB_ORDER_PRIORITY')
,(1,'Routine',TRUE,'R','LAB_ORDER_PRIORITY')
,(1,'Stat (do immediately)',TRUE,'S','LAB_ORDER_PRIORITY')
,(1,'Timing critical (do as near as possible to requested time)',TRUE,'T','LAB_ORDER_PRIORITY');

INSERT INTO enumeration (VERSION,DESCRIPTION,IS_ACTIVE,ENUM_CODE,ENUM_TYPE)VALUES (1,'Full time employed',TRUE,'1','EMPLOYMENT_STATUS')
,(1,'Part time employed',TRUE,'2E','EMPLOYMENT_STATUS')
,(1,'Unemployed',TRUE,'3E','EMPLOYMENT_STATUS')
,(1,'Self-employed',TRUE,'4E','EMPLOYMENT_STATUS')
,(1,'Retired',TRUE,'5E','EMPLOYMENT_STATUS')
,(1,'On active military duty',TRUE,'6E','EMPLOYMENT_STATUS')
,(1,'Unknown',TRUE,'9E','EMPLOYMENT_STATUS')
,(1,'Contract, per diem',TRUE,'CE','EMPLOYMENT_STATUS')
,(1,'Per Diem',TRUE,'DE','EMPLOYMENT_STATUS')
,(1,'Full Time',TRUE,'FE','EMPLOYMENT_STATUS')
,(1,'Leave of absence (e.g. Family leave, sabbatical, etc.)',TRUE,'LE','EMPLOYMENT_STATUS')
,(1,'Other',TRUE,'OE','EMPLOYMENT_STATUS')
,(1,'Part Time',TRUE,'PE','EMPLOYMENT_STATUS')
,(1,'Temporarily unemployed',TRUE,'TE','EMPLOYMENT_STATUS');

INSERT INTO enumeration (VERSION,DESCRIPTION,IS_ACTIVE,ENUM_CODE,ENUM_TYPE)VALUES (1,'Full-time student',TRUE,'F','STUDENT_STATUS')
,(1,'Not a student',TRUE,'N','STUDENT_STATUS')
,(1,'Part-time student',TRUE,'P','STUDENT_STATUS')
,(1,'Full-time student',TRUE,'F','STUDENT_STATUS');




INSERT INTO enumeration (VERSION,DESCRIPTION,IS_ACTIVE,ENUM_CODE,ENUM_TYPE)VALUES (1,'Patient is insured',TRUE,'01','POLICY_RELATIONSHIP')
,(1,'Spouse',TRUE,'02','POLICY_RELATIONSHIP')
,(1,'Natural child/insured financial responsibility',TRUE,'03','POLICY_RELATIONSHIP')
,(1,'Natural child/Insured does not have financial responsibility',TRUE,'04','POLICY_RELATIONSHIP')
,(1,'Step child',TRUE,'05','POLICY_RELATIONSHIP')
,(1,'Foster child',TRUE,'06','POLICY_RELATIONSHIP')
,(1,'Ward of the court',TRUE,'07','POLICY_RELATIONSHIP')
,(1,'Employee',TRUE,'08','POLICY_RELATIONSHIP')
,(1,'Unknown',TRUE,'09','POLICY_RELATIONSHIP')
,(1,'Handicapped dependent',TRUE,'10','POLICY_RELATIONSHIP')
,(1,'Organ donor',TRUE,'11','POLICY_RELATIONSHIP')
,(1,'Cadaver donor',TRUE,'12','POLICY_RELATIONSHIP')
,(1,'Grandchild',TRUE,'13','POLICY_RELATIONSHIP')
,(1,'Niece/nephew',TRUE,'14','POLICY_RELATIONSHIP')
,(1,'Injured plaintiff',TRUE,'15','POLICY_RELATIONSHIP')
,(1,'Sponsored dependent',TRUE,'16','POLICY_RELATIONSHIP')
,(1,'Minor dependent of a minor dependent',TRUE,'17','POLICY_RELATIONSHIP')
,(1,'Parent',TRUE,'18','POLICY_RELATIONSHIP')
,(1,'Grandparent',TRUE,'19','POLICY_RELATIONSHIP');


INSERT INTO enumeration(`ENUM_ID`,`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) VALUES (483,1,2,'Below Normal','L','LAB_RESULT_STATUS')
, (484,1,2,'Above High','H','LAB_RESULT_STATUS') ,(485,1,0,'Normal','N','LAB_RESULT_STATUS');

insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Andhra Pradesh','AP','STATE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Assam','ASM','STATE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Bihar','BH','STATE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Delhi','DH','STATE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Gujrat','GJ','STATE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Haryana','HY','STATE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Karnataka','KA','STATE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Kerala','KL','STATE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Madhya Pradesh','MP','STATE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Maharashtra','MH','STATE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Orissa','ORS','STATE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Punjab','PU','STATE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Tamil Nadu','TN','STATE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Uttar Pradesh','UP','STATE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Uttaranchal','UT','STATE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'West Bengal','WB','STATE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Goa','Goa','STATE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Himachal Pradesh','HP','STATE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Jammu & Kashmir','JK','STATE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Andaman & Nicobar','AN','STATE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Arunachal Pradesh','ARN','STATE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Chandigarh','CDG','STATE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Chattisgarh','CSG','STATE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Dadra & Nagar','DN','STATE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Daman & Diu','DD','STATE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Lakshdweep','LKS','STATE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Jharkhand','JK','STATE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Manipur','MN','STATE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Pondichery','PY','STATE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Meghalaya','MG','STATE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Sikkim','SK','STATE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Tripura','TP','STATE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Rajasthan','RJ','STATE');
commit;



insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'English','English','LANGUAGE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Hindi','Hindi','LANGUAGE');

insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Bengali','Bengali','LANGUAGE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Telugu','Telugu','LANGUAGE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Marathi','Marathi','LANGUAGE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Tamil','Tamil','LANGUAGE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Urdu','Urdu','LANGUAGE');

insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Gujarati','Gujarati','LANGUAGE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Kannada,'Kannada','LANGUAGE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Malayalam','Malayalam','LANGUAGE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Odia','Odia','LANGUAGE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Punjabi','Punjabi','LANGUAGE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Kashmiri','Kashmiri','LANGUAGE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Nepali','Nepali','LANGUAGE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Konkani','Konkani','LANGUAGE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Maithili','Maithili','LANGUAGE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Gondi','Gondi','LANGUAGE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Bhili','Bhili','LANGUAGE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Santali,'Santali','LANGUAGE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Sindhi','Sindhi','LANGUAGE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Dogri','Dogri','LANGUAGE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Khasi','Khasi','LANGUAGE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Ho','Ho','LANGUAGE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Mundari','Mundari','LANGUAGE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Kurush','Kurush','LANGUAGE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Tulu','Tulu','LANGUAGE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Assamese','Assamese','LANGUAGE');
insert  into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) values (TRUE,1,'Others','Others','LANGUAGE');

insert into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`,`CODE_SYSTEM`,`CODE_SYSTEM_NAME`) values (1,0,'Active','55561003','PROBLEM_STATUS_CODE','2.16.840.1.113883.6.96','SNOMED CT');
insert into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`,`CODE_SYSTEM`,`CODE_SYSTEM_NAME`) values (1,0,'Chronic','90734009','PROBLEM_STATUS_CODE','2.16.840.1.113883.6.96','SNOMED CT');
insert into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`,`CODE_SYSTEM`,`CODE_SYSTEM_NAME`) values (1,0,'Intermittent','7087005','PROBLEM_STATUS_CODE','2.16.840.1.113883.6.96','SNOMED CT');
insert into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`,`CODE_SYSTEM`,`CODE_SYSTEM_NAME`) values (1,0,'Recurrent','255227004','PROBLEM_STATUS_CODE','2.16.840.1.113883.6.96','SNOMED CT');
insert into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`,`CODE_SYSTEM`,`CODE_SYSTEM_NAME`) values (1,0,'Inactive','73425007','PROBLEM_STATUS_CODE','2.16.840.1.113883.6.96','SNOMED CT');
insert into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`,`CODE_SYSTEM`,`CODE_SYSTEM_NAME`) values (1,2,'Rule Out','415684004','PROBLEM_STATUS_CODE','2.16.840.1.113883.6.96','SNOMED CT');
insert into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`,`CODE_SYSTEM`,`CODE_SYSTEM_NAME`) values (1,2,'Ruled out','410516002','PROBLEM_STATUS_CODE','2.16.840.1.113883.6.96','SNOMED CT');
insert into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`,`CODE_SYSTEM`,`CODE_SYSTEM_NAME`) values (1,0,'Resolved','413322009','PROBLEM_STATUS_CODE','2.16.840.1.113883.6.96','SNOMED CT');
insert into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`,`CODE_SYSTEM`,`CODE_SYSTEM_NAME`) values (1,2,'Alive and well','81323004','PROBLEM_HEALTH_STATUS_CODE','2.16.840.1.113883.6.96','SNOMED CT');
insert into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`,`CODE_SYSTEM`,`CODE_SYSTEM_NAME`) values (1,2,'In remission','313386006','PROBLEM_HEALTH_STATUS_CODE','2.16.840.1.113883.6.96','SNOMED CT');
insert into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`,`CODE_SYSTEM`,`CODE_SYSTEM_NAME`) values (1,2,'Symptom free','162467007','PROBLEM_HEALTH_STATUS_CODE','2.16.840.1.113883.6.96','SNOMED CT');
insert into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`,`CODE_SYSTEM`,`CODE_SYSTEM_NAME`) values (1,2,'Chronically ill','161901003','PROBLEM_HEALTH_STATUS_CODE','2.16.840.1.113883.6.96','SNOMED CT');
insert into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`,`CODE_SYSTEM`,`CODE_SYSTEM_NAME`) values (1,2,'Severely ill','271593001','PROBLEM_HEALTH_STATUS_CODE','2.16.840.1.113883.6.96','SNOMED CT');
insert into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`,`CODE_SYSTEM`,`CODE_SYSTEM_NAME`) values (1,2,'Disabled','21134002','PROBLEM_HEALTH_STATUS_CODE','2.16.840.1.113883.6.96','SNOMED CT');
insert into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`,`CODE_SYSTEM`,`CODE_SYSTEM_NAME`) values (1,2,'Severely disabled','161045001','PROBLEM_HEALTH_STATUS_CODE','2.16.840.1.113883.6.96','SNOMED CT');
insert into `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`,`CODE_SYSTEM`,`CODE_SYSTEM_NAME`) values (1,2,'Deceased','419099009','PROBLEM_HEALTH_STATUS_CODE','2.16.840.1.113883.6.96','SNOMED CT');


INSERT  INTO `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) VALUES (TRUE,1,'General Ward','GW','WARD_TYPE');
INSERT  INTO `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) VALUES (TRUE,1,'Special Ward','SW','WARD_TYPE');
INSERT  INTO `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) VALUES (TRUE,1,'ICU Ward','IW','WARD_TYPE');
COMMIT;

INSERT  INTO `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) VALUES (TRUE,1,'MalNutrition','MN','NUTRITION_TYPE');
INSERT  INTO `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) VALUES (TRUE,1,'Nouished','NR','NUTRITION_TYPE');
COMMIT;

INSERT  INTO `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) VALUES (TRUE,1,'Thin','TH','BUILD_TYPE');
INSERT  INTO `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) VALUES (TRUE,1,'Average','AV','BUILD_TYPE');
INSERT  INTO `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) VALUES (TRUE,1,'Obese','OB','BUILD_TYPE');
COMMIT;

INSERT  INTO `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) VALUES (TRUE,1,'AC ROOM','ACR','ROOM_TYPE');
INSERT  INTO `enumeration`(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) VALUES (TRUE,1,'NON AC ROOM','NACR','ROOM_TYPE');
COMMIT;

INSERT INTO enumeration (CREATED_BY, VERSION, DESCRIPTION, ENUM_CODE, ENUM_TYPE) VALUES ( 'SEED',  1, 'Wealth tax', 'WEALTH_TAX', 'TAX_TYPE');
INSERT INTO enumeration (CREATED_BY, VERSION, DESCRIPTION, ENUM_CODE, ENUM_TYPE) VALUES ( 'SEED',  1, 'Corporate Income Tax', 'CORPORATE_INCOME_TAX' ,'TAX_TYPE');
INSERT INTO enumeration (CREATED_BY, VERSION, DESCRIPTION, ENUM_CODE, ENUM_TYPE) VALUES ( 'SEED',  1, 'Personal Income Tax', 'PERSONAL_INCOME_TAX', 'TAX_TYPE');
INSERT INTO enumeration (CREATED_BY, VERSION, DESCRIPTION, ENUM_CODE, ENUM_TYPE) VALUES ( 'SEED',  1, 'Sales Tax', 'SALES_TAX', 'TAX_TYPE');
INSERT INTO enumeration (CREATED_BY, VERSION, DESCRIPTION, ENUM_CODE, ENUM_TYPE) VALUES ( 'SEED',  1, 'Custom Duties', 'CUSTOM_DUTIES', 'TAX_TYPE');
INSERT INTO enumeration (CREATED_BY, VERSION, DESCRIPTION, ENUM_CODE, ENUM_TYPE) VALUES ( 'SEED',  1, 'Edu Cess', 'EDU_CESS', 'TAX_TYPE');
INSERT INTO enumeration (CREATED_BY, VERSION, DESCRIPTION, ENUM_CODE, ENUM_TYPE) VALUES ( 'SEED',  1, 'S. H. Edu Cess', 'SHEDU_CESS', 'TAX_TYPE');
INSERT INTO enumeration (CREATED_BY, VERSION, DESCRIPTION, ENUM_CODE, ENUM_TYPE) VALUES ( 'SEED',  1, 'Service Tax', 'SERVICE_TAX', 'TAX_TYPE');
COMMIT;

INSERT INTO enumeration (CREATED_BY, VERSION, DESCRIPTION, ENUM_CODE, ENUM_TYPE) VALUES ( 'SEED',  1, 'Cash', 'CASH', 'PAYMENT_MODE');
INSERT INTO enumeration (CREATED_BY, VERSION, DESCRIPTION, ENUM_CODE, ENUM_TYPE) VALUES ( 'SEED',  1, 'Debit Card', 'DEBIT_CARD', 'PAYMENT_MODE');
INSERT INTO enumeration (CREATED_BY, VERSION, DESCRIPTION, ENUM_CODE, ENUM_TYPE) VALUES ( 'SEED',  1, 'Credit Card', 'CREDIT_CARD', 'PAYMENT_MODE');
INSERT INTO enumeration (CREATED_BY, VERSION, DESCRIPTION, ENUM_CODE, ENUM_TYPE) VALUES ( 'SEED',  1, 'Cheque', 'PERSONAL_CHEQUE', 'PAYMENT_MODE');
INSERT INTO enumeration (CREATED_BY, VERSION, DESCRIPTION, ENUM_CODE, ENUM_TYPE) VALUES ( 'SEED',  1, 'Insurance', 'INSURANCE_CARD', 'PAYMENT_MODE');
COMMIT;

INSERT INTO enumeration (CREATED_BY, VERSION, DESCRIPTION, ENUM_CODE, ENUM_TYPE) VALUES ( 'SEED',  1, 'Cash', 'CASH_INSU', 'PAYMENT_MODE_WITHOUT_INSURANCE');
INSERT INTO enumeration (CREATED_BY, VERSION, DESCRIPTION, ENUM_CODE, ENUM_TYPE) VALUES ( 'SEED',  1, 'Debit Card', 'DEBIT_INSU', 'PAYMENT_MODE_WITHOUT_INSURANCE');
INSERT INTO enumeration (CREATED_BY, VERSION, DESCRIPTION, ENUM_CODE, ENUM_TYPE) VALUES ( 'SEED',  1, 'Credit Card', 'CREDIT_INSU', 'PAYMENT_MODE_WITHOUT_INSURANCE');
INSERT INTO enumeration (CREATED_BY, VERSION, DESCRIPTION, ENUM_CODE, ENUM_TYPE) VALUES ( 'SEED',  1, 'Cheque', 'PERSONAL_CHEQUE_INSU', 'PAYMENT_MODE_WITHOUT_INSURANCE');
COMMIT;


INSERT  INTO `enumeration`
(`IS_ACTIVE`,`VERSION`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`) VALUES
(TRUE,1,'DOCTOR','DOCTOR','OCCUPATION'),
(TRUE,1,'ENGINEERING','ENGINEERING','OCCUPATION'),
(TRUE,1,'LAW','LAW','OCCUPATION'),
(TRUE,1,'TEACHING','TEACHING','OCCUPATION'),
(TRUE,1,'ACCOUNTANT','ACCOUNTANT','OCCUPATION'),
(TRUE,1,'ARCHITECT','ARCHITECT','OCCUPATION'),
(TRUE,1,'NURSING','NURSING','OCCUPATION'),
(TRUE,1,'BUILDERS','BUILDERS','OCCUPATION'),
(TRUE,1,'OTHERS','OTHERS-OCCU','OCCUPATION'),
(TRUE,1,'FASHION DESIGNERS','FASHION DESIGNERS','OCCUPATION'),
(TRUE,1,'MANUFACTURING','MANUFACTURING','OCCUPATION');


INSERT INTO soap_component_authorization(ID,IS_ACTIVE, VERSION, ROLES, COMPONENTS) VALUES (10001,1,0,2260595906707456, 'SUBJECTIVE');
INSERT INTO soap_component_authorization(ID,IS_ACTIVE, VERSION, ROLES, COMPONENTS) VALUES (10002,1,0,2260595906707456, 'OBJECTIVE');
INSERT INTO soap_component_authorization(ID,IS_ACTIVE, VERSION, ROLES, COMPONENTS) VALUES (10003,1,0,2260595906707456, 'ASSESEMENT');
INSERT INTO soap_component_authorization(ID,IS_ACTIVE, VERSION, ROLES, COMPONENTS) VALUES (10004,1,0,2260595906707456, 'PLAN');
INSERT INTO soap_module(IS_ACTIVE,VERSION,MODULE_DESCRIPTION,NAME,MODULE_SHORT_NAME,SOAP_MODULE_TYPE,SORT_ORDER,SOAP_COMPONENT_AUTHORIZATION_ID) VALUES (1,0,'History of Present Illness','HPI','HPI','TEXT',1,10001);
INSERT INTO soap_module(IS_ACTIVE,VERSION,MODULE_DESCRIPTION,NAME,MODULE_SHORT_NAME,SOAP_MODULE_TYPE,SORT_ORDER,SOAP_COMPONENT_AUTHORIZATION_ID,CLASS_NAME) VALUES (1,0,'Family History','Family Hx','FHx','TEXT',2,10001,'com.nzion.domain.emr.soap.FamilyHistorySection');
INSERT INTO soap_module(IS_ACTIVE,VERSION,MODULE_DESCRIPTION,NAME,MODULE_SHORT_NAME,SOAP_MODULE_TYPE,SORT_ORDER,SOAP_COMPONENT_AUTHORIZATION_ID,CLASS_NAME) VALUES (1,0,'Social History','Social Hx','SHx','TEXT',3,10001,'com.nzion.domain.emr.soap.SocialHistorySection');
INSERT INTO soap_module(IS_ACTIVE,VERSION,MODULE_DESCRIPTION,NAME,MODULE_SHORT_NAME,SOAP_MODULE_TYPE,SORT_ORDER,SOAP_COMPONENT_AUTHORIZATION_ID,CLASS_NAME) VALUES (1,0,'Past History','Past Hx','Phx','TEXT',4,10001,'com.nzion.domain.emr.soap.PastHistorySection');
INSERT INTO soap_module(IS_ACTIVE,VERSION,MODULE_DESCRIPTION,NAME,MODULE_SHORT_NAME,SOAP_MODULE_TYPE,SORT_ORDER,SOAP_COMPONENT_AUTHORIZATION_ID,CLASS_NAME) VALUES (1,0,'Problem List','Problem List','PL','TEXT',4,10001,'com.nzion.domain.emr.soap.ProblemListSection');
INSERT INTO soap_module(IS_ACTIVE,VERSION,MODULE_DESCRIPTION,NAME,MODULE_SHORT_NAME,SOAP_MODULE_TYPE,SORT_ORDER,SOAP_COMPONENT_AUTHORIZATION_ID,CLASS_NAME) VALUES (1,0,'Allergy','Allergy','AG','TEXT',6,10001,'com.nzion.domain.emr.soap.AllergySection');
INSERT INTO soap_module(IS_ACTIVE,VERSION,MODULE_DESCRIPTION,NAME,MODULE_SHORT_NAME,SOAP_MODULE_TYPE,SORT_ORDER,SOAP_COMPONENT_AUTHORIZATION_ID,CLASS_NAME) VALUES (1,0,'Past Medication','MedicationHx','MHx','TEXT',5,10001,'com.nzion.domain.emr.soap.MedicationHistorySection');
INSERT INTO soap_module(IS_ACTIVE,VERSION,MODULE_DESCRIPTION,NAME,MODULE_SHORT_NAME,SOAP_MODULE_TYPE,SORT_ORDER,SOAP_COMPONENT_AUTHORIZATION_ID,CLASS_NAME) VALUES (1,0,'Gync & Obs History','Gync & Obs History','BHx','TEXT',8,10001,'com.nzion.domain.emr.soap.BirthHistorySection');
INSERT INTO soap_module(IS_ACTIVE,VERSION,MODULE_DESCRIPTION,NAME,MODULE_SHORT_NAME,SOAP_MODULE_TYPE,SORT_ORDER,SOAP_COMPONENT_AUTHORIZATION_ID,CLASS_NAME) VALUES (1,0,'Vital Sign','Vital Sign','VS','TEXT',10,10002,'com.nzion.domain.emr.soap.VitalSignSection');
INSERT INTO soap_module(IS_ACTIVE,VERSION,MODULE_DESCRIPTION,NAME,MODULE_SHORT_NAME,SOAP_MODULE_TYPE,SORT_ORDER,SOAP_COMPONENT_AUTHORIZATION_ID,ROLES,CLASS_NAME) VALUES (1,0,'Physical Examination','Physical Examination','EX','QA',11,10002,140737488355328,'com.nzion.domain.emr.soap.ExaminationSection');
INSERT INTO soap_module(IS_ACTIVE,VERSION,MODULE_DESCRIPTION,NAME,MODULE_SHORT_NAME,SOAP_MODULE_TYPE,SORT_ORDER,SOAP_COMPONENT_AUTHORIZATION_ID,CLASS_NAME) VALUES (1,0,'Immunization','Immunization','IM','TEXT',19,10002,'com.nzion.domain.emr.soap.ImmunizationSection');
INSERT INTO soap_module(IS_ACTIVE,VERSION,MODULE_DESCRIPTION,NAME,MODULE_SHORT_NAME,SOAP_MODULE_TYPE,SORT_ORDER,SOAP_COMPONENT_AUTHORIZATION_ID,CLASS_NAME) VALUES (1,0,'Diagnosis','Diagnosis','DG','TEXT',12,10003,'com.nzion.domain.emr.soap.DiagnosisSection');
INSERT INTO soap_module(IS_ACTIVE,VERSION,MODULE_DESCRIPTION,NAME,MODULE_SHORT_NAME,SOAP_MODULE_TYPE,SORT_ORDER,SOAP_COMPONENT_AUTHORIZATION_ID,CLASS_NAME) VALUES (1,0,'Lab Orders','Lab Orders','LO','TEXT',13,10003,'com.nzion.domain.emr.soap.LabOrderSection');
INSERT INTO soap_module(IS_ACTIVE,VERSION,MODULE_DESCRIPTION,NAME,MODULE_SHORT_NAME,SOAP_MODULE_TYPE,SORT_ORDER,SOAP_COMPONENT_AUTHORIZATION_ID,CLASS_NAME) VALUES (1,0,'Image','Image','IMG','TEXT',14,10003,'com.nzion.domain.emr.soap.ImageSection');
INSERT INTO soap_module(IS_ACTIVE,VERSION,MODULE_DESCRIPTION,NAME,MODULE_SHORT_NAME,SOAP_MODULE_TYPE,SORT_ORDER,SOAP_COMPONENT_AUTHORIZATION_ID,CLASS_NAME) VALUES (1,0,'Prescription','Rx','Rx','TEXT',15,10003,'com.nzion.domain.emr.soap.RxSection');
INSERT INTO soap_module(IS_ACTIVE,VERSION,MODULE_DESCRIPTION,NAME,MODULE_SHORT_NAME,SOAP_MODULE_TYPE,SORT_ORDER,SOAP_COMPONENT_AUTHORIZATION_ID,CLASS_NAME) VALUES (1,0,'Recommendation','Recommendation','RC','TEXT',17,10004,'com.nzion.domain.emr.soap.RecommendationSection');
INSERT INTO soap_module(IS_ACTIVE,VERSION,MODULE_DESCRIPTION,NAME,MODULE_SHORT_NAME,SOAP_MODULE_TYPE,SORT_ORDER,SOAP_COMPONENT_AUTHORIZATION_ID,CLASS_NAME) VALUES (1,0,'Referral','Referral','RF','TEXT',18,10004,'com.nzion.domain.emr.soap.ReferralSection');
INSERT INTO soap_module(IS_ACTIVE,VERSION,MODULE_DESCRIPTION,NAME,MODULE_SHORT_NAME,SOAP_MODULE_TYPE,SORT_ORDER,SOAP_COMPONENT_AUTHORIZATION_ID,CLASS_NAME) VALUES (1,0,'General Examination','General Examination','GE','TEXT',9,10002,'com.nzion.domain.emr.soap.GeneralExaminationSection');
COMMIT;

INSERT INTO enumeration
(`ENUM_ID`,`IS_ACTIVE`,`CREATED_BY`,`CREATE_TX_TIMESTAMP`,`DEACTIVATION_REASON`,`UPDATE_BY`,`UPDATED_TX_TIMESTAMP`,`VERSION`,`CODE_SYSTEM`,`CODE_SYSTEM_NAME`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`,`DEACTIVATEDBY`)
VALUES
(103160,'?',NULL,NULL,NULL,'admin','2011-01-12 15:34:46',0,NULL,NULL,'Severe','SEVERE','ILLNESS_SEVERITY',NULL);
INSERT INTO enumeration
(`ENUM_ID`,`IS_ACTIVE`,`CREATED_BY`,`CREATE_TX_TIMESTAMP`,`DEACTIVATION_REASON`,`UPDATE_BY`,`UPDATED_TX_TIMESTAMP`,`VERSION`,`CODE_SYSTEM`,`CODE_SYSTEM_NAME`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`,`DEACTIVATEDBY`)
VALUES
(103171,'?',NULL,NULL,NULL,'admin','2011-01-12 15:34:46',0,NULL,NULL,'Acute','ACUTE','ILLNESS_SEVERITY',NULL);
INSERT INTO enumeration
(`ENUM_ID`,`IS_ACTIVE`,`CREATED_BY`,`CREATE_TX_TIMESTAMP`,`DEACTIVATION_REASON`,`UPDATE_BY`,`UPDATED_TX_TIMESTAMP`,`VERSION`,`CODE_SYSTEM`,`CODE_SYSTEM_NAME`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`,`DEACTIVATEDBY`)
VALUES
(103182,'?',NULL,NULL,NULL,'admin','2011-01-12 15:34:46',0,NULL,NULL,'Moderate','MODERATE','ILLNESS_SEVERITY',NULL);
INSERT INTO enumeration
(`ENUM_ID`,`IS_ACTIVE`,`CREATED_BY`,`CREATE_TX_TIMESTAMP`,`DEACTIVATION_REASON`,`UPDATE_BY`,`UPDATED_TX_TIMESTAMP`,`VERSION`,`CODE_SYSTEM`,`CODE_SYSTEM_NAME`,`DESCRIPTION`,`ENUM_CODE`,`ENUM_TYPE`,`DEACTIVATEDBY`)
VALUES
(103193,'?',NULL,NULL,NULL,'admin','2011-01-12 15:34:46',0,NULL,NULL,'Chronic','CHRONIC','ILLNESS_SEVERITY',NULL);

INSERT INTO `enumeration` (`ENUM_ID`, `IS_ACTIVE`, `CREATED_BY`, `CREATE_TX_TIMESTAMP`, `DEACTIVATION_REASON`, `UPDATE_BY`, `UPDATED_TX_TIMESTAMP`, `VERSION`, `CODE_SYSTEM`, `CODE_SYSTEM_NAME`, `DESCRIPTION`, `ENUM_CODE`, `ENUM_TYPE`, `DEACTIVATEDBY`) VALUES('101590','',NULL,NULL,NULL,'admin','2011-01-11 13:02:23','0',NULL,NULL,'Unconfirmed','UNCONFIRMED','ALLERGY_STATUS',NULL);
INSERT INTO `enumeration` (`ENUM_ID`, `IS_ACTIVE`, `CREATED_BY`, `CREATE_TX_TIMESTAMP`, `DEACTIVATION_REASON`, `UPDATE_BY`, `UPDATED_TX_TIMESTAMP`, `VERSION`, `CODE_SYSTEM`, `CODE_SYSTEM_NAME`, `DESCRIPTION`, `ENUM_CODE`, `ENUM_TYPE`, `DEACTIVATEDBY`) VALUES('101600','',NULL,NULL,NULL,'admin','2011-01-11 13:02:23','0',NULL,NULL,'Pending','PENDING','ALLERGY_STATUS',NULL);
INSERT INTO `enumeration` (`ENUM_ID`, `IS_ACTIVE`, `CREATED_BY`, `CREATE_TX_TIMESTAMP`, `DEACTIVATION_REASON`, `UPDATE_BY`, `UPDATED_TX_TIMESTAMP`, `VERSION`, `CODE_SYSTEM`, `CODE_SYSTEM_NAME`, `DESCRIPTION`, `ENUM_CODE`, `ENUM_TYPE`, `DEACTIVATEDBY`) VALUES('101610','',NULL,NULL,NULL,'admin','2011-01-11 13:02:23','0',NULL,NULL,'Suspect','SUSPECT','ALLERGY_STATUS',NULL);
INSERT INTO `enumeration` (`ENUM_ID`, `IS_ACTIVE`, `CREATED_BY`, `CREATE_TX_TIMESTAMP`, `DEACTIVATION_REASON`, `UPDATE_BY`, `UPDATED_TX_TIMESTAMP`, `VERSION`, `CODE_SYSTEM`, `CODE_SYSTEM_NAME`, `DESCRIPTION`, `ENUM_CODE`, `ENUM_TYPE`, `DEACTIVATEDBY`) VALUES('101620','',NULL,NULL,NULL,'admin','2011-01-11 13:02:23','0',NULL,NULL,'Confirmed','CONFIRMED','ALLERGY_STATUS',NULL);
INSERT INTO `enumeration` (`ENUM_ID`, `IS_ACTIVE`, `CREATED_BY`, `CREATE_TX_TIMESTAMP`, `DEACTIVATION_REASON`, `UPDATE_BY`, `UPDATED_TX_TIMESTAMP`, `VERSION`, `CODE_SYSTEM`, `CODE_SYSTEM_NAME`, `DESCRIPTION`, `ENUM_CODE`, `ENUM_TYPE`, `DEACTIVATEDBY`) VALUES('101630','',NULL,NULL,NULL,'admin','2011-01-11 13:02:23','0',NULL,NULL,'Inactive','INACTIVE','ALLERGY_STATUS',NULL);
INSERT INTO `enumeration` (`ENUM_ID`, `IS_ACTIVE`, `CREATED_BY`, `CREATE_TX_TIMESTAMP`, `DEACTIVATION_REASON`, `UPDATE_BY`, `UPDATED_TX_TIMESTAMP`, `VERSION`, `CODE_SYSTEM`, `CODE_SYSTEM_NAME`, `DESCRIPTION`, `ENUM_CODE`, `ENUM_TYPE`, `DEACTIVATEDBY`) VALUES('101640','',NULL,NULL,NULL,'admin','2011-01-11 13:02:23','0',NULL,NULL,'Errorneous','ERRORNEOUS','ALLERGY_STATUS',NULL);
INSERT INTO `enumeration` (`ENUM_ID`, `IS_ACTIVE`, `CREATED_BY`, `CREATE_TX_TIMESTAMP`, `DEACTIVATION_REASON`, `UPDATE_BY`, `UPDATED_TX_TIMESTAMP`, `VERSION`, `CODE_SYSTEM`, `CODE_SYSTEM_NAME`, `DESCRIPTION`, `ENUM_CODE`, `ENUM_TYPE`, `DEACTIVATEDBY`) VALUES('101650','',NULL,NULL,NULL,'admin','2011-01-11 12:43:52','0',NULL,NULL,'Severe','SV','ALLERGY_SEVERITY',NULL);
INSERT INTO `enumeration` (`ENUM_ID`, `IS_ACTIVE`, `CREATED_BY`, `CREATE_TX_TIMESTAMP`, `DEACTIVATION_REASON`, `UPDATE_BY`, `UPDATED_TX_TIMESTAMP`, `VERSION`, `CODE_SYSTEM`, `CODE_SYSTEM_NAME`, `DESCRIPTION`, `ENUM_CODE`, `ENUM_TYPE`, `DEACTIVATEDBY`) VALUES('101660','',NULL,NULL,NULL,'admin','2011-01-11 12:43:52','0',NULL,NULL,'Moderate','MO','ALLERGY_SEVERITY',NULL);
INSERT INTO `enumeration` (`ENUM_ID`, `IS_ACTIVE`, `CREATED_BY`, `CREATE_TX_TIMESTAMP`, `DEACTIVATION_REASON`, `UPDATE_BY`, `UPDATED_TX_TIMESTAMP`, `VERSION`, `CODE_SYSTEM`, `CODE_SYSTEM_NAME`, `DESCRIPTION`, `ENUM_CODE`, `ENUM_TYPE`, `DEACTIVATEDBY`) VALUES('101670','',NULL,NULL,NULL,'admin','2011-01-11 12:43:52','0',NULL,NULL,'Mild','MI','ALLERGY_SEVERITY',NULL);
INSERT INTO `enumeration` (`ENUM_ID`, `IS_ACTIVE`, `CREATED_BY`, `CREATE_TX_TIMESTAMP`, `DEACTIVATION_REASON`, `UPDATE_BY`, `UPDATED_TX_TIMESTAMP`, `VERSION`, `CODE_SYSTEM`, `CODE_SYSTEM_NAME`, `DESCRIPTION`, `ENUM_CODE`, `ENUM_TYPE`, `DEACTIVATEDBY`) VALUES('101680','',NULL,NULL,NULL,'admin','2011-01-11 12:43:52','0',NULL,NULL,'Unknown','MU','ALLERGY_SEVERITY',NULL);

INSERT INTO `enumeration`(IS_ACTIVE,CREATED_BY,CREATE_TX_TIMESTAMP,DEACTIVATION_REASON,UPDATE_BY,UPDATED_TX_TIMESTAMP,VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE) VALUES (1,NULL,NULL,NULL,'samir','2011-01-12 15:34:46',0,'Mild','MILD','ILLNESS_SEVERITY');
INSERT INTO `enumeration`(IS_ACTIVE,CREATED_BY,CREATE_TX_TIMESTAMP,DEACTIVATION_REASON,UPDATE_BY,UPDATED_TX_TIMESTAMP,VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE) VALUES (1,NULL,NULL,NULL,'samir','2011-01-12 15:34:46',0,'Severe','SEVERE','ILLNESS_SEVERITY');
INSERT INTO `enumeration`(IS_ACTIVE,CREATED_BY,CREATE_TX_TIMESTAMP,DEACTIVATION_REASON,UPDATE_BY,UPDATED_TX_TIMESTAMP,VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE) VALUES (1,NULL,NULL,NULL,'samir','2011-01-12 15:34:46',0,'Acute','ACUTE','ILLNESS_SEVERITY');
INSERT INTO `enumeration`(IS_ACTIVE,CREATED_BY,CREATE_TX_TIMESTAMP,DEACTIVATION_REASON,UPDATE_BY,UPDATED_TX_TIMESTAMP,VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE) VALUES (1,NULL,NULL,NULL,'samir','2011-01-12 15:34:46',0,'Moderate','MODERATE','ILLNESS_SEVERITY');
INSERT INTO `enumeration`(IS_ACTIVE,CREATED_BY,CREATE_TX_TIMESTAMP,DEACTIVATION_REASON,UPDATE_BY,UPDATED_TX_TIMESTAMP,VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE) VALUES (1,NULL,NULL,NULL,'samir','2011-01-12 15:34:46',0,'Chronic','CHRONIC','ILLNESS_SEVERITY');
INSERT INTO `enumeration`(IS_ACTIVE,CREATED_BY,CREATE_TX_TIMESTAMP,DEACTIVATION_REASON,UPDATE_BY,UPDATED_TX_TIMESTAMP,VERSION,DESCRIPTION,ENUM_CODE,ENUM_TYPE) VALUES (1,NULL,NULL,NULL,'samir','2011-01-12 15:34:46',0,'Resolved','RESOLVED','ILLNESS_STATUS');


