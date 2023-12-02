package com.tonictrade.tonictrade.datamodel;

import javax.persistence.*;


@Entity
@Table
public class RemoteTradeOneData {
    @Id
    @SequenceGenerator(name = "remotetrade_one_sequence", sequenceName = "remotetrade_one_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "remotetrade_one_sequence")
    private Long id;

    private String title;
    private String category;
    private String stopLoss;
    private String target;
    private String strikePrice;
    private String tradeData;

    private String tradeImage;

    private String tradeDate;

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    private int month;

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public RemoteTradeOneData(String title, String category, String stopLoss, String target, String strikePrice, int month, String tradeData,String tradeImage,String tradeDate) {
        this.title = title;
        this.category = category;
        this.tradeDate = tradeDate;
        this.stopLoss = stopLoss;
        this.target = target;
        this.tradeImage = tradeImage;
        this.strikePrice = strikePrice;
        this.tradeData = tradeData;
        this.month = month;
    }

    public String getTradeImage() {
        return tradeImage;
    }

    public void setTradeImage(String tradeImage) {
        this.tradeImage = tradeImage;
    }

    public String getTradeData() {
        return tradeData;
    }

    public void setTradeData(String tradeData) {
        this.tradeData = tradeData;
    }

    public RemoteTradeOneData() {
    }

    public RemoteTradeOneData(Long id, String title, String category, String stopLoss, String target, String strikePrice) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.stopLoss = stopLoss;
        this.target = target;
        this.strikePrice = strikePrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RemoteTradeOneData(String title, String category, String stopLoss, String target, String strikePrice) {
        this.title = title;
        this.category = category;
        this.stopLoss = stopLoss;
        this.target = target;

        this.strikePrice = strikePrice;
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
}
