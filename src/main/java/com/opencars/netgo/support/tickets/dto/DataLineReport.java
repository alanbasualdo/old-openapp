package com.opencars.netgo.support.tickets.dto;

public class DataLineReport {

    private int[] data;
    private String label;
    private boolean fill;
    private double tension;
    private String borderColor;
    private String backgroundColor;

    public DataLineReport() {
    }

    public DataLineReport(int[] data, String label, boolean fill, double tension, String borderColor, String backgroundColor) {
        this.data = data;
        this.label = label;
        this.fill = fill;
        this.tension = tension;
        this.borderColor = borderColor;
        this.backgroundColor = backgroundColor;
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

    public boolean isFill() {
        return fill;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }

    public double getTension() {
        return tension;
    }

    public void setTension(double tension) {
        this.tension = tension;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
