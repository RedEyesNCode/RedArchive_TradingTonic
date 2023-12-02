package com.tonictrader.tonictrades.network_remote;

import com.tonictrader.tonictrades.admin.model.FcmToken;
import com.tonictrader.tonictrades.admin.model.TradeDataModel;
import com.tonictrader.tonictrades.admin.model.TradeHistoryModel;
import com.tonictrader.tonictrades.auth.login.CommonStatusMessageResponse;
import com.tonictrader.tonictrades.auth.login.repository.LoginUserResponse;
import com.tonictrader.tonictrades.auth.login.signup.model.SignupModel;
import com.tonictrader.tonictrades.auth.login.signup.model.SignupResponse;
import com.tonictrader.tonictrades.auth.login.signup.model.VersionCheckModel;
import com.tonictrader.tonictrades.base.CommonResponse;
import com.tonictrader.tonictrades.home.model.RemoteTradesModel;
import com.tonictrader.tonictrades.news.model.NewsData;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("feed")
    Call<NewsData> getNews(@Query("lang") String language, @Query("n") int numberOfStories);


    @GET("api/v1/getHistory")
    Call<TradeHistoryModel> getTradeHistory();

    @POST("api/v1/fcm")
    Call<CommonResponse> postFcmToken(@Body FcmToken fcmToken);


    @POST("/api/v1/signup")
    Call<SignupResponse> signupUser(@Body SignupModel signupModel);


    @GET("/api/v1/loginUser")
    Call<LoginUserResponse> loginUser(@Query("email") String email, @Query("password") String password);


    @GET("api/v1/getVersion")
    Call<VersionCheckModel> checkVersion();

    @GET("api/v1/getAllRemoteTrade")
    Call<ArrayList<RemoteTradesModel>> getAllRemoteTrades();

    @POST("api/v1/check-user-unique")
    Call<CommonStatusMessageResponse> checkUser(@Query("email") String email,@Query("number") String number);

    @Multipart
    @POST("api/v1/uploadToEC2")
    Call<CommonStatusMessageResponse> uploadImageToEC2(@Part MultipartBody.Part image);

}
