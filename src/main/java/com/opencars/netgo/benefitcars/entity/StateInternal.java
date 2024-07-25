package com.opencars.netgo.benefitcars.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@ApiModel(value = "Entidad Modelo de Estado de Solicitudes de Beneficio en Compra de Vehículos para Visualización Interna", description = "Información completa del Estado de Solicitudes de Beneficio en Compra de Vehículos para Visualización Interna")
public class StateInternal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String state;

    public StateInternal() {
    }

    public StateInternal(int id) {
        this.id = id;
    }

    public StateInternal(int id, String state) {
        this.id = id;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
