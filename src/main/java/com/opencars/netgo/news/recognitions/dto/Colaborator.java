package com.opencars.netgo.news.recognitions.dto;

import com.opencars.netgo.users.entity.Position;

import java.io.Serializable;
import java.util.List;

public class Colaborator implements Serializable {

    private String name;

    private String cuil;

    private List<Position> positions;

    public Colaborator() {
    }

    public Colaborator(String name, String cuil, List<Position> positions) {
        this.name = name;
        this.cuil = cuil;
        this.positions = positions;
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

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    private static final long serialVersionUID = 5017602741273654476L;

}
