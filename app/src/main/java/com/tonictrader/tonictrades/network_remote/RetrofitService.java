package com.tonictrader.tonictrades.network_remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private static final String url = "https://api.tickertick.com/";
//    private static final String urlSpringBoot = "http://3.108.60.146:8080/";
//    private static final String urlSpringBoot = "http://13.235.156.27:6767/"; // NEW ELASTIC IP FOR EC2 INSTANCE.
    private static final String urlSpringBoot = "http://13.235.156.27:6767/";


    private static final OkHttpClient httpClient = new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS).build();


    //OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static final Gson gson = new GsonBuilder().setLenient().create();

    private static final Retrofit.Builder newsBuilder = new Retrofit.Builder().baseUrl(url);
    private static final Retrofit.Builder springBootBuilder = new Retrofit.Builder().baseUrl(urlSpringBoot);

    public static <S> S createNewsService(Class<S> serviceClass) {
        Retrofit retrofit = newsBuilder.client(httpClient).
                addConverterFactory(GsonConverterFactory.create(gson)).
                build();
        return retrofit.create(serviceClass);
    }
    public static <S> S createSpringBootService(Class<S> serviceClass) {
        Retrofit retrofit = springBootBuilder.client(httpClient).
                addConverterFactory(GsonConverterFactory.create(gson)).
                build();
        return retrofit.create(serviceClass);
    }

    public static Retrofit getRetrofit() {
        return springBootBuilder.client(httpClient).build();
    }

}
