package com.opencars.netgo.news.letters.dto;

import java.time.LocalDateTime;

public class LetterListNotPublished {

    private int id;

    private String title;
    private LocalDateTime date;

    public LetterListNotPublished() {
    }

    public LetterListNotPublished(int id, String title, LocalDateTime date) {
        this.id = id;
        this.title = title;
        this.date = date;
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
}
