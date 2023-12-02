package com.tonictrader.tonictrades.callback;

import com.tonictrader.tonictrades.base.CommonResponse;

public interface CommonResponseListener {
    void onSuccessResponse(CommonResponse commonResponse);
    void onError(String error);

}
