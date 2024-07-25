package com.opencars.netgo.sales.viaticos.entity;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@ApiModel(value = "Entidad Modelo de Analistas Contables para Sucursales", description = "Información completa que se almacena de analistas contables para sucursales del proceso de compras")
public class TableContable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @OneToOne
    @JoinColumn(unique = true)
    private Branch branch;

    @NotNull
    @ManyToOne
    private User analyst;

    public TableContable() {
    }

    public TableContable(Branch branch, User analyst) {
        this.branch = branch;
        this.analyst = analyst;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public User getAnalyst() {
        return analyst;
    }

    public void setAnalyst(User analyst) {
        this.analyst = analyst;
    }
}
