package com.tonictrade.tonictrade.datamodel;

public class NotificationModelSet {

    private String to;
    private BodyTitle notification;
    private BodyTitle data;



    public NotificationModelSet(String to, BodyTitle notification, BodyTitle data) {
        this.to = to;
        this.notification = notification;
        this.data = data;
    }

    public BodyTitle getData() {
        return data;
    }

    public void setData(BodyTitle data) {
        this.data = data;
    }

    public NotificationModelSet(String to, BodyTitle notification) {
        this.to = to;
        this.notification = notification;
    }

    public NotificationModelSet() {
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public BodyTitle getNotification() {
        return notification;
    }

    public void setNotification(BodyTitle notification) {
        this.notification = notification;
    }
}
