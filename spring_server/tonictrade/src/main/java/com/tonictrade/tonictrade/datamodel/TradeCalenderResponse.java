package com.tonictrade.tonictrade.datamodel;

public class TradeCalenderResponse extends StatusCodeModel{

    private TradeData data;

    public TradeCalenderResponse(String status, int code, String message) {
        super(status, code, message);
    }

    public TradeCalenderResponse(String status, int code) {
        super(status, code);
    }

    public TradeData getData() {
        return data;
    }

    public void setData(TradeData data) {
        this.data = data;
    }

    public TradeCalenderResponse(String status, int code, String message, TradeData data) {
        super(status, code, message);
        this.data = data;
    }

    public TradeCalenderResponse(String status, int code, TradeData data) {
        super(status, code);
        this.data = data;
    }
}
