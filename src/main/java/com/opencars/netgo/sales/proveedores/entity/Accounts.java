package com.opencars.netgo.sales.proveedores.entity;

import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@ApiModel(value = "Entidad Modelo de Cuentas Contables", description = "Información completa que se almacena de una cuenta contable")
public class Accounts implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "concept_id")
    private SubcateorizedConcepts concept;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    public Accounts() {
    }

    public Accounts(Accounts account) {
    }

    public Accounts(int id) {
        this.id = id;
    }

    public Accounts(SubcateorizedConcepts concept, User owner) {
        this.concept = concept;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SubcateorizedConcepts getConcept() {
        return concept;
    }

    public void setConcept(SubcateorizedConcepts concept) {
        this.concept = concept;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
