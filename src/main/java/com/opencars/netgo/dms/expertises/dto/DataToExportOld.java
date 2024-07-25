package com.opencars.netgo.dms.expertises.dto;

import java.time.LocalDate;

public class DataToExportOld {

    private long numero;
    private LocalDate fecha;
    private String unidad;
    private String patente;
    private String cliente;
    private int gastos_reparacion;

    public DataToExportOld() {
    }

    public DataToExportOld(long numero, LocalDate fecha, String unidad, String patente, String cliente, int gastos_reparacion) {
        this.numero = numero;
        this.fecha = fecha;
        this.unidad = unidad;
        this.patente = patente;
        this.cliente = cliente;
        this.gastos_reparacion = gastos_reparacion;
    }

    public long getNumero() {
        return numero;
    }

    public void setNumero(long numero) {
        this.numero = numero;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getGastos_reparacion() {
        return gastos_reparacion;
    }

    public void setGastos_reparacion(int gastos_reparacion) {
        this.gastos_reparacion = gastos_reparacion;
    }
}
