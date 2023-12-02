package com.tonictrader.tonictrades.auth.login.viewmodel;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tonictrader.tonictrades.admin.model.FcmToken;
import com.tonictrader.tonictrades.auth.login.repository.FcmRepository;
import com.tonictrader.tonictrades.base.CommonResponse;
import com.tonictrader.tonictrades.callback.CommonResponseListener;

public class FcmViewModel extends ViewModel {
    private Context context;
    private final MutableLiveData<String> isFailed = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isConnecting = new MutableLiveData<>();
    private MutableLiveData<CommonResponse> commonResponseMutableLiveData;
    private FcmRepository fcmRepository;



    public LiveData<String> getIsFailed() {
        return isFailed;
    }

    public LiveData<Boolean> getIsConnecting() {
        return isConnecting;
    }


    public LiveData<CommonResponse> getFCMInsertResponse(){
        if(commonResponseMutableLiveData==null){
            commonResponseMutableLiveData = new MutableLiveData<>();
        }
        return commonResponseMutableLiveData;
    }

    public void init(Context context){
        this.context = context;
        if(commonResponseMutableLiveData==null){
            return;
        }
        fcmRepository = FcmRepository.getInstance();

    }

    //Calling Insert FCM Api.
    public void insertFcm(FcmToken fcmToken){
        isConnecting.setValue(true);
        fcmRepository.postFCM(context,fcmToken,commonResponseListener);


    }

    CommonResponseListener commonResponseListener = new CommonResponseListener() {
        @Override
        public void onSuccessResponse(CommonResponse commonResponse) {

            commonResponseMutableLiveData.postValue(commonResponse);
        }

        @Override
        public void onError(String error) {
            isFailed.setValue(error);
            Toast.makeText(context,error,Toast.LENGTH_LONG).show();

        }
    };

}
