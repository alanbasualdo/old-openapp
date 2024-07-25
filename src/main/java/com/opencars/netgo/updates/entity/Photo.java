package com.opencars.netgo.updates.entity;

import io.swagger.annotations.ApiModel;

import javax.persistence.*;

@Entity
@ApiModel(value = "Entidad Modelo de Sección Actualizada de la Intranet", description = "Se almacena una foto y descripción de una sección actualizada en la Intranet")
public class Photo {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;

    public Photo() {
    }

    public Photo(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

