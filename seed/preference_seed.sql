DELETE FROM  preference;

DELETE FROM  subscription_type;

INSERT INTO  subscription_type
       (ID,
        VERSION,
        CREATED_BY,
        CREATE_TX_TIMESTAMP,
        SUBSCRIPTION_TYPE_NAME,
        DESCRIPTION,
        FROM_DATETIME,
        THRU_DATETIME,
        PRODUCT_TYPE)
VALUES (
          1,
          0,
          'seed',
          NOW(),
          'PMS',
          'Practice Management Systems',
          NOW(),
          NULL,
          'PMS'
       );

INSERT INTO  subscription_type
       (ID,
        VERSION,
        CREATED_BY,
        CREATE_TX_TIMESTAMP,
        SUBSCRIPTION_TYPE_NAME,
        DESCRIPTION,
        FROM_DATETIME,
        THRU_DATETIME,
        PRODUCT_TYPE)
VALUES (
          2,
          0,
          'seed',
          NOW(),
          'EMR',
          'Electronic Medical Records',
          NOW(),
          NULL,
          'EMR'
       );

INSERT INTO  subscription_type
       (ID,
        VERSION,
        CREATED_BY,
        CREATE_TX_TIMESTAMP,
        SUBSCRIPTION_TYPE_NAME,
        DESCRIPTION,
        FROM_DATETIME,
        THRU_DATETIME,
        PRODUCT_TYPE)
VALUES (3, 0, 'seed', NOW(), 'BOTH', 'Both', NOW(), NULL, 'BOTH');

DELETE FROM  preference_definition;

DELETE FROM  preference_group_definition;

INSERT INTO  preference_group_definition
       (ID,
        VERSION,
        CREATED_BY,
        CREATE_TX_TIMESTAMP,
        DESCRIPTION,
        PREFERENCE_GROUP_DEFINATION_CODE,
        PREF_GROUP_TYPE,
        SUBCRIPTION_TYPE_ID)
VALUES (
          1,
          1,
          'seed',
          NOW(),
          'Automatic Assignment',
          'AUTOASSIGN',
          'PRACTICE',
          1
       );

INSERT INTO  preference_group_definition
       (ID,
        VERSION,
        CREATED_BY,
        CREATE_TX_TIMESTAMP,
        DESCRIPTION,
        PREFERENCE_GROUP_DEFINATION_CODE,
        PREF_GROUP_TYPE,
        SUBCRIPTION_TYPE_ID)
VALUES (3, 1, 'seed', NOW(), 'Patient Entry Defaults', 'PED', 'PRACTICE', 1);

INSERT INTO  preference_group_definition
       (ID,
        VERSION,
        CREATED_BY,
        CREATE_TX_TIMESTAMP,
        DESCRIPTION,
        PREFERENCE_GROUP_DEFINATION_CODE,
        PREF_GROUP_TYPE,
        SUBCRIPTION_TYPE_ID)
VALUES (
          4,
          1,
          'seed',
          NOW(),
          'Patient Entry Validations',
          'PEV',
          'PRACTICE',
          1
       );

INSERT INTO  preference_group_definition
       (ID,
        VERSION,
        CREATED_BY,
        CREATE_TX_TIMESTAMP,
        DESCRIPTION,
        PREFERENCE_GROUP_DEFINATION_CODE,
        PREF_GROUP_TYPE,
        SUBCRIPTION_TYPE_ID)
VALUES (6, 1, 'seed', NOW(), 'Patient Name Defaults', 'PND', 'PRACTICE', 1);

INSERT INTO  preference_group_definition
       (
	ID,
        VERSION,
        CREATED_BY,
        CREATE_TX_TIMESTAMP,
        DESCRIPTION,
        PREFERENCE_GROUP_DEFINATION_CODE,
        PREF_GROUP_TYPE,
        SUBCRIPTION_TYPE_ID)
VALUES (
	12,	
          1,
          'seed',
          NOW(),
          'Provider Preferences',
          'PROVIDER_PREF',
          'PROVIDER',
          NULL
       );
       
  INSERT INTO  preference_group_definition
       (
       ID,
        VERSION,
        CREATED_BY,
        CREATE_TX_TIMESTAMP,
        DESCRIPTION,
        PREFERENCE_GROUP_DEFINATION_CODE,
        PREF_GROUP_TYPE,
        SUBCRIPTION_TYPE_ID)
VALUES (
          13,
          1,
          'seed',
          NOW(),
          'Provider Prescriptions',
          'PROVIDER_PRESCRIPTIONS',
          'PROVIDER',
          NULL
       );
       
/***provider preferences end***/       
       
INSERT INTO  preference_definition
       (VERSION,
        CREATED_BY,
        CREATE_TX_TIMESTAMP,
        NAME,
        description,
        `label`,
        PREFERENCE_GROUP_DEFINITION_ID,
        SEQ_NUM,
        FIELD_TYPE,
        FIELD_VALUE)
VALUES (
          1,
          'seed',
          NOW(),
          'ACCOUNT_NUMBER',
          'accountNumber',
          'Patient Code',
          1,
          1,
          'IDSEQUENCE',
          'com.ecosmos.domain.Patient'
       );

INSERT INTO  preference_definition
		(VERSION,
        CREATED_BY,
        CREATE_TX_TIMESTAMP,
        NAME,
        description,
        `label`,
        PREFERENCE_GROUP_DEFINITION_ID,
        SEQ_NUM,
        FIELD_TYPE,
        FIELD_VALUE)
VALUES (
          1,
          'seed',
          NOW(),
          'INSURANCE_CODE',
          'accountNumber',
          'Insurance Code',
          1,
          4,
          'IDSEQUENCE',
          'com.ecosmos.domain.pms.InsuranceProvider'
       );

INSERT INTO  preference_definition
       (VERSION,
        CREATED_BY,
        CREATE_TX_TIMESTAMP,
        NAME,
        description,
        `label`,
        PREFERENCE_GROUP_DEFINITION_ID,
        SEQ_NUM,
        FIELD_TYPE,
        FIELD_VALUE)
VALUES (
          1,
          'seed',
          NOW(),
          'PROVIDER_CODE',
          'accountNumber',
          'Provider Code',
          1,
          5,
          'IDSEQUENCE',
          'com.ecosmos.domain.Provider'
       );

INSERT INTO preference_definition
       (VERSION,
        CREATED_BY,
        CREATE_TX_TIMESTAMP,
        NAME,
        description,
        `label`,
        PREFERENCE_GROUP_DEFINITION_ID,
        SEQ_NUM,
        FIELD_TYPE,
        FIELD_VALUE)
VALUES (
          1,
          'seed',
          NOW(),
          'LOCATION_CODE',
          'locationCode',
          'Location Code',
          1,
          6,
          'IDSEQUENCE',
          'com.ecosmos.domain.Location'
       );
       
/*
INSERT INTO preference_definition
       (VERSION,
        CREATED_BY,
        CREATE_TX_TIMESTAMP,
        NAME,
        description,
        `label`,
        PREFERENCE_GROUP_DEFINITION_ID,
        SEQ_NUM,
        FIELD_TYPE,
        FIELD_VALUE)
VALUES (
          1,
          'seed',
          NOW(),
          'PCP_CODE',
          'accountNumber',
          'PCP Code',
          1,
          7,
          'IDSEQUENCE',
          'com.ecosmos.domain.Pcp'
       );
*/


/*INSERT INTO preference_definition
       (VERSION,
        CREATED_BY,
        CREATE_TX_TIMESTAMP,
        NAME,
        description,
        `label`,
        PREFERENCE_GROUP_DEFINITION_ID,
        SEQ_NUM,
        FIELD_TYPE,
        FIELD_VALUE)
VALUES (
          1,
          'seed',
          NOW(),
          'CASE_MANAGER_CODE',
          'accountNumber',
          'Case Manager Code',
          1,
          9,
          'IDSEQUENCE',
          'com.ecosmos.domain.CaseManager'
       );*/


INSERT INTO preference_definition
       (VERSION,
        CREATED_BY,
        CREATE_TX_TIMESTAMP,
        NAME,
        description,
        `label`,
        PREFERENCE_GROUP_DEFINITION_ID,
        SEQ_NUM,
        FIELD_TYPE,
        FIELD_VALUE)
VALUES (
          1,
          'seed',
          NOW(),
          'REFERRAL_CODE',
          'accountNumber',
          'Referral Code',
          1,
          21,
          'IDSEQUENCE',
          'com.ecosmos.domain.Referral'
       );

INSERT INTO preference_definition
       (VERSION,
        CREATED_BY,
        CREATE_TX_TIMESTAMP,
        NAME,
        description,
        `label`,
        PREFERENCE_GROUP_DEFINITION_ID,
        SEQ_NUM,
        FIELD_TYPE,
        FIELD_VALUE)
VALUES (
          1,
          'seed',
          NOW(),
          'EMPLOYEE_CODE',
          'accountNumber',
          'Employee Code',
          1,
          22,
          'IDSEQUENCE',
          'com.ecosmos.domain.Employee'
       );       
       


INSERT INTO preference_definition
       (VERSION,
        CREATED_BY,
        CREATE_TX_TIMESTAMP,
        NAME,
        description,
        `label`,
        PREFERENCE_GROUP_DEFINITION_ID,
        SEQ_NUM,
        FIELD_TYPE,
        FIELD_VALUE)
VALUES (
          1,
          'seed',
          NOW(),
          'CITY_DEFINITION',
          'city',
          'City',
          3,
          1,
          'TEXTBOX',
          ''
       );

INSERT INTO preference_definition
       (VERSION,
        CREATED_BY,
        CREATE_TX_TIMESTAMP,
        NAME,
        description,
        `label`,
        PREFERENCE_GROUP_DEFINITION_ID,
        SEQ_NUM,
        FIELD_TYPE,
        FIELD_VALUE)
VALUES (
          1,
          'seed',
          NOW(),
          'STATE_DEFINITION',
          'stateProvinceGeo',
          'State',
          3,
          2,
          'TEXTBOX',
          ''
       );


INSERT INTO preference_definition
       (VERSION,
        CREATED_BY,
        CREATE_TX_TIMESTAMP,
        NAME,
        description,
        `label`,
        PREFERENCE_GROUP_DEFINITION_ID,
        SEQ_NUM,
        FIELD_TYPE,
        FIELD_VALUE)
VALUES (
          1,
          'seed',
          NOW(),
          'POSTAL_CODE',
          'postalCode',
          'Postal Code',
          3,
          3,
          'TEXTBOX',
          ''
       );

INSERT INTO  preference_definition
       (VERSION,
        CREATED_BY,
        CREATE_TX_TIMESTAMP,
        NAME,
        description,
        `label`,
        PREFERENCE_GROUP_DEFINITION_ID,
        SEQ_NUM,
        FIELD_TYPE,
        FIELD_VALUE)
VALUES (
          1,
          'seed',
          NOW(),
          'LAB_TEST_CODE',
          'accountNumber',
          'Lab Test Code',
          1,
          3,
          'IDSEQUENCE',
          'com.ecosmos.domain.emr.lab.LabTest'
       );

INSERT INTO  preference_definition
       (VERSION,
        CREATED_BY,
        CREATE_TX_TIMESTAMP,
        NAME,
        description,
        `label`,
        PREFERENCE_GROUP_DEFINITION_ID,
        SEQ_NUM,
        FIELD_TYPE,
        FIELD_VALUE)
VALUES (
          1,
          'seed',
          NOW(),
          'LAB_TEST_PANEL_CODE',
          'accountNumber',
          'Lab Test Panel Code',
          1,
          3,
          'IDSEQUENCE',
          'com.ecosmos.domain.emr.lab.LabTestPanel'
       );

       
/**patient entry validation**/


INSERT INTO preference_definition
       (VERSION,
        CREATED_BY,
        CREATE_TX_TIMESTAMP,
        NAME,
        description,
        `label`,
        PREFERENCE_GROUP_DEFINITION_ID,
        SEQ_NUM,
        FIELD_TYPE,
        FIELD_VALUE)
VALUES (
          1,
          'seed',
          NOW(),
          'MIDDLE_NAME',
          'middleName',
          'Middle Name',
          4,
          2,
          'CHECKBOX',
          NULL
       );

INSERT INTO preference_definition
       (VERSION,
        CREATED_BY,
        CREATE_TX_TIMESTAMP,
        NAME,
        description,
        `label`,
        PREFERENCE_GROUP_DEFINITION_ID,
        SEQ_NUM,
        FIELD_TYPE,
        FIELD_VALUE)
VALUES (
          1,
          'seed',
          NOW(),
          'ETHINCITY',
          'ethnicity',
          'Ethnicity',
          4,
          5,
          'CHECKBOX',
          "false"
       );       

/** Patient Name Defaults **/

INSERT INTO preference_definition
       (VERSION,
        CREATED_BY,
        CREATE_TX_TIMESTAMP,
        NAME,
        description,
        `label`,
        PREFERENCE_GROUP_DEFINITION_ID,
        SEQ_NUM,
        FIELD_TYPE,
        FIELD_VALUE,
        REQUIRED_COLUMN)
VALUES (
          1,
          'seed',
          NOW(),
          'FIRST_POSITION',
          'FIRST POSITION',
          'First Position',
          6,
          1,
          'ENUM',
          'lastName',
          'NAME_COMPONENTS'
       ); 



INSERT INTO preference_definition
       (VERSION,
        CREATED_BY,
        CREATE_TX_TIMESTAMP,
        NAME,
        description,
        `label`,
        PREFERENCE_GROUP_DEFINITION_ID,
        SEQ_NUM,
        FIELD_TYPE,
        FIELD_VALUE,
        REQUIRED_COLUMN)
VALUES (
          1,
          'seed',
          NOW(),
          'SECOND_POSITION',
          'SECOND POSITION',
          'Second Position',
          6,
          2,
          'ENUM',
          'firstName',
          'NAME_COMPONENTS'
       ); 


INSERT INTO preference_definition
       (VERSION,
        CREATED_BY,
        CREATE_TX_TIMESTAMP,
        NAME,
        description,
        `label`,
        PREFERENCE_GROUP_DEFINITION_ID,
        SEQ_NUM,
        FIELD_TYPE,
        FIELD_VALUE,
        REQUIRED_COLUMN)
VALUES (
          1,
          'seed',
          NOW(),
          'THIRD_POSITION',
          'THIRD POSITION',
          'Third Position',
          6,
          3,
          'ENUM',
          'middleName',
          'NAME_COMPONENTS'
       ); 
       
       
       
