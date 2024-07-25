package com.opencars.netgo.sales.proveedores.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@ApiModel(value = "Entidad Modelo de Tipos de Pagos", description = "Información completa que se almacena de un tipo de pago")
public class TypesPaids implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    private String type;

    public TypesPaids() {
    }

    public TypesPaids(int id) {
        this.id = id;
    }

    public TypesPaids(String type) {
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
