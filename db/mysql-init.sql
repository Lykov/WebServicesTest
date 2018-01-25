use sessions;

--
-- Table structure for table `sessions`
--

DROP TABLE IF EXISTS `sessions`;

CREATE TABLE `sessions` (
  `cell_id`      varchar(20) NOT NULL,
  `ctn`     	 varchar(20) NOT NULL,
  PRIMARY KEY (`cell_id`,`ctn`)
);

--
-- Loading data to table `sessions` from csv dump:
--

LOCK TABLES `sessions` WRITE;

LOAD DATA INFILE '/var/lib/mysql-files/sessions.csv' INTO TABLE sessions
FIELDS TERMINATED BY ',' 
IGNORE 1 LINES;

UNLOCK TABLES;
