DELIMITER $$
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
		
	END