package com.opencars.netgo.sales.viaticos.dto;

import com.opencars.netgo.users.entity.User;

public class ReportColaborators {

    private User colaborator;

    private double amount;

    public ReportColaborators() {
    }

    public ReportColaborators(User colaborator, double amount) {
        this.colaborator = colaborator;
        this.amount = amount;
    }

    public User getColaborator() {
        return colaborator;
    }

    public void setColaborator(User colaborator) {
        this.colaborator = colaborator;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
