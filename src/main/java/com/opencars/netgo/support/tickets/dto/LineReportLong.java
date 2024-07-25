package com.opencars.netgo.support.tickets.dto;

public class LineReportLong {

    private DataLineLong[] arrayData;

    private String[] labelsReport;

    public LineReportLong() {
    }

    public LineReportLong(DataLineLong[] arrayData, String[] labelsReport) {
        this.arrayData = arrayData;
        this.labelsReport = labelsReport;
    }

    public DataLineLong[] getArrayData() {
        return arrayData;
    }

    public void setArrayData(DataLineLong[] arrayData) {
        this.arrayData = arrayData;
    }

    public String[] getLabelsReport() {
        return labelsReport;
    }

    public void setLabelsReport(String[] labelsReport) {
        this.labelsReport = labelsReport;
    }
}

