/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import JavaBeans.clsRuta;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Manyor
 */
public class ModeloRuta {

    public boolean insertarRuta(clsRuta ruta) {

        Modelo.DataBase bd = new Modelo.DataBase();
        try {
            bd.conectar();
            CallableStatement cst
                    = bd.getConexion().prepareCall("{call pa_insertar_ruta(?,?,?,?,?,?)}");
            // Parametro de entrada del procedimiento almacenado
            cst.setInt(1, ruta.getIdruta());
            cst.setString(2, ruta.getLugarSalida());
            cst.setString(3, ruta.getLugarDestino());
            cst.setFloat(4, ruta.getPrecio());
            cst.setInt(5, ruta.getAsientos_disponibles());

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

    public clsRuta buscarRuta(String ruta) {
        clsRuta rut = new clsRuta();
        Modelo.DataBase bd = new Modelo.DataBase();
        ResultSet rs;

        try {
            bd.ejecutarSqlSelect("SELECT * FROM ruta WHERE idruta = " + ruta + ";");
            rs = bd.obtenerRegistro();
            do {
                rut = new clsRuta(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getInt(5));
            } while (rs.next());

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return rut;
    }

    public boolean editarRuta(clsRuta ruta) {

        Modelo.DataBase bd = new Modelo.DataBase();
        try {
            bd.conectar();
            CallableStatement cst
                    = bd.getConexion().prepareCall("{call pa_editar_ruta(?,?,?,?,?,?)}");
            // Parametro de entrada del procedimiento almacenado
            cst.setInt(1, ruta.getIdruta());
            cst.setString(2, ruta.getLugarSalida());
            cst.setString(3, ruta.getLugarDestino());
            cst.setFloat(4, ruta.getPrecio());
            cst.setInt(5, ruta.getAsientos_disponibles());

            // Definimos los tipos de los parametros de salida del procedimiento almacenado
            cst.registerOutParameter(6, java.sql.Types.BOOLEAN);

            // Ejecuta el procedimiento almacenado
            cst.execute();

            if (cst.getBoolean(6)) {
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
    
    public boolean eliminarRuta(int idRuta) { // corregir por int en vez de String

        Modelo.DataBase bd = new Modelo.DataBase();
        try {
            bd.conectar();
            CallableStatement cst
                    = bd.getConexion().prepareCall("{call pa_eliminar_ruta(?,?)}");
            // Parametro de entrada del procedimiento almacenado
            cst.setInt(1, idRuta);
            // Definimos los tipos de los parametros de salida del procedimiento almacenado
            cst.registerOutParameter(2, java.sql.Types.BOOLEAN);

            // Ejecuta el procedimiento almacenado
            cst.execute();

            if (cst.getBoolean(2)) {
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