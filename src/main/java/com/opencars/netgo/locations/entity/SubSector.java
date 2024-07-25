package com.opencars.netgo.locations.entity;

import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "subsector")
@ApiModel(value = "Entidad Modelo de Subáreas", description = "Información completa que se almacena de la sub área")
public class SubSector implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToOne
    private Sector sector;

    public SubSector() {
    }

    public SubSector(int id) {
        this.id = id;
    }

    public SubSector(String name) {
        this.name = name;
    }

    public SubSector(String name, Sector sector) {
        this.name = name;
        this.sector = sector;
    }

    public SubSector(int id, String name, Sector sector) {
        this.id = id;
        this.name = name;
        this.sector = sector;
    }

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

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }
}
