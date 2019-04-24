
DELIMITER //
CREATE PROCEDURE pa_buscar_chofer

(
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

END//

DELIMITER ;