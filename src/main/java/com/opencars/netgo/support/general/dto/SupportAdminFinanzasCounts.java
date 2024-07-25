package com.opencars.netgo.support.general.dto;

public class SupportAdminFinanzasCounts {

    private int ticketsFINInits;
    private int ticketsFINInProgress;

    public SupportAdminFinanzasCounts() {
    }

    public SupportAdminFinanzasCounts(int ticketsFINInits, int ticketsFINInProgress) {
        this.ticketsFINInits = ticketsFINInits;
        this.ticketsFINInProgress = ticketsFINInProgress;
    }

    public int getTicketsFINInits() {
        return ticketsFINInits;
    }

    public void setTicketsFINInits(int ticketsFINInits) {
        this.ticketsFINInits = ticketsFINInits;
    }

    public int getTicketsFINInProgress() {
        return ticketsFINInProgress;
    }

    public void setTicketsFINInProgress(int ticketsFINInProgress) {
        this.ticketsFINInProgress = ticketsFINInProgress;
    }
}
