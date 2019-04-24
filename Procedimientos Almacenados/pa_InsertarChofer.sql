CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_insertar_chofer`(
	IN p_cedula varchar(20),
	IN p_nombre varchar(30),
	IN p_apellido varchar(30), 
	IN p_correo varchar(45), 
	IN p_telefono int(8),
	IN p_direccion varchar(45),
	OUT success boolean
					
	)
BEGIN

		IF NOT EXISTS ( SELECT cedula
						FROM chofer 
						WHERE cedula = p_cedula) THEN
			
			INSERT INTO chofer(cedula, nombre, apellido,correo,telefono,direccion)
						VALUES (p_cedula, p_nombre, p_apellido, p_correo, p_telefono, p_direccion);
			set success = 1;

		ELSE
			SELECT 'Cédula ya existe!';
			set success = 0;
		END IF;
		
	END