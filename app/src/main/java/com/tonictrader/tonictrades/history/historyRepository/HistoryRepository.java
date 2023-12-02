package com.tonictrader.tonictrades.history.historyRepository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.tonictrader.tonictrades.admin.model.TradeDataModel;
import com.tonictrader.tonictrades.admin.model.TradeHistoryModel;
import com.tonictrader.tonictrades.base.CommonResponse;
import com.tonictrader.tonictrades.callback.TradeHistoryListener;
import com.tonictrader.tonictrades.network_remote.ApiInterface;
import com.tonictrader.tonictrades.network_remote.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryRepository {
    private static HistoryRepository historyRepository;
    private final MutableLiveData<TradeDataModel> tradeDataModelMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<TradeHistoryModel> tradeHistoryModelMutableLiveData = new MutableLiveData<>();


    private final MutableLiveData<CommonResponse> commonResponseMutableLiveData = new MutableLiveData<>();
    public static HistoryRepository getInstance(){
        if(historyRepository==null){
            historyRepository = new HistoryRepository();
        }
        return historyRepository;

    }
    private final ApiInterface apiInterfaceSpring;
    public  HistoryRepository(){

        apiInterfaceSpring = RetrofitService.createSpringBootService(ApiInterface.class);

    }


    public MutableLiveData<TradeHistoryModel> getHistory(Context context,TradeHistoryListener tradeHistoryListener){
        Call<TradeHistoryModel> call = apiInterfaceSpring.getTradeHistory();
        call.enqueue(new Callback<TradeHistoryModel>() {
            @Override
            public void onResponse(Call<TradeHistoryModel> call, Response<TradeHistoryModel> response) {

                if(response.code()==200){
                    if(response.body().getStatus().contains("success")){
                        tradeHistoryListener.onSuccessResponse(response.body());
                    }else {
                        tradeHistoryListener.onError("Could Not Insert Trade Data");
                    }
                }
            }

            @Override
            public void onFailure(Call<TradeHistoryModel> call, Throwable t) {


                tradeDataModelMutableLiveData.postValue(null);
            }
        });

        return tradeHistoryModelMutableLiveData;
    }



}
