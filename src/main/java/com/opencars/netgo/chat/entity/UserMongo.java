package com.opencars.netgo.chat.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document(collection = "users")
public class UserMongo implements Serializable {

    @Id
    private String id;
    private int idIntranet;
    private String name;
    private String email;
    private String password;
    private String cuil;
    private String role;
    private Date lastSeen;

    public UserMongo(int idIntranet, String name, String email, String password, String cuil, String role, Date lastSeen) {
        this.idIntranet = idIntranet;
        this.name = name;
        this.email = email;
        this.password = password;
        this.cuil = cuil;
        this.role = role;
        this.lastSeen = lastSeen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIdIntranet() {
        return idIntranet;
    }

    public void setIdIntranet(int idIntranet) {
        this.idIntranet = idIntranet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Date lastSeen) {
        this.lastSeen = lastSeen;
    }
}
