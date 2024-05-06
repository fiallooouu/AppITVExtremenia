package com.example.itvextremeo.modelo;

public class InfoVehiculo {

    private String matricula;
    private int idTipoVehiculo;

    public InfoVehiculo(String matricula, int idTipoVehiculo) {
        this.matricula = matricula;
        this.idTipoVehiculo = idTipoVehiculo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getIdTipoVehiculo() {
        return idTipoVehiculo;
    }

    public void setIdTipoVehiculo(int idTipoVehiculo) {
        this.idTipoVehiculo = idTipoVehiculo;
    }

    @Override
    public String toString() {
        return matricula;
    }
}
