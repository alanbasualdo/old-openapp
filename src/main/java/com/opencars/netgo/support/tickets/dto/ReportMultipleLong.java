package com.opencars.netgo.support.tickets.dto;

public class ReportMultipleLong {

    private DataReportLong[] arrayDataAmounts;

    private String[] labelsReportAmount;

    private DataReportLong[] arrayDataConcepts;

    private String[] labelsReportConcepts;

    public ReportMultipleLong() {
    }

    public ReportMultipleLong(DataReportLong[] arrayDataAmounts, String[] labelsReportAmount, DataReportLong[] arrayDataConcepts, String[] labelsReportConcepts) {
        this.arrayDataAmounts = arrayDataAmounts;
        this.labelsReportAmount = labelsReportAmount;
        this.arrayDataConcepts = arrayDataConcepts;
        this.labelsReportConcepts = labelsReportConcepts;
    }

    public DataReportLong[] getArrayDataAmounts() {
        return arrayDataAmounts;
    }

    public void setArrayDataAmounts(DataReportLong[] arrayDataAmounts) {
        this.arrayDataAmounts = arrayDataAmounts;
    }

    public String[] getLabelsReportAmount() {
        return labelsReportAmount;
    }

    public void setLabelsReportAmount(String[] labelsReportAmount) {
        this.labelsReportAmount = labelsReportAmount;
    }

    public DataReportLong[] getArrayDataConcepts() {
        return arrayDataConcepts;
    }

    public void setArrayDataConcepts(DataReportLong[] arrayDataConcepts) {
        this.arrayDataConcepts = arrayDataConcepts;
    }

    public String[] getLabelsReportConcepts() {
        return labelsReportConcepts;
    }

    public void setLabelsReportConcepts(String[] labelsReportConcepts) {
        this.labelsReportConcepts = labelsReportConcepts;
    }
}
