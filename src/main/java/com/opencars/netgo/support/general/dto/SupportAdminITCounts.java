package com.opencars.netgo.support.general.dto;

public class SupportAdminITCounts {

    private int ticketsNIInits;
    private int ticketsNIInProgress;
    private int ticketsBCInits;
    private int ticketsBCInProgress;
    private int ticketsITInits;
    private int ticketsITInProgress;

    public SupportAdminITCounts() {
    }

    public SupportAdminITCounts(int ticketsNIInits, int ticketsNIInProgress, int ticketsBCInits, int ticketsBCInProgress, int ticketsITInits, int ticketsITInProgress) {
        this.ticketsNIInits = ticketsNIInits;
        this.ticketsNIInProgress = ticketsNIInProgress;
        this.ticketsBCInits = ticketsBCInits;
        this.ticketsBCInProgress = ticketsBCInProgress;
        this.ticketsITInits = ticketsITInits;
        this.ticketsITInProgress = ticketsITInProgress;
    }

    public int getTicketsNIInits() {
        return ticketsNIInits;
    }

    public void setTicketsNIInits(int ticketsNIInits) {
        this.ticketsNIInits = ticketsNIInits;
    }

    public int getTicketsNIInProgress() {
        return ticketsNIInProgress;
    }

    public void setTicketsNIInProgress(int ticketsNIInProgress) {
        this.ticketsNIInProgress = ticketsNIInProgress;
    }

    public int getTicketsBCInits() {
        return ticketsBCInits;
    }

    public void setTicketsBCInits(int ticketsBCInits) {
        this.ticketsBCInits = ticketsBCInits;
    }

    public int getTicketsBCInProgress() {
        return ticketsBCInProgress;
    }

    public void setTicketsBCInProgress(int ticketsBCInProgress) {
        this.ticketsBCInProgress = ticketsBCInProgress;
    }

    public int getTicketsITInits() {
        return ticketsITInits;
    }

    public void setTicketsITInits(int ticketsITInits) {
        this.ticketsITInits = ticketsITInits;
    }

    public int getTicketsITInProgress() {
        return ticketsITInProgress;
    }

    public void setTicketsITInProgress(int ticketsITInProgress) {
        this.ticketsITInProgress = ticketsITInProgress;
    }
}
