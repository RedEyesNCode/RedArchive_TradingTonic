package com.tonictrader.tonictrades.settings.model;

public class ContactUsData {

    private String socialName;
    private int imagePath;


    public ContactUsData(String socialName, int imagePath) {
        this.socialName = socialName;
        this.imagePath = imagePath;
    }
    public ContactUsData(){}

    public String getSocialName() {
        return socialName;
    }

    public void setSocialName(String socialName) {
        this.socialName = socialName;
    }

    public int getImagePath() {
        return imagePath;
    }

    public void setImagePath(int imagePath) {
        this.imagePath = imagePath;
    }
}
