package com.opencars.netgo.dms.providers.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name = "fiscal_types")
@ApiModel(value = "Entidad Modelo de tipos de condiciones fiscales", description = "Información completa que se almacena de una una condición fiscal")
public class FiscalType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty
    private String nombre;

    public FiscalType() {
    }

    public FiscalType(int id) {
        this.id = id;
    }

    public FiscalType(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
