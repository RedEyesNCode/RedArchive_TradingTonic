package com.tonictrader.tonictrades.admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FcmToken {
    @SerializedName("fcmToken")
    @Expose
    private String fcmToken;
    @SerializedName("userName")
    @Expose
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
