package com.tonictrader.tonictrades.home;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.tonictrader.tonictrades.sharedPreferences.AppSession;
import com.tonictrader.tonictrades.R;
import com.tonictrader.tonictrades.admin.model.FcmToken;
import com.tonictrader.tonictrades.auth.login.LoginActivity;
import com.tonictrader.tonictrades.auth.login.repository.FcmRepository;
import com.tonictrader.tonictrades.base.CommonResponse;
import com.tonictrader.tonictrades.callback.CommonResponseListener;
import com.tonictrader.tonictrades.settings.SettingsFragment;
import com.tonictrader.tonictrades.base.BaseActivity;
import com.tonictrader.tonictrades.databinding.ActivityDashboardBinding;
import com.tonictrader.tonictrades.history.HistoryFragment;
import com.tonictrader.tonictrades.news.NewsFragment;
import com.tonictrader.tonictrades.utils.AppUtils;
import com.tonictrader.tonictrades.utils.CommonDialog;
import com.tonictrader.tonictrades.utils.CommonInteractionDialog;
import com.tonictrader.tonictrades.utils.FragmentUtils;

import java.util.HashMap;

public class DashboardActivity extends BaseActivity implements CommonInteractionDialog.onClicked {

    private ActivityDashboardBinding binding;
    private CommonInteractionDialog commonInteractionDialog;

    @Override
    public void onYesClicked() {

    }

    @Override
    public void onNoClicked() {

    }
    private void insertFcmToken(){
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(s -> {
            CommonResponseListener commonResponseListener =  new CommonResponseListener() {
                @Override
                public void onSuccessResponse(CommonResponse commonResponse) {
                }
                @Override
                public void onError(String error) {

                }
            };
            FcmRepository fcmRepository = new FcmRepository();
            FcmToken fcmToken = new FcmToken();
            fcmToken.setFcmToken(s);
            fcmToken.setUserName(Build.DEVICE);

            Log.i("FCM_TOKEN",s);

            fcmRepository.postFCM(DashboardActivity.this,fcmToken,commonResponseListener);

        });



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        setContentView(binding.getRoot());

        if(isInternetConnect()){
            insertFcmToken();
            checkRedirect();
            initNav();

        }else{
            CommonDialog commonDialog = new CommonDialog(DashboardActivity.this);
            commonInteractionDialog = new CommonInteractionDialog(DashboardActivity.this,this);
            commonDialog.showDialog("Please Ensure  to Turn on your Internet Connection.");
        }





    }

    private void checkRedirect(){
        String CHECK_REDIRECT = getIntent().getStringExtra("NOTIFICATION");
        if(!AppUtils.isNullEmpty(CHECK_REDIRECT)){
            FragmentUtils.replaceFragmentBackStack(getSupportFragmentManager(),R.id.activity_main_nav_host_fragment,new HistoryFragment(),HistoryFragment.class.getSimpleName(),true);
        }else {
            FragmentUtils.replaceFragmentBackStack(getSupportFragmentManager(),R.id.activity_main_nav_host_fragment,new HomeDashboardFragment(),HomeDashboardFragment.class.getSimpleName(),true);
        }

    }

    private void initNav(){
        NavController navController = Navigation.findNavController(DashboardActivity.this,R.id.activity_main_nav_host_fragment);
        NavigationUI.setupWithNavController(binding.bottomNavigationbar,navController,false);


    }




}