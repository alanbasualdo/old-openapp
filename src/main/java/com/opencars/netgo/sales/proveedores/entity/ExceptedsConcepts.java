package com.opencars.netgo.sales.proveedores.entity;

import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@ApiModel(value = "Entidad Modelo de Conceptos Exceptuados", description = "Información completa que se almacena de un concepto exceptuado")
public class ExceptedsConcepts implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "concept_id")
    private SubcateorizedConcepts concept;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "analyst_id")
    private User analyst;

    public ExceptedsConcepts() {
    }

    public ExceptedsConcepts(int id) {
        this.id = id;
    }

    public ExceptedsConcepts(SubcateorizedConcepts concept, User analyst) {
        this.concept = concept;
        this.analyst = analyst;
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

    public User getAnalyst() {
        return analyst;
    }

    public void setAnalyst(User analyst) {
        this.analyst = analyst;
    }
}
