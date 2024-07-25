package com.opencars.netgo.sales.proveedores.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SalesToExport {

    private long id;
    private double amount;
    private String state;
    private String colaborator;
    private LocalDateTime dateInit;
    private String treasurer;
    private LocalDate dateEmision;
    private LocalDate dateAgreed;
    private String provider;

    public SalesToExport() {
    }

    public SalesToExport(long id, double amount, String state, String colaborator, LocalDateTime dateInit, String treasurer, LocalDate dateEmision, LocalDate dateAgreed, String provider) {
        this.id = id;
        this.amount = amount;
        this.state = state;
        this.colaborator = colaborator;
        this.dateInit = dateInit;
        this.treasurer = treasurer;
        this.dateEmision = dateEmision;
        this.dateAgreed = dateAgreed;
        this.provider = provider;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getColaborator() {
        return colaborator;
    }

    public void setColaborator(String colaborator) {
        this.colaborator = colaborator;
    }

    public LocalDateTime getDateInit() {
        return dateInit;
    }

    public void setDateInit(LocalDateTime dateInit) {
        this.dateInit = dateInit;
    }

    public String getTreasurer() {
        return treasurer;
    }

    public void setTreasurer(String treasurer) {
        this.treasurer = treasurer;
    }

    public LocalDate getDateEmision() {
        return dateEmision;
    }

    public void setDateEmision(LocalDate dateEmision) {
        this.dateEmision = dateEmision;
    }

    public LocalDate getDateAgreed() {
        return dateAgreed;
    }

    public void setDateAgreed(LocalDate dateAgreed) {
        this.dateAgreed = dateAgreed;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
