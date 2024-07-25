package com.opencars.netgo.support.tickets.dto;

public class DataLong {

    private long[] data;

    public DataLong() {
    }

    public DataLong(long[] data) {
        this.data = data;
    }

    public long[] getData() {
        return data;
    }

    public void setData(long[] data) {
        this.data = data;
    }
}
