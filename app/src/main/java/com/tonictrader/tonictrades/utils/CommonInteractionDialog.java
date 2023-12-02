package com.tonictrader.tonictrades.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.tonictrader.tonictrades.R;
import com.tonictrader.tonictrades.databinding.CommonDialogBoxBinding;

public class CommonInteractionDialog {

    private final Context context;
    private Dialog dialog;
    private onClicked onClicked;

    private CommonDialogBoxBinding commonInteractiveDialogBinding;

    public CommonInteractionDialog(Context context, onClicked onClicked, ViewGroup viewGroup){
        this.context = context;
        this.dialog = dialog;
        this.onClicked=onClicked;
        dialog = new Dialog(context, com.tonictrader.tonictrades.R.style.RoundedCornersDialog);
        dialog.setCancelable(true);
        commonInteractiveDialogBinding = CommonDialogBoxBinding.inflate(LayoutInflater.from(context),viewGroup,false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);
        dialog.setContentView(commonInteractiveDialogBinding.getRoot());
    }
    public CommonInteractionDialog(Context context, onClicked onClicked){

        this.context = context;
        this.dialog = dialog;
        this.onClicked=onClicked;
        dialog = new Dialog(context, com.tonictrader.tonictrades.R.style.RoundedCornersDialog);
        dialog.setCancelable(true);

        commonInteractiveDialogBinding = CommonDialogBoxBinding.inflate(LayoutInflater.from(context));
        dialog.setContentView(commonInteractiveDialogBinding.getRoot());

    }


    public void showCommonInteractiveDialgog(String textOne){
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        commonInteractiveDialogBinding.tvCommonDialogLineOne.setText(textOne);

        commonInteractiveDialogBinding.btnYes.setOnClickListener(view -> {
            onClicked.onYesClicked();
            dialog.dismiss();
        });
        commonInteractiveDialogBinding.btnNo.setOnClickListener(view -> {
            onClicked.onNoClicked();
            dialog.dismiss();
        });
    }
    public interface onClicked{
        void onYesClicked();
        void onNoClicked();
    }
}
