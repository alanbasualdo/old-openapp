package com.opencars.netgo.support.general.dto;

public class SupportAdminComprasCounts {

    private int ticketsComprasInits;
    private int ticketsComprasInProgress;

    public SupportAdminComprasCounts() {
    }

    public SupportAdminComprasCounts(int ticketsComprasInits, int ticketsComprasInProgress) {
        this.ticketsComprasInits = ticketsComprasInits;
        this.ticketsComprasInProgress = ticketsComprasInProgress;
    }

    public int getTicketsComprasInits() {
        return ticketsComprasInits;
    }

    public void setTicketsComprasInits(int ticketsComprasInits) {
        this.ticketsComprasInits = ticketsComprasInits;
    }

    public int getTicketsComprasInProgress() {
        return ticketsComprasInProgress;
    }

    public void setTicketsComprasInProgress(int ticketsComprasInProgress) {
        this.ticketsComprasInProgress = ticketsComprasInProgress;
    }
}
