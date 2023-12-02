package com.tonictrader.tonictrades.auth.login.signup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserData{
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("userEmail")
    @Expose
    private String userEmail;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("userName")
    @Expose
    private String userName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

