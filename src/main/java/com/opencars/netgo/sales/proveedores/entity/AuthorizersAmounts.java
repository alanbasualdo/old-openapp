package com.opencars.netgo.sales.proveedores.entity;

import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@ApiModel(value = "Entidad Modelo de Autorizantes Designados por monto en Cuentas Contables", description = "Información completa que se almacena de Autorizantes Designados por monto en Cuentas Contables")
public class AuthorizersAmounts implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private double initAmount;

    @NotNull
    private double endAmount;

    @NotNull
    @ManyToOne
    private User authorizer;

    @ManyToOne
    @JoinColumn(name = "concept_id")
    private SubcateorizedConcepts concept;

    public AuthorizersAmounts() {
    }

    public AuthorizersAmounts(double initAmount, double endAmount, User authorizer, SubcateorizedConcepts concept) {
        this.initAmount = initAmount;
        this.endAmount = endAmount;
        this.authorizer = authorizer;
        this.concept = concept;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getInitAmount() {
        return initAmount;
    }

    public void setInitAmount(double initAmount) {
        this.initAmount = initAmount;
    }

    public double getEndAmount() {
        return endAmount;
    }

    public void setEndAmount(double endAmount) {
        this.endAmount = endAmount;
    }

    public User getAuthorizer() {
        return authorizer;
    }

    public void setAuthorizer(User authorizer) {
        this.authorizer = authorizer;
    }

    public SubcateorizedConcepts getConcept() {
        return concept;
    }

    public void setConcept(SubcateorizedConcepts concept) {
        this.concept = concept;
    }
}
