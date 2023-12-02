package com.tonictrader.tonictrades.news.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.tonictrader.tonictrades.callback.NewsListener;
import com.tonictrader.tonictrades.network_remote.ApiInterface;
import com.tonictrader.tonictrades.network_remote.RetrofitService;
import com.tonictrader.tonictrades.news.model.NewsData;
import com.tonictrader.tonictrades.utils.AppUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    private static NewsRepository newsRepository;
    private final MutableLiveData<NewsData> newsDataMutableLiveData = new MutableLiveData<>();

    public static NewsRepository getInstance(){
        if(newsRepository==null){
            newsRepository = new NewsRepository();

        }
        return newsRepository;
    }

    private final ApiInterface apiInterface;
    public NewsRepository(){

        apiInterface = RetrofitService.createNewsService(ApiInterface.class);



    }
    //Calling the News Api.
    public MutableLiveData<NewsData> getNews(Context context, String language, int number, NewsListener newsListener){
        Call<NewsData> call = apiInterface.getNews(language,number);
        call.enqueue(new Callback<NewsData>() {
            @Override
            public void onResponse(Call<NewsData> call, Response<NewsData> response) {
                if(response.code()==200 && response.body()!=null){
                    newsListener.onSuccessResponse(response.body());
                }else {
                    String serverHandling = AppUtils.getServerError(response.code(),response.errorBody(),context);
                    newsListener.onError(serverHandling);
                }
            }

            @Override
            public void onFailure(Call<NewsData> call, Throwable t) {

                newsListener.onError("Oops Something Went Wrong !");

                newsDataMutableLiveData.postValue(null);
            }
        });

        return newsDataMutableLiveData;
    }



}
