package com.opencars.netgo.cv.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@ApiModel(value = "Entidad Modelo de Cursos y Certificaciones", description = "Información completa que se almacena de un curso o certificado cargado en cv")
public class Certifications implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String course;
    @NotNull
    private String institution;
    @NotNull
    private int yearEnd;

    private String certificate;

    public Certifications() {
    }

    public Certifications(String course) {
        this.course = course;
    }

    public Certifications(String course, String institution, int yearEnd, String certificate) {
        this.course = course;
        this.institution = institution;
        this.yearEnd = yearEnd;
        this.certificate = certificate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
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
