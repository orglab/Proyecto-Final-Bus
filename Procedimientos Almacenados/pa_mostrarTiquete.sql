DELIMITER $$
CREATE PROCEDURE `pa_mostrar_tiquetes` (

)
BEGIN
SELECT ruta.lugar_salida,ruta.lugar_destino,tiquetes.ruta, tiquetes.numero_asiento,tiquetes.preferencial,tiquetes.fecha_venta,ruta.precio FROM tiquetes 
inner join ruta on ruta.idruta = tiquetes.ruta;
END