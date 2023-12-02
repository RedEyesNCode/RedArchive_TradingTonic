package com.tonictrader.tonictrades.auth.login.signup.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.tonictrader.tonictrades.auth.login.CommonStatusMessageResponse;
import com.tonictrader.tonictrades.auth.login.signup.model.SignupModel;
import com.tonictrader.tonictrades.auth.login.signup.model.SignupResponse;
import com.tonictrader.tonictrades.callback.CommonResponseListener;
import com.tonictrader.tonictrades.callback.CommonStatusMessageResponseListener;
import com.tonictrader.tonictrades.callback.SignupListener;
import com.tonictrader.tonictrades.network_remote.ApiInterface;
import com.tonictrader.tonictrades.network_remote.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupRepository {
    private static SignupRepository signupRepository;
    private final MutableLiveData<SignupResponse> signupResponseMutableLiveDataM= new MutableLiveData<>();
    private MutableLiveData<CommonStatusMessageResponse> commonStatusMessageResponseMutableLiveData = new MutableLiveData<>();

    public static SignupRepository getInstance(){
        if(signupRepository==null){
            signupRepository = new SignupRepository();

        }
        return signupRepository;
    }
    private final ApiInterface apiInterfaceSpring;

    public SignupRepository(){
        apiInterfaceSpring = RetrofitService.createSpringBootService(ApiInterface.class);
    }

    //Calling the Check user Unique Api.
    public void checkUser(String email,String number,CommonStatusMessageResponseListener commonStatusMessageResponseListener){
        Call<CommonStatusMessageResponse> call = apiInterfaceSpring.checkUser(email, number);
        call.enqueue(new Callback<CommonStatusMessageResponse>() {
            @Override
            public void onResponse(Call<CommonStatusMessageResponse> call, Response<CommonStatusMessageResponse> response) {
                if(response.isSuccessful()){
                    commonStatusMessageResponseListener.onSuccessResponse(response.body());

                }

            }

            @Override
            public void onFailure(Call<CommonStatusMessageResponse> call, Throwable t) {
                commonStatusMessageResponseListener.onError(t.getMessage());

            }
        });

    }


    //Calling the Signup api.
    public MutableLiveData<SignupResponse> signUp(Context context, SignupModel signupModel, SignupListener signupListener){
        Call<SignupResponse> call = apiInterfaceSpring.signupUser(signupModel);
        call.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().getCode()==200){
                        signupListener.onSuccessResponse(response.body());
                    }else if (response.body().getCode()==400){
                        signupListener.onSuccessResponse(response.body());
                    }


                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {

                signupListener.onError(t.getMessage());

            }
        });


        return signupResponseMutableLiveDataM;



    }


}
