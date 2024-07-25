package com.opencars.netgo.support.general.dto;

public class SupportAdminPVTACounts {

    private int ticketsFactInits;
    private int ticketsFactInProgress;
    private int ticketsCCInits;
    private int ticketsCCInProgress;
    private int ticketsContInits;
    private int ticketsContInProgress;
    private int ticketsTGInits;
    private int ticketsTGInProgress;


    public SupportAdminPVTACounts() {
    }

    public SupportAdminPVTACounts(int ticketsFactInits, int ticketsFactInProgress, int ticketsCCInits, int ticketsCCInProgress, int ticketsContInits, int ticketsContInProgress, int ticketsTGInits, int ticketsTGInProgress) {
        this.ticketsFactInits = ticketsFactInits;
        this.ticketsFactInProgress = ticketsFactInProgress;
        this.ticketsCCInits = ticketsCCInits;
        this.ticketsCCInProgress = ticketsCCInProgress;
        this.ticketsContInits = ticketsContInits;
        this.ticketsContInProgress = ticketsContInProgress;
        this.ticketsTGInits = ticketsTGInits;
        this.ticketsTGInProgress = ticketsTGInProgress;
    }

    public int getTicketsFactInits() {
        return ticketsFactInits;
    }

    public void setTicketsFactInits(int ticketsFactInits) {
        this.ticketsFactInits = ticketsFactInits;
    }

    public int getTicketsFactInProgress() {
        return ticketsFactInProgress;
    }

    public void setTicketsFactInProgress(int ticketsFactInProgress) {
        this.ticketsFactInProgress = ticketsFactInProgress;
    }

    public int getTicketsCCInits() {
        return ticketsCCInits;
    }

    public void setTicketsCCInits(int ticketsCCInits) {
        this.ticketsCCInits = ticketsCCInits;
    }

    public int getTicketsCCInProgress() {
        return ticketsCCInProgress;
    }

    public void setTicketsCCInProgress(int ticketsCCInProgress) {
        this.ticketsCCInProgress = ticketsCCInProgress;
    }

    public int getTicketsContInits() {
        return ticketsContInits;
    }

    public void setTicketsContInits(int ticketsContInits) {
        this.ticketsContInits = ticketsContInits;
    }

    public int getTicketsContInProgress() {
        return ticketsContInProgress;
    }

    public void setTicketsContInProgress(int ticketsContInProgress) {
        this.ticketsContInProgress = ticketsContInProgress;
    }

    public int getTicketsTGInits() {
        return ticketsTGInits;
    }

    public void setTicketsTGInits(int ticketsTGInits) {
        this.ticketsTGInits = ticketsTGInits;
    }

    public int getTicketsTGInProgress() {
        return ticketsTGInProgress;
    }

    public void setTicketsTGInProgress(int ticketsTGInProgress) {
        this.ticketsTGInProgress = ticketsTGInProgress;
    }
}
