package com.opencars.netgo.pushnotificacions.dto;

public class PushNotificationRequest {

    private String id;
    private String title;
    private String message;
    private String route;
    private String topic;
    private String token;
    private String icon;
    private long idNotification;

    public PushNotificationRequest() {

        super();
    }

    public PushNotificationRequest(String id, String title, String message, String route, String topic, String token, long idNotification) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.route = route;
        this.topic = topic;
        this.token = token;
        this.idNotification = idNotification;
    }

    public PushNotificationRequest(String id, String title, String message, String route, String topic, String token, String icon) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.route = route;
        this.topic = topic;
        this.token = token;
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public long getIdNotification() {
        return idNotification;
    }

    public void setIdNotification(long idNotification) {
        this.idNotification = idNotification;
    }
}
