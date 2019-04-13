Delimiter $$
CREATE DEFINER=`root`@`local` PROCEDURE `pa_insertar_bus`(
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
	END $$