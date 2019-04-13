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
public class ChoferesControlador implements ActionListener, WindowListener, KeyListener {

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
        this.moduloChofer.txtBuscar.addKeyListener(this);

        //Botones mapeados con Listener modulo Agregar Chofer Vista
        this.agregarChofer.btnGuardar.addActionListener(this);
        this.agregarChofer.btnCancelar.addActionListener(this);
        this.agregarChofer.btnLimpiar.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Acción para el botón que abre el módulo del chofer. 
        if (e.getSource() == moduloPrincipal.btnModChoferes) {
            moduloChofer.setVisible(true);
        }
        // Acción para el botón que abre el módulo del agregar chofer. 
        if (e.getSource() == moduloChofer.btnAgregar) {
            agregarChofer.setVisible(true);
            agregarChofer.setTitle("AgregarChofer");
        }
        // Acción para el botón guardar dentro del form agregarchofer chofer. 

        if (e.getSource() == agregarChofer.btnGuardar) {

            if (validarTexto()) {
                chofer.setCedula(agregarChofer.txtCedulaChofer.getText());
                chofer.setNombre(agregarChofer.txtNombreChofer.getText());
                chofer.setApellido(agregarChofer.txtApellidoChofer.getText());
                chofer.setCorreo(agregarChofer.txtCorreoChofer.getText());
                chofer.setTelefono(Integer.parseInt(agregarChofer.txtTelChofer.getText()));
                chofer.setDireccion(agregarChofer.txtDireccionChofer.getText());
            } else {
                JOptionPane.showMessageDialog(agregarChofer, "Debe completar los campos solicitados");

            }

            if (agregarChofer.getTitle().equals("AgregarChofer")) {
                //Agregar chofer
                if (modeloChofer.insertarChofer(chofer)) {
                    JOptionPane.showMessageDialog(agregarChofer, "Chofer Registrado");
                    limpiarVistaNuevo();
                } else {
                    JOptionPane.showMessageDialog(agregarChofer, "Error al guardar");
                }
            } else {
                //Editar CHofer
                modeloChofer.editarChofer(chofer);
                JOptionPane.showMessageDialog(agregarChofer, "Chofer Editado");
            }
        }
        // Acción para el botón que limpia  la pantalla en el form agregarchofer. 
        if (e.getSource() == agregarChofer.btnLimpiar) {
            limpiarVistaNuevo();
        }
        // Acción para el botón que cancela el modo de agregación del chofer. 
        if (e.getSource() == agregarChofer.btnCancelar) {
            agregarChofer.dispose();
        }

        if (e.getSource().equals(moduloChofer.btnEditar)) {
            if (moduloChofer.tblChoferes.getSelectedRowCount() > 0) {
                agregarChofer.setTitle("Editar Chofer");

                chofer = modeloChofer.buscarChofer(moduloChofer.tblChoferes.getValueAt(moduloChofer.tblChoferes.getSelectedRow(), 0).toString());

                agregarChofer.txtCedulaChofer.setText(chofer.getCedula());
                agregarChofer.txtCedulaChofer.setEnabled(false);
                agregarChofer.txtNombreChofer.setText(chofer.getNombre());
                agregarChofer.txtApellidoChofer.setText(chofer.getApellido());
                agregarChofer.txtCorreoChofer.setText(chofer.getCorreo());
                agregarChofer.txtTelChofer.setText(String.valueOf(chofer.getTelefono()));
                agregarChofer.txtDireccionChofer.setText(chofer.getDireccion());

                agregarChofer.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(agregarChofer, "Debe seleccionar al menos una fila para editar");
            }
        }
        if (e.getSource().equals(moduloChofer.btnEliminar)) {
            if (moduloChofer.tblChoferes.getSelectedRowCount() > 0) {
                int opcion = JOptionPane.showConfirmDialog(null, "Está seguro que desea eliminar ese registro ?");
                if (opcion == 0) {
                    if (modeloChofer.eliminarChofer(moduloChofer.tblChoferes.getValueAt(moduloChofer.tblChoferes.getSelectedRow(), 1).toString())) {
                        JOptionPane.showMessageDialog(null, "Registro eliminado exitosamente");
                    } else {
                        System.out.println("No Elimnars");
                    }
                } else {
                    JOptionPane.showMessageDialog(agregarChofer, "Debe seleccionar al menos una fila para eliminar");
                }
            }
        }

    }

    @Override
    public void windowActivated(WindowEvent we
    ) {
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
        agregarChofer.txtCedulaChofer.setText(null);
        agregarChofer.txtDireccionChofer.setText(null);
        agregarChofer.txtApellidoChofer.setText(null);
        agregarChofer.txtTelChofer.setText(null);
        agregarChofer.txtNombreChofer.setText(null);
        agregarChofer.txtCorreoChofer.setText(null);
    }

    private boolean validarTexto() {
        return !agregarChofer.txtCedulaChofer.getText().isEmpty()
                && !agregarChofer.txtNombreChofer.getText().isEmpty()
                && !agregarChofer.txtApellidoChofer.getText().isEmpty()
                && !agregarChofer.txtCorreoChofer.getText().isEmpty()
                && !agregarChofer.txtTelChofer.getText().isEmpty()
                && !agregarChofer.txtDireccionChofer.getText().isEmpty();

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        String titulos[] = {"cédula", "Nombre", "Apellido", "Correo", "Teléfono", "Dirección"};
        modelT = new DefaultTableModel(null, titulos);
        DataBase bd = new DataBase();

        ResultSet rs;

        try {
            //bd.ejecutarSqlSelect("select * from bus where placa like '%" + txtFiltro.getText().trim() +"%' OR modelo like '%" + txtFiltro.getText().trim() + "%'");
            bd.ejecutarSqlSelect("select * from chofer where cedula like '%" + moduloChofer.txtBuscar.getText().trim() + "%' OR nombre like '%" + moduloChofer.txtBuscar.getText().trim() + "%'OR apellido like '%" + moduloChofer.txtBuscar.getText().trim() + "%'");
            rs = bd.obtenerRegistro();
            do {
                chofer = new clsChofer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6));
                Object newRow[] = {chofer.getCedula(), chofer.getNombre(), chofer.getApellido(), chofer.getCorreo(), chofer.getTelefono(), chofer.getDireccion()};
                modelT.addRow(newRow);

            } while (rs.next());
            moduloChofer.tblChoferes.setModel(modelT);
            //frmA.lblRegistros.setText("Cantidad de Registros: " + modelT.getRowCount());

        } catch (SQLException ex) {
            //////
        }
    }

}
