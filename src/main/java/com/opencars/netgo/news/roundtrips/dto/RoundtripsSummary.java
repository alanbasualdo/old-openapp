package com.opencars.netgo.news.roundtrips.dto;

import com.opencars.netgo.auth.entity.Rol;
import com.opencars.netgo.locations.entity.Company;
import com.opencars.netgo.locations.entity.Sector;
import com.opencars.netgo.locations.entity.SubSector;

import java.time.LocalDateTime;

public class RoundtripsSummary {

    private int id;
    private String title;

    private LocalDateTime date;

    private Rol[] audience;

    private Company company;

    private Sector type;

    private SubSector subSector;

    public RoundtripsSummary() {
    }

    public RoundtripsSummary(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public RoundtripsSummary(int id, String title, LocalDateTime date, Rol[] audience, Company company, Sector type, SubSector subSector) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.audience = audience;
        this.company = company;
        this.type = type;
        this.subSector = subSector;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Sector getType() {
        return type;
    }

    public void setType(Sector type) {
        this.type = type;
    }

    public SubSector getSubSector() {
        return subSector;
    }

    public void setSubSector(SubSector subSector) {
        this.subSector = subSector;
    }
}
