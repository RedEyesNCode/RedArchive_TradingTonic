package com.tonictrade.tonictrade.datamodel;

public class SignupResponse {

    private int code;
    private String status;
    private  String message;
    private SignupModel user;

    public SignupResponse(int code, String status, String message, SignupModel user) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.user = user;
    }

    public SignupResponse() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SignupModel getId() {
        return user;
    }

    public void setId(SignupModel user) {
        this.user = user;
    }
}
