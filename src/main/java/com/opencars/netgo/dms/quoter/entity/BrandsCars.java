package com.opencars.netgo.dms.quoter.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@ApiModel(value = "Entidad Modelo de Marcas de Autos", description = "Información completa que se almacena de la tabla de marcas de autos.")
public class BrandsCars {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private int codia;

    @NotNull
    private String brand;

    public BrandsCars() {
    }

    public BrandsCars(int id) {
        this.id = id;
    }

    public BrandsCars(int codia, String brand) {
        this.codia = codia;
        this.brand = brand;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

}
