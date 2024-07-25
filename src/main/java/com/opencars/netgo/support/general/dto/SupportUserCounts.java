package com.opencars.netgo.support.general.dto;

public class SupportUserCounts {

    private int ticketsApplicantsInits;
    private int ticketsApplicantsInProgress;

    public SupportUserCounts() {
    }

    public SupportUserCounts(int ticketsApplicantsInits, int ticketsApplicantsInProgress) {
        this.ticketsApplicantsInits = ticketsApplicantsInits;
        this.ticketsApplicantsInProgress = ticketsApplicantsInProgress;
    }

    public int getTicketsApplicantsInits() {
        return ticketsApplicantsInits;
    }

    public void setTicketsApplicantsInits(int ticketsApplicantsInits) {
        this.ticketsApplicantsInits = ticketsApplicantsInits;
    }

    public int getTicketsApplicantsInProgress() {
        return ticketsApplicantsInProgress;
    }

    public void setTicketsApplicantsInProgress(int ticketsApplicantsInProgress) {
        this.ticketsApplicantsInProgress = ticketsApplicantsInProgress;
    }
}
