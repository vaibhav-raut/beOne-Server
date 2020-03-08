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
  `bank_name` varchar(100) NOT NULL,
  `branch_name` varchar(100) NOT NULL,
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
-- Table structure for table `brand`
--

DROP TABLE IF EXISTS `brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `brand` (
  `brand_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `manufacture_ac_no` bigint(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `name_display` varchar(15) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `properties` varchar(200) DEFAULT NULL,
  `link` varchar(200) DEFAULT NULL,
  `no_stocked_types` int(10) DEFAULT NULL,
  `no_lots` int(10) DEFAULT NULL,
  `no_per_set` int(10) DEFAULT NULL,
  `no_min_sets` int(10) DEFAULT NULL,
  `total_purchase_am` decimal(16,2) DEFAULT NULL,
  `total_stock_am` decimal(16,2) DEFAULT NULL,
  `total_sold_am` decimal(16,2) DEFAULT NULL,
  `total_sold_discount_am` decimal(16,2) DEFAULT NULL,
  `total_damage_am` decimal(16,2) DEFAULT NULL,
  `total_purchase_no` int(10) DEFAULT NULL,
  `total_stock_no` int(10) DEFAULT NULL,
  `total_sold_no` int(10) DEFAULT NULL,
  `total_sold_discount_no` int(10) DEFAULT NULL,
  `total_damage_no` int(10) DEFAULT NULL,
  `current_ordered_stock_am` decimal(16,2) DEFAULT NULL,
  `current_delivered_stock_am` decimal(16,2) DEFAULT NULL,
  `current_to_stock_am` decimal(16,2) DEFAULT NULL,
  `current_created_stock_am` decimal(16,2) DEFAULT NULL,
  `current_stock_am` decimal(16,2) DEFAULT NULL,
  `current_stock_discount_am` decimal(16,2) DEFAULT NULL,
  `current_ordered_stock_no` int(10) DEFAULT NULL,
  `current_delivered_stock_no` int(10) DEFAULT NULL,
  `current_to_stock_no` int(10) DEFAULT NULL,
  `current_created_stock_no` int(10) DEFAULT NULL,
  `current_stock_no` int(10) DEFAULT NULL,
  `current_stock_discount_no` int(10) DEFAULT NULL,
  `first_stock_price_am` decimal(16,2) DEFAULT NULL,
  `last_stock_price_am` decimal(16,2) DEFAULT NULL,
  `avg_mr_item_sold_am` decimal(16,2) DEFAULT NULL,
  `first_lot_ts` timestamp NULL DEFAULT NULL,
  `last_lot_ts` timestamp NULL DEFAULT NULL,
  `return_counter` int(10) DEFAULT NULL,
  `performance_index` float DEFAULT NULL,
  `return_index` float DEFAULT NULL,
  `sales_index` float DEFAULT NULL,
  `sales_20_per_days` float DEFAULT NULL,
  `sales_40_per_days` float DEFAULT NULL,
  `sales_60_per_days` float DEFAULT NULL,
  `sales_80_per_days` float DEFAULT NULL,
  `sales_100_per_days` float DEFAULT NULL,
  PRIMARY KEY (`brand_id`),
  KEY `brand_fk1_idx` (`manufacture_ac_no`),
  CONSTRAINT `brand_fk1` FOREIGN KEY (`manufacture_ac_no`) REFERENCES `g_profile` (`g_ac_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brand`
--

LOCK TABLES `brand` WRITE;
/*!40000 ALTER TABLE `brand` DISABLE KEYS */;
/*!40000 ALTER TABLE `brand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `catalog_item`
--

DROP TABLE IF EXISTS `catalog_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `catalog_item` (
  `catalog_item_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `stock_type_id` bigint(20) unsigned NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `properties` varchar(200) DEFAULT NULL,
  `pics` varchar(200) DEFAULT NULL,
  `link` varchar(200) DEFAULT NULL,
  `mr_price_am` decimal(16,2) DEFAULT NULL,
  `mrp_price_am` decimal(16,2) DEFAULT NULL,
  PRIMARY KEY (`catalog_item_id`),
  KEY `catalog_item_fk1_idx` (`stock_type_id`),
  CONSTRAINT `catalog_item_fk1` FOREIGN KEY (`stock_type_id`) REFERENCES `stock_type` (`stock_type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `catalog_item`
--

LOCK TABLES `catalog_item` WRITE;
/*!40000 ALTER TABLE `catalog_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `catalog_item` ENABLE KEYS */;
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
  `state` varchar(50) NOT NULL,
  `division` varchar(50) DEFAULT NULL,
  `district` varchar(50) NOT NULL,
  `lang_id` int(10) unsigned NOT NULL,
  `district_code` char(5) DEFAULT NULL,
  `group_counter` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`district_id`),
  KEY `district_FKIndex1` (`lang_id`),
  CONSTRAINT `district_ibfk_1` FOREIGN KEY (`lang_id`) REFERENCES `lang` (`lang_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=685 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `district`
--

LOCK TABLES `district` WRITE;
/*!40000 ALTER TABLE `district` DISABLE KEYS */;
INSERT INTO `district` VALUES (1,'Admin','Admin','Super Admin',1,'AD01',0),(2,'Admin','TEST','TEST1',1,'TS01',0),(3,'Admin','Udaan','Udaan',1,'UD01',0),(4,'Admin','Udaan','Vendor',1,'UD02',0),(5,'Admin','Udaan','Temp1',1,'UD03',0),(6,'Admin','Udaan','Temp2',1,'UD04',0),(7,'Admin','Udaan','Temp3',1,'UD05',0),(8,'Andaman and Nicobar','Andaman and Nicobar','Nicobar',1,'AN02',0),(9,'Andaman and Nicobar','Andaman and Nicobar','North And Middle Andaman',1,'AN03',0),(10,'Andaman and Nicobar','Andaman and Nicobar','South Andaman',1,'AN01',0),(11,'Andhra Pradesh','Andhra Pradesh','Anantapur',1,'AP02',0),(12,'Andhra Pradesh','Andhra Pradesh','Chittoor',1,'AP03',0),(13,'Andhra Pradesh','Andhra Pradesh','East Godavari',1,'AP05',0),(14,'Andhra Pradesh','Andhra Pradesh','Guntur',1,'AP07',0),(15,'Andhra Pradesh','Andhra Pradesh','Kadapa',1,'AP04',0),(16,'Andhra Pradesh','Andhra Pradesh','Krishna',1,'AP16',0),(17,'Andhra Pradesh','Andhra Pradesh','Kurnool',1,'AP21',0),(18,'Andhra Pradesh','Andhra Pradesh','Prakasam',1,'AP27',0),(19,'Andhra Pradesh','Andhra Pradesh','Sri Potti Sriramulu Nellore',1,'AP26',0),(20,'Andhra Pradesh','Andhra Pradesh','Srikakulam',1,'AP30',0),(21,'Andhra Pradesh','Andhra Pradesh','Visakhapatnam',1,'AP31',0),(22,'Andhra Pradesh','Andhra Pradesh','Vizianagaram',1,'AP35',0),(23,'Andhra Pradesh','Andhra Pradesh','West Godavari',1,'AP37',0),(24,'Arunachal Pradesh','Arunachal Pradesh','Anjaw',1,'AR17',0),(25,'Arunachal Pradesh','Arunachal Pradesh','Changlang',1,'AR12',0),(26,'Arunachal Pradesh','Arunachal Pradesh','Dibang Valley',1,'AR10',0),(27,'Arunachal Pradesh','Arunachal Pradesh','East Kameng',1,'AR05',0),(28,'Arunachal Pradesh','Arunachal Pradesh','East Siang',1,'AR09',0),(29,'Arunachal Pradesh','Arunachal Pradesh','Kra Daadi',1,'AR18',0),(30,'Arunachal Pradesh','Arunachal Pradesh','Kurung Kumey',1,'AR15',0),(31,'Arunachal Pradesh','Arunachal Pradesh','Lohit',1,'AR11',0),(32,'Arunachal Pradesh','Arunachal Pradesh','Longding',1,'AR19',0),(33,'Arunachal Pradesh','Arunachal Pradesh','Lower Dibang Valley',1,'AR20',0),(34,'Arunachal Pradesh','Arunachal Pradesh','Lower Subansiri',1,'AR06',0),(35,'Arunachal Pradesh','Arunachal Pradesh','Namsai',1,'AR21',0),(36,'Arunachal Pradesh','Arunachal Pradesh','Papum Pare',1,'AR22',0),(37,'Arunachal Pradesh','Arunachal Pradesh','Tawang',1,'AR03',0),(38,'Arunachal Pradesh','Arunachal Pradesh','Tirap',1,'AR13',0),(39,'Arunachal Pradesh','Arunachal Pradesh','Upper Siang',1,'AR14',0),(40,'Arunachal Pradesh','Arunachal Pradesh','Upper Subansiri',1,'AR07',0),(41,'Arunachal Pradesh','Arunachal Pradesh','West Kameng',1,'AR04',0),(42,'Arunachal Pradesh','Arunachal Pradesh','West Siang',1,'AR08',0),(43,'Assam','Assam','Baksa',1,'AS28',0),(44,'Assam','Assam','Barpeta',1,'AS15',0),(45,'Assam','Assam','Bongaigaon',1,'AS19',0),(46,'Assam','Assam','Cachar',1,'AS11',0),(47,'Assam','Assam','Chirang',1,'AS26',0),(48,'Assam','Assam','Darrang',1,'AS13',0),(49,'Assam','Assam','Dhemaji',1,'AS22',0),(50,'Assam','Assam','Dhubri',1,'AS17',0),(51,'Assam','Assam','Dibrugarh',1,'AS06',0),(52,'Assam','Assam','Dima Hasao',1,'AS08',0),(53,'Assam','Assam','Goalpara',1,'AS18',0),(54,'Assam','Assam','Golaghat',1,'AS05',0),(55,'Assam','Assam','Hailakandi',1,'AS24',0),(56,'Assam','Assam','Jorhat',1,'AS03',0),(57,'Assam','Assam','Kamrup',1,'AS25',0),(58,'Assam','Assam','Kamrup Metropolitan',1,'AS01',0),(59,'Assam','Assam','Karbi Anglong',1,'AS09',0),(60,'Assam','Assam','Karimganj',1,'AS10',0),(61,'Assam','Assam','Kokrajhar',1,'AS16',0),(62,'Assam','Assam','Lakhimpur',1,'AS07',0),(63,'Assam','Assam','Morigaon',1,'AS21',0),(64,'Assam','Assam','Nagaon',1,'AS02',0),(65,'Assam','Assam','Nalbari',1,'AS14',0),(66,'Assam','Assam','Sivasagar',1,'AS04',0),(67,'Assam','Assam','Sonitpur',1,'AS12',0),(68,'Assam','Assam','Tinsukia',1,'AS23',0),(69,'Assam','Assam','Udalguri',1,'AS27',0),(70,'Bihar','Bihar','Araria',1,'BR38',0),(71,'Bihar','Bihar','Arwal',1,'BR56',0),(72,'Bihar','Bihar','Aurangabad',1,'BR26',0),(73,'Bihar','Bihar','Banka',1,'BR51',0),(74,'Bihar','Bihar','Begusarai',1,'BR09',0),(75,'Bihar','Bihar','Bhagalpur',1,'BR10',0),(76,'Bihar','Bihar','Bhojpur',1,'BR03',0),(77,'Bihar','Bihar','Buxar',1,'BR44',0),(78,'Bihar','Bihar','Darbhanga',1,'BR07',0),(79,'Bihar','Bihar','East Champaran',1,'BR05',0),(80,'Bihar','Bihar','Gaya',1,'BR02',0),(81,'Bihar','Bihar','Gopalganj',1,'BR28',0),(82,'Bihar','Bihar','Jamui',1,'BR46',0),(83,'Bihar','Bihar','Jehanabad',1,'BR25',0),(84,'Bihar','Bihar','Kaimur',1,'BR45',0),(85,'Bihar','Bihar','Katihar',1,'BR39',0),(86,'Bihar','Bihar','Khagaria',1,'BR34',0),(87,'Bihar','Bihar','Kishanganj',1,'BR37',0),(88,'Bihar','Bihar','Lakhisarai',1,'BR53',0),(89,'Bihar','Bihar','Madhepura',1,'BR43',0),(90,'Bihar','Bihar','Madhubani',1,'BR32',0),(91,'Bihar','Bihar','Munger',1,'BR08',0),(92,'Bihar','Bihar','Muzaffarpur',1,'BR06',0),(93,'Bihar','Bihar','Nalanda',1,'BR21',0),(94,'Bihar','Bihar','Nawada',1,'BR27',0),(95,'Bihar','Bihar','Patna',1,'BR01',0),(96,'Bihar','Bihar','Purnia',1,'BR11',0),(97,'Bihar','Bihar','Rohtas',1,'BR57',0),(98,'Bihar','Bihar','Saharsa',1,'BR19',0),(99,'Bihar','Bihar','Samastipur',1,'BR33',0),(100,'Bihar','Bihar','Saran',1,'BR04',0),(101,'Bihar','Bihar','Sheikhpura',1,'BR52',0),(102,'Bihar','Bihar','Sheohar',1,'BR55',0),(103,'Bihar','Bihar','Sitamarhi',1,'BR30',0),(104,'Bihar','Bihar','Siwan',1,'BR29',0),(105,'Bihar','Bihar','Supaul',1,'BR50',0),(106,'Bihar','Bihar','Vaishali',1,'BR31',0),(107,'Bihar','Bihar','West Champaran',1,'BR22',0),(108,'Chandigarh','Chandigarh','Chandigarh',1,'CH01',0),(109,'Chhattisgarh','Chhattisgarh','Balod',1,'CG24',0),(110,'Chhattisgarh','Chhattisgarh','Baloda Bazar',1,'CG22',0),(111,'Chhattisgarh','Chhattisgarh','Balrampur',1,'CG30',0),(112,'Chhattisgarh','Chhattisgarh','Bastar',1,'CG17',0),(113,'Chhattisgarh','Chhattisgarh','Bemetara',1,'CG25',0),(114,'Chhattisgarh','Chhattisgarh','Bijapur',1,'CG20',0),(115,'Chhattisgarh','Chhattisgarh','Bilaspur',1,'CG10',0),(116,'Chhattisgarh','Chhattisgarh','Dantewada',1,'CG18',0),(117,'Chhattisgarh','Chhattisgarh','Dhamtari',1,'CG05',0),(118,'Chhattisgarh','Chhattisgarh','Durg',1,'CG07',0),(119,'Chhattisgarh','Chhattisgarh','Gariaband',1,'CG23',0),(120,'Chhattisgarh','Chhattisgarh','JanjgirChampa',1,'CG11',0),(121,'Chhattisgarh','Chhattisgarh','Jashpur',1,'CG14',0),(122,'Chhattisgarh','Chhattisgarh','Kabirdham',1,'CG09',0),(123,'Chhattisgarh','Chhattisgarh','Kanker',1,'CG19',0),(124,'Chhattisgarh','Chhattisgarh','Kondagaon',1,'CG27',0),(125,'Chhattisgarh','Chhattisgarh','Korba',1,'CG12',0),(126,'Chhattisgarh','Chhattisgarh','Koriya',1,'CG16',0),(127,'Chhattisgarh','Chhattisgarh','Mahasamund',1,'CG06',0),(128,'Chhattisgarh','Chhattisgarh','Mungeli',1,'CG28',0),(129,'Chhattisgarh','Chhattisgarh','Narayanpur',1,'CG21',0),(130,'Chhattisgarh','Chhattisgarh','Raigarh',1,'CG13',0),(131,'Chhattisgarh','Chhattisgarh','Raipur',1,'CG04',0),(132,'Chhattisgarh','Chhattisgarh','Rajnandgaon',1,'CG08',0),(133,'Chhattisgarh','Chhattisgarh','Sukma',1,'CG26',0),(134,'Chhattisgarh','Chhattisgarh','Surajpur',1,'CG29',0),(135,'Chhattisgarh','Chhattisgarh','Surguja',1,'CG15',0),(136,'Dadra and Nagar Haveli','Dadra and Nagar Haveli','Dadra And Nagar Haveli',1,'DN09',0),(137,'Daman and Diu','Daman and Diu','Daman',1,'DD03',0),(138,'Daman and Diu','Daman and Diu','Diu',1,'DD02',0),(139,'Delhi','Delhi','Central Delhi',1,'DL06',0),(140,'Delhi','Delhi','East Delhi',1,'DL07',0),(141,'Delhi','Delhi','New Delhi',1,'DL02',0),(142,'Delhi','Delhi','North Delhi',1,'DL01',0),(143,'Delhi','Delhi','North East Delhi',1,'DL05',0),(144,'Delhi','Delhi','North West Delhi',1,'DL08',0),(145,'Delhi','Delhi','Shahdara',1,'DL13',0),(146,'Delhi','Delhi','South Delhi',1,'DL03',0),(147,'Delhi','Delhi','South East Delhi',1,'DL19',0),(148,'Delhi','Delhi','South West Delhi',1,'DL09',0),(149,'Delhi','Delhi','West Delhi',1,'DL04',0),(150,'Goa','Goa','North Goa',1,'GA01',0),(151,'Goa','Goa','South Goa',1,'GA08',0),(152,'Gujarat','Gujarat','Ahmedabad',1,'GJ01',0),(153,'Gujarat','Gujarat','Amreli',1,'GJ14',0),(154,'Gujarat','Gujarat','Anand',1,'GJ23',0),(155,'Gujarat','Gujarat','Aravalli',1,'GJ33',0),(156,'Gujarat','Gujarat','Banaskantha',1,'GJ08',0),(157,'Gujarat','Gujarat','Bharuch',1,'GJ16',0),(158,'Gujarat','Gujarat','Bhavnagar',1,'GJ04',0),(159,'Gujarat','Gujarat','Botad',1,'GJ32',0),(160,'Gujarat','Gujarat','Chhota Udaipur',1,'GJ37',0),(161,'Gujarat','Gujarat','Dahod',1,'GJ20',0),(162,'Gujarat','Gujarat','Dang',1,'GJ30',0),(163,'Gujarat','Gujarat','Devbhoomi Dwarka',1,'GJ34',0),(164,'Gujarat','Gujarat','Gandhinagar',1,'GJ18',0),(165,'Gujarat','Gujarat','Gir Somnath',1,'GJ38',0),(166,'Gujarat','Gujarat','Jamnagar',1,'GJ10',0),(167,'Gujarat','Gujarat','Junagadh',1,'GJ11',0),(168,'Gujarat','Gujarat','Kutch',1,'GJ12',0),(169,'Gujarat','Gujarat','Kheda',1,'GJ07',0),(170,'Gujarat','Gujarat','Mahisagar',1,'GJ35',0),(171,'Gujarat','Gujarat','Mehsana',1,'GJ02',0),(172,'Gujarat','Gujarat','Morbi',1,'GJ36',0),(173,'Gujarat','Gujarat','Narmada',1,'GJ22',0),(174,'Gujarat','Gujarat','Navsari',1,'GJ21',0),(175,'Gujarat','Gujarat','Panchmahal',1,'GJ17',0),(176,'Gujarat','Gujarat','Patan',1,'GJ24',0),(177,'Gujarat','Gujarat','Porbandar',1,'GJ25',0),(178,'Gujarat','Gujarat','Rajkot',1,'GJ03',0),(179,'Gujarat','Gujarat','Sabarkantha',1,'GJ09',0),(180,'Gujarat','Gujarat','Surat',1,'GJ05',0),(181,'Gujarat','Gujarat','Surendranagar',1,'GJ13',0),(182,'Gujarat','Gujarat','Tapi',1,'GJ26',0),(183,'Gujarat','Gujarat','Vadodara',1,'GJ06',0),(184,'Gujarat','Gujarat','Valsad',1,'GJ15',0),(185,'Haryana','Haryana','Ambala',1,'HR01',0),(186,'Haryana','Haryana','Bhiwani',1,'HR16',0),(187,'Haryana','Haryana','Faridabad',1,'HR51',0),(188,'Haryana','Haryana','Fatehabad',1,'HR22',0),(189,'Haryana','Haryana','Gurgaon',1,'HR26',0),(190,'Haryana','Haryana','Hissar',1,'HR20',0),(191,'Haryana','Haryana','Jhajjar',1,'HR14',0),(192,'Haryana','Haryana','Jind',1,'HR09',0),(193,'Haryana','Haryana','Kaithal',1,'HR08',0),(194,'Haryana','Haryana','Karnal',1,'HR05',0),(195,'Haryana','Haryana','Kurukshetra',1,'HR07',0),(196,'Haryana','Haryana','Mahendragarh',1,'HR34',0),(197,'Haryana','Haryana','Mewat',1,'HR74',0),(198,'Haryana','Haryana','Palwal',1,'HR30',0),(199,'Haryana','Haryana','Panchkula',1,'HR03',0),(200,'Haryana','Haryana','Panipat',1,'HR06',0),(201,'Haryana','Haryana','Rewari',1,'HR36',0),(202,'Haryana','Haryana','Rohtak',1,'HR12',0),(203,'Haryana','Haryana','Sirsa',1,'HR24',0),(204,'Haryana','Haryana','Sonipat',1,'HR86',0),(205,'Haryana','Haryana','Yamuna Nagar',1,'HR90',0),(206,'Himachal Pradesh','Himachal Pradesh','Bilaspur',1,'HP24',0),(207,'Himachal Pradesh','Himachal Pradesh','Chamba',1,'HP48',0),(208,'Himachal Pradesh','Himachal Pradesh','Hamirpur',1,'HP22',0),(209,'Himachal Pradesh','Himachal Pradesh','Kangra',1,'HP40',0),(210,'Himachal Pradesh','Himachal Pradesh','Kinnaur',1,'HP25',0),(211,'Himachal Pradesh','Himachal Pradesh','Kullu',1,'HP34',0),(212,'Himachal Pradesh','Himachal Pradesh','Lahaul And Spiti',1,'HP42',0),(213,'Himachal Pradesh','Himachal Pradesh','Mandi',1,'HP05',0),(214,'Himachal Pradesh','Himachal Pradesh','Shimla',1,'HP07',0),(215,'Himachal Pradesh','Himachal Pradesh','Sirmaur',1,'HP18',0),(216,'Himachal Pradesh','Himachal Pradesh','Solan',1,'HP14',0),(217,'Himachal Pradesh','Himachal Pradesh','Una',1,'HP20',0),(218,'Jammu and Kashmir','Jammu and Kashmir','Anantnag',1,'JK03',0),(219,'Jammu and Kashmir','Jammu and Kashmir','Bandipora',1,'JK15',0),(220,'Jammu and Kashmir','Jammu and Kashmir','Baramulla',1,'JK05',0),(221,'Jammu and Kashmir','Jammu and Kashmir','Badgam',1,'JK04',0),(222,'Jammu and Kashmir','Jammu and Kashmir','Doda',1,'JK06',0),(223,'Jammu and Kashmir','Jammu and Kashmir','Ganderbal',1,'JK16',0),(224,'Jammu and Kashmir','Jammu and Kashmir','Jammu',1,'JK02',0),(225,'Jammu and Kashmir','Jammu and Kashmir','Kargil',1,'JK07',0),(226,'Jammu and Kashmir','Jammu and Kashmir','Kathua',1,'JK08',0),(227,'Jammu and Kashmir','Jammu and Kashmir','Kishtwar',1,'JK17',0),(228,'Jammu and Kashmir','Jammu and Kashmir','Kulgam',1,'JK18',0),(229,'Jammu and Kashmir','Jammu and Kashmir','Kupwara',1,'JK09',0),(230,'Jammu and Kashmir','Jammu and Kashmir','Leh',1,'JK10',0),(231,'Jammu and Kashmir','Jammu and Kashmir','Poonch',1,'JK12',0),(232,'Jammu and Kashmir','Jammu and Kashmir','Pulwama',1,'JK13',0),(233,'Jammu and Kashmir','Jammu and Kashmir','Rajouri',1,'JK11',0),(234,'Jammu and Kashmir','Jammu and Kashmir','Ramban',1,'JK19',0),(235,'Jammu and Kashmir','Jammu and Kashmir','Reasi',1,'JK20',0),(236,'Jammu and Kashmir','Jammu and Kashmir','Samba',1,'JK21',0),(237,'Jammu and Kashmir','Jammu and Kashmir','Shopian',1,'JK22',0),(238,'Jammu and Kashmir','Jammu and Kashmir','Srinagar',1,'JK01',0),(239,'Jammu and Kashmir','Jammu and Kashmir','Udhampur',1,'JK14',0),(240,'Jharkhand','Jharkhand','Bokaro',1,'JH09',0),(241,'Jharkhand','Jharkhand','Chatra',1,'JH13',0),(242,'Jharkhand','Jharkhand','Deoghar',1,'JH15',0),(243,'Jharkhand','Jharkhand','Dhanbad',1,'JH10',0),(244,'Jharkhand','Jharkhand','Dumka',1,'JH04',0),(245,'Jharkhand','Jharkhand','East Singhbhum',1,'JH05',0),(246,'Jharkhand','Jharkhand','Garhwa',1,'JH14',0),(247,'Jharkhand','Jharkhand','Giridih',1,'JH11',0),(248,'Jharkhand','Jharkhand','Godda',1,'JH17',0),(249,'Jharkhand','Jharkhand','Gumla',1,'JH07',0),(250,'Jharkhand','Jharkhand','Hazaribag',1,'JH02',0),(251,'Jharkhand','Jharkhand','Jamtara',1,'JH21',0),(252,'Jharkhand','Jharkhand','Khunti',1,'JH23',0),(253,'Jharkhand','Jharkhand','Koderma',1,'JH12',0),(254,'Jharkhand','Jharkhand','Latehar',1,'JH19',0),(255,'Jharkhand','Jharkhand','Lohardaga',1,'JH08',0),(256,'Jharkhand','Jharkhand','Pakur',1,'JH16',0),(257,'Jharkhand','Jharkhand','Palamu',1,'JH03',0),(258,'Jharkhand','Jharkhand','Ramgarh',1,'JH24',0),(259,'Jharkhand','Jharkhand','Ranchi',1,'JH01',0),(260,'Jharkhand','Jharkhand','Sahibganj',1,'JH18',0),(261,'Jharkhand','Jharkhand','Seraikela Kharsawan',1,'JH22',0),(262,'Jharkhand','Jharkhand','Simdega',1,'JH20',0),(263,'Jharkhand','Jharkhand','West Singhbhum',1,'JH06',0),(264,'Karnataka','Karnataka','Bagalkot',1,'KA29',0),(265,'Karnataka','Karnataka','Bellary',1,'KA34',0),(266,'Karnataka','Karnataka','Belgaum',1,'KA22',0),(267,'Karnataka','Karnataka','Bangalore Rural',1,'KA51',0),(268,'Karnataka','Karnataka','Bangalore Urban',1,'KA01',0),(269,'Karnataka','Karnataka','Bidar',1,'KA38',0),(270,'Karnataka','Karnataka','Chamarajnagar',1,'KA10',0),(271,'Karnataka','Karnataka','Chikkaballapur',1,'KA40',0),(272,'Karnataka','Karnataka','Chikkamagaluru',1,'KA18',0),(273,'Karnataka','Karnataka','Chitradurga',1,'KA16',0),(274,'Karnataka','Karnataka','Dakshina Kannada',1,'KA19',0),(275,'Karnataka','Karnataka','Davanagere',1,'KA17',0),(276,'Karnataka','Karnataka','Dharwad',1,'KA25',0),(277,'Karnataka','Karnataka','Gadag',1,'KA26',0),(278,'Karnataka','Karnataka','Hassan',1,'KA13',0),(279,'Karnataka','Karnataka','Haveri District',1,'KA27',0),(280,'Karnataka','Karnataka','Gulbarga',1,'KA32',0),(281,'Karnataka','Karnataka','Kodagu',1,'KA12',0),(282,'Karnataka','Karnataka','Kolar',1,'KA07',0),(283,'Karnataka','Karnataka','Koppal',1,'KA37',0),(284,'Karnataka','Karnataka','Mandya',1,'KA11',0),(285,'Karnataka','Karnataka','Mysore',1,'KA09',0),(286,'Karnataka','Karnataka','Raichur',1,'KA36',0),(287,'Karnataka','Karnataka','Ramanagara',1,'KA42',0),(288,'Karnataka','Karnataka','Shimoga',1,'KA14',0),(289,'Karnataka','Karnataka','Tumkur',1,'KA06',0),(290,'Karnataka','Karnataka','Udupi',1,'KA20',0),(291,'Karnataka','Karnataka','Uttara Kannada',1,'KA30',0),(292,'Karnataka','Karnataka','Vijayapura',1,'KA68',0),(293,'Karnataka','Karnataka','Yadgir',1,'KA33',0),(294,'Kerala','Kerala','Alappuzha',1,'KL04',0),(295,'Kerala','Kerala','Ernakulam',1,'KL07',0),(296,'Kerala','Kerala','Idukki',1,'KL06',0),(297,'Kerala','Kerala','Kannur',1,'KL13',0),(298,'Kerala','Kerala','Kasaragod',1,'KL14',0),(299,'Kerala','Kerala','Kollam',1,'KL02',0),(300,'Kerala','Kerala','Kottayam',1,'KL05',0),(301,'Kerala','Kerala','Kozhikode',1,'KL11',0),(302,'Kerala','Kerala','Malappuram',1,'KL10',0),(303,'Kerala','Kerala','Palakkad',1,'KL09',0),(304,'Kerala','Kerala','Pathanamthitta',1,'KL03',0),(305,'Kerala','Kerala','Thrissur',1,'KL08',0),(306,'Kerala','Kerala','Thiruvananthapuram',1,'KL22',0),(307,'Kerala','Kerala','Wayanad',1,'KL12',0),(308,'Lakshadweep','Lakshadweep','Lakshadweep',1,'LD01',0),(309,'Madhya Pradesh','Madhya Pradesh','Agar Malwa',1,'MP70',0),(310,'Madhya Pradesh','Madhya Pradesh','Alirajpur',1,'MP69',0),(311,'Madhya Pradesh','Madhya Pradesh','Anuppur',1,'MP65',0),(312,'Madhya Pradesh','Madhya Pradesh','Ashok Nagar',1,'MP67',0),(313,'Madhya Pradesh','Madhya Pradesh','Balaghat',1,'MP50',0),(314,'Madhya Pradesh','Madhya Pradesh','Barwani',1,'MP46',0),(315,'Madhya Pradesh','Madhya Pradesh','Betul',1,'MP48',0),(316,'Madhya Pradesh','Madhya Pradesh','Bhind',1,'MP30',0),(317,'Madhya Pradesh','Madhya Pradesh','Bhopal',1,'MP04',0),(318,'Madhya Pradesh','Madhya Pradesh','Burhanpur',1,'MP68',0),(319,'Madhya Pradesh','Madhya Pradesh','Chhatarpur',1,'MP16',0),(320,'Madhya Pradesh','Madhya Pradesh','Chhindwara',1,'MP28',0),(321,'Madhya Pradesh','Madhya Pradesh','Damoh',1,'MP34',0),(322,'Madhya Pradesh','Madhya Pradesh','Datia',1,'MP32',0),(323,'Madhya Pradesh','Madhya Pradesh','Dewas',1,'MP41',0),(324,'Madhya Pradesh','Madhya Pradesh','Dhar',1,'MP11',0),(325,'Madhya Pradesh','Madhya Pradesh','Dindori',1,'MP52',0),(326,'Madhya Pradesh','Madhya Pradesh','Guna',1,'MP08',0),(327,'Madhya Pradesh','Madhya Pradesh','Gwalior',1,'MP07',0),(328,'Madhya Pradesh','Madhya Pradesh','Harda',1,'MP47',0),(329,'Madhya Pradesh','Madhya Pradesh','Hoshangabad',1,'MP05',0),(330,'Madhya Pradesh','Madhya Pradesh','Indore',1,'MP09',0),(331,'Madhya Pradesh','Madhya Pradesh','Jabalpur',1,'MP20',0),(332,'Madhya Pradesh','Madhya Pradesh','Jhabua',1,'MP45',0),(333,'Madhya Pradesh','Madhya Pradesh','Katni',1,'MP21',0),(334,'Madhya Pradesh','Madhya Pradesh','Khandwa',1,'MP12',0),(335,'Madhya Pradesh','Madhya Pradesh','Khargone',1,'MP10',0),(336,'Madhya Pradesh','Madhya Pradesh','Mandla',1,'MP51',0),(337,'Madhya Pradesh','Madhya Pradesh','Mandsaur',1,'MP14',0),(338,'Madhya Pradesh','Madhya Pradesh','Morena',1,'MP06',0),(339,'Madhya Pradesh','Madhya Pradesh','Narsinghpur',1,'MP49',0),(340,'Madhya Pradesh','Madhya Pradesh','Neemuch',1,'MP44',0),(341,'Madhya Pradesh','Madhya Pradesh','Panna',1,'MP35',0),(342,'Madhya Pradesh','Madhya Pradesh','Raisen',1,'MP38',0),(343,'Madhya Pradesh','Madhya Pradesh','Rajgarh',1,'MP39',0),(344,'Madhya Pradesh','Madhya Pradesh','Ratlam',1,'MP43',0),(345,'Madhya Pradesh','Madhya Pradesh','Rewa',1,'MP17',0),(346,'Madhya Pradesh','Madhya Pradesh','Sagar',1,'MP15',0),(347,'Madhya Pradesh','Madhya Pradesh','Satna',1,'MP19',0),(348,'Madhya Pradesh','Madhya Pradesh','Sehore',1,'MP37',0),(349,'Madhya Pradesh','Madhya Pradesh','Seoni',1,'MP22',0),(350,'Madhya Pradesh','Madhya Pradesh','Shahdol',1,'MP18',0),(351,'Madhya Pradesh','Madhya Pradesh','Shajapur',1,'MP42',0),(352,'Madhya Pradesh','Madhya Pradesh','Sheopur',1,'MP31',0),(353,'Madhya Pradesh','Madhya Pradesh','Shivpuri',1,'MP33',0),(354,'Madhya Pradesh','Madhya Pradesh','Sidhi',1,'MP53',0),(355,'Madhya Pradesh','Madhya Pradesh','Singrauli',1,'MP66',0),(356,'Madhya Pradesh','Madhya Pradesh','Tikamgarh',1,'MP36',0),(357,'Madhya Pradesh','Madhya Pradesh','Ujjain',1,'MP13',0),(358,'Madhya Pradesh','Madhya Pradesh','Umaria',1,'MP54',0),(359,'Madhya Pradesh','Madhya Pradesh','Vidisha',1,'MP40',0),(360,'Maharashtra','Nashik','Ahmednagar',1,'MH16',0),(361,'Maharashtra','Amravati','Akola',1,'MH30',0),(362,'Maharashtra','Amravati','Amravati',1,'MH27',0),(363,'Maharashtra','Aurangabad','Aurangabad',1,'MH20',0),(364,'Maharashtra','Aurangabad','Beed',1,'MH23',0),(365,'Maharashtra','Nagpur','Bhandara',1,'MH36',0),(366,'Maharashtra','Amravati','Buldhana',1,'MH28',0),(367,'Maharashtra','Nagpur','Chandrapur',1,'MH34',0),(368,'Maharashtra','Nashik','Dhule',1,'MH18',0),(369,'Maharashtra','Nagpur','Gadchiroli',1,'MH33',0),(370,'Maharashtra','Nagpur','Gondia',1,'MH35',0),(371,'Maharashtra','Aurangabad','Hingoli',1,'MH38',0),(372,'Maharashtra','Nashik','Jalgaon',1,'MH19',0),(373,'Maharashtra','Aurangabad','Jalna',1,'MH21',0),(374,'Maharashtra','Pune','Kolhapur',1,'MH09',0),(375,'Maharashtra','Aurangabad','Latur',1,'MH24',0),(376,'Maharashtra','Konkan','Mumbai City',1,'MH01',0),(377,'Maharashtra','Konkan','Mumbai Suburban',1,'MH05',0),(378,'Maharashtra','Nagpur','Nagpur',1,'MH31',0),(379,'Maharashtra','Aurangabad','Nanded',1,'MH26',0),(380,'Maharashtra','Nashik','Nandurbar',1,'MH39',0),(381,'Maharashtra','Nashik','Nashik',1,'MH15',0),(382,'Maharashtra','Aurangabad','Osmanabad',1,'MH25',0),(383,'Maharashtra','Aurangabad','Parbhani',1,'MH22',0),(384,'Maharashtra','Pune','Pune',1,'MH12',0),(385,'Maharashtra','Konkan','Raigad',1,'MH06',0),(386,'Maharashtra','Konkan','Ratnagiri',1,'MH08',0),(387,'Maharashtra','Pune','Sangli',1,'MH10',0),(388,'Maharashtra','Pune','Satara',1,'MH11',0),(389,'Maharashtra','Konkan','Sindhudurg',1,'MH07',0),(390,'Maharashtra','Pune','Solapur',1,'MH13',0),(391,'Maharashtra','Konkan','Thane',1,'MH04',0),(392,'Maharashtra','Nagpur','Wardha',1,'MH32',0),(393,'Maharashtra','Amravati','Washim',1,'MH37',0),(394,'Maharashtra','Amravati','Yavatmal',1,'MH29',0),(395,'Manipur','Manipur','Bishnupur',1,'MN05',0),(396,'Manipur','Manipur','Churachandpur',1,'MN02',0),(397,'Manipur','Manipur','Chandel',1,'MN08',0),(398,'Manipur','Manipur','Imphal East',1,'MN06',0),(399,'Manipur','Manipur','Senapati',1,'MN09',0),(400,'Manipur','Manipur','Tamenglong',1,'MN10',0),(401,'Manipur','Manipur','Thoubal',1,'MN04',0),(402,'Manipur','Manipur','Ukhrul',1,'MN07',0),(403,'Manipur','Manipur','Imphal West',1,'MN01',0),(404,'Meghalaya','Meghalaya','East Garo Hills',1,'ML07',0),(405,'Meghalaya','Meghalaya','East Khasi Hills',1,'ML05',0),(406,'Meghalaya','Meghalaya','East Jaintia Hills',1,'ML04',0),(407,'Meghalaya','Meghalaya','North Garo Hills',1,'ML11',0),(408,'Meghalaya','Meghalaya','Ri Bhoi',1,'ML10',0),(409,'Meghalaya','Meghalaya','South Garo Hills',1,'ML09',0),(410,'Meghalaya','Meghalaya','South West Garo Hills',1,'ML12',0),(411,'Meghalaya','Meghalaya','South West Khasi Hills',1,'ML13',0),(412,'Meghalaya','Meghalaya','West Jaintia Hills',1,'ML14',0),(413,'Meghalaya','Meghalaya','West Garo Hills',1,'ML08',0),(414,'Meghalaya','Meghalaya','West Khasi Hills',1,'ML06',0),(415,'Mizoram','Mizoram','Aizawl',1,'MZ01',0),(416,'Mizoram','Mizoram','Champhai',1,'MZ04',0),(417,'Mizoram','Mizoram','Kolasib',1,'MZ05',0),(418,'Mizoram','Mizoram','Lawngtlai',1,'MZ07',0),(419,'Mizoram','Mizoram','Lunglei',1,'MZ02',0),(420,'Mizoram','Mizoram','Mamit',1,'MZ08',0),(421,'Mizoram','Mizoram','Saiha',1,'MZ03',0),(422,'Mizoram','Mizoram','Serchhip',1,'MZ06',0),(423,'Nagaland','Nagaland','Dimapur',1,'NL07',0),(424,'Nagaland','Nagaland','Kiphire',1,'NL08',0),(425,'Nagaland','Nagaland','Kohima',1,'NL01',0),(426,'Nagaland','Nagaland','Longleng',1,'NL09',0),(427,'Nagaland','Nagaland','Mokokchung',1,'NL02',0),(428,'Nagaland','Nagaland','Mon',1,'NL04',0),(429,'Nagaland','Nagaland','Peren',1,'NL10',0),(430,'Nagaland','Nagaland','Phek',1,'NL08',0),(431,'Nagaland','Nagaland','Tuensang',1,'NL03',0),(432,'Nagaland','Nagaland','Wokha',1,'NL05',0),(433,'Nagaland','Nagaland','Zunheboto',1,'NL06',0),(434,'Odisha','Odisha','Angul',1,'OD19',0),(435,'Odisha','Odisha','Boudh',1,'OD27',0),(436,'Odisha','Odisha','Bhadrak',1,'OD22',0),(437,'Odisha','Odisha','Balangir',1,'OD03',0),(438,'Odisha','Odisha','Bargarh',1,'OD17',0),(439,'Odisha','Odisha','Balasore',1,'OD01',0),(440,'Odisha','Odisha','Cuttack',1,'OD05',0),(441,'Odisha','Odisha','Debagarh',1,'OD28',0),(442,'Odisha','Odisha','Dhenkanal',1,'OD06',0),(443,'Odisha','Odisha','Ganjam',1,'OD07',0),(444,'Odisha','Odisha','Gajapati',1,'OD20',0),(445,'Odisha','Odisha','Jharsuguda',1,'OD23',0),(446,'Odisha','Odisha','Jajpur',1,'OD34',0),(447,'Odisha','Odisha','Jagatsinghpur',1,'OD21',0),(448,'Odisha','Odisha','Khordha',1,'OD36',0),(449,'Odisha','Odisha','Kendujhar',1,'OD09',0),(450,'Odisha','Odisha','Kalahandi',1,'OD08',0),(451,'Odisha','Odisha','Kandhamal',1,'OD12',0),(452,'Odisha','Odisha','Koraput',1,'OD10',0),(453,'Odisha','Odisha','Kendrapara',1,'OD29',0),(454,'Odisha','Odisha','Malkangiri',1,'OD30',0),(455,'Odisha','Odisha','Mayurbhanj',1,'OD11',0),(456,'Odisha','Odisha','Nabarangpur',1,'OD24',0),(457,'Odisha','Odisha','Nuapada',1,'OD26',0),(458,'Odisha','Odisha','Nayagarh',1,'OD25',0),(459,'Odisha','Odisha','Puri',1,'OD13',0),(460,'Odisha','Odisha','Rayagada',1,'OD18',0),(461,'Odisha','Odisha','Sambalpur',1,'OD15',0),(462,'Odisha','Odisha','Subarnapur',1,'OD31',0),(463,'Odisha','Odisha','Sundargarh',1,'OD16',0),(464,'Puducherry','Puducherry','Karaikal',1,'PY02',0),(465,'Puducherry','Puducherry','Mahe',1,'PY03',0),(466,'Puducherry','Puducherry','Pondicherry',1,'PY01',0),(467,'Puducherry','Puducherry','Yanam',1,'PY04',0),(468,'Punjab','Punjab','Amritsar',1,'PB02',0),(469,'Punjab','Punjab','Barnala',1,'PB19',0),(470,'Punjab','Punjab','Bathinda',1,'PB03',0),(471,'Punjab','Punjab','Firozpur',1,'PB05',0),(472,'Punjab','Punjab','Faridkot',1,'PB04',0),(473,'Punjab','Punjab','Fatehgarh Sahib',1,'PB23',0),(474,'Punjab','Punjab','Fazilka',1,'PB22',0),(475,'Punjab','Punjab','Gurdaspur',1,'PB06',0),(476,'Punjab','Punjab','Hoshiarpur',1,'PB07',0),(477,'Punjab','Punjab','Jalandhar',1,'PB08',0),(478,'Punjab','Punjab','Kapurthala',1,'PB09',0),(479,'Punjab','Punjab','Ludhiana',1,'PB10',0),(480,'Punjab','Punjab','Mansa',1,'PB31',0),(481,'Punjab','Punjab','Moga',1,'PB29',0),(482,'Punjab','Punjab','Sri Muktsar Sahib',1,'PB30',0),(483,'Punjab','Punjab','Pathankot',1,'PB35',0),(484,'Punjab','Punjab','Patiala',1,'PB11',0),(485,'Punjab','Punjab','Rupnagar',1,'PB12',0),(486,'Punjab','Punjab','Sahibzada Ajit Singh Nagar',1,'PB65',0),(487,'Punjab','Punjab','Sangrur',1,'PB13',0),(488,'Punjab','Punjab','Shahid Bhagat Singh Nagar',1,'PB32',0),(489,'Punjab','Punjab','Tarn Taran',1,'PB46',0),(490,'Rajasthan','Rajasthan','Ajmer',1,'RJ01',0),(491,'Rajasthan','Rajasthan','Alwar',1,'RJ02',0),(492,'Rajasthan','Rajasthan','Bikaner',1,'RJ07',0),(493,'Rajasthan','Rajasthan','Barmer',1,'RJ39',0),(494,'Rajasthan','Rajasthan','Banswara',1,'RJ03',0),(495,'Rajasthan','Rajasthan','Bharatpur',1,'RJ05',0),(496,'Rajasthan','Rajasthan','Baran',1,'RJ28',0),(497,'Rajasthan','Rajasthan','Bundi',1,'RJ08',0),(498,'Rajasthan','Rajasthan','Bhilwara',1,'RJ06',0),(499,'Rajasthan','Rajasthan','Churu',1,'RJ10',0),(500,'Rajasthan','Rajasthan','Chittorgarh',1,'RJ09',0),(501,'Rajasthan','Rajasthan','Dausa',1,'RJ29',0),(502,'Rajasthan','Rajasthan','Dholpur',1,'RJ11',0),(503,'Rajasthan','Rajasthan','Dungapur',1,'RJ12',0),(504,'Rajasthan','Rajasthan','Ganganagar',1,'RJ13',0),(505,'Rajasthan','Rajasthan','Hanumangarh',1,'RJ31',0),(506,'Rajasthan','Rajasthan','Jhunjhunu',1,'RJ18',0),(507,'Rajasthan','Rajasthan','Jalore',1,'RJ16',0),(508,'Rajasthan','Rajasthan','Jodhpur',1,'RJ19',0),(509,'Rajasthan','Rajasthan','Jaipur',1,'RJ14',0),(510,'Rajasthan','Rajasthan','Jaisalmer',1,'RJ15',0),(511,'Rajasthan','Rajasthan','Jhalawar',1,'RJ17',0),(512,'Rajasthan','Rajasthan','Karauli',1,'RJ34',0),(513,'Rajasthan','Rajasthan','Kota',1,'RJ20',0),(514,'Rajasthan','Rajasthan','Nagaur',1,'RJ21',0),(515,'Rajasthan','Rajasthan','Pali',1,'RJ22',0),(516,'Rajasthan','Rajasthan','Pratapgarh',1,'RJ35',0),(517,'Rajasthan','Rajasthan','Rajsamand',1,'RJ30',0),(518,'Rajasthan','Rajasthan','Sikar',1,'RJ23',0),(519,'Rajasthan','Rajasthan','Sawai Madhopur',1,'RJ25',0),(520,'Rajasthan','Rajasthan','Sirohi',1,'RJ24',0),(521,'Rajasthan','Rajasthan','Tonk',1,'RJ26',0),(522,'Rajasthan','Rajasthan','Udaipur',1,'RJ27',0),(523,'Sikkim','Sikkim','East Sikkim',1,'SK01',0),(524,'Sikkim','Sikkim','North Sikkim',1,'SK03',0),(525,'Sikkim','Sikkim','South Sikkim',1,'SK06',0),(526,'Sikkim','Sikkim','West Sikkim',1,'SK02',0),(527,'Tamil Nadu','Tamil Nadu','Ariyalur',1,'TN61',0),(528,'Tamil Nadu','Tamil Nadu','Chennai',1,'TN01',0),(529,'Tamil Nadu','Tamil Nadu','Coimbatore',1,'TN66',0),(530,'Tamil Nadu','Tamil Nadu','Cuddalore',1,'TN31',0),(531,'Tamil Nadu','Tamil Nadu','Dharmapuri',1,'TN29',0),(532,'Tamil Nadu','Tamil Nadu','Dindigul',1,'TN57',0),(533,'Tamil Nadu','Tamil Nadu','Erode',1,'TN33',0),(534,'Tamil Nadu','Tamil Nadu','Kanchipuram',1,'TN74',0),(535,'Tamil Nadu','Tamil Nadu','Kanyakumari',1,'TN74',0),(536,'Tamil Nadu','Tamil Nadu','Karur',1,'TN47',0),(537,'Tamil Nadu','Tamil Nadu','Krishnagiri',1,'TN24',0),(538,'Tamil Nadu','Tamil Nadu','Madurai',1,'TN58',0),(539,'Tamil Nadu','Tamil Nadu','Nagapattinam',1,'TN51',0),(540,'Tamil Nadu','Tamil Nadu','Nilgiris',1,'TN43',0),(541,'Tamil Nadu','Tamil Nadu','Namakkal',1,'TN28',0),(542,'Tamil Nadu','Tamil Nadu','Perambalur',1,'TN46',0),(543,'Tamil Nadu','Tamil Nadu','Pudukkottai',1,'TN55',0),(544,'Tamil Nadu','Tamil Nadu','Ramanathapuram',1,'TN65',0),(545,'Tamil Nadu','Tamil Nadu','Salem',1,'TN30',0),(546,'Tamil Nadu','Tamil Nadu','Sivaganga',1,'TN63',0),(547,'Tamil Nadu','Tamil Nadu','Tirupur',1,'TN39',0),(548,'Tamil Nadu','Tamil Nadu','Tiruchirappalli',1,'TN45',0),(549,'Tamil Nadu','Tamil Nadu','Theni',1,'TN60',0),(550,'Tamil Nadu','Tamil Nadu','Tirunelveli',1,'TN72',0),(551,'Tamil Nadu','Tamil Nadu','Thanjavur',1,'TN49',0),(552,'Tamil Nadu','Tamil Nadu','Thoothukudi',1,'TN69',0),(553,'Tamil Nadu','Tamil Nadu','Tiruvallur',1,'TN20',0),(554,'Tamil Nadu','Tamil Nadu','Tiruvarur',1,'TN50',0),(555,'Tamil Nadu','Tamil Nadu','Tiruvannamalai',1,'TN25',0),(556,'Tamil Nadu','Tamil Nadu','Vellore',1,'TN23',0),(557,'Tamil Nadu','Tamil Nadu','Viluppuram',1,'TN32',0),(558,'Tamil Nadu','Tamil Nadu','Virudhunagar',1,'TN67',0),(559,'Telangana','Telangana','Adilabad',1,'TS01',0),(560,'Telangana','Telangana','Hyderabad',1,'TS09',0),(561,'Telangana','Telangana','Karimnagar',1,'TS02',0),(562,'Telangana','Telangana','Khammam',1,'TS04',0),(563,'Telangana','Telangana','Mahbubnagar',1,'TS06',0),(564,'Telangana','Telangana','Medak',1,'TS15',0),(565,'Telangana','Telangana','Nalgonda',1,'TS05',0),(566,'Telangana','Telangana','Nizamabad',1,'TS16',0),(567,'Telangana','Telangana','Ranga Reddy',1,'TS07',0),(568,'Telangana','Telangana','Warangal',1,'TS03',0),(569,'Tripura','Tripura','Dhalai',1,'TR04',0),(570,'Tripura','Tripura','Gomati',1,'TR03',0),(571,'Tripura','Tripura','Khowai',1,'TR07',0),(572,'Tripura','Tripura','North Tripura',1,'TR05',0),(573,'Tripura','Tripura','Sepahijala',1,'TR08',0),(574,'Tripura','Tripura','South Tripura',1,'TR09',0),(575,'Tripura','Tripura','Unokoti',1,'TR10',0),(576,'Tripura','Tripura','West Tripura',1,'TR01',0),(577,'Uttar Pradesh','Uttar Pradesh','Agra',1,'UP80',0),(578,'Uttar Pradesh','Uttar Pradesh','Aligarh',1,'UP81',0),(579,'Uttar Pradesh','Uttar Pradesh','Allahabad',1,'UP70',0),(580,'Uttar Pradesh','Uttar Pradesh','Ambedkar Nagar',1,'UP45',0),(581,'Uttar Pradesh','Uttar Pradesh','Amethi',1,'UP36',0),(582,'Uttar Pradesh','Uttar Pradesh','Amroha',1,'UP23',0),(583,'Uttar Pradesh','Uttar Pradesh','Auraiya',1,'UP79',0),(584,'Uttar Pradesh','Uttar Pradesh','Azamgarh',1,'UP50',0),(585,'Uttar Pradesh','Uttar Pradesh','Bagpat',1,'UP17',0),(586,'Uttar Pradesh','Uttar Pradesh','Bahraich',1,'UP40',0),(587,'Uttar Pradesh','Uttar Pradesh','Ballia',1,'UP60',0),(588,'Uttar Pradesh','Uttar Pradesh','Balrampur',1,'UP47',0),(589,'Uttar Pradesh','Uttar Pradesh','Banda',1,'UP90',0),(590,'Uttar Pradesh','Uttar Pradesh','Barabanki',1,'UP41',0),(591,'Uttar Pradesh','Uttar Pradesh','Bareilly',1,'UP25',0),(592,'Uttar Pradesh','Uttar Pradesh','Basti',1,'UP51',0),(593,'Uttar Pradesh','Uttar Pradesh','Bijnor',1,'UP20',0),(594,'Uttar Pradesh','Uttar Pradesh','Budaun',1,'UP24',0),(595,'Uttar Pradesh','Uttar Pradesh','Bulandshahr',1,'UP13',0),(596,'Uttar Pradesh','Uttar Pradesh','Chandauli',1,'UP67',0),(597,'Uttar Pradesh','Uttar Pradesh','Chitrakoot',1,'UP96',0),(598,'Uttar Pradesh','Uttar Pradesh','Deoria',1,'UP52',0),(599,'Uttar Pradesh','Uttar Pradesh','Etah',1,'UP82',0),(600,'Uttar Pradesh','Uttar Pradesh','Etawah',1,'UP75',0),(601,'Uttar Pradesh','Uttar Pradesh','Faizabad',1,'UP42',0),(602,'Uttar Pradesh','Uttar Pradesh','Farrukhabad',1,'UP76',0),(603,'Uttar Pradesh','Uttar Pradesh','Fatehpur',1,'UP71',0),(604,'Uttar Pradesh','Uttar Pradesh','Firozabad',1,'UP83',0),(605,'Uttar Pradesh','Uttar Pradesh','Gautam Buddh Nagar',1,'UP16',0),(606,'Uttar Pradesh','Uttar Pradesh','Ghaziabad',1,'UP14',0),(607,'Uttar Pradesh','Uttar Pradesh','Ghazipur',1,'UP61',0),(608,'Uttar Pradesh','Uttar Pradesh','Gonda',1,'UP43',0),(609,'Uttar Pradesh','Uttar Pradesh','Gorakhpur',1,'UP53',0),(610,'Uttar Pradesh','Uttar Pradesh','Hamirpur',1,'UP91',0),(611,'Uttar Pradesh','Uttar Pradesh','Hapur',1,'UP37',0),(612,'Uttar Pradesh','Uttar Pradesh','Hardoi',1,'UP30',0),(613,'Uttar Pradesh','Uttar Pradesh','Hathras',1,'UP86',0),(614,'Uttar Pradesh','Uttar Pradesh','Jalaun',1,'UP92',0),(615,'Uttar Pradesh','Uttar Pradesh','Jaunpur District',1,'UP62',0),(616,'Uttar Pradesh','Uttar Pradesh','Jhansi',1,'UP93',0),(617,'Uttar Pradesh','Uttar Pradesh','Kannauj',1,'UP74',0),(618,'Uttar Pradesh','Uttar Pradesh','Kanpur Dehat',1,'UP77',0),(619,'Uttar Pradesh','Uttar Pradesh','Kanpur Nagar',1,'UP78',0),(620,'Uttar Pradesh','Uttar Pradesh','Kasganj',1,'UP87',0),(621,'Uttar Pradesh','Uttar Pradesh','Kaushambi',1,'UP73',0),(622,'Uttar Pradesh','Uttar Pradesh','Kushinagar',1,'UP57',0),(623,'Uttar Pradesh','Uttar Pradesh','Lakhimpur Kheri',1,'UP31',0),(624,'Uttar Pradesh','Uttar Pradesh','Lalitpur',1,'UP94',0),(625,'Uttar Pradesh','Uttar Pradesh','Lucknow',1,'UP32',0),(626,'Uttar Pradesh','Uttar Pradesh','Maharajganj',1,'UP56',0),(627,'Uttar Pradesh','Uttar Pradesh','Mahoba',1,'UP95',0),(628,'Uttar Pradesh','Uttar Pradesh','Mainpuri',1,'UP84',0),(629,'Uttar Pradesh','Uttar Pradesh','Mathura',1,'UP85',0),(630,'Uttar Pradesh','Uttar Pradesh','Mau',1,'UP54',0),(631,'Uttar Pradesh','Uttar Pradesh','Meerut',1,'UP15',0),(632,'Uttar Pradesh','Uttar Pradesh','Mirzapur',1,'UP63',0),(633,'Uttar Pradesh','Uttar Pradesh','Moradabad',1,'UP21',0),(634,'Uttar Pradesh','Uttar Pradesh','Muzaffarnagar',1,'UP12',0),(635,'Uttar Pradesh','Uttar Pradesh','Pilibhit',1,'UP26',0),(636,'Uttar Pradesh','Uttar Pradesh','Pratapgarh',1,'UP72',0),(637,'Uttar Pradesh','Uttar Pradesh','Raebareli',1,'UP33',0),(638,'Uttar Pradesh','Uttar Pradesh','Rampur',1,'UP22',0),(639,'Uttar Pradesh','Uttar Pradesh','Saharanpur',1,'UP11',0),(640,'Uttar Pradesh','Uttar Pradesh','Sambhal',1,'UP38',0),(641,'Uttar Pradesh','Uttar Pradesh','Sant Kabir Nagar',1,'UP58',0),(642,'Uttar Pradesh','Uttar Pradesh','Sant Ravidas Nagar',1,'UP66',0),(643,'Uttar Pradesh','Uttar Pradesh','Shahjahanpur',1,'UP27',0),(644,'Uttar Pradesh','Uttar Pradesh','Shamli',1,'UP19',0),(645,'Uttar Pradesh','Uttar Pradesh','Shravasti',1,'UP46',0),(646,'Uttar Pradesh','Uttar Pradesh','Siddharthnagar',1,'UP55',0),(647,'Uttar Pradesh','Uttar Pradesh','Sitapur',1,'UP34',0),(648,'Uttar Pradesh','Uttar Pradesh','Sonbhadra',1,'UP64',0),(649,'Uttar Pradesh','Uttar Pradesh','Sultanpur',1,'UP44',0),(650,'Uttar Pradesh','Uttar Pradesh','Unnao',1,'UP35',0),(651,'Uttar Pradesh','Uttar Pradesh','Varanasi',1,'UP65',0),(652,'Uttarakhand','Uttarakhand','Almora',1,'UA19',0),(653,'Uttarakhand','Uttarakhand','Bageshwar',1,'UA20',0),(654,'Uttarakhand','Uttarakhand','Chamoli',1,'UA11',0),(655,'Uttarakhand','Uttarakhand','Champawat',1,'UA21',0),(656,'Uttarakhand','Uttarakhand','Dehradun',1,'UA07',0),(657,'Uttarakhand','Uttarakhand','Haridwar',1,'UA08',0),(658,'Uttarakhand','Uttarakhand','Nainital',1,'UA22',0),(659,'Uttarakhand','Uttarakhand','Pauri Garhwal',1,'UA12',0),(660,'Uttarakhand','Uttarakhand','Pithoragarh',1,'UA23',0),(661,'Uttarakhand','Uttarakhand','Rudraprayag',1,'UA13',0),(662,'Uttarakhand','Uttarakhand','Tehri Garhwal',1,'UA09',0),(663,'Uttarakhand','Uttarakhand','Udham Singh Nagar',1,'UA24',0),(664,'Uttarakhand','Uttarakhand','Uttarkashi',1,'UA10',0),(665,'West Bengal','West Bengal','Alipurduar',1,'WB70',0),(666,'West Bengal','West Bengal','Bankura',1,'WB68',0),(667,'West Bengal','West Bengal','Bardhaman',1,'WB42',0),(668,'West Bengal','West Bengal','Birbhum',1,'WB54',0),(669,'West Bengal','West Bengal','Cooch Behar',1,'WB64',0),(670,'West Bengal','West Bengal','Dakshin Dinajpur',1,'WB62',0),(671,'West Bengal','West Bengal','Darjeeling',1,'WB77',0),(672,'West Bengal','West Bengal','Hooghly',1,'WB16',0),(673,'West Bengal','West Bengal','Howrah',1,'WB12',0),(674,'West Bengal','West Bengal','Jalpaiguri',1,'WB72',0),(675,'West Bengal','West Bengal','Kolkata',1,'WB01',0),(676,'West Bengal','West Bengal','Maldah',1,'WB66',0),(677,'West Bengal','West Bengal','Murshidabad',1,'WB58',0),(678,'West Bengal','West Bengal','Nadia',1,'WB52',0),(679,'West Bengal','West Bengal','North 24 Parganas',1,'WB26',0),(680,'West Bengal','West Bengal','Paschim Medinipur',1,'WB34',0),(681,'West Bengal','West Bengal','Purba Medinipur',1,'WB30',0),(682,'West Bengal','West Bengal','Purulia',1,'WB56',0),(683,'West Bengal','West Bengal','South 24 Parganas',1,'WB20',0),(684,'West Bengal','West Bengal','Uttar Dinajpur',1,'WB60',0);
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
  `g_ac_no` int(10) NOT NULL,
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
  `c_m_outstanding_saving_am` decimal(16,2) DEFAULT NULL,
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
  `a_m_outstanding_saving_am` decimal(16,2) DEFAULT NULL,
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
  `net_profit_proj_am` decimal(16,2) DEFAULT NULL,
  `expenses_am` decimal(16,2) DEFAULT NULL,
  `outstanding_expenses_am` decimal(16,2) DEFAULT NULL,
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
  `group_type` varchar(50) NOT NULL,
  `category` varchar(50) DEFAULT NULL,
  `default_ws_access_rights` bigint(20) DEFAULT NULL,
  `default_ui_access_rights` bigint(20) DEFAULT NULL,
  `group_type_desc` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`group_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_type`
--

LOCK TABLES `group_type` WRITE;
/*!40000 ALTER TABLE `group_type` DISABLE KEYS */;
INSERT INTO `group_type` VALUES (1,'SHG','SHG',0,0,'Self Help Group'),(2,'Federation','Federation',849510040337985,388814799371841,'Federation, Group of Multiple Self Help Group'),(3,'NGO','NGO',849510040337985,388814799371841,'NGO which helps monitor multiple SHG'),(4,'Bank','Bank',5076651343937,78634811457,'Bank which monitor multiple SHG having accounts'),(5,'GOV','GOV',5076651343937,78634811457,'Goverment Entity which monitor multiple SHG having accounts'),(6,'SHG-One Agent','Agent',0,0,'SHG-One Agent Group'),(7,'Area Admin','Admin',0,0,'SHG-One Area Admin Group'),(8,'Super Area Admin','Admin',0,0,'SHG-One Super Area Admin Group'),(9,'SHG-One Admin','Admin',0,0,'SHG-One Admin Group'),(10,'Udaan Admin','Admin',0,0,'Udaan Admin Group'),(11,'Mega HUB','HUB',0,0,'Mega HUB Group'),(12,'Local HUB','HUB',0,0,'Local HUB Group'),(13,'Manufacturer','Manufacturer',0,0,'Manufacturer Group'),(14,'Micro Retailer','Business Partner',0,0,'Micro Retailer Group'),(15,'Sales Executive','Operation Team',0,0,'Sales Executive Group'),(16,'Super Sales Executive','Operation Team',0,0,'Super Sales Executive Group');
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
-- Table structure for table `invoice_status`
--

DROP TABLE IF EXISTS `invoice_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoice_status` (
  `invoice_status_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `invoice_status` varchar(45) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `next_status` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`invoice_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice_status`
--

LOCK TABLES `invoice_status` WRITE;
/*!40000 ALTER TABLE `invoice_status` DISABLE KEYS */;
INSERT INTO `invoice_status` VALUES (1,'Draft','Invoice is saved as Draft','Quotation;Ordered;Cancelled'),(2,'Quotation','Got Quotation for Invoice','Ordered;Cancelled'),(3,'Ordered','Placed Ordered','Processing;Shipped;Delivered;Cancelled'),(4,'Processing','Order is Processing','Shipped;Delivered;Cancelled'),(5,'Shipped','Order is Shipped','Delivered;Cancelled'),(6,'Delivered','Order is Delivered','To Stock;Cancelled'),(7,'To Stock','Invoice Items is yet To Stock','Complete;Cancelled'),(8,'Complete','Invoice is Complete',''),(9,'Cancelled','Invoice is Cancelled','');
/*!40000 ALTER TABLE `invoice_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_condition`
--

DROP TABLE IF EXISTS `item_condition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_condition` (
  `item_condition_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `item_condition` varchar(45) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `sp_discount_per` float DEFAULT NULL,
  `mrp_discount_per` float DEFAULT NULL,
  PRIMARY KEY (`item_condition_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_condition`
--

LOCK TABLES `item_condition` WRITE;
/*!40000 ALTER TABLE `item_condition` DISABLE KEYS */;
INSERT INTO `item_condition` VALUES (1,'Fresh','Item condition is Fresh',0,0),(2,'Discount10','Item is with 10% Discount',0.05,0.1),(3,'Discount15','Item is with 15% Discount',0.075,0.15),(4,'Discount20','Item is with 20% Discount',0.1,0.2),(5,'Discount25','Item is with 25% Discount',0.15,0.25),(6,'Discount30','Item is with 30% Discount',0.175,0.3),(7,'Discount35','Item is with 35% Discount',0.2,0.35),(8,'Discount40','Item is with 40% Discount',0.275,0.4),(9,'DamageLevel1','Item is with Damage of Level 1',0.175,0.3),(10,'DamageLevel2','Item is with Damage of Level 2',0.25,0.4),(11,'DamageLevel3','Item is with Damage of Level 3',0.3,0.45),(12,'DamageLevel4','Item is with Damage of Level 4',0.4,0.5),(13,'DamageLevel5','Item is with Damage of Level 5',0.45,0.6);
/*!40000 ALTER TABLE `item_condition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_status`
--

DROP TABLE IF EXISTS `item_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_status` (
  `item_status_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `item_status` varchar(45) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `next_status` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`item_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_status`
--

LOCK TABLES `item_status` WRITE;
/*!40000 ALTER TABLE `item_status` DISABLE KEYS */;
INSERT INTO `item_status` VALUES (1,'Created','Item is Created','Stocked;Discarded'),(2,'Stocked','Item is Stocked','Sold;Mark Sold;Damaged;Damage Returned;Damage Sold'),(3,'Discarded','Item is Discarded',''),(4,'Lost','Item is Lost','Stocked;Discarded'),(5,'Sold','Item is Sold',''),(6,'Mark Sold','Item is to be marked as Sold','Stocked;Sold;Damage Sold'),(7,'Damaged','Item is Damaged','Mark Sold;Damage Returned;Damage Sold;Discarded'),(8,'Damage Returned','Item is Returned due to Damage',''),(9,'Damage Sold','Item is Sold with Discount due to Damage','');
/*!40000 ALTER TABLE `item_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_tag`
--

DROP TABLE IF EXISTS `item_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_tag` (
  `item_tag_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `stock_item_id` bigint(20) NOT NULL,
  `stock_type_id` bigint(20) NOT NULL,
  `tag_status_id` int(10) NOT NULL,
  `stock_type_name` varchar(100) DEFAULT NULL,
  `design_no` varchar(20) DEFAULT NULL,
  `brand_name` varchar(100) DEFAULT NULL,
  `mrp_price_am` varchar(45) DEFAULT NULL,
  `dis_mrp_price_am` varchar(45) DEFAULT NULL,
  `discount_per` varchar(45) DEFAULT NULL,
  `created_ts` timestamp NULL DEFAULT NULL,
  `printed_ts` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`item_tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_tag`
--

LOCK TABLES `item_tag` WRITE;
/*!40000 ALTER TABLE `item_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `item_tag` ENABLE KEYS */;
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
-- Table structure for table `lot`
--

DROP TABLE IF EXISTS `lot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lot` (
  `lot_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `p_invoice_id` bigint(20) unsigned NOT NULL,
  `stock_type_id` bigint(20) unsigned NOT NULL,
  `lot_status_id` int(10) unsigned NOT NULL,
  `name` varchar(100) NOT NULL,
  `no_per_set` int(10) DEFAULT NULL,
  `no_of_sets` int(10) DEFAULT NULL,
  `no_purchased` int(10) DEFAULT NULL,
  `no_delivered` int(10) DEFAULT NULL,
  `no_to_stock` int(10) DEFAULT NULL,
  `no_created` int(10) DEFAULT NULL,
  `no_stocked` int(10) DEFAULT NULL,
  `no_sold` int(10) DEFAULT NULL,
  `no_damaged` int(10) DEFAULT NULL,
  `item_price_am` decimal(16,2) DEFAULT NULL,
  `lot_price_am` decimal(16,2) DEFAULT NULL,
  `avg_mr_item_sold_am` decimal(16,2) DEFAULT NULL,
  `discount_per` float DEFAULT NULL,
  `discount_am` decimal(16,2) DEFAULT NULL,
  `vat_am` decimal(16,2) DEFAULT NULL,
  `return_counter` int(10) DEFAULT NULL,
  `performance_index` float DEFAULT NULL,
  `return_index` float DEFAULT NULL,
  `sales_index` float DEFAULT NULL,
  `sales_20_per_days` float DEFAULT NULL,
  `sales_40_per_days` float DEFAULT NULL,
  `sales_60_per_days` float DEFAULT NULL,
  `sales_80_per_days` float DEFAULT NULL,
  `sales_100_per_days` float DEFAULT NULL,
  PRIMARY KEY (`lot_id`),
  KEY `lot_fk1_idx` (`p_invoice_id`),
  KEY `lot_fk2_idx` (`stock_type_id`),
  CONSTRAINT `lot_fk1` FOREIGN KEY (`p_invoice_id`) REFERENCES `p_invoice` (`p_invoice_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `lot_fk2` FOREIGN KEY (`stock_type_id`) REFERENCES `stock_type` (`stock_type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lot`
--

LOCK TABLES `lot` WRITE;
/*!40000 ALTER TABLE `lot` DISABLE KEYS */;
/*!40000 ALTER TABLE `lot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lot_status`
--

DROP TABLE IF EXISTS `lot_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lot_status` (
  `lot_status_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `lot_status` varchar(45) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `next_status` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`lot_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lot_status`
--

LOCK TABLES `lot_status` WRITE;
/*!40000 ALTER TABLE `lot_status` DISABLE KEYS */;
INSERT INTO `lot_status` VALUES (1,'Draft','Lot is saved as Draft','Quotation;Ordered;Cancelled'),(2,'Quotation','Got Quotation for Invoice of the Lot','Ordered;Cancelled'),(3,'Ordered','Placed Ordered','Processing;Shipped;Delivered;Cancelled'),(4,'Processing','Order is Processing','Shipped;Delivered;Cancelled'),(5,'Shipped','Order is Shipped','Delivered;Cancelled'),(6,'Delivered','Order is Delivered','To Stock;Stocked;Sold Out;Cancelled'),(7,'To Stock','Some Lot item is To Stock','Created;Cancelled'),(8,'Created','All Lot items are Created','Stocked;Cancelled'),(9,'Stocked','All Lot items are Stocked','Sold Out;Cancelled'),(10,'Sold Out','Lot is Sold Out',''),(11,'Cancelled','Lot is Cancelled','');
/*!40000 ALTER TABLE `lot_status` ENABLE KEYS */;
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
  `outstanding_saving_am` decimal(16,2) DEFAULT NULL,
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
-- Table structure for table `m_profiling_type`
--

DROP TABLE IF EXISTS `m_profiling_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_profiling_type` (
  `point_id` int(11) NOT NULL,
  `point` varchar(45) NOT NULL,
  `category` varchar(45) DEFAULT NULL,
  `value_type` varchar(25) NOT NULL,
  `options` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`point_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_profiling_type`
--

LOCK TABLES `m_profiling_type` WRITE;
/*!40000 ALTER TABLE `m_profiling_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_profiling_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_role`
--

DROP TABLE IF EXISTS `m_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_role` (
  `m_role_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `m_role` varchar(50) NOT NULL,
  `m_role_category` varchar(50) NOT NULL,
  `belong_to` varchar(50) NOT NULL,
  `system` varchar(50) NOT NULL,
  `m_role_desc` varchar(100) DEFAULT NULL,
  `default_ws_access_rights` bigint(20) DEFAULT NULL,
  `default_ui_access_rights` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`m_role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_role`
--

LOCK TABLES `m_role` WRITE;
/*!40000 ALTER TABLE `m_role` DISABLE KEYS */;
INSERT INTO `m_role` VALUES (1,'Associate Member','SHG Member','SHG','SHG','Associate Member of the Group for a fix duration',4466900210185,68853694985),(2,'Core Member','SHG Member','SHG','SHG','Core forunder Member of the Group',4466900210249,68853695049),(3,'Group Accountant','Accountant','SHG','SHG','Non Member working as Accountant for a Group',5045915596353,630746625601),(4,'Group Secretary','SHG Member','SHG','SHG','Secretary of the Group',5045915858505,630746625609),(5,'Group Treasure','SHG Member','SHG','SHG','Treasure of the Group',5085107450441,687194617417),(6,'Group President','SHG Member','SHG','SHG','President of the Group',5085107581513,687194766921),(7,'NGO Agent','NGO','NGO','SHG','Agent from NGO Group which can manage Multiple Groups',849510040337985,388814799371841),(8,'NGO Support Admin','NGO','NGO','SHG','Support Admin from NGO Group which can manage Multiple Groups',955063220567617,16747761114283585),(9,'NGO Admin','NGO','NGO','SHG','Super Admin form NGO Group which can manage Multiple Groups',2221700682874433,18014398509481537),(10,'Bank Auditor','Auditor','Bank','SHG','Auditor from Bank which can monitor Multiple Groups',5076651343937,78634811457),(11,'Area Agent','Agent','SHG-One Agent','SHG','SHG-One Area Agent which can manage Multiple Groups in perticular Area',955063157653065,404620279021129),(12,'Area Support Admin','Admin','Area Admin','SHG','SHG-One Area Support Admin which can manage Multiple Groups in perticular Area',8977100123930185,61766165201944137),(13,'Area Admin','Admin','Area Admin','SHG','SHG-One Area Super Admin which can manage Multiple Groups in perticular Area',8977100123930185,144115188075855433),(14,'Super Area Support Admin','Admin','Super Area Admin','SHG','SHG-One Area Support Admin which can manage Multiple Groups in perticular Area',8977100123930185,61766165201944137),(15,'Super Area Admin','Admin','Super Area Admin','SHG','SHG-One Area Super Admin which can manage Multiple Groups in perticular Area',8977100123930185,144115188075855433),(16,'SHG-One Admin','Admin','SHG-One Admin','SHG','SHG-One Admin which can manage Groups, Auditor Groups & SHG-One Area Admins in all Areas',17984299378671177,144115188075855425),(17,'Super Admin','Admin','SHG-One Admin','SHG','SHG-One Super Admin',9223372036854775807,9223372036854775807),(18,'Udaan Admin','Admin','Udaan Admin','Micro Retailer','Udaan Super Admin',2251318777347657,2251318777347657),(19,'Mega HUB Manager','HUB Manager','Mega HUB','Micro Retailer','Mega HUB Manager',280993940373065,280993940373065),(20,'Local HUB Manager','HUB Manager','Local HUB','Micro Retailer','Local HUB Manager',3917010173513,3917010173513),(21,'Micro Retailer','Micro Retailer','Micro Retailer','Micro Retailer','Micro Retailer',262664,262664),(22,'Sales Executive','Sales Executive','Sales Executive','Micro Retailer','Sales Executive',1077681737,1077681737),(23,'Sub Local HUB Manager','HUB Manager','Sub Local HUB','Micro Retailer','Sub Local HUB Manager',3917010173513,3917010173513),(24,'Super Sales Executive','Sales Executive','Super Sales Executive','Micro Retailer','Super Sales Executive',1077681737,1077681737);
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
  `first_name` varchar(30) DEFAULT NULL,
  `middle_name` varchar(30) DEFAULT NULL,
  `last_name` varchar(30) DEFAULT NULL,
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
  `sms_format` varchar(500) DEFAULT NULL,
  `email_format` varchar(1000) DEFAULT NULL,
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
  `net_profit_proj_am` decimal(16,2) DEFAULT NULL,
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
  `loan_ac_name` varchar(50) DEFAULT NULL,
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
INSERT INTO `monthly_report_sheet` VALUES (1,1,'Receipts BANK','Receipts','Value of All Receipts','BANK','All the amount Reveived by Group','monthly_g_ac:saved_am:Total Saving;monthly_g_ac:rec_int_on_loan_am:Interest Earned On Loans;monthly_g_ac:p_rec_int_on_loan_am:Earnings On SHG Projects;monthly_g_ac:rec_int_on_fix_deposit_am:Interest Earned On Fixed Deposit;monthly_g_ac:rec_int_on_other_inv_am:Interest Earned On Other Investments;monthly_g_ac:bank_loan_am:Bank Loan;monthly_g_ac:fee_penalty_am:Penalty - Fees - Charges;monthly_g_ac:int_en_on_saving_ac_am:Interest Earned On Bank Saving Accounts;monthly_g_ac:rec_loan_am:Recovered Loans;monthly_g_ac:p_rec_loan_am:Recovered SHG Projects Loan;monthly_g_ac:rec_fix_deposit_am:Recovered Fixed Deposit;monthly_g_ac:rec_other_inv_am:Recovered Other Investments;'),(2,1,'Payments BANK','Payments','Value of All Payments','BANK','All Payments done by the Group','monthly_g_ac:loan_am:Total Loan;monthly_g_ac:p_loan_am:SHG Project Loan;monthly_g_ac:fix_deposit_inv_am:Fix Deposit Investments;monthly_g_ac:other_inv_am:Other Investments;monthly_g_ac:paid_bank_loan_am:Paid Bank Loans;monthly_g_ac:paid_int_on_bank_loan_am:Interest Paid on Bank Loans;monthly_g_ac:returned_saved_am:Returned Savings;monthly_g_ac:returned_int_en_am:Returned Interest on Savings;monthly_g_ac:bank_balance_am:Bank Balance;monthly_g_ac:expenses_am:Expenses;monthly_g_ac:profit_share_paid_am:Profit Share Paid;monthly_g_ac:divided_paid_am:Divided Paid;monthly_g_ac:cash_in_hand_am:Cash In Hand;'),(3,1,'Receipts DETAILED','Receipts','Value of All Receipts','DETAILED','All the amount Reveived by Group','monthly_g_ac:saved_am:Total Saving;monthly_g_ac:rec_int_on_loan_am:Interest Earned On Loans;monthly_g_ac:p_rec_int_on_loan_am:Earnings On SHG Projects;monthly_g_ac:rec_int_on_fix_deposit_am:Interest Earned On Fixed Deposit;monthly_g_ac:rec_int_on_other_inv_am:Interest Earned On Other Investments;monthly_g_ac:bank_loan_am:Bank Loan;monthly_g_ac:fee_penalty_am:Penalty - Fees - Charges;monthly_g_ac:int_en_on_saving_ac_am:Interest Earned On Bank Saving Accounts;monthly_g_ac:rec_loan_am:Recovered Loans;monthly_g_ac:p_rec_loan_am:Recovered SHG Projects Loan;monthly_g_ac:rec_fix_deposit_am:Recovered Fixed Deposit;monthly_g_ac:rec_other_inv_am:Recovered Other Investments;'),(4,1,'Payments DETAILED','Payments','Value of All Payments','DETAILED','All Payments done by the Group','monthly_g_ac:loan_am:Total Loan;monthly_g_ac:p_loan_am:SHG Project Loan;monthly_g_ac:fix_deposit_inv_am:Fix Deposit Investments;monthly_g_ac:other_inv_am:Other Investments;monthly_g_ac:paid_bank_loan_am:Paid Bank Loans;monthly_g_ac:paid_int_on_bank_loan_am:Interest Paid on Bank Loans;monthly_g_ac:returned_saved_am:Returned Savings;monthly_g_ac:returned_int_en_am:Returned Interest on Savings;monthly_g_ac:bank_balance_am:Bank Balance;monthly_g_ac:expenses_am:Expenses;monthly_g_ac:profit_share_paid_am:Profit Share Paid;monthly_g_ac:divided_paid_am:Divided Paid;monthly_g_ac:cash_in_hand_am:Cash In Hand;'),(5,2,'Profits BANK','Profits','Value of All Receipts','BANK','All the amount Profits for the Group','monthly_g_ac:rec_int_on_loan_am:Interest Earned On Loans;monthly_g_ac:p_rec_int_on_loan_am:Earning On SHG Projects;monthly_g_ac:rec_int_on_fix_deposit_am:Interest Earned On Fixed Deposit;monthly_g_ac:rec_int_on_other_inv_am:Interest Earned On Other Investments;monthly_g_ac:int_en_on_saving_ac_am:Interest Earned On Bank Saving Accounts;monthly_g_ac:fee_penalty_am:Penalty - Fees - Charges;monthly_g_ac:proj_int_on_loan_am:Projected Interest On Loan;monthly_g_ac:p_proj_int_on_loan_am:Projected Earnings On SHG Project;monthly_g_ac:proj_int_on_fix_deposit_am:Projected Earnings On Fix Deposit;monthly_g_ac:proj_int_on_other_inv_am:Projected Earnings On Other Investments;'),(6,2,'Losses BANK','Losses','Value of All Losses','BANK','All the amount Loss for the Group','monthly_g_ac:expenses_am:Expenses;monthly_g_ac:paid_int_on_bank_loan_am:Interest Paid on Bank Loans;monthly_g_ac:returned_int_en_am:Returned Interest on Savings;monthly_g_ac:profit_share_paid_am:Profit Share P\n\naid;monthly_g_ac:divided_paid_am:Divided Paid;monthly_g_ac:proj_int_on_bank_loan_am:Projected Interest On Bank Loan;monthly_g_ac:prov_int_en_am:Provisional Interest on Saving;monthly_g_ac:net_profit_am:Net Profit;'),(7,2,'Profits DETAILED','Profits','Value of All Profits','DETAILED','All the amount Profit for the Group','monthly_g_ac:rec_int_on_loan_am:Interest Earned On Loans;monthly_g_ac:p_rec_int_on_loan_am:Earning On SHG Projects;monthly_g_ac:rec_int_on_fix_deposit_am:Interest Earned On Fixed Deposit;monthly_g_ac:rec_int_on_other_inv_am:Interest Earned On Other Investments;monthly_g_ac:int_en_on_saving_ac_am:Interest Earned On Bank Saving Accounts;monthly_g_ac:fee_penalty_am:Penalty - Fees - Charges;monthly_g_ac:proj_int_on_loan_am:Projected Interest On Loan;monthly_g_ac:p_proj_int_on_loan_am:Projected Earnings On SHG Project;monthly_g_ac:proj_int_on_fix_deposit_am:Projected Earnings On Fix Deposit;monthly_g_ac:proj_int_on_other_inv_am:Projected Earnings On Other Investments;'),(8,2,'Losses DETAILED','Losses','Value of All Losses','DETAILED','All the amount Loss for the Group','monthly_g_ac:expenses_am:Expenses;monthly_g_ac:paid_int_on_bank_loan_am:Interest Paid on Bank Loans;monthly_g_ac:returned_int_en_am:Returned Interest on Savings;monthly_g_ac:profit_share_paid_am:Profit Share Paid;monthly_g_ac:divided_paid_am:Divided Paid;monthly_g_ac:proj_int_on_bank_loan_am:Projected Interest On Bank Loan;monthly_g_ac:prov_int_en_am:Provisional Interest on Saving;monthly_g_ac:net_profit_am:Net Profit;'),(9,3,'Assets BANK','Assets','Value of All Assets','BANK','All Assets of the Group','monthly_g_ac:outstanding_loan_am:Outstanding Loan;monthly_g_ac:outstanding_p_loan_am:Outstanding SHG Project Loan;monthly_g_ac:outstanding_fix_deposit_am:Fix Deposit Investments;monthly_g_ac:outstanding_other_inv_am:Other Investments;monthly_g_ac:bank_balance_am:Bank Balance;monthly_g_ac:cash_in_hand_am:Cash In Hand;monthly_g_ac:proj_int_on_loan_am:Projected Interest On Loan;monthly_g_ac:p_proj_int_on_loan_am:Projected Earnings On SHG Project;monthly_g_ac:proj_int_on_fix_deposit_am:Projected Earnings On Fix Deposit;monthly_g_ac:proj_int_on_other_inv_am:Projected Earnings On Other Investments;'),(10,3,'Liabilities BANK','Liabilities','Value of All Liabilities','BANK','All Liabilities of the Group','monthly_g_ac:outstanding_saved_am:Outstanding Saving;monthly_g_ac:outstanding_bank_loan_am:Outstanding Bank Loan;monthly_g_ac:proj_int_on_bank_loan_am:Projected Interest On Bank Loan;monthly_g_ac:outstanding_prov_int_en_am:Outstanding Provisional Interest on Saving;monthly_g_ac:net_profit_am:Net Profit;'),(11,3,'Assets DETAILED','Assets','Value of All Assets','DETAILED','All Assets of the Group','monthly_g_ac:outstanding_loan_am:Outstanding Loan;monthly_g_ac:outstanding_p_loan_am:Outstanding SHG Project Loan;monthly_g_ac:outstanding_fix_deposit_am:Fix Deposit Investments;monthly_g_ac:outstanding_other_inv_am:Other Investments;monthly_g_ac:bank_balance_am:Bank Balance;monthly_g_ac:cash_in_hand_am:Cash In Hand;monthly_g_ac:proj_int_on_loan_am:Projected Interest On Loan;monthly_g_ac:p_proj_int_on_loan_am:Projected Earnings On SHG Project;monthly_g_ac:proj_int_on_fix_deposit_am:Projected Earnings On Fix Deposit;monthly_g_ac:proj_int_on_other_inv_am:Projected Earnings On Other Investments;'),(12,3,'Liabilities DETAILED','Liabilities','Value of All Liabilities','DETAILED','All Liabilities of the Group','monthly_g_ac:outstanding_saved_am:Outstanding Saving;monthly_g_ac:outstanding_bank_loan_am:Outstanding Bank Loan;monthly_g_ac:proj_int_on_bank_loan_am:Projected Interest On Bank Loan;monthly_g_ac:outstanding_prov_int_en_am:Outstanding Provisional Interest on Saving;monthly_g_ac:net_profit_am:Net Profit;');
/*!40000 ALTER TABLE `monthly_report_sheet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mr_status`
--

DROP TABLE IF EXISTS `mr_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mr_status` (
  `mr_status_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `mr_status` varchar(45) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `next_status` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`mr_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mr_status`
--

LOCK TABLES `mr_status` WRITE;
/*!40000 ALTER TABLE `mr_status` DISABLE KEYS */;
INSERT INTO `mr_status` VALUES (1,'Non MR','Member is Non MR',''),(2,'Introduction','Sales pitch is done to Prospective MR','Interested;Opening;Submitted;Disqualified;Canceled;'),(3,'Interested','Prospective MR is Interested','Opening;Submitted;Disqualified;Canceled;'),(4,'Opening','MR is in Opening process','Submitted;Disqualified;Canceled;'),(5,'Submitted','MR is Submitted','Active;Disqualified;Canceled;'),(6,'Active','MR is Active','Inactive;Tobe Closed;Closed'),(7,'Inactive','MR is Inactive','Active;Tobe Closed;Closed'),(8,'Tobe Closed','MR is Tobe Closed','Active;Inactive;Closed'),(9,'Closed','MR is Closed',''),(10,'Disqualified','MR is Disqualified due to Eligibility problem',''),(11,'Canceled','MR is Canceled','');
/*!40000 ALTER TABLE `mr_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mr_type`
--

DROP TABLE IF EXISTS `mr_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mr_type` (
  `mr_type_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `mr_type` varchar(45) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `registration_fee` int(10) DEFAULT NULL,
  `deposit_credit_multiplier` float DEFAULT NULL,
  PRIMARY KEY (`mr_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mr_type`
--

LOCK TABLES `mr_type` WRITE;
/*!40000 ALTER TABLE `mr_type` DISABLE KEYS */;
INSERT INTO `mr_type` VALUES (1,'Non MR','Member is Non MR',0,0),(2,'Normal','Normal MR',500,4),(3,'Discounted','MR with Special Discount',500,0),(4,'Close Circle','MR in Close circle which are normally Inavtive',0,0);
/*!40000 ALTER TABLE `mr_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mr_ui_access_code`
--

DROP TABLE IF EXISTS `mr_ui_access_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mr_ui_access_code` (
  `access_code_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `access_code` varchar(45) NOT NULL,
  `access_code_display` varchar(45) NOT NULL,
  `access_code_category` varchar(45) NOT NULL,
  `access_code_desc` varchar(200) DEFAULT NULL,
  `access_level` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`access_code_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mr_ui_access_code`
--

LOCK TABLES `mr_ui_access_code` WRITE;
/*!40000 ALTER TABLE `mr_ui_access_code` DISABLE KEYS */;
INSERT INTO `mr_ui_access_code` VALUES (1,'HUB_DASHBOARD','HUB Dashboard','DASHBOARD',NULL,'NO_ACCESS:READ'),(2,'MR_DASHBOARD','MR Dashboard','DASHBOARD',NULL,'NO_ACCESS:READ'),(3,'MR_WISE_DASHBOARD','All MRs in HUB Dashboard','DASHBOARD',NULL,'NO_ACCESS:READ'),(4,'MR_AC','MR Account','ACCOUNT',NULL,'NO_ACCESS:READ:UPDATE:APPROVE'),(5,'SE_AC','SE Account','ACCOUNT',NULL,'NO_ACCESS:READ:UPDATE:APPROVE'),(6,'HUB_AC','HUB Account','ACCOUNT',NULL,'NO_ACCESS:READ:UPDATE:APPROVE'),(7,'MR_VISIT','MR Visit','TRANSACTION',NULL,'NO_ACCESS:READ:UPDATE:APPROVE'),(8,'MR_VISIT_TRACKING','MR Visit Tracking','TRANSACTION',NULL,'NO_ACCESS:READ:UPDATE:APPROVE'),(9,'PURCHASE_INVOICE','Purchase Invoice Management','PURCHASE',NULL,'NO_ACCESS:READ:UPDATE:APPROVE'),(10,'MANAGE_STOCK','Manage Stock','ADMIN',NULL,'NO_ACCESS:READ:UPDATE:APPROVE'),(11,'MR_REPORT','MR Report','REPORT',NULL,'NO_ACCESS:READ:UPDATE:APPROVE'),(12,'HUB_REPORT','HUB Report','REPORT',NULL,'NO_ACCESS:READ:UPDATE:APPROVE'),(13,'STOCK_REPORTS','Stock Reports','REPORT',NULL,'NO_ACCESS:READ:UPDATE:APPROVE'),(14,'ACCESS_RIGHTS','Access Rights','ADMIN',NULL,'NO_ACCESS:READ:UPDATE:APPROVE'),(15,'MANAGE_MHUB','Manage Mega HUB','ADMIN',NULL,'NO_ACCESS:READ:UPDATE:APPROVE'),(16,'MANAGE_DATA','Manage Bulk Data','ADMIN',NULL,'NO_ACCESS:READ:UPDATE:APPROVE'),(17,'ADMIN_NET','Admin Software Config','ADMIN',NULL,'NO_ACCESS:READ:UPDATE:APPROVE');
/*!40000 ALTER TABLE `mr_ui_access_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mr_visit`
--

DROP TABLE IF EXISTS `mr_visit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mr_visit` (
  `mr_visit_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `owner_ac_no` bigint(20) NOT NULL,
  `auth_ac_no` bigint(20) DEFAULT NULL,
  `visit_status_id` int(10) NOT NULL,
  `visit_type_id` int(10) NOT NULL,
  `scheduled_ts` timestamp NULL DEFAULT NULL,
  `start_ts` timestamp NULL DEFAULT NULL,
  `end_ts` timestamp NULL DEFAULT NULL,
  `latest_update_ts` timestamp NULL DEFAULT NULL,
  `opening_outstanding_am` decimal(16,2) DEFAULT NULL,
  `sold_paid_am` decimal(16,2) DEFAULT NULL,
  `sold_pending_am` decimal(16,2) DEFAULT NULL,
  `closing_outstanding_am` decimal(16,2) DEFAULT NULL,
  `opening_interest_penalty_am` decimal(16,2) DEFAULT NULL,
  `paid_interest_penalty_am` decimal(16,2) DEFAULT NULL,
  `closing_interest_penalty_am` decimal(16,2) DEFAULT NULL,
  `opening_collected_am` decimal(16,2) DEFAULT NULL,
  `paid_collected_am` decimal(16,2) DEFAULT NULL,
  `received_collected_am` decimal(16,2) DEFAULT NULL,
  `closing_collected_am` decimal(16,2) DEFAULT NULL,
  `opening_registration_am` decimal(16,2) DEFAULT NULL,
  `paid_registration_am` decimal(16,2) DEFAULT NULL,
  `closing_registration_am` decimal(16,2) DEFAULT NULL,
  `opening_deposit_am` decimal(16,2) DEFAULT NULL,
  `paid_deposit_am` decimal(16,2) DEFAULT NULL,
  `returned_deposit_am` decimal(16,2) DEFAULT NULL,
  `closing_deposit_am` decimal(16,2) DEFAULT NULL,
  `opening_stock_am` decimal(16,2) DEFAULT NULL,
  `return_stock_am` decimal(16,2) DEFAULT NULL,
  `sold_stock_am` decimal(16,2) DEFAULT NULL,
  `sold_stock_discount_am` decimal(16,2) DEFAULT NULL,
  `mr_sold_stock_am` decimal(16,2) DEFAULT NULL,
  `given_stock_am` decimal(16,2) DEFAULT NULL,
  `closing_stock_am` decimal(16,2) DEFAULT NULL,
  `opening_stock_no` int(10) DEFAULT NULL,
  `return_stock_no` int(10) DEFAULT NULL,
  `sold_stock_no` int(10) DEFAULT NULL,
  `sold_stock_discount_no` int(10) DEFAULT NULL,
  `given_stock_no` int(10) DEFAULT NULL,
  `closing_stock_no` int(10) DEFAULT NULL,
  `total_stock_am` decimal(16,2) DEFAULT NULL,
  `total_stock_returned_am` decimal(16,2) DEFAULT NULL,
  `total_stock_damaged_am` decimal(16,2) DEFAULT NULL,
  `total_stock_sold_am` decimal(16,2) DEFAULT NULL,
  `total_stock_sold_discount_am` decimal(16,2) DEFAULT NULL,
  `total_stock_no` int(10) DEFAULT NULL,
  `total_stock_returned_no` int(10) DEFAULT NULL,
  `total_stock_damaged_no` int(10) DEFAULT NULL,
  `total_stock_sold_no` int(10) DEFAULT NULL,
  `total_stock_sold_discount_no` int(10) DEFAULT NULL,
  `total_paid_sold_am` decimal(16,2) DEFAULT NULL,
  `total_paid_interest_penalty_am` decimal(16,2) DEFAULT NULL,
  `total_paid_collected_am` decimal(16,2) DEFAULT NULL,
  `total_registration_paid_am` decimal(16,2) DEFAULT NULL,
  `total_deposit_paid_am` decimal(16,2) DEFAULT NULL,
  `total_visit_counter` int(10) DEFAULT NULL,
  `location` varchar(200) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`mr_visit_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mr_visit`
--

LOCK TABLES `mr_visit` WRITE;
/*!40000 ALTER TABLE `mr_visit` DISABLE KEYS */;
/*!40000 ALTER TABLE `mr_visit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mr_ws_access_code`
--

DROP TABLE IF EXISTS `mr_ws_access_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mr_ws_access_code` (
  `access_code_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `access_code` varchar(45) NOT NULL,
  `access_code_display` varchar(45) NOT NULL,
  `access_code_category` varchar(45) NOT NULL,
  `access_code_desc` varchar(200) DEFAULT NULL,
  `access_level` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`access_code_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mr_ws_access_code`
--

LOCK TABLES `mr_ws_access_code` WRITE;
/*!40000 ALTER TABLE `mr_ws_access_code` DISABLE KEYS */;
INSERT INTO `mr_ws_access_code` VALUES (1,'HUB_DASHBOARD','HUB Dashboard','DASHBOARD',NULL,'NO_ACCESS:READ'),(2,'MR_DASHBOARD','MR Dashboard','DASHBOARD',NULL,'NO_ACCESS:READ'),(3,'MR_WISE_DASHBOARD','All MRs in HUB Dashboard','DASHBOARD',NULL,'NO_ACCESS:READ'),(4,'MR_AC','MR Account','ACCOUNT',NULL,'NO_ACCESS:READ:UPDATE:APPROVE'),(5,'SE_AC','SE Account','ACCOUNT',NULL,'NO_ACCESS:READ:UPDATE:APPROVE'),(6,'HUB_AC','Local HUB Account','ACCOUNT',NULL,'NO_ACCESS:READ:UPDATE:APPROVE'),(7,'MR_VISIT','MR Visit','TRANSACTION',NULL,'NO_ACCESS:READ:UPDATE:APPROVE'),(8,'MR_VISIT_TRACKING','MR Visit Tracking','TRANSACTION',NULL,'NO_ACCESS:READ:UPDATE:APPROVE'),(9,'PURCHASE_INVOICE','Purchase Invoice Management','PURCHASE',NULL,'NO_ACCESS:READ:UPDATE:APPROVE'),(10,'MANAGE_STOCK','Manage Stock','ADMIN',NULL,'NO_ACCESS:READ:UPDATE:APPROVE'),(11,'MR_REPORT','MR Report','REPORT',NULL,'NO_ACCESS:READ:UPDATE:APPROVE'),(12,'HUB_REPORT','HUB Report','REPORT',NULL,'NO_ACCESS:READ:UPDATE:APPROVE'),(13,'STOCK_REPORT','Stock Reports','REPORT',NULL,'NO_ACCESS:READ:UPDATE:APPROVE'),(14,'ACCESS_RIGHTS','Access Rights','ADMIN',NULL,'NO_ACCESS:READ:UPDATE:APPROVE'),(15,'MANAGE_MHUB','Manage Mega HUB','ADMIN',NULL,'NO_ACCESS:READ:UPDATE:APPROVE'),(16,'MANAGE_DATA','Manage Bulk Data','ADMIN',NULL,'NO_ACCESS:READ:UPDATE:APPROVE'),(17,'ADMIN_NET','Admin Software Config','ADMIN',NULL,'NO_ACCESS:READ:UPDATE:APPROVE');
/*!40000 ALTER TABLE `mr_ws_access_code` ENABLE KEYS */;
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
-- Table structure for table `p_category`
--

DROP TABLE IF EXISTS `p_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `p_category` (
  `p_category_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `category` varchar(45) NOT NULL,
  `sub_cat_l1` varchar(45) DEFAULT NULL,
  `sub_cat_l2` varchar(45) DEFAULT NULL,
  `sub_cat_l3` varchar(45) DEFAULT NULL,
  `sub_cat_l4` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`p_category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `p_category`
--

LOCK TABLES `p_category` WRITE;
/*!40000 ALTER TABLE `p_category` DISABLE KEYS */;
INSERT INTO `p_category` VALUES (1,'DM Pure Cotton Printed','Garments','Dress Material','Pure Cotton','Printed'),(2,'DM Pure Cotton Work','Garments','Dress Material','Pure Cotton','Work'),(3,'DM Pure Cotton 2 Top','Garments','Dress Material','Pure Cotton','2 Top'),(4,'DM Mix Cotton Printed','Garments','Dress Material','Mix Cotton','Printed'),(5,'DM Mix Cotton Work','Garments','Dress Material','Mix Cotton','Work'),(6,'DM Mix Cotton 2 Top','Garments','Dress Material','Mix Cotton','2 Top'),(7,'DM Synthetic Printed','Garments','Dress Material','Synthetic','Printed'),(8,'DM Synthetic Work','Garments','Dress Material','Synthetic','Work'),(9,'DM Synthetic 2 Top','Garments','Dress Material','Synthetic','2 Top'),(10,'DM Pure Silk Printed','Garments','Dress Material','Pure Silk','Printed'),(11,'DM Pure Silk Work','Garments','Dress Material','Pure Silk','Work'),(12,'DM Mix Silk Printed','Garments','Dress Material','Mix Silk','Printed'),(13,'DM Mix Silk Work','Garments','Dress Material','Mix Silk','Work'),(14,'S Pure Cotton Printed','Garments','Saree','Pure Cotton','Printed'),(15,'S Pure Cotton Work','Garments','Saree','Pure Cotton','Work'),(16,'S Pure Cotton Jari','Garments','Saree','Pure Cotton','Jari'),(17,'S Mix Cotton Printed','Garments','Saree','Mix Cotton','Printed'),(18,'S Mix Cotton Work','Garments','Saree','Mix Cotton','Work'),(19,'S Mix Cotton Jari','Garments','Saree','Mix Cotton','Jari'),(20,'S Synthetic Printed','Garments','Saree','Synthetic','Printed'),(21,'S Synthetic Work','Garments','Saree','Synthetic','Work'),(22,'S Synthetic Jari','Garments','Saree','Synthetic','Jari'),(23,'S Chiffon Printed','Garments','Saree','Chiffon','Printed'),(24,'S Chiffon Work','Garments','Saree','Chiffon','Work'),(25,'S Italian Silk Printed','Garments','Saree','Italian Silk','Printed'),(26,'S Italian Silk Work','Garments','Saree','Italian Silk','Work'),(27,'S Pure Silk Printed','Garments','Saree','Pure Silk','Printed'),(28,'S Pure Silk Work','Garments','Saree','Pure Silk','Work'),(29,'S Pure Silk Jari','Garments','Saree','Pure Silk','Jari'),(30,'S Mix Silk Printed','Garments','Saree','Mix Silk','Printed'),(31,'S Mix Silk Work','Garments','Saree','Mix Silk','Work'),(32,'S Mix Silk Jari','Garments','Saree','Mix Silk','Jari'),(33,'Top Pure Cotton Printed','Garments','Top','Pure Cotton','Printed'),(34,'Top Pure Cotton Work','Garments','Top','Pure Cotton','Work'),(35,'Top Mix Cotton Printed','Garments','Top','Mix Cotton','Printed'),(36,'Top Mix Cotton Work','Garments','Top','Mix Cotton','Work'),(37,'Top Synthetic Printed','Garments','Top','Synthetic','Printed'),(38,'Top Synthetic Work','Garments','Top','Synthetic','Work'),(39,'Top Pure Silk Printed','Garments','Top','Pure Silk','Printed'),(40,'Top Pure Silk Work','Garments','Top','Pure Silk','Work'),(41,'Top Mix Silk Printed','Garments','Top','Mix Silk','Printed'),(42,'Top Mix Silk Work','Garments','Top','Mix Silk','Work'),(43,'L Pure Cotton Printed','Garments','Legging','Pure Cotton','Printed'),(44,'L Pure Cotton Work','Garments','Legging','Pure Cotton','Plain'),(45,'L Mix Cotton Printed','Garments','Legging','Mix Cotton','Printed'),(46,'L Mix Cotton Work','Garments','Legging','Mix Cotton','Plain'),(47,'L Synthetic Printed','Garments','Legging','Synthetic','Printed'),(48,'L Synthetic Work','Garments','Legging','Synthetic','Plain'),(49,'Du Cotton Printed','Garments','Dupatta','Pure Cotton','Printed'),(50,'Du Cotton Work','Garments','Dupatta','Pure Cotton','Plain'),(51,'Du Synthetic Printed','Garments','Dupatta','Synthetic','Printed'),(52,'Du Synthetic Work','Garments','Dupatta','Synthetic','Plain'),(53,'Pat Cotton Printed','Garments','Patiala','Pure Cotton','Printed'),(54,'Pat Cotton Work','Garments','Patiala','Pure Cotton','Plain'),(55,'Pat Synthetic Printed','Garments','Patiala','Synthetic','Printed'),(56,'Pat Synthetic Work','Garments','Patiala','Synthetic','Plain');
/*!40000 ALTER TABLE `p_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `p_hub_ac`
--

DROP TABLE IF EXISTS `p_hub_ac`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `p_hub_ac` (
  `p_hub_ac_no` bigint(20) NOT NULL,
  `total_purchased_stock_am` decimal(16,2) DEFAULT NULL,
  `total_stock_am` decimal(16,2) DEFAULT NULL,
  `total_stock_returned_am` decimal(16,2) DEFAULT NULL,
  `total_stock_damaged_am` decimal(16,2) DEFAULT NULL,
  `total_stock_sold_am` decimal(16,2) DEFAULT NULL,
  `total_stock_mr_sold_am` decimal(16,2) DEFAULT NULL,
  `total_stock_direct_sold_am` decimal(16,2) DEFAULT NULL,
  `total_sold_discount_am` decimal(16,2) DEFAULT NULL,
  `current_stock_am` decimal(16,2) DEFAULT NULL,
  `current_stock_discount_am` decimal(16,2) DEFAULT NULL,
  `current_stock_unsettled_am` decimal(16,2) DEFAULT NULL,
  `current_stock_to_return_am` decimal(16,2) DEFAULT NULL,
  `total_purchased_stock_no` int(10) DEFAULT NULL,
  `total_stock_no` int(10) DEFAULT NULL,
  `total_stock_returned_no` int(10) DEFAULT NULL,
  `total_stock_damaged_no` int(10) DEFAULT NULL,
  `total_stock_sold_no` int(10) DEFAULT NULL,
  `total_stock_mr_sold_no` int(10) DEFAULT NULL,
  `total_stock_direct_sold_no` int(10) DEFAULT NULL,
  `total_sold_discount_no` int(10) DEFAULT NULL,
  `current_stock_no` int(10) DEFAULT NULL,
  `current_stock_discount_no` int(10) DEFAULT NULL,
  `current_stock_unsettled_no` int(10) DEFAULT NULL,
  `current_stock_to_return_no` int(10) DEFAULT NULL,
  `return_counter` int(10) DEFAULT NULL,
  `current_ordered_stock_am` decimal(16,2) DEFAULT NULL,
  `current_ordered_stock_no` int(10) DEFAULT NULL,
  `current_delivered_stock_am` decimal(16,2) DEFAULT NULL,
  `current_delivered_stock_no` int(10) DEFAULT NULL,
  `current_to_stock_am` decimal(16,2) DEFAULT NULL,
  `current_to_stock_no` int(10) DEFAULT NULL,
  `current_created_stock_am` decimal(16,2) DEFAULT NULL,
  `current_created_stock_no` int(10) DEFAULT NULL,
  `mh_total_collected_am` decimal(16,2) DEFAULT NULL,
  `mh_total_paid_collected_am` decimal(16,2) DEFAULT NULL,
  `mh_current_collected_am` decimal(16,2) DEFAULT NULL,
  `mh_sold_paid_am` decimal(16,2) DEFAULT NULL,
  `mh_sold_pending_am` decimal(16,2) DEFAULT NULL,
  `mh_total_stock_am` decimal(16,2) DEFAULT NULL,
  `mh_total_stock_returned_am` decimal(16,2) DEFAULT NULL,
  `mh_total_stock_damaged_am` decimal(16,2) DEFAULT NULL,
  `mh_total_stock_sold_am` decimal(16,2) DEFAULT NULL,
  `mh_total_stock_sold_discount_am` decimal(16,2) DEFAULT NULL,
  `mh_current_stock_am` decimal(16,2) DEFAULT NULL,
  `mh_current_stock_discount_am` decimal(16,2) DEFAULT NULL,
  `mh_current_stock_unsettled_am` decimal(16,2) DEFAULT NULL,
  `mh_current_stock_to_return_am` decimal(16,2) DEFAULT NULL,
  `mh_total_stock_no` int(10) DEFAULT NULL,
  `mh_total_stock_returned_no` int(10) DEFAULT NULL,
  `mh_total_stock_damaged_no` int(10) DEFAULT NULL,
  `mh_total_stock_sold_no` int(10) DEFAULT NULL,
  `mh_total_stock_sold_discount_no` int(10) DEFAULT NULL,
  `mh_current_stock_no` int(10) DEFAULT NULL,
  `mh_current_stock_discount_no` int(10) DEFAULT NULL,
  `mh_current_stock_unsettled_no` int(10) DEFAULT NULL,
  `mh_current_stock_to_return_no` int(10) DEFAULT NULL,
  `mh_return_counter` int(10) DEFAULT NULL,
  `lh_total_collected_am` decimal(16,2) DEFAULT NULL,
  `lh_total_paid_collected_am` decimal(16,2) DEFAULT NULL,
  `lh_current_collected_am` decimal(16,2) DEFAULT NULL,
  `lh_sold_paid_am` decimal(16,2) DEFAULT NULL,
  `lh_sold_pending_am` decimal(16,2) DEFAULT NULL,
  `lh_total_stock_am` decimal(16,2) DEFAULT NULL,
  `lh_total_stock_returned_am` decimal(16,2) DEFAULT NULL,
  `lh_total_stock_damaged_am` decimal(16,2) DEFAULT NULL,
  `lh_total_stock_sold_am` decimal(16,2) DEFAULT NULL,
  `lh_total_stock_sold_discount_am` decimal(16,2) DEFAULT NULL,
  `lh_current_stock_am` decimal(16,2) DEFAULT NULL,
  `lh_current_stock_discount_am` decimal(16,2) DEFAULT NULL,
  `lh_current_stock_unsettled_am` decimal(16,2) DEFAULT NULL,
  `lh_current_stock_to_return_am` decimal(16,2) DEFAULT NULL,
  `lh_total_stock_no` int(10) DEFAULT NULL,
  `lh_total_stock_returned_no` int(10) DEFAULT NULL,
  `lh_total_stock_damaged_no` int(10) DEFAULT NULL,
  `lh_total_stock_sold_no` int(10) DEFAULT NULL,
  `lh_total_stock_sold_discount_no` int(10) DEFAULT NULL,
  `lh_current_stock_no` int(10) DEFAULT NULL,
  `lh_current_stock_discount_no` int(10) DEFAULT NULL,
  `lh_current_stock_unsettled_no` int(10) DEFAULT NULL,
  `lh_current_stock_to_return_no` int(10) DEFAULT NULL,
  `lh_return_counter` int(10) DEFAULT NULL,
  `slh_total_collected_am` decimal(16,2) DEFAULT NULL,
  `slh_total_paid_collected_am` decimal(16,2) DEFAULT NULL,
  `slh_current_collected_am` decimal(16,2) DEFAULT NULL,
  `slh_sold_paid_am` decimal(16,2) DEFAULT NULL,
  `slh_sold_pending_am` decimal(16,2) DEFAULT NULL,
  `slh_total_stock_am` decimal(16,2) DEFAULT NULL,
  `slh_total_stock_returned_am` decimal(16,2) DEFAULT NULL,
  `slh_total_stock_damaged_am` decimal(16,2) DEFAULT NULL,
  `slh_total_stock_sold_am` decimal(16,2) DEFAULT NULL,
  `slh_total_stock_sold_discount_am` decimal(16,2) DEFAULT NULL,
  `slh_current_stock_am` decimal(16,2) DEFAULT NULL,
  `slh_current_stock_discount_am` decimal(16,2) DEFAULT NULL,
  `slh_current_stock_unsettled_am` decimal(16,2) DEFAULT NULL,
  `slh_current_stock_to_return_am` decimal(16,2) DEFAULT NULL,
  `slh_total_stock_no` int(10) DEFAULT NULL,
  `slh_total_stock_returned_no` int(10) DEFAULT NULL,
  `slh_total_stock_damaged_no` int(10) DEFAULT NULL,
  `slh_total_stock_sold_no` int(10) DEFAULT NULL,
  `slh_total_stock_sold_discount_no` int(10) DEFAULT NULL,
  `slh_current_stock_no` int(10) DEFAULT NULL,
  `slh_current_stock_discount_no` int(10) DEFAULT NULL,
  `slh_current_stock_unsettled_no` int(10) DEFAULT NULL,
  `slh_current_stock_to_return_no` int(10) DEFAULT NULL,
  `slh_return_counter` int(10) DEFAULT NULL,
  `se_total_collected_am` decimal(16,2) DEFAULT NULL,
  `se_total_paid_collected_am` decimal(16,2) DEFAULT NULL,
  `se_current_collected_am` decimal(16,2) DEFAULT NULL,
  `se_sold_paid_am` decimal(16,2) DEFAULT NULL,
  `se_sold_pending_am` decimal(16,2) DEFAULT NULL,
  `se_total_stock_am` decimal(16,2) DEFAULT NULL,
  `se_total_stock_returned_am` decimal(16,2) DEFAULT NULL,
  `se_total_stock_damaged_am` decimal(16,2) DEFAULT NULL,
  `se_total_stock_sold_am` decimal(16,2) DEFAULT NULL,
  `se_total_stock_sold_discount_am` decimal(16,2) DEFAULT NULL,
  `se_current_stock_am` decimal(16,2) DEFAULT NULL,
  `se_current_stock_discount_am` decimal(16,2) DEFAULT NULL,
  `se_current_stock_unsettled_am` decimal(16,2) DEFAULT NULL,
  `se_current_stock_to_return_am` decimal(16,2) DEFAULT NULL,
  `se_total_stock_no` int(10) DEFAULT NULL,
  `se_total_stock_returned_no` int(10) DEFAULT NULL,
  `se_total_stock_damaged_no` int(10) DEFAULT NULL,
  `se_total_stock_sold_no` int(10) DEFAULT NULL,
  `se_total_stock_sold_discount_no` int(10) DEFAULT NULL,
  `se_current_stock_no` int(10) DEFAULT NULL,
  `se_current_stock_discount_no` int(10) DEFAULT NULL,
  `se_current_stock_unsettled_no` int(10) DEFAULT NULL,
  `se_current_stock_to_return_no` int(10) DEFAULT NULL,
  `se_return_counter` int(10) DEFAULT NULL,
  `mr_total_no` int(10) DEFAULT NULL,
  `mr_active_no` int(10) DEFAULT NULL,
  `mr_inactive_no` int(10) DEFAULT NULL,
  `mr_tobe_closed_no` int(10) DEFAULT NULL,
  `mr_closed_no` int(10) DEFAULT NULL,
  `mr_registration_paid_am` decimal(16,2) DEFAULT NULL,
  `mr_registration_pending_am` decimal(16,2) DEFAULT NULL,
  `mr_deposit_paid_am` decimal(16,2) DEFAULT NULL,
  `mr_deposit_pending_am` decimal(16,2) DEFAULT NULL,
  `mr_deposit_return_am` decimal(16,2) DEFAULT NULL,
  `mr_credit_limit_am` decimal(16,2) DEFAULT NULL,
  `mr_sold_paid_am` decimal(16,2) DEFAULT NULL,
  `mr_sold_pending_am` decimal(16,2) DEFAULT NULL,
  `mr_paid_interest_penalty_am` decimal(16,2) DEFAULT NULL,
  `mr_pending_interest_penalty_am` decimal(16,2) DEFAULT NULL,
  `mr_total_stock_am` decimal(16,2) DEFAULT NULL,
  `mr_total_stock_returned_am` decimal(16,2) DEFAULT NULL,
  `mr_total_stock_damaged_am` decimal(16,2) DEFAULT NULL,
  `mr_total_stock_sold_am` decimal(16,2) DEFAULT NULL,
  `mr_total_stock_sold_discount_am` decimal(16,2) DEFAULT NULL,
  `mr_current_stock_am` decimal(16,2) DEFAULT NULL,
  `mr_current_stock_discount_am` decimal(16,2) DEFAULT NULL,
  `mr_current_stock_unsettled_am` decimal(16,2) DEFAULT NULL,
  `mr_current_stock_to_return_am` decimal(16,2) DEFAULT NULL,
  `mr_total_stock_no` int(10) DEFAULT NULL,
  `mr_total_stock_returned_no` int(10) DEFAULT NULL,
  `mr_total_stock_damaged_no` int(10) DEFAULT NULL,
  `mr_total_stock_sold_no` int(10) DEFAULT NULL,
  `mr_total_stock_sold_discount_no` int(10) DEFAULT NULL,
  `mr_current_stock_no` int(10) DEFAULT NULL,
  `mr_current_stock_discount_no` int(10) DEFAULT NULL,
  `mr_current_stock_unsettled_no` int(10) DEFAULT NULL,
  `mr_current_stock_to_return_no` int(10) DEFAULT NULL,
  `mr_return_counter` int(10) DEFAULT NULL,
  `performance_index` float DEFAULT NULL,
  `return_index` float DEFAULT NULL,
  `sales_index` float DEFAULT NULL,
  `sales_50_per_days` float DEFAULT NULL,
  `sales_70_per_days` float DEFAULT NULL,
  `sales_80_per_days` float DEFAULT NULL,
  `sales_90_per_days` float DEFAULT NULL,
  `sales_100_per_days` float DEFAULT NULL,
  PRIMARY KEY (`p_hub_ac_no`),
  CONSTRAINT `p_hub_ac_pk` FOREIGN KEY (`p_hub_ac_no`) REFERENCES `g_profile` (`g_ac_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `p_hub_ac`
--

LOCK TABLES `p_hub_ac` WRITE;
/*!40000 ALTER TABLE `p_hub_ac` DISABLE KEYS */;
/*!40000 ALTER TABLE `p_hub_ac` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `p_invoice`
--

DROP TABLE IF EXISTS `p_invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `p_invoice` (
  `p_invoice_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `vender_ac_no` bigint(20) NOT NULL,
  `invoice_no` varchar(20) DEFAULT NULL,
  `invoice_status_id` int(10) unsigned NOT NULL,
  `invoice_ts` timestamp NULL DEFAULT NULL,
  `entry_ts` timestamp NULL DEFAULT NULL,
  `stock_ts` timestamp NULL DEFAULT NULL,
  `expected_delivery_ts` timestamp NULL DEFAULT NULL,
  `lr_slips` varchar(100) DEFAULT NULL,
  `entry_by` bigint(20) DEFAULT NULL,
  `no_of_lots` int(10) DEFAULT NULL,
  `no_of_items` int(10) DEFAULT NULL,
  `vat_per` float DEFAULT NULL,
  `discount_per` float DEFAULT NULL,
  `total_am` decimal(16,2) DEFAULT NULL,
  `discount_am` decimal(16,2) DEFAULT NULL,
  `gross_total_am` decimal(16,2) DEFAULT NULL,
  `vat_am` decimal(16,2) DEFAULT NULL,
  `other_taxes_am` decimal(16,2) DEFAULT NULL,
  `net_total_am` decimal(16,2) DEFAULT NULL,
  `net_total_calculated_am` decimal(16,2) DEFAULT NULL,
  `advance_am` decimal(16,2) DEFAULT NULL,
  `net_payment_am` decimal(16,2) DEFAULT NULL,
  `payment_mode_id` int(10) unsigned DEFAULT NULL,
  `payment_ts` timestamp NULL DEFAULT NULL,
  `cheque_no` varchar(20) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `attachment` varchar(200) DEFAULT NULL,
  `entry_location` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`p_invoice_id`),
  KEY `p_invoice_fk1_idx` (`vender_ac_no`),
  KEY `p_invoice_fk3_idx` (`entry_by`),
  CONSTRAINT `p_invoice_fk1` FOREIGN KEY (`vender_ac_no`) REFERENCES `g_profile` (`g_ac_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `p_invoice_fk3` FOREIGN KEY (`entry_by`) REFERENCES `m_profile` (`m_ac_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `p_invoice`
--

LOCK TABLES `p_invoice` WRITE;
/*!40000 ALTER TABLE `p_invoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `p_invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `p_m_ac`
--

DROP TABLE IF EXISTS `p_m_ac`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `p_m_ac` (
  `p_m_ac_no` bigint(20) NOT NULL,
  `mr_status_id` int(10) DEFAULT NULL,
  `mr_type_id` int(10) DEFAULT NULL,
  `registration_paid_am` decimal(16,2) DEFAULT NULL,
  `registration_pending_am` decimal(16,2) DEFAULT NULL,
  `deposit_paid_am` decimal(16,2) DEFAULT NULL,
  `deposit_pending_am` decimal(16,2) DEFAULT NULL,
  `deposit_return_am` decimal(16,2) DEFAULT NULL,
  `credit_limit_am` decimal(16,2) DEFAULT NULL,
  `total_collected_am` decimal(16,2) DEFAULT NULL,
  `total_paid_collected_am` decimal(16,2) DEFAULT NULL,
  `current_collected_am` decimal(16,2) DEFAULT NULL,
  `sold_paid_am` decimal(16,2) DEFAULT NULL,
  `sold_pending_am` decimal(16,2) DEFAULT NULL,
  `paid_interest_penalty_am` decimal(16,2) DEFAULT NULL,
  `pending_interest_penalty_am` decimal(16,2) DEFAULT NULL,
  `last_visit_on` timestamp NULL DEFAULT NULL,
  `interest_calculated_on` timestamp NULL DEFAULT NULL,
  `current_stock_am` decimal(16,2) DEFAULT NULL,
  `current_stock_discount_am` decimal(16,2) DEFAULT NULL,
  `current_stock_unsettled_am` decimal(16,2) DEFAULT NULL,
  `current_stock_to_return_am` decimal(16,2) DEFAULT NULL,
  `current_stock_no` int(10) DEFAULT NULL,
  `current_stock_discount_no` int(10) DEFAULT NULL,
  `current_stock_unsettled_no` int(10) DEFAULT NULL,
  `current_stock_to_return_no` int(10) DEFAULT NULL,
  `total_stock_am` decimal(16,2) DEFAULT NULL,
  `total_stock_returned_am` decimal(16,2) DEFAULT NULL,
  `total_stock_damaged_am` decimal(16,2) DEFAULT NULL,
  `total_stock_sold_am` decimal(16,2) DEFAULT NULL,
  `total_stock_sold_discount_am` decimal(16,2) DEFAULT NULL,
  `total_stock_mr_sold_am` decimal(16,2) DEFAULT NULL,
  `total_stock_no` int(10) DEFAULT NULL,
  `total_stock_returned_no` int(10) DEFAULT NULL,
  `total_stock_damaged_no` int(10) DEFAULT NULL,
  `total_stock_sold_no` int(10) DEFAULT NULL,
  `total_stock_sold_discount_no` int(10) DEFAULT NULL,
  `total_visit_counter` int(10) DEFAULT NULL,
  `this_month_stock_am` decimal(16,2) DEFAULT NULL,
  `this_month_stock_returned_am` decimal(16,2) DEFAULT NULL,
  `this_month_stock_damaged_am` decimal(16,2) DEFAULT NULL,
  `this_month_stock_sold_am` decimal(16,2) DEFAULT NULL,
  `this_month_stock_sold_discount_am` decimal(16,2) DEFAULT NULL,
  `this_month_stock_mr_sold_am` decimal(16,2) DEFAULT NULL,
  `this_month_stock_no` int(10) DEFAULT NULL,
  `this_month_stock_returned_no` int(10) DEFAULT NULL,
  `this_month_stock_damaged_no` int(10) DEFAULT NULL,
  `this_month_stock_sold_no` int(10) DEFAULT NULL,
  `this_month_stock_sold_discount_no` int(10) DEFAULT NULL,
  `this_month_visit_counter` int(10) DEFAULT NULL,
  `last_month_stock_am` decimal(16,2) DEFAULT NULL,
  `last_month_stock_returned_am` decimal(16,2) DEFAULT NULL,
  `last_month_stock_damaged_am` decimal(16,2) DEFAULT NULL,
  `last_month_stock_sold_am` decimal(16,2) DEFAULT NULL,
  `last_month_stock_sold_discount_am` decimal(16,2) DEFAULT NULL,
  `last_month_stock_mr_sold_am` decimal(16,2) DEFAULT NULL,
  `last_month_stock_no` int(10) DEFAULT NULL,
  `last_month_stock_returned_no` int(10) DEFAULT NULL,
  `last_month_stock_damaged_no` int(10) DEFAULT NULL,
  `last_month_stock_sold_no` int(10) DEFAULT NULL,
  `last_month_stock_sold_discount_no` int(10) DEFAULT NULL,
  `last_month_visit_counter` int(10) DEFAULT NULL,
  `performance_index` float DEFAULT NULL,
  `return_index` float DEFAULT NULL,
  `sales_index` float DEFAULT NULL,
  PRIMARY KEY (`p_m_ac_no`),
  CONSTRAINT `p_m_ac_pk` FOREIGN KEY (`p_m_ac_no`) REFERENCES `m_profile` (`m_ac_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `p_m_ac`
--

LOCK TABLES `p_m_ac` WRITE;
/*!40000 ALTER TABLE `p_m_ac` DISABLE KEYS */;
/*!40000 ALTER TABLE `p_m_ac` ENABLE KEYS */;
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
-- Table structure for table `payment_type`
--

DROP TABLE IF EXISTS `payment_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_type` (
  `payment_type_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `payment_type` varchar(45) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`payment_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_type`
--

LOCK TABLES `payment_type` WRITE;
/*!40000 ALTER TABLE `payment_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pkg`
--

DROP TABLE IF EXISTS `pkg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pkg` (
  `pkg_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `pkg_status_id` int(10) unsigned NOT NULL,
  `pkg_type_id` int(10) unsigned NOT NULL,
  `super_pkg_id` bigint(20) unsigned DEFAULT NULL,
  `owner_ac_no` bigint(20) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `value_am` decimal(16,2) DEFAULT NULL,
  PRIMARY KEY (`pkg_id`),
  KEY `pkg_fk1_idx` (`super_pkg_id`),
  KEY `pkg_fk2_idx` (`owner_ac_no`),
  CONSTRAINT `pkg_fk1` FOREIGN KEY (`super_pkg_id`) REFERENCES `pkg` (`pkg_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `pkg_fk2` FOREIGN KEY (`owner_ac_no`) REFERENCES `p_m_ac` (`p_m_ac_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pkg`
--

LOCK TABLES `pkg` WRITE;
/*!40000 ALTER TABLE `pkg` DISABLE KEYS */;
/*!40000 ALTER TABLE `pkg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pkg_status`
--

DROP TABLE IF EXISTS `pkg_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pkg_status` (
  `pkg_status_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `pkg_status` varchar(45) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `next_status` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`pkg_status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pkg_status`
--

LOCK TABLES `pkg_status` WRITE;
/*!40000 ALTER TABLE `pkg_status` DISABLE KEYS */;
/*!40000 ALTER TABLE `pkg_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pkg_type`
--

DROP TABLE IF EXISTS `pkg_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pkg_type` (
  `pkg_type_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `pkg_type` varchar(45) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`pkg_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pkg_type`
--

LOCK TABLES `pkg_type` WRITE;
/*!40000 ALTER TABLE `pkg_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `pkg_type` ENABLE KEYS */;
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
-- Table structure for table `product_pricing`
--

DROP TABLE IF EXISTS `product_pricing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_pricing` (
  `product_pricing_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `product_pricing` varchar(45) NOT NULL,
  `wp_per` float DEFAULT NULL,
  `mr_per` float DEFAULT NULL,
  `vat_per` float DEFAULT NULL,
  `other_taxes_per` float DEFAULT NULL,
  PRIMARY KEY (`product_pricing_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_pricing`
--

LOCK TABLES `product_pricing` WRITE;
/*!40000 ALTER TABLE `product_pricing` DISABLE KEYS */;
INSERT INTO `product_pricing` VALUES (1,'Default',1.44,1.45,0,0),(2,'Saree',1.44,1.45,0,0),(3,'Dress Material',1.44,1.45,0,0),(4,'Top',1.44,1.45,0.12,0),(5,'Legging',1.44,1.45,0.12,0),(6,'Dupatta',1.44,1.45,0.12,0),(7,'Patiala',1.44,1.45,0.12,0);
/*!40000 ALTER TABLE `product_pricing` ENABLE KEYS */;
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
-- Table structure for table `s_invoice`
--

DROP TABLE IF EXISTS `s_invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_invoice` (
  `s_invoice_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `sold_by_ac_no` bigint(20) NOT NULL,
  `entry_by_ac_no` bigint(20) NOT NULL,
  `purchased_by_ac_no` bigint(20) NOT NULL,
  `invoice_status_id` int(10) unsigned NOT NULL,
  `invoice_ts` timestamp NULL DEFAULT NULL,
  `entry_ts` timestamp NULL DEFAULT NULL,
  `delivery_ts` timestamp NULL DEFAULT NULL,
  `no_of_lots` int(10) DEFAULT NULL,
  `no_of_items` int(10) DEFAULT NULL,
  `total_am` decimal(16,2) DEFAULT NULL,
  `discount_am` decimal(16,2) DEFAULT NULL,
  `gross_total_am` decimal(16,2) DEFAULT NULL,
  `vat_am` decimal(16,2) DEFAULT NULL,
  `other_tax_am` decimal(16,2) DEFAULT NULL,
  `net_total_am` decimal(16,2) DEFAULT NULL,
  `advance_am` decimal(16,2) DEFAULT NULL,
  `net_payment_am` decimal(16,2) DEFAULT NULL,
  `payment_mode_id` int(10) unsigned DEFAULT NULL,
  `payment_ts` timestamp NULL DEFAULT NULL,
  `cheque_no` varchar(20) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `entry_location` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`s_invoice_id`),
  KEY `s_invoice_fk1_idx` (`sold_by_ac_no`),
  KEY `s_invoice_fk2_idx` (`entry_by_ac_no`),
  KEY `s_invoice_fk3_idx` (`purchased_by_ac_no`),
  CONSTRAINT `s_invoice_fk1` FOREIGN KEY (`sold_by_ac_no`) REFERENCES `p_m_ac` (`p_m_ac_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `s_invoice_fk2` FOREIGN KEY (`entry_by_ac_no`) REFERENCES `m_profile` (`m_ac_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `s_invoice_fk3` FOREIGN KEY (`purchased_by_ac_no`) REFERENCES `p_m_ac` (`p_m_ac_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_invoice`
--

LOCK TABLES `s_invoice` WRITE;
/*!40000 ALTER TABLE `s_invoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `s_invoice` ENABLE KEYS */;
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
-- Table structure for table `shipment`
--

DROP TABLE IF EXISTS `shipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shipment` (
  `shipment_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `pkg_id` bigint(20) unsigned NOT NULL,
  `shipment_status_id` int(10) unsigned NOT NULL,
  `title` varchar(200) DEFAULT NULL,
  `to` varchar(100) DEFAULT NULL,
  `to_address` varchar(300) DEFAULT NULL,
  `from` varchar(100) DEFAULT NULL,
  `from_address` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`shipment_id`),
  KEY `shipment_fk1_idx` (`pkg_id`),
  CONSTRAINT `shipment_fk1` FOREIGN KEY (`pkg_id`) REFERENCES `pkg` (`pkg_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipment`
--

LOCK TABLES `shipment` WRITE;
/*!40000 ALTER TABLE `shipment` DISABLE KEYS */;
/*!40000 ALTER TABLE `shipment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shipment_status`
--

DROP TABLE IF EXISTS `shipment_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shipment_status` (
  `shipment_status_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `shipment_status` varchar(45) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `next_status` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`shipment_status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipment_status`
--

LOCK TABLES `shipment_status` WRITE;
/*!40000 ALTER TABLE `shipment_status` DISABLE KEYS */;
/*!40000 ALTER TABLE `shipment_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock_item`
--

DROP TABLE IF EXISTS `stock_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stock_item` (
  `stock_item_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `stock_type_id` bigint(20) unsigned NOT NULL,
  `lot_id` bigint(20) unsigned NOT NULL,
  `item_status_id` int(10) unsigned NOT NULL,
  `item_condition_id` int(10) unsigned NOT NULL,
  `s_invoice_id` bigint(20) unsigned DEFAULT NULL,
  `pkg_id` bigint(20) unsigned DEFAULT NULL,
  `owner_ac_no` bigint(20) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `design_no` varchar(20) DEFAULT NULL,
  `ws_price_am` decimal(16,2) DEFAULT NULL,
  `mr_price_am` decimal(16,2) DEFAULT NULL,
  `mrp_price_am` decimal(16,2) DEFAULT NULL,
  `dis_mrp_price_am` decimal(16,2) DEFAULT NULL,
  `sold_price_am` decimal(16,2) DEFAULT NULL,
  `mr_sold_price_am` decimal(16,2) DEFAULT NULL,
  `discount_per` float DEFAULT NULL,
  `discount_am` decimal(16,2) DEFAULT NULL,
  `vat_per` float DEFAULT NULL,
  `vat_am` decimal(16,2) DEFAULT NULL,
  `check_ts` timestamp NULL DEFAULT NULL,
  `check_flag` int(10) DEFAULT NULL,
  PRIMARY KEY (`stock_item_id`),
  KEY `stock_item_fk1_idx` (`stock_type_id`),
  KEY `stock_item_fk2_idx` (`lot_id`),
  KEY `stock_item_fk4_idx` (`s_invoice_id`),
  KEY `stock_item_fk5_idx` (`pkg_id`),
  KEY `stock_item_fk6_idx` (`owner_ac_no`),
  CONSTRAINT `stock_item_fk1` FOREIGN KEY (`stock_type_id`) REFERENCES `stock_type` (`stock_type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `stock_item_fk2` FOREIGN KEY (`lot_id`) REFERENCES `lot` (`lot_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `stock_item_fk4` FOREIGN KEY (`s_invoice_id`) REFERENCES `s_invoice` (`s_invoice_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `stock_item_fk5` FOREIGN KEY (`pkg_id`) REFERENCES `pkg` (`pkg_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `stock_item_fk6` FOREIGN KEY (`owner_ac_no`) REFERENCES `p_m_ac` (`p_m_ac_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock_item`
--

LOCK TABLES `stock_item` WRITE;
/*!40000 ALTER TABLE `stock_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `stock_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock_tx`
--

DROP TABLE IF EXISTS `stock_tx`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stock_tx` (
  `stock_tx_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `stock_tx_type_id` int(10) unsigned NOT NULL,
  `stock_item_id` bigint(20) unsigned DEFAULT NULL,
  `stock_type_id` bigint(20) unsigned DEFAULT NULL,
  `brand_id` bigint(20) unsigned DEFAULT NULL,
  `lot_id` bigint(20) unsigned DEFAULT NULL,
  `amount` decimal(16,2) DEFAULT NULL,
  `extra_am` decimal(16,2) DEFAULT NULL,
  `entry_by_ac_no` bigint(20) unsigned DEFAULT NULL,
  `owner_ac_no` bigint(20) unsigned DEFAULT NULL,
  `auth_ac_no` bigint(20) unsigned DEFAULT NULL,
  `mr_visit_id` bigint(20) unsigned DEFAULT NULL,
  `item_status_id` int(10) unsigned DEFAULT NULL,
  `prev_item_status_id` int(10) unsigned DEFAULT NULL,
  `tx_ts` timestamp NULL DEFAULT NULL,
  `entry_ts` timestamp NULL DEFAULT NULL,
  `verify_ts` timestamp NULL DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `entry_location` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`stock_tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock_tx`
--

LOCK TABLES `stock_tx` WRITE;
/*!40000 ALTER TABLE `stock_tx` DISABLE KEYS */;
/*!40000 ALTER TABLE `stock_tx` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock_tx_type`
--

DROP TABLE IF EXISTS `stock_tx_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stock_tx_type` (
  `stock_tx_type_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `stock_tx_type` varchar(45) NOT NULL,
  `tx_for` varchar(45) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `pre_p1_action_formula` varchar(3000) DEFAULT NULL,
  `pre_p2_action_formula` varchar(3000) DEFAULT NULL,
  `pre_p3_action_formula` varchar(3000) DEFAULT NULL,
  `post_p1_action_formula` varchar(3000) DEFAULT NULL,
  `post_p2_action_formula` varchar(3000) DEFAULT NULL,
  `post_p3_action_formula` varchar(3000) DEFAULT NULL,
  PRIMARY KEY (`stock_tx_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock_tx_type`
--

LOCK TABLES `stock_tx_type` WRITE;
/*!40000 ALTER TABLE `stock_tx_type` DISABLE KEYS */;
INSERT INTO `stock_tx_type` VALUES (1,'Created','Stock','Stock Item is Created',NULL,NULL,NULL,NULL,NULL,NULL),(2,'Stock Verified','Stock','Stock Item is Verified & Stocked','','','','stock_type:total_stock_am:ADD;stock_type:total_stock_no:INCR;stock_type:current_created_stock_am:SUB;stock_type:current_stock_am:ADD;stock_type:current_stock_discount_am:ADD;stock_type:current_created_stock_no:DECR;stock_type:current_stock_no:INCR;stock_type:current_stock_discount_no:INCR;stock_type:first_stock_price_am:THIS;stock_type:last_stock_price_am:THIS;stock_type:first_lot_ts:UPDATE;stock_type:last_lot_ts:UPDATE;brand:total_stock_am:ADD;brand:total_stock_no:INCR;brand:current_created_stock_am:SUB;brand:current_stock_am:ADD;brand:current_stock_discount_am:ADD;brand:current_created_stock_no:DECR;brand:current_stock_no:INCR;brand:current_stock_discount_no:INCR;brand:first_stock_price_am:THIS;brand:last_stock_price_am:THIS;brand:first_lot_ts:UPDATE;brand:last_lot_ts:UPDATE;lot:no_created:DECR;lot:no_stocked:INCR;','p_owner_ac:current_stock_am:ADD;p_owner_ac:current_stock_discount_am:ADD;p_owner_ac:current_stock_no:INCR;p_owner_ac:current_stock_discount_no:INCR;p_owner_ac:total_stock_am:ADD;p_owner_ac:total_stock_no:INCR;p_owner_ac:this_month_stock_am:ADD;p_owner_ac:this_month_stock_no:INCR;','p_hub_ac:total_stock_am:ADD;p_hub_ac:total_stock_no:INCR;p_hub_ac:current_stock_am:ADD;p_hub_ac:current_stock_discount_am:ADD;p_hub_ac:current_stock_no:INCR;p_hub_ac:current_stock_discount_no:INCR;p_hub_ac:current_created_stock_am:SUB;p_hub_ac:current_created_stock_no:DECR;p_hub_ac:mh_total_stock_am:ADD;p_hub_ac:mh_current_stock_am:ADD;p_hub_ac:mh_current_stock_discount_am:ADD;p_hub_ac:mh_total_stock_no:INCR;p_hub_ac:mh_current_stock_no:INCR;p_hub_ac:mh_current_stock_discount_no:INCR;'),(3,'Given','Stock','Stock Item is Given','','mr_visit:given_stock_am:ADD;mr_visit:given_stock_no:INCR;mr_visit:closing_stock_am:ADD;mr_visit:closing_stock_no:INCR;mr_visit:total_stock_am:ADD;mr_visit:total_stock_no:INCR;stock_item:item_status_id:UPDATE;stock_item:owner_ac_no:UPDATE;stock_item:check_ts:CURRENT_TS;stock_item:check_flag:UPDATE;mr_visit:latest_update_ts:CURRENT_TS;','','p_auth_ac:current_stock_am:SUB;p_auth_ac:current_stock_discount_am:SUB;p_auth_ac:current_stock_no:DECR;p_auth_ac:current_stock_discount_no:DECR;p_owner_ac:current_stock_am:ADD;p_owner_ac:current_stock_discount_am:ADD;p_owner_ac:current_stock_no:INCR;p_owner_ac:current_stock_discount_no:INCR;p_owner_ac:total_stock_am:ADD;p_owner_ac:total_stock_no:INCR;p_owner_ac:this_month_stock_am:ADD;p_owner_ac:this_month_stock_no:INCR;','p_hub_ac:mh_total_stock_am:ADD;p_hub_ac:mh_current_stock_am:ADD;p_hub_ac:mh_current_stock_discount_am:ADD;p_hub_ac:mh_total_stock_no:INCR;p_hub_ac:mh_current_stock_no:INCR;p_hub_ac:mh_current_stock_discount_no:INCR;p_hub_ac:a_mh_current_stock_am:SUB;p_hub_ac:a_mh_current_stock_discount_am:SUB;p_hub_ac:a_mh_current_stock_no:DECR;p_hub_ac:a_mh_current_stock_discount_no:DECR;p_hub_ac:lh_total_stock_am:ADD;p_hub_ac:lh_current_stock_am:ADD;p_hub_ac:lh_current_stock_discount_am:ADD;p_hub_ac:lh_total_stock_no:INCR;p_hub_ac:lh_current_stock_no:INCR;p_hub_ac:lh_current_stock_discount_no:INCR;p_hub_ac:a_lh_current_stock_am:SUB;p_hub_ac:a_lh_current_stock_discount_am:SUB;p_hub_ac:a_lh_current_stock_no:DECR;p_hub_ac:a_lh_current_stock_discount_no:DECR;p_hub_ac:slh_total_stock_am:ADD;p_hub_ac:slh_current_stock_am:ADD;p_hub_ac:slh_current_stock_discount_am:ADD;p_hub_ac:slh_total_stock_no:INCR;p_hub_ac:slh_current_stock_no:INCR;p_hub_ac:slh_current_stock_discount_no:INCR;p_hub_ac:a_slh_current_stock_am:SUB;p_hub_ac:a_slh_current_stock_discount_am:SUB;p_hub_ac:a_slh_current_stock_no:DECR;p_hub_ac:a_slh_current_stock_discount_no:DECR;p_hub_ac:se_total_stock_am:ADD;p_hub_ac:se_current_stock_am:ADD;p_hub_ac:se_current_stock_discount_am:ADD;p_hub_ac:se_total_stock_no:INCR;p_hub_ac:se_current_stock_no:INCR;p_hub_ac:se_current_stock_discount_no:INCR;p_hub_ac:a_se_current_stock_am:SUB;p_hub_ac:a_se_current_stock_discount_am:SUB;p_hub_ac:a_se_current_stock_no:DECR;p_hub_ac:a_se_current_stock_\ndiscount_no:DECR;p_hub_ac:mr_total_stock_am:ADD;p_hub_ac:mr_current_stock_am:ADD;p_hub_ac:mr_current_stock_discount_am:ADD;p_hub_ac:mr_total_stock_no:INCR;p_hub_ac:mr_current_stock_no:INCR;p_hub_ac:mr_current_stock_discount_no:INCR;',''),(4,'Returned','Stock','Stock Item is Returned','','mr_visit:return_stock_am:ADD;mr_visit:return_stock_no:INCR;mr_visit:closing_stock_am:SUB;mr_visit:closing_stock_no:DECR;mr_visit:total_stock_returned_am:ADD;mr_visit:total_stock_returned_no:INCR;stock_item:item_status_id:UPDATE;stock_item:owner_ac_no:UPDATE;stock_item:check_ts:CURRENT_TS;stock_item:check_flag:UPDATE;mr_visit:latest_update_ts:CURRENT_TS;','','p_auth_ac:current_stock_am:ADD;p_auth_ac:current_stock_discount_am:ADD;p_auth_ac:current_stock_no:INCR;p_auth_ac:current_stock_discount_no:INCR;p_owner_ac:current_stock_am:SUB;p_owner_ac:current_stock_discount_am:SUB;p_owner_ac:current_stock_no:DECR;p_owner_ac:current_stock_discount_no:DECR;p_owner_ac:total_stock_returned_am:ADD;p_owner_ac:total_stock_returned_no:INCR;p_owner_ac:this_month_stock_returned_am:ADD;p_owner_ac:this_month_stock_returned_no:INCR;stock_type:return_counter:INCR;brand:return_counter:INCR;lot:return_counter:INCR;','p_hub_ac:total_stock_returned_am:ADD;p_hub_ac:total_stock_returned_no:INCR;p_hub_ac:return_counter:INCR;p_hub_ac:mh_total_stock_returned_am:ADD;p_hub_ac:mh_current_stock_am:SUB;p_hub_ac:mh_current_stock_discount_am:SUB;p_hub_ac:mh_current_stock_to_return_am:SUB;p_hub_ac:mh_total_stock_returned_no:INCR;p_hub_ac:mh_current_stock_no:DECR;p_hub_ac:mh_current_stock_discount_no:DECR;p_hub_ac:mh_current_stock_to_return_no:DECR;p_hub_ac:mh_return_counter:INCR;p_hub_ac:a_mh_current_stock_am:ADD;p_hub_ac:a_mh_current_stock_discount_am:ADD;p_hub_ac:a_mh_current_stock_no:INCR;p_hub_ac:a_mh_current_stock_discount_no:INCR;p_hub_ac:lh_total_stock_returned_am:ADD;p_hub_ac:lh_current_stock_am:SUB;p_hub_ac:lh_current_stock_discount_am:SUB;p_hub_ac:lh_current_stock_to_return_am:SUB;p_hub_ac:lh_total_stock_returned_no:INCR;p_hub_ac:lh_current_stock_no:DECR;p_hub_ac:lh_current_stock_discount_no:DECR;p_hub_ac:lh_current_stock_to_return_no:DECR;p_hub_ac:lh_return_counter:INCR;p_hub_ac:a_lh_current_stock_am:ADD;p_hub_ac:a_lh_current_stock_discount_am:ADD;p_hub_ac:a_lh_current_stock_no:INCR;p_hub_ac:a_lh_current_stock_discount_no:INCR;p_hub_ac:slh_total_stock_returned_am:ADD;p_hub_ac:slh_current_stock_am:SUB;p_hub_ac:slh_current_stock_discount_am:SUB;p_hub_ac:slh_current_stock_to_return_am:SUB;p_hub_ac:slh_total_stock_returned_no:INCR;p_hub_ac:slh_current_stock_no:DECR;p_hub_ac:slh_current_stock_discount_no:DECR;p_hub_ac:slh_current_stock_to_return_no:DECR;p_hub_ac:slh_return_counter:INCR;p_hub_ac:a_slh_current_stock_am:ADD;p_hub_ac:a_slh_current_stock_discount_am:ADD;p_hub_ac:a_slh_current_stock_no:INCR;p_hub_ac:a_slh_current_stock_discount_no:INCR;p_hub_ac:se_total_stock_returned_am:ADD;p_hub_ac:se_current_stock_am:SUB;p_hub_ac:se_current_stock_discount_am:SUB;p_hub_ac:se_current_stock_to_return_am:SUB;p_hub_ac:se_total_stock_returned_no:INCR;p_hub_ac:se_current_stock_no:DECR;p_hub_ac:se_current_stock_discount_no:DECR;p_hub_ac:se_current_stock_to_return_no:DECR;p_hub_ac:se_return_counter:INCR;p_hub_ac:a_se_current_stock_am:ADD;p_hub_ac:a_se_current_stock_discount_am:ADD;p_hub_ac:a_se_current_stock_no:INCR;p_hub_ac:a_se_current_stock_discount_no:INCR;p_hub_ac:mr_total_stock_returned_am:ADD;p_hub_ac:mr_current_stock_am:SUB;p_hub_ac:mr_current_stock_discount_am:SUB;p_hub_ac:mr_current_stock_to_return_am:SUB;p_hub_ac:mr_total_stock_returned_no:INCR;p_hub_ac:mr_current_stock_no:DECR;p_hub_ac:mr_current_stock_discount_no:DECR;p_hub_ac:mr_current_stock_to_return_no:DECR;p_hub_ac:mr_return_counter:INCR;',''),(5,'Sold','Stock','Stock Item is Sold','stock_type:avg_mr_item_sold_am:THIS;brand:avg_mr_item_sold_am:THIS;lot:avg_mr_item_sold_am:THIS;','mr_visit:sold_pending_am:ADD;mr_visit:closing_outstanding_am:ADD;mr_visit:sold_stock_am:ADD;mr_visit:sold_stock_discount_am:ADD;mr_visit:mr_sold_stock_am:ADD;mr_visit:closing_stock_am:SUB;mr_visit:sold_stock_no:INCR;mr_visit:sold_stock_disc\nount_no:INCR;mr_visit:closing_stock_no:DECR;mr_visit:total_stock_sold_am:ADD;mr_visit:total_stock_sold_discount_am:ADD;mr_visit:total_stock_sold_no:INCR;mr_visit:total_stock_sold_discount_no:INCR;mr_visit:latest_update_ts:CURRENT_TS;','','p_owner_ac:sold_pending_am:ADD;p_owner_ac:current_stock_am:SUB;p_owner_ac:current_stock_discount_am:SUB;p_owner_ac:current_stock_no:DECR;p_owner_ac:current_stock_discount_no:DECR;p_owner_ac:total_stock_sold_am:ADD;p_owner_ac:total_stock_sold_discount_am:ADD;p_owner_ac:total_stock_mr_sold_am:ADD;p_owner_ac:total_stock_sold_no:INCR;p_owner_ac:total_stock_sold_discount_no:INCR;p_owner_ac:this_month_stock_sold_am:ADD;p_owner_ac:this_month_stock_sold_discount_am:ADD;p_owner_ac:this_month_stock_mr_sold_am:ADD;p_owner_ac:this_month_stock_sold_no:INCR;p_owner_ac:this_month_stock_sold_discount_no:INCR;stock_type:total_sold_am:ADD;stock_type:total_sold_discount_am:ADD;stock_type:total_sold_no:INCR;stock_type:total_sold_discount_no:INCR;stock_type:current_stock_am:SUB;stock_type:current_stock_discount_am:SUB;stock_type:current_stock_no:DECR;stock_type:current_stock_discount_no:DECR;brand:total_sold_am:ADD;brand:total_sold_discount_am:ADD;brand:total_sold_no:INCR;brand:total_sold_discount_no:INCR;brand:current_stock_am:SUB;brand:current_stock_discount_am:SUB;brand:current_stock_no:DECR;brand:current_stock_discount_no:DECR;lot:no_stocked:DECR;lot:no_sold:INCR;','p_hub_ac:total_stock_sold_am:ADD;p_hub_ac:total_stock_mr_sold_am:ADD;p_hub_ac:total_stock_direct_sold_am:ADD;p_hub_ac:total_sold_discount_am:ADD;p_hub_ac:total_stock_sold_no:INCR;p_hub_ac:total_stock_mr_sold_no:INCR;p_hub_ac:total_stock_direct_sold_no:INCR;p_hub_ac:total_sold_discount_no:INCR;p_hub_ac:current_stock_am:SUB;p_hub_ac:current_stock_discount_am:SUB;p_hub_ac:current_stock_no:DECR;p_hub_ac:current_stock_discount_no:DECR;p_hub_ac:mh_sold_pending_am:ADD;p_hub_ac:mh_total_stock_sold_am:ADD;p_hub_ac:mh_total_stock_sold_discount_am:ADD;p_hub_ac:mh_current_stock_am:SUB;p_hub_ac:mh_current_stock_discount_am:SUB;p_hub_ac:mh_total_stock_sold_no:INCR;p_hub_ac:mh_total_stock_sold_discount_no:INCR;p_hub_ac:mh_current_stock_no:DECR;p_hub_ac:mh_current_stock_discount_no:DECR;p_hub_ac:lh_sold_pending_am:ADD;p_hub_ac:lh_total_stock_sold_am:ADD;p_hub_ac:lh_total_stock_sold_discount_am:ADD;p_hub_ac:lh_current_stock_am:SUB;p_hub_ac:lh_current_stock_discount_am:SUB;p_hub_ac:lh_total_stock_sold_no:INCR;p_hub_ac:lh_total_stock_sold_discount_no:INCR;p_hub_ac:lh_current_stock_no:DECR;p_hub_ac:lh_current_stock_discount_no:DECR;p_hub_ac:slh_sold_pending_am:ADD;p_hub_ac:slh_total_stock_sold_am:ADD;p_hub_ac:slh_total_stock_sold_discount_am:ADD;p_hub_ac:slh_current_stock_am:SUB;p_hub_ac:slh_current_stock_discount_am:SUB;p_hub_ac:slh_total_stock_sold_no:INCR;p_hub_ac:slh_total_stock_sold_discount_no:INCR;p_hub_ac:slh_current_stock_no:DECR;p_hub_ac:slh_current_stock_discount_no:DECR;p_hub_ac:se_sold_pending_am:ADD;p_hub_ac:se_total_stock_sold_am:ADD;p_hub_ac:se_total_stock_sold_discount_am:ADD;p_hub_ac:se_current_stock_am:SUB;p_hub_ac:se_current_stock_discount_am:SUB;p_hub_ac:se_total_stock_sold_no:INCR;p_hub_ac:se_total_stock_sold_discount_no:INCR;p_hub_ac:se_current_stock_no:DECR;p_hub_ac:se_current_stock_discount_no:DECR;p_hub_ac:mr_sold_pending_am:ADD;p_hub_ac:mr_total_stock_sold_am:ADD;p_hub_ac:mr_total_stock_sold_discount_am:ADD;p_hub_ac:mr_current_stock_am:SUB;p_hub_ac:mr_current_stock_discount_am:SUB;p_hub_ac:mr_total_stock_sold_no:INCR;p_hub_ac:mr_total_stock_sold_discount_no:INCR;p_hub_ac:mr_current_stock_no:DECR;p_hub_ac:mr_current_stock_discount_no:DECR;',''),(6,'Check','Stock','Stock Item is Check',NULL,NULL,NULL,NULL,NULL,NULL),(7,'Damage Returned','Stock','Stock Item is Returned due to Damage',NULL,NULL,NULL,NULL,NULL,NULL),(8,'Damage Sold','Stock','Stock Item is Sold with Discount due to Damage',NULL,NULL,NULL,NULL,NULL,NULL),(9,'Damaged','Stock','Stock Item is marked Damaged',NULL,NULL,NULL,NULL,NULL,NULL),(10,'Payment','Pay','Pay for Payment',NULL,NULL,NULL,NULL,NULL,NULL),(11,'Sold Paid','Pay','Pay for Sold Paid',NULL,'mr_visit:sold_paid_am:AD\nD;mr_visit:sold_pending_am:SUB;mr_visit:closing_outstanding_am:SUB;mr_visit:total_paid_sold_am:ADD;mr_visit:latest_update_ts:CURRENT_TS;','p_owner_ac:sold_paid_am:ADD;p_owner_ac:sold_pending_am:SUB;','p_auth_ac:total_collected_am:ADD;p_auth_ac:current_collected_am:ADD;','','p_hub_ac:mh_total_collected_am:ADD;p_hub_ac:mh_current_collected_am:ADD;p_hub_ac:mh_sold_paid_am:ADD;p_hub_ac:mh_sold_pending_am:SUB;p_hub_ac:a_mh_total_collected_am:ADD;p_hub_ac:a_mh_current_collected_am:ADD;p_hub_ac:lh_sold_paid_am:ADD;p_hub_ac:lh_sold_pending_am:SUB;p_hub_ac:a_lh_total_collected_am:ADD;p_hub_ac:a_lh_current_collected_am:ADD;p_hub_ac:slh_sold_paid_am:ADD;p_hub_ac:slh_sold_pending_am:SUB;p_hub_ac:a_slh_total_collected_am:ADD;p_hub_ac:a_slh_current_collected_am:ADD;p_hub_ac:se_sold_paid_am:ADD;p_hub_ac:se_sold_pending_am:SUB;p_hub_ac:a_se_total_collected_am:ADD;p_hub_ac:a_se_current_collected_am:ADD;p_hub_ac:mr_sold_paid_am:ADD;p_hub_ac:mr_sold_pending_am:SUB;'),(12,'Collection Paid','Pay','Pay for Collection Paid',NULL,'mr_visit:paid_collected_am:ADD;mr_visit:closing_collected_am:SUB;mr_visit:total_paid_collected_am:ADD;mr_visit:latest_update_ts:CURRENT_TS;','p_owner_ac:total_paid_collected_am:ADD;p_owner_ac:current_collected_am:SUB;','p_auth_ac:total_collected_am:ADD;p_auth_ac:current_collected_am:ADD;','','p_hub_ac:mh_total_paid_collected_am:ADD;p_hub_ac:mh_current_collected_am:SUB;p_hub_ac:a_mh_total_collected_am:ADD;p_hub_ac:a_mh_current_collected_am:ADD;p_hub_ac:lh_total_paid_collected_am:ADD;p_hub_ac:lh_current_collected_am:SUB;p_hub_ac:a_lh_total_collected_am:ADD;p_hub_ac:a_lh_current_collected_am:ADD;p_hub_ac:slh_total_paid_collected_am:ADD;p_hub_ac:slh_current_collected_am:SUB;p_hub_ac:a_slh_total_collected_am:ADD;p_hub_ac:a_slh_current_collected_am:ADD;p_hub_ac:se_total_paid_collected_am:ADD;p_hub_ac:se_current_collected_am:SUB;p_hub_ac:a_se_total_collected_am:ADD;p_hub_ac:a_se_current_collected_am:ADD;'),(13,'Registration Fee','Pay','Pay for Registration Fee',NULL,'mr_visit:paid_registration_am:ADD;mr_visit:closing_registration_am:SUB;mr_visit:total_registration_paid_am:ADD;mr_visit:latest_update_ts:CURRENT_TS;','p_owner_ac:registration_paid_am:ADD;p_owner_ac:registration_pending_am:SUB;','p_auth_ac:total_collected_am:ADD;p_auth_ac:current_collected_am:ADD;','','p_hub_ac:a_mh_total_collected_am:ADD;p_hub_ac:a_mh_current_collected_am:ADD;p_hub_ac:a_lh_total_collected_am:ADD;p_hub_ac:a_lh_current_collected_am:ADD;p_hub_ac:a_slh_total_collected_am:ADD;p_hub_ac:a_slh_current_collected_am:ADD;p_hub_ac:a_se_total_collected_am:ADD;p_hub_ac:a_se_current_collected_am:ADD;p_hub_ac:mr_registration_paid_am:ADD;p_hub_ac:mr_registration_pending_am:SUB;'),(14,'Deposit','Pay','Pay for Deposit',NULL,'mr_visit:paid_deposit_am:ADD;mr_visit:closing_deposit_am:SUB;mr_visit:total_deposit_paid_am:ADD;mr_visit:latest_update_ts:CURRENT_TS;','p_owner_ac:deposit_paid_am:ADD;p_owner_ac:deposit_pending_am:SUB;','p_auth_ac:total_collected_am:ADD;p_auth_ac:current_collected_am:ADD;','','p_hub_ac:a_mh_total_collected_am:ADD;p_hub_ac:a_mh_current_collected_am:ADD;p_hub_ac:a_lh_total_collected_am:ADD;p_hub_ac:a_lh_current_collected_am:ADD;p_hub_ac:a_slh_total_collected_am:ADD;p_hub_ac:a_slh_current_collected_am:ADD;p_hub_ac:a_se_total_collected_am:ADD;p_hub_ac:a_se_current_collected_am:ADD;p_hub_ac:mr_deposit_paid_am:ADD;p_hub_ac:mr_deposit_pending_am:SUB;'),(15,'Return Deposit','Pay','Pay for Return Deposit',NULL,'mr_visit:returned_deposit_am:ADD;mr_visit:closing_deposit_am:ADD;mr_visit:total_deposit_paid_am:SUB;mr_visit:latest_update_ts:CURRENT_TS;','p_owner_ac:deposit_return_am:ADD;','p_auth_ac:total_paid_collected_am:ADD;p_auth_ac:current_collected_am:SUB;','','p_hub_ac:a_mh_total_paid_collected_am:ADD;p_hub_ac:a_mh_current_collected_am:SUB;p_hub_ac:a_lh_total_paid_collected_am:ADD;p_hub_ac:a_lh_current_collected_am:SUB;p_hub_ac:a_slh_total_paid_collected_am:ADD;p_hub_ac:a_slh_current_collected_am:SUB;p_hub_ac:a_se_total_paid_collected_am:ADD;p_hub_ac:a_se_current_collected_am:SUB;p_hub_ac:mr_deposit_return_am:ADD;'),(16,'Cash Deposit','Pay','Pay for C\nash Deposit',NULL,NULL,NULL,NULL,NULL,NULL),(17,'Cash Advance','Pay','Pay for Cash Advance',NULL,'mr_visit:received_collected_am:ADD;mr_visit:closing_collected_am:ADD;mr_visit:latest_update_ts:CURRENT_TS;','p_owner_ac:total_collected_am:ADD;p_owner_ac:current_collected_am:ADD;','p_auth_ac:total_paid_collected_am:ADD;p_auth_ac:current_collected_am:SUB;','','p_hub_ac:mh_total_collected_am:ADD;p_hub_ac:mh_current_collected_am:ADD;p_hub_ac:a_mh_total_paid_collected_am:ADD;p_hub_ac:a_mh_current_collected_am:SUB;p_hub_ac:lh_total_collected_am:ADD;p_hub_ac:lh_current_collected_am:ADD;p_hub_ac:a_lh_total_paid_collected_am:ADD;p_hub_ac:a_lh_current_collected_am:SUB;p_hub_ac:slh_total_collected_am:ADD;p_hub_ac:slh_current_collected_am:ADD;p_hub_ac:a_slh_total_paid_collected_am:ADD;p_hub_ac:a_slh_current_collected_am:SUB;p_hub_ac:se_total_collected_am:ADD;p_hub_ac:se_current_collected_am:ADD;p_hub_ac:a_se_total_paid_collected_am:ADD;p_hub_ac:a_se_current_collected_am:SUB;'),(18,'Interest Penalty','Pay','Pay for Interest Penalty',NULL,'mr_visit:paid_interest_penalty_am:ADD;mr_visit:closing_interest_penalty_am:SUB;mr_visit:total_paid_interest_penalty_am:ADD;mr_visit:latest_update_ts:CURRENT_TS;','p_owner_ac:paid_interest_penalty_am:ADD;p_owner_ac:pending_interest_penalty_am:SUB;','p_auth_ac:total_collected_am:ADD;p_auth_ac:current_collected_am:ADD;','','p_hub_ac:a_mh_total_collected_am:ADD;p_hub_ac:a_mh_current_collected_am:ADD;p_hub_ac:a_lh_total_collected_am:ADD;p_hub_ac:a_lh_current_collected_am:ADD;p_hub_ac:a_slh_total_collected_am:ADD;p_hub_ac:a_slh_current_collected_am:ADD;p_hub_ac:a_se_total_collected_am:ADD;p_hub_ac:a_se_current_collected_am:ADD;p_hub_ac:mr_paid_interest_penalty_am:ADD;p_hub_ac:mr_pending_interest_penalty_am:SUB;'),(19,'Late Fee','Pay','Pay for Late Fee',NULL,NULL,NULL,NULL,NULL,NULL),(20,'Other Fee','Pay','Pay for Other Fee',NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `stock_tx_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock_type`
--

DROP TABLE IF EXISTS `stock_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stock_type` (
  `stock_type_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `stock_status_id` int(10) unsigned NOT NULL,
  `p_category_id` int(10) unsigned NOT NULL,
  `product_pricing_id` int(10) unsigned NOT NULL,
  `item_condition_id` int(10) unsigned NOT NULL,
  `brand_id` bigint(20) unsigned NOT NULL,
  `name` varchar(100) NOT NULL,
  `name_display` varchar(15) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `properties` varchar(200) DEFAULT NULL,
  `pics` varchar(200) DEFAULT NULL,
  `link` varchar(200) DEFAULT NULL,
  `no_lots` int(10) DEFAULT NULL,
  `no_per_set` int(10) DEFAULT NULL,
  `no_min_sets` int(10) DEFAULT NULL,
  `total_purchase_am` decimal(16,2) DEFAULT NULL,
  `total_stock_am` decimal(16,2) DEFAULT NULL,
  `total_sold_am` decimal(16,2) DEFAULT NULL,
  `total_sold_discount_am` decimal(16,2) DEFAULT NULL,
  `total_damage_am` decimal(16,2) DEFAULT NULL,
  `total_purchase_no` int(10) DEFAULT NULL,
  `total_stock_no` int(10) DEFAULT NULL,
  `total_sold_no` int(10) DEFAULT NULL,
  `total_sold_discount_no` int(10) DEFAULT NULL,
  `total_damage_no` int(10) DEFAULT NULL,
  `current_ordered_stock_am` decimal(16,2) DEFAULT NULL,
  `current_delivered_stock_am` decimal(16,2) DEFAULT NULL,
  `current_to_stock_am` decimal(16,2) DEFAULT NULL,
  `current_created_stock_am` decimal(16,2) DEFAULT NULL,
  `current_stock_am` decimal(16,2) DEFAULT NULL,
  `current_stock_discount_am` decimal(16,2) DEFAULT NULL,
  `current_ordered_stock_no` int(10) DEFAULT NULL,
  `current_delivered_stock_no` int(10) DEFAULT NULL,
  `current_to_stock_no` int(10) DEFAULT NULL,
  `current_created_stock_no` int(10) DEFAULT NULL,
  `current_stock_no` int(10) DEFAULT NULL,
  `current_stock_discount_no` int(10) DEFAULT NULL,
  `first_stock_price_am` decimal(16,2) DEFAULT NULL,
  `last_stock_price_am` decimal(16,2) DEFAULT NULL,
  `avg_mr_item_sold_am` decimal(16,2) DEFAULT NULL,
  `first_lot_ts` timestamp NULL DEFAULT NULL,
  `last_lot_ts` timestamp NULL DEFAULT NULL,
  `return_counter` int(10) DEFAULT NULL,
  `performance_index` float DEFAULT NULL,
  `return_index` float DEFAULT NULL,
  `sales_index` float DEFAULT NULL,
  `sales_20_per_days` float DEFAULT NULL,
  `sales_40_per_days` float DEFAULT NULL,
  `sales_60_per_days` float DEFAULT NULL,
  `sales_80_per_days` float DEFAULT NULL,
  `sales_100_per_days` float DEFAULT NULL,
  PRIMARY KEY (`stock_type_id`),
  KEY `stock_type_fk4_idx` (`brand_id`),
  CONSTRAINT `stock_type_fk4` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`brand_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock_type`
--

LOCK TABLES `stock_type` WRITE;
/*!40000 ALTER TABLE `stock_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `stock_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock_type_status`
--

DROP TABLE IF EXISTS `stock_type_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stock_type_status` (
  `stock_type_status_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `stock_type_status` varchar(45) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `next_status` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`stock_type_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock_type_status`
--

LOCK TABLES `stock_type_status` WRITE;
/*!40000 ALTER TABLE `stock_type_status` DISABLE KEYS */;
INSERT INTO `stock_type_status` VALUES (1,'Created','Stock Type is Created','Stocked;Discarded'),(2,'Stocked','Stock Type is Stocked','Stocked Closed;Sold Out;Sold Out Closed;Discarded'),(3,'Stocked Closed','Stock Type is Stocked & Closed as no longer available','Sold Out Closed'),(4,'Sold Out','Stock Type is Completely Sold Out','Sold Out Closed'),(5,'Sold Out Closed','Stock Type is Completely Sold Out & Closed as no longer available',''),(6,'Discarded','Stock Type is Discarded','');
/*!40000 ALTER TABLE `stock_type_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag_status`
--

DROP TABLE IF EXISTS `tag_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag_status` (
  `tag_status_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tag_status` varchar(45) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `next_status` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`tag_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag_status`
--

LOCK TABLES `tag_status` WRITE;
/*!40000 ALTER TABLE `tag_status` DISABLE KEYS */;
INSERT INTO `tag_status` VALUES (1,'Created','Tag is Created','Printed;Canceled'),(2,'Printed','Tag is Printed',''),(3,'Canceled','Tag is Canceled','');
/*!40000 ALTER TABLE `tag_status` ENABLE KEYS */;
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
  `penalty_paid_am` decimal(16,2) DEFAULT NULL,
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
INSERT INTO `tx_type` VALUES (1,'Saving Installment','MEMBER SAVING','RECEIPT','LIABILITIES','MEMBER','Installment for Saving','m_saving_ac:saved_am:ADD;m_saving_ac:cumulative_saved_am:ADD;m_saving_ac:exp_no_of_inst:DECR;m_saving_ac:no_of_inst_paid:INCR;m_saving_ac:no_of_insall_late:INCR;m_ac:saved_am:ADD;g_ac:c_m_saved_am:ADD;g_ac:a_m_saved_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:ADD;','m_saving_ac:saved_am:SUB;m_saving_ac:cumulative_saved_am:SUB;m_saving_ac:exp_no_of_inst:INCR;m_saving_ac:no_of_inst_paid:DECR;m_saving_ac:no_of_insall_late:DECR;m_ac:saved_am:SUB;g_ac:c_m_saved_am:SUB;g_ac:a_m_saved_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','m_saving_ac:saved_am:SUB;m_saving_ac:cumulative_saved_am:SUB;m_saving_ac:exp_no_of_inst:INCR;m_saving_ac:no_of_inst_paid:DECR;m_saving_ac:no_of_insall_late:DECR;m_ac:saved_am:SUB;g_ac:c_m_saved_am:SUB;g_ac:a_m_saved_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;'),(2,'Provisional Interest Earned','MEMBER SAVING','INTERNAL','LIABILITIES','MEMBER','Provisional Interest Earned on the Saving','m_saving_ac:current_fy_int_en_am:ADD;m_saving_ac:total_int_en_am:ADD;m_saving_ac:prev_month_int_am:THIS;m_ac:prov_int_en_am:ADD;m_ac:prev_month_int_en_am:THIS;g_ac:c_m_prov_int_en_am:ADD;g_ac:a_m_prov_int_en_am:ADD;g_ac_by_txtype:amount:ADD;','m_saving_ac:current_fy_int_en_am:SUB;m_saving_ac:total_int_en_am:SUB;m_ac:prov_int_en_am:SUB;g_ac:c_m_prov_int_en_am:SUB;g_ac:a_m_prov_int_en_am:SUB;g_ac_by_txtype:amount:SUB;','','m_saving_ac:current_fy_int_en_am:SUB;m_saving_ac:total_int_en_am:SUB;m_ac:prov_int_en_am:SUB;g_ac:c_m_prov_int_en_am:SUB;g_ac:a_m_prov_int_en_am:SUB;g_ac_by_txtype:amount:SUB;'),(3,'Compute Cumulative Saving','MEMBER SAVING','INTERNAL','LIABILITIES','MEMBER','Compute Cumulative Saving for the given Period','m_saving_ac:cumulative_saved_am:ADD;m_saving_ac:current_fy_int_en_am:ZERO;','m_saving_ac:cumulative_saved_am:SUB;m_saving_ac:current_fy_int_en_am:THIS;','','m_saving_ac:cumulative_saved_am:SUB;m_saving_ac:current_fy_int_en_am:THIS;'),(4,'Saving Returned','MEMBER SAVING','VOUCHER','LIABILITIES','MEMBER','Saving Returned, at Account Closure or regular return','m_saving_ac:returned_saved_am:ADD;m_saving_ac:cumulative_saved_am:SUB;m_ac:returned_saved_am:ADD;g_ac:c_m_returned_saved_am:ADD;g_ac:a_m_returned_saved_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','m_saving_ac:returned_saved_am:SUB;m_saving_ac:cumulative_saved_am:ADD;m_ac:returned_saved_am:SUB;g_ac:c_m_returned_saved_am:SUB;g_ac:a_m_returned_saved_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','m_saving_ac:returned_saved_am:SUB;m_saving_ac:cumulative_saved_am:ADD;m_ac:returned_saved_am:SUB;g_ac:c_m_returned_saved_am:SUB;g_ac:a_m_returned_saved_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(5,'Saving Interest Returned','MEMBER SAVING','VOUCHER','LIABILITIES','MEMBER','Saving Interest Returned, at Account Closure or regular return','m_saving_ac:returned_int_en_am:ADD;m_saving_ac:current_fy_int_en_am:SUB_ZERO;m_ac:returned_int_en_am:ADD;g_ac:c_m_returned_int_en_am:ADD;g_ac:a_m_returned_int_en_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','m_saving_ac:returned_int_en_am:SUB;m_ac:returned_int_en_am:SUB;g_ac:c_m_returned_int_en_am:SUB;g_ac:a_m_returned_int_en_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','m_saving_ac:returned_int_en_am:SUB;m_ac:returned_int_en_am:SUB;g_ac:c_m_returned_int_en_am:SUB;g_ac:a_m_returned_int_en_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(6,'Loan Disbursement','MEMBER LOAN','VOUCHER','ASSETS','ME\nMBER','Loan Disbursement for the Member','m_loan_ac:principle_am:THIS;m_loan_ac:pending_principle_am:THIS;m_ac:loan_am:ADD;g_ac:c_m_loan_am:ADD;g_ac:a_m_loan_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','m_loan_ac:principle_am:THIS;m_loan_ac:pending_principle_am:SUB;m_ac:loan_am:SUB;g_ac:c_m_loan_am:SUB;g_ac:a_m_loan_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','m_loan_ac:principle_am:THIS;m_loan_ac:pending_principle_am:SUB;m_ac:loan_am:SUB;g_ac:c_m_loan_am:SUB;g_ac:a_m_loan_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(7,'Loan Installment','MEMBER LOAN','RECEIPT','ASSETS','MEMBER','Installment for Loan Payment','m_loan_ac:pending_principle_am:SUB;m_loan_ac:no_of_inst_paid:INCR;m_loan_ac:no_of_insall_late:INCR;m_ac:rec_loan_am:ADD;g_ac:c_m_rec_loan_am:ADD;g_ac:a_m_rec_loan_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:ADD;','m_loan_ac:pending_principle_am:ADD;m_loan_ac:no_of_inst_paid:DECR;m_loan_ac:no_of_insall_late:DECR;m_ac:rec_loan_am:SUB;g_ac:c_m_rec_loan_am:SUB;g_ac:a_m_rec_loan_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','m_loan_ac:pending_principle_am:ADD;m_loan_ac:no_of_inst_paid:DECR;m_loan_ac:no_of_insall_late:DECR;m_ac:rec_loan_am:SUB;g_ac:c_m_rec_loan_am:SUB;g_ac:a_m_rec_loan_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;'),(8,'Loan Interest Installment','MEMBER LOAN','RECEIPT','PROFIT','MEMBER','Loan Interest Installment','m_loan_ac:rec_interest_am:ADD;m_loan_ac:proj_interest_am:SUB_ZERO;m_ac:rec_int_on_loan_am:ADD;m_ac:proj_int_on_loan_am:SUB_ZERO;g_ac:c_m_rec_int_on_loan_am:ADD;g_ac:c_m_proj_int_on_loan_am:SUB_ZERO;g_ac:a_m_rec_int_on_loan_am:ADD;g_ac:a_m_proj_int_on_loan_am:SUB_ZERO;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:ADD;','m_loan_ac:rec_interest_am:SUB;m_loan_ac:proj_interest_am:ADD;m_ac:rec_int_on_loan_am:SUB;m_ac:proj_int_on_loan_am:ADD;g_ac:c_m_rec_int_on_loan_am:SUB;g_ac:c_m_proj_int_on_loan_am:ADD;g_ac:a_m_rec_int_on_loan_am:SUB;g_ac:a_m_proj_int_on_loan_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','m_loan_ac:rec_interest_am:SUB;m_loan_ac:proj_interest_am:ADD;m_ac:rec_int_on_loan_am:SUB;m_ac:proj_int_on_loan_am:ADD;g_ac:c_m_rec_int_on_loan_am:SUB;g_ac:c_m_proj_int_on_loan_am:ADD;g_ac:a_m_rec_int_on_loan_am:SUB;g_ac:a_m_proj_int_on_loan_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;'),(9,'Loan Prepayment','MEMBER LOAN','RECEIPT','ASSETS','MEMBER','Prepayment for the Loan in Advance of Schedule','m_loan_ac:pending_principle_am:SUB_ZERO;m_ac:rec_loan_am:ADD;g_ac:c_m_rec_loan_am:ADD;g_ac:a_m_rec_loan_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:ADD;','m_loan_ac:pending_principle_am:ADD;m_ac:rec_loan_am:SUB;g_ac:c_m_rec_loan_am:SUB;g_ac:a_m_rec_loan_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','m_loan_ac:pending_principle_am:ADD;m_ac:rec_loan_am:SUB;g_ac:c_m_rec_loan_am:SUB;g_ac:a_m_rec_loan_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;'),(10,'Pre Interest Installment','MEMBER LOAN','RECEIPT','PROFIT','MEMBER','Pre Interest charged during Loan Disbursement','m_loan_ac:pre_emi_interest_am:THIS;m_loan_ac:rec_interest_am:ADD;m_ac:rec_int_on_loan_am:ADD;g_ac:c_m_rec_int_on_loan_am:ADD;g_ac:a_m_rec_int_on_loan_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:ADD;','m_loan_ac:rec_interest_am:SUB;m_ac:rec_int_on_loan_am:SUB;g_ac:c_m_rec_int_on_loan_am:SUB;g_ac:a_m_rec_int_on_loan_am\n:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','m_loan_ac:rec_interest_am:SUB;m_ac:rec_int_on_loan_am:SUB;g_ac:c_m_rec_int_on_loan_am:SUB;g_ac:a_m_rec_int_on_loan_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;'),(11,'Bad Debt Closure','MEMBER LOAN','INTERNAL','EXPENSE','MEMBER','Closure of Bad Debt','m_ac:bad_dept_closed_am:ADD;g_ac:c_m_bad_dept_closed_am:ADD;g_ac:a_m_bad_dept_closed_am:ADD;g_ac_by_txtype:amount:ADD;','m_ac:bad_dept_closed_am:SUB;g_ac:c_m_bad_dept_closed_am:SUB;g_ac:a_m_bad_dept_closed_am:SUB;g_ac_by_txtype:amount:SUB;','','m_ac:bad_dept_closed_am:SUB;g_ac:c_m_bad_dept_closed_am:SUB;g_ac:a_m_bad_dept_closed_am:SUB;g_ac_by_txtype:amount:SUB;'),(12,'Group Project Dev Fund','PROJECT LOAN','VOUCHER','ASSETS','GROUP','Group Project Dev Fund','g_invt_ac:invt_am:THIS;g_ac:p_loan_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;b_group_bank_account:balance_update:ADD;','g_invt_ac:invt_am:THIS;g_ac:p_loan_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;b_group_bank_account:balance_update:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;b_group_bank_account:balance_approve:ADD;','g_invt_ac:invt_am:THIS;g_ac:p_loan_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;b_group_bank_account:balance_update:SUB;'),(13,'Group Project Recovery','PROJECT LOAN','RECEIPT','ASSETS','GROUP','Fund Recovery from Group Project','g_invt_ac:rec_invt_am:ADD;g_ac:p_rec_loan_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:ADD;b_group_bank_account:balance_update:SUB;','g_invt_ac:rec_invt_am:SUB;g_ac:p_rec_loan_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;b_group_bank_account:balance_update:ADD;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;b_group_bank_account:balance_approve:SUB;','g_invt_ac:rec_invt_am:SUB;g_ac:p_rec_loan_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;b_group_bank_account:balance_update:ADD;'),(14,'Group Project Earning','PROJECT LOAN','RECEIPT','ASSETS','GROUP','Fund Earning from Group Project','g_invt_ac:rec_interest_am:ADD;g_invt_ac:proj_interest_am:SUB_ZERO;g_ac:p_rec_int_on_loan_am:ADD;g_ac:p_proj_int_on_loan_am:SUB_ZERO;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:ADD;b_group_bank_account:interest_am:SUB;','g_invt_ac:rec_interest_am:SUB;g_invt_ac:proj_interest_am:ADD;g_ac:p_rec_int_on_loan_am:SUB;g_ac:p_proj_int_on_loan_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;b_group_bank_account:interest_am:ADD;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','g_invt_ac:rec_interest_am:SUB;g_invt_ac:proj_interest_am:ADD;g_ac:p_rec_int_on_loan_am:SUB;g_ac:p_proj_int_on_loan_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;b_group_bank_account:interest_am:ADD;'),(15,'Bank Loan Disbursement','BANK LOAN','RECEIPT','LIABILITIES','BANK','Receipt of Loan amount from Bank','g_loan_ac:loan_am:THIS;g_loan_ac:pending_principle_am:THIS;g_ac:borrowed_loan_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:ADD;b_group_bank_account:balance_update:SUB;','g_loan_ac:loan_am:THIS;g_loan_ac:pending_principle_am:SUB;g_ac:borrowed_loan_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;b_group_bank_account:balance_update:ADD;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;b_group_bank_account:balance_approve:SUB;','g_loan_ac:loan_am:THIS;g_loan_ac:pending_principle_am:SUB;g_ac:borrowed_loan_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;b_group_bank_account:balance_update:ADD;'),(16,'Bank Loan Installment','BANK LOAN','VOUCHER','LIABILITIES','BA\nNK','Installment for the Loan taken from Bank','g_loan_ac:pending_principle_am:SUB;g_loan_ac:no_of_inst_paid:INCR;g_loan_ac:no_of_insall_late:INCR;g_ac:paid_borrowed_loan_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;b_group_bank_account:balance_update:ADD;','g_loan_ac:pending_principle_am:ADD;g_loan_ac:no_of_inst_paid:DECR;g_loan_ac:no_of_insall_late:DECR;g_ac:paid_borrowed_loan_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;b_group_bank_account:balance_update:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;b_group_bank_account:balance_approve:ADD;','g_loan_ac:pending_principle_am:ADD;g_loan_ac:no_of_inst_paid:DECR;g_loan_ac:no_of_insall_late:DECR;g_ac:paid_borrowed_loan_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;b_group_bank_account:balance_update:SUB;'),(17,'Bank Loan Interest Installment','BANK LOAN','VOUCHER','LIABILITIES','BANK','Interest Installment for the Loan taken from Bank','g_loan_ac:paid_interest_am:ADD;g_loan_ac:proj_interest_am:SUB_ZERO;g_ac:paid_int_on_borrowed_loan_am:ADD;g_ac:proj_int_on_borrowed_loan_am:SUB_ZERO;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;b_group_bank_account:interest_am:ADD;','g_loan_ac:paid_interest_am:SUB;g_loan_ac:proj_interest_am:ADD;g_ac:paid_int_on_borrowed_loan_am:SUB;g_ac:proj_int_on_borrowed_loan_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;b_group_bank_account:interest_am:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','g_loan_ac:paid_interest_am:SUB;g_loan_ac:proj_interest_am:ADD;g_ac:paid_int_on_borrowed_loan_am:SUB;g_ac:proj_int_on_borrowed_loan_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;b_group_bank_account:interest_am:SUB;'),(18,'Bank Loan Prepayment','BANK LOAN','VOUCHER','LIABILITIES','BANK','Prepayment of Loan taken fron Bank','m_loan_ac:pending_principle_am:SUB;g_ac:paid_borrowed_loan_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;b_group_bank_account:balance_update:ADD;','m_loan_ac:pending_principle_am:ADD;g_ac:paid_borrowed_loan_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;b_group_bank_account:balance_update:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;b_group_bank_account:balance_approve:ADD;','m_loan_ac:pending_principle_am:ADD;g_ac:paid_borrowed_loan_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;b_group_bank_account:balance_update:SUB;'),(19,'Bank Pre Interest Installment','BANK LOAN','VOUCHER','LIABILITIES','BANK','Prepayment of Loan taken fron Bank','g_loan_ac:pre_emi_interest_am:ADD;g_loan_ac:paid_interest_am:ADD;g_ac:paid_int_on_borrowed_loan_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;b_group_bank_account:interest_am:ADD;','g_loan_ac:pre_emi_interest_am:SUB;g_loan_ac:paid_interest_am:SUB;g_ac:paid_int_on_borrowed_loan_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;b_group_bank_account:interest_am:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','g_loan_ac:pre_emi_interest_am:SUB;g_loan_ac:paid_interest_am:SUB;g_ac:paid_int_on_borrowed_loan_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;b_group_bank_account:interest_am:SUB;'),(20,'Bank Withdrawal','TRANSFER','TRANSFER','TRANSFER','MEMBER','Withdrawal of Cash from Bank','g_ac:subj_clearing_bank_balance_am:SUB;g_ac:subj_clearing_cash_in_hand_am:ADD;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','g_ac:subj_clearing_bank_balance_am:ADD;g_ac:subj_clearing_cash_in_hand_am:SUB;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:subj_clearing_bank_balance_am:ADD;g_ac:subj_clearing_cash_in_hand_am:SUB;g_ac:clear_bank_balance_am:SUB;g_ac:clear_cash_in_hand_am:ADD;g_bank_acco\nunt:balance_approve:SUB;','g_ac:subj_clearing_bank_balance_am:ADD;g_ac:subj_clearing_cash_in_hand_am:SUB;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(21,'Bank Transfer','TRANSFER','TRANSFER','TRANSFER','MEMBER','Cash Deposit to Bank Account','g_ac:subj_clearing_cash_in_hand_am:SUB;g_ac:subj_clearing_bank_balance_am:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:ADD;','g_ac:subj_clearing_cash_in_hand_am:ADD;g_ac:subj_clearing_bank_balance_am:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;','g_ac:subj_clearing_cash_in_hand_am:ADD;g_ac:subj_clearing_bank_balance_am:SUB;g_ac:clear_cash_in_hand_am:SUB;g_ac:clear_bank_balance_am:ADD;g_bank_account:balance_approve:ADD;','g_ac:subj_clearing_cash_in_hand_am:ADD;g_ac:subj_clearing_bank_balance_am:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;'),(22,'Divided Share Paid','PROFIT SHARE','VOUCHER','LOSS','MEMBER','Divided Shared paid to Associate Member','m_ac:divided_profit_paid_am:ADD;m_ac:divided_profit_declared_am:SUB;g_ac:a_m_divided_paid_am:ADD;g_ac:a_m_divided_declared_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','m_ac:divided_profit_paid_am:SUB;m_ac:divided_profit_declared_am:ADD;g_ac:a_m_divided_paid_am:SUB;g_ac:a_m_divided_declared_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','m_ac:divided_profit_paid_am:SUB;m_ac:divided_profit_declared_am:ADD;g_ac:a_m_divided_paid_am:SUB;g_ac:a_m_divided_declared_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(23,'Profit Share Paid','PROFIT SHARE','VOUCHER','LOSS','MEMBER','Profit Share paid to Core Member','m_ac:divided_profit_paid_am:ADD;m_ac:divided_profit_declared_am:SUB;g_ac:c_m_profit_share_paid_am:ADD;g_ac:c_m_profit_share_declared_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','m_ac:divided_profit_paid_am:SUB;m_ac:divided_profit_declared_am:ADD;g_ac:c_m_profit_share_paid_am:SUB;g_ac:c_m_profit_share_declared_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','m_ac:divided_profit_paid_am:SUB;m_ac:divided_profit_declared_am:ADD;g_ac:c_m_profit_share_paid_am:SUB;g_ac:c_m_profit_share_declared_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(24,'Divided Share Declare','PROFIT SHARE','INTERNAL','LOSS','MEMBER','Divided Shared Declare for Associate Member','m_ac:divided_profit_declared_am:ADD;g_ac:a_m_divided_declared_am:ADD;g_ac_by_txtype:amount:ADD;','m_ac:divided_profit_declared_am:SUB;g_ac:a_m_divided_declared_am:SUB;g_ac_by_txtype:amount:SUB;','','m_ac:divided_profit_declared_am:SUB;g_ac:a_m_divided_declared_am:SUB;g_ac_by_txtype:amount:SUB;'),(25,'Profit Share Declare','PROFIT SHARE','INTERNAL','LOSS','MEMBER','Profit Share Declare for Core Member','m_ac:divided_profit_declared_am:ADD;g_ac:c_m_profit_share_paid_am:ADD;g_ac_by_txtype:amount:ADD;','m_ac:divided_profit_declared_am:SUB;g_ac:c_m_profit_share_paid_am:SUB;g_ac_by_txtype:amount:SUB;','','m_ac:divided_profit_declared_am:SUB;g_ac:c_m_profit_share_paid_am:SUB;g_ac_by_txtype:amount:SUB;'),(26,'Bank Interest Earned','INVESTMENT','RECEIPT','PROFIT','BANK','Interest Earned on Bank Investment','g_ac:int_en_on_saving_ac_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_bank_account:interest_am:ADD;g_ac_by_txtype:amount:ADD;','g_ac:int_en_on_saving_ac_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_bank_account:interest_am:SUB;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','g_ac:int_en_on_saving_ac_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_bank_account:interest_am:SUB;g_ac_by_txtype:amount:SUB;'),(27,'Fix Deposit Investment','INVESTMENT','VOUCHER','ASSETS','BANK','Fix Deposit Investment in Bank','g_invt_ac:invt_am:THIS;\ng_ac:fix_deposit_inv_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;b_group_bank_account:balance_update:ADD;','g_invt_ac:invt_am:THIS;g_ac:fix_deposit_inv_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;b_group_bank_account:balance_update:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;b_group_bank_account:balance_approve:ADD;','g_invt_ac:invt_am:THIS;g_ac:fix_deposit_inv_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;b_group_bank_account:balance_update:SUB;'),(28,'Fix Deposit Recovery','INVESTMENT','RECEIPT','ASSETS','BANK','Recovery of Fix Deposit Investment in Bank','g_invt_ac:rec_invt_am:ADD;g_ac:rec_fix_deposit_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;b_group_bank_account:balance_update:SUB;','g_invt_ac:rec_invt_am:SUB;g_ac:rec_fix_deposit_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;b_group_bank_account:balance_update:ADD;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;b_group_bank_account:balance_approve:SUB;','g_invt_ac:rec_invt_am:SUB;g_ac:rec_fix_deposit_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;b_group_bank_account:balance_update:ADD;'),(29,'Fix Deposit Interest','INVESTMENT','RECEIPT','ASSETS','BANK','Recovered Interest on Fix Deposit Investment in Bank','g_invt_ac:rec_interest_am:ADD;g_invt_ac:proj_interest_am:SUB_ZERO;g_ac:rec_int_on_fix_deposit_am:ADD;g_ac:proj_int_on_fix_deposit_am:SUB_ZERO;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;b_group_bank_account:interest_am:SUB;','g_invt_ac:rec_interest_am:SUB;g_invt_ac:proj_interest_am:ADD;g_ac:rec_int_on_fix_deposit_am:SUB;g_ac:proj_int_on_fix_deposit_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;b_group_bank_account:interest_am:ADD;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','g_invt_ac:rec_interest_am:SUB;g_invt_ac:proj_interest_am:ADD;g_ac:rec_int_on_fix_deposit_am:SUB;g_ac:proj_int_on_fix_deposit_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;b_group_bank_account:interest_am:ADD;'),(30,'Other Investment','INVESTMENT','VOUCHER','ASSETS','BANK','Other Investments','g_invt_ac:invt_am:THIS;g_ac:other_inv_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;b_group_bank_account:balance_update:ADD;','g_invt_ac:invt_am:THIS;g_ac:other_inv_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;b_group_bank_account:balance_update:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;b_group_bank_account:balance_approve:ADD;','g_invt_ac:invt_am:THIS;g_ac:other_inv_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;b_group_bank_account:balance_update:SUB;'),(31,'Other Investment Recovery','INVESTMENT','RECEIPT','ASSETS','BANK','Recovery of Other Investments','g_invt_ac:rec_invt_am:ADD;g_ac:rec_other_inv_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;b_group_bank_account:balance_update:SUB;','g_invt_ac:rec_invt_am:SUB;g_ac:rec_other_inv_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;b_group_bank_account:balance_update:ADD;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;b_group_bank_account:balance_approve:SUB;','g_invt_ac:rec_invt_am:SUB;g_ac:rec_other_inv_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;b_group_bank_account:balance_update:ADD;'),(32,'Other Investment Earning','INVESTMENT','RECEIPT','ASSETS','BANK','Recovered Earnings on Other Investments','g_invt_ac:rec_interest_am:ADD;g_invt_ac:proj_interest_am:SUB_ZERO;g_ac:rec_int_on_other_inv_am:ADD;g_ac:proj_int_on_other_inv_am:SUB_ZERO;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_\nac_by_txtype:amount:SUB;b_group_bank_account:interest_am:SUB;','g_invt_ac:rec_interest_am:SUB;g_invt_ac:proj_interest_am:ADD;g_ac:rec_int_on_other_inv_am:SUB;g_ac:proj_int_on_other_inv_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;b_group_bank_account:interest_am:ADD;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','g_invt_ac:rec_interest_am:SUB;g_invt_ac:proj_interest_am:ADD;g_ac:rec_int_on_other_inv_am:SUB;g_ac:proj_int_on_other_inv_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;b_group_bank_account:interest_am:ADD;'),(33,'Registration Fee','FEE PENALTY','RECEIPT','FEE','MEMBER','Fee for Registration at Member','m_ac:rec_penalty_am:ADD;g_ac:rec_penalty_am:ADD;g_ac:pen_shg_one_mem_reg_fee:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:ADD;','m_ac:rec_penalty_am:SUB;g_ac:rec_penalty_am:SUB;g_ac:pen_shg_one_mem_reg_fee:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','m_ac:rec_penalty_am:SUB;g_ac:rec_penalty_am:SUB;g_ac:pen_shg_one_mem_reg_fee:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;'),(34,'Late Fee','FEE PENALTY','RECEIPT','FEE','MEMBER','Fee for Late Payment','m_ac:rec_penalty_am:ADD;g_ac:rec_penalty_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:ADD;','m_ac:rec_penalty_am:SUB;g_ac:rec_penalty_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','m_ac:rec_penalty_am:SUB;g_ac:rec_penalty_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;'),(35,'Loan Processing Fee','FEE PENALTY','RECEIPT','FEE','MEMBER','Fee for Loan Processing','m_ac:rec_penalty_am:ADD;g_ac:rec_penalty_am:ADD;g_ac:pen_shg_one_service_charges:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:ADD;','m_ac:rec_penalty_am:SUB;g_ac:rec_penalty_am:SUB;g_ac:pen_shg_one_service_charges:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','m_ac:rec_penalty_am:SUB;g_ac:rec_penalty_am:SUB;g_ac:pen_shg_one_service_charges:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;'),(36,'Loan Prepayment Fee','FEE PENALTY','RECEIPT','FEE','MEMBER','Fee for Loan Prepayment','m_ac:rec_penalty_am:ADD;g_ac:rec_penalty_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:ADD;','m_ac:rec_penalty_am:SUB;g_ac:rec_penalty_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','m_ac:rec_penalty_am:SUB;g_ac:rec_penalty_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;'),(37,'Cheque Bouncing Penalty','FEE PENALTY','RECEIPT','FEE','MEMBER','Panalty for Cheque Bouncing','m_ac:rec_penalty_am:ADD;g_ac:rec_penalty_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:ADD;','m_ac:rec_penalty_am:SUB;g_ac:rec_penalty_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','m_ac:rec_penalty_am:SUB;g_ac:rec_penalty_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;'),(38,'Auto Debit Failure Penalty','FEE PENALTY','RECEIPT','FEE','MEMBER','Panalty for Auto Debit Failure','m_ac:rec_penalty_am:ADD;g_ac:rec_penalty_am:ADD;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:ADD;','m_ac:rec_penalty_am:SUB;g_ac:rec_penalty_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','m_ac:rec_penalty_a\nm:SUB;g_ac:rec_penalty_am:SUB;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;'),(39,'Application Fee','FEE PENALTY','RECEIPT','FEE','MEMBER','Application Fee','g_ac:expenses_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(40,'Miscellaneous Fee','FEE PENALTY','RECEIPT','FEE','MEMBER','Miscellaneous Fee','g_ac:expenses_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(41,'Recovered Outstanding Fee','FEE PENALTY','RECEIPT','FEE','MEMBER','Revovered amount which was Outstanding Fee','m_ac:rec_penalty_am:ADD;m_ac:pending_penalty_am:SUB;g_ac:rec_penalty_am:ADD;g_ac:pending_penalty_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:ADD;','m_ac:rec_penalty_am:SUB;m_ac:pending_penalty_am:ADD;g_ac:rec_penalty_am:SUB;g_ac:pending_penalty_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:ADD;g_bank_account:balance_approve:ADD;','m_ac:rec_penalty_am:SUB;m_ac:pending_penalty_am:ADD;g_ac:rec_penalty_am:SUB;g_ac:pending_penalty_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:SUB;'),(42,'Outstanding Late Fee','OUTSTANDING FEE','INTERNAL','OUTSTANDING FEE','MEMBER','Outstanding Fee for Late Payment','m_ac:pending_penalty_am:ADD;g_ac:pending_penalty_am:ADD;g_ac_by_txtype:amount:ADD;','m_ac:pending_penalty_am:SUB;g_ac:pending_penalty_am:SUB;g_ac_by_txtype:amount:SUB;','','m_ac:pending_penalty_am:SUB;g_ac:pending_penalty_am:SUB;g_ac_by_txtype:amount:SUB;'),(43,'Outstanding Cheque Bouncing','OUTSTANDING FEE','INTERNAL','OUTSTANDING FEE','MEMBER','Outstanding Panalty for Cheque Bouncing','m_ac:pending_penalty_am:ADD;g_ac:pending_penalty_am:ADD;g_ac_by_txtype:amount:ADD;','m_ac:pending_penalty_am:SUB;g_ac:pending_penalty_am:SUB;g_ac_by_txtype:amount:SUB;','','m_ac:pending_penalty_am:SUB;g_ac:pending_penalty_am:SUB;g_ac_by_txtype:amount:SUB;'),(44,'Outstanding Auto Debit Fail','OUTSTANDING FEE','INTERNAL','OUTSTANDING FEE','MEMBER','Outstanding Panalty for Auto Debit Failure','m_ac:pending_penalty_am:ADD;g_ac:pending_penalty_am:ADD;g_ac_by_txtype:amount:ADD;','m_ac:pending_penalty_am:SUB;g_ac:pending_penalty_am:SUB;g_ac_by_txtype:amount:SUB;','','m_ac:pending_penalty_am:SUB;g_ac:pending_penalty_am:SUB;g_ac_by_txtype:amount:SUB;'),(45,'Outstanding Miscellaneous Fee','OUTSTANDING FEE','INTERNAL','OUTSTANDING FEE','MEMBER','Outstanding Miscellaneous Fee','m_ac:pending_penalty_am:ADD;g_ac:pending_penalty_am:ADD;g_ac_by_txtype:amount:ADD;','m_ac:pending_penalty_am:SUB;g_ac:pending_penalty_am:SUB;g_ac_by_txtype:amount:SUB;','','m_ac:pending_penalty_am:SUB;g_ac:pending_penalty_am:SUB;g_ac_by_txtype:amount:SUB;'),(46,'Stationary Expense','EXPENSE','VOUCHER','EXPENSE','MEMBER','Expenses done for Stationary','g_ac:expenses_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(47,'Furniture & Fixture Expense','EXPENSE','VOUCHER','EXPENSE','MEMBER','Expenses done for Furniture & Fixture','g_ac:expenses_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','g_ac:expenses_am:SUB;g_ac:balance_upda\nte:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(48,'Telephone & Postage Expense','EXPENSE','VOUCHER','EXPENSE','MEMBER','Expenses done for Telephone & Postage','g_ac:expenses_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(49,'Office Expense','EXPENSE','VOUCHER','EXPENSE','MEMBER','Expenses done for Office','g_ac:expenses_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(50,'Salary Expense','EXPENSE','VOUCHER','EXPENSE','MEMBER','Expenses done for Salary','g_ac:expenses_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(51,'Advertisement Expense','EXPENSE','VOUCHER','EXPENSE','MEMBER','Expenses done for Advertisement','g_ac:expenses_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(52,'Meeting Expense','EXPENSE','VOUCHER','EXPENSE','MEMBER','Expenses done for Meeting','g_ac:expenses_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(53,'Events Expense','EXPENSE','VOUCHER','EXPENSE','MEMBER','Expenses done for Events','g_ac:expenses_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(54,'Training Expense','EXPENSE','VOUCHER','EXPENSE','MEMBER','Expenses done for Training','g_ac:expenses_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(55,'Travel Expense','EXPENSE','VOUCHER','EXPENSE','MEMBER','Expenses done for Travel','g_ac:expenses_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(56,'Misc\nellaneous Expense','EXPENSE','VOUCHER','EXPENSE','MEMBER','Expenses done for Miscellaneous','g_ac:expenses_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(57,'Bank Charges Expense','EXPENSE','VOUCHER','EXPENSE','BANK','Expenses done for Bank Charges','g_ac:expenses_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;g_bank_account:penalty_charges_am:SUB;b_group_bank_account:penalty_charges_am:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;g_bank_account:penalty_charges_am:ADD;b_group_bank_account:penalty_charges_am:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;g_bank_account:penalty_charges_am:ADD;b_group_bank_account:penalty_charges_am:SUB;'),(58,'Bank Penalty Expense','EXPENSE','VOUCHER','EXPENSE','BANK','Expenses done for Bank Penalty','g_ac:expenses_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;g_bank_account:penalty_charges_am:SUB;b_group_bank_account:penalty_charges_am:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;g_bank_account:penalty_charges_am:ADD;b_group_bank_account:penalty_charges_am:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;g_bank_account:penalty_charges_am:ADD;b_group_bank_account:penalty_charges_am:SUB;'),(59,'Accounting Charges Expense','EXPENSE','VOUCHER','EXPENSE','MEMBER','Expenses done for Accounting Charges','g_ac:expenses_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;'),(60,'SHGOne Charges Expense','EXPENSE','VOUCHER','EXPENSE','MEMBER','Expenses done for SHG-One.Net Charges','g_ac:expenses_am:ADD;g_ac:balance_update:SUB;g_bank_account:balance_update:SUB;g_ac_by_txtype:amount:ADD;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;','g_ac:balance_approve:SUB;g_bank_account:balance_approve:SUB;','g_ac:expenses_am:SUB;g_ac:balance_update:ADD;g_bank_account:balance_update:ADD;g_ac_by_txtype:amount:SUB;');
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
-- Table structure for table `vendor_payment`
--

DROP TABLE IF EXISTS `vendor_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vendor_payment` (
  `vendor_payment_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `p_invoice_id` bigint(20) unsigned NOT NULL,
  `payment_type_id` int(10) unsigned NOT NULL,
  `vender_ac_no` bigint(20) NOT NULL,
  `done_by_ac_no` bigint(20) NOT NULL,
  `entered_by_ac_no` bigint(20) NOT NULL,
  `verified_by_ac_no` bigint(20) DEFAULT NULL,
  `amount` decimal(16,2) NOT NULL,
  `creation_ts` timestamp NULL DEFAULT NULL,
  `payment_ts` timestamp NULL DEFAULT NULL,
  `verify_ts` timestamp NULL DEFAULT NULL,
  `attachment` varchar(200) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`vendor_payment_id`),
  KEY `vendor_payment_fk1_idx` (`p_invoice_id`),
  KEY `vendor_payment_fk3_idx` (`vender_ac_no`),
  KEY `vendor_payment_fk4_idx` (`done_by_ac_no`),
  KEY `vendor_payment_fk5_idx` (`entered_by_ac_no`),
  KEY `vendor_payment_fk6_idx` (`verified_by_ac_no`),
  CONSTRAINT `vendor_payment_fk1` FOREIGN KEY (`p_invoice_id`) REFERENCES `p_invoice` (`p_invoice_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `vendor_payment_fk3` FOREIGN KEY (`vender_ac_no`) REFERENCES `g_profile` (`g_ac_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `vendor_payment_fk4` FOREIGN KEY (`done_by_ac_no`) REFERENCES `m_profile` (`m_ac_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `vendor_payment_fk5` FOREIGN KEY (`entered_by_ac_no`) REFERENCES `m_profile` (`m_ac_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `vendor_payment_fk6` FOREIGN KEY (`verified_by_ac_no`) REFERENCES `m_profile` (`m_ac_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vendor_payment`
--

LOCK TABLES `vendor_payment` WRITE;
/*!40000 ALTER TABLE `vendor_payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `vendor_payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `visit_status`
--

DROP TABLE IF EXISTS `visit_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `visit_status` (
  `visit_status_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `visit_status` varchar(45) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `next_status` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`visit_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visit_status`
--

LOCK TABLES `visit_status` WRITE;
/*!40000 ALTER TABLE `visit_status` DISABLE KEYS */;
INSERT INTO `visit_status` VALUES (1,'Requested','Visit is Requested!','Scheduled;Canceled'),(2,'Expected','Visit is Expected!','Scheduled;Canceled'),(3,'Scheduled','Visit is Scheduled!','Started;Not Available;Canceled'),(4,'Unscheduled','Visit is Unscheduled!','Started;Not Available;Canceled'),(5,'Started','Visit is Started!','Ended;Auto Closed'),(6,'Ended','Visit is properly Ended!',''),(7,'Auto Closed','Visit is not properly Ended, thus Auto Closed!',''),(8,'Not Available','Visit Owner was Not Available!','Scheduled;Started;Canceled'),(9,'Missed','Visit was Missed by MR!','Scheduled;Started;Canceled'),(10,'Canceled','Visit is Canceled!','');
/*!40000 ALTER TABLE `visit_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `visit_type`
--

DROP TABLE IF EXISTS `visit_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `visit_type` (
  `visit_type_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `visit_type` varchar(45) NOT NULL,
  `visit_for` varchar(50) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`visit_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visit_type`
--

LOCK TABLES `visit_type` WRITE;
/*!40000 ALTER TABLE `visit_type` DISABLE KEYS */;
INSERT INTO `visit_type` VALUES (1,'MR Pitch Visit','','SE Visit to Pitch for new MR account creation'),(2,'Scheduled MR Visit','Micro Retailer','Scheduled MR Visit by SE'),(3,'Requested MR Visit','Micro Retailer','Visit Requested by MR'),(4,'Delivery MR Visit','Micro Retailer','Delivery requested by MR'),(5,'Feedback MR Visit','Micro Retailer','Feedback MR Visit by SE'),(6,'Special MR Visit','Micro Retailer','Special MR Visit by SE'),(7,'Opening MR Account','Micro Retailer','Opening MR Account Visit by SE'),(8,'Closing MR Account','Micro Retailer','Closing MR Account Visit by SE'),(9,'SE Account','Sales Executive','SE interaction with HUB'),(10,'SSE Account','Super Sales Executive','SSE interaction with HUB'),(11,'Sub Local HUB','Sub Local HUB Manager','Sub Local HUB interaction with HUB'),(12,'Local HUB','Local HUB Manager','Local HUB interaction with HUB');
/*!40000 ALTER TABLE `visit_type` ENABLE KEYS */;
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

-- Dump completed on 2016-06-17 10:08:10
