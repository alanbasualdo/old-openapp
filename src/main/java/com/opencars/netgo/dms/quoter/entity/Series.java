package com.opencars.netgo.dms.quoter.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;

@Entity
@ApiModel(value = "Entidad Modelo de Tabla de Series", description = "Información completa que se almacena de la tabla de series para el cotizador de VO")
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private int codia;

    @NotNull
    private String model;

    @NotNull
    @ManyToOne
    private BrandsCars brand;

    @NotNull
    private int percent;

    public Series() {
    }

    public Series(int codia, String model, BrandsCars brand, int percent) {
        this.codia = codia;
        this.model = model;
        this.brand = brand;
        this.percent = percent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodia() {
        return codia;
    }

    public void setCodia(int codia) {
        this.codia = codia;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BrandsCars getBrand() {
        return brand;
    }

    public void setBrand(BrandsCars brand) {
        this.brand = brand;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
