package com.opencars.netgo.dms.expertises.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name = "transmision")
@ApiModel(value = "Entidad Modelo de Tipo de Transmisión de autos para usar en Peritajes y otros", description = "Información completa que se almacena de un tipo de transmisión")
public class Transmision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty
    private String type;

    public Transmision() {
    }

    public Transmision(int id) {
        this.id = id;
    }

    public Transmision(String type) {
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
