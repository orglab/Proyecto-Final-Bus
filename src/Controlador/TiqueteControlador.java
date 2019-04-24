/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import JavaBeans.clsTiquete;
import Modelo.DataBase;
import Modelo.ModeloTiquete;
import Vista.frmBoleteria;
import Vista.frmPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author JJGB
 */
public class TiqueteControlador implements ActionListener, WindowListener, MouseListener {

    private frmBoleteria moduloBoleteria;
    public frmPrincipal moduloPrincipal;
    private clsTiquete tiquete;
    private ModeloTiquete modeloTiquete;

    public TiqueteControlador(frmBoleteria modBoleteria, frmPrincipal modPrincipal, clsTiquete tiquet, ModeloTiquete modeloTiquet) {
        this.moduloPrincipal = modPrincipal;
        this.moduloBoleteria = modBoleteria;
        this.tiquete = tiquet;
        this.modeloTiquete = modeloTiquet;

        //Listener de la  de choferes
        //Listener para los Botones mapeados en la vista
        this.moduloPrincipal.btnBoleteria.addActionListener(this);

        // Botones mapeados para el la vista de la boleteria
        this.moduloBoleteria.btnImprimir.addActionListener(this);
        this.moduloBoleteria.btnCancelar.addActionListener(this);
        this.moduloBoleteria.btnLimpiar.addActionListener(this);
        this.moduloBoleteria.cmbRuta.addActionListener(this);
        this.moduloBoleteria.cmbHora.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == moduloPrincipal.btnBoleteria) {
            moduloBoleteria.setTitle("Modulo de Boleteria");
            cargarComboBoleteria();
            //moduloBoleteria.txtIdTiquete.setEnabled(false);
            Date now = new Date(System.currentTimeMillis());
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            String date = dateFormat.format(now);
            moduloBoleteria.txtFechaVenta.setText(date);
            moduloBoleteria.txtFechaVenta.setEditable(false);
            moduloBoleteria.setVisible(true);
        }

        if (e.getSource().equals(moduloBoleteria.cmbRuta)) {
            if (moduloBoleteria.cmbRuta.getSelectedIndex() > 0) {
                
                String idruta[] = moduloBoleteria.cmbRuta.getSelectedItem().toString().split("-");
                cargarComboHorarios(idruta[0]);
            }
        }

        if (e.getSource() == moduloBoleteria.btnImprimir) {

            if (validarTexto()) {
                Date now = new Date(System.currentTimeMillis());
                tiquete.setFechaVenta(now);
                tiquete.setIdTiquete(Integer.parseInt(moduloBoleteria.txtIdTiquete.getText()));
                String[] ruta = moduloBoleteria.cmbRuta.getSelectedItem().toString().split("-");
                tiquete.setRuta(Integer.parseInt(ruta[0]));
                tiquete.setNumAsiento(Integer.parseInt(moduloBoleteria.txtNumAsiento.getText()));
                String[] hora = moduloBoleteria.cmbHora.getSelectedItem().toString().split("-");
                if (e.getSource().equals(moduloBoleteria.chkPreferencial.isSelected())) {
                    // 1 = asiento preferencial
                    tiquete.setPreferencial(1);
                } else {
                    // 0 = asiento regular
                    tiquete.setPreferencial(0);
                }

            } else {
                JOptionPane.showMessageDialog(moduloBoleteria, "Debe completar los campos solicitados");

            }

            if (modeloTiquete.insertarTiquete(tiquete)) {
                JOptionPane.showMessageDialog(moduloBoleteria, "tiquete Registrado");
                cargarComboBoleteria();
                limpiarVistaNuevo();
            } else {
                JOptionPane.showMessageDialog(moduloBoleteria, "Error al guardar");
            }
        }

        if (e.getSource() == moduloBoleteria.btnCancelar) {
            moduloBoleteria.dispose();
        }

        if (e.getSource() == moduloBoleteria.btnLimpiar) {
            limpiarVistaNuevo();
            cargarComboBoleteria();

        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosed(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean validarTexto() {
        return !moduloBoleteria.txtIdTiquete.getText().isEmpty()
                && moduloBoleteria.cmbRuta.getSelectedIndex() > 0
                && !moduloBoleteria.txtNumAsiento.getText().isEmpty()
                && moduloBoleteria.cmbHora.getSelectedIndex() > 0
                && !moduloBoleteria.txtFechaVenta.getText().isEmpty();
    }

    public void limpiarVistaNuevo() {
        moduloBoleteria.txtFechaVenta.setText(null);
        moduloBoleteria.txtIdTiquete.setText(null);
        moduloBoleteria.txtNumAsiento.setText(null);
        moduloBoleteria.cmbRuta.setSelectedItem(0);
        moduloBoleteria.chkPreferencial.setSelected(false);
        moduloBoleteria.cmbHora.setSelectedItem(0);
        moduloBoleteria.txtPrecio.setText(null);
    }

    private void cargarComboBoleteria() {
        DefaultComboBoxModel cmbModel = new DefaultComboBoxModel();
        DataBase bd = new DataBase();
        cmbModel.addElement("Seleccionar.....");

        ResultSet rs;

        try {
            bd.ejecutarSqlSelect("select idruta,lugar_salida,lugar_destino,precio from ruta");
            rs = bd.obtenerRegistro();
            do {
                cmbModel.addElement(rs.getInt(1) + "- " + rs.getString(2) + "- " + rs.getString(3));
                moduloBoleteria.txtPrecio.setEnabled(false);
                moduloBoleteria.txtPrecio.setText(String.valueOf(rs.getFloat(4)));
            } while (rs.next());
            moduloBoleteria.cmbRuta.setModel(cmbModel);

        } catch (SQLException ex) {
            cmbModel.removeAllElements();
            cmbModel.addElement("No hay rutas disponibles");
            moduloBoleteria.cmbRuta.setModel(cmbModel);
        }
    }

    private void cargarComboHorarios(String str) {
        DefaultComboBoxModel cmbModel = new DefaultComboBoxModel();
        DataBase bd = new DataBase();
        cmbModel.addElement("Seleccionar.....");
        ResultSet rs;

        try {
            bd.ejecutarSqlSelect("Select * from horario  WHERE idhorario NOT IN(SELECT idhorario FROM `ruta-horario` WHERE idruta=" + str + ")");
            rs = bd.obtenerRegistro();
            do {
                cmbModel.addElement(rs.getInt(1) + "- " + rs.getString(2));
            } while (rs.next());
            moduloBoleteria.cmbHora.setModel(cmbModel);

        } catch (SQLException ex) {
            cmbModel.removeAllElements();
            cmbModel.addElement("No hay horarios disponibles");
            moduloBoleteria.cmbHora.setModel(cmbModel);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
