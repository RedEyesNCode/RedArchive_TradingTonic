package com.tonictrade.tonictrade.datamodel;

import javax.persistence.*;


@Entity
@Table
public class AppVersionModel {


    @Id
    @SequenceGenerator(name = "users_data_sequence", sequenceName = "users_data_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_data_sequence")
    private Long id;

    private int versionCode;
    private String versionName;

    public AppVersionModel(int versionCode, String versionName) {
        this.versionCode = versionCode;
        this.versionName = versionName;
    }

    public AppVersionModel(Long id, int versionCode, String versionName) {
        this.id = id;
        this.versionCode = versionCode;
        this.versionName = versionName;
    }

    public AppVersionModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
}
