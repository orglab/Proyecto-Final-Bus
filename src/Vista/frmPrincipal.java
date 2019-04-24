/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;
import javax.swing.ImageIcon;
import Controlador.BusControlador;
import Controlador.ChoferesControlador;
import Controlador.ControladorHorariosRuta;
import JavaBeans.clsChofer;
import Modelo.ModeloChofer;
import JavaBeans.clsRuta;
import Modelo.ModeloRuta;
import Controlador.RutasControlador;
import Controlador.TiqueteControlador;
import JavaBeans.clsBus;
import JavaBeans.clsTiquete;
import Modelo.ModeloBus;
import Modelo.ModeloHorariosRuta;
import Modelo.ModeloTiquete;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

/**
 *
 * @author JJGB
 */
public class frmPrincipal extends javax.swing.JFrame {

    frmPrincipal principal;
     
    //Frames relacionados a la boleteria
    frmBoleteria frameBoleteria = new frmBoleteria(this, true);
    TiqueteControlador controladorTiquete;
    clsTiquete tiquete = new clsTiquete();
    ModeloTiquete modeloTiquete = new ModeloTiquete();
    frmVistaTiquetes frameVistaTiq = new frmVistaTiquetes(this, true);

    //Frames relacionados a los horarios
    frmHorariosRutas horaRutas = new frmHorariosRutas();
    ControladorHorariosRuta controladorHorarioRuta;
    ModeloHorariosRuta modeloHorarioRuta= new ModeloHorariosRuta();

// frames relacionados con el modulo de Choferes
    frmModuloChofer modChofer = new frmModuloChofer();
    frmAgregarChofer adChofer = new frmAgregarChofer();
    clsChofer chofer = new clsChofer();
    ModeloChofer modeloChofer = new ModeloChofer();

    // frames relacionados con el modulo de Rutas
    frmModuloRuta modRuta = new frmModuloRuta();
    frmAgregarRuta adRuta = new frmAgregarRuta();
    clsRuta ruta = new clsRuta();
    ModeloRuta modeloRuta = new ModeloRuta();

    //Frames relacionados al Modulo buses
    frmModuloBuses modBus = new frmModuloBuses();
    frmAgregarBus adBus = new frmAgregarBus();
    clsBus bus = new clsBus();
    ModeloBus modeloBus = new ModeloBus();

    /**
     * Creates new form frmPrincipal
     */
    public frmPrincipal() {
        initComponents();
        controladorTiquete = new TiqueteControlador(frameBoleteria, this,tiquete, modeloTiquete,frameVistaTiq);
        setIcon();
   
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnModChoferes = new javax.swing.JButton();
        btnModBuses = new javax.swing.JButton();
        btnModHorarios = new javax.swing.JButton();
        btnBoleteria = new javax.swing.JButton();
        btnModRutas = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        panelPrincipal = new javax.swing.JPanel(){
            public void paintComponent(Graphics g)
            {
                ImageIcon im = new ImageIcon("bus.jpg");
                Image i=im.getImage();

                g.drawImage(i, 0, 0, this.getSize().width, this.getSize().height, this);
            }

        };

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("AUTOBUSES LIBERIA");
        setIconImages(null);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnModChoferes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/driver.png"))); // NOI18N
        btnModChoferes.setText("Módulo de Choferes");
        btnModChoferes.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnModChoferes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModChoferesActionPerformed(evt);
            }
        });

        btnModBuses.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Bus.png"))); // NOI18N
        btnModBuses.setText("Módulo de Buses");
        btnModBuses.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnModBuses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModBusesActionPerformed(evt);
            }
        });

        btnModHorarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/calendar.png"))); // NOI18N
        btnModHorarios.setText("Módulo de Horarios");
        btnModHorarios.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnModHorarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModHorariosActionPerformed(evt);
            }
        });

        btnBoleteria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ticket-office.png"))); // NOI18N
        btnBoleteria.setText("Boletería");
        btnBoleteria.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnBoleteria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBoleteriaActionPerformed(evt);
            }
        });

        btnModRutas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/location.png"))); // NOI18N
        btnModRutas.setText("Módulo de Rutas");
        btnModRutas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnModRutas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModRutasActionPerformed(evt);
            }
        });

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/log-out.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnModRutas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBoleteria, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnModBuses, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnModHorarios, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE))
                    .addComponent(btnModChoferes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(btnModChoferes)
                .addGap(18, 18, 18)
                .addComponent(btnModBuses)
                .addGap(18, 18, 18)
                .addComponent(btnModHorarios)
                .addGap(18, 18, 18)
                .addComponent(btnBoleteria)
                .addGap(18, 18, 18)
                .addComponent(btnModRutas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                .addComponent(btnSalir)
                .addGap(14, 14, 14))
        );

        panelPrincipal.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 687, Short.MAX_VALUE)
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnModChoferesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModChoferesActionPerformed
        // TODO add your handling code here:
        ChoferesControlador controller = new ChoferesControlador(modChofer, this, adChofer, chofer, modeloChofer);
        controller.actionPerformed(evt);
        this.principal = controller.moduloPrincipal;
    }//GEN-LAST:event_btnModChoferesActionPerformed

    private void btnModRutasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModRutasActionPerformed
        // TODO add your handling code here:
        RutasControlador controller = new RutasControlador(modRuta, this, adRuta, ruta, modeloRuta);
        controller.actionPerformed(evt);
        this.principal = controller.moduloPrincipal;
    }//GEN-LAST:event_btnModRutasActionPerformed

    private void btnModBusesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModBusesActionPerformed
        // TODO add your handling code here:
        BusControlador controller = new BusControlador(modBus, this, adBus, bus, modeloBus);
        controller.actionPerformed(evt);
        this.principal = controller.moduloPrincipal;
    }//GEN-LAST:event_btnModBusesActionPerformed


    private void btnModHorariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModHorariosActionPerformed
        // TODO add your handling code here:
        controladorHorarioRuta = new ControladorHorariosRuta(horaRutas, modeloHorarioRuta,this);
        controladorHorarioRuta.actionPerformed(evt);
        
    }//GEN-LAST:event_btnModHorariosActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnBoleteriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBoleteriaActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_btnBoleteriaActionPerformed


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmPrincipal().setVisible(true);
            }
        });
    }
    private void setIcon(){
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/school-bus.png")));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnBoleteria;
    public javax.swing.JButton btnModBuses;
    public javax.swing.JButton btnModChoferes;
    public javax.swing.JButton btnModHorarios;
    public javax.swing.JButton btnModRutas;
    public javax.swing.JButton btnSalir;
    private javax.swing.JPanel jPanel1;
    public javax.swing.JPanel panelPrincipal;
    // End of variables declaration//GEN-END:variables
}
