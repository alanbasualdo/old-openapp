package com.opencars.netgo.support.general.dto;

public class SupportAdminLogiCounts {

    private int ticketsLogiInits;
    private int ticketsLogiInProgress;

    public SupportAdminLogiCounts() {
    }

    public SupportAdminLogiCounts(int ticketsLogiInits, int ticketsLogiInProgress) {
        this.ticketsLogiInits = ticketsLogiInits;
        this.ticketsLogiInProgress = ticketsLogiInProgress;
    }

    public int getTicketsLogiInits() {
        return ticketsLogiInits;
    }

    public void setTicketsLogiInits(int ticketsLogiInits) {
        this.ticketsLogiInits = ticketsLogiInits;
    }

    public int getTicketsLogiInProgress() {
        return ticketsLogiInProgress;
    }

    public void setTicketsLogiInProgress(int ticketsLogiInProgress) {
        this.ticketsLogiInProgress = ticketsLogiInProgress;
    }
}
