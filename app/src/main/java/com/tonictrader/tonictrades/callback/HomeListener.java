package com.tonictrader.tonictrades.callback;

import com.tonictrader.tonictrades.home.model.RemoteTradesModel;

import java.util.ArrayList;

public interface HomeListener {
    void onSuccessRemoteTrades(ArrayList<RemoteTradesModel> remoteTradesModels);
    void onErrorRemoteTrades(String error);

}
