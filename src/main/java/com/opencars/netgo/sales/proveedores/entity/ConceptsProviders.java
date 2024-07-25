package com.opencars.netgo.sales.proveedores.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@ApiModel(value = "Entidad Modelo de Tipos de conceptos de Compras-Proveedores", description = "Información completa que se almacena del concepto de una compra-proveedor")
public class ConceptsProviders implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String concept;

    public ConceptsProviders() {
    }

    public ConceptsProviders(int id) {
        this.id = id;
    }

    public ConceptsProviders(int id, String concept) {
        this.id = id;
        this.concept = concept;
    }

    public ConceptsProviders(String concept) {
        this.concept = concept;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }
}
