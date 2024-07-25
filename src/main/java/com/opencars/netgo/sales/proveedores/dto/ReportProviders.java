package com.opencars.netgo.sales.proveedores.dto;

public class ReportProviders {

    private String colaborator;
    private int idColaborator;
    private String subConcept;
    private int idSubconcept;
    private long invoiceCount;
    private double invoiceSum;
    private long rejecteds;
    private long notRejecteds;

    public ReportProviders() {
    }

    public ReportProviders(String colaborator,  int idColaborator, String subConcept, int idSubconcept, long invoiceCount, double invoiceSum, long rejecteds, long notRejecteds) {
        this.colaborator = colaborator;
        this.idColaborator = idColaborator;
        this.subConcept = subConcept;
        this.idSubconcept = idSubconcept;
        this.invoiceCount = invoiceCount;
        this.invoiceSum = invoiceSum;
        this.rejecteds = rejecteds;
        this.notRejecteds = notRejecteds;
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

    public String getSubConcept() {
        return subConcept;
    }

    public void setSubConcept(String subConcept) {
        this.subConcept = subConcept;
    }

    public int getIdSubconcept() {
        return idSubconcept;
    }

    public void setIdSubconcept(int idSubconcept) {
        this.idSubconcept = idSubconcept;
    }

    public long getInvoiceCount() {
        return invoiceCount;
    }

    public void setInvoiceCount(long invoiceCount) {
        this.invoiceCount = invoiceCount;
    }

    public double getInvoiceSum() {
        return invoiceSum;
    }

    public void setInvoiceSum(double invoiceSum) {
        this.invoiceSum = invoiceSum;
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
