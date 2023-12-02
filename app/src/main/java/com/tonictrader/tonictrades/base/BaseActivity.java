package com.tonictrader.tonictrades.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.snackbar.Snackbar;
import com.tonictrader.tonictrades.utils.CommonDialog;

public class BaseActivity  extends AppCompatActivity {

    private ProgressDialogTrade progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialogTrade(BaseActivity.this);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);



    }

    //Implementing other common methods here.

    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    //ProgressDialog Methods.
    //Hide ProgressDialog.
    public void hideLoader(){
        try {
            progressDialog.dismiss();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    //ShowLoader
    public void showLoader(){
        progressDialog.show();
    }

    //Show CUSTOM message in dialog.

    public void showLog(String tag, String msg){
        Log.v(tag, msg);

    }
    public void showSnackBar(Context context, View view, String message){
        Snackbar.make(context,view,message,Snackbar.LENGTH_LONG).show();
    }

    public void showDialog(String message){
        CommonDialog commonDialog = new CommonDialog(this);
        commonDialog.showDialog(message);



    }

    //IsInternet_Connected Method.
    public boolean isInternetConnect(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting() && cm.getActiveNetworkInfo().isAvailable()
                && netInfo.isConnected();
    }
}
