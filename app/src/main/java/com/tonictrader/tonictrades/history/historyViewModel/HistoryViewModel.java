package com.tonictrader.tonictrades.history.historyViewModel;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tonictrader.tonictrades.admin.model.TradeHistoryModel;
import com.tonictrader.tonictrades.callback.TradeHistoryListener;
import com.tonictrader.tonictrades.history.historyRepository.HistoryRepository;

public class HistoryViewModel extends ViewModel {

    private Context context;
    private final MutableLiveData<String> isFailed = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isConnecting = new MutableLiveData<>();
    private MutableLiveData<TradeHistoryModel> tradeHistoryModelMutableLiveData;
    private HistoryRepository historyRepository;



    public LiveData<String> getIsFailed() {
        return isFailed;
    }

    public LiveData<Boolean> getIsConnecting() {
        return isConnecting;
    }


    public LiveData<TradeHistoryModel> getTradeInsertResponse(){
        if(tradeHistoryModelMutableLiveData==null){
            tradeHistoryModelMutableLiveData = new MutableLiveData<>();
        }
        return tradeHistoryModelMutableLiveData;
    }

    public void init(Context context){
        this.context = context;
        if(tradeHistoryModelMutableLiveData==null){
            return;
        }
        historyRepository = HistoryRepository.getInstance();

    }

    //Calling Insert Trade Api.
    public void getHistory(){
        isConnecting.setValue(true);
        historyRepository.getHistory(context,tradeHistoryListener);


    }

    TradeHistoryListener tradeHistoryListener = new TradeHistoryListener() {
        @Override
        public void onSuccessResponse(TradeHistoryModel tradeHistoryModel) {

            tradeHistoryModelMutableLiveData.postValue(tradeHistoryModel);
        }

        @Override
        public void onError(String error) {
            isFailed.setValue(error);
            Toast.makeText(context,error,Toast.LENGTH_LONG).show();

        }
    };


}
