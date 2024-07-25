package com.opencars.netgo.news.roundtrips.dto;

import com.opencars.netgo.auth.entity.Rol;

import java.time.LocalDateTime;

public class RoundtripsList {

    private int id;
    private String title;
    private LocalDateTime date;

    private Rol[] audience;


    public RoundtripsList() {
    }

    public RoundtripsList(int id, String title, LocalDateTime date, Rol[] audience) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.audience = audience;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Rol[] getAudience() {
        return audience;
    }

    public void setAudience(Rol[] audience) {
        this.audience = audience;
    }
}
