package com.opencars.netgo.support.tickets.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ticketsstates")
@ApiModel(value = "Entidad Modelo de Estado de Ticket", description = "Información completa que se almacena del estado del ticket")
public class TicketsStates implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String state;

    public TicketsStates() {
    }

    public TicketsStates(int id) {
        this.id = id;
    }

    public TicketsStates(int id, String state) {
        this.id = id;
        this.state = state;
    }

    public TicketsStates(String state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
