package com.opencars.netgo.support.networks.entity;

import com.opencars.netgo.locations.entity.Branch;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@ApiModel(value = "Entidad Modelo de Redes", description = "Información completa que se almacena de una red")
public class Networks implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
    @NotEmpty
    private String network;
    @NotEmpty
    private String password;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "device_id")
    private Devices device;

    public Networks() {
    }

    public Networks(Branch branch, String network, String password, Devices device) {
        this.branch = branch;
        this.network = network;
        this.password = password;
        this.device = device;
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

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Devices getDevice() {
        return device;
    }

    public void setDevice(Devices device) {
        this.device = device;
    }
}
