package com.opencars.netgo.Notifications.entity;

import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@Table(name = "notifications")
@ApiModel(value = "Entidad Modelo de Notificaciones", description = "Información completa que se almacena de una notificación")
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String title;
    @Column(columnDefinition = "TEXT")
    private String body;
    private LocalDateTime date;
    private String icon;
    private String route;

    @ManyToMany(fetch = FetchType.LAZY)
    @SortNatural
    @JoinTable(name = "notifications_users", joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private SortedSet<User> users = new TreeSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @SortNatural
    @JoinTable(name = "notifications_viewusers", joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private SortedSet<User> views = new TreeSet<>();
    private Long idTicket;

    @PrePersist
    public void persistDate(){
        this.date = LocalDateTime.now();
    }

    public Notifications() {
    }

    public Notifications(String title, String body, String icon, String route, SortedSet<User> users, Long idTicket) {
        this.title = title;
        this.body = body;
        this.icon = icon;
        this.route = route;
        this.users = users;
        this.idTicket = idTicket;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public SortedSet<User> getUsers() {
        return users;
    }

    public void setUsers(SortedSet<User> users) {
        this.users = users;
    }

    public SortedSet<User> getViews() {
        return views;
    }

    public void setViews(SortedSet<User> views) {
        this.views = views;
    }

    public Long getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Long idTicket) {
        this.idTicket = idTicket;
    }
}
