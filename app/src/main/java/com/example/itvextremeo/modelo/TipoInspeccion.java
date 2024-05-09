package com.example.itvextremeo.modelo;

public class TipoInspeccion {

    private String nombre;
    private int costo;
    private int id;

    public TipoInspeccion(String nombre, int costo, int id) {
        this.nombre = nombre;
        this.costo = costo;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
