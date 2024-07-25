package com.opencars.netgo.support.networks.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@ApiModel(value = "Entidad Modelo de Dispositivos de Red", description = "Información completa que se almacena de un Dispositivo de Red")
public class Devices implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    private String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "type_id")
    private DeviceType type;

    public Devices() {
    }

    public Devices(int id) {
        this.id = id;
    }

    public Devices(String name, DeviceType type) {
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeviceType getType() {
        return type;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }
}
