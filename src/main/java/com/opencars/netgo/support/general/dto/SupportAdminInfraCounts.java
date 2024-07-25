package com.opencars.netgo.support.general.dto;

public class SupportAdminInfraCounts {

    private int ticketsTGInfraInits;
    private int ticketsTGInfraInProgress;

    public SupportAdminInfraCounts() {
    }

    public SupportAdminInfraCounts(int ticketsTGInfraInits, int ticketsTGInfraInProgress) {
        this.ticketsTGInfraInits = ticketsTGInfraInits;
        this.ticketsTGInfraInProgress = ticketsTGInfraInProgress;
    }

    public int getTicketsTGInfraInits() {
        return ticketsTGInfraInits;
    }

    public void setTicketsTGInfraInits(int ticketsTGInfraInits) {
        this.ticketsTGInfraInits = ticketsTGInfraInits;
    }

    public int getTicketsTGInfraInProgress() {
        return ticketsTGInfraInProgress;
    }

    public void setTicketsTGInfraInProgress(int ticketsTGInfraInProgress) {
        this.ticketsTGInfraInProgress = ticketsTGInfraInProgress;
    }
}
