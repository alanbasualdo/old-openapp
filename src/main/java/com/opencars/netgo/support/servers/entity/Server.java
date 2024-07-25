package com.opencars.netgo.support.servers.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@ApiModel(value = "Entidad Modelo de Servidores", description = "Información completa que se almacena de un servidor")
public class Server implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String ip;
    @NotEmpty
    private String user;
    @NotEmpty
    private String password;
    @NotEmpty
    private String operativeSystem;
    @NotEmpty
    private String accessType;
    @NotEmpty
    private String ubication;
    @Column(columnDefinition = "TEXT")
    private String note;

    public Server() {
    }

    public Server(String name, String ip, String user, String password, String operativeSystem, String accessType, String ubication, String note) {
        this.name = name;
        this.ip = ip;
        this.user = user;
        this.password = password;
        this.operativeSystem = operativeSystem;
        this.accessType = accessType;
        this.ubication = ubication;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOperativeSystem() {
        return operativeSystem;
    }

    public void setOperativeSystem(String operativeSystem) {
        this.operativeSystem = operativeSystem;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getUbication() {
        return ubication;
    }

    public void setUbication(String ubication) {
        this.ubication = ubication;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
