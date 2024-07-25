package com.opencars.netgo.chat.dto;

import java.io.Serializable;

public class IdMongo implements Serializable {

    private String idMongo;

    public IdMongo() {
    }

    public IdMongo(String idMongo) {
        this.idMongo = idMongo;
    }

    public String getIdMongo() {
        return idMongo;
    }

    public void setIdMongo(String idMongo) {
        this.idMongo = idMongo;
    }

}
