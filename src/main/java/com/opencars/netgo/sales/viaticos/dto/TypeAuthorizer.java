package com.opencars.netgo.sales.viaticos.dto;

public class TypeAuthorizer {

    private String type;

    private boolean isAuthorizer;

    private double initAmount;

    private double endAmount;

    public TypeAuthorizer() {
    }

    public TypeAuthorizer(String type, boolean isAuthorizer) {
        this.type = type;
        this.isAuthorizer = isAuthorizer;
    }

    public TypeAuthorizer(String type, boolean isAuthorizer, double initAmount, double endAmount) {
        this.type = type;
        this.isAuthorizer = isAuthorizer;
        this.initAmount = initAmount;
        this.endAmount = endAmount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAuthorizer() {
        return isAuthorizer;
    }

    public void setAuthorizer(boolean authorizer) {
        isAuthorizer = authorizer;
    }

    public double getInitAmount() {
        return initAmount;
    }

    public void setInitAmount(double initAmount) {
        this.initAmount = initAmount;
    }

    public double getEndAmount() {
        return endAmount;
    }

    public void setEndAmount(double endAmount) {
        this.endAmount = endAmount;
    }
}
