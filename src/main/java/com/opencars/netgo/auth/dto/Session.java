package com.opencars.netgo.auth.dto;

import java.util.Date;

public class Session {

    private Date end;
    private String active;
    private String name;
    private String cuil;
    private String sucursal;

    private String position;

    public Session() {
    }

    public Session(Date end, String active, String name, String cuil, String sucursal, String position) {
        this.end = end;
        this.active = active;
        this.name = name;
        this.cuil = cuil;
        this.sucursal = sucursal;
        this.position = position;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
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

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
