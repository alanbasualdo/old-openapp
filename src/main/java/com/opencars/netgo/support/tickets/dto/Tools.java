package com.opencars.netgo.support.tickets.dto;

import java.io.Serializable;

public class Tools implements Serializable {

    private int id;
    private String name;

    public Tools() {
    }

    public Tools(int id) {
        this.id = id;
    }

    public Tools(int id, String name) {
        this.id = id;
        this.name = name;
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
}
