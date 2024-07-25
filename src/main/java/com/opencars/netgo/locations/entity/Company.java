package com.opencars.netgo.locations.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@ApiModel(value = "Entidad Modelo de Empresa", description = "Información completa que se almacena de la empresa")
public class Company implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(unique = true, nullable = false)
    private String cuit;
    @NotNull
    private String logo;

    public Company() {
    }

    public Company(int id) {
        this.id = id;
    }

    public Company(String name, String cuit, String logo) {
        this.name = name;
        this.cuit = cuit;
        this.logo = logo;
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

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
