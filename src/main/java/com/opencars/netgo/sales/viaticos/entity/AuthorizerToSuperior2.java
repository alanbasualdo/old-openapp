package com.opencars.netgo.sales.viaticos.entity;

import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@ApiModel(value = "Entidad Modelo de Autorizante de Autorizante Superior 2", description = "Información completa que se almacena de un autorizante superior 2")
public class AuthorizerToSuperior2 implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @OneToOne
    private User colaborator;

    public AuthorizerToSuperior2() {
    }

    public AuthorizerToSuperior2(User colaborator) {
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
