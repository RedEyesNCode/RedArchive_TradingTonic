package com.tonictrader.tonictrades.auth.login.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.tonictrader.tonictrades.R;
import com.tonictrader.tonictrades.auth.login.CommonStatusMessageResponse;
import com.tonictrader.tonictrades.auth.login.VerifyOtpActivity;
import com.tonictrader.tonictrades.auth.login.signup.model.SignupModel;
import com.tonictrader.tonictrades.auth.login.signup.model.SignupResponse;
import com.tonictrader.tonictrades.auth.login.signup.repository.SignupRepository;
import com.tonictrader.tonictrades.auth.login.signup.viewmodel.SigupViewmodel;
import com.tonictrader.tonictrades.base.BaseActivity;
import com.tonictrader.tonictrades.callback.SignupListener;
import com.tonictrader.tonictrades.databinding.ActivitySignupBinding;
import com.tonictrader.tonictrades.home.DashboardActivity;
import com.tonictrader.tonictrades.sharedPreferences.AppSession;
import com.tonictrader.tonictrades.sharedPreferences.Constant;
import com.tonictrader.tonictrades.utils.AppUtils;

import java.util.concurrent.TimeUnit;

public class SignupActivity extends BaseActivity {
    private String enteredOtp, otpId;
    private FirebaseAuth firebaseAuth;
    private String phoneNumber,phoneNumberReal;
    private ActivitySignupBinding binding;
    private String password, email,name;
    private FirebaseStorage firebaseStorage;
    private DatabaseReference signUpDatabaseReference;
    private FirebaseDatabase firebaseDatabase;
    private boolean isOtpVerified = false;
    private SignupListener signupListener;
    private SignupModel signupModel;
    private SigupViewmodel sigupViewmodel;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        signUpDatabaseReference = firebaseDatabase.getReference("users");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
        sigupViewmodel = new ViewModelProvider(this).get(SigupViewmodel.class);
        binding.setViewmodel(sigupViewmodel);
        sigupViewmodel.init();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        setContentView(binding.getRoot());
        initView();
        initClicks();


    }

    private void initView() {
        sigupViewmodel.getIsFailed().observe(this, s -> {
            hideLoader();
            showDialog(s);
        });
        sigupViewmodel.getIsConnecting().observe(this, aBoolean -> showLoader());

//        sigupViewmodel.observeCheckuser().observe(this, commonStatusMessageResponse -> {
//            hideLoader();
//            if(commonStatusMessageResponse.getStatus().contains("success")){
//                SignupModel SIGN_UP_MODEL = new SignupModel(binding.edtEmail.getText().toString(),binding.edtName.getText().toString(),"+91"+binding.edtPhoneNumber.getText().toString(),binding.edtPassword.getText().toString());
//                Intent verifyOtpIntent = new Intent(SignupActivity.this, VerifyOtpActivity.class);
//                verifyOtpIntent.putExtra("SIGN_UP_MODEL",SIGN_UP_MODEL);
//                startActivity(verifyOtpIntent);
//            }else{
//                showDialog(commonStatusMessageResponse.getMessage());
//            }
//
//        });

    }

    private void initClicks(){
        binding.btnNext.setOnClickListener(v->{
            if(performValidation()){
              //  sigupViewmodel.checkUser(binding.edtEmail.getText().toString(),binding.edtPhoneNumber.getText().toString());
                SignupModel SIGN_UP_MODEL = new SignupModel(binding.edtEmail.getText().toString(),binding.edtName.getText().toString(),"+91"+binding.edtPhoneNumber.getText().toString(),binding.edtPassword.getText().toString());
                callSignupApi(SIGN_UP_MODEL);
//                Intent verifyOtpIntent = new Intent(SignupActivity.this, VerifyOtpActivity.class);
//                verifyOtpIntent.putExtra("SIGN_UP_MODEL",SIGN_UP_MODEL);
//                startActivity(verifyOtpIntent);
            }

        });
        binding.termsLayout.setOnClickListener(view -> {
            String url = "https://tonictrading.wordpress.com/terms-conditions/";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        });
        binding.tvPolicy.setOnClickListener(view -> {
            String url = "https://tonictrading.wordpress.com/privacy-policy/";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });

    }
    private void callSignupApi(SignupModel signupModel){
        SignupRepository signupRepository = new SignupRepository();

        SignupListener signupListener = new SignupListener() {
            @Override
            public void onSuccessResponse(SignupResponse signupResponse) {
                hideLoader();
                if(signupResponse!=null){
                    if(signupResponse.getCode()==200){
                        AppSession.getInstance(SignupActivity.this).putObject(Constant.USER,signupResponse);
//                        Intent dashboardIntent = new Intent(SignupActivity.this, DashboardActivity.class);
//                        dashboardIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        AppSession.getInstance(SignupActivity.this).setValue(Constant.IS_USER_LOGIN,"yes");
//                        startActivity(dashboardIntent);
                        onBackPressed();
                    }else{
                        showToast(signupResponse.getMessage());
                        finish();
                    }

                }
            }

            @Override
            public void onError(String error) {
                hideLoader();
                showToast(error);
            }
        };

        signupRepository.signUp(SignupActivity.this,signupModel,signupListener);

    }

    private boolean performValidation(){
        String email = binding.edtEmail.getText().toString();
        String name = binding.edtName.getText().toString();
        String number = binding.edtPhoneNumber.getText().toString();
        String password = binding.edtPassword.getText().toString();
        String confirmPassword = binding.edtConfirmPassword.getText().toString();

        if(AppUtils.isNullEmpty(name)){
            binding.edtName.setError("Please enter your name");            return false;

        }else if(AppUtils.isNullEmpty(email) || !AppUtils.isValidEmail(email)){
            if (AppUtils.isNullEmpty(email)) {
                binding.edtEmail.setError("Please enter your email");            return false;

            }else{
                binding.edtEmail.setError("Please enter valid email");            return false;

            }

        }else if(AppUtils.isNullEmpty(number)|| number.length()<10 ){
            if(AppUtils.isNullEmpty(number)){
                binding.edtPhoneNumber.setError("Please enter your number");            return false;

            }else{
                binding.edtPhoneNumber.setError("Please enter valid number");            return false;

            }

        }else if(AppUtils.isNullEmpty(password) || password.length()<4){
            if(AppUtils.isNullEmpty(password)){
                binding.edtPassword.setError("Please enter your password");            return false;

            }else{
                binding.edtPassword.setError("Password length must be atleast 4.");            return false;

            }

        }else if (AppUtils.isNullEmpty(confirmPassword) || confirmPassword.length()<4){
            if(AppUtils.isNullEmpty(password)){
                binding.edtConfirmPassword.setError("Please enter your password");            return false;

            }else{
                binding.edtConfirmPassword.setError("Password length must be atleast 4.");            return false;

            }
        }else if(!password.equals(confirmPassword)){
            binding.edtConfirmPassword.setError("Password do not match");
            return false;
        }else{
            return true;
        }

    }


    //FIREBASE INSERT USER DATA INTO DATABASE.







}