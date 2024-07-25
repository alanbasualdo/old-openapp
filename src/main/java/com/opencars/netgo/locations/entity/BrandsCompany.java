package com.opencars.netgo.locations.entity;

import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "brandscompany")
@ApiModel(value = "Entidad Modelo de Marcas para Compañías", description = "Información completa que se almacena de una Marca de Vehículos")
public class BrandsCompany implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public BrandsCompany() {
    }

    public BrandsCompany(int id) {
        this.id = id;
    }

    public BrandsCompany(String name, Company company) {
        this.name = name;
        this.company = company;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
