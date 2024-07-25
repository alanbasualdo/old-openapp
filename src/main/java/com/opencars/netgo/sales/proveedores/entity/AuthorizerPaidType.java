package com.opencars.netgo.sales.proveedores.entity;

import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@ApiModel(value = "Entidad Modelo de Autorizante de Tipo de Pago", description = "Información completa que se almacena del método de pago")
public class AuthorizerPaidType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @OneToOne
    private User colaborator;

    public AuthorizerPaidType() {
    }

    public AuthorizerPaidType(User colaborator) {
        this.colaborator = colaborator;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getColaborator() {
        return colaborator;
    }

    public void setColaborator(User colaborator) {
        this.colaborator = colaborator;
    }
}
