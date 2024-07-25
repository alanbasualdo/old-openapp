package com.opencars.netgo.support.tickets.dto;

public class ReportGraphicLong {

    private DataReportLong[] arrayData;

    private String[] labelsReport;

    public ReportGraphicLong() {
    }

    public ReportGraphicLong(DataReportLong[] arrayData, String[] labelsReport) {
        this.arrayData = arrayData;
        this.labelsReport = labelsReport;
    }

    public DataReportLong[] getArrayData() {
        return arrayData;
    }

    public void setArrayData(DataReportLong[] arrayData) {
        this.arrayData = arrayData;
    }

    public String[] getLabelsReport() {
        return labelsReport;
    }

    public void setLabelsReport(String[] labelsReport) {
        this.labelsReport = labelsReport;
    }
}
