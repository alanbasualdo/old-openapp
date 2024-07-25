package com.opencars.netgo.locations.entity;

import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@ApiModel(value = "Entidad Modelo de Sector", description = "Información completa que se almacena del sector")
public class Sector implements Serializable {

    /* Attributes */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    public Sector() {
    }

    public Sector(int id) {
        this.id = id;
    }

    public Sector(String name) {
        this.name = name;
    }

    /* Getter and setters */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
