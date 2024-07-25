package com.opencars.netgo.sales.viaticos.entity;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@ApiModel(value = "Entidad Modelo de Tesoreros para Sucursales", description = "Información completa que se almacena de los tesoreros para sucursales del proceso de compras")
public class TableTreasurers implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @OneToOne
    @JoinColumn(unique = true)
    private Branch branch;

    @NotNull
    @ManyToOne
    private User treasurer;

    public TableTreasurers() {
    }

    public TableTreasurers(Branch branch, User treasurer) {
        this.branch = branch;
        this.treasurer = treasurer;
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

    public User getTreasurer() {
        return treasurer;
    }

    public void setTreasurer(User treasurer) {
        this.treasurer = treasurer;
    }
}
