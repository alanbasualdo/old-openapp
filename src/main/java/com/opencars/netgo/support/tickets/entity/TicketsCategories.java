package com.opencars.netgo.support.tickets.entity;

import com.opencars.netgo.auth.entity.Rol;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ticketscategories")
@ApiModel(value = "Entidad Modelo de Categoría de Ticket", description = "Información completa que se almacena de la categoría de ticket")
public class TicketsCategories implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String category;

    @ManyToOne(fetch = FetchType.EAGER)
    private Rol rol;

    public TicketsCategories() {
    }

    public TicketsCategories(int id) {
        this.id = id;
    }

    public TicketsCategories(String category, Rol rol) {
        this.category = category;
        this.rol = rol;
    }

    public TicketsCategories(int id, String category, Rol rol) {
        this.id = id;
        this.category = category;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

}
