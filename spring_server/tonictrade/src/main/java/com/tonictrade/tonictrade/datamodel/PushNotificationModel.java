package com.tonictrade.tonictrade.datamodel;

public class PushNotificationModel {



    private String to;
    private String priority;
    private NotificationData data;

    public PushNotificationModel(String to, String priority, NotificationData data) {
        this.to = to;
        this.priority = priority;
        this.data = data;
    }

    public PushNotificationModel() {
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public NotificationData getData() {
        return data;
    }

    public void setData(NotificationData data) {
        this.data = data;
    }
}
