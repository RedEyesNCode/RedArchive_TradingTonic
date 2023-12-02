package com.tonictrader.tonictrades.history.model;

public class HistoryModel {
    String category;
    String title;
    String strikePrice;
    String target;
    String stoploss;

    public HistoryModel(String category, String title, String strikePrice, String target, String stoploss) {
        this.category = category;
        this.title = title;
        this.strikePrice = strikePrice;
        this.target = target;
        this.stoploss = stoploss;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(String strikePrice) {
        this.strikePrice = strikePrice;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getStoploss() {
        return stoploss;
    }

    public void setStoploss(String stoploss) {
        this.stoploss = stoploss;
    }
}
