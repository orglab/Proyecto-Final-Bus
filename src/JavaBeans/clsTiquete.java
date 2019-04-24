/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaBeans;

import java.sql.Date;

/**
 *
 * @author JJGB
 */
public class clsTiquete {
    private int idTiquete;
    private int ruta;
    private int numAsiento;
    private int preferencial;
    private Date fechaVenta;

    public clsTiquete(int idTiquete, int ruta, int numAsiento, int preferencial, Date fechaVenta) {
        this.idTiquete = idTiquete;
        this.ruta = ruta;
        this.numAsiento = numAsiento;
        this.preferencial = preferencial;
        this.fechaVenta = fechaVenta;
    }
    
    public clsTiquete() {
        this.idTiquete = 0;
        this.ruta = 0;
        this.numAsiento = 0;
        this.preferencial = 0;
        this.fechaVenta = null;
    }

    public int getIdTiquete() {
        return idTiquete;
    }

    public void setIdTiquete(int idTiquete) {
        this.idTiquete = idTiquete;
    }

    public int getRuta() {
        return ruta;
    }

    public void setRuta(int ruta) {
        this.ruta = ruta;
    }

    public int getNumAsiento() {
        return numAsiento;
    }

    public void setNumAsiento(int numAsiento) {
        this.numAsiento = numAsiento;
    }

    public int getPreferencial() {
        return preferencial;
    }

    public void setPreferencial(int preferencial) {
        this.preferencial = preferencial;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }
    
    
}
