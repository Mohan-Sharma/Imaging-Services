/*
SQLyog Community v10.2 
MySQL - 5.0.51b-community-nt 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

/*Table structure for table `laboratories` */

/*
DROP TABLE IF EXISTS `laboratories`;

CREATE TABLE `laboratories` (
  `LABORATORY_CODE` varchar(10) default NULL,
  `LABORATORY` varchar(256) default NULL,
  `DEPARTMENT` varchar(256) default NULL,
  `DESCRIPTION` varchar(256) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
*/

/*Data for the table `laboratories` */


insert into `laboratories` (`LABORATORY_CODE`, `LABORATORY`, `DEPARTMENT`, `DESCRIPTION`) values('1','Histopathology','Anatomic Pathology\r',NULL);
insert into `laboratories` (`LABORATORY_CODE`, `LABORATORY`, `DEPARTMENT`, `DESCRIPTION`) values('2','Cytopathology','Anatomic Pathology\r',NULL);
insert into `laboratories` (`LABORATORY_CODE`, `LABORATORY`, `DEPARTMENT`, `DESCRIPTION`) values('3','Electron Microscopy','Anatomic Pathology\r',NULL);
insert into `laboratories` (`LABORATORY_CODE`, `LABORATORY`, `DEPARTMENT`, `DESCRIPTION`) values('4','Bacteriology','Clinical Microbiology\r',NULL);
insert into `laboratories` (`LABORATORY_CODE`, `LABORATORY`, `DEPARTMENT`, `DESCRIPTION`) values('5','Virology','Clinical Microbiology\r',NULL);
insert into `laboratories` (`LABORATORY_CODE`, `LABORATORY`, `DEPARTMENT`, `DESCRIPTION`) values('6','Parasitology','Clinical Microbiology\r',NULL);
insert into `laboratories` (`LABORATORY_CODE`, `LABORATORY`, `DEPARTMENT`, `DESCRIPTION`) values('7','Immunology','Clinical Microbiology\r',NULL);
insert into `laboratories` (`LABORATORY_CODE`, `LABORATORY`, `DEPARTMENT`, `DESCRIPTION`) values('8','Mycology','Clinical Microbiology\r',NULL);
insert into `laboratories` (`LABORATORY_CODE`, `LABORATORY`, `DEPARTMENT`, `DESCRIPTION`) values('9','Enzymology','Clinical Chemistry \r',NULL);
insert into `laboratories` (`LABORATORY_CODE`, `LABORATORY`, `DEPARTMENT`, `DESCRIPTION`) values('10','Toxicology','Clinical Chemistry \r',NULL);
insert into `laboratories` (`LABORATORY_CODE`, `LABORATORY`, `DEPARTMENT`, `DESCRIPTION`) values('11','Endocrinology','Clinical Chemistry \r',NULL);
insert into `laboratories` (`LABORATORY_CODE`, `LABORATORY`, `DEPARTMENT`, `DESCRIPTION`) values('12','Coagulation','Hematology\r',NULL);
insert into `laboratories` (`LABORATORY_CODE`, `LABORATORY`, `DEPARTMENT`, `DESCRIPTION`) values('13','Blood Bank','Hematology\r',NULL);
insert into `laboratories` (`LABORATORY_CODE`, `LABORATORY`, `DEPARTMENT`, `DESCRIPTION`) values('14','Genetics','Genetics\r',NULL);
insert into `laboratories` (`LABORATORY_CODE`, `LABORATORY`, `DEPARTMENT`, `DESCRIPTION`) values('15','Cytogenetics','Cytogenetics\r',NULL);
