/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.frmModuloChofer;
import Vista.frmPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author JJGB
 */
public class ChoferesControlador implements ActionListener {

    private frmModuloChofer moduloChofer;
    public frmPrincipal moduloPrincipal;

    public ChoferesControlador(frmModuloChofer modChofer, frmPrincipal modPrincipal) {
        this.moduloChofer = modChofer;
        this.moduloPrincipal = modPrincipal;
        this.moduloPrincipal.btnModChoferes.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == moduloPrincipal.btnModChoferes) {
            moduloPrincipal.panelPrincipal.add(moduloChofer);
            
            moduloChofer.show();
        }
        
    }

}
