package com.opencars.netgo.sales.proveedores.dto;

public class ConceptChargeds {

    private String subConcept;
    private long idSubconcept;
    private long count;
    private double sum;
    private long rejecteds;
    private long notRejecteds;

    public ConceptChargeds() {
    }

    public ConceptChargeds(String subConcept, long idSubconcept, long count, double sum, long rejecteds, long notRejecteds) {
        this.subConcept = subConcept;
        this.idSubconcept = idSubconcept;
        this.count = count;
        this.sum = sum;
        this.rejecteds = rejecteds;
        this.notRejecteds = notRejecteds;
    }

    public String getSubConcept() {
        return subConcept;
    }

    public void setSubConcept(String subConcept) {
        this.subConcept = subConcept;
    }

    public long getIdSubconcept() {
        return idSubconcept;
    }

    public void setIdSubconcept(long idSubconcept) {
        this.idSubconcept = idSubconcept;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public long getRejecteds() {
        return rejecteds;
    }

    public void setRejecteds(long rejecteds) {
        this.rejecteds = rejecteds;
    }

    public long getNotRejecteds() {
        return notRejecteds;
    }

    public void setNotRejecteds(long notRejecteds) {
        this.notRejecteds = notRejecteds;
    }
}
