package com.tonictrader.tonictrades.splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.tonictrader.tonictrades.auth.login.LoginActivity;
import com.tonictrader.tonictrades.home.DashboardActivity;
import com.tonictrader.tonictrades.sharedPreferences.AppSession;
import com.tonictrader.tonictrades.sharedPreferences.Constant;
import com.tonictrader.tonictrades.utils.AppUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        setContentView(com.tonictrader.tonictrades.R.layout.activity_splash);

        AppUtils.insertFcmToken(SplashActivity.this);

        new Handler().postDelayed(() -> {
            if(AppSession.getInstance(SplashActivity.this).getValue(Constant.IS_USER_LOGIN).contains("yes")){
                startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
                finish();
            }else{
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }


        },  5000);
    }
}