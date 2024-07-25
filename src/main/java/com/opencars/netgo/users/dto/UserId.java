package com.opencars.netgo.users.dto;

public class UserId {

    private int id;
    private String mail;

    public UserId() {
    }

    public UserId(int id) {
        this.id = id;
    }

    public UserId(int id, String mail) {
        this.id = id;
        this.mail = mail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
