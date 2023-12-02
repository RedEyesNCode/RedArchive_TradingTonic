package com.tonictrade.tonictrade.datamodel;

import java.util.List;

public class TradeHistoryJson {

    private String status;
    private int code;


    private List<TradeHistoryModel> data;

    public TradeHistoryJson() {
    }

    public TradeHistoryJson(String status, int code, List<TradeHistoryModel> data) {
        this.status = status;
        this.code = code;
        this.data = data;
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

    public List<TradeHistoryModel> getData() {
        return data;
    }

    public void setData(List<TradeHistoryModel> data) {
        this.data = data;
    }
}
