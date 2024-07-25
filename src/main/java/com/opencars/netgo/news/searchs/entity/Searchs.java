package com.opencars.netgo.news.searchs.entity;

import com.opencars.netgo.locations.entity.Company;
import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@ApiModel(value = "Entidad Modelo de Búsquedas de Personal", description = "Información completa de una búsqueda de personal")
public class Searchs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    String position;
    @NotNull
    private String flyer;
    @NotNull
    private String state;
    @NotNull
    private LocalDateTime date;
    @NotNull
    @ManyToOne
    private Company company;

    @ManyToMany(fetch = FetchType.EAGER)
    @SortNatural
    @JoinTable(name = "searchs_likesusers", joinColumns = @JoinColumn(name = "search_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private SortedSet<User> likes = new TreeSet<>();

    private int views;

    public Searchs() {
    }

    public Searchs(String position, String flyer, String state, Company company) {
        this.position = position;
        this.flyer = flyer;
        this.state = state;
        this.company = company;
    }

    public Searchs(String position, String flyer, String state, LocalDateTime date, Company company) {
        this.position = position;
        this.flyer = flyer;
        this.state = state;
        this.date = date;
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getFlyer() {
        return flyer;
    }

    public void setFlyer(String flyer) {
        this.flyer = flyer;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
