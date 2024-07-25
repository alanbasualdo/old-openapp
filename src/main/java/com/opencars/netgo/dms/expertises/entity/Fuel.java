package com.opencars.netgo.dms.expertises.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name = "fuel")
@ApiModel(value = "Entidad Modelo de tipos de combustibles de autos para usar en Peritajes y otros", description = "Información completa que se almacena de un tipo de combustible")
public class Fuel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty
    private String type;

    public Fuel() {
    }

    public Fuel(int id) {
        this.id = id;
    }

    public Fuel(String type) {
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
