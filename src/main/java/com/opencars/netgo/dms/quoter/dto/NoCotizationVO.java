package com.opencars.netgo.dms.quoter.dto;

import java.time.LocalDateTime;

public class NoCotizationVO {

    private LocalDateTime date;
    private long priceMagazine;
    private long kmAverage;
    private String observation;

    private int code;

    public NoCotizationVO() {
    }

    public NoCotizationVO(LocalDateTime date, long priceMagazine, long kmAverage, String observation, int code) {
        this.date = date;
        this.priceMagazine = priceMagazine;
        this.kmAverage = kmAverage;
        this.observation = observation;
        this.code = code;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public long getPriceMagazine() {
        return priceMagazine;
    }

    public void setPriceMagazine(long priceMagazine) {
        this.priceMagazine = priceMagazine;
    }

    public long getKmAverage() {
        return kmAverage;
    }

    public void setKmAverage(long kmAverage) {
        this.kmAverage = kmAverage;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
