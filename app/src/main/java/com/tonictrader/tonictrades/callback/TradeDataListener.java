package com.tonictrader.tonictrades.callback;

import com.tonictrader.tonictrades.base.CommonResponse;

public interface TradeDataListener {

    void onSuccessResponse(CommonResponse commonResponse);
    void onError(String error);
}
