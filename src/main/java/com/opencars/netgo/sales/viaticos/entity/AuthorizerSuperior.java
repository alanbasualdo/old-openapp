package com.opencars.netgo.sales.viaticos.entity;

import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@ApiModel(value = "Entidad Modelo de Autorizante Superior", description = "Información completa que se almacena de un autorizante superior")
public class AuthorizerSuperior implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @OneToOne
    private User colaborator;

    public AuthorizerSuperior() {
    }

    public AuthorizerSuperior(User colaborator) {
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
