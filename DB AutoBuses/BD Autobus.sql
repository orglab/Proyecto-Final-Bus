-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema autobus
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema autobus
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `autobus` DEFAULT CHARACTER SET utf8 ;
USE `autobus` ;

-- -----------------------------------------------------
-- Table `autobus`.`chofer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `autobus`.`chofer` (
  `cedula` VARCHAR(20) NOT NULL,
  `nombre` VARCHAR(30) NOT NULL,
  `apellido` VARCHAR(30) NOT NULL,
  `correo` VARCHAR(45) NOT NULL,
  `telefono` INT(8) NOT NULL,
  `direccion` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`cedula`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `autobus`.`bus`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `autobus`.`bus` (
  `idBus` INT(11) NOT NULL,
  `placa` VARCHAR(7) NOT NULL,
  `marca` VARCHAR(20) NOT NULL,
  `modelo` VARCHAR(20) NOT NULL,
  `anio` INT(4) NOT NULL,
  `chofer` VARCHAR(20) NOT NULL,
  `capacidad` INT(2) NOT NULL,
  PRIMARY KEY (`idBus`),
  UNIQUE INDEX `placa_UNIQUE` (`placa` ASC),
  INDEX `FK_BUS_CHOFER_idx` (`chofer` ASC),
  CONSTRAINT `FK_BUS_CHOFER`
    FOREIGN KEY (`chofer`)
    REFERENCES `autobus`.`chofer` (`cedula`)
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `autobus`.`ruta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `autobus`.`ruta` (
  `idruta` INT(11) NOT NULL,
  `lugar_salida` VARCHAR(20) NOT NULL,
  `lugar_destino` VARCHAR(20) NOT NULL,
  `precio` FLOAT NOT NULL,
  `placa` VARCHAR(7) NOT NULL,
  `asientos_disponibles` INT(2) NOT NULL,
  PRIMARY KEY (`idruta`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `autobus`.`bus_ruta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `autobus`.`bus_ruta` (
  `idbus` INT(11) NOT NULL,
  `idRuta` INT(11) NOT NULL,
  INDEX `FK_BUS_RUTA_idx` (`idbus` ASC),
  INDEX `FK_RUTA_BUS_idx` (`idRuta` ASC),
  CONSTRAINT `FK_BUS_RUTA`
    FOREIGN KEY (`idbus`)
    REFERENCES `autobus`.`bus` (`idBus`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_RUTA_BUS`
    FOREIGN KEY (`idRuta`)
    REFERENCES `autobus`.`ruta` (`idruta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `autobus`.`horario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `autobus`.`horario` (
  `idhorario` INT(11) NOT NULL,
  `hora` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`idhorario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `autobus`.`ruta-horario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `autobus`.`ruta-horario` (
  `idruta` INT(11) NOT NULL,
  `idHorario` INT(11) NOT NULL,
  INDEX `FK_RUTASHORARIOS_RUTAS_idx` (`idruta` ASC),
  INDEX `FK_RUTASHORARIOS_HORARIOS` (`idHorario` ASC),
  CONSTRAINT `FK_RUTASHORARIOS_HORARIOS`
    FOREIGN KEY (`idHorario`)
    REFERENCES `autobus`.`horario` (`idhorario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_RUTASHORARIOS_RUTAS`
    FOREIGN KEY (`idruta`)
    REFERENCES `autobus`.`ruta` (`idruta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `autobus`.`tiquetes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `autobus`.`tiquetes` (
  `idtiquetes` INT(11) NOT NULL,
  `ruta` INT(11) NOT NULL,
  `numero_asiento` INT(2) NOT NULL,
  `preferencial` TINYINT(4) NOT NULL,
  `fecha_venta` DATETIME NOT NULL,
  PRIMARY KEY (`idtiquetes`),
  INDEX `FK_RUTAS_TICKET_idx` (`ruta` ASC),
  CONSTRAINT `FK_RUTAS_TICKET`
    FOREIGN KEY (`ruta`)
    REFERENCES `autobus`.`ruta` (`idruta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

USE `autobus` ;

-- -----------------------------------------------------
-- procedure pa_buscar_chofer
-- -----------------------------------------------------

DELIMITER $$
USE `autobus`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_buscar_chofer`(
	IN p_cedula varchar(20),
	IN p_nombre varchar(30),
	IN p_apellido varchar(30), 
	IN p_correo varchar(45), 
	IN p_telefono int(8),
	IN p_direccion varchar(45),
	OUT success boolean
)
BEGIN
IF EXISTS ( SELECT cedula
					FROM chofer 
					WHERE cedula = p_cedula) THEN

SELECT * FROM chofer
WHERE cedula = p_cedula;

set success = true;
ELSE 
	SELECT 'Este numero de cédula de chofer no existe en la base de datos!';
	set success = false;
END IF; 

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure pa_editar_Bus
-- -----------------------------------------------------

DELIMITER $$
USE `autobus`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_editar_Bus`(
	IN p_placa varchar(7),
	IN p_marca varchar(20), 
	IN p_modelo varchar(20), 
	IN p_anio int(4),
    IN p_chofer varchar(20),
    IN p_capacidad int(2),
	OUT success boolean
)
BEGIN
 IF EXISTS ( SELECT placa
						FROM autobus.bus 
						WHERE  placa= p_placa) THEN
 
UPDATE bus set marca= p_marca, modelo = p_modelo,anio = p_anio ,chofer = p_chofer, capacidad=p_capacidad
WHERE  placa= p_placa;

set success = true;
ELSE 

SELECT 'Este numero de Autobus no existe de chofer no existe en la base de datos!';

set success = false;
END IF; 

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure pa_editar_chofer
-- -----------------------------------------------------

DELIMITER $$
USE `autobus`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_editar_chofer`(
IN p_cedula varchar(20),
	IN p_nombre varchar(30),
	IN p_apellido varchar(30), 
	IN p_correo varchar(45), 
	IN p_telefono int(8),
	IN p_direccion varchar(45),
	OUT success boolean
)
BEGIN
 IF EXISTS ( SELECT cedula
 FROM chofer 
 WHERE cedula = p_cedula) THEN
 
UPDATE chofer set nombre = p_nombre, apellido = p_apellido, correo = p_correo,telefono = p_telefono ,direccion = p_direccion
WHERE cedula = p_cedula;

set success = true;
ELSE 

SELECT 'Este numero de cédula de chofer no existe en la base de datos!';

set success = false;
END IF; 

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure pa_eliminar_Bus
-- -----------------------------------------------------

DELIMITER $$
USE `autobus`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_eliminar_Bus`(
   IN p_placa varchar(7),
	OUT success boolean
)
BEGIN
 IF EXISTS ( SELECT p_placa
FROM bus 
WHERE placa = p_placa) THEN

DELETE FROM bus
WHERE placa = p_placa;

set success = true;
ELSE 
	SELECT 'Este numero de Placa ingresado no existe en la base de datos!';
	set success = false;
END IF; 

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure pa_eliminar_chofer
-- -----------------------------------------------------

DELIMITER $$
USE `autobus`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_eliminar_chofer`(
IN p_cedula varchar(20),
	OUT success boolean
)
BEGIN
 IF EXISTS ( SELECT cedula
FROM chofer 
WHERE cedula = p_cedula) THEN

DELETE FROM chofer
WHERE cedula = p_cedula;

set success = true;
ELSE 
	SELECT 'Este numero de cédula de chofer no existe en la base de datos!';
	set success = false;
END IF; 

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure pa_insertar_bus
-- -----------------------------------------------------

DELIMITER $$
USE `autobus`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_insertar_bus`(
	IN p_idBus int(11),
	IN p_placa varchar(7),
	IN p_marca varchar(20), 
	IN p_modelo varchar(20), 
	IN p_anio int(4),
  IN p_chofer varchar(20),
  IN p_capacidad int(2),
	OUT success boolean

	)
BEGIN
		IF NOT EXISTS ( SELECT idBus
						FROM autobus.bus 
						WHERE  placa= p_placa) THEN
			INSERT INTO bus(idBus ,placa ,marca ,modelo ,anio ,chofer ,capacidad)
						VALUES (p_idBus ,p_placa ,p_marca , p_modelo , p_anio ,p_chofer , p_capacidad);
			set success = 1;
		ELSE
			SELECT 'Bus ya existe!';
			set success = 0;
		END IF;
	END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure pa_insertar_chofer
-- -----------------------------------------------------

DELIMITER $$
USE `autobus`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_insertar_chofer`(
	IN p_cedula varchar(20),
	IN p_nombre varchar(30),
	IN p_apellido varchar(30), 
	IN p_correo varchar(45), 
	IN p_telefono int(8),
	IN p_direccion varchar(45),
	OUT success boolean
					
	)
BEGIN

		IF NOT EXISTS ( SELECT cedula
						FROM chofer 
						WHERE cedula = p_cedula) THEN
			
			INSERT INTO chofer(cedula, nombre, apellido,correo,telefono,direccion)
						VALUES (p_cedula, p_nombre, p_apellido, p_correo, p_telefono, p_direccion);
			set success = 1;

		ELSE
			SELECT 'Cédula ya existe!';
			set success = 0;
		END IF;
		
	END$$

DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
