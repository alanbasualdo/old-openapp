package com.opencars.netgo.support.tickets.dto;

public class DataReport {

    private int[] data;
    private String label;

    public DataReport() {
    }

    public DataReport(int[] data, String label) {
        this.data = data;
        this.label = label;
    }

    public int[] getData() {
        return data;
    }

    public void setData(int[] data) {
        this.data = data;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
