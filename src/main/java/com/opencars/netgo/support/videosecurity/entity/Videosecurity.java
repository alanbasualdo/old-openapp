package com.opencars.netgo.support.videosecurity.entity;

import com.opencars.netgo.locations.entity.Branch;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@ApiModel(value = "Entidad Modelo de Videovigilancia", description = "Información completa que se almacena del monitoreo de seguridad")
public class Videosecurity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
    private String sn;
    @NotEmpty
    private String user;
    @NotEmpty
    private String password;
    private String ip;
    private String port;
    @Column(columnDefinition = "TEXT")
    private String note;

    public Videosecurity() {
    }

    public Videosecurity(Branch branch, String sn, String user, String password, String ip, String port, String note) {
        this.branch = branch;
        this.sn = sn;
        this.user = user;
        this.password = password;
        this.ip = ip;
        this.port = port;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
