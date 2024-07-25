package com.opencars.netgo.support.tickets.dto;

public class DataReportLong {

    private long[] data;
    private String label;

    public DataReportLong() {
    }

    public DataReportLong(long[] data, String label) {
        this.data = data;
        this.label = label;
    }

    public long[] getData() {
        return data;
    }

    public void setData(long[] data) {
        this.data = data;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
