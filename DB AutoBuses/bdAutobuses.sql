-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema Autobus
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema Autobus
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Autobus` DEFAULT CHARACTER SET utf8 ;
USE `Autobus` ;

-- -----------------------------------------------------
-- Table `Autobus`.`chofer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Autobus`.`chofer` (
  `cedula` VARCHAR(20) NOT NULL,
  `nombre` VARCHAR(30) NOT NULL,
  `apellido` VARCHAR(30) NOT NULL,
  `correo` VARCHAR(45) NOT NULL,
  `telefono` INT(8) NOT NULL,
  `direccion` VARCHAR(45) NULL,
  PRIMARY KEY (`cedula`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Autobus`.`bus`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Autobus`.`bus` (
  `idBus` INT NOT NULL,
  `placa` VARCHAR(7) NOT NULL,
  `marca` VARCHAR(20) NOT NULL,
  `modelo` VARCHAR(20) NOT NULL,
  `anio` INT(4) NOT NULL,
  `chofer` VARCHAR(20) NOT NULL,
  `capacidad` INT(2) NOT NULL,
  INDEX `FK_BUS_CHOFER_idx` (`chofer` ASC),
  PRIMARY KEY (`idBus`),
  UNIQUE INDEX `placa_UNIQUE` (`placa` ASC),
  CONSTRAINT `FK_BUS_CHOFER`
    FOREIGN KEY (`chofer`)
    REFERENCES `Autobus`.`chofer` (`cedula`)
    ON DELETE RESTRICT
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Autobus`.`ruta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Autobus`.`ruta` (
  `idruta` INT NOT NULL,
  `lugar_salida` VARCHAR(20) NOT NULL,
  `lugar_destino` VARCHAR(20) NOT NULL,
  `precio` FLOAT(10) NOT NULL,
  `placa` VARCHAR(7) NOT NULL,
  `asientos_disponibles` INT(2) NOT NULL,
  PRIMARY KEY (`idruta`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Autobus`.`horario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Autobus`.`horario` (
  `idhorario` INT NOT NULL,
  `hora` DATETIME NOT NULL,
  PRIMARY KEY (`idhorario`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Autobus`.`ruta-horario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Autobus`.`ruta-horario` (
  `idruta` INT NOT NULL,
  `idHorario` INT NOT NULL,
  INDEX `FK_RUTASHORARIOS_RUTAS_idx` (`idruta` ASC),
  CONSTRAINT `FK_RUTASHORARIOS_HORARIOS`
    FOREIGN KEY (`idHorario`)
    REFERENCES `Autobus`.`horario` (`idhorario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_RUTASHORARIOS_RUTAS`
    FOREIGN KEY (`idruta`)
    REFERENCES `Autobus`.`ruta` (`idruta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Autobus`.`tiquetes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Autobus`.`tiquetes` (
  `idtiquetes` INT NOT NULL,
  `ruta` INT NOT NULL,
  `numero_asiento` INT(2) NOT NULL,
  `preferencial` TINYINT NOT NULL,
  `fecha_venta` DATETIME NOT NULL,
  PRIMARY KEY (`idtiquetes`),
  INDEX `FK_RUTAS_TICKET_idx` (`ruta` ASC),
  CONSTRAINT `FK_RUTAS_TICKET`
    FOREIGN KEY (`ruta`)
    REFERENCES `Autobus`.`ruta` (`idruta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Autobus`.`bus_Ruta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Autobus`.`bus_Ruta` (
  `idbus` INT NOT NULL,
  `idRuta` INT NOT NULL,
  INDEX `FK_BUS_RUTA_idx` (`idbus` ASC),
  INDEX `FK_RUTA_BUS_idx` (`idRuta` ASC),
  CONSTRAINT `FK_BUS_RUTA`
    FOREIGN KEY (`idbus`)
    REFERENCES `Autobus`.`bus` (`idBus`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_RUTA_BUS`
    FOREIGN KEY (`idRuta`)
    REFERENCES `Autobus`.`ruta` (`idruta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
