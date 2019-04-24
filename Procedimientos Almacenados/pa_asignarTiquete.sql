DELIMITER $$
CREATE PROCEDURE `pa_insertar_tiquete` (
IN p_idTiquete int(11),
IN p_ruta int(11),
IN p_numeroAsiento int(2),
IN p_preferencial tinyint(4),
IN fechaVenta datetime,
OUT success boolean
)
BEGIN
IF NOT EXISTS ( SELECT idtiquetes
						FROM tiquetes 
						WHERE idtiquetes = p_idTiquete) THEN
			
			INSERT INTO tiquetes(idtiquetes, ruta, numero_asiento,preferencial,fecha_venta)
						VALUES (p_idTiquete, p_ruta, p_numeroAsiento, p_preferencial, fechaVenta);
			set success = 1;

		ELSE
			SELECT 'idTiquete ya existe!';
			set success = 0;
		END IF;
END