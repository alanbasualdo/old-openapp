package com.opencars.netgo.dms.calidad.entity;

import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@ApiModel(value = "Entidad Modelo de Registro de Reporte de Calidad", description = "Información de la fecha de actualización de un reporte de calidad")
public class ReportRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @NotNull
    LocalDateTime date;

    @NotNull
    LocalDate shortdate;

    @ManyToMany(fetch = FetchType.EAGER)
    @SortNatural
    @JoinTable(name = "reportcalidad_likesusers", joinColumns = @JoinColumn(name = "reportregister_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private SortedSet<User> likes = new TreeSet<>();

    private int views;

    public ReportRegister() {
    }

    public ReportRegister(LocalDateTime date, LocalDate shortdate) {
        this.date = date;
        this.shortdate = shortdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDate getShortdate() {
        return shortdate;
    }

    public void setShortdate(LocalDate shortdate) {
        this.shortdate = shortdate;
    }

    public SortedSet<User> getLikes() {
        return likes;
    }

    public void setLikes(SortedSet<User> likes) {
        this.likes = likes;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
