/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import JavaBeans.clsRuta;
import java.sql.CallableStatement;
import java.sql.SQLException;

/**
 *
 * @author Manyor
 */
public class ModeloHorariosRuta {
     public boolean insertarHorarioRuta(int ruta, int horario) {

        Modelo.DataBase bd = new Modelo.DataBase();
        try {
            bd.conectar();
            CallableStatement cst
                    = bd.getConexion().prepareCall("{call pa_AsignarHorarioRuta(?,?,?)}");
            // Parametro de entrada del procedimiento almacenado
            cst.setInt(1, ruta);
            cst.setInt(2, horario);
                      // Definimos los tipos de los parametros de salida del procedimiento almacenado
            cst.registerOutParameter(3, java.sql.Types.BOOLEAN);

            // Ejecuta el procedimiento almacenado
            cst.execute();

            if (cst.getBoolean(3)) {
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
     public boolean desAsignarHorarioRuta(int ruta, int horario) {

        Modelo.DataBase bd = new Modelo.DataBase();
        try {
            bd.conectar();
            CallableStatement cst
                    = bd.getConexion().prepareCall("{call pa_DesAsignarHorarioRuta(?,?,?)}");
            // Parametro de entrada del procedimiento almacenado
            cst.setInt(1, ruta);
            cst.setInt(2, horario);
                      // Definimos los tipos de los parametros de salida del procedimiento almacenado
            cst.registerOutParameter(3, java.sql.Types.BOOLEAN);

            // Ejecuta el procedimiento almacenado
            cst.execute();

            if (cst.getBoolean(3)) {
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
