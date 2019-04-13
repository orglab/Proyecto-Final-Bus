/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import JavaBeans.clsChofer;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Manyor
 */
public class ModeloChofer {

    public boolean insertarChofer(clsChofer chofer) {

        Modelo.DataBase bd = new Modelo.DataBase();
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
            System.out.println(e);
            return false;
        } finally {
            // cerrar la conexion
            bd.desconectar();
        }
        return false;
    }

    public clsChofer buscarChofer(String chofer) {
        clsChofer chfer = new clsChofer();
        Modelo.DataBase bd = new Modelo.DataBase();
        ResultSet rs;

        try {
            bd.ejecutarSqlSelect("SELECT * FROM chofer WHERE cedula = " + chofer + ";");
            rs = bd.obtenerRegistro();
            do {
                chfer = new clsChofer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6));
            } while (rs.next());

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return chfer;
    }

    public boolean editarChofer(clsChofer chofer) {

        Modelo.DataBase bd = new Modelo.DataBase();
        try {
            bd.conectar();
            CallableStatement cst
                    = bd.getConexion().prepareCall("{call pa_editar_chofer(?,?,?,?,?,?,?)}");
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
    
    public boolean eliminarChofer(String cedula) {

        Modelo.DataBase bd = new Modelo.DataBase();
        try {
            bd.conectar();
            CallableStatement cst
                    = bd.getConexion().prepareCall("{call pa_eliminar_chofer(?,?)}");
            // Parametro de entrada del procedimiento almacenado
            cst.setString(1, cedula);
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
