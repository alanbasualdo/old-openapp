package com.opencars.netgo.sales.proveedores.entity;

import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@ApiModel(value = "Entidad Modelo de Conceptos Exceptuados para un Tesorero", description = "Información completa que se almacena de un concepto exceptuado para un tesorero")
public class ExceptedsConceptsTreasurers implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "concept_id")
    private SubcateorizedConcepts concept;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "treasurer_id")
    private User treasurer;

    public ExceptedsConceptsTreasurers() {
    }

    public ExceptedsConceptsTreasurers(int id) {
        this.id = id;
    }

    public ExceptedsConceptsTreasurers(SubcateorizedConcepts concept, User treasurer) {
        this.concept = concept;
        this.treasurer = treasurer;
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

    public User getTreasurer() {
        return treasurer;
    }

    public void setTreasurer(User treasurer) {
        this.treasurer = treasurer;
    }
}
