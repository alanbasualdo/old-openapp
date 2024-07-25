package com.opencars.netgo.sales.proveedores.entity;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;

@Entity
@ApiModel(value = "Entidad Modelo de Jefes de Sucursal", description = "Información completa que se almacena de un jefe de sucursal")
public class BranchChiefs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @OneToOne
    private Branch branch;

    @NotNull
    @ManyToOne
    private User chief;

    public BranchChiefs() {
    }

    public BranchChiefs(@NotNull Branch branch, @NotNull User chief) {
        this.branch = branch;
        this.chief = chief;
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

    public User getChief() {
        return chief;
    }

    public void setChief(User chief) {
        this.chief = chief;
    }
}
