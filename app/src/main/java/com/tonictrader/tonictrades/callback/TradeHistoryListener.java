package com.tonictrader.tonictrades.callback;

import com.tonictrader.tonictrades.admin.model.TradeHistoryModel;

public interface TradeHistoryListener {
    void onSuccessResponse(TradeHistoryModel tradeHistoryModel);
    void onError(String error);
}
