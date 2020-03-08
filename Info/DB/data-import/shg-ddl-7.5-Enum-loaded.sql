CREATE DATABASE  IF NOT EXISTS `shg` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `shg`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: shg
-- ------------------------------------------------------
-- Server version	5.6.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account_status`
--

DROP TABLE IF EXISTS `account_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account_status` (
  `account_status_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `account_status` varchar(20) NOT NULL COMMENT '"PENDING","DONE","UNDONE","OVERDUE","MISSED","CANCELLED"',
  `account_status_desc` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`account_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_status`
--

LOCK TABLES `account_status` WRITE;
/*!40000 ALTER TABLE `account_status` DISABLE KEYS */;
INSERT INTO `account_status` VALUES (1,'Draft','Account is saved as Draft'),(2,'Submitted','Account request is Submitted'),(3,'Active','Account is Approved and Active'),(4,'Rejected','Account is Rejected'),(5,'Sub Standard','Account is Sub Standard, missed 2 or more times previous payments'),(6,'Bad Debt','Account is Bad Debt, missed 6 or more times previous payments'),(7,'Foreclosed','Account is Closed before expected completion date'),(8,'Bad Debt Closed','Account is Closed as Bad Debt without complete payment'),(9,'Complete','Account is Closed as Complete'),(10,'Under Review','Account is Under Review'),(11,'On Hold','Account is put On Hold for further consideration'),(12,'Sent Back','Account is Sent Back to resubmission after missing updates'),(13,'Approved','Account is Approved');
/*!40000 ALTER TABLE `account_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `active_status`
--

DROP TABLE IF EXISTS `active_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `active_status` (
  `active_status_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `active_status` varchar(20) NOT NULL,
  `active_status_desc` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`active_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `active_status`
--

LOCK TABLES `active_status` WRITE;
/*!40000 ALTER TABLE `active_status` DISABLE KEYS */;
INSERT INTO `active_status` VALUES (1,'Requested','Member account is Requested'),(2,'Active','Member account is Active'),(3,'Inactive','Member account is Inactive as after approval no activity is done on the account'),(4,'Idle','Member account is Idle as not activity is done for more than 2 Months'),(5,'Locked','Member account is Locked due to some reason'),(6,'Completed','Member account has Compeleted given specific duration'),(7,'Closed','Member account is Closed');
/*!40000 ALTER TABLE `active_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `advance_cheques`
--

DROP TABLE IF EXISTS `advance_cheques`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `advance_cheques` (
  `advance_cheques_id` int(10) unsigned NOT NULL,
  `bank_account_no` bigint(20) NOT NULL,
  `cheque_no` int(10) NOT NULL,
  `used` tinyint(1) DEFAULT '0',
  `deposit_ts` date DEFAULT NULL,
  `amount` decimal(16,2) DEFAULT NULL,
  PRIMARY KEY (`advance_cheques_id`),
  KEY `advance_cheques_fk1_idx` (`bank_account_no`),
  CONSTRAINT `advance_cheques_fk1` FOREIGN KEY (`bank_account_no`) REFERENCES `bank_account` (`bank_account_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `advance_cheques`
--

LOCK TABLES `advance_cheques` WRITE;
/*!40000 ALTER TABLE `advance_cheques` DISABLE KEYS */;
/*!40000 ALTER TABLE `advance_cheques` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `approval_status`
--

DROP TABLE IF EXISTS `approval_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `approval_status` (
  `approval_status_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `approval_status` varchar(20) NOT NULL,
  `approval_status_desc` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`approval_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `approval_status`
--

LOCK TABLES `approval_status` WRITE;
/*!40000 ALTER TABLE `approval_status` DISABLE KEYS */;
INSERT INTO `approval_status` VALUES (1,'Draft','Request is Saved as Draft and not Submitted'),(2,'Submitted','Request is Submitted'),(3,'Under Review','Request is Under Review'),(4,'Approved','Request is Approved'),(5,'Rejected','Request is Rejected as not satisfying criteria'),(6,'On Hold','Request is put On Hold for further consideration'),(7,'Sent Back','Request is Sent Back to resubmission after missing updates');
/*!40000 ALTER TABLE `approval_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bank_ac_upload`
--

DROP TABLE IF EXISTS `bank_ac_upload`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bank_ac_upload` (
  `upload_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bank_account_no` bigint(20) NOT NULL,
  `amount` decimal(16,2) DEFAULT NULL,
  `upload_date` date DEFAULT NULL,
  `file` mediumblob NOT NULL,
  PRIMARY KEY (`upload_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bank_ac_upload`
--

LOCK TABLES `bank_ac_upload` WRITE;
/*!40000 ALTER TABLE `bank_ac_upload` DISABLE KEYS */;
/*!40000 ALTER TABLE `bank_ac_upload` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bank_account`
--

DROP TABLE IF EXISTS `bank_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bank_account` (
  `bank_account_no` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_type_id` int(10) unsigned NOT NULL,
  `b_g_ac_no` bigint(20) NOT NULL,
  `account_name` varchar(50) DEFAULT NULL,
  `account_number` varchar(25) DEFAULT NULL,
  `attachment_url` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`bank_account_no`),
  KEY `bank_account_ibfk_2_idx` (`b_g_ac_no`),
  CONSTRAINT `bank_account_ibfk_2` FOREIGN KEY (`b_g_ac_no`) REFERENCES `bank_profile` (`g_ac_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bank_account`
--

LOCK TABLES `bank_account` WRITE;
/*!40000 ALTER TABLE `bank_account` DISABLE KEYS */;
/*!40000 ALTER TABLE `bank_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bank_account_type`
--

DROP TABLE IF EXISTS `bank_account_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bank_account_type` (
  `account_type_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `account_type` varchar(20) NOT NULL,
  `account_type_desc` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`account_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bank_account_type`
--

LOCK TABLES `bank_account_type` WRITE;
/*!40000 ALTER TABLE `bank_account_type` DISABLE KEYS */;
INSERT INTO `bank_account_type` VALUES (1,'Saving Account',''),(2,'Loan Account',''),(3,'Investment Account',''),(4,'Project Account',''),(5,'Current Account',''),(6,'Fix Deposit Account',''),(7,'Over Draft Account','');
/*!40000 ALTER TABLE `bank_account_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bank_profile`
--

DROP TABLE IF EXISTS `bank_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bank_profile` (
  `g_ac_no` bigint(20) NOT NULL,
  `ifcs_code` varchar(12) NOT NULL,
  `bank_name` varchar(40) NOT NULL,
  `branch_name` varchar(25) NOT NULL,
  `bank_loan_int_rate` float DEFAULT NULL,
  `bank_fd_int_rate` float DEFAULT NULL,
  `bank_rating` float DEFAULT NULL,
  `loan_processing_fee` int(10) DEFAULT NULL,
  `loan_application_charges` int(10) DEFAULT NULL,
  `loan_prepayment_charges` int(10) DEFAULT NULL,
  `late_payment_charges` int(10) DEFAULT NULL,
  `missed_payment_charges` int(10) DEFAULT NULL,
  KEY `bank_profile_ibfk_1_idx` (`g_ac_no`),
  CONSTRAINT `bank_profile_ibfk_1` FOREIGN KEY (`g_ac_no`) REFERENCES `g_profile` (`g_ac_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bank_profile`
--

LOCK TABLES `bank_profile` WRITE;
/*!40000 ALTER TABLE `bank_profile` DISABLE KEYS */;
/*!40000 ALTER TABLE `bank_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact` (
  `contact_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `district_id` int(10) unsigned NOT NULL,
  `address` varchar(100) DEFAULT NULL,
  `village` varchar(25) DEFAULT NULL,
  `grampanchayat` varchar(25) DEFAULT NULL,
  `taluka` varchar(25) DEFAULT NULL,
  `pin_code` varchar(8) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `pri_mobile` varchar(15) DEFAULT NULL,
  `sec_mobile` varchar(15) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`contact_id`),
  KEY `contact_FKIndex1` (`district_id`),
  CONSTRAINT `contact_ibfk_1` FOREIGN KEY (`district_id`) REFERENCES `district` (`district_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact`
--

LOCK TABLES `contact` WRITE;
/*!40000 ALTER TABLE `contact` DISABLE KEYS */;
/*!40000 ALTER TABLE `contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `district`
--

DROP TABLE IF EXISTS `district`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `district` (
  `district_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `state` varchar(20) NOT NULL,
  `division` varchar(20) DEFAULT NULL,
  `district` varchar(20) NOT NULL,
  `lang_id` int(10) unsigned NOT NULL,
  `district_code` char(5) DEFAULT NULL,
  `group_counter` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`district_id`),
  KEY `district_FKIndex1` (`lang_id`),
  CONSTRAINT `district_ibfk_1` FOREIGN KEY (`lang_id`) REFERENCES `lang` (`lang_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `district`
--

LOCK TABLES `district` WRITE;
/*!40000 ALTER TABLE `district` DISABLE KEYS */;
INSERT INTO `district` VALUES (1,'Admin','Admin','Super Admin',1,'AD01',0),(2,'Admin','TEST','TEST1',1,'TS01',0),(3,'Admin','TEST','TEST2',1,'TS02',0),(4,'Maharashtra','Nashik','Ahmednagar',1,'MH16',0),(5,'Maharashtra','Amravati','Akola',1,'MH30',0),(6,'Maharashtra','Amravati','Amravati',1,'MH27',0),(7,'Maharashtra','Aurangabad','Aurangabad',1,'MH20',0),(8,'Maharashtra','Aurangabad','Beed',1,'MH23',0),(9,'Maharashtra','Nagpur','Bhandara',1,'MH36',0),(10,'Maharashtra','Amravati','Buldhana',1,'MH28',0),(11,'Maharashtra','Nagpur','Chandrapur',1,'MH34',0),(12,'Maharashtra','Nashik','Dhule',1,'MH18',0),(13,'Maharashtra','Nagpur','Gadchiroli',1,'MH33',0),(14,'Maharashtra','Nagpur','Gondia',1,'MH35',0),(15,'Maharashtra','Aurangabad','Hingoli',1,'MH38',0),(16,'Maharashtra','Nashik','Jalgaon',1,'MH19',0),(17,'Maharashtra','Aurangabad','Jalna',1,'MH21',0),(18,'Maharashtra','Pune','Kolhapur',1,'MH09',0),(19,'Maharashtra','Aurangabad','Latur',1,'MH24',0),(20,'Maharashtra','Konkan','Mumbai City',1,'MH01',0),(21,'Maharashtra','Konkan','Mumbai Suburban',1,'MH05',0),(22,'Maharashtra','Nagpur','Nagpur',1,'MH31',0),(23,'Maharashtra','Aurangabad','Nanded',1,'MH26',0),(24,'Maharashtra','Nashik','Nandurbar',1,'MH39',0),(25,'Maharashtra','Nashik','Nashik',1,'MH15',0),(26,'Maharashtra','Aurangabad','Osmanabad',1,'MH25',0),(27,'Maharashtra','Aurangabad','Parbhani',1,'MH22',0),(28,'Maharashtra','Pune','Pune',1,'MH12',0),(29,'Maharashtra','Konkan','Raigad',1,'MH06',0),(30,'Maharashtra','Konkan','Ratnagiri',1,'MH08',0),(31,'Maharashtra','Pune','Sangli',1,'MH10',0),(32,'Maharashtra','Pune','Satara',1,'MH11',0),(33,'Maharashtra','Konkan','Sindhudurg',1,'MH07',0),(34,'Maharashtra','Pune','Solapur',1,'MH13',0),(35,'Maharashtra','Konkan','Thane',1,'MH04',0),(36,'Maharashtra','Nagpur','Wardha',1,'MH32',0),(37,'Maharashtra','Amravati','Washim',1,'MH37',0),(38,'Maharashtra','Amravati','Yavatmal',1,'MH29',0);
/*!40000 ALTER TABLE `district` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doc`
--

DROP TABLE IF EXISTS `doc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doc` (
  `doc_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `doc_type_id` int(10) unsigned NOT NULL,
  `file` mediumblob NOT NULL,
  `update_ts` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`doc_id`),
  KEY `FK_Index1_idx` (`doc_type_id`),
  CONSTRAINT `FK_Index1` FOREIGN KEY (`doc_type_id`) REFERENCES `doc_type` (`doc_type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doc`
--

LOCK TABLES `doc` WRITE;
/*!40000 ALTER TABLE `doc` DISABLE KEYS */;
/*!40000 ALTER TABLE `doc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doc_type`
--

DROP TABLE IF EXISTS `doc_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doc_type` (
  `doc_type_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `doc_type` varchar(50) NOT NULL,
  `doc_for` varchar(100) NOT NULL,
  `doc_category` varchar(100) NOT NULL,
  `doc_desc` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`doc_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doc_type`
--

LOCK TABLES `doc_type` WRITE;
/*!40000 ALTER TABLE `doc_type` DISABLE KEYS */;
INSERT INTO `doc_type` VALUES (1,'Signed Application','Member:Group:Loan','Application',''),(2,'Account Details','Group','Application',''),(3,'Water Bill','Member','Address Proof',''),(4,'Telephone Bill','Member','Address Proof',''),(5,'Electricity Bill','Member','Address Proof',''),(6,'Bank Statement','Member:Group','Address Proof',''),(7,'Election ID Card','Member','Address Proof:Identity Proof',''),(8,'Proof of Gas Connection','Member','Address Proof',''),(9,'Passport','Member','Address Proof:Identity Proof',''),(10,'Ration Card','Member','Address Proof',''),(11,'Aadhaar Card','Member','Address Proof:Identity Proof',''),(12,'PAN Card','Member','Address Proof:Identity Proof',''),(13,'Rent Agreement','Member','Address Proof',''),(14,'Development Plan','Loan','Development Plan',''),(15,'Bank Cheque','Bank Account','Bank Document',''),(16,'Bank Passbook','Bank Account','Bank Document',''),(17,'Bank Statement','Bank Account','Bank Document',''),(18,'Miscellaneous','Member:Group:Loan:Bank Account','Miscellaneous',''),(19,'SHG Declaration','Member:Group:Loan:Bank Account','SHG Document',''),(20,'SHG Accounts','Member:Group:Loan:Bank Account','SHG Document',''),(21,'SHG Form','Member:Group','Form',''),(22,'Member Form','Member:Group','Form','');
/*!40000 ALTER TABLE `doc_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fund_type`
--

DROP TABLE IF EXISTS `fund_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fund_type` (
  `fund_type_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `fund_type` varchar(35) NOT NULL,
  `fund_category` varchar(25) NOT NULL,
  `fund_type_desc` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`fund_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fund_type`
--

LOCK TABLES `fund_type` WRITE;
/*!40000 ALTER TABLE `fund_type` DISABLE KEYS */;
INSERT INTO `fund_type` VALUES (1,'Consumption Loan','Regular Recovery','Loan with lower limit for personal consumption in emergency'),(2,'Individual Development Fund','Regular Recovery','Fund for Individual Member Development with approved plan'),(3,'Project Development Fund','Regular Recovery','Fund for Project Development by multiple Members with approved plan'),(4,'Group Project Development Fund','None Regular Recovery','Project undertaken by the Group with no fixed Returns on Profit/Loss basis'),(5,'Socio Care Fund','No Recovery','Fund spend for Social cause by Group with no returns'),(6,'Revolving Fund','None Regular Recovery','Revolving Fund provided to t Group going and had tobe returned'),(7,'Gov Development Fund','No Recovery','Fund provided by Gov for Development and non recoverable');
/*!40000 ALTER TABLE `fund_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `g_ac`
--

DROP TABLE IF EXISTS `g_ac`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `g_ac` (
  `g_ac_no` bigint(20) NOT NULL,
  `credit_rating` float DEFAULT NULL,
  `approval_rating` float DEFAULT NULL,
  `meeting_attendance` float DEFAULT NULL,
  `c_m_saved_am` decimal(16,2) DEFAULT NULL,
  `c_m_planned_monthly_saving` decimal(16,2) DEFAULT NULL,
  `c_m_prov_int_en_am` decimal(16,2) DEFAULT NULL,
  `c_m_returned_saved_am` decimal(16,2) DEFAULT NULL,
  `c_m_returned_int_en_am` decimal(16,2) DEFAULT NULL,
  `c_m_profit_share_declared_am` decimal(16,2) DEFAULT NULL,
  `c_m_profit_share_paid_am` decimal(16,2) DEFAULT NULL,
  `c_m_loan_am` decimal(16,2) DEFAULT NULL,
  `c_m_rec_loan_am` decimal(16,2) DEFAULT NULL,
  `c_m_rec_int_on_loan_am` decimal(16,2) DEFAULT NULL,
  `c_m_proj_int_on_loan_am` decimal(16,2) DEFAULT NULL,
  `c_m_no_of_loans` int(10) DEFAULT NULL,
  `c_m_no_of_active_loans` int(10) DEFAULT NULL,
  `c_m_sub_std_dept_am` decimal(16,2) DEFAULT NULL,
  `c_m_no_of_sub_std_dept` int(10) DEFAULT NULL,
  `c_m_bad_dept_exp_am` decimal(16,2) DEFAULT NULL,
  `c_m_no_of_bad_dept_exp` int(10) DEFAULT NULL,
  `c_m_bad_dept_closed_am` decimal(16,2) DEFAULT NULL,
  `c_m_no_of_bad_dept_closed` int(10) DEFAULT NULL,
  `a_m_saved_am` decimal(16,2) DEFAULT NULL,
  `a_m_planned_monthly_saving` decimal(16,2) DEFAULT NULL,
  `a_m_prov_int_en_am` decimal(16,2) DEFAULT NULL,
  `a_m_returned_saved_am` decimal(16,2) DEFAULT NULL,
  `a_m_returned_int_en_am` decimal(16,2) DEFAULT NULL,
  `a_m_divided_declared_am` decimal(16,2) DEFAULT NULL,
  `a_m_divided_paid_am` decimal(16,2) DEFAULT NULL,
  `a_m_loan_am` decimal(16,2) DEFAULT NULL,
  `a_m_rec_loan_am` decimal(16,2) DEFAULT NULL,
  `a_m_rec_int_on_loan_am` decimal(16,2) DEFAULT NULL,
  `a_m_proj_int_on_loan_am` decimal(16,2) DEFAULT NULL,
  `a_m_no_of_loans` int(10) DEFAULT NULL,
  `a_m_no_of_active_loans` int(10) DEFAULT NULL,
  `a_m_sub_std_dept_am` decimal(16,2) DEFAULT NULL,
  `a_m_no_of_sub_std_dept` int(10) DEFAULT NULL,
  `a_m_bad_dept_exp_am` decimal(16,2) DEFAULT NULL,
  `a_m_no_of_bad_dept_exp` int(10) DEFAULT NULL,
  `a_m_bad_dept_closed_am` decimal(16,2) DEFAULT NULL,
  `a_m_no_of_bad_dept_closed` int(10) DEFAULT NULL,
  `p_loan_am` decimal(16,2) DEFAULT NULL,
  `p_rec_loan_am` decimal(16,2) DEFAULT NULL,
  `p_rec_int_on_loan_am` decimal(16,2) DEFAULT NULL,
  `p_proj_int_on_loan_am` decimal(16,2) DEFAULT NULL,
  `no_of_project` int(10) DEFAULT NULL,
  `no_of_active_project` int(10) DEFAULT NULL,
  `fix_deposit_inv_am` decimal(16,2) DEFAULT NULL,
  `rec_fix_deposit_am` decimal(16,2) DEFAULT NULL,
  `rec_int_on_fix_deposit_am` decimal(16,2) DEFAULT NULL,
  `proj_int_on_fix_deposit_am` decimal(16,2) DEFAULT NULL,
  `no_of_fix_deposit` int(10) DEFAULT NULL,
  `no_of_active_fix_deposit` int(10) DEFAULT NULL,
  `other_inv_am` decimal(16,2) DEFAULT NULL,
  `rec_other_inv_am` decimal(16,2) DEFAULT NULL,
  `rec_int_on_other_inv_am` decimal(16,2) DEFAULT NULL,
  `proj_int_on_other_inv_am` decimal(16,2) DEFAULT NULL,
  `no_of_other_inv` int(10) DEFAULT NULL,
  `no_of_active_other_inv` int(10) DEFAULT NULL,
  `int_en_on_saving_ac_am` decimal(16,2) DEFAULT NULL,
  `net_profit_am` decimal(16,2) DEFAULT NULL,
  `expenses_am` decimal(16,2) DEFAULT NULL,
  `rec_penalty_am` decimal(16,2) DEFAULT NULL,
  `pending_penalty_am` decimal(16,2) DEFAULT NULL,
  `borrowed_loan_am` decimal(16,2) DEFAULT NULL,
  `paid_borrowed_loan_am` decimal(16,2) DEFAULT NULL,
  `paid_int_on_borrowed_loan_am` decimal(16,2) DEFAULT NULL,
  `proj_int_on_borrowed_loan_am` decimal(16,2) DEFAULT NULL,
  `no_of_bank_loan` int(10) DEFAULT NULL,
  `no_of_active_bank_loan` int(10) DEFAULT NULL,
  `bank_sub_std_dept_am` decimal(16,2) DEFAULT NULL,
  `bank_no_of_sub_std_dept` int(10) DEFAULT NULL,
  `bank_bad_dept_exp_am` decimal(16,2) DEFAULT NULL,
  `bank_no_of_bad_dept_exp` int(10) DEFAULT NULL,
  `bank_bad_dept_closed_am` decimal(16,2) DEFAULT NULL,
  `bank_no_of_bad_dept_closed` int(10) DEFAULT NULL,
  `gov_revolving_fund_am` decimal(16,2) DEFAULT NULL,
  `gov_revolving_fund_returned_am` decimal(16,2) DEFAULT NULL,
  `no_of_gov_revolving_fund` int(10) DEFAULT NULL,
  `no_of_active_gov_revolving_fund` int(10) DEFAULT NULL,
  `gov_development_fund_am` decimal(16,2) DEFAULT NULL,
  `no_of_gov_development_fund` int(10) DEFAULT NULL,
  `pen_shg_one_mem_reg_fee` decimal(16,2) DEFAULT NULL,
  `pen_shg_one_service_charges` decimal(16,2) DEFAULT NULL,
  `clear_bank_balance_am` decimal(16,2) DEFAULT NULL,
  `subj_clearing_bank_balance_am` decimal(16,2) DEFAULT NULL,
  `clear_cash_in_hand_am` decimal(16,2) DEFAULT NULL,
  `subj_clearing_cash_in_hand_am` decimal(16,2) DEFAULT NULL,
  `no_of_txs_monthly_exp` int(10) DEFAULT NULL,
  `no_of_txs_monthly_done` int(10) DEFAULT NULL,
  `no_of_txs_monthly_approved` int(10) DEFAULT NULL,
  `last_updated_ts` timestamp NULL DEFAULT NULL,
  KEY `g_ac_FKIndex1` (`g_ac_no`),
  CONSTRAINT `g_ac_ibfk_1` FOREIGN KEY (`g_ac_no`) REFERENCES `g_profile` (`g_ac_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `g_ac`
--

LOCK TABLES `g_ac` WRITE;
/*!40000 ALTER TABLE `g_ac` DISABLE KEYS */;
/*!40000 ALTER TABLE `g_ac` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `g_ac_by_txtype`
--

DROP TABLE IF EXISTS `g_ac_by_txtype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `g_ac_by_txtype` (
  `g_ac_no` bigint(20) NOT NULL,
  `tx_type_id` int(10) unsigned NOT NULL,
  `amount` decimal(16,2) DEFAULT NULL,
  PRIMARY KEY (`g_ac_no`,`tx_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `g_ac_by_txtype`
--

LOCK TABLES `g_ac_by_txtype` WRITE;
/*!40000 ALTER TABLE `g_ac_by_txtype` DISABLE KEYS */;
/*!40000 ALTER TABLE `g_ac_by_txtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `g_bank_account`
--

DROP TABLE IF EXISTS `g_bank_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `g_bank_account` (
  `bank_account_no` bigint(20) NOT NULL,
  `g_ac_no` bigint(20) NOT NULL,
  `clear_balance_am` decimal(16,2) DEFAULT NULL,
  `subj_clearing_balance_am` decimal(16,2) DEFAULT NULL,
  `verified_balance_am` decimal(16,2) DEFAULT NULL,
  `interest_am` decimal(16,2) DEFAULT NULL,
  `penalty_charges_am` decimal(16,2) DEFAULT NULL,
  PRIMARY KEY (`bank_account_no`),
  KEY `g_bank_acount_FKIndex1` (`g_ac_no`),
  KEY `g_bank_acount_FKIndex2` (`bank_account_no`),
  CONSTRAINT `g_bank_account_ibfk_1` FOREIGN KEY (`g_ac_no`) REFERENCES `g_profile` (`g_ac_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `g_bank_account_ibfk_2` FOREIGN KEY (`bank_account_no`) REFERENCES `bank_account` (`bank_account_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `g_bank_account`
--

LOCK TABLES `g_bank_account` WRITE;
/*!40000 ALTER TABLE `g_bank_account` DISABLE KEYS */;
/*!40000 ALTER TABLE `g_bank_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `g_g`
--

DROP TABLE IF EXISTS `g_g`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `g_g` (
  `top_g_ac_no` bigint(20) NOT NULL,
  `down_g_ac_no` bigint(20) NOT NULL,
  `group_relation_id` int(10) unsigned NOT NULL,
  `ws_access_rights` varchar(45) DEFAULT NULL,
  `ui_access_rights` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`top_g_ac_no`,`down_g_ac_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `g_g`
--

LOCK TABLES `g_g` WRITE;
/*!40000 ALTER TABLE `g_g` DISABLE KEYS */;
/*!40000 ALTER TABLE `g_g` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `g_invt_ac`
--

DROP TABLE IF EXISTS `g_invt_ac`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `g_invt_ac` (
  `g_invt_ac_no` bigint(20) NOT NULL AUTO_INCREMENT,
  `g_ac_no` bigint(20) NOT NULL,
  `b_g_ac_no` bigint(20) NOT NULL,
  `invt_bank_ac_no` bigint(20) NOT NULL,
  `investment_type_id` int(10) unsigned NOT NULL,
  `account_status_id` int(10) unsigned NOT NULL,
  `investment_no` varchar(20) DEFAULT NULL,
  `investment_ac_name` varchar(50) DEFAULT NULL,
  `investment_desc` varchar(200) DEFAULT NULL,
  `approved_by_m` bigint(20) DEFAULT NULL,
  `invt_am` decimal(16,2) DEFAULT NULL,
  `rec_invt_am` decimal(16,2) DEFAULT NULL,
  `rec_interest_am` decimal(16,2) DEFAULT NULL,
  `proj_interest_am` decimal(16,2) DEFAULT NULL,
  `interest_rate` float DEFAULT NULL,
  `requested_date` date DEFAULT NULL,
  `approved_date` date DEFAULT NULL,
  `maturity_date` date DEFAULT NULL,
  `closure_date` date DEFAULT NULL,
  `attachment_url` varchar(500) DEFAULT NULL,
  `development_plan` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`g_invt_ac_no`),
  KEY `g_invt_ac_ibfk_1_idx` (`g_ac_no`),
  CONSTRAINT `g_invt_ac_ibfk_1` FOREIGN KEY (`g_ac_no`) REFERENCES `g_ac` (`g_ac_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `g_invt_ac`
--

LOCK TABLES `g_invt_ac` WRITE;
/*!40000 ALTER TABLE `g_invt_ac` DISABLE KEYS */;
/*!40000 ALTER TABLE `g_invt_ac` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `g_loan_ac`
--

DROP TABLE IF EXISTS `g_loan_ac`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `g_loan_ac` (
  `g_loan_ac_no` bigint(20) NOT NULL AUTO_INCREMENT,
  `g_ac_no` bigint(20) NOT NULL,
  `b_g_ac_no` bigint(20) NOT NULL,
  `loan_bank_ac_no` bigint(20) NOT NULL,
  `recovery_period_id` int(10) unsigned NOT NULL,
  `fund_type_id` int(10) unsigned NOT NULL,
  `loan_calculation_id` int(10) unsigned NOT NULL,
  `account_status_id` int(10) unsigned NOT NULL,
  `loan_source_id` int(10) unsigned NOT NULL,
  `loan_ac_name` varchar(50) DEFAULT NULL,
  `approved_by_m` bigint(20) DEFAULT NULL,
  `principle_am` decimal(16,2) DEFAULT NULL,
  `pending_principle_am` decimal(16,2) DEFAULT NULL,
  `paid_interest_am` decimal(16,2) DEFAULT NULL,
  `proj_interest_am` decimal(16,2) DEFAULT NULL,
  `installment_am` decimal(16,2) DEFAULT NULL,
  `pre_emi_interest_am` decimal(16,2) DEFAULT NULL,
  `pending_interest_due_am` decimal(16,2) DEFAULT NULL,
  `loan_processing_fee` decimal(16,2) DEFAULT NULL,
  `other_fee` decimal(16,2) DEFAULT NULL,
  `interest_rate` float DEFAULT NULL,
  `exp_no_of_inst` int(10) unsigned DEFAULT NULL,
  `startup_no_of_inst` int(10) DEFAULT NULL,
  `no_of_inst_paid` int(10) unsigned DEFAULT NULL,
  `no_of_insall_missed` int(10) DEFAULT NULL,
  `no_of_insall_late` int(10) DEFAULT NULL,
  `requested_date` date DEFAULT NULL,
  `approved_date` date DEFAULT NULL,
  `disbursement_date` date DEFAULT NULL,
  `inst_start_date` date DEFAULT NULL,
  `exp_completion_date` date DEFAULT NULL,
  `closure_date` date DEFAULT NULL,
  `development_plan` varchar(500) DEFAULT NULL,
  `attachment_url` varchar(500) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`g_loan_ac_no`),
  KEY `g_loan_ac_ibfk_1_idx` (`g_ac_no`),
  CONSTRAINT `g_loan_ac_ibfk_1` FOREIGN KEY (`g_ac_no`) REFERENCES `g_ac` (`g_ac_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `g_loan_ac`
--

LOCK TABLES `g_loan_ac` WRITE;
/*!40000 ALTER TABLE `g_loan_ac` DISABLE KEYS */;
/*!40000 ALTER TABLE `g_loan_ac` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `g_m`
--

DROP TABLE IF EXISTS `g_m`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `g_m` (
  `g_ac_no` bigint(20) NOT NULL,
  `m_ac_no` bigint(20) NOT NULL,
  `ws_access_rights` bigint(20) DEFAULT NULL,
  `ui_access_rights` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`g_ac_no`,`m_ac_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `g_m`
--

LOCK TABLES `g_m` WRITE;
/*!40000 ALTER TABLE `g_m` DISABLE KEYS */;
/*!40000 ALTER TABLE `g_m` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `g_profile`
--

DROP TABLE IF EXISTS `g_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `g_profile` (
  `g_ac_no` bigint(20) NOT NULL,
  `approval_status_id` int(10) unsigned NOT NULL,
  `active_status_id` int(10) unsigned NOT NULL,
  `group_type_id` int(10) unsigned NOT NULL,
  `approved_by_m` bigint(20) DEFAULT NULL,
  `member_counter` int(10) unsigned zerofill DEFAULT NULL,
  `percentage_pro_comp` float DEFAULT NULL,
  `active_child_groups` int(10) DEFAULT NULL,
  `active_core_members` int(10) unsigned zerofill DEFAULT NULL,
  `active_associate_members` int(10) unsigned zerofill DEFAULT NULL,
  `no_of_top_g` int(10) unsigned zerofill DEFAULT NULL,
  `no_of_down_g` int(10) unsigned zerofill DEFAULT NULL,
  `registration_no` varchar(15) DEFAULT NULL,
  `profile_code` blob,
  `last_logged_in_ts` timestamp NULL DEFAULT NULL,
  `establishment_date` date DEFAULT NULL,
  `approval_date` date DEFAULT NULL,
  `date_of_registration` date DEFAULT NULL,
  `logo_url` varchar(100) DEFAULT NULL,
  `attachment_url` varchar(500) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `location` varchar(100) DEFAULT NULL,
  `error_messages` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`g_ac_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `g_profile`
--

LOCK TABLES `g_profile` WRITE;
/*!40000 ALTER TABLE `g_profile` DISABLE KEYS */;
/*!40000 ALTER TABLE `g_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `g_rules`
--

DROP TABLE IF EXISTS `g_rules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `g_rules` (
  `g_ac_no` bigint(20) NOT NULL,
  `max_no_of_core_members` smallint(5) unsigned DEFAULT NULL,
  `allow_associate_members` smallint(5) unsigned DEFAULT NULL,
  `cm_monthly_saving` int(10) unsigned DEFAULT NULL,
  `am_min_monthly_saving` int(10) unsigned DEFAULT NULL,
  `am_max_monthly_saving` int(10) unsigned DEFAULT NULL,
  `cm_registration_fee` smallint(5) unsigned DEFAULT NULL,
  `am_registration_fee` smallint(5) unsigned DEFAULT NULL,
  `cm_saving_late_fee` smallint(5) unsigned DEFAULT NULL,
  `am_saving_late_fee` smallint(5) unsigned DEFAULT NULL,
  `cm_loan_late_fee` smallint(5) unsigned DEFAULT NULL,
  `am_loan_late_fee` smallint(5) unsigned DEFAULT NULL,
  `cheque_bouncing_penalty` smallint(5) unsigned DEFAULT NULL,
  `auto_debit_failure_penalty` smallint(5) unsigned DEFAULT NULL,
  `cm_application_fee` smallint(5) unsigned DEFAULT NULL,
  `am_application_fee` smallint(5) unsigned DEFAULT NULL,
  `cm_loan_processing_fee_percent` float DEFAULT NULL,
  `am_loan_processing_fee_percent` float DEFAULT NULL,
  `cm_int_on_saving` float DEFAULT NULL,
  `am_int_on_saving` float DEFAULT NULL,
  `add_int_to_saving_after_months` int(10) unsigned DEFAULT NULL,
  `cm_base_int_on_loan` float DEFAULT NULL,
  `am_base_int_on_loan` float DEFAULT NULL,
  `int_on_consumption_loan` float DEFAULT NULL,
  `individual_dev_loan_range1` int(10) unsigned DEFAULT NULL,
  `individual_dev_loan_range2` int(10) unsigned DEFAULT NULL,
  `individual_dev_loan_range3` int(10) unsigned DEFAULT NULL,
  `int_on_individual_dev_loan_range1` float DEFAULT NULL,
  `int_on_individual_dev_loan_range2` float DEFAULT NULL,
  `int_on_individual_dev_loan_range3` float DEFAULT NULL,
  `int_on_individual_dev_loan_range4` float DEFAULT NULL,
  `project_dev_loan_range1` int(10) unsigned DEFAULT NULL,
  `project_dev_loan_range2` int(10) unsigned DEFAULT NULL,
  `project_dev_loan_range3` int(10) unsigned DEFAULT NULL,
  `int_on_project_dev_loan_range1` float DEFAULT NULL,
  `int_on_project_dev_loan_range2` float DEFAULT NULL,
  `int_on_project_dev_loan_range3` float DEFAULT NULL,
  `int_on_project_dev_loan_range4` float DEFAULT NULL,
  `cm_limit_on_consumption_loan` int(10) unsigned DEFAULT NULL,
  `am_limit_on_consumption_loan` int(10) unsigned DEFAULT NULL,
  `cm_min_months_to_con_loan` smallint(5) unsigned DEFAULT NULL,
  `am_min_months_to_con_loan` smallint(5) unsigned DEFAULT NULL,
  `cm_saving_x_consumption_loan` int(10) unsigned DEFAULT NULL,
  `am_saving_x_consumption_loan` int(10) unsigned DEFAULT NULL,
  `cm_min_months_to_dev_loan` smallint(5) unsigned DEFAULT NULL,
  `am_min_months_to_dev_loan` smallint(5) unsigned DEFAULT NULL,
  `cm_saving_x_dev_loan` float DEFAULT NULL,
  `am_saving_x_dev_loan` float unsigned DEFAULT NULL,
  `cm_min_months_int_on_loan` smallint(5) unsigned DEFAULT NULL,
  `am_min_months_int_on_loan` smallint(5) unsigned DEFAULT NULL,
  `credit_rating_loan_pass_above` float DEFAULT NULL,
  `credit_rating_loan_causion_above` float DEFAULT NULL,
  `credit_rating_loan_dengerous_above` float DEFAULT NULL,
  `compute_day_of_month` smallint(5) unsigned DEFAULT NULL,
  `due_day_of_month` smallint(5) unsigned DEFAULT NULL,
  `sms_sub_key` bigint(20) unsigned DEFAULT NULL,
  `mail_sub_key` bigint(20) unsigned DEFAULT NULL,
  `report_printing_service` bigint(20) unsigned DEFAULT NULL,
  `sms_service_lang_id` int(10) unsigned NOT NULL,
  `mail_service_lang_id` int(10) unsigned NOT NULL,
  `report_printing_service_lang_id` int(10) unsigned NOT NULL,
  `activation_flags` bigint(20) DEFAULT NULL,
  `shg_one_cm_reg_charge` smallint(5) unsigned DEFAULT NULL,
  `shg_one_am_reg_charge` smallint(5) unsigned DEFAULT NULL,
  `shg_one_cm_loan_pro_fee_percent` float DEFAULT NULL,
  `shg_one_am_loan_pro_fee_percent` float DEFAULT NULL,
  `shg_one_min_annual_service_charge` smallint(5) unsigned DEFAULT NULL,
  `shg_one_max_loan_pro_fee_off` smallint(5) unsigned DEFAULT NULL,
  `shg_one_billing_cycle_in_months` smallint(5) unsigned DEFAULT NULL,
  KEY `g_rules_fk1_idx` (`g_ac_no`),
  CONSTRAINT `g_rules_fk1` FOREIGN KEY (`g_ac_no`) REFERENCES `g_profile` (`g_ac_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `g_rules`
--

LOCK TABLES `g_rules` WRITE;
/*!40000 ALTER TABLE `g_rules` DISABLE KEYS */;
/*!40000 ALTER TABLE `g_rules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_contact`
--

DROP TABLE IF EXISTS `group_contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_contact` (
  `contact_id` bigint(20) NOT NULL,
  `g_ac_no` bigint(20) NOT NULL,
  `lang_id` int(10) unsigned NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `vision` varchar(500) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`contact_id`),
  KEY `group_contact_FKIndex1` (`contact_id`),
  KEY `group_contact_FKIndex2` (`g_ac_no`),
  KEY `group_contact_FKIndex3` (`lang_id`),
  CONSTRAINT `group_contact_ibfk_1` FOREIGN KEY (`contact_id`) REFERENCES `contact` (`contact_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `group_contact_ibfk_2` FOREIGN KEY (`lang_id`) REFERENCES `lang` (`lang_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `group_contact_ibfk_3` FOREIGN KEY (`g_ac_no`) REFERENCES `g_profile` (`g_ac_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_contact`
--

LOCK TABLES `group_contact` WRITE;
/*!40000 ALTER TABLE `group_contact` DISABLE KEYS */;
/*!40000 ALTER TABLE `group_contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_relation`
--

DROP TABLE IF EXISTS `group_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_relation` (
  `group_relation_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `group_relation` varchar(20) NOT NULL,
  `group_relation_desc` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`group_relation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_relation`
--

LOCK TABLES `group_relation` WRITE;
/*!40000 ALTER TABLE `group_relation` DISABLE KEYS */;
/*!40000 ALTER TABLE `group_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_type`
--

DROP TABLE IF EXISTS `group_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_type` (
  `group_type_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `group_type` varchar(20) NOT NULL,
  `category` varchar(20) DEFAULT NULL,
  `default_ws_access_rights` bigint(20) DEFAULT NULL,
  `default_ui_access_rights` bigint(20) DEFAULT NULL,
  `group_type_desc` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`group_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_type`
--

LOCK TABLES `group_type` WRITE;
/*!40000 ALTER TABLE `group_type` DISABLE KEYS */;
INSERT INTO `group_type` VALUES (1,'SHG','SHG',0,0,'Self Help Group'),(2,'Federation','Federation',849510040337985,388814799371841,'Federation, Group of Multiple Self Help Group'),(3,'NGO','NGO',849510040337985,388814799371841,'NGO which helps monitor multiple SHG'),(4,'Bank','Bank',5076651343937,78634811457,'Bank which monitor multiple SHG having accounts'),(5,'GOV','GOV',5076651343937,78634811457,'Goverment Entity which monitor multiple SHG having accounts'),(6,'SHG-One Agent','Agent',0,0,'SHG-One Agent Group'),(7,'Area Admin','Admin',0,0,'SHG-One Area Admin Group'),(8,'Super Area Admin','Admin',0,0,'SHG-One Super Area Admin Group'),(9,'SHG-One Admin','Admin',0,0,'SHG-One Admin Group');
/*!40000 ALTER TABLE `group_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `investment_type`
--

DROP TABLE IF EXISTS `investment_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `investment_type` (
  `investment_type_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `investment_type` varchar(30) NOT NULL,
  `investment_type_desc` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`investment_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `investment_type`
--

LOCK TABLES `investment_type` WRITE;
/*!40000 ALTER TABLE `investment_type` DISABLE KEYS */;
INSERT INTO `investment_type` VALUES (1,'Project Development','Investments in Project Development'),(2,'Fix Deposit','Investments in Fix Deposit'),(3,'Investment Account','Investment Account'),(4,'Over Draft Account','Over Draft Account with Bank'),(5,'Mutual Fund','Investments in Mutual Fund');
/*!40000 ALTER TABLE `investment_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lang`
--

DROP TABLE IF EXISTS `lang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lang` (
  `lang_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `lang` varchar(15) NOT NULL,
  `lang_desc` varchar(100) NOT NULL,
  PRIMARY KEY (`lang_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lang`
--

LOCK TABLES `lang` WRITE;
/*!40000 ALTER TABLE `lang` DISABLE KEYS */;
INSERT INTO `lang` VALUES (1,'English',''),(2,'Hindi',''),(3,'Marathi',''),(4,'Bengali',''),(5,'Gujarati',''),(6,'Kannada',''),(7,'Tamil',''),(8,'Telugu',''),(9,'Malayalam',''),(10,'Punjabi',''),(11,'Urdu','');
/*!40000 ALTER TABLE `lang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `language_massage`
--

DROP TABLE IF EXISTS `language_massage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `language_massage` (
  `massage_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `lang_id` int(10) unsigned NOT NULL,
  `massage_key` varchar(45) DEFAULT NULL,
  `massage` varchar(100) DEFAULT NULL,
  `massage_desc` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`massage_id`,`lang_id`),
  KEY `fk_language_massage_lang1_idx` (`lang_id`),
  CONSTRAINT `fk_language_massage_lang1` FOREIGN KEY (`lang_id`) REFERENCES `lang` (`lang_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `language_massage`
--

LOCK TABLES `language_massage` WRITE;
/*!40000 ALTER TABLE `language_massage` DISABLE KEYS */;
/*!40000 ALTER TABLE `language_massage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loan_calculation`
--

DROP TABLE IF EXISTS `loan_calculation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `loan_calculation` (
  `loan_calculation_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `loan_calculation` varchar(30) NOT NULL,
  `loan_calculation_desc` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`loan_calculation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loan_calculation`
--

LOCK TABLES `loan_calculation` WRITE;
/*!40000 ALTER TABLE `loan_calculation` DISABLE KEYS */;
INSERT INTO `loan_calculation` VALUES (1,'Reducing Interest','Reducing Interest calculated for the tenure given'),(2,'Normal EMI','Normal EMI calculated for the tenure given'),(3,'Fixed EMI','Fixed given EMI based calculation for the Monthly Fixed EMI');
/*!40000 ALTER TABLE `loan_calculation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loan_source`
--

DROP TABLE IF EXISTS `loan_source`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `loan_source` (
  `loan_source_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `loan_source` varchar(20) NOT NULL,
  `loan_source_desc` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`loan_source_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loan_source`
--

LOCK TABLES `loan_source` WRITE;
/*!40000 ALTER TABLE `loan_source` DISABLE KEYS */;
INSERT INTO `loan_source` VALUES (1,'SHG','Loan amount is provided by SHG'),(2,'BANK','Loan amount is provided by BANK'),(3,'PARENT SHG','Loan amount is provided by PARENT SHG'),(4,'OTHER SHG','Loan amount is provided by OTHER SHG'),(5,'MICRO FINANCE','Loan amount is provided by MICRO FINANCE');
/*!40000 ALTER TABLE `loan_source` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_ac`
--

DROP TABLE IF EXISTS `m_ac`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_ac` (
  `m_ac_no` bigint(20) NOT NULL,
  `credit_rating` float DEFAULT NULL,
  `saved_am` decimal(16,2) DEFAULT NULL,
  `prov_int_en_am` decimal(16,2) DEFAULT NULL,
  `returned_saved_am` decimal(16,2) DEFAULT NULL,
  `returned_int_en_am` decimal(16,2) DEFAULT NULL,
  `divided_profit_declared_am` decimal(16,2) DEFAULT NULL,
  `divided_profit_paid_am` decimal(16,2) DEFAULT NULL,
  `loan_am` decimal(16,2) DEFAULT NULL,
  `rec_loan_am` decimal(16,2) DEFAULT NULL,
  `rec_int_on_loan_am` decimal(16,2) DEFAULT NULL,
  `proj_int_on_loan_am` decimal(16,2) DEFAULT NULL,
  `bad_dept_closed_am` decimal(16,2) DEFAULT NULL,
  `rec_penalty_am` decimal(16,2) DEFAULT NULL,
  `pending_penalty_am` decimal(16,2) DEFAULT NULL,
  `last_updated_ts` timestamp NULL DEFAULT NULL,
  `planned_monthly_saving_am` decimal(16,2) DEFAULT NULL,
  `prev_month_int_en_am` decimal(16,2) DEFAULT NULL,
  `no_of_loans` int(10) DEFAULT NULL,
  `no_of_active_loans` int(10) DEFAULT NULL,
  `meeting_attendance` float DEFAULT NULL,
  `meeting_attended` int(10) DEFAULT NULL,
  `meeting_missed` int(10) DEFAULT NULL,
  PRIMARY KEY (`m_ac_no`),
  KEY `m_ac_FKIndex1` (`m_ac_no`),
  CONSTRAINT `m_ac_ibfk_1` FOREIGN KEY (`m_ac_no`) REFERENCES `m_profile` (`m_ac_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_ac`
--

LOCK TABLES `m_ac` WRITE;
/*!40000 ALTER TABLE `m_ac` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_ac` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_bank_account`
--

DROP TABLE IF EXISTS `m_bank_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_bank_account` (
  `bank_account_no` bigint(20) NOT NULL,
  `m_ac_no` bigint(20) NOT NULL,
  PRIMARY KEY (`bank_account_no`),
  KEY `m_bank_account_FKIndex1` (`m_ac_no`),
  KEY `m_bank_account_FKIndex2` (`bank_account_no`),
  CONSTRAINT `m_bank_account_ibfk_1` FOREIGN KEY (`m_ac_no`) REFERENCES `m_profile` (`m_ac_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `m_bank_account_ibfk_2` FOREIGN KEY (`bank_account_no`) REFERENCES `bank_account` (`bank_account_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_bank_account`
--

LOCK TABLES `m_bank_account` WRITE;
/*!40000 ALTER TABLE `m_bank_account` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_bank_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_loan_ac`
--

DROP TABLE IF EXISTS `m_loan_ac`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_loan_ac` (
  `m_loan_ac_no` bigint(20) NOT NULL AUTO_INCREMENT,
  `m_ac_no` bigint(20) NOT NULL,
  `recovery_period_id` int(10) unsigned NOT NULL,
  `fund_type_id` int(10) unsigned NOT NULL,
  `loan_calculation_id` int(10) unsigned NOT NULL,
  `account_status_id` int(10) unsigned NOT NULL,
  `loan_source_id` int(10) unsigned NOT NULL,
  `approved_by_m` bigint(20) DEFAULT NULL,
  `principle_am` decimal(16,2) DEFAULT NULL,
  `pending_principle_am` decimal(16,2) DEFAULT NULL,
  `rec_interest_am` decimal(16,2) DEFAULT NULL,
  `proj_interest_am` decimal(16,2) DEFAULT NULL,
  `installment_am` decimal(16,2) DEFAULT NULL,
  `pre_emi_interest_am` decimal(16,2) DEFAULT NULL,
  `pending_interest_due_am` decimal(16,2) DEFAULT NULL,
  `rate_of_interest` float DEFAULT NULL,
  `startup_no_of_inst` int(10) unsigned DEFAULT NULL,
  `exp_no_of_inst` int(10) unsigned DEFAULT NULL,
  `no_of_inst_paid` int(10) unsigned DEFAULT NULL,
  `no_of_insall_late` int(10) unsigned DEFAULT NULL,
  `no_of_insall_missed` int(10) unsigned DEFAULT NULL,
  `requested_date` date DEFAULT NULL,
  `approved_date` date DEFAULT NULL,
  `disbursement_date` date DEFAULT NULL,
  `inst_start_date` date DEFAULT NULL,
  `exp_completion_date` date DEFAULT NULL,
  `closure_date` date DEFAULT NULL,
  `development_plan` varchar(500) DEFAULT NULL,
  `attachment_url` varchar(500) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`m_loan_ac_no`),
  KEY `m_loan_ac_ibfk_3_idx` (`m_ac_no`),
  CONSTRAINT `m_loan_ac_ibfk_1` FOREIGN KEY (`m_ac_no`) REFERENCES `m_ac` (`m_ac_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_loan_ac`
--

LOCK TABLES `m_loan_ac` WRITE;
/*!40000 ALTER TABLE `m_loan_ac` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_loan_ac` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_message`
--

DROP TABLE IF EXISTS `m_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_message` (
  `m_ac_no` bigint(20) NOT NULL,
  `m_role_id` int(10) unsigned NOT NULL,
  `group_type_id` int(10) unsigned NOT NULL,
  `mobile_no` bigint(20) unsigned DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `mobile_verified` int(10) DEFAULT NULL,
  `email_verified` int(10) DEFAULT NULL,
  `sms_sub_key` bigint(20) DEFAULT NULL,
  `email_sub_key` bigint(20) DEFAULT NULL,
  `last_updated` date DEFAULT NULL,
  PRIMARY KEY (`m_ac_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_message`
--

LOCK TABLES `m_message` WRITE;
/*!40000 ALTER TABLE `m_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_profile`
--

DROP TABLE IF EXISTS `m_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_profile` (
  `m_ac_no` bigint(20) NOT NULL,
  `m_role_id` int(10) unsigned NOT NULL,
  `approval_status_id` int(10) unsigned NOT NULL,
  `active_status_id` int(10) unsigned NOT NULL,
  `passcode` varchar(20) DEFAULT NULL,
  `pass_set` tinyint(4) DEFAULT NULL,
  `profile_code` blob,
  `date_of_enroll` date DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `date_of_anni` date DEFAULT NULL,
  `date_of_closure` date DEFAULT NULL,
  `last_logged_in_ts` timestamp NULL DEFAULT NULL,
  `approved_by_m` bigint(20) DEFAULT NULL,
  `reco_by_m` bigint(20) DEFAULT NULL,
  `gender` enum('Male','Female','Unknow') DEFAULT NULL,
  `percentage_pro_comp` float DEFAULT NULL,
  `uidai_no` varchar(20) DEFAULT NULL,
  `pan_card_no` varchar(20) DEFAULT NULL,
  `voter_id_no` varchar(20) DEFAULT NULL,
  `driving_license_no` varchar(20) DEFAULT NULL,
  `photo_url` varchar(100) DEFAULT NULL,
  `attachment_url` varchar(500) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `location` varchar(100) DEFAULT NULL,
  `error_messages` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`m_ac_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_profile`
--

LOCK TABLES `m_profile` WRITE;
/*!40000 ALTER TABLE `m_profile` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_role`
--

DROP TABLE IF EXISTS `m_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_role` (
  `m_role_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `m_role` varchar(30) NOT NULL,
  `m_role_category` varchar(20) NOT NULL,
  `belong_to` varchar(20) NOT NULL,
  `m_role_desc` varchar(100) DEFAULT NULL,
  `default_ws_access_rights` bigint(20) DEFAULT NULL,
  `default_ui_access_rights` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`m_role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_role`
--

LOCK TABLES `m_role` WRITE;
/*!40000 ALTER TABLE `m_role` DISABLE KEYS */;
INSERT INTO `m_role` VALUES (1,'Associate Member','SHG Member','SHG','Associate Member of the Group for a fix duration',4466900210185,68853694985),(2,'Core Member','SHG Member','SHG','Core forunder Member of the Group',4466900210249,68853695049),(3,'Group Accountant','Accountant','SHG','Non Member working as Accountant for a Group',5045915596353,630746625601),(4,'Group Secretary','SHG Member','SHG','Secretary of the Group',5045915858505,630746625609),(5,'Group Treasure','SHG Member','SHG','Treasure of the Group',5085107450441,687194617417),(6,'Group President','SHG Member','SHG','President of the Group',5085107581513,687194766921),(7,'NGO Agent','NGO','NGO','Agent from NGO Group which can manage Multiple Groups',849510040337985,388814799371841),(8,'NGO Support Admin','NGO','NGO','Support Admin from NGO Group which can manage Multiple Groups',955063220567617,16747761114283585),(9,'NGO Admin','NGO','NGO','Super Admin form NGO Group which can manage Multiple Groups',2221700682874433,18014398509481537),(10,'Bank Auditor','Auditor','Bank','Auditor from Bank which can monitor Multiple Groups',5076651343937,78634811457),(11,'Area Agent','Agent','SHG-One Agent','SHG-One Area Agent which can manage Multiple Groups in perticular Area',955063157653065,404620279021129),(12,'Area Support Admin','Admin','Area Admin','SHG-One Area Support Admin which can manage Multiple Groups in perticular Area',8977100123930185,61766165201944137),(13,'Area Admin','Admin','Area Admin','SHG-One Area Super Admin which can manage Multiple Groups in perticular Area',8977100123930185,144115188075855433),(14,'Super Area Support Admin','Admin','Super Area Admin','SHG-One Area Support Admin which can manage Multiple Groups in perticular Area',8977100123930185,61766165201944137),(15,'Super Area Admin','Admin','Super Area Admin','SHG-One Area Super Admin which can manage Multiple Groups in perticular Area',8977100123930185,144115188075855433),(16,'SHG-One Admin','Admin','SHG-One Admin','SHG-One Admin which can manage Groups, Auditor Groups & SHG-One Area Admins in all Areas',17984299378671177,144115188075855425),(17,'Super Admin','Admin','SHG-One Admin','SHG-One Super Admin',9223372036854775807,9223372036854775807);
/*!40000 ALTER TABLE `m_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_saving_ac`
--

DROP TABLE IF EXISTS `m_saving_ac`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_saving_ac` (
  `m_saving_ac_no` bigint(20) NOT NULL AUTO_INCREMENT,
  `m_ac_no` bigint(20) NOT NULL,
  `recovery_period_id` int(10) unsigned NOT NULL,
  `account_status_id` int(10) unsigned NOT NULL,
  `saved_am` decimal(16,2) DEFAULT NULL,
  `cumulative_saved_am` decimal(16,2) DEFAULT NULL,
  `saving_installment_am` decimal(16,2) DEFAULT NULL,
  `total_int_en_am` decimal(16,2) DEFAULT NULL,
  `current_fy_int_en_am` decimal(16,2) DEFAULT NULL,
  `prev_month_int_am` decimal(16,2) DEFAULT NULL,
  `returned_saved_am` decimal(16,2) DEFAULT NULL,
  `returned_int_en_am` decimal(16,2) DEFAULT NULL,
  `exp_no_of_inst` int(10) unsigned DEFAULT NULL,
  `no_of_inst_paid` int(10) unsigned DEFAULT NULL,
  `no_of_insall_late` int(10) unsigned DEFAULT NULL,
  `no_of_insall_missed` int(10) unsigned DEFAULT NULL,
  `requested_date` date DEFAULT NULL,
  `approved_date` date DEFAULT NULL,
  `actual_start_date` date DEFAULT NULL,
  `inst_start_date` date DEFAULT NULL,
  `exp_completion_date` date DEFAULT NULL,
  `closure_date` date DEFAULT NULL,
  PRIMARY KEY (`m_saving_ac_no`),
  KEY `m_saving_ac_FKIndex3` (`m_ac_no`),
  CONSTRAINT `m_saving_ac_ibfk_1` FOREIGN KEY (`m_ac_no`) REFERENCES `m_ac` (`m_ac_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_saving_ac`
--

LOCK TABLES `m_saving_ac` WRITE;
/*!40000 ALTER TABLE `m_saving_ac` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_saving_ac` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member_contact`
--

DROP TABLE IF EXISTS `member_contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `member_contact` (
  `contact_id` bigint(20) NOT NULL,
  `m_ac_no` bigint(20) NOT NULL,
  `lang_id` int(10) unsigned NOT NULL,
  `title_id` int(10) unsigned DEFAULT NULL,
  `first_name` varchar(20) DEFAULT NULL,
  `middle_name` varchar(20) DEFAULT NULL,
  `last_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`contact_id`),
  KEY `member_contact_FKIndex1` (`contact_id`),
  KEY `member_contact_FKIndex2` (`title_id`),
  KEY `member_contact_FKIndex3` (`lang_id`),
  KEY `member_contact_FKIndex4` (`m_ac_no`),
  CONSTRAINT `member_contact_ibfk_1` FOREIGN KEY (`contact_id`) REFERENCES `contact` (`contact_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `member_contact_ibfk_2` FOREIGN KEY (`title_id`) REFERENCES `name_title` (`title_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `member_contact_ibfk_3` FOREIGN KEY (`lang_id`) REFERENCES `lang` (`lang_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `member_contact_ibfk_4` FOREIGN KEY (`m_ac_no`) REFERENCES `m_profile` (`m_ac_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member_contact`
--

LOCK TABLES `member_contact` WRITE;
/*!40000 ALTER TABLE `member_contact` DISABLE KEYS */;
/*!40000 ALTER TABLE `member_contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_type`
--

DROP TABLE IF EXISTS `message_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_type` (
  `message_type_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `message_type` varchar(30) DEFAULT NULL,
  `sms_format` varchar(200) DEFAULT NULL,
  `email_format` varchar(500) DEFAULT NULL,
  `pass_key` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`message_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_type`
--

LOCK TABLES `message_type` WRITE;
/*!40000 ALTER TABLE `message_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `message_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mobile_m`
--

DROP TABLE IF EXISTS `mobile_m`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mobile_m` (
  `mobile_no` bigint(20) unsigned NOT NULL,
  `m_ac_no` bigint(20) NOT NULL COMMENT '"PENDING","DONE","UNDONE","OVERDUE","MISSED","CANCELLED"',
  `m_role_id` int(10) unsigned NOT NULL,
  `group_type_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`mobile_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mobile_m`
--

LOCK TABLES `mobile_m` WRITE;
/*!40000 ALTER TABLE `mobile_m` DISABLE KEYS */;
/*!40000 ALTER TABLE `mobile_m` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monthly_g_ac`
--

DROP TABLE IF EXISTS `monthly_g_ac`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monthly_g_ac` (
  `g_ac_no` bigint(20) NOT NULL,
  `month` varchar(10) NOT NULL,
  `credit_rating` float DEFAULT NULL,
  `approval_rating` float DEFAULT NULL,
  `meeting_attendance` float DEFAULT NULL,
  `c_m_saved_am` decimal(16,2) DEFAULT NULL,
  `c_m_planned_monthly_saving` decimal(16,2) DEFAULT NULL,
  `c_m_prov_int_en_am` decimal(16,2) DEFAULT NULL,
  `c_m_returned_saved_am` decimal(16,2) DEFAULT NULL,
  `c_m_returned_int_en_am` decimal(16,2) DEFAULT NULL,
  `c_m_profit_share_declared_am` decimal(16,2) DEFAULT NULL,
  `c_m_profit_share_paid_am` decimal(16,2) DEFAULT NULL,
  `c_m_loan_am` decimal(16,2) DEFAULT NULL,
  `c_m_pending_loan_am` decimal(16,2) DEFAULT NULL,
  `c_m_rec_loan_am` decimal(16,2) DEFAULT NULL,
  `c_m_rec_int_on_loan_am` decimal(16,2) DEFAULT NULL,
  `c_m_proj_int_on_loan_am` decimal(16,2) DEFAULT NULL,
  `c_m_no_of_loans` int(10) DEFAULT NULL,
  `c_m_no_of_active_loans` int(10) DEFAULT NULL,
  `c_m_sub_std_dept_am` decimal(16,2) DEFAULT NULL,
  `c_m_no_of_sub_std_dept` int(10) DEFAULT NULL,
  `c_m_bad_dept_exp_am` decimal(16,2) DEFAULT NULL,
  `c_m_no_of_bad_dept_exp` int(10) DEFAULT NULL,
  `c_m_bad_dept_closed_am` decimal(16,2) DEFAULT NULL,
  `c_m_no_of_bad_dept_closed` int(10) DEFAULT NULL,
  `a_m_saved_am` decimal(16,2) DEFAULT NULL,
  `a_m_planned_monthly_saving` decimal(16,2) DEFAULT NULL,
  `a_m_prov_int_en_am` decimal(16,2) DEFAULT NULL,
  `a_m_returned_saved_am` decimal(16,2) DEFAULT NULL,
  `a_m_returned_int_en_am` decimal(16,2) DEFAULT NULL,
  `a_m_divided_declared_am` decimal(16,2) DEFAULT NULL,
  `a_m_divided_paid_am` decimal(16,2) DEFAULT NULL,
  `a_m_loan_am` decimal(16,2) DEFAULT NULL,
  `a_m_pending_loan_am` decimal(16,2) DEFAULT NULL,
  `a_m_rec_loan_am` decimal(16,2) DEFAULT NULL,
  `a_m_rec_int_on_loan_am` decimal(16,2) DEFAULT NULL,
  `a_m_proj_int_on_loan_am` decimal(16,2) DEFAULT NULL,
  `a_m_no_of_loans` int(10) DEFAULT NULL,
  `a_m_no_of_active_loans` int(10) DEFAULT NULL,
  `a_m_sub_std_dept_am` decimal(16,2) DEFAULT NULL,
  `a_m_no_of_sub_std_dept` int(10) DEFAULT NULL,
  `a_m_bad_dept_exp_am` decimal(16,2) DEFAULT NULL,
  `a_m_no_of_bad_dept_exp` int(10) DEFAULT NULL,
  `a_m_bad_dept_closed_am` decimal(16,2) DEFAULT NULL,
  `a_m_no_of_bad_dept_closed` int(10) DEFAULT NULL,
  `p_loan_am` decimal(16,2) DEFAULT NULL,
  `p_pending_loan_am` decimal(16,2) DEFAULT NULL,
  `p_rec_loan_am` decimal(16,2) DEFAULT NULL,
  `p_rec_int_on_loan_am` decimal(16,2) DEFAULT NULL,
  `p_proj_int_on_loan_am` decimal(16,2) DEFAULT NULL,
  `no_of_project` int(10) DEFAULT NULL,
  `no_of_active_project` int(10) DEFAULT NULL,
  `fix_deposit_inv_am` decimal(16,2) DEFAULT NULL,
  `pending_fix_deposit_am` decimal(16,2) DEFAULT NULL,
  `rec_fix_deposit_am` decimal(16,2) DEFAULT NULL,
  `rec_int_on_fix_deposit_am` decimal(16,2) DEFAULT NULL,
  `proj_int_on_fix_deposit_am` decimal(16,2) DEFAULT NULL,
  `no_of_fix_deposit` int(10) DEFAULT NULL,
  `no_of_active_fix_deposit` int(10) DEFAULT NULL,
  `other_inv_am` decimal(16,2) DEFAULT NULL,
  `pending_other_inv_am` decimal(16,2) DEFAULT NULL,
  `rec_other_inv_am` decimal(16,2) DEFAULT NULL,
  `rec_int_on_other_inv_am` decimal(16,2) DEFAULT NULL,
  `proj_int_on_other_inv_am` decimal(16,2) DEFAULT NULL,
  `no_of_other_inv` int(10) DEFAULT NULL,
  `no_of_active_other_inv` int(10) DEFAULT NULL,
  `int_en_on_saving_ac_am` decimal(16,2) DEFAULT NULL,
  `net_profit_am` decimal(16,2) DEFAULT NULL,
  `expenses_am` decimal(16,2) DEFAULT NULL,
  `rec_penalty_am` decimal(16,2) DEFAULT NULL,
  `pending_penalty_am` decimal(16,2) DEFAULT NULL,
  `borrowed_loan_am` decimal(16,2) DEFAULT NULL,
  `pending_borrowed_loan_am` decimal(16,2) DEFAULT NULL,
  `paid_borrowed_loan_am` decimal(16,2) DEFAULT NULL,
  `paid_int_on_borrowed_loan_am` decimal(16,2) DEFAULT NULL,
  `proj_int_on_borrowed_loan_am` decimal(16,2) DEFAULT NULL,
  `no_of_bank_loan` int(10) DEFAULT NULL,
  `no_of_active_bank_loan` int(10) DEFAULT NULL,
  `bank_sub_std_dept_am` decimal(16,2) DEFAULT NULL,
  `bank_no_of_sub_std_dept` int(10) DEFAULT NULL,
  `bank_bad_dept_exp_am` decimal(16,2) DEFAULT NULL,
  `bank_no_of_bad_dept_exp` int(10) DEFAULT NULL,
  `bank_bad_dept_closed_am` decimal(16,2) DEFAULT NULL,
  `bank_no_of_bad_dept_closed` int(10) DEFAULT NULL,
  `gov_revolving_fund_am` decimal(16,2) DEFAULT NULL,
  `gov_revolving_fund_returned_am` decimal(16,2) DEFAULT NULL,
  `no_of_gov_revolving_fund` int(10) DEFAULT NULL,
  `no_of_active_gov_revolving_fund` int(10) DEFAULT NULL,
  `gov_development_fund_am` decimal(16,2) DEFAULT NULL,
  `no_of_gov_development_fund` int(10) DEFAULT NULL,
  `clear_bank_balance_am` decimal(16,2) DEFAULT NULL,
  `subj_clearing_bank_balance_am` decimal(16,2) DEFAULT NULL,
  `clear_cash_in_hand_am` decimal(16,2) DEFAULT NULL,
  `subj_clearing_cash_in_hand_am` decimal(16,2) DEFAULT NULL,
  `pen_shg_one_mem_reg_fee` decimal(16,2) DEFAULT NULL,
  `pen_shg_one_service_charges` decimal(16,2) DEFAULT NULL,
  `no_of_txs_monthly_exp` int(10) DEFAULT NULL,
  `no_of_txs_monthly_done` int(10) DEFAULT NULL,
  `no_of_txs_monthly_approved` int(10) DEFAULT NULL,
  `attachment_url` varchar(200) DEFAULT NULL,
  `last_updated_ts` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`g_ac_no`,`month`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monthly_g_ac`
--

LOCK TABLES `monthly_g_ac` WRITE;
/*!40000 ALTER TABLE `monthly_g_ac` DISABLE KEYS */;
/*!40000 ALTER TABLE `monthly_g_ac` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monthly_g_ac_by_txtype`
--

DROP TABLE IF EXISTS `monthly_g_ac_by_txtype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monthly_g_ac_by_txtype` (
  `month` varchar(10) NOT NULL,
  `g_ac_no` bigint(20) NOT NULL,
  `tx_type_id` int(10) unsigned NOT NULL,
  `amount` decimal(16,2) DEFAULT NULL,
  PRIMARY KEY (`month`,`g_ac_no`,`tx_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monthly_g_ac_by_txtype`
--

LOCK TABLES `monthly_g_ac_by_txtype` WRITE;
/*!40000 ALTER TABLE `monthly_g_ac_by_txtype` DISABLE KEYS */;
/*!40000 ALTER TABLE `monthly_g_ac_by_txtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monthly_g_bank_account`
--

DROP TABLE IF EXISTS `monthly_g_bank_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monthly_g_bank_account` (
  `month` varchar(10) NOT NULL,
  `bank_account_no` bigint(20) NOT NULL,
  `g_ac_no` bigint(20) NOT NULL,
  `clear_balance_am` decimal(16,2) DEFAULT NULL,
  `subj_clearing_balance_am` decimal(16,2) DEFAULT NULL,
  `verified_balance_am` decimal(16,2) DEFAULT NULL,
  `interest_am` decimal(16,2) DEFAULT NULL,
  `penalty_charges_am` decimal(16,2) DEFAULT NULL,
  PRIMARY KEY (`month`,`bank_account_no`),
  KEY `g_bank_acount_FKIndex2` (`bank_account_no`),
  CONSTRAINT `g_bank_account_ibfk_20` FOREIGN KEY (`bank_account_no`) REFERENCES `bank_account` (`bank_account_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monthly_g_bank_account`
--

LOCK TABLES `monthly_g_bank_account` WRITE;
/*!40000 ALTER TABLE `monthly_g_bank_account` DISABLE KEYS */;
/*!40000 ALTER TABLE `monthly_g_bank_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monthly_g_invt_ac`
--

DROP TABLE IF EXISTS `monthly_g_invt_ac`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monthly_g_invt_ac` (
  `month` varchar(10) NOT NULL,
  `g_invt_ac_no` bigint(20) NOT NULL,
  `g_ac_no` bigint(20) NOT NULL,
  `b_group_ac_no` bigint(20) NOT NULL,
  `investment_type_id` int(10) unsigned NOT NULL,
  `account_status_id` int(10) unsigned NOT NULL,
  `investment_ac_name` varchar(50) DEFAULT NULL,
  `invt_am` decimal(16,2) DEFAULT NULL,
  `rec_invt_am` decimal(16,2) DEFAULT NULL,
  `rec_interest_am` decimal(16,2) DEFAULT NULL,
  `proj_interest_am` decimal(16,2) DEFAULT NULL,
  PRIMARY KEY (`month`,`g_invt_ac_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monthly_g_invt_ac`
--

LOCK TABLES `monthly_g_invt_ac` WRITE;
/*!40000 ALTER TABLE `monthly_g_invt_ac` DISABLE KEYS */;
/*!40000 ALTER TABLE `monthly_g_invt_ac` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monthly_g_loan_ac`
--

DROP TABLE IF EXISTS `monthly_g_loan_ac`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monthly_g_loan_ac` (
  `month` varchar(10) NOT NULL,
  `g_loan_ac_no` bigint(20) NOT NULL,
  `g_ac_no` bigint(20) NOT NULL,
  `b_group_ac_no` bigint(20) NOT NULL,
  `fund_type_id` int(10) unsigned NOT NULL,
  `account_status_id` int(10) unsigned NOT NULL,
  `loan_source_id` int(10) unsigned NOT NULL,
  `principle_am` decimal(16,2) DEFAULT NULL,
  `pending_principle_am` decimal(16,2) DEFAULT NULL,
  `paid_interest_am` decimal(16,2) DEFAULT NULL,
  `proj_interest_am` decimal(16,2) DEFAULT NULL,
  `installment_am` decimal(16,2) DEFAULT NULL,
  `pre_emi_interest_am` decimal(16,2) DEFAULT NULL,
  `pending_interest_due_am` decimal(16,2) DEFAULT NULL,
  PRIMARY KEY (`month`,`g_loan_ac_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monthly_g_loan_ac`
--

LOCK TABLES `monthly_g_loan_ac` WRITE;
/*!40000 ALTER TABLE `monthly_g_loan_ac` DISABLE KEYS */;
/*!40000 ALTER TABLE `monthly_g_loan_ac` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monthly_m_ac`
--

DROP TABLE IF EXISTS `monthly_m_ac`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monthly_m_ac` (
  `month` varchar(10) NOT NULL,
  `m_ac_no` bigint(20) NOT NULL,
  `credit_rating` float DEFAULT NULL,
  `saved_am` decimal(16,2) DEFAULT NULL,
  `prov_int_en_am` decimal(16,2) DEFAULT NULL,
  `returned_saved_am` decimal(16,2) DEFAULT NULL,
  `returned_int_en_am` decimal(16,2) DEFAULT NULL,
  `divided_profit_declared_am` decimal(16,2) DEFAULT NULL,
  `divided_profit_paid_am` decimal(16,2) DEFAULT NULL,
  `loan_am` decimal(16,2) DEFAULT NULL,
  `rec_loan_am` decimal(16,2) DEFAULT NULL,
  `rec_int_on_loan_am` decimal(16,2) DEFAULT NULL,
  `proj_int_on_loan_am` decimal(16,2) DEFAULT NULL,
  `bad_dept_closed_am` decimal(16,2) DEFAULT NULL,
  `rec_penalty_am` decimal(16,2) DEFAULT NULL,
  `pending_penalty_am` decimal(16,2) DEFAULT NULL,
  `last_updated_ts` timestamp NULL DEFAULT NULL,
  `planned_monthly_saving_am` decimal(16,2) DEFAULT NULL,
  `prev_month_int_en_am` decimal(16,2) DEFAULT NULL,
  `no_of_loans` int(10) DEFAULT NULL,
  `no_of_active_loans` int(10) DEFAULT NULL,
  `meeting_attendance` float DEFAULT NULL,
  `meeting_attended` int(10) DEFAULT NULL,
  `meeting_missed` int(10) DEFAULT NULL,
  PRIMARY KEY (`m_ac_no`,`month`),
  KEY `m_ac_FKIndex1` (`m_ac_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monthly_m_ac`
--

LOCK TABLES `monthly_m_ac` WRITE;
/*!40000 ALTER TABLE `monthly_m_ac` DISABLE KEYS */;
/*!40000 ALTER TABLE `monthly_m_ac` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monthly_m_loan_ac`
--

DROP TABLE IF EXISTS `monthly_m_loan_ac`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monthly_m_loan_ac` (
  `month` varchar(10) NOT NULL,
  `m_loan_ac_no` bigint(20) NOT NULL,
  `m_ac_no` bigint(20) NOT NULL,
  `recovery_period_id` int(10) unsigned NOT NULL,
  `fund_type_id` int(10) unsigned NOT NULL,
  `loan_calculation_id` int(10) unsigned NOT NULL,
  `account_status_id` int(10) unsigned NOT NULL,
  `loan_source_id` int(10) unsigned NOT NULL,
  `principle_am` decimal(16,2) DEFAULT NULL,
  `pending_principle_am` decimal(16,2) DEFAULT NULL,
  `rec_interest_am` decimal(16,2) DEFAULT NULL,
  `proj_interest_am` decimal(16,2) DEFAULT NULL,
  `installment_am` decimal(16,2) DEFAULT NULL,
  `pre_emi_interest_am` decimal(16,2) DEFAULT NULL,
  `pending_interest_due_am` decimal(16,2) DEFAULT NULL,
  `rate_of_interest` float DEFAULT NULL,
  `startup_no_of_inst` int(10) unsigned DEFAULT NULL,
  `exp_no_of_inst` int(10) unsigned DEFAULT NULL,
  `no_of_inst_paid` int(10) unsigned DEFAULT NULL,
  `no_of_insall_late` int(10) unsigned DEFAULT NULL,
  `no_of_insall_missed` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`m_loan_ac_no`,`month`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monthly_m_loan_ac`
--

LOCK TABLES `monthly_m_loan_ac` WRITE;
/*!40000 ALTER TABLE `monthly_m_loan_ac` DISABLE KEYS */;
/*!40000 ALTER TABLE `monthly_m_loan_ac` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monthly_m_saving_ac`
--

DROP TABLE IF EXISTS `monthly_m_saving_ac`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monthly_m_saving_ac` (
  `month` varchar(10) NOT NULL,
  `m_saving_ac_no` bigint(20) NOT NULL,
  `m_ac_no` bigint(20) NOT NULL,
  `recovery_period_id` int(10) unsigned NOT NULL,
  `account_status_id` int(10) unsigned NOT NULL,
  `saved_am` decimal(16,2) DEFAULT NULL,
  `cumulative_saved_am` decimal(16,2) DEFAULT NULL,
  `saving_installment_am` decimal(16,2) DEFAULT NULL,
  `total_int_en_am` decimal(16,2) DEFAULT NULL,
  `current_fy_int_en_am` decimal(16,2) DEFAULT NULL,
  `prev_month_int_am` decimal(16,2) DEFAULT NULL,
  `returned_saved_am` decimal(16,2) DEFAULT NULL,
  `returned_int_en_am` decimal(16,2) DEFAULT NULL,
  `exp_no_of_inst` int(10) unsigned DEFAULT NULL,
  `no_of_inst_paid` int(10) unsigned DEFAULT NULL,
  `no_of_insall_late` int(10) unsigned DEFAULT NULL,
  `no_of_insall_missed` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`m_saving_ac_no`,`month`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monthly_m_saving_ac`
--

LOCK TABLES `monthly_m_saving_ac` WRITE;
/*!40000 ALTER TABLE `monthly_m_saving_ac` DISABLE KEYS */;
/*!40000 ALTER TABLE `monthly_m_saving_ac` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monthly_report`
--

DROP TABLE IF EXISTS `monthly_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monthly_report` (
  `monthly_report_id` int(10) NOT NULL AUTO_INCREMENT,
  `report_name` varchar(30) DEFAULT NULL,
  `report_display_name` varchar(45) DEFAULT NULL,
  `report_desc` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`monthly_report_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monthly_report`
--

LOCK TABLES `monthly_report` WRITE;
/*!40000 ALTER TABLE `monthly_report` DISABLE KEYS */;
INSERT INTO `monthly_report` VALUES (1,'Trail Balance Sheet','Trail Balance Sheet','Trail Balance Sheet of the Group'),(2,'Profit & Loss Statement','Profit & Loss Statement','Profit & Loss Statement of the Group'),(3,'Balance Sheet','Balance Sheet','Balance Sheet of the Group');
/*!40000 ALTER TABLE `monthly_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monthly_report_sheet`
--

DROP TABLE IF EXISTS `monthly_report_sheet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monthly_report_sheet` (
  `monthly_report_sheet_id` int(10) NOT NULL AUTO_INCREMENT,
  `monthly_report_id` int(10) NOT NULL,
  `report_sheet_name` varchar(50) DEFAULT NULL,
  `report_sheet_top_title` varchar(50) DEFAULT NULL,
  `report_sheet_bottom_title` varchar(50) DEFAULT NULL,
  `sheet_format` varchar(25) DEFAULT NULL,
  `report_sheet_desc` varchar(200) DEFAULT NULL,
  `report_sheet_formula` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`monthly_report_sheet_id`),
  KEY `fk_report_item_mapping_monthly_report1` (`monthly_report_id`),
  CONSTRAINT `fk_report_item_mapping_monthly_report1` FOREIGN KEY (`monthly_report_id`) REFERENCES `monthly_report` (`monthly_report_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monthly_report_sheet`
--

LOCK TABLES `monthly_report_sheet` WRITE;
/*!40000 ALTER TABLE `monthly_report_sheet` DISABLE KEYS */;
INSERT INTO `monthly_report_sheet` VALUES (1,1,'Receipts BANK','Receipts','Value of All Receipts','BANK','All the amount Reveived by Group','monthly_g_ac:saved_am:Total Saving;monthly_g_ac:rec_int_on_loan_am:Interest Earned On Loans;monthly_g_ac:p_rec_int_on_loan_am:Earnings On SHG Projects;monthly_g_ac:rec_int_on_fix_deposit_am:Interest Earned On Fixed Deposit;monthly_g_ac:rec_int_on_other_inv_am:Interest Earned On Other Investments;monthly_g_ac:bank_loan_am:Bank Loan;monthly_g_ac:fee_penalty_am:Penalty - Fees - Charges;monthly_g_ac:int_en_on_saving_ac_am:Interest Earned On Bank Saving Accounts;monthly_g_ac:rec_loan_am:Recovered Loans;monthly_g_ac:p_rec_loan_am:Recovered SHG Projects Loan;monthly_g_ac:rec_fix_deposit_am:Recovered Fixed Deposit;monthly_g_ac:rec_other_inv_am:Recovered Other Investments;'),(2,1,'Payments BANK','Payments','Value of All Payments','BANK','All Payments done by the Group','monthly_g_ac:loan_am:Total Loan;monthly_g_ac:p_loan_am:SHG Project Loan;monthly_g_ac:fix_deposit_inv_am:Fix Deposit Investments;monthly_g_ac:other_inv_am:Other Investments;monthly_g_ac:paid_bank_loan_am:Paid Bank Loans;monthly_g_ac:paid_int_on_bank_loan_am:Interest Paid on Bank Loans;monthly_g_ac:returned_saved_am:Returned Savings;monthly_g_ac:returned_int_en_am:Returned Interest on Savings;monthly_g_ac:bank_balance_am:Bank Balance;monthly_g_ac:expenses_am:Expenses;monthly_g_ac:profit_share_paid_am:Profit Share Paid;monthly_g_ac:divided_paid_am:Divided Paid;monthly_g_ac:cash_in_hand_am:Cash In Hand;'),(3,1,'Receipts DETAILED','Receipts','Value of All Receipts','DETAILED','All the amount Reveived by Group','monthly_g_ac:saved_am:Total Saving;monthly_g_ac:rec_int_on_loan_am:Interest Earned On Loans;monthly_g_ac:p_rec_int_on_loan_am:Earnings On SHG Projects;monthly_g_ac:rec_int_on_fix_deposit_am:Interest Earned On Fixed Deposit;monthly_g_ac:rec_int_on_other_inv_am:Interest Earned On Other Investments;monthly_g_ac:bank_loan_am:Bank Loan;monthly_g_ac:fee_penalty_am:Penalty - Fees - Charges;monthly_g_ac:int_en_on_saving_ac_am:Interest Earned On Bank Saving Accounts;monthly_g_ac:rec_loan_am:Recovered Loans;monthly_g_ac:p_rec_loan_am:Recovered SHG Projects Loan;monthly_g_ac:rec_fix_deposit_am:Recovered Fixed Deposit;monthly_g_ac:rec_other_inv_am:Recovered Other Investments;'),(4,1,'Payments DETAILED','Payments','Value of All Payments','DETAILED','All Payments done by the Group','monthly_g_ac:loan_am:Total Loan;monthly_g_ac:p_loan_am:SHG Project Loan;monthly_g_ac:fix_deposit_inv_am:Fix Deposit Investments;monthly_g_ac:other_inv_am:Other Investments;monthly_g_ac:paid_bank_loan_am:Paid Bank Loans;monthly_g_ac:paid_int_on_bank_loan_am:Interest Paid on Bank Loans;monthly_g_ac:returned_saved_am:Returned Savings;monthly_g_ac:returned_int_en_am:Returned Interest on Savings;monthly_g_ac:bank_balance_am:Bank Balance;monthly_g_ac:expenses_am:Expenses;monthly_g_ac:profit_share_paid_am:Profit Share Paid;monthly_g_ac:divided_paid_am:Divided Paid;monthly_g_ac:cash_in_hand_am:Cash In Hand;'),(5,2,'Profits BANK','Profits','Value of All Receipts','BANK','All the amount Profits for the Group','monthly_g_ac:rec_int_on_loan_am:Interest Earned On Loans;monthly_g_ac:p_rec_int_on_loan_am:Earning On SHG Projects;monthly_g_ac:rec_int_on_fix_deposit_am:Interest Earned On Fixed Deposit;monthly_g_ac:rec_int_on_other_inv_am:Interest Earned On Other Investments;monthly_g_ac:int_en_on_saving_ac_am:Interest Earned On Bank Saving Accounts;monthly_g_ac:fee_penalty_am:Penalty - Fees - Charges;monthly_g_ac:proj_int_on_loan_am:Projected Interest On Loan;monthly_g_ac:p_proj_int_on_loan_am:Projected Earnings On SHG Project;monthly_g_ac:proj_int_on_fix_deposit_am:Projected Earnings On Fix Deposit;monthly_g_ac:proj_int_on_other_inv_am:Projected Earnings On Other Investments;'),(6,2,'Losses BANK','Losses','Value of All Losses','BANK','All the amount Loss for the Group','monthly_g_ac:expenses_am:Expenses;monthly_g_ac:paid_int_on_bank_loan_am:Interest Paid on Bank Loans;monthly_g_ac:returned_int_en_am:Returned Interest on Savings;monthly_g_ac:profit_share_paid_am:Profit Share Paid;monthly_g_ac:divided_paid_am:Divided Paid;monthly_g_ac:proj_int_on_bank_loan_am:Projected Interest On Bank Loan;monthly_g_ac:prov_int_en_am:Provisional Interest on Saving;monthly_g_ac:net_profit_am:Net Profit;'),(7,2,'Profits DETAILED','Profits','Value of All Profits','DETAILED','All the amount Profit for the Group','monthly_g_ac:rec_int_on_loan_am:Interest Earned On Loans;monthly_g_ac:p_rec_int_on_loan_am:Earning On SHG Projects;monthly_g_ac:rec_int_on_fix_deposit_am:Interest Earned On Fixed Deposit;monthly_g_ac:rec_int_on_other_inv_am:Interest Earned On Other Investments;monthly_g_ac:int_en_on_saving_ac_am:Interest Earned On Bank Saving Accounts;monthly_g_ac:fee_penalty_am:Penalty - Fees - Charges;monthly_g_ac:proj_int_on_loan_am:Projected Interest On Loan;monthly_g_ac:p_proj_int_on_loan_am:Projected Earnings On SHG Project;monthly_g_ac:proj_int_on_fix_deposit_am:Projected Earnings On Fix Deposit;monthly_g_ac:proj_int_on_other_inv_am:Projected Earnings On Other Investments;'),(8,2,'Losses DETAILED','Losses','Value of All Losses','DETAILED','All the amount Loss for the Group','monthly_g_ac:expenses_am:Expenses;monthly_g_ac:paid_int_on_bank_loan_am:Interest Paid on Bank Loans;monthly_g_ac:returned_int_en_am:Returned Interest on Savings;monthly_g_ac:profit_share_paid_am:Profit Share Paid;monthly_g_ac:divided_paid_am:Divided Paid;monthly_g_ac:proj_int_on_bank_loan_am:Projected Interest On Bank Loan;monthly_g_ac:prov_int_en_am:Provisional Interest on Saving;monthly_g_ac:net_profit_am:Net Profit;'),(9,3,'Assets BANK','Assets','Value of All Assets','BANK','All Assets of the Group','monthly_g_ac:outstanding_loan_am:Outstanding Loan;monthly_g_ac:outstanding_p_loan_am:Outstanding SHG Project Loan;monthly_g_ac:outstanding_fix_deposit_am:Fix Deposit Investments;monthly_g_ac:outstanding_other_inv_am:Other Investments;monthly_g_ac:bank_balance_am:Bank Balance;monthly_g_ac:cash_in_hand_am:Cash In Hand;monthly_g_ac:proj_int_on_loan_am:Projected Interest On Loan;monthly_g_ac:p_proj_int_on_loan_am:Projected Earnings On SHG Project;monthly_g_ac:proj_int_on_fix_deposit_am:Projected Earnings On Fix Deposit;monthly_g_ac:proj_int_on_other_inv_am:Projected Earnings On Other Investments;'),(10,3,'Liabilities BANK','Liabilities','Value of All Liabilities','BANK','All Liabilities of the Group','monthly_g_ac:outstanding_saved_am:Outstanding Saving;monthly_g_ac:outstanding_bank_loan_am:Outstanding Bank Loan;monthly_g_ac:proj_int_on_bank_loan_am:Projected Interest On Bank Loan;monthly_g_ac:outstanding_prov_int_en_am:Outstanding Provisional Interest on Saving;monthly_g_ac:net_profit_am:Net Profit;'),(11,3,'Assets DETAILED','Assets','Value of All Assets','DETAILED','All Assets of the Group','monthly_g_ac:outstanding_loan_am:Outstanding Loan;monthly_g_ac:outstanding_p_loan_am:Outstanding SHG Project Loan;monthly_g_ac:outstanding_fix_deposit_am:Fix Deposit Investments;monthly_g_ac:outstanding_other_inv_am:Other Investments;monthly_g_ac:bank_balance_am:Bank Balance;monthly_g_ac:cash_in_hand_am:Cash In Hand;monthly_g_ac:proj_int_on_loan_am:Projected Interest On Loan;monthly_g_ac:p_proj_int_on_loan_am:Projected Earnings On SHG Project;monthly_g_ac:proj_int_on_fix_deposit_am:Projected Earnings On Fix Deposit;monthly_g_ac:proj_int_on_other_inv_am:Projected Earnings On Other Investments;'),(12,3,'Liabilities DETAILED','Liabilities','Value of All Liabilities','DETAILED','All Liabilities of the Group','monthly_g_ac:outstanding_saved_am:Outstanding Saving;monthly_g_ac:outstanding_bank_loan_am:Outstanding Bank Loan;monthly_g_ac:proj_int_on_bank_loan_am:Projected Interest On Bank Loan;monthly_g_ac:outstanding_prov_int_en_am:Outstanding Provisional Interest on Saving;monthly_g_ac:net_profit_am:Net Profit;');
/*!40000 ALTER TABLE `monthly_report_sheet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `multi_m_to_loan_ac`
--

DROP TABLE IF EXISTS `multi_m_to_loan_ac`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `multi_m_to_loan_ac` (
  `m_loan_ac_no` bigint(20) NOT NULL,
  `m_ac_no` bigint(20) NOT NULL,
  `loan_share` float unsigned zerofill DEFAULT NULL,
  KEY `multi_m_to_loan_ac_FKIndex2` (`m_ac_no`),
  KEY `multi_m_to_loan_ac_FKIndex1` (`m_loan_ac_no`),
  CONSTRAINT `multi_m_to_loan_ac_ibfk_1` FOREIGN KEY (`m_loan_ac_no`) REFERENCES `m_loan_ac` (`m_loan_ac_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `multi_m_to_loan_ac_ibfk_2` FOREIGN KEY (`m_ac_no`) REFERENCES `m_ac` (`m_ac_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `multi_m_to_loan_ac`
--

LOCK TABLES `multi_m_to_loan_ac` WRITE;
/*!40000 ALTER TABLE `multi_m_to_loan_ac` DISABLE KEYS */;
/*!40000 ALTER TABLE `multi_m_to_loan_ac` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `name_title`
--

DROP TABLE IF EXISTS `name_title`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `name_title` (
  `title_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `lang_id` int(10) unsigned NOT NULL,
  `title` varchar(10) NOT NULL,
  `gender` enum('Male','Female','Unknow') DEFAULT NULL,
  PRIMARY KEY (`title_id`),
  KEY `name_title_FKIndex1` (`lang_id`),
  CONSTRAINT `name_title_ibfk_1` FOREIGN KEY (`lang_id`) REFERENCES `lang` (`lang_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `name_title`
--

LOCK TABLES `name_title` WRITE;
/*!40000 ALTER TABLE `name_title` DISABLE KEYS */;
INSERT INTO `name_title` VALUES (1,1,'','Unknow'),(2,1,'Shri','Male'),(3,1,'Shrimati','Female'),(4,1,'Sau','Female'),(5,1,'Ku','Female'),(6,1,'Mr','Male'),(7,1,'Mrs','Female'),(8,1,'Miss','Female');
/*!40000 ALTER TABLE `name_title` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nominee`
--

DROP TABLE IF EXISTS `nominee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `nominee` (
  `nominee_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `m_ac_no` bigint(20) NOT NULL,
  `contact_id` bigint(20) NOT NULL,
  `name` varchar(40) NOT NULL,
  `relation` varchar(40) NOT NULL,
  `share` float DEFAULT '1',
  PRIMARY KEY (`nominee_id`),
  KEY `nominee_ibfk_1_idx_idx` (`contact_id`),
  KEY `nominee_ibfk_2_idx_idx` (`m_ac_no`),
  CONSTRAINT `nominee_ibfk_1_idx` FOREIGN KEY (`contact_id`) REFERENCES `contact` (`contact_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `nominee_ibfk_2_idx` FOREIGN KEY (`m_ac_no`) REFERENCES `m_profile` (`m_ac_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nominee`
--

LOCK TABLES `nominee` WRITE;
/*!40000 ALTER TABLE `nominee` DISABLE KEYS */;
/*!40000 ALTER TABLE `nominee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_mode`
--

DROP TABLE IF EXISTS `payment_mode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_mode` (
  `payment_mode_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `payment_mode_type` varchar(15) NOT NULL COMMENT 'CASH,CHEQUE,A2A,OLBNK,DEBT,CRDT,DD',
  `payment_mode_desc` varchar(100) NOT NULL,
  PRIMARY KEY (`payment_mode_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_mode`
--

LOCK TABLES `payment_mode` WRITE;
/*!40000 ALTER TABLE `payment_mode` DISABLE KEYS */;
INSERT INTO `payment_mode` VALUES (1,'CASH','Payment Done in Cash!'),(2,'CHEQUE','Bank'),(3,'DEBIT CARD','Bank'),(4,'CREDIT CARD','Bank'),(5,'DEMAND DRAFT','Bank'),(6,'NET BANKING','Bank'),(7,'AUTO DEBIT','Bank'),(8,'OUTSTANDING','Internal'),(9,'INTERNAL','Internal');
/*!40000 ALTER TABLE `payment_mode` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `process_scheduler`
--

DROP TABLE IF EXISTS `process_scheduler`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `process_scheduler` (
  `process_scheduler_id` int(11) NOT NULL AUTO_INCREMENT,
  `start_time` timestamp NULL DEFAULT NULL,
  `end_time` timestamp NULL DEFAULT NULL,
  `done_by_member` bigint(20) DEFAULT NULL,
  `scheduler_key` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`process_scheduler_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `process_scheduler`
--

LOCK TABLES `process_scheduler` WRITE;
/*!40000 ALTER TABLE `process_scheduler` DISABLE KEYS */;
/*!40000 ALTER TABLE `process_scheduler` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `process_scheduler_sprint`
--

DROP TABLE IF EXISTS `process_scheduler_sprint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `process_scheduler_sprint` (
  `process_scheduler_sprint_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `process_scheduler_id` int(11) DEFAULT NULL,
  `district_id` int(11) DEFAULT NULL,
  `sprint_index` int(11) DEFAULT NULL,
  `no_of_groups` int(11) DEFAULT NULL,
  `start_time` timestamp NULL DEFAULT NULL,
  `end_time` timestamp NULL DEFAULT NULL,
  `info` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`process_scheduler_sprint_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `process_scheduler_sprint`
--

LOCK TABLES `process_scheduler_sprint` WRITE;
/*!40000 ALTER TABLE `process_scheduler_sprint` DISABLE KEYS */;
/*!40000 ALTER TABLE `process_scheduler_sprint` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reason_to_undo`
--

DROP TABLE IF EXISTS `reason_to_undo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reason_to_undo` (
  `reason_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `reason` varchar(20) NOT NULL,
  `reason_desc` varchar(100) NOT NULL,
  PRIMARY KEY (`reason_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reason_to_undo`
--

LOCK TABLES `reason_to_undo` WRITE;
/*!40000 ALTER TABLE `reason_to_undo` DISABLE KEYS */;
INSERT INTO `reason_to_undo` VALUES (1,'Entry Mistake','Transaction is undone due to mistake in entry'),(2,'Cheque Bounced','Transaction is undone due to Cheque Bounced'),(3,'Auto Debit Failure','Transaction is undone due to Auto Debit Failure'),(4,'Payment Tobe Done','Transaction is undone due to Duplicate');
/*!40000 ALTER TABLE `reason_to_undo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recovery_period`
--

DROP TABLE IF EXISTS `recovery_period`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recovery_period` (
  `recovery_period_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `period` varchar(20) NOT NULL,
  `recovery_period_desc` varchar(100) NOT NULL,
  PRIMARY KEY (`recovery_period_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recovery_period`
--

LOCK TABLES `recovery_period` WRITE;
/*!40000 ALTER TABLE `recovery_period` DISABLE KEYS */;
INSERT INTO `recovery_period` VALUES (1,'Daily','Recovery done every Day'),(2,'Weekly','Recovery done every Week'),(3,'Monthly','Recovery done every Month'),(4,'Bi-Monthly','Recovery done every 2 Months'),(5,'Quarterly','Recovery done every Quarter Year or in 3 Month'),(6,'Half Yearly','Recovery done every Half Year or in 6 Month'),(7,'Yearly','Recovery done every Year');
/*!40000 ALTER TABLE `recovery_period` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule_process_error`
--

DROP TABLE IF EXISTS `schedule_process_error`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schedule_process_error` (
  `schedule_process_error_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `process_scheduler_id` int(11) DEFAULT NULL,
  `group_ac_no` bigint(20) DEFAULT NULL,
  `member_ac_no` bigint(20) DEFAULT NULL,
  `time` timestamp NULL DEFAULT NULL,
  `error_job` varchar(50) DEFAULT NULL,
  `error_message` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`schedule_process_error_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule_process_error`
--

LOCK TABLES `schedule_process_error` WRITE;
/*!40000 ALTER TABLE `schedule_process_error` DISABLE KEYS */;
/*!40000 ALTER TABLE `schedule_process_error` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tx`
--

DROP TABLE IF EXISTS `tx`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tx` (
  `tx_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `g_ac_no` bigint(20) NOT NULL,
  `m_ac_no` bigint(20) DEFAULT NULL,
  `b_g_ac_no` bigint(20) DEFAULT NULL,
  `tx_type_id` int(10) unsigned NOT NULL,
  `tx_status_id` int(10) unsigned NOT NULL,
  `payment_mode_id` int(10) unsigned NOT NULL,
  `done_by_m` bigint(20) NOT NULL,
  `approved_by_m` bigint(20) DEFAULT NULL,
  `tx_todo_id` bigint(20) DEFAULT NULL,
  `counter_tx_id` bigint(20) DEFAULT NULL,
  `g_bank_account` bigint(20) DEFAULT NULL,
  `m_bank_account` bigint(20) DEFAULT NULL,
  `b_g_bank_account` bigint(20) DEFAULT NULL,
  `m_saving_ac_no` bigint(20) DEFAULT NULL,
  `m_loan_ac_no` bigint(20) DEFAULT NULL,
  `g_invt_ac_no` bigint(20) DEFAULT NULL,
  `g_loan_ac_no` bigint(20) DEFAULT NULL,
  `reason_to_undo` int(10) unsigned DEFAULT NULL,
  `receipt_voucher_no` varchar(12) DEFAULT NULL,
  `cheque_no` varchar(12) DEFAULT NULL,
  `amount` decimal(16,2) DEFAULT NULL,
  `payment_ts` timestamp NULL DEFAULT NULL,
  `entry_ts` timestamp NULL DEFAULT NULL,
  `approved_ts` timestamp NULL DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `entry_location` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tx`
--

LOCK TABLES `tx` WRITE;
/*!40000 ALTER TABLE `tx` DISABLE KEYS */;
/*!40000 ALTER TABLE `tx` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tx_status`
--

DROP TABLE IF EXISTS `tx_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tx_status` (
  `tx_status_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tx_status` varchar(20) NOT NULL,
  `tx_status_desc` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`tx_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tx_status`
--

LOCK TABLES `tx_status` WRITE;
/*!40000 ALTER TABLE `tx_status` DISABLE KEYS */;
INSERT INTO `tx_status` VALUES (1,'Draft','Transaction is saved as Draft'),(2,'Submitted','Transaction is Submitted'),(3,'Missing Bank Info','Transaction is Missing Bank Info'),(4,'Payment Tobe Done','Transaction is waiting for Payment Tobe Done'),(5,'Approved','Transaction is Approved'),(6,'Rejected','Transaction is Rejected'),(7,'Undone','Transaction is Undone, thus all action is reverted'),(8,'Complete','Transaction is Complete and ready for Approval'),(9,'Auto Approved','Transaction is Auto Approved by the System at the End of the Month'),(10,'Auto Rejected','Transaction is Auto Rejected, as Incomplete by the System at the End of the Month'),(11,'Ready','Transaction is Approved and Ready');
/*!40000 ALTER TABLE `tx_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tx_todo`
--

DROP TABLE IF EXISTS `tx_todo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tx_todo` (
  `tx_todo_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `m_ac_no` bigint(20) DEFAULT NULL,
  `g_ac_no` bigint(20) NOT NULL,
  `b_g_ac_no` bigint(20) DEFAULT NULL,
  `tx_type_id` int(10) unsigned NOT NULL,
  `tx_todo_status_id` int(10) unsigned NOT NULL,
  `m_saving_ac_no` bigint(20) DEFAULT NULL,
  `m_loan_ac_no` bigint(20) DEFAULT NULL,
  `g_loan_ac_no` bigint(20) DEFAULT NULL,
  `expected_pmt_mode` int(10) unsigned DEFAULT NULL,
  `amount` decimal(16,2) NOT NULL,
  `interest_component` decimal(16,2) DEFAULT NULL,
  `penalty_am` decimal(16,2) DEFAULT NULL,
  `due_date` date NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `creation_on_ts` timestamp NULL DEFAULT NULL,
  `done_on_ts` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`tx_todo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tx_todo`
--

LOCK TABLES `tx_todo` WRITE;
/*!40000 ALTER TABLE `tx_todo` DISABLE KEYS */;
/*!40000 ALTER TABLE `tx_todo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tx_todo_status`
--

DROP TABLE IF EXISTS `tx_todo_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tx_todo_status` (
  `tx_todo_status_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tx_todo_status` varchar(20) NOT NULL COMMENT '"PENDING","DONE","UNDONE","OVERDUE","MISSED","CANCELLED"',
  `tx_todo_status_desc` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`tx_todo_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tx_todo_status`
--

LOCK TABLES `tx_todo_status` WRITE;
/*!40000 ALTER TABLE `tx_todo_status` DISABLE KEYS */;
INSERT INTO `tx_todo_status` VALUES (1,'Draft','Todo Transaction is saved as Draft'),(2,'Todo','Todo Transaction is Todo state'),(3,'Done','Todo Transaction is Complete'),(4,'Undone','Todo Transaction is waiting for Payment Tobe Done'),(5,'Over Due','Todo Transaction is Over Due as it missed its Due Date'),(6,'Missed','Todo Transaction is Missed as it was done in current month'),(7,'Approved','Related Transaction is Approved'),(8,'Rejected','Related Transaction is Rejected'),(9,'Auto Approved','Transaction is Auto Approved by the System at the End of the Month'),(10,'Auto Rejected','Transaction is Auto Rejected, as Incomplete by the System at the End of the Month'),(11,'Previous Missed','Todo Transaction is previously Missed and carried forward');
/*!40000 ALTER TABLE `tx_todo_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tx_type`
--

DROP TABLE IF EXISTS `tx_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tx_type` (
  `tx_type_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tx_name` varchar(30) NOT NULL,
  `tx_category` varchar(20) NOT NULL,
  `slip_type` varchar(20) NOT NULL,
  `amount_type` varchar(20) NOT NULL,
  `tx_with` varchar(20) NOT NULL,
  `tx_type_desc` varchar(100) DEFAULT NULL,
  `update_formula_on_done` varchar(500) DEFAULT NULL,
  `update_formula_on_undone` varchar(500) DEFAULT NULL,
  `update_formula_on_approve` varchar(500) DEFAULT NULL,
  `update_formula_on_reject` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`tx_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tx_type`
--

LOCK TABLES `tx_type` WRITE;
/*!40000 ALTER TABLE `tx_type` DISABLE KEYS */;
INSERT INTO `tx_type` VALUES (1,'Saving Installment','MEMBER SAVING','RECEIPT','LIABILITIES','MEMBER','Installment for Saving','m_saving_ac:saved_am:ADD;m_saving_ac:cumulative_saved_am:ADD;m_saving_ac:exp_no_of_inst:DECR;m_saving_ac:no_of_inst_paid:INCR;m_saving_ac:no_of_insall_late:INCR;m_ac:saved_am:ADD;g_ac:c_m_saved_am:ADD;g_ac:a_m_saved_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:ADD;','m_saving_ac:saved_am:SUB;m_saving_ac:cumulative_saved_am:SUB;m_saving_ac:exp_no_of_inst:INCR;m_saving_ac:no_of_inst_paid:DECR;m_saving_ac:no_of_insall_late:DECR;m_ac:saved_am:SUB;g_ac:c_m_saved_am:SUB;g_ac:a_m_saved_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','m_saving_ac:saved_am:SUB;m_saving_ac:cumulative_saved_am:SUB;m_saving_ac:exp_no_of_inst:INCR;m_saving_ac:no_of_inst_paid:DECR;m_saving_ac:no_of_insall_late:DECR;m_ac:saved_am:SUB;g_ac:c_m_saved_am:SUB;g_ac:a_m_saved_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;'),(2,'Provisional Interest Earned','MEMBER SAVING','INTERNAL','LIABILITIES','MEMBER','Provisional Interest Earned on the Saving','m_saving_ac:current_fy_int_en_am:ADD;m_saving_ac:total_int_en_am:ADD;m_saving_ac:prev_month_int_am:THIS;m_ac:prov_int_en_am:ADD;m_ac:prev_month_int_en_am:THIS;g_ac:c_m_prov_int_en_am:ADD;g_ac:a_m_prov_int_en_am:ADD;g_ac_by_txtype:amount:ADD;','m_saving_ac:current_fy_int_en_am:SUB;m_saving_ac:total_int_en_am:SUB;m_ac:prov_int_en_am:SUB;g_ac:c_m_prov_int_en_am:SUB;g_ac:a_m_prov_int_en_am:SUB;g_ac_by_txtype:amount:SUB;','','m_saving_ac:current_fy_int_en_am:SUB;m_saving_ac:total_int_en_am:SUB;m_ac:prov_int_en_am:SUB;g_ac:c_m_prov_int_en_am:SUB;g_ac:a_m_prov_int_en_am:SUB;g_ac_by_txtype:amount:SUB;'),(3,'Compute Cumulative Saving','MEMBER SAVING','INTERNAL','LIABILITIES','MEMBER','Compute Cumulative Saving for the given Period','m_saving_ac:cumulative_saved_am:ADD;m_saving_ac:current_fy_int_en_am:ZERO;','m_saving_ac:cumulative_saved_am:SUB;m_saving_ac:current_fy_int_en_am:THIS;','','m_saving_ac:cumulative_saved_am:SUB;m_saving_ac:current_fy_int_en_am:THIS;'),(4,'Saving Returned','MEMBER SAVING','VOUCHER','LIABILITIES','MEMBER','Saving Returned, at Account Closure or regular return','m_saving_ac:returned_saved_am:ADD;m_saving_ac:cumulative_saved_am:SUB;m_ac:returned_saved_am:ADD;g_ac:c_m_returned_saved_am:ADD;g_ac:a_m_returned_saved_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','m_saving_ac:returned_saved_am:SUB;m_saving_ac:cumulative_saved_am:ADD;m_ac:returned_saved_am:SUB;g_ac:c_m_returned_saved_am:SUB;g_ac:a_m_returned_saved_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','m_saving_ac:returned_saved_am:SUB;m_saving_ac:cumulative_saved_am:ADD;m_ac:returned_saved_am:SUB;g_ac:c_m_returned_saved_am:SUB;g_ac:a_m_returned_saved_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(5,'Saving Interest Returned','MEMBER SAVING','VOUCHER','LIABILITIES','MEMBER','Saving Interest Returned, at Account Closure or regular return','m_saving_ac:returned_int_en_am:ADD;m_saving_ac:current_fy_int_en_am:SUB_ZERO;m_ac:returned_int_en_am:ADD;g_ac:c_m_returned_int_en_am:ADD;g_ac:a_m_returned_int_en_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','m_saving_ac:returned_int_en_am:SUB;m_ac:returned_int_en_am:SUB;g_ac:c_m_returned_int_en_am:SUB;g_ac:a_m_returned_int_en_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','m_saving_ac:returned_int_en_am:SUB;m_ac:returned_int_en_am:SUB;g_ac:c_m_returned_int_en_am:SUB;g_ac:a_m_returned_int_en_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(6,'Loan Disbursement','MEMBER LOAN','VOUCHER','ASSETS','MEMBER','Loan Disbursement for the Member','m_loan_ac:principle_am:THIS;m_loan_ac:pending_principle_am:THIS;m_ac:loan_am:ADD;g_ac:c_m_loan_am:ADD;g_ac:a_m_loan_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','m_loan_ac:principle_am:THIS;m_loan_ac:pending_principle_am:SUB;m_ac:loan_am:SUB;g_ac:c_m_loan_am:SUB;g_ac:a_m_loan_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','m_loan_ac:principle_am:THIS;m_loan_ac:pending_principle_am:SUB;m_ac:loan_am:SUB;g_ac:c_m_loan_am:SUB;g_ac:a_m_loan_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(7,'Loan Installment','MEMBER LOAN','RECEIPT','ASSETS','MEMBER','Installment for Loan Payment','m_loan_ac:pending_principle_am:SUB;m_loan_ac:no_of_inst_paid:INCR;m_loan_ac:no_of_insall_late:INCR;m_ac:rec_loan_am:ADD;g_ac:c_m_rec_loan_am:ADD;g_ac:a_m_rec_loan_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:ADD;','m_loan_ac:pending_principle_am:ADD;m_loan_ac:no_of_inst_paid:DECR;m_loan_ac:no_of_insall_late:DECR;m_ac:rec_loan_am:SUB;g_ac:c_m_rec_loan_am:SUB;g_ac:a_m_rec_loan_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','m_loan_ac:pending_principle_am:ADD;m_loan_ac:no_of_inst_paid:DECR;m_loan_ac:no_of_insall_late:DECR;m_ac:rec_loan_am:SUB;g_ac:c_m_rec_loan_am:SUB;g_ac:a_m_rec_loan_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;'),(8,'Loan Interest Installment','MEMBER LOAN','RECEIPT','PROFIT','MEMBER','Loan Interest Installment','m_loan_ac:rec_interest_am:ADD;m_loan_ac:proj_interest_am:SUB_ZERO;m_ac:rec_int_on_loan_am:ADD;m_ac:proj_int_on_loan_am:SUB_ZERO;g_ac:c_m_rec_int_on_loan_am:ADD;g_ac:c_m_proj_int_on_loan_am:SUB_ZERO;g_ac:a_m_rec_int_on_loan_am:ADD;g_ac:a_m_proj_int_on_loan_am:SUB_ZERO;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:ADD;','m_loan_ac:rec_interest_am:SUB;m_loan_ac:proj_interest_am:ADD;m_ac:rec_int_on_loan_am:SUB;m_ac:proj_int_on_loan_am:ADD;g_ac:c_m_rec_int_on_loan_am:SUB;g_ac:c_m_proj_int_on_loan_am:ADD;g_ac:a_m_rec_int_on_loan_am:SUB;g_ac:a_m_proj_int_on_loan_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','m_loan_ac:rec_interest_am:SUB;m_loan_ac:proj_interest_am:ADD;m_ac:rec_int_on_loan_am:SUB;m_ac:proj_int_on_loan_am:ADD;g_ac:c_m_rec_int_on_loan_am:SUB;g_ac:c_m_proj_int_on_loan_am:ADD;g_ac:a_m_rec_int_on_loan_am:SUB;g_ac:a_m_proj_int_on_loan_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;'),(9,'Loan Prepayment','MEMBER LOAN','RECEIPT','ASSETS','MEMBER','Prepayment for the Loan in Advance of Schedule','m_loan_ac:pending_principle_am:SUB_ZERO;m_ac:rec_loan_am:ADD;g_ac:c_m_rec_loan_am:ADD;g_ac:a_m_rec_loan_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:ADD;','m_loan_ac:pending_principle_am:ADD;m_ac:rec_loan_am:SUB;g_ac:c_m_rec_loan_am:SUB;g_ac:a_m_rec_loan_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','m_loan_ac:pending_principle_am:ADD;m_ac:rec_loan_am:SUB;g_ac:c_m_rec_loan_am:SUB;g_ac:a_m_rec_loan_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;'),(10,'Pre Interest Installment','MEMBER LOAN','RECEIPT','PROFIT','MEMBER','Pre Interest charged during Loan Disbursement','m_loan_ac:pre_emi_interest_am:THIS;m_loan_ac:rec_interest_am:ADD;m_ac:rec_int_on_loan_am:ADD;g_ac:c_m_rec_int_on_loan_am:ADD;g_ac:a_m_rec_int_on_loan_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:ADD;','m_loan_ac:rec_interest_am:SUB;m_ac:rec_int_on_loan_am:SUB;g_ac:c_m_rec_int_on_loan_am:SUB;g_ac:a_m_rec_int_on_loan_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','m_loan_ac:rec_interest_am:SUB;m_ac:rec_int_on_loan_am:SUB;g_ac:c_m_rec_int_on_loan_am:SUB;g_ac:a_m_rec_int_on_loan_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;'),(11,'Bad Debt Closure','MEMBER LOAN','INTERNAL','EXPENSE','MEMBER','Closure of Bad Debt','m_ac:bad_dept_closed_am:ADD;g_ac:c_m_bad_dept_closed_am:ADD;g_ac:a_m_bad_dept_closed_am:ADD;g_ac_by_txtype:amount:ADD;','m_ac:bad_dept_closed_am:SUB;g_ac:c_m_bad_dept_closed_am:SUB;g_ac:a_m_bad_dept_closed_am:SUB;g_ac_by_txtype:amount:SUB;','','m_ac:bad_dept_closed_am:SUB;g_ac:c_m_bad_dept_closed_am:SUB;g_ac:a_m_bad_dept_closed_am:SUB;g_ac_by_txtype:amount:SUB;'),(12,'Group Project Dev Fund','PROJECT LOAN','VOUCHER','ASSETS','GROUP','Group Project Dev Fund','g_invt_ac:invt_am:THIS;g_ac:p_loan_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;b_group_bank_account:balance_update:ADD;','g_invt_ac:invt_am:THIS;g_ac:p_loan_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;b_group_bank_account:balance_update:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;b_group_bank_account:balance_approve:ADD;','g_invt_ac:invt_am:THIS;g_ac:p_loan_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;b_group_bank_account:balance_update:SUB;'),(13,'Group Project Recovery','PROJECT LOAN','RECEIPT','ASSETS','GROUP','Fund Recovery from Group Project','g_invt_ac:rec_invt_am:ADD;g_ac:p_rec_loan_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:ADD;b_group_bank_account:balance_update:SUB;','g_invt_ac:rec_invt_am:SUB;g_ac:p_rec_loan_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;b_group_bank_account:balance_update:ADD;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;b_group_bank_account:balance_approve:SUB;','g_invt_ac:rec_invt_am:SUB;g_ac:p_rec_loan_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;b_group_bank_account:balance_update:ADD;'),(14,'Group Project Earning','PROJECT LOAN','RECEIPT','ASSETS','GROUP','Fund Earning from Group Project','g_invt_ac:rec_interest_am:ADD;g_invt_ac:proj_interest_am:SUB_ZERO;g_ac:p_rec_int_on_loan_am:ADD;g_ac:p_proj_int_on_loan_am:SUB_ZERO;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:ADD;b_group_bank_account:interest_am:SUB;','g_invt_ac:rec_interest_am:SUB;g_invt_ac:proj_interest_am:ADD;g_ac:p_rec_int_on_loan_am:SUB;g_ac:p_proj_int_on_loan_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;b_group_bank_account:interest_am:ADD;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','g_invt_ac:rec_interest_am:SUB;g_invt_ac:proj_interest_am:ADD;g_ac:p_rec_int_on_loan_am:SUB;g_ac:p_proj_int_on_loan_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;b_group_bank_account:interest_am:ADD;'),(15,'Bank Loan Disbursement','BANK LOAN','RECEIPT','LIABILITIES','BANK','Receipt of Loan amount from Bank','g_loan_ac:loan_am:THIS;g_loan_ac:pending_principle_am:THIS;g_ac:borrowed_loan_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:ADD;b_group_bank_account:balance_update:SUB;','g_loan_ac:loan_am:THIS;g_loan_ac:pending_principle_am:SUB;g_ac:borrowed_loan_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;b_group_bank_account:balance_update:ADD;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;b_group_bank_account:balance_approve:SUB;','g_loan_ac:loan_am:THIS;g_loan_ac:pending_principle_am:SUB;g_ac:borrowed_loan_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;b_group_bank_account:balance_update:ADD;'),(16,'Bank Loan Installment','BANK LOAN','VOUCHER','LIABILITIES','BANK','Installment for the Loan taken from Bank','g_loan_ac:pending_principle_am:SUB;g_loan_ac:no_of_inst_paid:INCR;g_loan_ac:no_of_insall_late:INCR;g_ac:paid_borrowed_loan_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;b_group_bank_account:balance_update:ADD;','g_loan_ac:pending_principle_am:ADD;g_loan_ac:no_of_inst_paid:DECR;g_loan_ac:no_of_insall_late:DECR;g_ac:paid_borrowed_loan_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;b_group_bank_account:balance_update:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;b_group_bank_account:balance_approve:ADD;','g_loan_ac:pending_principle_am:ADD;g_loan_ac:no_of_inst_paid:DECR;g_loan_ac:no_of_insall_late:DECR;g_ac:paid_borrowed_loan_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;b_group_bank_account:balance_update:SUB;'),(17,'Bank Loan Interest Installment','BANK LOAN','VOUCHER','LIABILITIES','BANK','Interest Installment for the Loan taken from Bank','g_loan_ac:paid_interest_am:ADD;g_loan_ac:proj_interest_am:SUB_ZERO;g_ac:paid_int_on_borrowed_loan_am:ADD;g_ac:proj_int_on_borrowed_loan_am:SUB_ZERO;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;b_group_bank_account:interest_am:ADD;','g_loan_ac:paid_interest_am:SUB;g_loan_ac:proj_interest_am:ADD;g_ac:paid_int_on_borrowed_loan_am:SUB;g_ac:proj_int_on_borrowed_loan_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;b_group_bank_account:interest_am:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','g_loan_ac:paid_interest_am:SUB;g_loan_ac:proj_interest_am:ADD;g_ac:paid_int_on_borrowed_loan_am:SUB;g_ac:proj_int_on_borrowed_loan_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;b_group_bank_account:interest_am:SUB;'),(18,'Bank Loan Prepayment','BANK LOAN','VOUCHER','LIABILITIES','BANK','Prepayment of Loan taken fron Bank','m_loan_ac:pending_principle_am:SUB;g_ac:paid_borrowed_loan_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;b_group_bank_account:balance_update:ADD;','m_loan_ac:pending_principle_am:ADD;g_ac:paid_borrowed_loan_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;b_group_bank_account:balance_update:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;b_group_bank_account:balance_approve:ADD;','m_loan_ac:pending_principle_am:ADD;g_ac:paid_borrowed_loan_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;b_group_bank_account:balance_update:SUB;'),(19,'Bank Pre Interest Installment','BANK LOAN','VOUCHER','LIABILITIES','BANK','Prepayment of Loan taken fron Bank','g_loan_ac:pre_emi_interest_am:ADD;g_loan_ac:paid_interest_am:ADD;g_ac:paid_int_on_borrowed_loan_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;b_group_bank_account:interest_am:ADD;','g_loan_ac:pre_emi_interest_am:SUB;g_loan_ac:paid_interest_am:SUB;g_ac:paid_int_on_borrowed_loan_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;b_group_bank_account:interest_am:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','g_loan_ac:pre_emi_interest_am:SUB;g_loan_ac:paid_interest_am:SUB;g_ac:paid_int_on_borrowed_loan_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;b_group_bank_account:interest_am:SUB;'),(20,'Bank Withdrawal','TRANSFER','TRANSFER','TRANSFER','MEMBER','Withdrawal of Cash from Bank','g_ac:subj_clearing_bank_balance_am:SUB;g_ac:subj_clearing_cash_in_hand_am:ADD;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','g_ac:subj_clearing_bank_balance_am:ADD;g_ac:subj_clearing_cash_in_hand_am:SUB;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:subj_clearing_bank_balance_am:ADD;g_ac:subj_clearing_cash_in_hand_am:SUB;g_ac:clear_bank_balance_am:SUB;g_ac:clear_cash_in_hand_am:ADD;g_bank_account:balance_approve:SUB;','g_ac:subj_clearing_bank_balance_am:ADD;g_ac:subj_clearing_cash_in_hand_am:SUB;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(21,'Bank Transfer','TRANSFER','TRANSFER','TRANSFER','MEMBER','Cash Deposit to Bank Account','g_ac:subj_clearing_cash_in_hand_am:SUB;g_ac:subj_clearing_bank_balance_am:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:ADD;','g_ac:subj_clearing_cash_in_hand_am:ADD;g_ac:subj_clearing_bank_balance_am:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;','g_ac:subj_clearing_cash_in_hand_am:ADD;g_ac:subj_clearing_bank_balance_am:SUB;g_ac:clear_cash_in_hand_am:SUB;g_ac:clear_bank_balance_am:ADD;g_bank_account:balance_approve:ADD;','g_ac:subj_clearing_cash_in_hand_am:ADD;g_ac:subj_clearing_bank_balance_am:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;'),(22,'Divided Share Paid','PROFIT SHARE','VOUCHER','LOSS','MEMBER','Divided Shared paid to Associate Member','m_ac:divided_profit_paid_am:ADD;m_ac:divided_profit_declared_am:SUB;g_ac:a_m_divided_paid_am:ADD;g_ac:a_m_divided_declared_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','m_ac:divided_profit_paid_am:SUB;m_ac:divided_profit_declared_am:ADD;g_ac:a_m_divided_paid_am:SUB;g_ac:a_m_divided_declared_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','m_ac:divided_profit_paid_am:SUB;m_ac:divided_profit_declared_am:ADD;g_ac:a_m_divided_paid_am:SUB;g_ac:a_m_divided_declared_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(23,'Profit Share Paid','PROFIT SHARE','VOUCHER','LOSS','MEMBER','Profit Share paid to Core Member','m_ac:divided_profit_paid_am:ADD;m_ac:divided_profit_declared_am:SUB;g_ac:c_m_profit_share_paid_am:ADD;g_ac:c_m_profit_share_declared_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','m_ac:divided_profit_paid_am:SUB;m_ac:divided_profit_declared_am:ADD;g_ac:c_m_profit_share_paid_am:SUB;g_ac:c_m_profit_share_declared_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','m_ac:divided_profit_paid_am:SUB;m_ac:divided_profit_declared_am:ADD;g_ac:c_m_profit_share_paid_am:SUB;g_ac:c_m_profit_share_declared_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(24,'Divided Share Declare','PROFIT SHARE','INTERNAL','LOSS','MEMBER','Divided Shared Declare for Associate Member','m_ac:divided_profit_declared_am:ADD;g_ac:a_m_divided_declared_am:ADD;g_ac_by_txtype:amount:ADD;','m_ac:divided_profit_declared_am:SUB;g_ac:a_m_divided_declared_am:SUB;g_ac_by_txtype:amount:SUB;','','m_ac:divided_profit_declared_am:SUB;g_ac:a_m_divided_declared_am:SUB;g_ac_by_txtype:amount:SUB;'),(25,'Profit Share Declare','PROFIT SHARE','INTERNAL','LOSS','MEMBER','Profit Share Declare for Core Member','m_ac:divided_profit_declared_am:ADD;g_ac:c_m_profit_share_paid_am:ADD;g_ac_by_txtype:amount:ADD;','m_ac:divided_profit_declared_am:SUB;g_ac:c_m_profit_share_paid_am:SUB;g_ac_by_txtype:amount:SUB;','','m_ac:divided_profit_declared_am:SUB;g_ac:c_m_profit_share_paid_am:SUB;g_ac_by_txtype:amount:SUB;'),(26,'Bank Interest Earned','INVESTMENT','RECEIPT','PROFIT','BANK','Interest Earned on Bank Investment','g_ac:int_en_on_saving_ac_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_bank_account:interest_am:ADD;g_ac_by_txtype:amount:ADD;','g_ac:int_en_on_saving_ac_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_bank_account:interest_am:SUB;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','g_ac:int_en_on_saving_ac_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_bank_account:interest_am:SUB;g_ac_by_txtype:amount:SUB;'),(27,'Fix Deposit Investment','INVESTMENT','VOUCHER','ASSETS','BANK','Fix Deposit Investment in Bank','g_invt_ac:invt_am:THIS;g_ac:fix_deposit_inv_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;b_group_bank_account:balance_update:ADD;','g_invt_ac:invt_am:THIS;g_ac:fix_deposit_inv_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;b_group_bank_account:balance_update:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;b_group_bank_account:balance_approve:ADD;','g_invt_ac:invt_am:THIS;g_ac:fix_deposit_inv_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;b_group_bank_account:balance_update:SUB;'),(28,'Fix Deposit Recovery','INVESTMENT','RECEIPT','ASSETS','BANK','Recovery of Fix Deposit Investment in Bank','g_invt_ac:rec_invt_am:ADD;g_ac:rec_fix_deposit_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;b_group_bank_account:balance_update:SUB;','g_invt_ac:rec_invt_am:SUB;g_ac:rec_fix_deposit_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;b_group_bank_account:balance_update:ADD;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;b_group_bank_account:balance_approve:SUB;','g_invt_ac:rec_invt_am:SUB;g_ac:rec_fix_deposit_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;b_group_bank_account:balance_update:ADD;'),(29,'Fix Deposit Interest','INVESTMENT','RECEIPT','ASSETS','BANK','Recovered Interest on Fix Deposit Investment in Bank','g_invt_ac:rec_interest_am:ADD;g_invt_ac:proj_interest_am:SUB_ZERO;g_ac:rec_int_on_fix_deposit_am:ADD;g_ac:proj_int_on_fix_deposit_am:SUB_ZERO;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;b_group_bank_account:interest_am:SUB;','g_invt_ac:rec_interest_am:SUB;g_invt_ac:proj_interest_am:ADD;g_ac:rec_int_on_fix_deposit_am:SUB;g_ac:proj_int_on_fix_deposit_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;b_group_bank_account:interest_am:ADD;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','g_invt_ac:rec_interest_am:SUB;g_invt_ac:proj_interest_am:ADD;g_ac:rec_int_on_fix_deposit_am:SUB;g_ac:proj_int_on_fix_deposit_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;b_group_bank_account:interest_am:ADD;'),(30,'Other Investment','INVESTMENT','VOUCHER','ASSETS','BANK','Other Investments','g_invt_ac:invt_am:THIS;g_ac:other_inv_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;b_group_bank_account:balance_update:ADD;','g_invt_ac:invt_am:THIS;g_ac:other_inv_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;b_group_bank_account:balance_update:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;b_group_bank_account:balance_approve:ADD;','g_invt_ac:invt_am:THIS;g_ac:other_inv_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;b_group_bank_account:balance_update:SUB;'),(31,'Other Investment Recovery','INVESTMENT','RECEIPT','ASSETS','BANK','Recovery of Other Investments','g_invt_ac:rec_invt_am:ADD;g_ac:rec_other_inv_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;b_group_bank_account:balance_update:SUB;','g_invt_ac:rec_invt_am:SUB;g_ac:rec_other_inv_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;b_group_bank_account:balance_update:ADD;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;b_group_bank_account:balance_approve:SUB;','g_invt_ac:rec_invt_am:SUB;g_ac:rec_other_inv_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;b_group_bank_account:balance_update:ADD;'),(32,'Other Investment Earning','INVESTMENT','RECEIPT','ASSETS','BANK','Recovered Earnings on Other Investments','g_invt_ac:rec_interest_am:ADD;g_invt_ac:proj_interest_am:SUB_ZERO;g_ac:rec_int_on_other_inv_am:ADD;g_ac:proj_int_on_other_inv_am:SUB_ZERO;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;b_group_bank_account:interest_am:SUB;','g_invt_ac:rec_interest_am:SUB;g_invt_ac:proj_interest_am:ADD;g_ac:rec_int_on_other_inv_am:SUB;g_ac:proj_int_on_other_inv_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;b_group_bank_account:interest_am:ADD;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','g_invt_ac:rec_interest_am:SUB;g_invt_ac:proj_interest_am:ADD;g_ac:rec_int_on_other_inv_am:SUB;g_ac:proj_int_on_other_inv_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;b_group_bank_account:interest_am:ADD;'),(33,'Registration Fee','FEE PENALTY','RECEIPT','FEE','MEMBER','Fee for Registration at Member','m_ac:rec_penalty_am:ADD;g_ac:rec_penalty_am:ADD;g_ac:pen_shg_one_mem_reg_fee:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:ADD;','m_ac:rec_penalty_am:SUB;g_ac:rec_penalty_am:SUB;g_ac:pen_shg_one_mem_reg_fee:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','m_ac:rec_penalty_am:SUB;g_ac:rec_penalty_am:SUB;g_ac:pen_shg_one_mem_reg_fee:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;'),(34,'Late Fee','FEE PENALTY','RECEIPT','FEE','MEMBER','Fee for Late Payment','m_ac:rec_penalty_am:ADD;g_ac:rec_penalty_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:ADD;','m_ac:rec_penalty_am:SUB;g_ac:rec_penalty_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','m_ac:rec_penalty_am:SUB;g_ac:rec_penalty_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;'),(35,'Loan Processing Fee','FEE PENALTY','RECEIPT','FEE','MEMBER','Fee for Loan Processing','m_ac:rec_penalty_am:ADD;g_ac:rec_penalty_am:ADD;g_ac:pen_shg_one_service_charges:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:ADD;','m_ac:rec_penalty_am:SUB;g_ac:rec_penalty_am:SUB;g_ac:pen_shg_one_service_charges:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','m_ac:rec_penalty_am:SUB;g_ac:rec_penalty_am:SUB;g_ac:pen_shg_one_service_charges:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;'),(36,'Loan Prepayment Fee','FEE PENALTY','RECEIPT','FEE','MEMBER','Fee for Loan Prepayment','m_ac:rec_penalty_am:ADD;g_ac:rec_penalty_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:ADD;','m_ac:rec_penalty_am:SUB;g_ac:rec_penalty_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','m_ac:rec_penalty_am:SUB;g_ac:rec_penalty_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;'),(37,'Cheque Bouncing Penalty','FEE PENALTY','RECEIPT','FEE','MEMBER','Panalty for Cheque Bouncing','m_ac:rec_penalty_am:ADD;g_ac:rec_penalty_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:ADD;','m_ac:rec_penalty_am:SUB;g_ac:rec_penalty_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','m_ac:rec_penalty_am:SUB;g_ac:rec_penalty_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;'),(38,'Auto Debit Failure Penalty','FEE PENALTY','RECEIPT','FEE','MEMBER','Panalty for Auto Debit Failure','m_ac:rec_penalty_am:ADD;g_ac:rec_penalty_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:ADD;','m_ac:rec_penalty_am:SUB;g_ac:rec_penalty_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','m_ac:rec_penalty_am:SUB;g_ac:rec_penalty_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;'),(39,'Application Fee','FEE PENALTY','RECEIPT','FEE','MEMBER','Application Fee','g_ac:expenses_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(40,'Miscellaneous Fee','FEE PENALTY','RECEIPT','FEE','MEMBER','Miscellaneous Fee','g_ac:expenses_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(41,'Recovered Outstanding Fee','FEE PENALTY','RECEIPT','FEE','MEMBER','Revovered amount which was Outstanding Fee','m_ac:rec_penalty_am:ADD;m_ac:pending_penalty_am:SUB;g_ac:rec_penalty_am:ADD;g_ac:pending_penalty_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:ADD;','m_ac:rec_penalty_am:SUB;m_ac:pending_penalty_am:ADD;g_ac:rec_penalty_am:SUB;g_ac:pending_penalty_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','m_ac:rec_penalty_am:SUB;m_ac:pending_penalty_am:ADD;g_ac:rec_penalty_am:SUB;g_ac:pending_penalty_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;'),(42,'Outstanding Late Fee','OUTSTANDING FEE','INTERNAL','OUTSTANDING FEE','MEMBER','Outstanding Fee for Late Payment','m_ac:pending_penalty_am:ADD;g_ac:pending_penalty_am:ADD;g_ac_by_txtype:amount:ADD;','m_ac:pending_penalty_am:SUB;g_ac:pending_penalty_am:SUB;g_ac_by_txtype:amount:SUB;','','m_ac:pending_penalty_am:SUB;g_ac:pending_penalty_am:SUB;g_ac_by_txtype:amount:SUB;'),(43,'Outstanding Cheque Bouncing','OUTSTANDING FEE','INTERNAL','OUTSTANDING FEE','MEMBER','Outstanding Panalty for Cheque Bouncing','m_ac:pending_penalty_am:ADD;g_ac:pending_penalty_am:ADD;g_ac_by_txtype:amount:ADD;','m_ac:pending_penalty_am:SUB;g_ac:pending_penalty_am:SUB;g_ac_by_txtype:amount:SUB;','','m_ac:pending_penalty_am:SUB;g_ac:pending_penalty_am:SUB;g_ac_by_txtype:amount:SUB;'),(44,'Outstanding Auto Debit Fail','OUTSTANDING FEE','INTERNAL','OUTSTANDING FEE','MEMBER','Outstanding Panalty for Auto Debit Failure','m_ac:pending_penalty_am:ADD;g_ac:pending_penalty_am:ADD;g_ac_by_txtype:amount:ADD;','m_ac:pending_penalty_am:SUB;g_ac:pending_penalty_am:SUB;g_ac_by_txtype:amount:SUB;','','m_ac:pending_penalty_am:SUB;g_ac:pending_penalty_am:SUB;g_ac_by_txtype:amount:SUB;'),(45,'Outstanding Miscellaneous Fee','OUTSTANDING FEE','INTERNAL','OUTSTANDING FEE','MEMBER','Outstanding Miscellaneous Fee','m_ac:pending_penalty_am:ADD;g_ac:pending_penalty_am:ADD;g_ac_by_txtype:amount:ADD;','m_ac:pending_penalty_am:SUB;g_ac:pending_penalty_am:SUB;g_ac_by_txtype:amount:SUB;','','m_ac:pending_penalty_am:SUB;g_ac:pending_penalty_am:SUB;g_ac_by_txtype:amount:SUB;'),(46,'Stationary Expense','EXPENSE','VOUCHER','EXPENSE','MEMBER','Expenses done for Stationary','g_ac:expenses_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(47,'Furniture & Fixture Expense','EXPENSE','VOUCHER','EXPENSE','MEMBER','Expenses done for Furniture & Fixture','g_ac:expenses_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(48,'Telephone & Postage Expense','EXPENSE','VOUCHER','EXPENSE','MEMBER','Expenses done for Telephone & Postage','g_ac:expenses_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(49,'Office Expense','EXPENSE','VOUCHER','EXPENSE','MEMBER','Expenses done for Office','g_ac:expenses_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(50,'Salary Expense','EXPENSE','VOUCHER','EXPENSE','MEMBER','Expenses done for Salary','g_ac:expenses_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(51,'Advertisement Expense','EXPENSE','VOUCHER','EXPENSE','MEMBER','Expenses done for Advertisement','g_ac:expenses_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(52,'Meeting Expense','EXPENSE','VOUCHER','EXPENSE','MEMBER','Expenses done for Meeting','g_ac:expenses_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(53,'Events Expense','EXPENSE','VOUCHER','EXPENSE','MEMBER','Expenses done for Events','g_ac:expenses_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(54,'Training Expense','EXPENSE','VOUCHER','EXPENSE','MEMBER','Expenses done for Training','g_ac:expenses_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(55,'Travel Expense','EXPENSE','VOUCHER','EXPENSE','MEMBER','Expenses done for Travel','g_ac:expenses_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(56,'Miscellaneous Expense','EXPENSE','VOUCHER','EXPENSE','MEMBER','Expenses done for Miscellaneous','g_ac:expenses_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(57,'Bank Charges Expense','EXPENSE','VOUCHER','EXPENSE','BANK','Expenses done for Bank Charges','g_ac:expenses_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;g_bank_account:penalty_charges_am:SUB;b_group_bank_account:penalty_charges_am:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;g_bank_account:penalty_charges_am:ADD;b_group_bank_account:penalty_charges_am:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;g_bank_account:penalty_charges_am:ADD;b_group_bank_account:penalty_charges_am:SUB;'),(58,'Bank Penalty Expense','EXPENSE','VOUCHER','EXPENSE','BANK','Expenses done for Bank Penalty','g_ac:expenses_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;g_bank_account:penalty_charges_am:SUB;b_group_bank_account:penalty_charges_am:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;g_bank_account:penalty_charges_am:ADD;b_group_bank_account:penalty_charges_am:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;g_bank_account:penalty_charges_am:ADD;b_group_bank_account:penalty_charges_am:SUB;'),(59,'Accounting Charges Expense','EXPENSE','VOUCHER','EXPENSE','MEMBER','Expenses done for Accounting Charges','g_ac:expenses_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(60,'SHGOne Charges Expense','EXPENSE','VOUCHER','EXPENSE','MEMBER','Expenses done for SHG-One.Net Charges','g_ac:expenses_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;');
/*!40000 ALTER TABLE `tx_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ui_access_code`
--

DROP TABLE IF EXISTS `ui_access_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ui_access_code` (
  `access_code_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `access_code` varchar(45) NOT NULL,
  `access_code_display` varchar(45) NOT NULL,
  `access_code_category` varchar(45) NOT NULL,
  `access_code_desc` varchar(200) DEFAULT NULL,
  `access_level` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`access_code_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ui_access_code`
--

LOCK TABLES `ui_access_code` WRITE;
/*!40000 ALTER TABLE `ui_access_code` DISABLE KEYS */;
INSERT INTO `ui_access_code` VALUES (1,'GROUP_DASHBOARD','Group Dashboard','DASHBOARD','','NO_ACCESS:READ'),(2,'MEMBER_DASHBOARD','Member Dashboard','DASHBOARD','','NO_ACCESS:READ'),(3,'MEMBER_WISE_DASHBOARD','All Memebers in Group Dashboard','DASHBOARD','','NO_ACCESS:READ'),(4,'MEMBER_AC','Member Account','MEMBER','','NO_ACCESS:READ:UPDATE:APPROVE'),(5,'MEMBER_LOAN_AC','Member Loan Account','MEMBER','','NO_ACCESS:READ:UPDATE:APPROVE'),(6,'SHG_GROUP_AC','SHG Group Account','GROUP','','NO_ACCESS:READ:UPDATE:APPROVE'),(7,'ADD_TRANSACTION','Add Transactions','TRANSACTION','','NO_ACCESS:READ:UPDATE:APPROVE'),(8,'TRANSACTION_TRACKING','Group Transaction Tracking','TRANSACTION','','NO_ACCESS:READ:UPDATE:APPROVE'),(9,'BANK_INTERACTION','Bank Interaction','BANK','','NO_ACCESS:READ:UPDATE:APPROVE'),(10,'MEMBER_ACCOUNT_BOOK','Member Account Book','REPORT','','NO_ACCESS:READ:UPDATE:APPROVE'),(11,'GROUP_ACCOUNT_BOOK','Group Account Book','REPORT','','NO_ACCESS:READ:UPDATE:APPROVE'),(12,'GROUP_ACCOUNTS_TRACKING','Group Accounts Tracking','REPORT','','NO_ACCESS:READ:UPDATE:APPROVE'),(13,'REPORTS','Reports for SHG & Bank','REPORT','','NO_ACCESS:READ'),(14,'SHG_GROUP_RULES','SHG Group Rules','GROUP','','NO_ACCESS:READ:UPDATE:APPROVE'),(15,'ACCESS_RIGHTS','Access Rights','ADMIN','','NO_ACCESS:READ:UPDATE:APPROVE'),(16,'MANAGE_SHG','Manage Multiple SHG Groups','ADMIN','','NO_ACCESS:READ:UPDATE:APPROVE'),(17,'FIRM_GROUP_AC','Firm Group Account','GROUP','','NO_ACCESS:READ:UPDATE:APPROVE'),(18,'GROUP_MEMBERS_MAPPING','Group To Member Mapping','GROUP','','NO_ACCESS:READ:UPDATE:APPROVE'),(19,'MANAGE_DATA','Manage Bulk Data','ADMIN','','NO_ACCESS:READ:UPDATE:APPROVE'),(20,'ADMIN_NET','Admin Software Config','ADMIN','','NO_ACCESS:READ:UPDATE:APPROVE');
/*!40000 ALTER TABLE `ui_access_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ws_access_code`
--

DROP TABLE IF EXISTS `ws_access_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ws_access_code` (
  `access_code_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `access_code` varchar(45) NOT NULL,
  `access_code_display` varchar(45) NOT NULL,
  `access_code_category` varchar(45) NOT NULL,
  `access_code_desc` varchar(200) DEFAULT NULL,
  `access_level` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`access_code_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ws_access_code`
--

LOCK TABLES `ws_access_code` WRITE;
/*!40000 ALTER TABLE `ws_access_code` DISABLE KEYS */;
INSERT INTO `ws_access_code` VALUES (1,'GROUP_DASHBOARD','Group Dashboard','DASHBOARD','','NO_ACCESS:READ'),(2,'MEMBER_DASHBOARD','Member Dashboard','DASHBOARD','','NO_ACCESS:READ'),(3,'MEMBER_WISE_DASHBOARD','All Memebers in Group Dashboard','DASHBOARD','','NO_ACCESS:READ'),(4,'MEMBER_AC','Member Account','MEMBER','','NO_ACCESS:READ:UPDATE:APPROVE'),(5,'MEMBER_LOAN_AC','Member Loan Account','MEMBER','','NO_ACCESS:READ:UPDATE:APPROVE'),(6,'SHG_GROUP_AC','SHG Group Account','GROUP','','NO_ACCESS:READ:UPDATE:APPROVE'),(7,'SHG_GROUP_RULES','SHG Group Rules','GROUP','','NO_ACCESS:READ:UPDATE:APPROVE'),(8,'FIRM_GROUP_AC','Firm Group Account','GROUP','','NO_ACCESS:READ:UPDATE:APPROVE'),(9,'GROUP_MEMBERS_MAPPING','Group To Member Mapping','GROUP','','NO_ACCESS:READ:UPDATE:APPROVE'),(10,'MEMBER_TRANSACTION','Member Transaction','TRANSACTION','','NO_ACCESS:READ:UPDATE:APPROVE'),(11,'GROUP_TRANSACTION','Group Transaction','TRANSACTION','','NO_ACCESS:READ:UPDATE:APPROVE'),(12,'BANK_INTERACTION','Bank Interaction','BANK','','NO_ACCESS:READ:UPDATE:APPROVE'),(13,'MEMBER_ACCOUNT_BOOK','Member Account Book','REPORT','','NO_ACCESS:READ'),(14,'GROUP_ACCOUNT_BOOK','Group Account Book','REPORT','','NO_ACCESS:READ'),(15,'REPORTS','Reports for SHG & Bank','REPORT','','NO_ACCESS:READ'),(16,'ACCESS_RIGHTS','Access Rights','ADMIN','','NO_ACCESS:READ:UPDATE:APPROVE'),(17,'MANAGE_SHG','Manage Multiple SHG Groups','ADMIN','','NO_ACCESS:READ:UPDATE:APPROVE'),(18,'MANAGE_DATA','Manage Bulk Data','ADMIN','','NO_ACCESS:READ:UPDATE:APPROVE'),(19,'ADMIN_NET','Admin Software Config','ADMIN','','NO_ACCESS:UPDATE');
/*!40000 ALTER TABLE `ws_access_code` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-02-05  0:01:28
