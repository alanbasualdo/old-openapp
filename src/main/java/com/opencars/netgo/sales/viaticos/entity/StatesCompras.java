package com.opencars.netgo.sales.viaticos.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;


@ApiModel(value = "Entidad Modelo de Estados de Compras", description = "Información completa que se almacena del estado de una compra")
@Entity
public class StatesCompras implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String state;

    public StatesCompras() {
    }

    public StatesCompras(int id) {
        this.id = id;
    }

    public StatesCompras(int id, String state) {
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
