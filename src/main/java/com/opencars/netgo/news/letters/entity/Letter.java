package com.opencars.netgo.news.letters.entity;

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
@Table(name = "letters")
@ApiModel(value = "Entidad Modelo de Carta de Presidencia", description = "Información completa que se almacena de la carta de presidencia")
public class Letter {

    /* Attributes */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String title;
    @NotNull
    @Column(columnDefinition = "TEXT")
    private String description;
    @NotNull
    private String stringDate;
    @NotNull
    private LocalDateTime date;

    @NotNull
    private int published;

    @NotNull
    private LocalDate shortDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @SortNatural
    @JoinTable(name = "letters_likesusers", joinColumns = @JoinColumn(name = "letter_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private SortedSet<User> likes = new TreeSet<>();

    private int views;

    public Letter() {
    }

    public Letter(String title, String description, int published) {
        this.title = title;
        this.description = description;
        this.published = published;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStringDate() {
        return stringDate;
    }

    public void setStringDate(String stringDate) {
        this.stringDate = stringDate;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDate getShortDate() {
        return shortDate;
    }

    public void setShortDate(LocalDate shortDate) {
        this.shortDate = shortDate;
    }

    public int getPublished() {
        return published;
    }

    public void setPublished(int published) {
        this.published = published;
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
