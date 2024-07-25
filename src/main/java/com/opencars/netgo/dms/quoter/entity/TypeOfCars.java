package com.opencars.netgo.dms.quoter.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@ApiModel(value = "Entidad Modelo de Tabla de Tipo de Vehículos", description = "Información completa que se almacena de la tabla de tipo de vehículos para el cotizador de VO")
public class TypeOfCars {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String type;

    @NotNull
    private int percent;

    public TypeOfCars() {
    }

    public TypeOfCars(int id) {
        this.id = id;
    }

    public TypeOfCars(String type, int percent) {
        this.type = type;
        this.percent = percent;
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

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
