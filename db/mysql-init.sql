-- drop database if exists sessions;
-- create database sessions;
use sessions;

--
-- Table structure for table `sessions`
--

DROP TABLE IF EXISTS `sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sessions` (
  `cell_id`      varchar(20) NOT NULL,
  `ctn`     	 varchar(20) NOT NULL,
  PRIMARY KEY (`cell_id`,`ctn`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EXAMPLE_TABLE`
--

LOCK TABLES `sessions` WRITE;
/*!40000 ALTER TABLE `sessions` DISABLE KEYS */;
INSERT INTO `sessions` (cell_id, ctn)
VALUES
( '11111', '01234567890'), 
( '11111', '01234567891'), 
( '11111', '01234567892');
/*!40000 ALTER TABLE `sessions` ENABLE KEYS */;
UNLOCK TABLES;
