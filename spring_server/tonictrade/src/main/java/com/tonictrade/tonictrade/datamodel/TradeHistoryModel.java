package com.tonictrade.tonictrade.datamodel;


import javax.persistence.*;

@Entity
@Table
public class TradeHistoryModel {

    @Id
    @SequenceGenerator(name = "tradehistory_sequence", sequenceName = "tradehistory_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tradehistory_sequence")

    private Long id;

    private String title;
    private String category;
    private String stopLoss;
    private String target;
    private String strikePrice;
    private String tradeDate;
    private int month;

    private String tradeMessage;

    private String tradeImage;

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
    public String getTradeImage() {
        return tradeImage;
    }
    public void setTradeImage(String tradeImage) {
        this.tradeImage = tradeImage;
    }

    public TradeHistoryModel(String title, String category, String stopLoss, String target, String strikePrice, int month, String tradeDate, String tradeMessage, String tradeImage) {
        this.title = title;
        this.category = category;
        this.stopLoss = stopLoss;
        this.target = target;
        this.month = month;
        this.tradeMessage = tradeMessage;
        this.strikePrice = strikePrice;
        this.tradeDate = tradeDate;
        this.tradeImage = tradeImage;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public TradeHistoryModel() {
    }

    public String getTradeMessage() {
        return tradeMessage;
    }

    public void setTradeMessage(String tradeMessage) {
        this.tradeMessage = tradeMessage;
    }

    public TradeHistoryModel(Long id, String title, String category, String stopLoss, String target, String strikePrice) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.stopLoss = stopLoss;
        this.target = target;
        this.strikePrice = strikePrice;
    }

    public TradeHistoryModel(String title, String category, String stopLoss, String target, String strikePrice) {
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
