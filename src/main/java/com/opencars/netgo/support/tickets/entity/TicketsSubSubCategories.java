package com.opencars.netgo.support.tickets.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ticketssubsubcategories")
@ApiModel(value = "Entidad Modelo de SubSubCategoría de Ticket", description = "Información completa que se almacena de la subsubcategoría de ticket")
public class TicketsSubSubCategories implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String subSubCategory;

    @NotNull
    @OneToOne
    private TicketsSubcategories subCategory;

    public TicketsSubSubCategories() {
    }

    public TicketsSubSubCategories(int id) {
        this.id = id;
    }

    public TicketsSubSubCategories(String subSubCategory, TicketsSubcategories subCategory) {
        this.subSubCategory = subSubCategory;
        this.subCategory = subCategory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubSubCategory() {
        return subSubCategory;
    }

    public void setSubSubCategory(String subSubCategory) {
        this.subSubCategory = subSubCategory;
    }

    public TicketsSubcategories getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(TicketsSubcategories subCategory) {
        this.subCategory = subCategory;
    }
}
