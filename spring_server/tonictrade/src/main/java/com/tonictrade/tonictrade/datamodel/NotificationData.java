package com.tonictrade.tonictrade.datamodel;

import java.util.HashMap;

public class NotificationData {

    private String body;
    private String title;
    private HashMap<String,String> data;

    public NotificationData(String body, String title, HashMap<String, String> data) {
        this.body = body;
        this.title = title;
        this.data = data;
    }

    public HashMap<String, String> getData() {
        return data;
    }

    public void setData(HashMap<String, String> data) {
        this.data = data;
    }

    public NotificationData(String body, String title) {
        this.body = body;
        this.title = title;
    }

    public NotificationData() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
