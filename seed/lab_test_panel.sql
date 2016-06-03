/*
SQLyog Community v10.2 
MySQL - 5.0.51b-community-nt 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

insert into `lab_test_panel` (`ID`, `IS_ACTIVE`, `CREATED_BY`, `CREATE_TX_TIMESTAMP`, `DEACTIVATION_REASON`, `UPDATE_BY`, `UPDATED_TX_TIMESTAMP`, `VERSION`, `DEPARTMENT`, `PANEL_CODE`, `PANEL_DESCRIPTION`, `PANEL_PNEUMONIC`, `DEACTIVATEDBY`) 
values('1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Anatomic Pathology','Panel 1','Complete Blood Count','Complete Blood Count',NULL);

ALTER TABLE `lab_test_panel` ADD COLUMN `PRESCRIPTION_REQUIRED` BIT(1) DEFAULT NOT NULL;
