package com.opencars.netgo.dms.finanzas.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@ApiModel(value = "Entidad Modelo de Registro de Actualizaciones de Cotización", description = "Información del registro de una cotización de dólar")
public class DolarRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    int valuePreview;

    @NotNull
    int valueCurrent;

    @NotNull
    LocalDateTime date;

    public DolarRegister() {
    }

    public DolarRegister(int valuePreview, int valueCurrent, LocalDateTime date) {
        this.valuePreview = valuePreview;
        this.valueCurrent = valueCurrent;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getValuePreview() {
        return valuePreview;
    }

    public void setValuePreview(int valuePreview) {
        this.valuePreview = valuePreview;
    }

    public int getValueCurrent() {
        return valueCurrent;
    }

    public void setValueCurrent(int valueCurrent) {
        this.valueCurrent = valueCurrent;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
