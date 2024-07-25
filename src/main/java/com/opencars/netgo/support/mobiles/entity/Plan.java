package com.opencars.netgo.support.mobiles.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@ApiModel(value = "Entidad Modelo de un plan de celular", description = "Información completa que se almacena del plan de un celular")
public class Plan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty
    private String plan;

    public Plan() {
    }

    public Plan(int id) {
        this.id = id;
    }

    public Plan(String plan) {
        this.plan = plan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
}
