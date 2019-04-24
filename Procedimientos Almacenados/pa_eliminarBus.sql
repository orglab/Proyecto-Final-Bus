
DELIMITER //
CREATE PROCEDURE pa_eliminar_Bus
(
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

END//

DELIMITER ;