create database cara_store;
use cara_store;
-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: cara_store
-- ------------------------------------------------------
-- Server version	8.1.0

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
-- Table structure for table `brands`
--

DROP TABLE IF EXISTS `brands`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brands` (
  `brand_id` int NOT NULL AUTO_INCREMENT,
  `brand_name` varchar(50) NOT NULL,
  PRIMARY KEY (`brand_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brands`
--

LOCK TABLES `brands` WRITE;
/*!40000 ALTER TABLE `brands` DISABLE KEYS */;
INSERT INTO `brands` VALUES (1,'Nike'),(2,'Adidas'),(3,'Puma');
/*!40000 ALTER TABLE `brands` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `cart_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `product_id` int NOT NULL,
  `detail_id` int NOT NULL,
  `quantity` int NOT NULL,
  `price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`cart_id`),
  KEY `user_id` (`user_id`),
  KEY `product_id` (`product_id`),
  KEY `cart_ibfk_3` (`detail_id`),
  CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  CONSTRAINT `cart_ibfk_3` FOREIGN KEY (`detail_id`) REFERENCES `product_details` (`detail_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'T-shirts'),(2,'Pants'),(3,'Jackets');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `images`
--

DROP TABLE IF EXISTS `images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `images` (
  `image_id` int NOT NULL AUTO_INCREMENT,
  `image_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`image_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `images_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `images`
--

LOCK TABLES `images` WRITE;
/*!40000 ALTER TABLE `images` DISABLE KEYS */;
INSERT INTO `images` VALUES (1,'n11.jpg',1),(2,'n12.jpg',1),(3,'n13.jpg',1),(4,'n21.jpg',2),(5,'n22.jpg',2),(6,'n23.jpg',2),(7,'a11.jpg',3),(8,'a12.jpg',3),(9,'a13.jpg',3),(10,'a14.jpg',3),(11,'a21.jpg',4),(12,'a22.jpg',4),(13,'a23.jpg',4),(14,'a24.jpg',4),(15,'a_p11.jpg',5),(16,'a_p12.jpg',5),(17,'a_p13.jpg',5),(18,'a_p14.jpg',5),(19,'a14.jpg',3),(20,'a_p22.jpg',6),(21,'a_p23.jpg',6),(22,'p11.jpg',7),(23,'p12.jpg',7),(24,'p13.jpg',7),(25,'p14.jpg',7),(26,'p21.jpg',8),(27,'p22.jpg',8),(28,'p23.jpg',8),(29,'p24.jpg',8),(30,'n_j11.jpg',9),(31,'n_j12.jpg',9),(32,'n_j13.jpg',9),(33,'n_j14.jpg',9),(34,'n_j21.jpg',10),(35,'n_j22.jpg',10),(36,'n_j23.jpg',10),(37,'n_j31.jpg',11),(38,'n_j32.jpg',11),(39,'n_j33.jpg',11),(40,'n_j34.jpg',11),(41,'j11.jpg',12),(42,'j12.jpg',12),(43,'j13.jpg',12),(44,'j14.jpg',12),(45,'j21.jpg',13),(46,'j22.jpg',13),(47,'j23.jpg',13),(48,'j24.jpg',13);
/*!40000 ALTER TABLE `images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_details`
--

DROP TABLE IF EXISTS `order_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_details` (
  `order_detail_id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `product_id` int NOT NULL,
  `detail_id` int NOT NULL,
  `quantity` int NOT NULL,
  `price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`order_detail_id`),
  KEY `order_id` (`order_id`),
  KEY `product_id` (`product_id`),
  KEY `detail_id` (`detail_id`),
  CONSTRAINT `order_details_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `order_details_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  CONSTRAINT `order_details_ibfk_3` FOREIGN KEY (`detail_id`) REFERENCES `product_details` (`detail_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_details`
--

LOCK TABLES `order_details` WRITE;
/*!40000 ALTER TABLE `order_details` DISABLE KEYS */;
INSERT INTO `order_details` VALUES (7,5,3,19,6,59.99),(8,5,3,25,9,59.99),(9,5,4,26,1,29.99),(10,6,6,39,1,39.99),(11,6,6,37,1,39.99),(12,6,13,60,1,54.99),(13,6,9,49,1,79.99);
/*!40000 ALTER TABLE `order_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `order_date` date NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (5,1,'2023-11-14'),(6,1,'2023-11-14');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_details`
--

DROP TABLE IF EXISTS `product_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_details` (
  `detail_id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `color` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `size` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`detail_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `product_details_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_details`
--

LOCK TABLES `product_details` WRITE;
/*!40000 ALTER TABLE `product_details` DISABLE KEYS */;
INSERT INTO `product_details` VALUES (3,1,'rgb(255,255,255)','S'),(4,1,'rgb(255,255,255)','XL'),(5,1,'rgb(0,0,0)','L'),(6,1,'rgb(0,0,0)','XL'),(7,1,'rgb(0,0,0)','S'),(8,1,'rgb(0,0,0)','M'),(9,1,'rgb(0,0,255)','M'),(10,1,'rgb(0,0,255)','L'),(12,2,'rgb(255,0,0)','S'),(13,2,'rgb(255,0,0)','M'),(14,2,'rgb(255,0,0)','L'),(15,2,'rgb(128,128,128)','L'),(16,2,'rgb(128,128,128)','XL'),(17,2,'rgb(128,128,128)','S'),(18,2,'rgb(128,128,128)','M'),(19,3,'rgb(0,0,0)','M'),(20,3,'rgb(0,0,0)','L'),(21,3,'rgb(0,0,255)','L'),(22,3,'rgb(0,0,255)','XL'),(23,3,'rgb(0,0,255)','S'),(24,3,'rgb(255,0,0)','S'),(25,3,'rgb(255,0,0)','M'),(26,4,'rgb(0,0,0)','S'),(27,4,'rgb(0,0,0)','L'),(28,5,'rgb(0,0,0)','M'),(29,5,'rgb(0,0,0)','L'),(30,5,'rgb(0,0,0)','S'),(31,5,'rgb(0,0,0)','XL'),(32,5,'rgb(0,0,255)','L'),(33,5,'rgb(0,0,255)','M'),(34,5,'rgb(0,0,255)','XL'),(35,6,'rgb(0,0,0)','S'),(36,6,'rgb(0,0,0)','L'),(37,6,'rgb(0,0,0)','XL'),(38,6,'rgb(128,128,128)','L'),(39,6,'rgb(128,128,128)','S'),(40,6,'rgb(128,128,128)','XL'),(41,7,'rgb(0,0,0)','M'),(42,7,'rgb(0,0,0)','L'),(43,7,'rgb(0,0,0)','S'),(44,8,'rgb(255,255,255)','M'),(45,8,'rgb(255,255,255)','L'),(46,8,'rgb(255,255,255)','S'),(47,9,'rgb(0,0,128)','M'),(48,9,'rgb(0,0,128)','L'),(49,9,'rgb(0,0,128)','S'),(50,9,'rgb(0,0,128)','XL'),(51,10,'rgb(128,128,128)','M'),(52,10,'rgb(0,0,0)','L'),(53,11,'rgb(0,0,0)','M'),(54,11,'rgb(0,0,0)','L'),(55,12,'rgb(255,255,255)','M'),(56,12,'rgb(255,255,255)','XL'),(57,12,'rgb(128,128,128)','L'),(58,12,'rgb(128,128,128)','S'),(59,13,'rgb(0,0,0)','M'),(60,13,'rgb(0,0,0)','L'),(61,13,'rgb(0,0,0)','S'),(62,13,'rgb(0,0,0)','XL'),(66,1,'rgb(255, 255, 255)','L'),(67,1,'rgb(255, 255, 255)','XL');
/*!40000 ALTER TABLE `product_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(100) NOT NULL,
  `description` text,
  `price` decimal(10,2) NOT NULL,
  `category_id` int NOT NULL,
  `brand_id` int NOT NULL,
  PRIMARY KEY (`product_id`),
  KEY `category_id` (`category_id`),
  KEY `brand_id` (`brand_id`),
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`),
  CONSTRAINT `products_ibfk_2` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`brand_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Nike Sportswear Club T-Shirt','Stay comfortable and stylish with the Nike Sportswear Club T-Shirt. This classic tee features a soft cotton blend and a clean, casual design, making it a versatile addition to your wardrobe',19.99,1,1),(2,'Nike Essential Futura T-Shirt','Stay comfortable and stylish with the Nike Sportswear Club T-Shirt. This classic tee features a soft cotton blend and a clean, casual design, making it a versatile addition to your wardrobe',39.99,1,1),(3,'Adidas Badge of Sport Tee','It features a relaxed fit, a modern design, and the iconic Nike logo, making it a perfect choice for daily wear or workouts.',59.99,1,2),(4,'Adidas Winners Muscle Tee','Made from breathable fabric, this tee showcases the Adidas logo, offering a modern and sporty look suitable for various activities',29.99,1,2),(5,'Adidas Tiro 19 Training Pants','Ideal for athletic training, the Adidas Tiro 19 Training Pants offer a slim fit, moisture-wicking fabric, and mesh ventilation, ensuring comfort and style during your workouts',29.99,2,2),(6,'Adidas Essentials 3-Stripes Pants','The Adidas Essentials 3-Stripes Pants feature a tapered fit, soft fabric, and the classic Adidas 3-Stripes, perfect for a sporty or casual look',39.99,2,2),(7,'Puma Essentials Fleece Pants','Crafted from soft fleece fabric, the Puma Essentials Fleece Pants offer a relaxed fit and an elastic waistband, ensuring comfort during workouts or leisure time',19.99,2,3),(8,'Puma Classics T7 Track Pants','With a sleek, slim fit and T7 panel inserts, the Puma Classics T7 Track Pants are perfect for an athletic and modern look in any casual setting',29.99,2,3),(9,'Nike Windrunner Jacket','The Nike Windrunner Jacket features a lightweight, water-repellent design with a classic chevron chest panel, providing protection and style during outdoor activities',79.99,3,1),(10,'Nike Sportswear Club Fleece Jacket','Made from soft fleece fabric, the Nike Sportswear Club Fleece Jacket offers a relaxed fit and classic Nike style, perfect for casual wear or workouts',64.99,3,1),(11,'Nike Tech Fleece Windrunner Jacket','The Nike Tech Fleece Windrunner Jacket features innovative Tech Fleece fabric, providing lightweight warmth and a modern look suitable for any occasion',119.99,3,1),(12,'Puma Classics T7 Track Jacket','With the classic T7 panel inserts, the Puma Classics T7 Track Jacket offers a sleek, slim fit and a modern athletic look suitable for any casual setting',69.99,3,3),(13,'Puma Essentials Padded Jacket','The Puma Essentials Padded Jacket provides warmth and comfort, ideal for cooler weather, featuring a padded design and functional style',54.99,3,3);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ROLE_CUSTOMER'),(2,'ROLE_ADMIN');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `full_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `pass` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'pele','pele@gmail.com','abc123','0123456789','$2a$10$PG09EKU/na8YArkymBfPV.xffL61Ml4o2xZG3vRh7IEpg76i/uwwS',1),(2,'admin','admin@gmail.com','TP HCM','0234567890','$2a$10$Wnll4dwY8a/ZSc/l7YOILuHX8n7L1MJNiHly.ralW3zeqB707Gyuu',2);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'cara_store'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-24 21:49:56
