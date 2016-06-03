/*
SQLyog Community v10.2 
MySQL - 5.0.51b-community-nt : Database - afya_test
*********************************************************************
*/


CREATE TABLE `service_main_group` (
  `SERVICE_CODE` varchar(20) NOT NULL,
  `SERVICE_MAIN_GROUP` varchar(255) default NULL,
  PRIMARY KEY  (`SERVICE_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `service_main_group` */

insert  into `service_main_group`(`SERVICE_CODE`,`SERVICE_MAIN_GROUP`) values ('01','Consultation services\r'),('02','General Services\r'),('03','Physical Therapy\r'),('04','Dental\r'),('05','Surgery Minor\r'),('06','Emergency Services\r'),('07','Laboratory Services\r'),('08','Radiology Services\r');

