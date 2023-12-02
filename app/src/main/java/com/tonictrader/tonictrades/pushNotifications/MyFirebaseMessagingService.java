package com.tonictrader.tonictrades.pushNotifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;
import android.webkit.URLUtil;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tonictrader.tonictrades.R;
import com.tonictrader.tonictrades.auth.login.LoginActivity;
import com.tonictrader.tonictrades.home.DashboardActivity;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    Context ct= this;
    public static boolean isNotificationClicked = false;

    //Overiding the Nesscary FirebaseMessagingMethods.


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        //Getting the Message from the Firebase using the Key Provided from the Backend.

        //You can Also Show The data in the Log..it is optional.

        //Getting the DATA ONCE THE KEY IS GIVEN.

        //Enter the Key  here in the try section PROVIDED FROM THE BACKEND.
        String reference_id = "", type="";
        try{
            //OPTIONAL TO SHOW DATA IN THE LOGCAT SHOWING FOR TESTING PURPOSE.


            Log.i("NOTIFICATION LOG", remoteMessage.getData()+"");


            //status ,body, title, order_id
            //Getting these Keys from the getData.
            // {status=0, body=2746348519Order was assigned to you, title=Order Assign, order_id=2746348519}


            //sendNotification(notificationTitle,notificationBody,orderIdNotification,orderStatusNotification);
            //Writing the Code for Based on Redirection to particular based on the status.

            //TODO THIS ONLY SHOWS IMAGE NOTIFICATION WHEN THE APP IS IN FOREGROUND.
            // NEED TO DO BACKEND AND FRONT END CHANGES AS WELL.
            String notificationBody = remoteMessage.getNotification().getBody();
            String notificationKeyTitle = remoteMessage.getNotification().getTitle();

            if(URLUtil.isHttpsUrl(notificationBody)){
                sendImageNotification(notificationBody,notificationKeyTitle,"HIGH");
            }else{
                sendNotification(notificationBody,notificationKeyTitle,"HIGH");
            }
            /*Toast.makeText(getApplicationContext(), orderStatusNotification, Toast.LENGTH_SHORT).show();*/


        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void sendImageNotification(String notificationBody, String notificationKeyTitle, String type) {
        try{
            Intent intent = null;
            intent = new Intent(this, DashboardActivity.class)
                    .putExtra("NOTIFICATION",type);


            /*    Intent intent = new Intent().setClassName("packagename", "packagename.YourActivityname");
            // give any activity name which you want to open
             */

            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP){
                currentapiVersion = R.mipmap.ic_launcher;
            } else{
                currentapiVersion = R.mipmap.ic_launcher;
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_IMMUTABLE);

            String CHANNEL_ID = "tonic_trades_channel_01";


            NotificationCompat.Builder mBuidler = new NotificationCompat.Builder(this);

            Notification notification = mBuidler.setSmallIcon(getNotificationIcon(mBuidler)).setTicker("TICKER_TEXT")
                    .setWhen(0)
                    .setAutoCancel(true)
                    .setSmallIcon(currentapiVersion)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_signals))
                    .setContentTitle(notificationKeyTitle)
                    .setContentText("Why This was suggested ? ")
                    .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(Glide.with(ct).asBitmap().load(notificationBody).submit().get()))
                    .setContentIntent(pendingIntent)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setChannelId(CHANNEL_ID)
                    .setContentText("Why This was suggested ? ").build();

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            int time = (int) System.currentTimeMillis();

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                //the id of the Channel.

                CharSequence name = "Tonic Trades   ";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                notificationManager.createNotificationChannel(mChannel);
            }
            notificationManager.notify(time,notification);
        } catch (Exception e){
            e.printStackTrace();
        }



    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }
    //Creating a Method to generate Push Notification. when on Message Received is Called.


    private void sendNotificationStackOverFlow(String msg, String notificationTitle) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        long notificatioId = System.currentTimeMillis();

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);// Here pass your activity where you want to redirect.

        intent.putExtra("NOTIFICATION_CLICK","NOTIFICATION");


        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(this, (int) (Math.random() * 100), intent, 0);

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP){
            currentapiVersion = R.mipmap.ic_launcher;
        } else{
            currentapiVersion = R.mipmap.ic_launcher;
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(currentapiVersion)
                .setContentTitle(notificationTitle)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setDefaults(Notification.FLAG_AUTO_CANCEL | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent);
        mNotificationManager.notify((int) notificatioId, notificationBuilder.build());
    }





    private void sendNotification(String title, String messageBody,String type){

        try{
            Intent intent = null;
            intent = new Intent(this, DashboardActivity.class)
                    .putExtra("NOTIFICATION",type);


            /*    Intent intent = new Intent().setClassName("packagename", "packagename.YourActivityname");
            // give any activity name which you want to open
             */

            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP){
                currentapiVersion = R.mipmap.ic_launcher;
            } else{
                currentapiVersion = R.mipmap.ic_launcher;
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_IMMUTABLE);

            String CHANNEL_ID = "tonic_trades_channel_01";


            NotificationCompat.Builder mBuidler = new NotificationCompat.Builder(this);

            Notification notification = mBuidler.setSmallIcon(getNotificationIcon(mBuidler)).setTicker("TICKER_TEXT")
                    .setWhen(0)
                    .setAutoCancel(true)
                    .setSmallIcon(currentapiVersion)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_signals))
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setContentIntent(pendingIntent)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setChannelId(CHANNEL_ID)
                    .setContentText(messageBody).build();

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            int time = (int) System.currentTimeMillis();

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                //the id of the Channel.

                CharSequence name = "Tonic Trades   ";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                notificationManager.createNotificationChannel(mChannel);
            }
            notificationManager.notify(time,notification);
        } catch (Exception e){
            e.printStackTrace();
        }


    }
    //Method to get Notification Icon.

    private int getNotificationIcon(NotificationCompat.Builder notificationBuilder){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            int color = 0x008000;
            notificationBuilder.setColor(color);
            return R.drawable.ic_signals;
        }
        return R.drawable.ic_signals;
    }


}
