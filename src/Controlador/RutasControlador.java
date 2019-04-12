/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import JavaBeans.clsRuta;
import Modelo.ModeloRuta;
import Vista.frmAgregarRuta;
import Vista.frmModuloRuta;
import Vista.frmPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Modelo.DataBase;

/**
 *
 * @author JJGB
 */
public class RutasControlador implements ActionListener, WindowListener, KeyListener {

    private frmModuloRuta moduloRuta;
    public frmPrincipal moduloPrincipal;
    private frmAgregarRuta agregarRuta;
    private clsRuta ruta;
    private ModeloRuta modeloRuta;

    DefaultTableModel modelT;

    public RutasControlador(frmModuloRuta modRuta, frmPrincipal modPrincipal, frmAgregarRuta agRuta, clsRuta ruta, ModeloRuta modelRuta) {
        this.moduloRuta = modRuta;
        this.moduloPrincipal = modPrincipal;
        this.agregarRuta = agRuta;
        this.ruta = ruta;
        this.modeloRuta = modelRuta;

        //Listener de la  de choferes
        //Listener para los Botones mapeados en la vista
        this.moduloPrincipal.btnModChoferes.addActionListener(this);

        // Botones mapeados para el la vista de la ruta
        this.moduloRuta.btnAgregar.addActionListener(this);
        this.moduloRuta.btnEditar.addActionListener(this);
        this.moduloRuta.btnEliminar.addActionListener(this);
        this.moduloRuta.addWindowListener(this);
        this.moduloRuta.txtBuscar.addKeyListener(this);

        //Botones mapeados con Listener modulo Agregar Ruta Vista
        this.agregarRuta.btnGuardar.addActionListener(this);
        this.agregarRuta.btnCancelar.addActionListener(this);
        this.agregarRuta.btnLimpiar.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Acción para el botón que abre el módulo del chofer. 
        if (e.getSource() == moduloPrincipal.btnModRutas) {
            moduloRuta.setTitle("Modulo de Rutas");
            moduloRuta.setVisible(true);
        }
        // Acción para el botón que abre el módulo del agregar chofer. 
        if (e.getSource() == moduloRuta.btnAgregar) {
            agregarRuta.setVisible(true);
            agregarRuta.setTitle("Agregar Ruta");
        }
        // Acción para el botón guardar dentro del form agregarchofer chofer. 

        if (e.getSource() == agregarRuta.btnGuardar) {

            if (validarTexto()) {
                ruta.setIdruta(Integer.parseInt(agregarRuta.txtIdRuta.getText()));
                ruta.setLugarSalida(agregarRuta.txtLugarSalida.getText());
               ruta.setLugarDestino(agregarRuta.txtLugarDestino.getText());
                ruta.setPrecio(Float.parseFloat(agregarRuta.txtPrecio.getText()));
                ruta.setAsientos_disponibles(Integer.parseInt(agregarRuta.txtAsientosDisp.getText()));
            } else {
                JOptionPane.showMessageDialog(agregarRuta, "Debe completar los campos solicitados");

            }

            if (agregarRuta.getTitle().equals("Agregar Ruta")) {
                //Agregar chofer
                if (modeloRuta.insertarRuta(ruta)) {
                    JOptionPane.showMessageDialog(agregarRuta, "Ruta Registrada");
                    limpiarVistaNuevo();
                } else {
                    JOptionPane.showMessageDialog(agregarRuta, "Error al guardar");
                }
            } else {
                //Editar CHofer
                modeloRuta.editarRuta(ruta);
                JOptionPane.showMessageDialog(agregarRuta, "Ruta Editada");
            }
        }
        // Acción para el botón que limpia  la pantalla en el form agregarchofer. 
        if (e.getSource() == agregarRuta.btnLimpiar) {
            limpiarVistaNuevo();
        }
        // Acción para el botón que cancela el modo de agregación del chofer. 
        if (e.getSource() == agregarRuta.btnCancelar) {
            agregarRuta.dispose();
        }

        if (e.getSource().equals(moduloRuta.btnEditar)) {
            if (moduloRuta.tblDatos.getSelectedRowCount() > 0) {
                agregarRuta.setTitle("Editar Ruta");
                
                ruta = modeloRuta.buscarRuta(moduloRuta.tblDatos.getValueAt(moduloRuta.tblDatos.getSelectedRow(), 0).toString());
                
                agregarRuta.txtIdRuta.setText(String.valueOf(ruta.getIdruta()));
                agregarRuta.txtIdRuta.setEnabled(false);
                agregarRuta.txtLugarSalida.setText(ruta.getLugarSalida());
                agregarRuta.txtLugarDestino.setText(ruta.getLugarDestino());
                agregarRuta.txtPrecio.setText(String.valueOf(ruta.getPrecio()));
                agregarRuta.txtAsientosDisp.setText(String.valueOf(ruta.getAsientos_disponibles()));
                
                agregarRuta.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(agregarRuta, "Debe seleccionar al menos una fila para editar");
            }
        }
        if (e.getSource().equals(moduloRuta.btnEliminar)) {
            if (moduloRuta.tblDatos.getSelectedRowCount() > 0) {
                int opcion = JOptionPane.showConfirmDialog(null, "Está seguro que desea eliminar ese registro ?");
                if (opcion == 0) {
                    if (modeloRuta.eliminarRuta(Integer.parseInt(moduloRuta.tblDatos.getValueAt(moduloRuta.tblDatos.getSelectedRow(), 0).toString()))) {
                        JOptionPane.showMessageDialog(null, "Registro eliminado exitosamente");
                    } else {
                        System.out.println("No Elimnars");
                    }
                } else {
                    JOptionPane.showMessageDialog(agregarRuta, "Debe seleccionar al menos una fila para eliminar");
                }
            }
        }

    }

    @Override
    public void windowActivated(WindowEvent we
    ) {
        String titulos[] = {"ID Ruta", "Lugar Salida", "Lugar Destino", "Precio", "Asientos Disponibles"};
        modelT = new DefaultTableModel(null, titulos);
        DataBase bd = new DataBase();

        ResultSet rs;

        try {
            bd.ejecutarSqlSelect("select * from ruta");
            rs = bd.obtenerRegistro();
            do {
                ruta = new clsRuta(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getInt(5));
                Object newRow[] = {ruta.getIdruta(), ruta.getLugarSalida(), ruta.getLugarDestino(), ruta.getPrecio(), ruta.getAsientos_disponibles()};
                modelT.addRow(newRow);

            } while (rs.next());
            moduloRuta.tblDatos.setModel(modelT);
            //moduloChofer.lblRegistros.setText("Cantidad de Registros: " + modelT.getRowCount());

        } catch (SQLException ex) {
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    public void limpiarVistaNuevo() {
        agregarRuta.txtIdRuta.setText(null);
        agregarRuta.txtLugarSalida.setText(null);
        agregarRuta.txtLugarDestino.setText(null);
        agregarRuta.txtPrecio.setText(null);
        agregarRuta.txtAsientosDisp.setText(null);
    }

    private boolean validarTexto() {
        return !agregarRuta.txtIdRuta.getText().isEmpty()
                && !agregarRuta.txtLugarSalida.getText().isEmpty()
                && !agregarRuta.txtLugarDestino.getText().isEmpty()
                && !agregarRuta.txtPrecio.getText().isEmpty()
                && !agregarRuta.txtAsientosDisp.getText().isEmpty();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
           String titulos[] = {"ID Ruta", "Lugar Salida", "Lugar Destino", "Precio", "Asientos Disponibles"};
        modelT = new DefaultTableModel(null, titulos);
        DataBase bd = new DataBase();

        ResultSet rs;

        try {
            bd.ejecutarSqlSelect("select * from ruta where idruta like '%" + moduloRuta.txtBuscar.getText().trim() +"%' OR precio like '%" + moduloRuta.txtBuscar.getText().trim() + "%'");
            rs = bd.obtenerRegistro();
            do {
                ruta = new clsRuta(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getInt(5));
                Object newRow[] = {ruta.getIdruta(), ruta.getLugarSalida(), ruta.getLugarDestino(), ruta.getPrecio(), ruta.getAsientos_disponibles()};;
                modelT.addRow(newRow);

            } while (rs.next());
            moduloRuta.tblDatos.setModel(modelT);
            //frmA.lblRegistros.setText("Cantidad de Registros: " + modelT.getRowCount());

        } catch (SQLException ex) {
            //////
        }
    }

}
