package com.example.itvextremeo.modelo;

public class Cita {

    private String codigo;
    private String matricula;
    private String fecha;
    private String hora;
    private String tipoInspec;
    private String tipoVehiculo;
    private String descripccion;
    private String activa;

    public Cita(String codigo, String matricula, String fecha, String hora, String tipoInspec, String tipoVehiculo, String descripccion, String activa) {
        this.codigo = codigo;
        this.matricula = matricula;
        this.fecha = fecha;
        this.hora = hora;
        this.tipoInspec = tipoInspec;
        this.tipoVehiculo = tipoVehiculo;
        this.descripccion = descripccion;
        this.activa = activa;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getTipoInspec() {
        return tipoInspec;
    }

    public void setTipoInspec(String tipoInspec) {
        this.tipoInspec = tipoInspec;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public String getDescripccion() {
        return descripccion;
    }

    public void setDescripccion(String descripccion) {
        this.descripccion = descripccion;
    }

    public String getActiva() {
        return activa;
    }

    public void setActiva(String activa) {
        this.activa = activa;
    }
}
