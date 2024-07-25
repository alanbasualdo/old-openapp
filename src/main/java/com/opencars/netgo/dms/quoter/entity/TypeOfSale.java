package com.opencars.netgo.dms.quoter.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@ApiModel(value = "Entidad Modelo de Tipo de Venta", description = "Información completa que se almacena de los tipos de ventas para el cotizador de VO")
public class TypeOfSale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String type;

    @NotNull
    private int percent;

    public TypeOfSale() {
    }

    public TypeOfSale(int id) {
        this.id = id;
    }

    public TypeOfSale(String type, int percent) {
        this.type = type;
        this.percent = percent;
    }

    public TypeOfSale(int id, String type, int percent) {
        this.id = id;
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
