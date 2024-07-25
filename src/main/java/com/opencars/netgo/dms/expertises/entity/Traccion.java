package com.opencars.netgo.dms.expertises.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name = "traccion")
@ApiModel(value = "Entidad Modelo de Tipo de tracción de autos para usar en Peritajes y otros", description = "Información completa que se almacena de un tipo de tracción")
public class Traccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty
    private String type;

    public Traccion() {
    }

    public Traccion(int id) {
        this.id = id;
    }

    public Traccion(String type) {
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
