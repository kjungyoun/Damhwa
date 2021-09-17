-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';


-- -----------------------------------------------------
-- Schema damwha
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `damhwa` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema damhwa
-- -----------------------------------------------------
USE `damhwa` ;

-- -----------------------------------------------------
-- Table `damhwa`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `damhwa`.`user` ;

CREATE TABLE IF NOT EXISTS `damhwa`.`user` (
  `userno` BIGINT NOT NULL,
  `email` VARCHAR(50) NOT NULL,
  `username` VARCHAR(30) NULL,
  `profile` VARCHAR(400) NULL,
  `token` VARCHAR(500) NULL,
  PRIMARY KEY (`userno`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `damhwa`.`flower`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `damhwa`.`flower` ;

CREATE TABLE IF NOT EXISTS `damhwa`.`flower` (
  `fno` BIGINT NOT NULL,
  `fnameKR` VARCHAR(40) NULL,
  `fnameEN` VARCHAR(40) NULL,
  `fmonth` INT NULL,
  `fday` INT NULL,
  `flang` VARCHAR(100) NULL,
  `fcontents` VARCHAR(500) NULL,
  `fuse` VARCHAR(500) NULL,
  `fgrow` VARCHAR(500) NULL,
  `img1` VARCHAR(400) NULL,
  `img2` VARCHAR(400) NULL,
  `img3` VARCHAR(400) NULL,
  PRIMARY KEY (`fno`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `damhwa`.`history`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `damhwa`.`history` ;

CREATE TABLE IF NOT EXISTS `damhwa`.`history` (
  `hno` BIGINT NOT NULL,
  `userno` BIGINT NULL,
  `fno` BIGINT NULL,
  `htype` TINYINT(1) NULL,
  `contents` VARCHAR(500) NULL,
  `to` VARCHAR(100) NULL,
  `regdate` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`hno`),
  INDEX `user_userno_FK_idx` (`userno` ASC) VISIBLE,
  INDEX `flower_fno_FK_idx` (`fno` ASC) VISIBLE,
  CONSTRAINT `user_userno_FK`
    FOREIGN KEY (`userno`)
    REFERENCES `mydb`.`user` (`userno`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `flower_fno_FK`
    FOREIGN KEY (`fno`)
    REFERENCES `mydb`.`flower` (`fno`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `damhwa`.`feeling`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `damhwa`.`feeling` ;

CREATE TABLE IF NOT EXISTS `damhwa`.`feeling` (
  `feelingno` BIGINT NOT NULL,
  `feelingname` VARCHAR(30) NULL,
  PRIMARY KEY (`feelingno`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
