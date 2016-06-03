INSERT  INTO `password_policy`(`CREATED_BY`,`CREATE_TX_TIMESTAMP`,`VERSION`,`PWD_MAX_REPEATED_CHARS`,`PWD_EXPIRE_WARNING`,`PWD_GRACE_LOGIN_LIMIT`,`PWD_CHECK_SYNTAX`,`PWD_FAILURE_COUNT_INTERVAL`,`PWD_IN_HISTORY`,`PWD_MAX_AGE`,`PWD_MAX_FAILURE`,`PWD_MIN_LENGTH`,`PWD_MUST_CHANGE`,`SELECTED_PATTERN`,`SESSION_TIME_OUT`) VALUES ('SEED',NOW(),2,NULL,NULL,NULL,TRUE,NULL,NULL,100,3,8,'','([0-9]+(\\p{Alpha})+)+([0-9]*(\\p{Alpha})*)*|((\\p{Alpha})+[0-9]+)([0-9]*(\\p{Alpha})*)*',600);

INSERT INTO USER_LOGIN (IMPERSONATED,VERSION,USERNAME,PASSWORD,IS_ACTIVE, ACCOUNT_NON_EXPIRED, ACCOUNT_NON_LOCKED,CREDENTIALS_NON_EXPIRED,SUCCESSIVE_FAILED_LOGINS,IS_REQUIRE_PASSWORD_CHANGE,ROLES,ACCEPTED_TERMS_AND_CONDITIONS) VALUES (FALSE,0,'superadmin', 'superadmin', TRUE, TRUE, TRUE, TRUE, 0, FALSE,576460752303423488,true);

-- DELETE FROM SLOT_TYPE;

DELETE FROM ENTITY_SEQUENCES;


INSERT INTO PKGENERATOR(CLASSNAME,PK_VALUE) values ('com.nzion.domain.emr.SoapModule',0);


source enumeration_seed.sql

-- source cpt.sql
-- source icd_element.sql
-- source loinc.sql
-- source lab_specimen_source.sql
-- source \.sql

-- source drug_sig.sql
-- source granted_security_permission.sql
-- commit;
source unit_of_measurement.sql;
commit;
-- source lab_specimen_source.sql;
commit;
-- source bed_characters.sql
commit;
-- source bed_status.sql
commit;
-- source productCategory.sql
commit;
-- source discharge_section_metadata.sql;
commit;
source speciality.sql;
commit;
-- source chiefcomplaints.sql;
commit;
-- source speciality-chiefcomplaint.sql;
commit;
source preference.sql;
commit;

source lab_department.sql;
commit;
source lab_type.sql;
commit;
source laboratories.sql;
commit;
source race.sql;
commit;
source result_type.sql;
commit;
source specimen.sql;
commit;
source investigation.sql;
commit;
source lab_test.sql;
commit;
source lab_test_profile.sql;
commit;
source lab_test_panel.sql;
commit;
source service_main_group.sql;
commit;
source service_sub_group.sql;
commit;
source pricing.sql;
commit;

source clinic.sql;
commit;

INSERT INTO REFERRAL(ID,IS_ACTIVE,CREATED_BY,CREATE_TX_TIMESTAMP,UPDATE_BY,UPDATED_TX_TIMESTAMP,VERSION,FIRST_NAME,LAST_NAME)
VALUES (9000,TRUE,'admin',SYSDATE(),'admin',SYSDATE(),1,'SELF','');

INSERT INTO PERSON (ID,IS_ACTIVE,CREATED_BY,CREATE_TX_TIMESTAMP,UPDATE_BY,UPDATED_TX_TIMESTAMP,VERSION,ACCOUNT_NUMBER,
PARTY_TYPE,CORPORATE_EMAIL,EMAIL,MOBILE_NUMBER,OFFICE_PHONE,ADDRESS1,CITY,POSTAL_CODE,STATE_PROVINCE_GEO,
FIRST_NAME,LAST_NAME,SCHEDULABLE)
VALUES(9000,TRUE,'admin',SYSDATE(),'admin',SYSDATE(),1,9000,'PROVIDER','','','','','','','','',
'INHOUSE DOCTOR','',TRUE);

INSERT INTO EMPLOYEE (ID) VALUES(9000);

INSERT INTO PROVIDER (ID,PROVIDER_ASSISTANT) VALUES (9000,FALSE);


