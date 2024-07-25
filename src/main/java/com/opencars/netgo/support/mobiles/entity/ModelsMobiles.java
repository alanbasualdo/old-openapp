package com.opencars.netgo.support.mobiles.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@ApiModel(value = "Entidad Modelo de Modelos de Celulares", description = "Información completa que se almacena de un modelo de celular")
public class ModelsMobiles implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String model;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "brand_id")
    private BrandsMobiles brand;

    public ModelsMobiles() {
    }

    public ModelsMobiles(int id) {
        this.id = id;
    }

    public ModelsMobiles(String model, BrandsMobiles brand) {
        this.model = model;
        this.brand = brand;
    }

    public ModelsMobiles(int id, String model, BrandsMobiles brand) {
        this.id = id;
        this.model = model;
        this.brand = brand;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BrandsMobiles getBrand() {
        return brand;
    }

    public void setBrand(BrandsMobiles brand) {
        this.brand = brand;
    }
}
