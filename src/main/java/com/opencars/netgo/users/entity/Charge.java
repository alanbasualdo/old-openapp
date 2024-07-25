package com.opencars.netgo.users.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "charges")
@ApiModel(value = "Entidad Modelo de Cargo dentro de la empresa", description = "Nombre que se almacena de un cargo dentro de la empresa")
public class Charge implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty
    private String name;

    public Charge() {
    }

    public Charge(int id) {
        this.id = id;
    }

    public Charge(String name) {
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
