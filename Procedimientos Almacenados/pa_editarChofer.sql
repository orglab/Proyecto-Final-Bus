use 'clinica-db'

DELIMITER //
CREATE PROCEDURE pa_editar_chofer
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
 
UPDATE chofer set nombre = p_nombre, apellido = p_apellido, correo = p_correo,telefono = p_telefono ,direccion = p_direccion
WHERE cedula = p_cedula;

set success = true;
ELSE 

SELECT 'Este numero de cédula de chofer no existe en la base de datos!';

set success = false;
END IF; 

END//

DELIMITER ;