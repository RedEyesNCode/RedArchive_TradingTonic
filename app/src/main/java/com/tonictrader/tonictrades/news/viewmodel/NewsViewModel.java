package com.tonictrader.tonictrades.news.viewmodel;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tonictrader.tonictrades.callback.NewsListener;
import com.tonictrader.tonictrades.news.model.NewsData;
import com.tonictrader.tonictrades.news.repository.NewsRepository;

public class NewsViewModel extends ViewModel {
    private Context context;
    private final MutableLiveData<String> isFailed = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isConnecting = new MutableLiveData<>();
    private MutableLiveData<NewsData> newsDataMutableLiveData;
    private NewsRepository newsRepository;

    public LiveData<String> getIsFailed() {
        return isFailed;
    }

    public LiveData<Boolean> getIsConnecting() {
        return isConnecting;
    }

    public LiveData<NewsData> getNewsResponse(){
        if(newsDataMutableLiveData==null){
            newsDataMutableLiveData = new MutableLiveData<>();
        }
        return newsDataMutableLiveData;
    }

    public void init(Context context){
        this.context = context;
        if(newsDataMutableLiveData==null){
            return;
        }
        newsRepository = NewsRepository.getInstance();

    }

    //Calling the News Api
    public void getNews(String langage , int count){
        isConnecting.setValue(true);
        newsRepository.getNews(context,langage,count,newsListener);

    }
    NewsListener newsListener = new NewsListener() {
        @Override
        public void onSuccessResponse(NewsData newsData) {
            newsDataMutableLiveData.postValue(newsData);
        }

        @Override
        public void onError(String error) {

            isFailed.setValue(error);
            Toast.makeText(context,error,Toast.LENGTH_LONG).show();
        }
    };




}
