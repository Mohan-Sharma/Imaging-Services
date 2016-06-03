/*
SQLyog Community v10.2 
MySQL - 5.0.51b-community-nt 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

/*Table structure for table `specimen` */

DROP TABLE IF EXISTS `specimen`;

CREATE TABLE `specimen` (
  `SPECIMEN_CODE` varchar(10) default NULL,
  `DESCRIPTION` varchar(256) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `specimen` */

insert into `specimen` (`SPECIMEN_CODE`, `DESCRIPTION`) values('01','BLOOD                                             \r');
insert into `specimen` (`SPECIMEN_CODE`, `DESCRIPTION`) values('02','BREAST FLUID                                      \r');
insert into `specimen` (`SPECIMEN_CODE`, `DESCRIPTION`) values('03','ASCITIC FLUID                                     \r');
insert into `specimen` (`SPECIMEN_CODE`, `DESCRIPTION`) values('04','BRONCHIAL FLUID                                   \r');
insert into `specimen` (`SPECIMEN_CODE`, `DESCRIPTION`) values('05','OVARIAN FLUID                                     \r');
insert into `specimen` (`SPECIMEN_CODE`, `DESCRIPTION`) values('06','SEBACEAUS MATERIAL\r');
insert into `specimen` (`SPECIMEN_CODE`, `DESCRIPTION`) values('07','PUS SWAB                                          \r');
insert into `specimen` (`SPECIMEN_CODE`, `DESCRIPTION`) values('08','URINE                                             \r');
insert into `specimen` (`SPECIMEN_CODE`, `DESCRIPTION`) values('09','BODY FLUID                                        \r');
insert into `specimen` (`SPECIMEN_CODE`, `DESCRIPTION`) values('10','STOOL                                             \r');
insert into `specimen` (`SPECIMEN_CODE`, `DESCRIPTION`) values('11','SERUM                                             \r');
insert into `specimen` (`SPECIMEN_CODE`, `DESCRIPTION`) values('12','BONE MARROW                                       \r');
insert into `specimen` (`SPECIMEN_CODE`, `DESCRIPTION`) values('13','CORE BIOPSY                                       \r');
insert into `specimen` (`SPECIMEN_CODE`, `DESCRIPTION`) values('14','BLOOD SMEAR                                       \r');
insert into `specimen` (`SPECIMEN_CODE`, `DESCRIPTION`) values('15','SMEAR                                             \r');
insert into `specimen` (`SPECIMEN_CODE`, `DESCRIPTION`) values('16','PLASMA                                            \r');
insert into `specimen` (`SPECIMEN_CODE`, `DESCRIPTION`) values('17','CLOTTED BLOOD                                     \r');
insert into `specimen` (`SPECIMEN_CODE`, `DESCRIPTION`) values('18','24HR URINE                                        \r');
insert into `specimen` (`SPECIMEN_CODE`, `DESCRIPTION`) values('19','SEMEN                                             \r');
insert into `specimen` (`SPECIMEN_CODE`, `DESCRIPTION`) values('20','48 Hours Urine specimen \r');
