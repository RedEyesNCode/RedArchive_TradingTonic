package com.tonictrader.tonictrades.home.repository;

import androidx.lifecycle.MutableLiveData;


import com.tonictrader.tonictrades.callback.HomeListener;
import com.tonictrader.tonictrades.home.model.RemoteTradesModel;
import com.tonictrader.tonictrades.network_remote.ApiInterface;
import com.tonictrader.tonictrades.network_remote.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeRepository {

    private static HomeRepository homeRepository;
    private final MutableLiveData<List<RemoteTradesModel>> tradeDataModelMutableLiveData = new MutableLiveData<>();
    public static HomeRepository getInstance(){
        if(homeRepository==null){
            homeRepository = new HomeRepository();
        }
        return homeRepository;

    }
    private final ApiInterface apiInterfaceSpring;
    public  HomeRepository(){

        apiInterfaceSpring = RetrofitService.createSpringBootService(ApiInterface.class);

    }

    public void getAllRemoteTrades(HomeListener homeListener){
        Call<ArrayList<RemoteTradesModel>> call = apiInterfaceSpring.getAllRemoteTrades();
        call.enqueue(new Callback<ArrayList<RemoteTradesModel>>() {
            @Override
            public void onResponse(Call<ArrayList<RemoteTradesModel>> call, Response<ArrayList<RemoteTradesModel>> response) {
                if(response.isSuccessful()){

                    homeListener.onSuccessRemoteTrades(response.body());
                }else{
                    homeListener.onErrorRemoteTrades("Server Error"+response.code());

                }

            }

            @Override
            public void onFailure(Call<ArrayList<RemoteTradesModel>> call, Throwable t) {
                homeListener.onErrorRemoteTrades(t.getMessage());
            }
        });



    }
}
