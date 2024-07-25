package com.opencars.netgo.news.compliance.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "compliance")
@ApiModel(value = "Entidad Modelo de Compliance", description = "Información completa que se almacena del manual de compliance")
public class Compliance {

    /* Attributes */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Titles title;
    @ManyToOne
    private Subtitle subtitle;
    private String internalsubtitle;
    @NotNull
    @Column(columnDefinition = "TEXT")
    private String text;

    public Compliance() {
    }

    public Compliance(Titles title, Subtitle subtitle, String internalsubtitle, String text) {
        this.title = title;
        this.subtitle= subtitle;
        this.internalsubtitle = internalsubtitle;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Titles getTitle() {
        return title;
    }

    public void setTitle(Titles title) {
        this.title = title;
    }

    public Subtitle getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(Subtitle subtitle) {
        this.subtitle = subtitle;
    }

    public String getInternalsubtitle() {
        return internalsubtitle;
    }

    public void setInternalsubtitle(String internalsubtitle) {
        this.internalsubtitle = internalsubtitle;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
