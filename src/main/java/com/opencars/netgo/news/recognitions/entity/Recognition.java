package com.opencars.netgo.news.recognitions.entity;

import com.opencars.netgo.news.recognitions.dto.Colaborator;
import com.opencars.netgo.news.recognitions.dto.Manager;
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
@Table(name = "recognitions")
@ApiModel(value = "Entidad Modelo de Reconocimiento", description = "Información completa que se almacena del reconocimiento")
public class Recognition implements Serializable {

    /* Attributes */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String type;
    @NotNull
    private String title;
    @NotNull
    @Column(columnDefinition = "TEXT")
    private String body;
    private LocalDateTime createdAt;
    @NotNull
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private Colaborator leader;
    @NotNull
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private Colaborator[] colaborators;
    @NotNull
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private Manager[] managers;
    @NotNull
    private String footerText;

    @NotNull
    private int published;

    @NotNull
    private LocalDate shortDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @SortNatural
    @JoinTable(name = "recognitions_likesusers", joinColumns = @JoinColumn(name = "recognition_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private SortedSet<User> likes = new TreeSet<>();

    private int views;


    public Recognition() {
    }

    public Recognition(String type, String title, String body, Colaborator leader, @NotNull Colaborator[] colaborators, @NotNull Manager[] managers, String footerText, int published) {
        this.type = type;
        this.title = title;
        this.body = body;
        this.leader = leader;
        this.colaborators = colaborators;
        this.managers = managers;
        this.footerText = footerText;
        this.published = published;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Colaborator getLeader() {
        return leader;
    }

    public void setLeader(Colaborator leader) {
        this.leader = leader;
    }

    public Colaborator[] getColaborators() {
        return colaborators;
    }

    public void setColaborators(Colaborator[] colaborators) {
        this.colaborators = colaborators;
    }

    public Manager[] getManagers() {
        return managers;
    }

    public void setManagers(Manager[] managers) {
        this.managers = managers;
    }

    public String getFooterText() {
        return footerText;
    }

    public void setFooterText(String footerText) {
        this.footerText = footerText;
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
