package com.tonictrade.tonictrade.datamodel;

import com.amazonaws.services.dynamodbv2.xspec.S;

import java.util.HashMap;

public class BodyTitle {

    private String body;
    private String title;

    private String icon;
    public BodyTitle(String body, String title, String icon) {
        this.body = body;
        this.title = title;
        this.icon = icon;
    }

    public BodyTitle(String body, String title) {
        this.body = body;
        this.title = title;
    }

    public String getClick_action() {
        return icon;
    }

    public void setClick_action(String icon) {
        this.icon = icon;
    }

    public BodyTitle() {
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
