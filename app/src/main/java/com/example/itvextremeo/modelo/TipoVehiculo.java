package com.example.itvextremeo.modelo;

public class TipoVehiculo {

    private String tipo;
    private int valor;

    public TipoVehiculo(String tipo, int valor) {
        this.tipo = tipo;
        this.valor = valor;
    }


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return tipo;
    }
}
