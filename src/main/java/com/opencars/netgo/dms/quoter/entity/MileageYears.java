package com.opencars.netgo.dms.quoter.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@ApiModel(value = "Entidad Modelo de Tabla de Porcentajes por año y kilometraje", description = "Información completa que se almacena de la tabla de porcentajes por año del auto y kilometraje para el cotizador de VO")
public class MileageYears {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private int year;

    @NotNull
    private long initKm;

    @NotNull
    private long endKm;

    @NotNull
    private int percent;

    public MileageYears() {
    }

    public MileageYears(int year, long initKm, long endKm, int percent) {
        this.year = year;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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
