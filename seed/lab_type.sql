/*
SQLyog Community v10.2 
MySQL - 5.0.51b-community-nt 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

/*Table structure for table `lab_type` */

DROP TABLE IF EXISTS `lab_type`;

CREATE TABLE `lab_type` (
  `LAB_TYPE_CODE` varchar(15) default NULL,
  `LABORATORY_TYPE` varchar(256) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `lab_type` */

insert into `lab_type` (`LAB_TYPE_CODE`, `LABORATORY_TYPE`) values('01','Individual Laboratory\r');
insert into `lab_type` (`LAB_TYPE_CODE`, `LABORATORY_TYPE`) values('02','Hospital Laboratory\r');
insert into `lab_type` (`LAB_TYPE_CODE`, `LABORATORY_TYPE`) values('03','Clinic Laboratory\r');
