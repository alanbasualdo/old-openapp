package com.opencars.netgo.users.dto;

import com.opencars.netgo.auth.entity.Sesiones;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class UserSessions implements Serializable {

    private String name;
    private String cuil;
    private String position;
    private Set<Sesiones> sesiones = new HashSet<>();

    public UserSessions() {

    }

    public UserSessions(String name, String cuil, String position, Set<Sesiones> sesiones) {
        this.name = name;
        this.cuil = cuil;
        this.position = position;
        this.sesiones = sesiones;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Set<Sesiones> getSesiones() {
        return sesiones;
    }

    public void setSesiones(Set<Sesiones> sesiones) {
        this.sesiones = sesiones;
    }
}
