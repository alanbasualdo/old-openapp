package com.opencars.netgo.dms.quoter.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@ApiModel(value = "Entidad Modelo de Tabla de Porcentajes por Modelo de Vehículo", description = "Información completa que se almacena de la tabla de porcentajes por modelo del vehículo para el cotizador de VO")
public class ModelsCars implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private int codia;

    @NotNull
    private String description;

    @NotNull
    private int percent;

    public ModelsCars() {
    }

    public ModelsCars(@NotNull int codia, @NotNull String description, @NotNull int percent) {
        this.codia = codia;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

}
