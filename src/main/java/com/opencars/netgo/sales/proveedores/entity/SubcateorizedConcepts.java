package com.opencars.netgo.sales.proveedores.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@ApiModel(value = "Entidad Modelo de Subcategorización de Conceptos", description = "Información completa que se almacena de subcategorías de conceptos")
public class SubcateorizedConcepts implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    private String subConcept;

    @ManyToOne
    @JoinColumn(name = "concept_id")
    private ConceptsProviders concept;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "type_id")
    private AuthorizationTypes type;

    public SubcateorizedConcepts() {
    }

    public SubcateorizedConcepts(int id) {
        this.id = id;
    }

    public SubcateorizedConcepts(String subConcept, ConceptsProviders concept, AuthorizationTypes type) {
        this.subConcept = subConcept;
        this.concept = concept;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubConcept() {
        return subConcept;
    }

    public void setSubConcept(String subConcept) {
        this.subConcept = subConcept;
    }

    public ConceptsProviders getConcept() {
        return concept;
    }

    public void setConcept(ConceptsProviders concept) {
        this.concept = concept;
    }

    public AuthorizationTypes getType() {
        return type;
    }

    public void setType(AuthorizationTypes type) {
        this.type = type;
    }
}
