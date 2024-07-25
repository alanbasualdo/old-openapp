package com.opencars.netgo.support.tickets.dto;

import com.opencars.netgo.support.tickets.entity.*;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TicketsResume implements Serializable {

    private long id;
    private String title;
    private String category;
    private String subCategory;
    private String subSubCategory;
    private TicketsStates state;
    private LocalDateTime openingDate;
    private LocalDateTime editDate;
    private LocalDateTime closingDate;
    private String applicant;

    public TicketsResume() {
    }

    public TicketsResume(long id, String title, String category, String subCategory, String subSubCategory, TicketsStates state, LocalDateTime openingDate, LocalDateTime editDate, LocalDateTime closingDate, String applicant) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.subCategory = subCategory;
        this.subSubCategory = subSubCategory;
        this.state = state;
        this.openingDate = openingDate;
        this.editDate = editDate;
        this.closingDate = closingDate;
        this.applicant = applicant;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getSubSubCategory() {
        return subSubCategory;
    }

    public void setSubSubCategory(String subSubCategory) {
        this.subSubCategory = subSubCategory;
    }

    public TicketsStates getState() {
        return state;
    }

    public void setState(TicketsStates state) {
        this.state = state;
    }

    public LocalDateTime getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(LocalDateTime openingDate) {
        this.openingDate = openingDate;
    }

    public LocalDateTime getEditDate() {
        return editDate;
    }

    public void setEditDate(LocalDateTime editDate) {
        this.editDate = editDate;
    }

    public LocalDateTime getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(LocalDateTime closingDate) {
        this.closingDate = closingDate;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

}
