package com.opencars.netgo.news.searchs.dto;

public class ListPositions {

    private int id;

    private String position;

    public ListPositions() {
    }

    public ListPositions(int id, String position) {
        this.id = id;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
