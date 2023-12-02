package com.tonictrader.tonictrades.chat;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tonictrader.tonictrades.auth.login.CommonStatusMessageResponse;
import com.tonictrader.tonictrades.callback.CommonStatusMessageResponseListener;

import java.io.File;

public class ChatViewmodel extends ViewModel {
    private final MutableLiveData<String> isFailed = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isConnecting = new MutableLiveData<>();
    private MutableLiveData<CommonStatusMessageResponse> commonStatusMessageResponseMutableLiveData;
    public LiveData<String> getIsFailed() {
        return isFailed;
    }
    private Context context;
    public LiveData<Boolean> getIsConnecting() {
        return isConnecting;
    }
    private ChatRepository chatRepository;
    public LiveData<CommonStatusMessageResponse> observeImageUploadResponse(){
        if(commonStatusMessageResponseMutableLiveData==null){
            commonStatusMessageResponseMutableLiveData = new MutableLiveData<>();

        }
        return  commonStatusMessageResponseMutableLiveData;
    }
    public void uploadImageToEC2(File image){
        isConnecting.setValue(true);
        ChatRepository adminRepository = ChatRepository.getInstance();
        adminRepository.uploadMediaFile(image,commonStatusMessageListener);
    }
    CommonStatusMessageResponseListener commonStatusMessageListener = new CommonStatusMessageResponseListener() {
        @Override
        public void onSuccessResponse(CommonStatusMessageResponse commonStatusMessageResponse) {
            commonStatusMessageResponseMutableLiveData.postValue(commonStatusMessageResponse);
        }

        @Override
        public void onError(String error) {

            isFailed.setValue(error);
        }
    };



    public void init(Context context) {
        this.context=context;
        chatRepository= new ChatRepository();
    }
}
