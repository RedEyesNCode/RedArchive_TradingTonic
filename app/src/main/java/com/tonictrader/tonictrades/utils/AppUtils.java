package com.tonictrader.tonictrades.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tonictrader.tonictrades.admin.model.FcmToken;
import com.tonictrader.tonictrades.auth.login.CommonStatusMessageResponse;
import com.tonictrader.tonictrades.auth.login.repository.FcmRepository;
import com.tonictrader.tonictrades.base.CommonResponse;
import com.tonictrader.tonictrades.callback.CommonResponseListener;
import com.tonictrader.tonictrades.home.DashboardActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;

public class AppUtils {

    //NULL_POINTER_STRING_METHOD
    public static boolean isNullEmpty(String str) {

        // check if string is null
        if (str == null) {
            return true;
        } else return str.isEmpty();
    }
    public static boolean isValidEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
    public static String checkIfNullTextView(String value) {

        if (value == null || value.isEmpty()) {

            return "";

        }

        return value;

    }

    public static String getServerError(int responseCode, ResponseBody responseBody, Context context)
    {
        String serverHandling =  "Error "+responseCode+" "+"Please try again.";

        switch (responseCode){
            case 401:
                //LogOutTheUser
                //AppUtils.backToLoginScreen(context);
                Toast.makeText(context, "Unauthorized Access : Invalid Token Error : 401", Toast.LENGTH_SHORT).show();
                serverHandling = "401";
                break;
            case 400:
                //Bad Request Display The Message

                try {
                    if(responseBody!=null){
                        Gson gson = new GsonBuilder().create();
                        CommonStatusMessageResponse commonStatusMessageResponseOne = new CommonStatusMessageResponse();
                        commonStatusMessageResponseOne = gson.fromJson(responseBody.string(),CommonStatusMessageResponse.class);
                        Log.i("ERROR_BODY",commonStatusMessageResponseOne.getMessage());
                        serverHandling = commonStatusMessageResponseOne.getMessage();
                        return serverHandling;
                    }
                } catch (Exception e){

                    e.printStackTrace();
                }



        }
        return serverHandling;

    }

    // Getting more network by notifications
    public static void insertFcmToken(Context context){
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(s -> {
            CommonResponseListener commonResponseListener =  new CommonResponseListener() {
                @Override
                public void onSuccessResponse(CommonResponse commonResponse) {
                }
                @Override
                public void onError(String error) {

                }
            };
            FcmRepository fcmRepository = new FcmRepository();
            FcmToken fcmToken = new FcmToken();
            fcmToken.setFcmToken(s);
            fcmToken.setUserName(Build.DEVICE);

            Log.i("FCM_TOKEN",s);

            fcmRepository.postFCM(context,fcmToken,commonResponseListener);

        });



    }

    public static File getFile(Context context, Uri uri) throws IOException {
        try {
            File destinationFilename = new File(context.getFilesDir().getPath() + File.separatorChar + queryName(context, uri));
            try (InputStream ins = context.getContentResolver().openInputStream(uri)) {
                createFileFromStream(ins, destinationFilename);
            } catch (Exception ex) {
                Log.e("Save File", ex.getMessage());
                ex.printStackTrace();
            }
            return destinationFilename;
        }catch (Exception e){
            e.printStackTrace();
            return null ;
        }
    }
    public static void createFileFromStream(InputStream ins, File destination) {
        try (OutputStream os = new FileOutputStream(destination)) {
            byte[] buffer = new byte[4096];
            int length;
            while ((length = ins.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.flush();
        } catch (Exception ex) {
            Log.e("Save File", ex.getMessage());
            ex.printStackTrace();
        }
    }
    private static String queryName(Context context, Uri uri) {
        Cursor cursor = null;
        try {
            String name = null ;
            if (uri.getScheme().equals("content")) {
                cursor = context.getContentResolver().query(uri, null, null, null, null);
                assert cursor != null;
                int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                cursor.moveToFirst();
                name = cursor.getString(nameIndex);
            }
            if (name == null) {
                name = uri.getPath();
                int cut = name.lastIndexOf('/');
                if (cut != -1) {
                    name = name.substring(cut + 1);
                }
            }

            return name;
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }finally {
            if(null != cursor){
                cursor.close();
            }
        }
    }



}
