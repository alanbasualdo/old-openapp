package com.opencars.netgo.dms.quoter.entity;

import io.swagger.annotations.ApiModel;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@ApiModel(value = "Entidad Modelo de Marcas de Autos de Info Auto", description = "Información completa que se almacena de la tabla de marcas de info auto")
public class BrandInfoAuto {

    @Id
    private int id;
    private String name;

    public BrandInfoAuto() {
    }

    public BrandInfoAuto(int id, String name) {
        this.id = id;
        this.name = name;
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
}
