DELIMITER $$
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
		
	END