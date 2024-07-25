package com.opencars.netgo.cv.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@ApiModel(value = "Entidad Modelo de Experiencia Laboral", description = "Información completa que se almacena de una experiencia laboral cargada en cv")
public class Experience implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String position;
    @NotNull
    private String enterprise;
    @NotNull
    private int yearInit;
    @NotNull
    private int yearEnd;
    @NotNull
    private String detail;

    public Experience() {
    }

    public Experience(String position) {
        this.position = position;
    }

    public Experience(String position, String enterprise, int yearInit, int yearEnd, String detail) {
        this.position = position;
        this.enterprise = enterprise;
        this.yearInit = yearInit;
        this.yearEnd = yearEnd;
        this.detail = detail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public int getYearInit() {
        return yearInit;
    }

    public void setYearInit(int yearInit) {
        this.yearInit = yearInit;
    }

    public int getYearEnd() {
        return yearEnd;
    }

    public void setYearEnd(int yearEnd) {
        this.yearEnd = yearEnd;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
