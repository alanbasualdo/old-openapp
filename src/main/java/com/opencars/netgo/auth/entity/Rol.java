package com.opencars.netgo.auth.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

@Entity
@Table(name = "roles")
@ApiModel(value = "Entidad Modelo de Rol", description = "Información completa que se almacena del rol de usuario")
public class Rol implements Serializable, Comparable<Rol> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String rolName;
    @NotNull
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull
    private String type;

    public Rol() {
    }

    public Rol(int id) {
        this.id = id;
    }

    public Rol(String rolName) {
        this.rolName = rolName;
    }

    public Rol(String rolName, String description, String type) {
        this.rolName = rolName;
        this.description = description;
        this.type = type;
    }

    public Rol(int id, String rolName, String description, String type) {
        this.id = id;
        this.rolName = rolName;
        this.description = description;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRolName() {
        return rolName;
    }

    public void setRolName(String rolName) {
        this.rolName = rolName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int compareTo(Rol rol) {
        return Objects.compare(rolName, rol.getRolName(), Comparator.nullsLast(String::compareTo));
    }

    private static final long serialVersionUID = -1746872685452825086L;
}
