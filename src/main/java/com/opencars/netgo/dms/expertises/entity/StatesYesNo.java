package com.opencars.netgo.dms.expertises.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name = "statesyesno")
@ApiModel(value = "Entidad Modelo de Estados Si-No para usar en Peritajes y otros", description = "Información completa que se almacena de un estado")
public class StatesYesNo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty
    private String state;

    public StatesYesNo() {
    }

    public StatesYesNo(int id) {
        this.id = id;
    }

    public StatesYesNo(String state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
