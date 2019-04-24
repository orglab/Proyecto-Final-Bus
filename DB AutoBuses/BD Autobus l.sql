-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

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
  UNIQUE INDEX `placa_UNIQUE` (`placa` ASC) ,
  INDEX `FK_BUS_CHOFER_idx` (`chofer` ASC) ,
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
  INDEX `FK_BUS_RUTA_idx` (`idbus` ASC) ,
  INDEX `FK_RUTA_BUS_idx` (`idRuta` ASC) ,
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
  `hora` DATETIME NOT NULL,
  PRIMARY KEY (`idhorario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `autobus`.`ruta-horario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `autobus`.`ruta-horario` (
  `idruta` INT(11) NOT NULL,
  `idHorario` INT(11) NOT NULL,
  INDEX `FK_RUTASHORARIOS_RUTAS_idx` (`idruta` ASC) ,
  INDEX `FK_RUTASHORARIOS_HORARIOS` (`idHorario` ASC) ,
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
  INDEX `FK_TIQUETES_RUTA_HORARIO_idx` (`ruta` ASC) ,
  CONSTRAINT `FK_TIQUETES_RUTA_HORARIO`
    FOREIGN KEY (`ruta`)
    REFERENCES `autobus`.`ruta-horario` (`idruta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

USE `autobus` ;

-- -----------------------------------------------------
-- procedure pa_AsignarHorarioRuta
-- -----------------------------------------------------

DELIMITER $$
USE `autobus`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_AsignarHorarioRuta`(
IN p_idruta int(11),
IN p_idHorario varchar(45),
OUT success boolean
)
BEGIN
IF EXISTS (SELECT idruta from ruta WHERE idruta=p_idruta)THEN
INSERT INTO `ruta-hoRario` (`idruta`,
`idHorario`)
VALUES(p_idruta,p_idHorario);
SET success =true;
else
SET success =false;
END IF;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure pa_DesAsignarHorarioRuta
-- -----------------------------------------------------

DELIMITER $$
USE `autobus`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_DesAsignarHorarioRuta`(
IN p_idruta int(11),
IN p_idHorario varchar(45),
OUT success boolean
)
BEGIN
IF EXISTS (SELECT idruta from ruta WHERE idruta=p_idruta)THEN
DELETE FROM `autobus`.`ruta-horario`
WHERE idruta=p_idruta AND idHorario=p_idHorario;

SET success =true;
else
SET success =false;
END IF;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure pa_buscar_chofer
-- -----------------------------------------------------

DELIMITER $$
USE `autobus`$$
CREATE DEFINER=`` PROCEDURE `pa_buscar_chofer`(
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
-- procedure pa_buscar_chofer_Id
-- -----------------------------------------------------

DELIMITER $$
USE `autobus`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_buscar_chofer_Id`(
	IN p_cedula varchar(20),
    OUT p_nombre varchar(30),
	OUT p_apellido varchar(30), 
	OUT p_correo varchar(45), 
	OUT p_telefono int(8),
	OUT p_direccion varchar(45),
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
-- procedure pa_buscar_ruta
-- -----------------------------------------------------

DELIMITER $$
USE `autobus`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_buscar_ruta`(
	IN p_idRuta int(11),
	IN p_lugar_salida varchar(20),
	IN p_lugar_destino varchar(20), 
	IN p_precio float, 
	IN p_asientos_disponibles int(2),
	OUT success boolean
					
	)
BEGIN
IF EXISTS ( SELECT idRuta
					FROM ruta 
					WHERE idRuta = p_idRuta) THEN

SELECT * FROM ruta
WHERE idRuta = p_idRuta;

set success = true;
ELSE 
	SELECT 'Este numero de id de ruta no existe en la base de datos!';
	set success = false;
END IF; 
		
	END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure pa_editar_chofer
-- -----------------------------------------------------

DELIMITER $$
USE `autobus`$$
CREATE DEFINER=`` PROCEDURE `pa_editar_chofer`(
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
-- procedure pa_editar_ruta
-- -----------------------------------------------------

DELIMITER $$
USE `autobus`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_editar_ruta`(
	IN p_idRuta int(11),
	IN p_lugar_salida varchar(20),
	IN p_lugar_destino varchar(20), 
	IN p_precio float, 
	IN p_asientos_disponibles int(2),
	OUT success boolean
					
	)
BEGIN
IF EXISTS ( SELECT idRuta
 FROM ruta 
 WHERE idRuta = p_idRuta) THEN
 
UPDATE ruta set lugar_salida = p_lugar_salida, lugar_destino = p_lugar_destino, precio = p_precio = p_telefono ,asientos_disponibles = p_asientos_disponibles
WHERE idRuta = p_idRuta;

set success = true;
ELSE 

SELECT 'Este numero de id de la ruta no existe en la base de datos!';

set success = false;
END IF; 
		
	END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure pa_eliminar_Bus
-- -----------------------------------------------------

DELIMITER $$
USE `autobus`$$
CREATE DEFINER=`` PROCEDURE `pa_eliminar_Bus`(
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
CREATE DEFINER=`` PROCEDURE `pa_eliminar_chofer`(
   IN p_cedula varchar(7),
	OUT success boolean
)
BEGIN
 IF EXISTS ( SELECT p_cedula
FROM chofer 
WHERE cedula = p_cedula) THEN

DELETE FROM chofer
WHERE cedula = p_cedula;

set success = true;
ELSE 
	SELECT 'Este numero de cedula ingresado no existe en la base de datos!';
	set success = false;
END IF; 

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure pa_eliminar_ruta
-- -----------------------------------------------------

DELIMITER $$
USE `autobus`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_eliminar_ruta`(
	IN p_idRuta int(11),
	OUT success boolean
					
	)
BEGIN
 IF EXISTS ( SELECT p_idRuta
FROM ruta 
WHERE idRuta = p_idRuta) THEN

DELETE FROM ruta
WHERE idRuta = p_idRuta;

set success = true;
ELSE 
	SELECT 'Este numero de id de la ruta no existe en la base de datos!';
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

-- -----------------------------------------------------
-- procedure pa_insertar_ruta
-- -----------------------------------------------------

DELIMITER $$
USE `autobus`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_insertar_ruta`(
	IN p_idRuta int(11),
	IN p_lugar_salida varchar(20),
	IN p_lugar_destino varchar(20), 
	IN p_precio float, 
	IN p_asientos_disponibles int(2),
	OUT success boolean
					
	)
BEGIN

		IF NOT EXISTS ( SELECT idRuta
						FROM ruta 
						WHERE idRuta = p_idRuta) THEN
			
			INSERT INTO ruta(idRuta, lugar_salida, lugar_destino,precio,asientos_disponibles)
						VALUES (p_idRuta, p_lugar_salida, p_lugar_destino, p_precio, p_asientos_disponibles);
			set success = 1;

		ELSE
			SELECT 'idRuta ya existe!';
			set success = 0;
		END IF;
		
	END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure pa_insertar_tiquete
-- -----------------------------------------------------

DELIMITER $$
USE `autobus`$$
CREATE DEFINER=`` PROCEDURE `pa_insertar_tiquete`(
IN p_idTiquete int(11),
IN p_ruta int(11),
IN p_numeroAsiento int(2),
IN p_preferencial tinyint(4),
IN fechaVenta datetime,
OUT success boolean
)
BEGIN
IF NOT EXISTS ( SELECT idTiquete
						FROM tiquetes 
						WHERE idTiquete = p_idTiquete) THEN
			
			INSERT INTO tiquetes(idTiquete, ruta, numeroAsiento,preferencial,fechaVenta)
						VALUES (p_idTiquete, p_ruta, p_numeroAsiento, p_preferencial, fechaVenta);
			set success = 1;

		ELSE
			SELECT 'idTiquete ya existe!';
			set success = 0;
		END IF;
END$$

DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
