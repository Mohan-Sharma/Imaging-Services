/*
SQLyog Community v10.2 
MySQL - 5.0.51b-community-nt 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

/*Table structure for table `lab_department` */

/*
DROP TABLE IF EXISTS `lab_department`;

CREATE TABLE `lab_department` (
  `LAB_DEPARTMENT_CODE` varchar(10) default NULL,
  `DEPARTMENT` varchar(256) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
*/

/*Data for the table `lab_department` */


insert into `lab_department` (`LAB_DEPARTMENT_CODE`, `DEPARTMENT`) values('1','Anatomic Pathology\r');
insert into `lab_department` (`LAB_DEPARTMENT_CODE`, `DEPARTMENT`) values('2','Clinical Microbiology\r');
insert into `lab_department` (`LAB_DEPARTMENT_CODE`, `DEPARTMENT`) values('3','Clinical Chemistry \r');
insert into `lab_department` (`LAB_DEPARTMENT_CODE`, `DEPARTMENT`) values('4','Hematology\r');
insert into `lab_department` (`LAB_DEPARTMENT_CODE`, `DEPARTMENT`) values('5','Genetics\r');
insert into `lab_department` (`LAB_DEPARTMENT_CODE`, `DEPARTMENT`) values('6','Cytogenetics\r');
