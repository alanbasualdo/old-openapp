package com.opencars.netgo.dms.finanzas.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@ApiModel(value = "Entidad Modelo de Tasas de intereses", description = "Información del valor de tasas de intereses")
public class Tasas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @NotNull
    Double value;
    @NotNull
    LocalDateTime date;
    @NotNull
    int enable;
    private String type;

    public Tasas() {
    }

    public Tasas(int id, Double value, LocalDateTime date, int enable, String type) {
        this.id = id;
        this.value = value;
        this.date = date;
        this.enable = enable;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
