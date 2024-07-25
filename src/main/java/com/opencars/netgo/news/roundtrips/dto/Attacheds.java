package com.opencars.netgo.news.roundtrips.dto;

import java.io.Serializable;

public class Attacheds implements Serializable {

    private String name;

    public Attacheds() {
    }

    public Attacheds(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
