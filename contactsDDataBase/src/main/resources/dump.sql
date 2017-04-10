CREATE DATABASE `kutash_galina`
  CHARACTER SET 'utf8mb4'
  COLLATE 'utf8mb4_general_ci';



CREATE TABLE `kutash_galina`.`address` (
  `idAddress` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `country` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `street` varchar(45) DEFAULT NULL,
  `house` VARCHAR (45)  DEFAULT NULL,
   `flat` VARCHAR (45) DEFAULT NULL,
  `index` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idAddress`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;





CREATE TABLE `kutash_galina`.`attachment` (
  `idAttach` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `path` varchar(21000) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `comment` varchar(500) DEFAULT NULL,
  `idContact` int(10) DEFAULT NULL,
  PRIMARY KEY (`idAttach`),
  UNIQUE KEY `idAttach_UNIQUE` (`idAttach`),
  KEY `AttachFK_idx` (`idContact`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `kutash_galina`.`telephone` (
  `idPhone` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `countryCode` varchar(3) DEFAULT NULL,
  `operatorCode` varchar(45) DEFAULT NULL,
  `number` varchar(45) DEFAULT NULL,
  `kind` varchar(45) DEFAULT NULL,
  `comment` varchar(500) DEFAULT NULL,
  `idContact` int(10) DEFAULT NULL,
  PRIMARY KEY (`idPhone`),
  UNIQUE KEY `idPhone_UNIQUE` (`idPhone`),
  KEY `PhoneFk_idx` (`idContact`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;





CREATE TABLE `kutash_galina`.`contact` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `firstName` varchar(45) NOT NULL,
  `middleName` varchar(45) NOT NULL,
  `lastName` varchar(45) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `email` varchar(90) DEFAULT NULL,
  `sex` varchar(45) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `citizenship` varchar(45) DEFAULT NULL,
  `photo` varchar(100) DEFAULT NULL,
  `site` varchar(45) DEFAULT NULL,
  `company` varchar(45) DEFAULT NULL,
  `idAddress` int(10) unsigned DEFAULT NULL,
  `idPhone` int(10) unsigned default null,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_contact_address1_idx` (`idAddress`),
  key `fk_contact_phone_idx` (`idPhone`),
  FOREIGN KEY (`idAddress` )
  REFERENCES `kutash_galina`.`address` (`idAddress` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
    FOREIGN KEY (`idPhone` )
  REFERENCES `kutash_galina`.`telephone` (`idPhone` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
