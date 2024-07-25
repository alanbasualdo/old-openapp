package com.opencars.netgo.news.welcomes.dto;

public class WelcomesSummary {

    private int id;
    private String name;

    public WelcomesSummary() {
    }

    public WelcomesSummary(int id, String name) {
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
