package com.tonictrader.tonictrades.callback;

import com.tonictrader.tonictrades.auth.login.signup.model.SignupResponse;

public interface SignupListener {
    void onSuccessResponse(SignupResponse signupResponse);
    void onError(String error);
}
