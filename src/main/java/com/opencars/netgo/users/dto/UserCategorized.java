package com.opencars.netgo.users.dto;

public class UserCategorized {

    private int id;
    private int enable;
    private String mail;
    private String name;
    private String position;

    public UserCategorized() {
    }

    public UserCategorized(int id, int enable, String mail, String name, String position) {
        this.id = id;
        this.enable = enable;
        this.mail = mail;
        this.name = name;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
