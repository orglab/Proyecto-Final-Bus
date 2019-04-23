DELIMITER $$
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
DELIMITER 