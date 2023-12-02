package com.tonictrader.tonictrades.auth.login.signup.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tonictrader.tonictrades.auth.login.CommonStatusMessageResponse;
import com.tonictrader.tonictrades.auth.login.repository.FcmRepository;
import com.tonictrader.tonictrades.auth.login.signup.repository.SignupRepository;
import com.tonictrader.tonictrades.callback.CommonStatusMessageResponseListener;

public class SigupViewmodel extends ViewModel {


    private final MutableLiveData<String> isFailed = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isConnecting = new MutableLiveData<>();
    private SignupRepository signupRepository;

    private MutableLiveData<CommonStatusMessageResponse> commonStatusMessageResponseMutableLiveData;
    public void init(){
        if(commonStatusMessageResponseMutableLiveData==null){
            return;
        }
        signupRepository = SignupRepository.getInstance();

    }

    public LiveData<String> getIsFailed() {
        return isFailed;
    }

    public LiveData<Boolean> getIsConnecting() {
        return isConnecting;
    }

    public LiveData<CommonStatusMessageResponse> observeCheckuser(){
        if(commonStatusMessageResponseMutableLiveData==null){

            commonStatusMessageResponseMutableLiveData = new MutableLiveData<>();

        }
        return commonStatusMessageResponseMutableLiveData;


    }



    public void checkUser(String email,String number){
        isConnecting.setValue(true);
        SignupRepository signupRepository = new SignupRepository();
        signupRepository.checkUser(email,number,commonStatusMessageResponseListener);


    }

    CommonStatusMessageResponseListener commonStatusMessageResponseListener = new CommonStatusMessageResponseListener() {
        @Override public void onSuccessResponse(CommonStatusMessageResponse commonStatusMessageResponse) {
            commonStatusMessageResponseMutableLiveData.setValue(commonStatusMessageResponse);
        }

        @Override
        public void onError(String error) {
            isFailed.setValue(error);
        }
    };
}
