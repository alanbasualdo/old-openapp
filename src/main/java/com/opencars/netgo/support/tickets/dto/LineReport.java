package com.opencars.netgo.support.tickets.dto;

public class LineReport {

    private DataLineReport[] arrayData;

    private String[] labelsReport;

    public LineReport() {
    }

    public LineReport(DataLineReport[] arrayData, String[] labelsReport) {
        this.arrayData = arrayData;
        this.labelsReport = labelsReport;
    }

    public DataLineReport[] getArrayData() {
        return arrayData;
    }

    public void setArrayData(DataLineReport[] arrayData) {
        this.arrayData = arrayData;
    }

    public String[] getLabelsReport() {
        return labelsReport;
    }

    public void setLabelsReport(String[] labelsReport) {
        this.labelsReport = labelsReport;
    }
}
