package com.tonictrader.tonictrades.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.tonictrader.tonictrades.databinding.CommonDialogBoxBinding;
import com.tonictrader.tonictrades.databinding.CommonDialogSimpleBinding;

public class CommonDialog {

    private final Context context;
    private Dialog dialog;
    private CommonDialogSimpleBinding commonDialogSimpleBinding;



    public CommonDialog(Context context, ViewGroup viewGroup) {
        this.context = context;
        this.dialog = dialog;
        dialog = new Dialog(context, com.tonictrader.tonictrades.R.style.RoundedCornersDialog);
        dialog.setCancelable(true);
         commonDialogSimpleBinding= CommonDialogSimpleBinding.inflate(LayoutInflater.from(context),viewGroup,false);
        dialog.setContentView(commonDialogSimpleBinding.getRoot());
    }
    public CommonDialog(Context context){



        this.context = context;
        this.dialog = dialog;
        dialog = new Dialog(context, com.tonictrader.tonictrades.R.style.RoundedCornersDialog);
        dialog.setCancelable(true);
        commonDialogSimpleBinding= CommonDialogSimpleBinding.inflate(LayoutInflater.from(context));
        dialog.setContentView(commonDialogSimpleBinding.getRoot());


    }
    public void  showDialog(String textOne){
        commonDialogSimpleBinding.tvSetText.setText(textOne);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        dialog.show();


        commonDialogSimpleBinding.btnOk.setOnClickListener(view -> dialog.dismiss());
    }
}
