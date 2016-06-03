/*
SQLyog Community v10.2 
MySQL - 5.0.51b-community-nt : Database - afya_test
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;


CREATE TABLE `service_sub_group` (
  `SERVICE_CODE` varchar(20) NOT NULL,
  `DESCRIPTION` varchar(255) default NULL,
  `SERVICE_MAIN_GROUP_CODE` varchar(20) default NULL,
  PRIMARY KEY  (`SERVICE_CODE`),
  KEY `SERVICE_MAIN_GROUP_CODE` (`SERVICE_MAIN_GROUP_CODE`),
  CONSTRAINT `service_sub_group_ibfk_1` FOREIGN KEY (`SERVICE_MAIN_GROUP_CODE`) REFERENCES `service_main_group` (`SERVICE_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `service_sub_group` */

insert  into `service_sub_group`(`SERVICE_CODE`,`DESCRIPTION`,`SERVICE_MAIN_GROUP_CODE`) values ('001','Regular Consultations','01'),('002','Specialist Consultations','01'),('003','Referral Consultations','01'),('004','Certificates','02'),('005','Documents','02'),('006','Cardiovascular & pulmonary physiotherapy','03'),('007','Clinical electrophysiology','03'),('008','Geriatric','03'),('009','Integumentary','03'),('010','Neurological','03'),('011','Orthopedic','03'),('012','Pediatric','03'),('013','Sports','03'),('014','Womens health','03'),('015','Palliative care','03'),('016','Endodontics','04'),('017','Oral and Maxillofacial pathology','04'),('018','Oral and Maxillofacial Radiology','04'),('019','Oral and Maxillofacial Surgery','04'),('020','Orhtodontics','04'),('021','Periodontology','04'),('022','Pediatric Dentistry ','04'),('023','Prosthodontics','04'),('024','Hygienist','04'),('025','Allergy testing and shots (serum must be provided by your allergist)','05'),('026','Aspiration/incision of abscesses or cysts','05'),('027','Biopsy of skin lesions and moles','05'),('028','Burn treatment','05'),('029','Cryotherapy','05'),('030','Destruction of skin lesions','05'),('031','EKG testing and interpretation','05'),('032','Echocardiogram','05'),('033','Excision of moles for cosmetic or medical reasons','05'),('034','Holter Monitor','05'),('035','Hemorrhoid excision','05'),('036','Injection of medication','05'),('037','Injection of joints','05'),('038','Removal of ear wax','05'),('039','Removal of foreign bodies from ear, nose or skin','05'),('040','Skin biopsies','05'),('041','Shaving of corns or calluses','05'),('042','Spirometry','05'),('043','TB testing','05'),('044','Vaccine administration','05'),('045','Venipuncture blood testing','05'),('046','Wound repair (stitches)','05'),('047','Basic Life Support','06'),('048','Advanced Life Support','06'),('049','Plebotomy Services','07'),('050','Laboratory Services ','07'),('051','X Ray Services','08'),('052','ECG','08'),('053','EEG','08');
