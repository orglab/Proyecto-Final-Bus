/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaBeans;

/**
 *
 * @author Manyor
 */
public class clsBus {
    private int idBus;
    private String placa;
    private String marca;
    private String modelo;
    private int annio;
    private String chofer;
    private int capacidad;

    public clsBus(int idBus, String placa, String marca, String modelo, int annio, String chofer, int capacidad) {
        this.idBus = idBus;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.annio = annio;
        this.chofer = chofer;
        this.capacidad = capacidad;
    }

    public int getIdBus() {
        return idBus;
    }

    public void setIdBus(int idBus) {
        this.idBus = idBus;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnnio() {
        return annio;
    }

    public void setAnnio(int annio) {
        this.annio = annio;
    }

    public String getChofer() {
        return chofer;
    }

    public void setChofer(String chofer) {
        this.chofer = chofer;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public clsBus() {
        this.idBus = 0;
        this.placa = null;
        this.marca = null;
        this.modelo = null;
        this.annio = 0;
        this.chofer = null;
        this.capacidad = 0;
    }
    

}
