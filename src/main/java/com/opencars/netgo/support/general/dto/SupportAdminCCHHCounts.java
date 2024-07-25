package com.opencars.netgo.support.general.dto;

public class SupportAdminCCHHCounts {

    private int welcomesPending;
    private int lettersPending;
    private int recognitionsPending;

    public SupportAdminCCHHCounts() {
    }

    public SupportAdminCCHHCounts(int welcomesPending, int lettersPending, int recognitionsPending) {
        this.welcomesPending = welcomesPending;
        this.lettersPending = lettersPending;
        this.recognitionsPending = recognitionsPending;
    }

    public int getWelcomesPending() {
        return welcomesPending;
    }

    public void setWelcomesPending(int welcomesPending) {
        this.welcomesPending = welcomesPending;
    }

    public int getLettersPending() {
        return lettersPending;
    }

    public void setLettersPending(int lettersPending) {
        this.lettersPending = lettersPending;
    }

    public int getRecognitionsPending() {
        return recognitionsPending;
    }

    public void setRecognitionsPending(int recognitionsPending) {
        this.recognitionsPending = recognitionsPending;
    }
}
