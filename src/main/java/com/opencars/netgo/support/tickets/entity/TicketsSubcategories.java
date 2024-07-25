package com.opencars.netgo.support.tickets.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ticketssubcategories")
@ApiModel(value = "Entidad Modelo de Subcategoría de Ticket", description = "Información completa que se almacena de la subcategoría de ticket")
public class TicketsSubcategories implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String subCategory;

    @NotNull
    @OneToOne
    private TicketsCategories category;

    public TicketsSubcategories() {
    }

    public TicketsSubcategories(int id) {
        this.id = id;
    }


    public TicketsSubcategories(String subCategory, TicketsCategories category) {
        this.subCategory = subCategory;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public TicketsCategories getCategory() {
        return category;
    }

    public void setCategory(TicketsCategories category) {
        this.category = category;
    }
}

