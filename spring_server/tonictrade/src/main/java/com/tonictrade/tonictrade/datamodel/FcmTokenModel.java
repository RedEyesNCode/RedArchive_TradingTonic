package com.tonictrade.tonictrade.datamodel;


import javax.persistence.*;

@Entity
@Table
public class FcmTokenModel {


    @Id
    @SequenceGenerator(name = "fcm_sequence", sequenceName = "fcm_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fcm_sequence")


    private Long id;
    private String fcmToken;

    private String userName;

    public FcmTokenModel(Long id, String fcmToken, String userName) {
        this.id = id;
        this.fcmToken = fcmToken;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public FcmTokenModel(String fcmToken, String userName) {
        this.fcmToken = fcmToken;
        this.userName = userName;
    }

    public FcmTokenModel() {
    }

    public FcmTokenModel(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public FcmTokenModel(Long id, String fcmToken) {
        this.id = id;
        this.fcmToken = fcmToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
