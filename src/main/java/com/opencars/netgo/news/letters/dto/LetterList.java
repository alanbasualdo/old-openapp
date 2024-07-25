package com.opencars.netgo.news.letters.dto;

import java.time.LocalDate;

public class LetterList {

    private int id;
    private LocalDate shortDate;

    public LetterList() {
    }

    public LetterList(int id, LocalDate shortDate) {
        this.id = id;
        this.shortDate = shortDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return shortDate;
    }

    public void setDate(LocalDate shortDate) {
        this.shortDate = shortDate;
    }
}
