-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: heardropdb
-- ------------------------------------------------------
-- Server version	8.0.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `donation`
--

DROP TABLE IF EXISTS `donation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `item_name` varchar(100) NOT NULL,
  `description` text,
  `quantity` int NOT NULL,
  `pickup_location` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donation`
--

LOCK TABLES `donation` WRITE;
/*!40000 ALTER TABLE `donation` DISABLE KEYS */;
INSERT INTO `donation` VALUES (1,'Canned Food','10 cans of mixed vegetables',10,'123 Main St.'),(2,'Blankets','5 winter blankets',5,'456 Elm St.'),(3,'Water Bottles','20 bottles of clean drinking water',20,'789 Pine St.'),(4,'Winter Coats','3 adult-size winter coats',3,'321 Oak St.'),(5,'Books','A collection of children’s storybooks',15,'654 Maple St.');
/*!40000 ALTER TABLE `donation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `donationstatus`
--

DROP TABLE IF EXISTS `donationstatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donationstatus` (
  `id` int NOT NULL AUTO_INCREMENT,
  `donation_id` int NOT NULL,
  `status` varchar(50) DEFAULT 'Available',
  `recipient_username` varchar(50) DEFAULT NULL,
  `pickup_datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `donation_id` (`donation_id`),
  CONSTRAINT `donationstatus_ibfk_1` FOREIGN KEY (`donation_id`) REFERENCES `donation` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donationstatus`
--

LOCK TABLES `donationstatus` WRITE;
/*!40000 ALTER TABLE `donationstatus` DISABLE KEYS */;
INSERT INTO `donationstatus` VALUES (1,1,'Available',NULL,NULL),(2,2,'Reserved','charlie','2024-12-20 14:00:00'),(3,3,'Available',NULL,NULL),(4,4,'Reserved','diana','2024-12-25 10:00:00'),(5,5,'Available',NULL,NULL);
/*!40000 ALTER TABLE `donationstatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `donor`
--

DROP TABLE IF EXISTS `donor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donor` (
  `id` int NOT NULL AUTO_INCREMENT,
  `donation_id` int NOT NULL,
  `donor_id` int NOT NULL,
  `donor_username` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `donation_id` (`donation_id`),
  KEY `donor_id` (`donor_id`),
  CONSTRAINT `donor_ibfk_1` FOREIGN KEY (`donation_id`) REFERENCES `donation` (`id`) ON DELETE CASCADE,
  CONSTRAINT `donor_ibfk_2` FOREIGN KEY (`donor_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donor`
--

LOCK TABLES `donor` WRITE;
/*!40000 ALTER TABLE `donor` DISABLE KEYS */;
INSERT INTO `donor` VALUES (1,1,1,'alice'),(2,2,1,'alice'),(3,3,2,'bob'),(4,4,2,'bob'),(5,5,1,'alice');
/*!40000 ALTER TABLE `donor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'alice','alice123'),(2,'bob','bob123'),(3,'charlie','charlie123'),(4,'diana','diana123'),(5,'admin','admin123');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-18 17:36:03
