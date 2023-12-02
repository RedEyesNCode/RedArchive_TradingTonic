package com.tonictrade.tonictrade.datamodel;

import org.springframework.data.relational.core.sql.In;

import java.util.ArrayList;
import java.util.List;

public class TradeData {

    private ArrayList<Integer> Month;

    private List<TradeHistoryModel> trades;

    public TradeData() {

    }

    public ArrayList<Integer> getMonth() {
        return Month;
    }

    public void setMonth(ArrayList<Integer> month) {
        Month = month;
    }

    public List<TradeHistoryModel> getTrades() {
        return trades;
    }

    public void setTrades(List<TradeHistoryModel> trades) {
        this.trades = trades;
    }

    public TradeData(ArrayList<Integer> month, List<TradeHistoryModel> trades) {
        Month = month;
        this.trades = trades;
    }
}
