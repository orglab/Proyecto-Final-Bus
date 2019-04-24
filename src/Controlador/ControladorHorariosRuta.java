/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import JavaBeans.clsChofer;
import Modelo.DataBase;
import Modelo.ModeloHorariosRuta;
import Vista.frmHorariosRutas;
import Vista.frmPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Manyor
 */
public class ControladorHorariosRuta implements ActionListener, WindowListener {

    frmHorariosRutas frmhorariosRutas;
    ModeloHorariosRuta modeloHorariosRuta;
    frmPrincipal principal;

    public ControladorHorariosRuta(frmHorariosRutas rutasVista, ModeloHorariosRuta modeloHoraRta, frmPrincipal prin) {
        
        // Botones mapeados para el la vista de los horarios
        this.frmhorariosRutas = rutasVista;
        this.modeloHorariosRuta = modeloHoraRta;
        this.principal = prin;
        this.principal.btnModHorarios.addActionListener(this);
        this.frmhorariosRutas.addWindowListener(this);
        this.frmhorariosRutas.btnAgregarHorario.addActionListener(this);
        this.frmhorariosRutas.btnEliminarHorario.addActionListener(this);
        this.frmhorariosRutas.cmbRutas.addActionListener(this);
    }

    @Override
    public void windowOpened(WindowEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosed(WindowEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        cargarComboRutas();
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(principal.btnModHorarios)) {
            frmhorariosRutas.setVisible(true);
        }

        if (e.getSource().equals(frmhorariosRutas.btnAgregarHorario)) {
            if (frmhorariosRutas.cmbRutas.getSelectedIndex() != -1 && frmhorariosRutas.lstHorario.getSelectedIndex() != -1) {
                String idRuta[] = frmhorariosRutas.cmbRutas.getSelectedItem().toString().split("-");
                String idHorario[] = frmhorariosRutas.lstHorario.getSelectedValue().split("-");
                if (modeloHorariosRuta.insertarHorarioRuta(Integer.parseInt(idRuta[0]), Integer.parseInt(idHorario[0]))) {
                    String idruta[] = frmhorariosRutas.cmbRutas.getSelectedItem().toString().split("-");
                    cargarHorariosListasDispo(idruta[0]);
                    cargarHorariosAsignados(idruta[0]);
                } else {
                    JOptionPane.showConfirmDialog(principal, "Error al asignar el horario");
                }
            }
        }
        if (e.getSource().equals(frmhorariosRutas.btnEliminarHorario)) {
            if (frmhorariosRutas.cmbRutas.getSelectedIndex() != -1 && frmhorariosRutas.lstHorarioSeleccionado.getSelectedIndex() != -1) {
                String idRuta[] = frmhorariosRutas.cmbRutas.getSelectedItem().toString().split("-");
                String idHorario[] = frmhorariosRutas.lstHorarioSeleccionado.getSelectedValue().split("-");
                if (modeloHorariosRuta.desAsignarHorarioRuta(Integer.parseInt(idRuta[0]), Integer.parseInt(idHorario[0]))) {
                    String idruta[] = frmhorariosRutas.cmbRutas.getSelectedItem().toString().split("-");
                    cargarHorariosListasDispo(idruta[0]);
                    cargarHorariosAsignados(idruta[0]);
                } else {
                    JOptionPane.showConfirmDialog(principal, "Error al desasignar el horario");
                }
            }
        }
        if (e.getSource().equals(frmhorariosRutas.cmbRutas)) {
            if (frmhorariosRutas.cmbRutas.getSelectedIndex() > 0) {
                String idruta[] = frmhorariosRutas.cmbRutas.getSelectedItem().toString().split("-");
                cargarHorariosListasDispo(idruta[0]);
                cargarHorariosAsignados(idruta[0]);
            }else{
                DefaultListModel lstMo = (DefaultListModel) frmhorariosRutas.lstHorario.getModel();
                lstMo.removeAllElements();
              frmhorariosRutas.lstHorario.setModel(lstMo);
               frmhorariosRutas.lstHorarioSeleccionado.setModel(lstMo);
            
            
            }
        }

    }

    private DefaultComboBoxModel cargarComboRutas() {
        DefaultComboBoxModel cmbModel = new DefaultComboBoxModel();
        cmbModel.addElement("Seleccione un ruta..");
        DataBase bd = new DataBase();
        ResultSet rs;

        try {
            bd.ejecutarSqlSelect("select idruta,lugar_salida,lugar_destino from ruta");
            rs = bd.obtenerRegistro();
            do {
                cmbModel.addElement(rs.getInt(1) + "- " + rs.getString(2) + " - " + rs.getString(3));
            } while (rs.next());
            frmhorariosRutas.cmbRutas.setModel(cmbModel);

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return cmbModel;
    }

    private void cargarHorariosListasDispo(String str) {
        DefaultListModel lstModel = new DefaultListModel();
        lstModel.removeAllElements();
        DataBase bd = new DataBase();
        ResultSet rs;

        try {
            bd.ejecutarSqlSelect("Select * from horario  WHERE idhorario NOT IN(SELECT idhorario FROM `ruta-horario` WHERE idruta=" + str + ")");
            rs = bd.obtenerRegistro();
            do {
                lstModel.addElement(rs.getInt(1) + "- " + rs.getString(2));
            } while (rs.next());
            frmhorariosRutas.lstHorario.setModel(lstModel);

        } catch (SQLException ex) {
            System.out.println(ex);
              lstModel.removeAllElements();
            frmhorariosRutas.lstHorario.setModel(lstModel);
        }

    }

    private void cargarHorariosAsignados(String str) {
        DefaultListModel lstModel = new DefaultListModel();
        DataBase bd = new DataBase();
        ResultSet rs;
        lstModel.removeAllElements();

        try {
            bd.ejecutarSqlSelect("Select * from horario  WHERE idhorario IN(SELECT idhorario FROM `ruta-horario` WHERE idruta=" + str + ") ORDER BY idhorario ASC");
            rs = bd.obtenerRegistro();
            do {
                lstModel.addElement(rs.getInt(1) + "- " + rs.getString(2));
            } while (rs.next());
            frmhorariosRutas.lstHorarioSeleccionado.setModel(lstModel);

        } catch (SQLException ex) {
            lstModel.removeAllElements();
            frmhorariosRutas.lstHorarioSeleccionado.setModel(lstModel);
        }

    }

}
