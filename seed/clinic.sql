/*
SQLyog Community v10.2 
MySQL - 5.0.51b-community-nt : Database - afya_lab_db
*********************************************************************
*/

CREATE TABLE `clinic` (
  `CLINIC_ID` varchar(255) NOT NULL,
  `CLINIC_REFERRAL_ID` varchar(255) default NULL,
  `CLINIC_NAME` varchar(255) default NULL,
  PRIMARY KEY  (`CLINIC_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

