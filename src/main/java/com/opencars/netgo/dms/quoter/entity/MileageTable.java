package com.opencars.netgo.dms.quoter.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@ApiModel(value = "Entidad Modelo de Tabla de Kilometraje", description = "Información completa que se almacena de la tabla de kilometraje para el cotizador de VO")
public class MileageTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private long initKm;

    @NotNull
    private long endKm;

    @NotNull
    private int percent;

    public MileageTable() {
    }

    public MileageTable(long initKm, long endKm, int percent) {
        this.initKm = initKm;
        this.endKm = endKm;
        this.percent = percent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getInitKm() {
        return initKm;
    }

    public void setInitKm(long initKm) {
        this.initKm = initKm;
    }

    public long getEndKm() {
        return endKm;
    }

    public void setEndKm(long endKm) {
        this.endKm = endKm;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
