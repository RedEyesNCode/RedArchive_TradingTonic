package com.tonictrader.tonictrades.callback;

import com.tonictrader.tonictrades.auth.login.signup.model.VersionCheckModel;

public interface VersionCallback {

    void onSucessResponse(VersionCheckModel versionCheckModel);
    void onError(String error);
}
