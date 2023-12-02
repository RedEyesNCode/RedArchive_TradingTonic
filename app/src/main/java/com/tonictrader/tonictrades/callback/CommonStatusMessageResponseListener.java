package com.tonictrader.tonictrades.callback;

import com.tonictrader.tonictrades.auth.login.CommonStatusMessageResponse;

public interface CommonStatusMessageResponseListener {
    void onSuccessResponse(CommonStatusMessageResponse commonStatusMessageResponse);
    void onError(String error);

}
