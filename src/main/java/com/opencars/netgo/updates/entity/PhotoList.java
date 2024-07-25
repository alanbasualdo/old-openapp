package com.opencars.netgo.updates.entity;

import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@ApiModel(value = "Entidad Modelo de Actualización de Intranet", description = "Datos que se almacenan de una actualización de la Intranet")
public class PhotoList {
    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String title;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Photo> photos;

    private LocalDate date;

    private int views;

    @ManyToMany(fetch = FetchType.EAGER)
    @SortNatural
    @JoinTable(name = "updates_likesusers", joinColumns = @JoinColumn(name = "photolist_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private SortedSet<User> likes = new TreeSet<>();

    @PrePersist
    public void persistDate(){
        this.date = LocalDate.now();
    }

    public PhotoList() {
    }

    public PhotoList(String title, List<Photo> photos) {
        this.title = title;
        this.photos = photos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public SortedSet<User> getLikes() {
        return likes;
    }

    public void setLikes(SortedSet<User> likes) {
        this.likes = likes;
    }
}
