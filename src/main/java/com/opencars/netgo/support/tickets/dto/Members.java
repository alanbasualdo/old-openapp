package com.opencars.netgo.support.tickets.dto;

import java.io.Serializable;

public class Members implements Serializable {

    private String name;

    public Members() {
    }

    public Members(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

