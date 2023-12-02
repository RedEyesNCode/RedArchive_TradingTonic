package com.tonictrader.tonictrades.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.tonictrader.tonictrades.auth.login.signup.model.SignupResponse;
import com.tonictrader.tonictrades.auth.login.signup.model.UserData;
import com.tonictrader.tonictrades.sharedPreferences.Constant;

public class AppSession {

    public static AppSession yourPreference;
    public SharedPreferences sharedPreferences;

    public static AppSession getInstance(Context context) {
        if (yourPreference == null) {
            yourPreference = new AppSession(context);
        }
        return yourPreference;
    }

    public void clear() {
        sharedPreferences.edit().clear().commit();
    }

    public AppSession(Context context) {
        sharedPreferences = context.getSharedPreferences(Constant.PREFERENCES_NAME, Context.MODE_PRIVATE);
    }
    public void putObject(String key, Object obj) {
        checkForNullKey(key);
        Gson gson = new Gson();
        putString(key, gson.toJson(obj));
    }
    public boolean checkForNullKey(String key) {
        return key == null;
    }
    public void putString(String key, String value) {
        checkForNullKey(key);
        checkForNullValue(value);
        sharedPreferences.edit().putString(key, value).apply();
    }
    public void checkForNullValue(String value) {
        if (value == null) {
            throw new NullPointerException();
        }
    }
    public void setValue(String key, String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }
    public UserData getUser() {
        return (UserData) getObject(Constant.USER, UserData.class);
    }
    public Object getObject(String key, Class<?> classOfT) {
        String json = getString(key);
        return new Gson().fromJson(json, classOfT);
    }
    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public String getValue(String key) {
        if (sharedPreferences != null) {
            return sharedPreferences.getString(key, "");
        }
        return "";
    }
}
