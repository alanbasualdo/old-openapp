package com.opencars.netgo.news.recognitions.dto;

import java.time.LocalDateTime;

public class RecognitionsList {

    private int id;
    private String title;

    private LocalDateTime createdAt;

    public RecognitionsList() {
    }

    public RecognitionsList(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public RecognitionsList(int id, String title, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
