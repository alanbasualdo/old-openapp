package com.opencars.netgo.sales.viaticos.dto;

import com.opencars.netgo.users.entity.User;

public class ReportFinallyColaborators {

    private User colaborator;

    private double amountMax90days;
    private double amount90days;
    private double amount60days;
    private double amount30days;
    private double amountTotal;

    public ReportFinallyColaborators() {
    }

    public ReportFinallyColaborators(User colaborator, double amountMax90days, double amount90days, double amount60days, double amount30days, double amountTotal) {
        this.colaborator = colaborator;
        this.amountMax90days = amountMax90days;
        this.amount90days = amount90days;
        this.amount60days = amount60days;
        this.amount30days = amount30days;
        this.amountTotal = amountTotal;
    }

    public User getColaborator() {
        return colaborator;
    }

    public void setColaborator(User colaborator) {
        this.colaborator = colaborator;
    }

    public double getAmountMax90days() {
        return amountMax90days;
    }

    public void setAmountMax90days(double amountMax90days) {
        this.amountMax90days = amountMax90days;
    }

    public double getAmount90days() {
        return amount90days;
    }

    public void setAmount90days(double amount90days) {
        this.amount90days = amount90days;
    }

    public double getAmount60days() {
        return amount60days;
    }

    public void setAmount60days(double amount60days) {
        this.amount60days = amount60days;
    }

    public double getAmount30days() {
        return amount30days;
    }

    public void setAmount30days(double amount30days) {
        this.amount30days = amount30days;
    }

    public double getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(double amountTotal) {
        this.amountTotal = amountTotal;
    }

}
