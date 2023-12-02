package com.tonictrader.tonictrades.auth.login.repository;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.tonictrader.tonictrades.admin.model.FcmToken;
import com.tonictrader.tonictrades.admin.model.TradeDataModel;
import com.tonictrader.tonictrades.auth.login.signup.model.VersionCheckModel;
import com.tonictrader.tonictrades.base.CommonResponse;
import com.tonictrader.tonictrades.callback.CommonResponseListener;
import com.tonictrader.tonictrades.callback.LoginListener;
import com.tonictrader.tonictrades.callback.VersionCallback;
import com.tonictrader.tonictrades.network_remote.ApiInterface;
import com.tonictrader.tonictrades.network_remote.RetrofitService;
import com.tonictrader.tonictrades.utils.AppUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FcmRepository {
    private static FcmRepository fcmRepository;
    private final MutableLiveData<TradeDataModel> tradeDataModelMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<VersionCheckModel> versionCheckModelMutableLiveData = new MutableLiveData<>();

    private final MutableLiveData<FcmRepository> fcmRepositoryMutableLiveData = new MutableLiveData<>();



    private final ApiInterface apiInterfaceSpring;
    private final MutableLiveData<CommonResponse> commonResponseMutableLiveData = new MutableLiveData<>();
    public static FcmRepository getInstance(){
        if(fcmRepository==null){
            fcmRepository = new FcmRepository();
        }
        return fcmRepository;

    }

    public  FcmRepository(){

        apiInterfaceSpring = RetrofitService.createSpringBootService(ApiInterface.class);

    }

    public void loginUser(String email, String password, LoginListener loginListener){
        Call<LoginUserResponse> call = apiInterfaceSpring.loginUser(email, password);
        call.enqueue(new Callback<LoginUserResponse>() {
            @Override
            public void onResponse(Call<LoginUserResponse> call, Response<LoginUserResponse> response) {
                if(response.code()==200){
                    loginListener.onSucessLogin(response.body());

                }else{
                    loginListener.onErrorLogin("Invalid user-email/password.");
                }
            }

            @Override
            public void onFailure(Call<LoginUserResponse> call, Throwable t) {
                loginListener.onErrorLogin(t.getMessage());
            }
        });



    }


    //Calling the Api to check the version number from the server.
    public MutableLiveData<VersionCheckModel> getAppVersion(Context context, VersionCallback versionCallback){
        Call<VersionCheckModel> call = apiInterfaceSpring.checkVersion();
        call.enqueue(new Callback<VersionCheckModel>() {
            @Override
            public void onResponse(Call<VersionCheckModel> call, Response<VersionCheckModel> response) {
                if(response.isSuccessful()){

                    versionCallback.onSucessResponse(response.body());
                }


            }

            @Override
            public void onFailure(Call<VersionCheckModel> call, Throwable t) {

                versionCallback.onError("Oops Something Went Wrong !");
            }

        });



        return versionCheckModelMutableLiveData;


    }



    public MutableLiveData<CommonResponse>  postFCM(Context context, FcmToken fcmToken, CommonResponseListener commonResponseListener){
        Call<CommonResponse> call = apiInterfaceSpring.postFcmToken(fcmToken);



        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse commonResponse = response.body();
                /*Toast.makeText(context, response.body().getStatus(), Toast.LENGTH_SHORT).show();*/

                if(response.code()==200){
                    if(response.body().getStatus().contains("success")){
                        commonResponseListener.onSuccessResponse(response.body());
                    }
                }else {
                    commonResponseListener.onError("Could Not Insert Trade Data");
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {

                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();


                commonResponseMutableLiveData.postValue(null);
            }
        });

        return commonResponseMutableLiveData;
    }
}
