package com.tonictrader.tonictrades.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.tonictrader.tonictrades.R;

public class BaseFragment extends Fragment {
    private ProgressDialogTrade progressDialog;
    private  Context context;
    private Activity activity;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
        progressDialog = new ProgressDialogTrade(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //appSession = AppSession.getInstance(context);
    }

    public void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void hideLoader() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showLoader() {
        try {
            if (progressDialog == null) {
                progressDialog = new ProgressDialogTrade(context);
            }
            progressDialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void showSnackBar(View view, Context context, String message){
        try {
            Snackbar.make(context,view,message,Snackbar.LENGTH_SHORT).setBackgroundTint(ContextCompat.getColor(context, R.color.bluetheme)).show();


        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
