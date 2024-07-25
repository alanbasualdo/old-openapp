package com.opencars.netgo.support.pcs.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@ApiModel(value = "Entidad Modelo de Modelo de Pc", description = "Información completa que se almacena del modelo de pc")
public class Model implements Serializable {

    /* Attributes */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String model;

    public Model() {
    }

    public Model(int id) {
        this.id = id;
    }

    public Model(String model) {
        this.model = model;
    }

    public Model(int id, String model) {
        this.id = id;
        this.model = model;
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
}
