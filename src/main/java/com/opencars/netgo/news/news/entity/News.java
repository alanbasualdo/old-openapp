package com.opencars.netgo.news.news.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.io.Serializable;

@Document(collection = "noticias")
public class News implements Serializable {

    @Id
    private String id;
    private String image;
    private String text;
    @Field(targetType = FieldType.STRING)
    private String link;
    private int user;

    public News() {
    }

    public News(String image, String text, String link, int user) {
        this.image = image;
        this.text = text;
        this.link = link;
        this.user = user;
    }

    public News(String id, String image, String text, String link, int user) {
        this.id = id;
        this.image = image;
        this.text = text;
        this.link = link;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }
}
