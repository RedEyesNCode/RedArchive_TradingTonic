package com.tonictrader.tonictrades.auth.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.tonictrader.tonictrades.sharedPreferences.AppSession;
import com.tonictrader.tonictrades.R;
import com.tonictrader.tonictrades.auth.login.signup.SignupActivity;
import com.tonictrader.tonictrades.auth.login.signup.model.SignupModel;
import com.tonictrader.tonictrades.auth.login.signup.model.SignupResponse;
import com.tonictrader.tonictrades.auth.login.signup.repository.SignupRepository;
import com.tonictrader.tonictrades.base.BaseActivity;
import com.tonictrader.tonictrades.callback.SignupListener;
import com.tonictrader.tonictrades.databinding.ActivityVerifyOtpBinding;
import com.tonictrader.tonictrades.home.DashboardActivity;
import com.tonictrader.tonictrades.sharedPreferences.Constant;
import com.tonictrader.tonictrades.utils.AppUtils;

import java.util.concurrent.TimeUnit;

public class VerifyOtpActivity extends BaseActivity {
    private ActivityVerifyOtpBinding binding;
    private SignupModel SIGN_UP_MODEL = new SignupModel();
    private String enteredOtp, otpId;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    PhoneAuthCredential tempCredentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyOtpBinding.inflate(getLayoutInflater());
        getIntentData();
        initClicks();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        setContentView(binding.getRoot());
    }
    private void getIntentData(){
        if (getIntent().hasExtra("SIGN_UP_MODEL")){
            SIGN_UP_MODEL = (SignupModel) getIntent().getSerializableExtra("SIGN_UP_MODEL");
            FirebaseOtpVerification(SIGN_UP_MODEL.getNumber());

        }else{
            showSnackBar(VerifyOtpActivity.this,binding.getRoot(),"Unable to parse json data");
        }

        if (!AppUtils.isNullEmpty(SIGN_UP_MODEL.getNumber())){
            binding.numberText.setText("We have sent an OTP to "+SIGN_UP_MODEL.getNumber()+" \n Please verify to proceed.");
        }
    }

    private void initClicks() {
         new Handler().postDelayed(() -> binding.resendLayout.setVisibility(View.VISIBLE),5000);
         binding.btnResendOTP.setOnClickListener(v -> {
             FirebaseOtpVerification(SIGN_UP_MODEL.getNumber());
         });
         binding.btnVerifyNumber.setOnClickListener(v->{
             enteredOtp = binding.firstPinView.getText().toString();
                 if(enteredOtp.length()==6){
                     if(otpId!=null){
                         try {
                             tempCredentials = PhoneAuthProvider.getCredential(otpId, enteredOtp);
                             signInWithPhoneAuthCredential(tempCredentials);
                         }catch (Exception e){
                             showToast(e.getMessage());
                         }

                     }
                 } else{
                 showSnackBar(VerifyOtpActivity.this,binding.getRoot(),"Please enter valid otp");
             }
         });


    }
    // FIREBASE OTP VERIFICATION
    // FIREBASE OTP VERIFICATION
    private void FirebaseOtpVerification(String phoneNumber){

        showLoader();
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        hideLoader();
                        tempCredentials = phoneAuthCredential;
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        hideLoader();
                        showToast(e.getMessage());
                        binding.resendLayout.setVisibility(View.VISIBLE);
                        finish();


                    }

                    @Override
                    public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                        super.onCodeAutoRetrievalTimeOut(s);

                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        otpId = s;
                        hideLoader();
                        Log.i("VERFICATION_ID_FIREBASE",s);
                        showToast("Otp Sent Successfully !");
                        binding.resendLayout.setVisibility(View.VISIBLE);
                        super.onCodeSent(s, forceResendingToken);

                    }
                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

/*.setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        hideLoader();
                        tempCredentials = phoneAuthCredential;
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        hideLoader();
                        showToast(e.getMessage());
                        binding.resendLayout.setVisibility(View.VISIBLE);
                        finish();

                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId,
                                           @NonNull PhoneAuthProvider.ForceResendingToken token) {
                        otpId = verificationId;
                        hideLoader();
                        Log.i("VERFICATION_ID_FIREBASE",verificationId);
                        showToast("Otp Sent Successfully !");
                        binding.resendLayout.setVisibility(View.VISIBLE);

                    }
                })*/
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
            if(task.isSuccessful()){
                callSignupApi(SIGN_UP_MODEL);
            }else{
                showToast(task.getException().getMessage());
            }

        }).addOnFailureListener(e -> {
            Log.i("OTP_ERRROR-->",e.getMessage());
            showSnackBar(VerifyOtpActivity.this,binding.getRoot(),e.getMessage());

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
                        firebaseEmailSignup(signupModel.getUserEmail(),signupModel.getPassword());
                        AppSession.getInstance(VerifyOtpActivity.this).putObject(Constant.USER,signupResponse);

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

        signupRepository.signUp(VerifyOtpActivity.this,signupModel,signupListener);

    }
    //FIREBASE EMAIL AND PASSWORD SIGN UP.
    private void firebaseEmailSignup(String email, String password){
        showLoader();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(VerifyOtpActivity.this, task -> {
            if(task.isSuccessful()){
                hideLoader();
                Intent dashboardIntent = new Intent(VerifyOtpActivity.this, DashboardActivity.class);
                dashboardIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                AppSession.getInstance(VerifyOtpActivity.this).setValue(Constant.IS_USER_LOGIN,"yes");
                startActivity(dashboardIntent);
                showSnackBar(VerifyOtpActivity.this,binding
                        .getRoot(),"Welcome trader !");
            }else {

                hideLoader();
                showToast(task.getException().getMessage());
                finish();
            }
        }).addOnFailureListener(e -> {

            hideLoader();
            showToast(e.getMessage());
            finish();

        });




    }




}