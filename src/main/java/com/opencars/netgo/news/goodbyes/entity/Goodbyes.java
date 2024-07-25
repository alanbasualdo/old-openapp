package com.opencars.netgo.news.goodbyes.entity;

import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@Table(name = "goodbyes")
@ApiModel(value = "Entidad Modelo de Despedida", description = "Información completa que se almacena de la despedida")
public class Goodbyes {

    /* Attributes */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String name;
    @NotNull
    private String imgUser;
    @NotNull
    @Column(columnDefinition = "TEXT")
    private String body;
    @NotNull
    private LocalDate date;
    @NotNull
    private String footerText;
    @NotNull
    private String cuil;

    @ManyToMany(fetch = FetchType.EAGER)
    @SortNatural
    @JoinTable(name = "goodbyes_likesusers", joinColumns = @JoinColumn(name = "goodbye_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private SortedSet<User> likes = new TreeSet<>();

    private int views;

    public Goodbyes() {
    }

    public Goodbyes(String name, String imgUser, String body, String footerText, String cuil) {
        this.name = name;
        this.imgUser = imgUser;
        this.body = body;
        this.footerText = footerText;
        this.cuil = cuil;
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

    public String getImgUser() {
        return imgUser;
    }

    public void setImgUser(String imgUser) {
        this.imgUser = imgUser;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getFooterText() {
        return footerText;
    }

    public void setFooterText(String footerText) {
        this.footerText = footerText;
    }

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
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
