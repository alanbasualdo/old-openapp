package com.opencars.netgo.support.tickets.entity;

import com.opencars.netgo.news.roundtrips.dto.Attacheds;
import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "ticketscoments")
@ApiModel(value = "Entidad Modelo de Seguimiento de Ticket", description = "Información completa que se almacena del seguimiento de ticket")
public class TicketsComents implements Serializable, Comparable<TicketsComents> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(columnDefinition = "TEXT")
    private String comment;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private Attacheds[] attacheds;
    @NotNull
    @ManyToOne
    private User creator;
    @NotNull
    private LocalDateTime date;

    public TicketsComents() {
    }

    public TicketsComents(Long id) {
        this.id = id;
    }

    public TicketsComents(String comment, Attacheds[] attacheds, User creator) {
        this.comment = comment;
        this.attacheds = attacheds;
        this.creator = creator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Attacheds[] getAttacheds() {
        return attacheds;
    }

    public void setAttacheds(Attacheds[] attacheds) {
        this.attacheds = attacheds;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public int compareTo(TicketsComents ticketsComents) {
        return comment.compareTo(ticketsComents.getComment());
    }
}
