package com.opencars.netgo.sales.proveedores.entity;


import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;

@ApiModel(value = "Entidad Modelo de Estados de Módulo Compras-Proveedores", description = "Información completa que se almacena del estado de una compra")
@Entity
public class StatesProveedores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String state;

    public StatesProveedores() {
    }

    public StatesProveedores(int id) {
        this.id = id;
    }

    public StatesProveedores(int id, String state) {
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
