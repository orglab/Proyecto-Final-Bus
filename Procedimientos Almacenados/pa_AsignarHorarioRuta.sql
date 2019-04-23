DELIMITER $$
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
DELIMITER 