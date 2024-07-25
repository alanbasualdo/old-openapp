package com.opencars.netgo.auth.entity;

import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "sesiones")
@ApiModel(value = "Entidad Modelo de Sesiones", description = "Información completa de las sesiones de usuarios")
public class Sesiones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @OneToOne
    private User colaborator;
    @NotNull
    private String token;
    @NotNull
    private Date init;
    @NotNull
    private Date end;
    @NotNull
    private String active;
    @NotNull
    private String navToken;
    @NotNull
    private LocalDate today;

    public Sesiones() {
    }

    public Sesiones(User colaborator, String token, Date init, Date end, String active, String navToken, LocalDate today) {
        this.colaborator = colaborator;
        this.token = token;
        this.init = init;
        this.end = end;
        this.active = active;
        this.navToken = navToken;
        this.today = today;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getColaborator() {
        return colaborator;
    }

    public void setColaborator(User colaborator) {
        this.colaborator = colaborator;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getInit() {
        return init;
    }

    public void setInit(Date init) {
        this.init = init;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getNavToken() {
        return navToken;
    }

    public void setNavToken(String navToken) {
        this.navToken = navToken;
    }

    public LocalDate getToday() {
        return today;
    }

    public void setToday(LocalDate today) {
        this.today = today;
    }
}
