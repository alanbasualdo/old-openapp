package com.opencars.netgo.users.dto;

import java.time.LocalDate;

public class Birthday {

    private int id;
    private String name;
    private String imgProfile;
    private String branch;
    private String company;
    private String birthDate;
    private String cuil;

    private LocalDate date;

    public Birthday() {
    }

    public Birthday(int id, String name, String imgProfile, String branch, String company, String birthDate, String cuil, LocalDate date) {
        this.id = id;
        this.name = name;
        this.imgProfile = imgProfile;
        this.branch = branch;
        this.company = company;
        this.birthDate = birthDate;
        this.cuil = cuil;
        this.date = date;
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

    public String getImgProfile() {
        return imgProfile;
    }

    public void setImgProfile(String imgProfile) {
        this.imgProfile = imgProfile;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
