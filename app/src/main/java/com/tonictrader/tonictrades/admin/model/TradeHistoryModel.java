package com.tonictrader.tonictrades.admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TradeHistoryModel {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("data")
    @Expose
    private ArrayList<Data> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }
    public class Data {

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
        @SerializedName("tradeDate")
        @Expose
        private String tradeDate;
        @SerializedName("tradeMessage")
        @Expose
        private String tradeMessage;
        @SerializedName("tradeImage")
        @Expose
        private String tradeImage;

        public String getTradeImage() {
            return tradeImage;
        }

        public void setTradeImage(String tradeImage) {
            this.tradeImage = tradeImage;
        }

        public String getTradeMessage() {
            return tradeMessage;
        }

        public void setTradeMessage(String tradeMessage) {
            this.tradeMessage = tradeMessage;
        }

        public String getTradeDate() {
            return tradeDate;
        }

        public void setTradeDate(String tradeDate) {
            this.tradeDate = tradeDate;
        }

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

    }
}
