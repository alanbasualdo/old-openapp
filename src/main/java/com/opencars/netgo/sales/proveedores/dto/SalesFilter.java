package com.opencars.netgo.sales.proveedores.dto;

import com.opencars.netgo.sales.proveedores.entity.StatesProveedores;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SalesFilter {

    private long id;
    private LocalDate dateEmision;
    private LocalDateTime dateInit;
    private LocalDate dateAgreed;
    private String provider;
    private double amount;
    private StatesProveedores state;

    public SalesFilter() {
    }

    public SalesFilter(long id, LocalDate dateEmision, LocalDateTime dateInit, LocalDate dateAgreed, String provider, double amount, StatesProveedores state) {
        this.id = id;
        this.dateEmision = dateEmision;
        this.dateInit = dateInit;
        this.dateAgreed = dateAgreed;
        this.provider = provider;
        this.amount = amount;
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDateEmision() {
        return dateEmision;
    }

    public void setDateEmision(LocalDate dateEmision) {
        this.dateEmision = dateEmision;
    }

    public LocalDateTime getDateInit() {
        return dateInit;
    }

    public void setDateInit(LocalDateTime dateInit) {
        this.dateInit = dateInit;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public StatesProveedores getState() {
        return state;
    }

    public void setState(StatesProveedores state) {
        this.state = state;
    }
}
