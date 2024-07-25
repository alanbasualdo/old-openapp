package com.opencars.netgo.news.welcomes.entity;

import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@Table(name = "welcomes")
@ApiModel(value = "Entidad Modelo de Bienvenida", description = "Información completa que se almacena de la bienvenida")
public class Welcome implements Serializable {

    /* Attributes */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String name;
    private String position;
    @NotNull
    private String img;
    private String branch;
    private String company;
    private String mail;
    private String cuil;
    private LocalDateTime entryDate;
    @NotNull
    private String gender;
    @NotNull
    private String category;
    @NotNull
    @Column(columnDefinition = "TEXT")
    private String text;

    @NotNull
    private int published;

    @NotNull
    private LocalDate shortDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @SortNatural
    @JoinTable(name = "welcomes_likesusers", joinColumns = @JoinColumn(name = "welcome_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private SortedSet<User> likes = new TreeSet<>();

    private int views;

    public Welcome() {
    }

    public Welcome(String name, String position, String img, String branch, String company, String mail, String cuil, String gender, String category, String text, int published) {
        this.name = name;
        this.position = position;
        this.img = img;
        this.branch = branch;
        this.company = company;
        this.mail = mail;
        this.cuil = cuil;
        this.gender = gender;
        this.category = category;
        this.text = text;
        this.published = published;
    }

    public Welcome(Welcome welcome) {
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPublished() {
        return published;
    }

    public void setPublished(int published) {
        this.published = published;
    }

    public LocalDate getShortDate() {
        return shortDate;
    }

    public void setShortDate(LocalDate shortDate) {
        this.shortDate = shortDate;
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
