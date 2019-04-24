/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import JavaBeans.clsTiquete;
import java.sql.CallableStatement;
import java.sql.SQLException;

/**
 *
 * @author JJGB
 */
public class ModeloTiquete {
    public boolean insertarTiquete(clsTiquete tiq) {

        Modelo.DataBase bd = new Modelo.DataBase();
        try {
            bd.conectar();
            CallableStatement cst
                    = bd.getConexion().prepareCall("{call pa_insertar_tiquete(?,?,?,?,?,?)}");
            // Parametro de entrada del procedimiento almacenado
            cst.setInt(1, tiq.getIdTiquete());
            cst.setInt(2, tiq.getRuta());
            cst.setInt(3, tiq.getNumAsiento());
            cst.setInt(4, tiq.getPreferencial());
            cst.setDate(5, tiq.getFechaVenta());
                      // Definimos los tipos de los parametros de salida del procedimiento almacenado
            cst.registerOutParameter(6, java.sql.Types.BOOLEAN);

            // Ejecuta el procedimiento almacenado
            cst.execute();

            if (cst.getBoolean(6)) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println(e);
            return false;
        } finally {
            // cerrar la conexion
            bd.desconectar();
        }
        return false;
    }
}
