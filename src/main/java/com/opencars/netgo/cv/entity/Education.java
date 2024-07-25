package com.opencars.netgo.cv.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@ApiModel(value = "Entidad Modelo de Títulos y Carreras", description = "Información completa que se almacena de un título cargado en cv")
public class Education implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String title;
    @NotNull
    private String institution;
    @NotNull
    private int yearInit;
    @NotNull
    private int yearEnd;

    private String certificate;

    public Education() {
    }

    public Education(String title) {
        this.title = title;
    }

    public Education(String title, String institution, int yearInit, int yearEnd, String certificate) {
        this.title = title;
        this.institution = institution;
        this.yearInit = yearInit;
        this.yearEnd = yearEnd;
        this.certificate = certificate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
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

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }
}
