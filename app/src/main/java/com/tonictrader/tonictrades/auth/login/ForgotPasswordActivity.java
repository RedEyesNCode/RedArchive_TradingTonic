package com.tonictrader.tonictrades.auth.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.tonictrader.tonictrades.R;
import com.tonictrader.tonictrades.base.BaseActivity;
import com.tonictrader.tonictrades.databinding.ActivityForgotPasswordBinding;
import com.tonictrader.tonictrades.utils.AppUtils;

public class ForgotPasswordActivity extends BaseActivity {
    private ActivityForgotPasswordBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        setContentView(binding.getRoot());
        initClicks();
    }
    private void initClicks(){
        binding.btnSendLink.setOnClickListener(v->{
            String enteredEmail = binding.edtEmail.getText().toString();
            if(!AppUtils.isNullEmpty(enteredEmail) && AppUtils.isValidEmail(enteredEmail)){
                forgotPassword(enteredEmail);
            }else{
                showSnackBar(ForgotPasswordActivity.this,binding.getRoot(),"Please enter valid email address.");
            }
        });
    }

    private  void forgotPassword(String email){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if(task.isSuccessful()){

                Snackbar infoSnackbar = Snackbar.make(ForgotPasswordActivity.this,binding.getRoot(),"Link has been sent to your mail",Snackbar.LENGTH_LONG);
                infoSnackbar.show();
                finish();
                
            }else {
                showSnackBar(ForgotPasswordActivity.this,binding.getRoot(),task.getException().getMessage());

            }
        });






    }
    private void redirectEmail(){
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        startActivity(emailIntent);
    }

}