/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import JavaBeans.clsBus;
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

    public clsBus buscarBus(String bus) {
        clsBus autobus = new clsBus();
        Modelo.DataBase bd = new Modelo.DataBase();
        ResultSet rs;

        try {
            bd.ejecutarSqlSelect("SELECT * FROM bus WHERE idBus = " + bus + ";");
            rs = bd.obtenerRegistro();
            do {
                autobus = new clsBus(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6),rs.getInt(7));
            } while (rs.next());

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return autobus;
    }

    public boolean editarBus(clsBus bus) {
        Modelo.DataBase bd = new Modelo.DataBase();
        try {
            bd.conectar();
            CallableStatement cst
                    = bd.getConexion().prepareCall("{call pa_editar_Bus(?,?,?,?,?,?,?)}");
            // Parametro de entrada del procedimiento almacenado
            cst.setString(1, bus.getPlaca());
            cst.setString(2, bus.getMarca());
            cst.setString(3, bus.getModelo());
            cst.setInt(4, bus.getAnnio());
            cst.setString(5, bus.getChofer());
            cst.setInt(6, bus.getCapacidad());

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

    public boolean eliminarBus(String placa) {

        Modelo.DataBase bd = new Modelo.DataBase();
        try {
            bd.conectar();
            CallableStatement cst
                    = bd.getConexion().prepareCall("{call pa_eliminar_Bus(?,?)}");
            // Parametro de entrada del procedimiento almacenado
            cst.setString(1, placa);
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
