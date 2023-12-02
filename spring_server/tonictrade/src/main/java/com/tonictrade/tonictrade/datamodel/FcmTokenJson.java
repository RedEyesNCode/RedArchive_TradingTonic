package com.tonictrade.tonictrade.datamodel;

import java.util.List;

public class FcmTokenJson {

    private String status;
    private int code;
    private List<FcmTokenModel> data;

    public FcmTokenJson(String status, int code, List<FcmTokenModel> data) {
        this.status = status;
        this.code = code;
        this.data = data;
    }

    public FcmTokenJson() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<FcmTokenModel> getData() {
        return data;
    }

    public void setData(List<FcmTokenModel> data) {
        this.data = data;
    }
}
