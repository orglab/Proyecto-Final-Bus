/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import JavaBeans.clsChofer;
import Modelo.ModeloChofer;
import Vista.frmAgregarChofer;
import Vista.frmModuloChofer;
import Vista.frmPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.DataBase;

/**
 *
 * @author JJGB
 */
public class ChoferesControlador implements ActionListener, WindowListener {

    private frmModuloChofer moduloChofer;
    public frmPrincipal moduloPrincipal;
    private frmAgregarChofer agregarChofer;
    private clsChofer chofer;
    private ModeloChofer modeloChofer;

    DefaultTableModel modelT;

    public ChoferesControlador(frmModuloChofer modChofer, frmPrincipal modPrincipal, frmAgregarChofer agChofer, clsChofer chofer, ModeloChofer modelChofer) {
        this.moduloChofer = modChofer;
        this.moduloPrincipal = modPrincipal;
        this.agregarChofer = agChofer;
        this.chofer = chofer;
        this.modeloChofer = modelChofer;

        //Listener de la  de choferes
        //Listener para los Botones mapeados en la vista
        this.moduloPrincipal.btnModChoferes.addActionListener(this);

        // Botones mapeados para el la vista del chofer
        this.moduloChofer.btnAgregar.addActionListener(this);
        this.moduloChofer.btnEditar.addActionListener(this);
        this.moduloChofer.btnEliminar.addActionListener(this);
        this.moduloChofer.addWindowListener(this);

        //Botones mapeados con Listener modulo Agregar Chofer Vista
        this.agregarChofer.btnGuardar.addActionListener(this);
        this.agregarChofer.btnCancelar.addActionListener(this);
        this.agregarChofer.btnLimpiar.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == moduloPrincipal.btnModChoferes) {
            moduloChofer.setVisible(true);
        }
        if (e.getSource() == moduloChofer.btnAgregar) {
            agregarChofer.setVisible(true);
            agregarChofer.setTitle("AgregarChofer");

        }
        if (e.getSource() == agregarChofer.btnGuardar) {
            chofer.setCedula(agregarChofer.txtCedulaChofer.getText());
            chofer.setNombre(agregarChofer.txtNombreChofer.getText());
            chofer.setApellido(agregarChofer.txtApellidoChofer.getText());
            chofer.setCorreo(agregarChofer.txtCorreoChofer.getText());
            chofer.setTelefono(Integer.parseInt(agregarChofer.txtTelChofer.getText()));
            chofer.setDireccion(agregarChofer.txtDireccionChofer.getText());

            if (modeloChofer.insertarChofer(chofer)) {
                JOptionPane.showMessageDialog(agregarChofer, "Chofer Registrado");

                limpiarVistaNuevo();
            } else {
                JOptionPane.showMessageDialog(agregarChofer, "Error al guardar");
            }

        }

        if (e.getSource() == agregarChofer.btnLimpiar) {
            limpiarVistaNuevo();
        }
        if (e.getSource() == agregarChofer.btnCancelar) {
            agregarChofer.dispose();
        }

    }

    @Override
    public void windowActivated(WindowEvent we) {
        String titulos[] = {"Cédula", "Nombre", "Apellido", "Correo", "Teléfono", "Dirección"};
        modelT = new DefaultTableModel(null, titulos);
        DataBase bd = new DataBase();

        ResultSet rs;

        try {
            bd.ejecutarSqlSelect("select chofer.* from chofer");
            rs = bd.obtenerRegistro();
            do {
                chofer = new clsChofer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6));
                Object newRow[] = {chofer.getCedula(), chofer.getNombre(), chofer.getApellido(), chofer.getCorreo(), chofer.getTelefono(), chofer.getDireccion()};
                modelT.addRow(newRow);

            } while (rs.next());
            moduloChofer.tblChoferes.setModel(modelT);
            //moduloChofer.lblRegistros.setText("Cantidad de Registros: " + modelT.getRowCount());

        } catch (SQLException ex) {
            //////
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        //      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosed(WindowEvent e) {
        //       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        //      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        //      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void limpiarVistaNuevo() {
        agregarChofer.txtCedulaChofer.setText(null);
        agregarChofer.txtDireccionChofer.setText(null);
        agregarChofer.txtApellidoChofer.setText(null);
        agregarChofer.txtTelChofer.setText(null);
        agregarChofer.txtNombreChofer.setText(null);
        agregarChofer.txtCorreoChofer.setText(null);
    }

}
