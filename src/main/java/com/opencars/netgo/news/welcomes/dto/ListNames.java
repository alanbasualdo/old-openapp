package com.opencars.netgo.news.welcomes.dto;

import java.time.LocalDateTime;

public class ListNames {

    private int id;
    private String name;
    private LocalDateTime entryDate;

    public ListNames() {
    }

    public ListNames(int id, String name, LocalDateTime entryDate) {
        this.id = id;
        this.name = name;
        this.entryDate = entryDate;
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

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }


}
