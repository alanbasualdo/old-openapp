package com.opencars.netgo.support.pcs.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@ApiModel(value = "Entidad Modelo de Tipo de Pc", description = "Información completa que se almacena del tipo de pc")
public class PcTypes {

    /* Attributes */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String type;

    public PcTypes() {
    }

    public PcTypes(int id) {
        this.id = id;
    }

    public PcTypes(String type) {
        this.type = type;
    }

    public PcTypes(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
