/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaBeans;

/**
 *
 * @author JJGB
 */
public class clsRuta {
    private int idruta; 
    private String lugarSalida; 
    private String lugarDestino; 
    private float precio;  
    private int asientos_disponibles;

    public clsRuta(int idruta, String lugarSalida, String lugarDestino, float precio, int asientos_disponibles) {
        this.idruta = idruta;
        this.lugarSalida = lugarSalida;
        this.lugarDestino = lugarDestino;
        this.precio = precio;
        this.asientos_disponibles = asientos_disponibles;
    }
    
     public clsRuta() {
        this.idruta = 0;
        this.lugarSalida = "";
        this.lugarDestino = "";
        this.precio = 0;
        this.asientos_disponibles = 0;
    }

    public int getIdruta() {
        return idruta;
    }

    public void setIdruta(int idruta) {
        this.idruta = idruta;
    }

    public String getLugarSalida() {
        return lugarSalida;
    }

    public void setLugarSalida(String lugarSalida) {
        this.lugarSalida = lugarSalida;
    }

    public String getLugarDestino() {
        return lugarDestino;
    }

    public void setLugarDestino(String lugarDestino) {
        this.lugarDestino = lugarDestino;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getAsientos_disponibles() {
        return asientos_disponibles;
    }

    public void setAsientos_disponibles(int asientos_disponibles) {
        this.asientos_disponibles = asientos_disponibles;
    }
    
     
}
