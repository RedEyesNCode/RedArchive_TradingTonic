package com.tonictrader.tonictrades.auth.login.signup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VersionCheckModel {

    @SerializedName("versionCode")
    @Expose
    private Integer versionCode;
    @SerializedName("versionName")
    @Expose
    private String versionName;

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
}
