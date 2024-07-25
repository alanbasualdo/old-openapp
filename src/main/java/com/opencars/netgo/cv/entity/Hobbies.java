package com.opencars.netgo.cv.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;

@Entity
@ApiModel(value = "Entidad Modelo de Hobbies", description = "Información completa que se almacena de un hobbie cargado en cv")
public class Hobbies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String hobbie;

    @NotNull
    private String description;

    public Hobbies() {
    }

    public Hobbies(String hobbie, String description) {
        this.hobbie = hobbie;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHobbie() {
        return hobbie;
    }

    public void setHobbie(String hobbie) {
        this.hobbie = hobbie;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
