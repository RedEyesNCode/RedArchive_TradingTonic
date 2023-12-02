package com.tonictrader.tonictrades.home.bankNiftyTrade;

public class TradeModel {
    private String strikePrice;
    private String stopLoss;
    private String target;


    public TradeModel(){}

    public TradeModel(String strikePrice, String stopLoss, String target) {
        this.strikePrice = strikePrice;
        this.stopLoss = stopLoss;
        this.target = target;
    }

    public String getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(String strikePrice) {
        this.strikePrice = strikePrice;
    }

    public String getStopLoss() {
        return stopLoss;
    }

    public void setStopLoss(String stopLoss) {
        this.stopLoss = stopLoss;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
