

DELIMITER //
CREATE PROCEDURE pa_eliminar_chofer
(
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

END//

DELIMITER ;