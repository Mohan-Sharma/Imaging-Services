/*
SQLyog Community v10.2 
MySQL - 5.0.51b-community-nt 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

/*Table structure for table `race` */

DROP TABLE IF EXISTS `race`;

CREATE TABLE `race` (
  `RACE_CODE` varchar(10) default NULL,
  `DESCRIPTION` varchar(256) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `race` */

insert into `race` (`RACE_CODE`, `DESCRIPTION`) values('01','Caucasian\r');
insert into `race` (`RACE_CODE`, `DESCRIPTION`) values('02','African\r');
insert into `race` (`RACE_CODE`, `DESCRIPTION`) values('03','Asian\r');
insert into `race` (`RACE_CODE`, `DESCRIPTION`) values('04','Middle Eastern\r');
