package com.opencars.netgo.sales.proveedores.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@ApiModel(value = "Entidad Modelo de Conceptos con Excepciones en tipos de pago", description = "Información completa que se almacena de una excepcion de tipo de pago en un concepto del módulo compras")
public class TableConceptsTypesPaids implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @OneToOne
    @JoinColumn(unique = true)
    private SubcateorizedConcepts concept;

    @NotNull
    @OneToOne
    @JoinColumn()
    private TypesPaids typePaid;

    public TableConceptsTypesPaids() {
    }

    public TableConceptsTypesPaids(SubcateorizedConcepts concept, TypesPaids typePaid) {
        this.concept = concept;
        this.typePaid = typePaid;
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

    public TypesPaids getTypePaid() {
        return typePaid;
    }

    public void setTypePaid(TypesPaids typePaid) {
        this.typePaid = typePaid;
    }
}
