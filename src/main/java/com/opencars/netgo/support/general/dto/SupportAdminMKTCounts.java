package com.opencars.netgo.support.general.dto;

public class SupportAdminMKTCounts {

    private int welcomesPending;
    private int recognitionsPending;
    private int ticketsComInits;
    private int ticketsComInProgress;
    private int ticketsAdmInits;
    private int ticketsAdmInProgress;
    private int ticketsPVInits;
    private int ticketsPVInProgress;

    public SupportAdminMKTCounts() {
    }

    public SupportAdminMKTCounts(int welcomesPending, int recognitionsPending, int ticketsComInits, int ticketsComInProgress, int ticketsAdmInits, int ticketsAdmInProgress, int ticketsPVInits, int ticketsPVInProgress) {
        this.welcomesPending = welcomesPending;
        this.recognitionsPending = recognitionsPending;
        this.ticketsComInits = ticketsComInits;
        this.ticketsComInProgress = ticketsComInProgress;
        this.ticketsAdmInits = ticketsAdmInits;
        this.ticketsAdmInProgress = ticketsAdmInProgress;
        this.ticketsPVInits = ticketsPVInits;
        this.ticketsPVInProgress = ticketsPVInProgress;
    }

    public int getWelcomesPending() {
        return welcomesPending;
    }

    public void setWelcomesPending(int welcomesPending) {
        this.welcomesPending = welcomesPending;
    }

    public int getRecognitionsPending() {
        return recognitionsPending;
    }

    public void setRecognitionsPending(int recognitionsPending) {
        this.recognitionsPending = recognitionsPending;
    }

    public int getTicketsComInits() {
        return ticketsComInits;
    }

    public void setTicketsComInits(int ticketsComInits) {
        this.ticketsComInits = ticketsComInits;
    }

    public int getTicketsComInProgress() {
        return ticketsComInProgress;
    }

    public void setTicketsComInProgress(int ticketsComInProgress) {
        this.ticketsComInProgress = ticketsComInProgress;
    }

    public int getTicketsAdmInits() {
        return ticketsAdmInits;
    }

    public void setTicketsAdmInits(int ticketsAdmInits) {
        this.ticketsAdmInits = ticketsAdmInits;
    }

    public int getTicketsAdmInProgress() {
        return ticketsAdmInProgress;
    }

    public void setTicketsAdmInProgress(int ticketsAdmInProgress) {
        this.ticketsAdmInProgress = ticketsAdmInProgress;
    }

    public int getTicketsPVInits() {
        return ticketsPVInits;
    }

    public void setTicketsPVInits(int ticketsPVInits) {
        this.ticketsPVInits = ticketsPVInits;
    }

    public int getTicketsPVInProgress() {
        return ticketsPVInProgress;
    }

    public void setTicketsPVInProgress(int ticketsPVInProgress) {
        this.ticketsPVInProgress = ticketsPVInProgress;
    }
}
