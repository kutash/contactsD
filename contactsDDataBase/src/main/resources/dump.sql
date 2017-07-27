SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `contacts` DEFAULT CHARACTER SET utf8mb4 ;

-- -----------------------------------------------------
-- Table `contacts`.`contact`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `contacts`.`contact` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(45) NOT NULL,
  `middleName` VARCHAR(45) NOT NULL,
  `lastName` VARCHAR(45) NULL DEFAULT NULL,
  `birthday` DATE NULL DEFAULT NULL,
  `email` VARCHAR(90) NULL DEFAULT NULL,
  `sex` VARCHAR(45) NULL DEFAULT NULL,
  `status` VARCHAR(45) NULL DEFAULT NULL,
  `citizenship` VARCHAR(45) NULL DEFAULT NULL,
  `photo` VARCHAR(100) NULL DEFAULT NULL,
  `site` VARCHAR(45) NULL DEFAULT NULL,
  `company` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `contacts`.`address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `contacts`.`address` (
  `id_address` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `country` VARCHAR(45) NULL DEFAULT NULL,
  `city` VARCHAR(45) NULL DEFAULT NULL,
  `street` VARCHAR(45) NULL DEFAULT NULL,
  `house` VARCHAR(45) NULL DEFAULT NULL,
  `flat` VARCHAR(45) NULL DEFAULT NULL,
  `index` VARCHAR(45) NULL DEFAULT NULL,
  `contact_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id_address`, `contact_id`),
  UNIQUE INDEX `id_address_UNIQUE` (`id_address` ASC),
  INDEX `fk_address_contact1_idx` (`contact_id` ASC),
  CONSTRAINT `fk_address_contact1`
    FOREIGN KEY (`contact_id`)
    REFERENCES `contacts`.`contact` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `contacts`.`attachment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `contacts`.`attachment` (
  `id_attach` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `path` VARCHAR(21000) NULL DEFAULT NULL,
  `name` VARCHAR(200) NULL DEFAULT NULL,
  `date` DATE NULL DEFAULT NULL,
  `comment` VARCHAR(500) NULL DEFAULT NULL,
  `contact_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id_attach`, `contact_id`),
  UNIQUE INDEX `id_attach_UNIQUE` (`id_attach` ASC),
  INDEX `fk_attachment_contact_idx` (`contact_id` ASC),
  CONSTRAINT `fk_attachment_contact`
    FOREIGN KEY (`contact_id`)
    REFERENCES `contacts`.`contact` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `contacts`.`telephone`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `contacts`.`telephone` (
  `id_phone` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `countryCode` VARCHAR(3) NULL DEFAULT NULL,
  `operatorCode` VARCHAR(45) NULL DEFAULT NULL,
  `number` VARCHAR(45) NULL DEFAULT NULL,
  `kind` VARCHAR(45) NULL DEFAULT NULL,
  `comment` VARCHAR(500) NULL DEFAULT NULL,
  `contact_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id_phone`, `contact_id`),
  UNIQUE INDEX `id_phone_UNIQUE` (`id_phone` ASC),
  INDEX `fk_telephone_contact1_idx` (`contact_id` ASC),
  CONSTRAINT `fk_telephone_contact1`
    FOREIGN KEY (`contact_id`)
    REFERENCES `contacts`.`contact` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
