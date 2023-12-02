package com.tonictrader.tonictrades.callback;

import com.tonictrader.tonictrades.auth.login.repository.LoginUserResponse;

public interface LoginListener {

    void onSucessLogin(LoginUserResponse loginUserResponse);
    void onErrorLogin(String error);
}
