package com.tonictrader.tonictrades.callback;

import com.tonictrader.tonictrades.news.model.NewsData;

public interface NewsListener {

    void onSuccessResponse(NewsData newsData);
    void onError(String error);

}
