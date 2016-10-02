-- MySQL dump 10.13  Distrib 5.7.9, for osx10.9 (x86_64)
--
-- Host: localhost    Database: Flockathon
-- ------------------------------------------------------
-- Server version	5.5.52-0ubuntu0.14.04.1

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
-- Table structure for table `agent`
--

DROP TABLE IF EXISTS `agent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `agent` (
  `agentId` int(11) NOT NULL AUTO_INCREMENT,
  `flockUserId` varchar(200) NOT NULL,
  `flockUserToken` text,
  `flockName` varchar(200) DEFAULT NULL,
  `lastAction` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `companyId` int(11) NOT NULL,
  `status` varchar(20) DEFAULT 'Active',
  PRIMARY KEY (`agentId`),
  UNIQUE KEY `flockUserId_UNIQUE` (`flockUserId`),
  KEY `agents_company_companyId_fk` (`companyId`),
  CONSTRAINT `agents_company_companyId_fk` FOREIGN KEY (`companyId`) REFERENCES `company` (`companyId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cannedResponse`
--

DROP TABLE IF EXISTS `cannedResponse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cannedResponse` (
  `agentId` int(11) DEFAULT NULL,
  `responseId` int(11) NOT NULL,
  `title` text NOT NULL,
  `content` text NOT NULL,
  PRIMARY KEY (`responseId`),
  KEY `Cannedresponse_agents_agentId_fk` (`agentId`),
  CONSTRAINT `Cannedresponse_agents_agentId_fk` FOREIGN KEY (`agentId`) REFERENCES `agent` (`agentId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cannedResponse`
--

LOCK TABLES `cannedResponse` WRITE;
/*!40000 ALTER TABLE `cannedResponse` DISABLE KEYS */;
/*!40000 ALTER TABLE `cannedResponse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company` (
  `companyId` int(11) NOT NULL AUTO_INCREMENT,
  `companyName` varchar(64) NOT NULL,
  `flockTeamId` text,
  PRIMARY KEY (`companyId`),
  UNIQUE KEY `company_companyName_uindex` (`companyName`)
) ENGINE=InnoDB AUTO_INCREMENT=9877 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `customerId` int(11) NOT NULL AUTO_INCREMENT,
  `serviceId` int(11) NOT NULL,
  `customerName` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`customerId`),
  KEY `customer_service_serviceId_fk` (`serviceId`),
  CONSTRAINT `customer_service_serviceId_fk` FOREIGN KEY (`serviceId`) REFERENCES `service` (`serviceId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customerFacebook`
--

DROP TABLE IF EXISTS `customerFacebook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customerFacebook` (
  `facebookCustomerId` int(11) NOT NULL AUTO_INCREMENT,
  `senderId` text,
  PRIMARY KEY (`facebookCustomerId`),
  CONSTRAINT `serviceFacebook_customer_customerid_fk` FOREIGN KEY (`facebookCustomerId`) REFERENCES `customer` (`customerId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customerFacebook`
--

LOCK TABLES `customerFacebook` WRITE;
/*!40000 ALTER TABLE `customerFacebook` DISABLE KEYS */;
/*!40000 ALTER TABLE `customerFacebook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customerTwitter`
--

DROP TABLE IF EXISTS `customerTwitter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customerTwitter` (
  `twitterCustomerId` int(11) NOT NULL AUTO_INCREMENT,
  `senderId` text,
  PRIMARY KEY (`twitterCustomerId`),
  CONSTRAINT `serviceTwitter_customer_customerid_fk` FOREIGN KEY (`twitterCustomerId`) REFERENCES `customer` (`customerId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customerTwitter`
--

LOCK TABLES `customerTwitter` WRITE;
/*!40000 ALTER TABLE `customerTwitter` DISABLE KEYS */;
/*!40000 ALTER TABLE `customerTwitter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messageData`
--

DROP TABLE IF EXISTS `messageData`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messageData` (
  `messageMapId` int(11) NOT NULL,
  `msg` longtext,
  `inoutstatus` int(11) DEFAULT NULL,
  `createdAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messageData`
--

LOCK TABLES `messageData` WRITE;
/*!40000 ALTER TABLE `messageData` DISABLE KEYS */;
/*!40000 ALTER TABLE `messageData` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messageMap`
--

DROP TABLE IF EXISTS `messageMap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messageMap` (
  `mapId` int(11) NOT NULL AUTO_INCREMENT,
  `customerId` int(11) NOT NULL,
  `agentId` int(11) DEFAULT NULL,
  `status` varchar(45) DEFAULT 'Not Started',
  PRIMARY KEY (`mapId`),
  KEY `messageMap_agents_agentId_fk` (`agentId`),
  KEY `messageMap_customer_customerid_fk` (`customerId`),
  CONSTRAINT `messageMap_agents_agentId_fk` FOREIGN KEY (`agentId`) REFERENCES `agent` (`agentId`),
  CONSTRAINT `messageMap_customer_customerid_fk` FOREIGN KEY (`customerId`) REFERENCES `customer` (`customerId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messageMap`
--

LOCK TABLES `messageMap` WRITE;
/*!40000 ALTER TABLE `messageMap` DISABLE KEYS */;
/*!40000 ALTER TABLE `messageMap` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `service` (
  `serviceId` int(11) NOT NULL AUTO_INCREMENT,
  `serviceName` varchar(64) NOT NULL,
  PRIMARY KEY (`serviceId`),
  UNIQUE KEY `service_serviceName_uindex` (`serviceName`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service`
--

LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
INSERT INTO `service` VALUES (1,'facebook'),(3,'kayako'),(2,'twitter');
/*!40000 ALTER TABLE `service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `serviceFacebook`
--

DROP TABLE IF EXISTS `serviceFacebook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `serviceFacebook` (
  `companyId` int(11) DEFAULT NULL,
  `token` text,
  KEY `serviceFacebook_company_companyId_fk` (`companyId`),
  CONSTRAINT `serviceFacebook_company_companyId_fk` FOREIGN KEY (`companyId`) REFERENCES `company` (`companyId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `serviceTwitter`
--

DROP TABLE IF EXISTS `serviceTwitter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `serviceTwitter` (
  `companyId` int(11) DEFAULT NULL,
  `token` text,
  KEY `serviceTwitter_company_companyId_fk` (`companyId`),
  CONSTRAINT `serviceTwitter_company_companyId_fk` FOREIGN KEY (`companyId`) REFERENCES `company` (`companyId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `serviceTwitter`
--

LOCK TABLES `serviceTwitter` WRITE;
/*!40000 ALTER TABLE `serviceTwitter` DISABLE KEYS */;
/*!40000 ALTER TABLE `serviceTwitter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'Flockathon'
--

--
-- Dumping routines for database 'Flockathon'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-09-25 10:26:53
