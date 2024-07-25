package com.opencars.netgo.support.tickets.dto;

import com.opencars.netgo.users.entity.User;

public class CardTechnician {

    private User technician;
    private int cantTicketsAssigned;
    private int cantTicketsTotal;
    private int cantTicketsResolved;
    private int cantTicketsInProgress;
    private int cantTicketsOnHold;
    private String timeAverageResolution;

    private Data[] arrayData;

    private Data[] arrayTotalResolvedVsTotal;

    private String[] labelsMyManagement;

    private String[] labelsTotal;

    private int totalOthers;

    private int cantNotManaged;

    public CardTechnician() {
    }

    public CardTechnician(User technician, int cantTicketsAssigned, int cantTicketsTotal, int cantTicketsResolved, int cantTicketsInProgress, int cantTicketsOnHold, String timeAverageResolution, Data[] arrayData, Data[] arrayTotalResolvedVsTotal, String[] labelsMyManagement, String[] labelsTotal, int totalOthers, int cantNotManaged) {
        this.technician = technician;
        this.cantTicketsAssigned = cantTicketsAssigned;
        this.cantTicketsTotal = cantTicketsTotal;
        this.cantTicketsResolved = cantTicketsResolved;
        this.cantTicketsInProgress = cantTicketsInProgress;
        this.cantTicketsOnHold = cantTicketsOnHold;
        this.timeAverageResolution = timeAverageResolution;
        this.arrayData = arrayData;
        this.arrayTotalResolvedVsTotal = arrayTotalResolvedVsTotal;
        this.labelsMyManagement = labelsMyManagement;
        this.labelsTotal = labelsTotal;
        this.totalOthers = totalOthers;
        this.cantNotManaged = cantNotManaged;
    }

    public User getTechnician() {
        return technician;
    }

    public void setTechnician(User technician) {
        this.technician = technician;
    }

    public int getCantTicketsAssigned() {
        return cantTicketsAssigned;
    }

    public void setCantTicketsAssigned(int cantTicketsAssigned) {
        this.cantTicketsAssigned = cantTicketsAssigned;
    }

    public int getCantTicketsTotal() {
        return cantTicketsTotal;
    }

    public void setCantTicketsTotal(int cantTicketsTotal) {
        this.cantTicketsTotal = cantTicketsTotal;
    }

    public int getCantTicketsResolved() {
        return cantTicketsResolved;
    }

    public void setCantTicketsResolved(int cantTicketsResolved) {
        this.cantTicketsResolved = cantTicketsResolved;
    }

    public int getCantTicketsInProgress() {
        return cantTicketsInProgress;
    }

    public void setCantTicketsInProgress(int cantTicketsInProgress) {
        this.cantTicketsInProgress = cantTicketsInProgress;
    }

    public int getCantTicketsOnHold() {
        return cantTicketsOnHold;
    }

    public void setCantTicketsOnHold(int cantTicketsOnHold) {
        this.cantTicketsOnHold = cantTicketsOnHold;
    }

    public String getTimeAverageResolution() {
        return timeAverageResolution;
    }

    public void setTimeAverageResolution(String timeAverageResolution) {
        this.timeAverageResolution = timeAverageResolution;
    }

    public Data[] getArrayData() {
        return arrayData;
    }

    public void setArrayData(Data[] arrayData) {
        this.arrayData = arrayData;
    }

    public Data[] getArrayTotalResolvedVsTotal() {
        return arrayTotalResolvedVsTotal;
    }

    public void setArrayTotalResolvedVsTotal(Data[] arrayTotalResolvedVsTotal) {
        this.arrayTotalResolvedVsTotal = arrayTotalResolvedVsTotal;
    }

    public String[] getLabelsMyManagement() {
        return labelsMyManagement;
    }

    public void setLabelsMyManagement(String[] labelsMyManagement) {
        this.labelsMyManagement = labelsMyManagement;
    }

    public String[] getLabelsTotal() {
        return labelsTotal;
    }

    public void setLabelsTotal(String[] labelsTotal) {
        this.labelsTotal = labelsTotal;
    }

    public int getTotalOthers() {
        return totalOthers;
    }

    public void setTotalOthers(int totalOthers) {
        this.totalOthers = totalOthers;
    }

    public int getCantNotManaged() {
        return cantNotManaged;
    }

    public void setCantNotManaged(int cantNotManaged) {
        this.cantNotManaged = cantNotManaged;
    }
}
