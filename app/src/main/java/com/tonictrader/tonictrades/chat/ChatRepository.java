package com.tonictrader.tonictrades.chat;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.tonictrader.tonictrades.admin.model.FcmToken;
import com.tonictrader.tonictrades.auth.login.CommonStatusMessageResponse;
import com.tonictrader.tonictrades.auth.login.repository.FcmRepository;
import com.tonictrader.tonictrades.base.CommonResponse;
import com.tonictrader.tonictrades.callback.CommonResponseListener;
import com.tonictrader.tonictrades.callback.CommonStatusMessageResponseListener;
import com.tonictrader.tonictrades.network_remote.ApiInterface;
import com.tonictrader.tonictrades.network_remote.RetrofitService;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatRepository {

    private static ChatRepository chatRepository;
    private final ApiInterface apiInterfaceSpring;

    private final MutableLiveData<CommonStatusMessageResponse> commonStatusMessageResponseMutableLiveData = new MutableLiveData<>();
    public static ChatRepository getInstance(){
        if(chatRepository==null){
            chatRepository = new ChatRepository();
        }
        return chatRepository;

    }

    public  ChatRepository(){

        apiInterfaceSpring = RetrofitService.createSpringBootService(ApiInterface.class);

    }

    public void  uploadMediaFile(File file, CommonStatusMessageResponseListener commonResponseListener){
        RequestBody profilePicRequestBody = RequestBody.create(MediaType.parse("image/jpeg"),file);
        MultipartBody.Part multiPartProfilePic = MultipartBody.Part.createFormData("image",file.getName(),profilePicRequestBody);
        Call<CommonStatusMessageResponse> call = apiInterfaceSpring.uploadImageToEC2(multiPartProfilePic);



       call.enqueue(new Callback<CommonStatusMessageResponse>() {
           @Override
           public void onResponse(Call<CommonStatusMessageResponse> call, Response<CommonStatusMessageResponse> response) {
               if(response.code()==200){
                   commonResponseListener.onSuccessResponse(response.body());
               }else {
                   commonResponseListener.onError("Could Not Insert Trade Data");
               }
           }

           @Override
           public void onFailure(Call<CommonStatusMessageResponse> call, Throwable t) {
               commonResponseListener.onError(t.getMessage());

           }
       });

    }
}
