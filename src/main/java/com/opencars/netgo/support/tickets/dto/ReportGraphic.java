package com.opencars.netgo.support.tickets.dto;

public class ReportGraphic {

    private DataReport[] arrayData;

    private String[] labelsReport;

    public ReportGraphic() {

    }

    public ReportGraphic(DataReport[] arrayData, String[] labelsReport) {
        this.arrayData = arrayData;
        this.labelsReport = labelsReport;
    }

    public DataReport[] getArrayData() {
        return arrayData;
    }

    public void setArrayData(DataReport[] arrayData) {
        this.arrayData = arrayData;
    }

    public String[] getLabelsReport() {
        return labelsReport;
    }

    public void setLabelsReport(String[] labelsReport) {
        this.labelsReport = labelsReport;
    }
}

