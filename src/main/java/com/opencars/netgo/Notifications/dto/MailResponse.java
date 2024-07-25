package com.opencars.netgo.Notifications.dto;

public class MailResponse {

    private String response;
    private int code;

    public MailResponse() {
    }

    public MailResponse(String response, int code) {
        this.response = response;
        this.code = code;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

