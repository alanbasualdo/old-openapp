package com.opencars.netgo.sales.proveedores.entity;

import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@ApiModel(value = "Entidad Modelo de Tipos de Autorizaciones", description = "Información completa que se almacena de un tipo de autorización")
public class AuthorizationTypes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String type;

    public AuthorizationTypes() {
    }

    public AuthorizationTypes(int id) {
        this.id = id;
    }

    public AuthorizationTypes(String type) {
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
