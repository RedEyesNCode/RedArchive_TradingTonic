package com.tonictrader.tonictrades.home.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoteTradesModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("stopLoss")
    @Expose
    private String stopLoss;
    @SerializedName("target")
    @Expose
    private String target;
    @SerializedName("strikePrice")
    @Expose
    private String strikePrice;
    @SerializedName("tradeData")
    @Expose
    private String tradeData;
    @SerializedName("tradeImage")
    @Expose
    private String tradeImage;
    @SerializedName("tradeDate")
    @Expose
    private String tradeDate;
    @SerializedName("month")
    @Expose
    private Integer month;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(String strikePrice) {
        this.strikePrice = strikePrice;
    }

    public String getTradeData() {
        return tradeData;
    }

    public void setTradeData(String tradeData) {
        this.tradeData = tradeData;
    }

    public String getTradeImage() {
        return tradeImage;
    }

    public void setTradeImage(String tradeImage) {
        this.tradeImage = tradeImage;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }
}
