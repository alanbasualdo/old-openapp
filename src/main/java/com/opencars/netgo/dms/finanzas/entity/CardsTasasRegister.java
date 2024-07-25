package com.opencars.netgo.dms.finanzas.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@ApiModel(value = "Entidad Modelo de Registro de Actualizaciones de Tasas de Tarjetas", description = "Información del registro de una tasa de tarjeta de crédito")
public class CardsTasasRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotNull
    Double valuePreview;

    @NotNull
    Double valueCurrent;
    @NotNull
    LocalDateTime date;
    @NotEmpty
    String type;

    public CardsTasasRegister() {
    }

    public CardsTasasRegister(Double valuePreview, Double valueCurrent, LocalDateTime date, String type) {
        this.valuePreview = valuePreview;
        this.valueCurrent = valueCurrent;
        this.date = date;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValuePreview() {
        return valuePreview;
    }

    public void setValuePreview(Double valuePreview) {
        this.valuePreview = valuePreview;
    }

    public Double getValueCurrent() {
        return valueCurrent;
    }

    public void setValueCurrent(Double valueCurrent) {
        this.valueCurrent = valueCurrent;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
