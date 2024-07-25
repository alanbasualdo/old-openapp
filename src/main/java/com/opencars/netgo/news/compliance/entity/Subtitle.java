package com.opencars.netgo.news.compliance.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@ApiModel(value = "Entidad Modelo Subtítulos de Compliance", description = "Subtítulos del manual de compliance.")
public class Subtitle {

    /* Attributes */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String subtitle;
    @NotNull
    private int number;

    public Subtitle() {
    }

    public Subtitle(int id) {
        this.id = id;
    }

    public Subtitle(String subtitle, int number) {
        this.subtitle = subtitle;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
