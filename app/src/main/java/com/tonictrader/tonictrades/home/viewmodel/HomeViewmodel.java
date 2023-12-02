package com.tonictrader.tonictrades.home.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tonictrader.tonictrades.auth.login.CommonStatusMessageResponse;
import com.tonictrader.tonictrades.auth.login.signup.repository.SignupRepository;
import com.tonictrader.tonictrades.callback.HomeListener;
import com.tonictrader.tonictrades.home.model.RemoteTradesModel;
import com.tonictrader.tonictrades.home.repository.HomeRepository;

import java.util.ArrayList;

public class HomeViewmodel extends ViewModel {
    private final MutableLiveData<String> isFailed = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isConnecting = new MutableLiveData<>();

    private MutableLiveData<ArrayList<RemoteTradesModel>> remoteTrades = new MutableLiveData<>();

    private HomeRepository homeRepository;


    public void init(){
        if(homeRepository==null){
            return;
        }
        homeRepository = HomeRepository.getInstance();

    }

    public LiveData<String> getIsFailed() {
        return isFailed;
    }

    public LiveData<Boolean> getIsConnecting() {
        return isConnecting;
    }

    public LiveData<ArrayList<RemoteTradesModel>> observeRemoteTrades(){
        if(remoteTrades==null){
            remoteTrades = new MutableLiveData<>();
        }

        return remoteTrades;
    }

    public void getAllRemoteTrades(){
        isConnecting.setValue(true);
        homeRepository = HomeRepository.getInstance();
        homeRepository.getAllRemoteTrades(homeListener);
    }

    HomeListener homeListener = new HomeListener() {
        @Override
        public void onSuccessRemoteTrades(ArrayList<RemoteTradesModel> remoteTradesModels) {
            remoteTrades.setValue(remoteTradesModels);
        }

        @Override
        public void onErrorRemoteTrades(String error) {
            isFailed.setValue(error);
        }
    };

}
