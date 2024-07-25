package com.opencars.netgo.cv.dto;

public class CountCVs {

    private String lblCreated;
    private String lblNotCreated;
    private int cantCreated;
    private int cantNotCreated;

    public CountCVs() {
    }

    public CountCVs(String lblCreated, String lblNotCreated, int cantCreated, int cantNotCreated) {
        this.lblCreated = lblCreated;
        this.lblNotCreated = lblNotCreated;
        this.cantCreated = cantCreated;
        this.cantNotCreated = cantNotCreated;
    }

    public String getLblCreated() {
        return lblCreated;
    }

    public void setLblCreated(String lblCreated) {
        this.lblCreated = lblCreated;
    }

    public String getLblNotCreated() {
        return lblNotCreated;
    }

    public void setLblNotCreated(String lblNotCreated) {
        this.lblNotCreated = lblNotCreated;
    }

    public int getCantCreated() {
        return cantCreated;
    }

    public void setCantCreated(int cantCreated) {
        this.cantCreated = cantCreated;
    }

    public int getCantNotCreated() {
        return cantNotCreated;
    }

    public void setCantNotCreated(int cantNotCreated) {
        this.cantNotCreated = cantNotCreated;
    }
}
