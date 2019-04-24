/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import JavaBeans.clsTiquete;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

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
    
    public DefaultTableModel mostrarTiquetesVendidos() {
        DataBase bd = new DataBase();
        String titulos[] = {"Lugar Salida", "Lugar Destino", "Ruta", "numero Asiento", "Preferencial", "Fecha Venta", "Precio"};
        DefaultTableModel modelo = new DefaultTableModel(null, titulos);
        try {
            bd.conectar();
            CallableStatement cst = bd.getConexion().prepareCall("{call pa_mostrar_tiquetes()}");
            cst.execute();
            ResultSet rs = cst.getResultSet();
            while (rs.next()) {

                Object newRow[] = {rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getString(6),
                    rs.getFloat(7)};
                modelo.addRow(newRow);

            }
        } catch (SQLException e) {

        } finally {
            // cerrar la conexion
            bd.desconectar();
        }
        return modelo;
    }
}
