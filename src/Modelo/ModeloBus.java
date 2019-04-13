/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import JavaBeans.clsBus;
import JavaBeans.clsChofer;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Manyor
 */
public class ModeloBus {

   public boolean insertarBus(clsBus bus) {

        Modelo.DataBase bd = new Modelo.DataBase();
        try {
            bd.conectar();
            CallableStatement cst
                    = bd.getConexion().prepareCall("{call pa_insertar_bus(?,?,?,?,?,?,?,?)}");
            // Parametro de entrada del procedimiento almacenado
            cst.setInt(1, bus.getIdBus());
            cst.setString(2, bus.getPlaca());
            cst.setString(3, bus.getMarca());
            cst.setString(4, bus.getModelo());
            cst.setInt(5, bus.getAnnio());
            cst.setString(6, bus.getChofer());
            cst.setInt(7, bus.getCapacidad());

            // Definimos los tipos de los parametros de salida del procedimiento almacenado
            cst.registerOutParameter(8, java.sql.Types.BOOLEAN);

            // Ejecuta el procedimiento almacenado
            cst.execute();

            if (cst.getBoolean(8)) {
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

    public clsChofer buscarBus(String chofer) {
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

    public boolean editarBus(clsBus bus) {
        Modelo.DataBase bd = new Modelo.DataBase();
        try {
            bd.conectar();
            CallableStatement cst
                    = bd.getConexion().prepareCall("{call pa_editar_Bus(?,?,?,?,?,?,?,?)}");
            // Parametro de entrada del procedimiento almacenado
            cst.setInt(1, bus.getIdBus());
            cst.setString(2, bus.getPlaca());
            cst.setString(3, bus.getMarca());
            cst.setString(4, bus.getModelo());
            cst.setInt(5, bus.getAnnio());
            cst.setString(6, bus.getChofer());
            cst.setInt(7, bus.getCapacidad());

            // Definimos los tipos de los parametros de salida del procedimiento almacenado
            cst.registerOutParameter(8, java.sql.Types.BOOLEAN);

            // Ejecuta el procedimiento almacenado
            cst.execute();

            if (cst.getBoolean(8)) {
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

    public boolean eliminarBus(String cedula) {

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
            return false;
        } finally {
            // cerrar la conexion
            bd.desconectar();
        }
        return false;
    }
}
