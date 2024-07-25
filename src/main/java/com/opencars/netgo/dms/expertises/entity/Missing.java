package com.opencars.netgo.dms.expertises.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "missing")
@ApiModel(value = "Entidad Modelo de faltantes de peritajes", description = "Información completa que se almacena de un faltante de un peritaje")
public class Missing implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    private String missing;

    private Long price;

    public Missing() {
    }

    public Missing(int id) {
        this.id = id;
    }

    public Missing(String missing) {
        this.missing = missing;
    }

    public Missing(String missing, Long price) {
        this.missing = missing;
        this.price = price;
    }

    public Missing(int id, String missing, Long price) {
        this.id = id;
        this.missing = missing;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMissing() {
        return missing;
    }

    public void setMissing(String missing) {
        this.missing = missing;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
