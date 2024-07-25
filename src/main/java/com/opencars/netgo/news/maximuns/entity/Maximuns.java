package com.opencars.netgo.news.maximuns.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "maximuns")
@ApiModel(value = "Entidad Modelo de Máxima", description = "Información completa que se almacena de la máxima")
public class Maximuns {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String img;
    @NotNull
    private int outstanding;
    @NotNull
    private int numbermaximun;
    @NotNull
    private String orientation;


    public Maximuns() {
    }

    public Maximuns(String img, int outstanding, int numbermaximun, String orientation) {
        this.img = img;
        this.outstanding = outstanding;
        this.numbermaximun = numbermaximun;
        this.orientation = orientation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(int outstanding) {
        this.outstanding = outstanding;
    }

    public int getNumbermaximun() {
        return numbermaximun;
    }

    public void setNumbermaximun(int numbermaximun) {
        this.numbermaximun = numbermaximun;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }
}
