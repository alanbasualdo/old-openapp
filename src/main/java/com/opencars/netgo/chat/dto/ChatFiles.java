package com.opencars.netgo.chat.dto;

public class ChatFiles {

    private String name;
    private String b64File;

    public ChatFiles() {
    }

    public ChatFiles(String name, String b64File) {
        this.name = name;
        this.b64File = b64File;
    }

    public String getB64File() {
        return b64File;
    }

    public void setB64File(String b64File) {
        this.b64File = b64File;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
