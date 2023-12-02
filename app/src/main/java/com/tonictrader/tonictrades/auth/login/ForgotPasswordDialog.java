package com.tonictrader.tonictrades.auth.login;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tonictrader.tonictrades.R;
import com.tonictrader.tonictrades.databinding.ForgotPasswordDialogBinding;
import com.tonictrader.tonictrades.utils.AppUtils;

public class ForgotPasswordDialog {
    private final Context context;
    private Dialog dialog;
    private ForgotPasswordDialogBinding binding;
    private onSendLink onSendLink;


    public ForgotPasswordDialog(Context context, onSendLink onSendLink) {
        this.context = context;
        this.dialog = dialog;
        this.binding = binding;
        this.onSendLink = onSendLink;

        dialog = new Dialog(context, R.style.RoundedCornersDialog);
        dialog.setCancelable(true);
        binding = ForgotPasswordDialogBinding.inflate(LayoutInflater.from(context));
        dialog.setContentView(binding.getRoot());
    }

    public void showDialog(){
        dialog.show();
        binding.btnSendLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.edtEmail.getText().toString();
                if(AppUtils.isValidEmail(email) && !AppUtils.isNullEmpty(email)){
                    onSendLink.onSendLink(email.trim());
                }else {
                    Toast.makeText(context,"Please Enter Valid Email Address",Toast.LENGTH_LONG).show();
                }
            }
        });



    }
    public void dismissDialog(){
        dialog.dismiss();
    }


    public interface onSendLink{
        void onSendLink(String email);
    }




}
