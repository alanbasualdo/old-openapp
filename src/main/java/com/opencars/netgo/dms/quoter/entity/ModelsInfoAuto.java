package com.opencars.netgo.dms.quoter.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@ApiModel(value = "Entidad Modelo de Modelos de Autos de Info Auto", description = "Información completa que se almacena de la tabla de modelos de info auto")
public class ModelsInfoAuto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty
    private int codia;
    @NotEmpty
    private String description;

    public ModelsInfoAuto() {
    }

    public ModelsInfoAuto(int codia, String description) {
        this.codia = codia;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCodia() {
        return codia;
    }

    public void setCodia(int codia) {
        this.codia = codia;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
