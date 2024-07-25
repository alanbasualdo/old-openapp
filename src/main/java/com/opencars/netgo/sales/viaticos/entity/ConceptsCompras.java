package com.opencars.netgo.sales.viaticos.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@ApiModel(value = "Entidad Modelo de Tipos de conceptos de Compras", description = "Información completa que se almacena del concepto de una compra")
public class ConceptsCompras implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String concept;

    private String legend;

    public ConceptsCompras() {
    }

    public ConceptsCompras(int id) {
        this.id = id;
    }

    public ConceptsCompras(int id, String concept) {
        this.id = id;
        this.concept = concept;
    }

    public ConceptsCompras(String concept, String legend) {
        this.concept = concept;
        this.legend = legend;
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

    public String getLegend() {
        return legend;
    }

    public void setLegend(String legend) {
        this.legend = legend;
    }
}
