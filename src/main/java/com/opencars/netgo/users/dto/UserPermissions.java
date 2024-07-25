package com.opencars.netgo.users.dto;

import com.opencars.netgo.auth.entity.Rol;

import java.io.Serializable;
import java.util.SortedSet;
import java.util.TreeSet;

public class UserPermissions implements Serializable {

    private String name;

    private SortedSet<Rol> roles = new TreeSet<>();

    public UserPermissions() {
    }

    public UserPermissions(String name, SortedSet<Rol> roles) {
        this.name = name;
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SortedSet<Rol> getRoles() {
        return roles;
    }

    public void setRoles(SortedSet<Rol> roles) {
        this.roles = roles;
    }
}
