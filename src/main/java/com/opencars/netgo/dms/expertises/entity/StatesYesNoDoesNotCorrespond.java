package com.opencars.netgo.dms.expertises.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name = "statesyesnodoesnotcorrespond")
@ApiModel(value = "Entidad Modelo de Estados Si-No-No Corresponde, para usar en Peritajes y otros", description = "Información completa que se almacena de un estado")
public class StatesYesNoDoesNotCorrespond {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty
    private String state;

    public StatesYesNoDoesNotCorrespond() {
    }

    public StatesYesNoDoesNotCorrespond(int id) {
        this.id = id;
    }

    public StatesYesNoDoesNotCorrespond(String state) {
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
