package com.tonictrader.tonictrades.auth.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.BuildConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import com.tonictrader.tonictrades.auth.login.repository.FcmRepository;
import com.tonictrader.tonictrades.auth.login.repository.LoginUserResponse;
import com.tonictrader.tonictrades.auth.login.signup.SignupActivity;
import com.tonictrader.tonictrades.auth.login.signup.model.VersionCheckModel;
import com.tonictrader.tonictrades.base.BaseActivity;
import com.tonictrader.tonictrades.callback.LoginListener;
import com.tonictrader.tonictrades.callback.VersionCallback;
import com.tonictrader.tonictrades.databinding.ActivityLoginBinding;
import com.tonictrader.tonictrades.home.DashboardActivity;
import com.tonictrader.tonictrades.sharedPreferences.AppSession;
import com.tonictrader.tonictrades.sharedPreferences.Constant;
import com.tonictrader.tonictrades.utils.AppUtils;
import com.tonictrader.tonictrades.utils.CommonDialog;

public class LoginActivity extends BaseActivity {



    private FirebaseAuth firebaseAuth;
    private ActivityLoginBinding binding;
    private FcmRepository fcmRepository = new FcmRepository();
    private VersionCallback versionCallback;
    private CommonDialog commonDialog;

    // Update for Api login than firebase login system as firebase console account changed.
    LoginListener loginListener = new LoginListener() {
        @Override
        public void onSucessLogin(LoginUserResponse loginUserResponse) {
            hideLoader();
                Intent dashboardIntent = new Intent(LoginActivity.this,DashboardActivity.class);
                AppSession.getInstance(LoginActivity.this).setValue(Constant.IS_USER_LOGIN,"yes");
                dashboardIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                dashboardIntent.putExtra("EMAIL",binding.edtEmail.getText().toString());

                startActivity(dashboardIntent);
                showToast("Welcome Trader !");
        }

        @Override
        public void onErrorLogin(String error) {
            hideLoader();
            showToast(error);

        }
    };






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);





        setContentView(binding.getRoot());
        checkRedirect();


        commonDialog = new CommonDialog(LoginActivity.this);
        firebaseAuth = FirebaseAuth.getInstance();
//        insertFcmToken();
//        checkAppVersion();
        initClicks();

        //Generating sample token for FCM
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(task.isSuccessful()){
                    Log.i("FCM_TOKEN",task.getResult());
                }
            }
        });

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.i("FCM_TOKEN", String.valueOf(task.getException().getMessage()));
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        Toast.makeText(LoginActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void initClicks(){
        binding.tvForgotPassword.setOnClickListener(view -> {
            Intent forgotPasswordIntent = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
            startActivity(forgotPasswordIntent);


        });
        binding.tvSignUp.setOnClickListener(view -> {
            Intent signUpIntent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(signUpIntent);

        });



        binding.btnLogin.setOnClickListener(view -> {
            String email ,password;
            email = binding.edtEmail.getText().toString();
            password = binding.edtPassword.getText().toString();

            if (AppUtils.isNullEmpty(email) || !AppUtils.isValidEmail(email)) {
                if(AppUtils.isNullEmpty(email)){
                    binding.edtEmail.setError("Please enter your email.");
                }else{
                    binding.edtEmail.setError("Please enter valid email.");
                }

            }else if(AppUtils.isNullEmpty(password)){
                binding.edtPassword.setError("Please enter your password");
            }else{
                signIn(email,password);
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

    private void signIn(String email, String password){




        showLoader();
        fcmRepository.loginUser(email,password,loginListener);


//        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
//            if(task.isSuccessful()){
//                hideLoader();
//                Intent dashboardIntent = new Intent(LoginActivity.this,DashboardActivity.class);
//                AppSession.getInstance(LoginActivity.this).setValue(Constant.IS_USER_LOGIN,"yes");
//                dashboardIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
//                dashboardIntent.putExtra("EMAIL",email);
//
//                startActivity(dashboardIntent);
//                showToast("Welcome Trader !");
//            }else {
//                hideLoader();
//                showToast("Invalid user/password.");
//
//            }
//
//        });





    }
    private void checkRedirect(){
        String redirect = getIntent().getStringExtra("NOTIFICATION");
        String isLogin = AppSession.getInstance(LoginActivity.this).getValue(Constant.IS_USER_LOGIN);
        if(AppUtils.isNullEmpty(redirect) || AppUtils.isNullEmpty(isLogin)){

            Log.i("REDIRECT","DONT REDIRECT");
        }else {
            if(redirect.contains("REDIRECT") &&  isLogin.contains("yes")){
                Intent dashboardIntent = new Intent(LoginActivity.this,DashboardActivity.class);
                dashboardIntent.putExtra("NOTIFICATION","REDIRECT");
                AppSession.getInstance(LoginActivity.this).setValue(Constant.IS_USER_LOGIN,"yes");
                startActivity(dashboardIntent);
                showToast("Welcome Trader !");
            }
        }





    }


    private void checkAppVersion(){
        versionCallback = new VersionCallback() {
            @Override
            public void onSucessResponse(VersionCheckModel versionCheckModel) {
                commonDialog.showDialog("Please Update your app to latest version \n for Higher Accuracy.");


            }

            @Override
            public void onError(String error) {
                showToast(error);

            }
        };
        fcmRepository.getAppVersion(LoginActivity.this,versionCallback);




    }




}