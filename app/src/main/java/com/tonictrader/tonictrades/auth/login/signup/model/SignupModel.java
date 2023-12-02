package com.tonictrader.tonictrades.auth.login.signup.model;

import java.io.Serializable;

public class SignupModel implements Serializable {

    private String userEmail;
    private String userName;
    private String number;
    private String password;

    public SignupModel() {
    }

    public SignupModel(String userEmail, String userName, String number, String password) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.number = number;
        this.password = password;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
