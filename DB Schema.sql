-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema enjoytrip
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema enjoytrip
-- -----------------------------------------------------

use enjoytrip;

-- -----------------------------------------------------
-- Table `mydb`.`members`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`members` (
  `userid` VARCHAR(30) NOT NULL,
  `userpwd` VARCHAR(30) NOT NULL,
  `username` VARCHAR(30) NOT NULL,
  `isdelete` TINYINT NOT NULL DEFAULT 0,
  `profile` VARCHAR(1000) NULL DEFAULT 'noprofile',
  `registdate` DATE NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`userid`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`freeboard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`freeboard` (
  `articleno` INT NOT NULL AUTO_INCREMENT,
  `userid` VARCHAR(30) NOT NULL,
  `subject` VARCHAR(100) NOT NULL,
  `content` VARCHAR(1000) NOT NULL,
  `hit` INT NULL DEFAULT 0,
  `likes` INT NULL DEFAULT 0,
  `registdate` DATE NULL DEFAULT CURRENT_TIMESTAMP,
  `` VARCHAR(45) NULL,
  PRIMARY KEY (`articleno`),
  INDEX `mTOfb_idx` (`userid` ASC) VISIBLE,
  CONSTRAINT `mTOfb`
    FOREIGN KEY (`userid`)
    REFERENCES `mydb`.`members` (`userid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`freeboardfiles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`freeboardfiles` (
  `articleno` INT NOT NULL,
  `url` VARCHAR(1000) NOT NULL,
  `filename` VARCHAR(1000) NULL DEFAULT 'file',
  INDEX `fbTOfbf_idx` (`articleno` ASC) VISIBLE,
  CONSTRAINT `fbTOfbf`
    FOREIGN KEY (`articleno`)
    REFERENCES `mydb`.`freeboard` (`articleno`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`reviewboard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`reviewboard` (
  `articleno` INT NOT NULL,
  `userid` VARCHAR(30) NOT NULL,
  `subject` VARCHAR(100) NOT NULL,
  `content` VARCHAR(1000) NOT NULL,
  `hit` INT NULL DEFAULT 0,
  `likes` INT NULL DEFAULT 0,
  `registdate` DATE NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`articleno`),
  INDEX `rbTOm_idx` (`userid` ASC) VISIBLE,
  CONSTRAINT `rbTOm`
    FOREIGN KEY (`userid`)
    REFERENCES `mydb`.`members` (`userid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`reviewboardfiles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`reviewboardfiles` (
  `articleno` INT NOT NULL,
  `url` VARCHAR(1000) NOT NULL,
  `filename` VARCHAR(1000) NULL DEFAULT 'file',
  INDEX `rbfTOrb_idx` (`articleno` ASC) VISIBLE,
  CONSTRAINT `rbfTOrb`
    FOREIGN KEY (`articleno`)
    REFERENCES `mydb`.`reviewboard` (`articleno`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `enjoytrip`.`attraction_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`attraction_info` (
  `content_id` INT NOT NULL,
  `content_type_id` INT NULL DEFAULT NULL,
  `title` VARCHAR(100) NULL DEFAULT NULL,
  `addr1` VARCHAR(100) NULL DEFAULT NULL,
  `addr2` VARCHAR(50) NULL DEFAULT NULL,
  `zipcode` VARCHAR(50) NULL DEFAULT NULL,
  `tel` VARCHAR(50) NULL DEFAULT NULL,
  `first_image` VARCHAR(200) NULL DEFAULT NULL,
  `first_image2` VARCHAR(200) NULL DEFAULT NULL,
  `readcount` INT NULL DEFAULT NULL,
  `sido_code` INT NULL DEFAULT NULL,
  `gugun_code` INT NULL DEFAULT NULL,
  `latitude` DECIMAL(20,17) NULL DEFAULT NULL,
  `longitude` DECIMAL(20,17) NULL DEFAULT NULL,
  `mlevel` VARCHAR(2) NULL DEFAULT NULL,
  PRIMARY KEY (`content_id`),
  INDEX `attraction_to_content_type_id_fk_idx` (`content_type_id` ASC) VISIBLE,
  INDEX `attraction_to_sido_code_fk_idx` (`sido_code` ASC) VISIBLE,
  INDEX `attraction_to_gugun_code_fk_idx` (`gugun_code` ASC) VISIBLE,
  CONSTRAINT `attraction_to_content_type_id_fk`
    FOREIGN KEY (`content_type_id`)
    REFERENCES `enjoytrip`.`content_type` (`content_type_id`),
  CONSTRAINT `attraction_to_gugun_code_fk`
    FOREIGN KEY (`gugun_code`)
    REFERENCES `enjoytrip`.`gugun` (`gugun_code`),
  CONSTRAINT `attraction_to_sido_code_fk`
    FOREIGN KEY (`sido_code`)
    REFERENCES `enjoytrip`.`sido` (`sido_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `mydb`.`reviewboardattr`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`reviewboardattr` (
  `articleno` INT NOT NULL,
  `contentid` INT NOT NULL,
  `score` INT NOT NULL,
  INDEX `rbaTOrb_idx` (`articleno` ASC) VISIBLE,
  INDEX `rbaTOai_idx` (`contentid` ASC) VISIBLE,
  CONSTRAINT `rbaTOrb`
    FOREIGN KEY (`articleno`)
    REFERENCES `mydb`.`reviewboard` (`articleno`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `rbaTOai`
    FOREIGN KEY (`contentid`)
    REFERENCES `enjoytrip`.`attraction_info` (`content_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `enjoytrip` ;

-- -----------------------------------------------------
-- Table `enjoytrip`.`sido`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`sido` (
  `sido_code` INT NOT NULL,
  `sido_name` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`sido_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `enjoytrip`.`gugun`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`gugun` (
  `gugun_code` INT NOT NULL,
  `gugun_name` VARCHAR(30) NULL DEFAULT NULL,
  `sido_code` INT NOT NULL,
  PRIMARY KEY (`gugun_code`, `sido_code`),
  INDEX `gugun_to_sido_sido_code_fk_idx` (`sido_code` ASC) VISIBLE,
  CONSTRAINT `gugun_to_sido_sido_code_fk`
    FOREIGN KEY (`sido_code`)
    REFERENCES `enjoytrip`.`sido` (`sido_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `enjoytrip`.`attraction_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`attraction_info` (
  `content_id` INT NOT NULL,
  `content_type_id` INT NULL DEFAULT NULL,
  `title` VARCHAR(100) NULL DEFAULT NULL,
  `addr1` VARCHAR(100) NULL DEFAULT NULL,
  `addr2` VARCHAR(50) NULL DEFAULT NULL,
  `zipcode` VARCHAR(50) NULL DEFAULT NULL,
  `tel` VARCHAR(50) NULL DEFAULT NULL,
  `first_image` VARCHAR(200) NULL DEFAULT NULL,
  `first_image2` VARCHAR(200) NULL DEFAULT NULL,
  `readcount` INT NULL DEFAULT NULL,
  `sido_code` INT NULL DEFAULT NULL,
  `gugun_code` INT NULL DEFAULT NULL,
  `latitude` DECIMAL(20,17) NULL DEFAULT NULL,
  `longitude` DECIMAL(20,17) NULL DEFAULT NULL,
  `mlevel` VARCHAR(2) NULL DEFAULT NULL,
  PRIMARY KEY (`content_id`),
  INDEX `attraction_to_content_type_id_fk_idx` (`content_type_id` ASC) VISIBLE,
  INDEX `attraction_to_sido_code_fk_idx` (`sido_code` ASC) VISIBLE,
  INDEX `attraction_to_gugun_code_fk_idx` (`gugun_code` ASC) VISIBLE,
  CONSTRAINT `attraction_to_content_type_id_fk`
    FOREIGN KEY (`content_type_id`)
    REFERENCES `enjoytrip`.`content_type` (`content_type_id`),
  CONSTRAINT `attraction_to_gugun_code_fk`
    FOREIGN KEY (`gugun_code`)
    REFERENCES `enjoytrip`.`gugun` (`gugun_code`),
  CONSTRAINT `attraction_to_sido_code_fk`
    FOREIGN KEY (`sido_code`)
    REFERENCES `enjoytrip`.`sido` (`sido_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `enjoytrip`.`arrtrank`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`arrtrank` (
  `contentid` INT NOT NULL,
  `score` INT NOT NULL,
  `sidocode` INT NOT NULL,
  INDEX `arTOai_idx` (`contentid` ASC) VISIBLE,
  INDEX `arTOai2_idx` (`sidocode` ASC) VISIBLE,
  CONSTRAINT `arTOai`
    FOREIGN KEY (`contentid`)
    REFERENCES `enjoytrip`.`attraction_info` (`content_id`),
  CONSTRAINT `arTOai2`
    FOREIGN KEY (`sidocode`)
    REFERENCES `enjoytrip`.`attraction_info` (`sido_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`attraction_description`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`attraction_description` (
  `content_id` INT NOT NULL,
  `homepage` VARCHAR(100) NULL DEFAULT NULL,
  `overview` VARCHAR(10000) NULL DEFAULT NULL,
  `telname` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`content_id`),
  CONSTRAINT `attraction_detail_to_attraciton_id_fk`
    FOREIGN KEY (`content_id`)
    REFERENCES `enjoytrip`.`attraction_info` (`content_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `enjoytrip`.`members`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`members` (
  `userid` VARCHAR(30) NOT NULL,
  `userpwd` VARCHAR(30) NOT NULL,
  `username` VARCHAR(30) NOT NULL,
  `isdelete` TINYINT NOT NULL DEFAULT '0',
  `profile` VARCHAR(1000) NULL DEFAULT 'noprofile',
  `registdate` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `token` VARCHAR(1000) NULL DEFAULT NULL,
  PRIMARY KEY (`userid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`courselist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`courselist` (
  `userid` VARCHAR(30) NOT NULL,
  `contentid` INT NOT NULL,
  INDEX `clTOm_idx` (`userid` ASC) VISIBLE,
  INDEX `clTOai_idx` (`contentid` ASC) VISIBLE,
  CONSTRAINT `clTOai`
    FOREIGN KEY (`contentid`)
    REFERENCES `enjoytrip`.`attraction_info` (`content_id`),
  CONSTRAINT `clTOm`
    FOREIGN KEY (`userid`)
    REFERENCES `enjoytrip`.`members` (`userid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`freeboard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`freeboard` (
  `articleno` INT NOT NULL AUTO_INCREMENT,
  `userid` VARCHAR(30) NOT NULL,
  `subject` VARCHAR(100) NOT NULL,
  `content` VARCHAR(1000) NOT NULL,
  `hit` INT NULL DEFAULT '0',
  `likes` INT NULL DEFAULT '0',
  `registdate` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `isdelete` TINYINT NOT NULL DEFAULT '0',
  PRIMARY KEY (`articleno`),
  INDEX `mTOfb_idx` (`userid` ASC) VISIBLE,
  CONSTRAINT `mTOfb`
    FOREIGN KEY (`userid`)
    REFERENCES `enjoytrip`.`members` (`userid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`freeboardfiles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`freeboardfiles` (
  `articleno` INT NOT NULL,
  `url` VARCHAR(1000) NOT NULL,
  `filename` VARCHAR(1000) NULL DEFAULT 'file',
  INDEX `fbTOfbf_idx` (`articleno` ASC) VISIBLE,
  CONSTRAINT `fbTOfbf`
    FOREIGN KEY (`articleno`)
    REFERENCES `enjoytrip`.`freeboard` (`articleno`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`historylist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`historylist` (
  `userid` VARCHAR(30) NOT NULL,
  `contentid` INT NOT NULL,
  INDEX `hlTOm_idx` (`userid` ASC) VISIBLE,
  INDEX `hlTOai_idx` (`contentid` ASC) VISIBLE,
  CONSTRAINT `hlTOai`
    FOREIGN KEY (`contentid`)
    REFERENCES `enjoytrip`.`attraction_info` (`content_id`),
  CONSTRAINT `hlTOm`
    FOREIGN KEY (`userid`)
    REFERENCES `enjoytrip`.`members` (`userid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`reviewboard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`reviewboard` (
  `articleno` INT NOT NULL,
  `userid` VARCHAR(30) NOT NULL,
  `subject` VARCHAR(100) NOT NULL,
  `content` VARCHAR(1000) NOT NULL,
  `hit` INT NULL DEFAULT '0',
  `likes` INT NULL DEFAULT '0',
  `registdate` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `isdelete` TINYINT NOT NULL DEFAULT '0',
  PRIMARY KEY (`articleno`),
  INDEX `rbTOm_idx` (`userid` ASC) VISIBLE,
  CONSTRAINT `rbTOm`
    FOREIGN KEY (`userid`)
    REFERENCES `enjoytrip`.`members` (`userid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`reviewboardattr`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`reviewboardattr` (
  `articleno` INT NOT NULL,
  `contentid` INT NOT NULL,
  `score` INT NOT NULL,
  INDEX `rbaTOrb_idx` (`articleno` ASC) VISIBLE,
  INDEX `rbaTOai_idx` (`contentid` ASC) VISIBLE,
  CONSTRAINT `rbaTOai`
    FOREIGN KEY (`contentid`)
    REFERENCES `enjoytrip`.`attraction_info` (`content_id`),
  CONSTRAINT `rbaTOrb`
    FOREIGN KEY (`articleno`)
    REFERENCES `enjoytrip`.`reviewboard` (`articleno`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`reviewboardfiles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`reviewboardfiles` (
  `articleno` INT NOT NULL,
  `url` VARCHAR(1000) NOT NULL,
  `filename` VARCHAR(1000) NULL DEFAULT 'file',
  INDEX `rbfTOrb_idx` (`articleno` ASC) VISIBLE,
  CONSTRAINT `rbfTOrb`
    FOREIGN KEY (`articleno`)
    REFERENCES `enjoytrip`.`reviewboard` (`articleno`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`wishlist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`wishlist` (
  `userid` VARCHAR(30) NOT NULL,
  `conentid` INT NOT NULL,
  INDEX `wlTOm_idx` (`userid` ASC) VISIBLE,
  INDEX `wlTOai_idx` (`conentid` ASC) VISIBLE,
  CONSTRAINT `wlTOai`
    FOREIGN KEY (`conentid`)
    REFERENCES `enjoytrip`.`attraction_info` (`content_id`),
  CONSTRAINT `wlTOm`
    FOREIGN KEY (`userid`)
    REFERENCES `enjoytrip`.`members` (`userid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`freecomment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`freecomment` (
  `commentno` INT NOT NULL,
  `articleno` INT NOT NULL,
  `userid` VARCHAR(30) NOT NULL,
  `subject` VARCHAR(100) NOT NULL,
  `content` VARCHAR(1000) NOT NULL,
  `isdelete` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`commentno`),
  INDEX `fcTOm_idx` (`userid` ASC) VISIBLE,
  CONSTRAINT `fcTOfb`
    FOREIGN KEY (`articleno`)
    REFERENCES `enjoytrip`.`freeboard` (`articleno`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fcTOm`
    FOREIGN KEY (`userid`)
    REFERENCES `enjoytrip`.`members` (`userid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `enjoytrip`.`reviewcomment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`reviewcomment` (
  `commentno` INT NOT NULL,
  `articleno` INT NOT NULL,
  `userid` VARCHAR(30) NOT NULL,
  `subject` VARCHAR(100) NOT NULL,
  `content` VARCHAR(1000) NOT NULL,
  `isdelete` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`commentno`),
  INDEX `fcTOm_idx` (`userid` ASC) VISIBLE,
  CONSTRAINT `fcTOfb0`
    FOREIGN KEY (`commentno`)
    REFERENCES `enjoytrip`.`reviewboard` (`articleno`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fcTOm0`
    FOREIGN KEY (`userid`)
    REFERENCES `enjoytrip`.`members` (`userid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
