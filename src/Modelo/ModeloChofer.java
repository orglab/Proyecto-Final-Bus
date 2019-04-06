/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;
import JavaBeans.clsChofer;
import java.sql.CallableStatement;
import java.sql.SQLException;

/**
 *
 * @author Manyor
 */
public class ModeloChofer {
    public boolean insertarChofer(clsChofer chofer) {

        modelo.DataBase bd = new modelo.DataBase();
        try {
            bd.conectar();
            CallableStatement cst
                    = bd.getConexion().prepareCall("{call pa_insertar_chofer(?,?,?,?,?,?,?)}");
            // Parametro de entrada del procedimiento almacenado
            cst.setString(1, chofer.getCedula());
            cst.setString(2, chofer.getNombre());
            cst.setString(3, chofer.getApellido());
            cst.setString(4, chofer.getCorreo());
            cst.setInt(5, chofer.getTelefono());
            cst.setString(6, chofer.getDireccion());

            // Definimos los tipos de los parametros de salida del procedimiento almacenado
            cst.registerOutParameter(7, java.sql.Types.BOOLEAN);

            // Ejecuta el procedimiento almacenado
            cst.execute();

            if (cst.getBoolean(7)) {
                return true;
            }

        } catch (SQLException e) {
            return false;
        } finally {
            // cerrar la conexion
            bd.desconectar();
        }
        return false;
    }
    
}
