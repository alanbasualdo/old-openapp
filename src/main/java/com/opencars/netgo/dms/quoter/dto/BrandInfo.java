package com.opencars.netgo.dms.quoter.dto;

import java.io.Serializable;

public class BrandInfo implements Serializable {

    private int id;
    private String name;

    public BrandInfo() {
    }

    public BrandInfo(int id, String name) {
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
