package com.tonictrade.tonictrade.datamodel;

import java.util.ArrayList;

public class NotificationModelAll {

    private ArrayList<String> registration_ids;
    private BodyTitle notification;
    private BodyTitle data;


    public NotificationModelAll(ArrayList<String> registration_ids, BodyTitle notification, BodyTitle data) {
        this.registration_ids = registration_ids;
        this.notification = notification;
        this.data = data;
    }

    public NotificationModelAll() {
    }

    public ArrayList<String> getRegistration_ids() {
        return registration_ids;
    }

    public void setRegistration_ids(ArrayList<String> registration_ids) {
        this.registration_ids = registration_ids;
    }

    public BodyTitle getNotification() {
        return notification;
    }

    public void setNotification(BodyTitle notification) {
        this.notification = notification;
    }

    public BodyTitle getData() {
        return data;
    }

    public void setData(BodyTitle data) {
        this.data = data;
    }
}
