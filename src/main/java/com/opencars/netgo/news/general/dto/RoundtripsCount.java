package com.opencars.netgo.news.general.dto;

public class RoundtripsCount {

    int id;
    String name;
    int cant;

    public RoundtripsCount() {
    }

    public RoundtripsCount(int id, String name, int cant) {
        this.id = id;
        this.name = name;
        this.cant = cant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
