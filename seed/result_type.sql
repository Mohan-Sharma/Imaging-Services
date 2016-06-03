/*
SQLyog Community v10.2 
MySQL - 5.0.51b-community-nt 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

/*Table structure for table `result_type` */

DROP TABLE IF EXISTS `result_type`;

CREATE TABLE `result_type` (
  `RESULT_TYPE_CODE` varchar(10) default NULL,
  `DESCRIPTION` varchar(256) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `result_type` */


insert into `result_type` (`RESULT_TYPE_CODE`, `DESCRIPTION`) values('1','Numeric Value\r');
insert into `result_type` (`RESULT_TYPE_CODE`, `DESCRIPTION`) values('2','Text Box\r');
insert into `result_type` (`RESULT_TYPE_CODE`, `DESCRIPTION`) values('3','Upload\r');
