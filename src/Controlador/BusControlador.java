/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import JavaBeans.clsBus;
import Modelo.DataBase;
import Modelo.ModeloBus;
import Vista.frmAgregarBus;
import Vista.frmModuloBuses;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Manyor
 */
public class BusControlador implements ActionListener, WindowListener, KeyListener {

    private frmModuloBuses moduloBus;
    public frmPrincipal moduloPrincipal;
    private frmAgregarBus agregarBus;
    private clsBus bus;
    private ModeloBus modeloBus;

    DefaultTableModel modelT;

    public BusControlador(frmModuloBuses modBus, frmPrincipal modPrincipal, frmAgregarBus agBus, clsBus bus, ModeloBus modelBus) {
        this.moduloBus = modBus;
        this.moduloPrincipal = modPrincipal;
        this.agregarBus = agBus;
        this.bus = bus;
        this.modeloBus = modelBus;

        //Listener de la  de choferes
        //Listener para los Botones mapeados en la vista
        this.moduloPrincipal.btnModChoferes.addActionListener(this);
        // Botones mapeados para el la vista del chofer
        this.moduloBus.btnAgregar.addActionListener(this);
        this.moduloBus.btnEditar.addActionListener(this);
        this.moduloBus.btnEliminar.addActionListener(this);
        this.moduloBus.addWindowListener(this);
        this.moduloBus.txtBuscar.addKeyListener(this);

        //Botones mapeados con Listener modulo Agregar Chofer Vista
        this.agregarBus.btnGuardar.addActionListener(this);
        this.agregarBus.btnCancelar.addActionListener(this);
        this.agregarBus.btnLimpiar.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == moduloPrincipal.btnModBuses) {
            moduloBus.setVisible(true);
        }
        // Acción para el botón que abre el módulo del agregar chofer. 
        if (e.getSource() == moduloBus.btnAgregar) {
            agregarBus.setVisible(true);
            agregarBus.setTitle("AgregarBus");
            agregarBus.txtId.setText(String.valueOf(moduloBus.tblDatos.getModel().getRowCount() + 1));
            cargarComboBus();

        }
        // Acción para el botón guardar dentro del form agregarchofer chofer. 
        if (e.getSource() == agregarBus.btnGuardar) {
            if (validarTexto()) {
                String[] parts = agregarBus.cmbChoferBus.getSelectedItem().toString().split(" ");
                bus.setIdBus(Integer.parseInt(agregarBus.txtId.getText()));
                bus.setPlaca(agregarBus.txtPlaca.getText());
                bus.setMarca(agregarBus.txtMarca.getText());
                bus.setModelo(agregarBus.txtModelo.getText());
                bus.setAnnio(Integer.parseInt(agregarBus.txtAnio.getText()));
                bus.setChofer(parts[0]);
                bus.setCapacidad(Integer.parseInt(agregarBus.txtCapacidad.getText()));
            } else {
                JOptionPane.showMessageDialog(agregarBus, "Debe completar los campos solicitados");

            }

            if (agregarBus.getTitle().equals("AgregarBus")) {
                //Agregar chofer
                if (modeloBus.insertarBus(bus)) {
                    JOptionPane.showMessageDialog(agregarBus, "Bus Registrado");
                    cargarComboBus();
                    limpiarVistaNuevo();
                } else {
                    JOptionPane.showMessageDialog(agregarBus, "Error al guardar");
                }
            } else {
                //Editar CHofer
                modeloBus.editarBus(bus);
                JOptionPane.showMessageDialog(agregarBus, "Bus agrado con exito");
            }
        }
        // Acción para el botón que limpia  la pantalla en el form agregarchofer. 
        if (e.getSource() == agregarBus.btnLimpiar) {
            limpiarVistaNuevo();
        }
//        // Acción para el botón que cancela el modo de agregación del chofer. 
        if (e.getSource() == agregarBus.btnCancelar) {
            agregarBus.dispose();
        }

        if (e.getSource().equals(moduloBus.btnEditar)) {
            if (moduloBus.tblDatos.getSelectedRowCount() > 0) {
                agregarBus.setTitle("Editar Bus");

                bus = modeloBus.buscarBus(moduloBus.tblDatos.getValueAt(moduloBus.tblDatos.getSelectedRow(), 0).toString());

                agregarBus.txtId.setText(String.valueOf(bus.getIdBus()));
                agregarBus.txtPlaca.setText(bus.getPlaca());
                agregarBus.txtPlaca.setEnabled(false);
                agregarBus.txtMarca.setText(bus.getMarca());
                agregarBus.txtModelo.setText(bus.getModelo());
                agregarBus.txtAnio.setText(String.valueOf(bus.getAnnio()));
                agregarBus.cmbChoferBus.setSelectedItem(String.valueOf(bus.getChofer()));
                agregarBus.txtCapacidad.setText(String.valueOf(bus.getCapacidad()));
                cargarComboBus();
                agregarBus.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(agregarBus, "Debe seleccionar al menos una fila para editar");
            }
        }
        if (e.getSource().equals(moduloBus.btnEliminar)) {
            if (moduloBus.tblDatos.getSelectedRowCount() > 0) {
                int opcion = JOptionPane.showConfirmDialog(null, "Está seguro que desea eliminar ese registro ?");
                if (opcion == 0) {
                    if (modeloBus.eliminarBus(moduloBus.tblDatos.getValueAt(moduloBus.tblDatos.getSelectedRow(), 1).toString())) {
                        JOptionPane.showMessageDialog(null, "Registro eliminado exitosamente");
                    } else {
                        System.out.println("No Elimnars");
                    }
                } else {
                    JOptionPane.showMessageDialog(agregarBus, "Debe seleccionar al menos una fila para eliminar");
                }
            }
        }

    }

    @Override
    public void windowOpened(WindowEvent e) {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosed(WindowEvent e) {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent e) {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String titulos[] = {"ID", "#Placa", "Marca", "Modelo", "AÑO", "Identificacion Conductor", "Capacidad"};
        modelT = new DefaultTableModel(null, titulos);
        DataBase bd = new DataBase();

        ResultSet rs;

        try {
            bd.ejecutarSqlSelect("select * from bus");
            rs = bd.obtenerRegistro();
            do {
                bus = new clsBus(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getInt(7));
                Object newRow[] = {bus.getIdBus(), bus.getPlaca(), bus.getMarca(), bus.getModelo(), bus.getAnnio(), bus.getChofer(), bus.getCapacidad()};
                modelT.addRow(newRow);
            } while (rs.next());
            moduloBus.tblDatos.setModel(modelT);
            //moduloChofer.lblRegistros.setText("Cantidad de Registros: " + modelT.getRowCount());

        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        String titulos[] = {"ID", "#Placa", "Marca", "Modelo", "AÑO", "Identificacion Conductor", "Capacidad"};
        modelT = new DefaultTableModel(null, titulos);
        DataBase bd = new DataBase();

        ResultSet rs;

        try {
            //bd.ejecutarSqlSelect("select * from bus where placa like '%" + txtFiltro.getText().trim() +"%' OR modelo like '%" + txtFiltro.getText().trim() + "%'");
            bd.ejecutarSqlSelect("select * from bus where idBus like '%" + moduloBus.txtBuscar.getText().trim() + "%' OR placa like '%" + moduloBus.txtBuscar.getText().trim() + "%'OR chofer like '%" + moduloBus.txtBuscar.getText().trim() + "%'");
            rs = bd.obtenerRegistro();
            do {
                 bus = new clsBus(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getInt(7));
                Object newRow[] = {bus.getIdBus(), bus.getPlaca(), bus.getMarca(), bus.getModelo(), bus.getAnnio(), bus.getChofer(), bus.getCapacidad()};
                modelT.addRow(newRow);

            } while (rs.next());
            moduloBus.tblDatos.setModel(modelT);
            //frmA.lblRegistros.setText("Cantidad de Registros: " + modelT.getRowCount());

        } catch (SQLException ex) {
            //////
        }
    }

    private boolean validarTexto() {
        return !agregarBus.txtMarca.getText().isEmpty()
                && !agregarBus.txtModelo.getText().isEmpty()
                && !agregarBus.txtAnio.getText().isEmpty()
                && !agregarBus.txtCapacidad.getText().isEmpty()
                && agregarBus.cmbChoferBus.getSelectedIndex() > 0;
    }

    public void limpiarVistaNuevo() {
        agregarBus.txtMarca.setText(null);
        agregarBus.txtModelo.setText(null);
        agregarBus.txtAnio.setText(null);
        agregarBus.txtCapacidad.setText(null);
        agregarBus.txtPlaca.setText(null);
        agregarBus.cmbChoferBus.setSelectedItem(0);
    }

    private void cargarComboBus() {
        DefaultComboBoxModel cmbModel = new DefaultComboBoxModel();
        DataBase bd = new DataBase();
        cmbModel.addElement("Seleccionar.....");

        ResultSet rs;

        try {
            bd.ejecutarSqlSelect("SELECT chofer.Cedula,chofer.nombre FROM chofer WHERE chofer.Cedula NOT IN (SELECT bus.chofer FROM bus)");
            rs = bd.obtenerRegistro();
            do {
                cmbModel.addElement(rs.getString(1) + " " + rs.getString(2));
            } while (rs.next());
            agregarBus.cmbChoferBus.setModel(cmbModel);
            //moduloChofer.lblRegistros.setText("Cantidad de Registros: " + modelT.getRowCount());

        } catch (SQLException ex) {
            cmbModel.removeAllElements();
            cmbModel.addElement("No hay choferes disponibles");
           agregarBus.cmbChoferBus.setModel(cmbModel);
        }
    }

}
