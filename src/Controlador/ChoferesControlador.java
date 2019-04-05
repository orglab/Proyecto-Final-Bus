/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.frmAgregarChofer;
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
    private frmAgregarChofer agregarChofer;

    public ChoferesControlador(frmModuloChofer modChofer, frmPrincipal modPrincipal,frmAgregarChofer agChofer) {
        this.moduloChofer = modChofer;
        this.moduloPrincipal = modPrincipal;
        this.agregarChofer=agChofer;
        
        
        //Listener para los Botones mapeados en la vista
        this.moduloPrincipal.btnModChoferes.addActionListener(this);
        
        // Botones mapeados para el la vista del chofer
        this.moduloChofer.btnAgregar.addActionListener(this);
        this.moduloChofer.btnEditar.addActionListener(this);
        this.moduloChofer.btnEliminar.addActionListener(this);

        //Botones mapeados modulo Agregar Chofer Vista
              
        this.agregarChofer.btnGuardar.addActionListener(this);
        this.agregarChofer.btnCancelar.addActionListener(this);
        this.agregarChofer.btnLimpiar.addActionListener(this);
     
        

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == moduloPrincipal.btnModChoferes) {
            moduloPrincipal.panelPrincipal.add(moduloChofer);
            moduloChofer.show();
        }
         if (e.getSource() == moduloChofer.btnAgregar) {
            agregarChofer.setVisible(true);
            agregarChofer.setTitle("AgregarChofer");
        }
                
    }

}
