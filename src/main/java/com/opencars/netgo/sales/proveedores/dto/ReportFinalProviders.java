package com.opencars.netgo.sales.proveedores.dto;

public class ReportFinalProviders {

    private String colaborator;
    private int idColaborator;
    private ConceptChargeds[] conceptChargeds;
    private double amountTotal;
    private long totalInvoices;

    public ReportFinalProviders() {
    }

    public ReportFinalProviders(String colaborator, int idColaborator, ConceptChargeds[] conceptChargeds, double amountTotal, long totalInvoices) {
        this.colaborator = colaborator;
        this.idColaborator = idColaborator;
        this.conceptChargeds = conceptChargeds;
        this.amountTotal = amountTotal;
        this.totalInvoices = totalInvoices;
    }

    public String getColaborator() {
        return colaborator;
    }

    public void setColaborator(String colaborator) {
        this.colaborator = colaborator;
    }

    public int getIdColaborator() {
        return idColaborator;
    }

    public void setIdColaborator(int idColaborator) {
        this.idColaborator = idColaborator;
    }

    public ConceptChargeds[] getConceptChargeds() {
        return conceptChargeds;
    }

    public void setConceptChargeds(ConceptChargeds[] conceptChargeds) {
        this.conceptChargeds = conceptChargeds;
    }

    public double getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(double amountTotal) {
        this.amountTotal = amountTotal;
    }

    public long getTotalInvoices() {
        return totalInvoices;
    }

    public void setTotalInvoices(long totalInvoices) {
        this.totalInvoices = totalInvoices;
    }
}
