package com.tonictrade.tonictrade.firebase;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.google.firebase.messaging.*;
import com.tonictrade.tonictrade.datamodel.BodyTitle;
import com.tonictrade.tonictrade.datamodel.NotificationModelAll;
import com.tonictrade.tonictrade.datamodel.NotificationModelSet;
import com.tonictrade.tonictrade.repository.FcmRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

@Service
public class FCMService {

    private Logger logger = LoggerFactory.getLogger(FCMService.class);

    @Value("${application.bucket.name}")
    private String bucketName;

    private final String BUCKET_BASE_URL = "https://redeyesncodemaster.s3.ap-south-1.amazonaws.com/";

    @Autowired
    private AmazonS3 s3Client;

    private FcmRepository fcmRepository;

    public FCMService(AmazonS3 s3Client,FcmRepository fcmRepository) {
        this.s3Client = s3Client;
        this.fcmRepository = fcmRepository;
    }

/*public void sendMessage(Map<String, String> data, PushNotificationRequest request)
            throws InterruptedException, ExecutionException {
        //Message message = getPreconfiguredMessageWithData(data, request);
        String response = sendAndGetResponse(message);
        logger.info("Sent message with data. Topic: " + request.getTopic() + ", " + response);
    }*/

    /*public void sendMessageWithoutData(PushNotificationRequest request)
            throws InterruptedException, ExecutionException {
        //Message message = getPreconfiguredMessageWithoutData(request);
        String response = sendAndGetResponse(message);
        logger.info("Sent message without data. Topic: " + request.getTopic() + ", " + response);
    }
*/
    public void sendMessageToToken(NotificationModelSet request)
            throws InterruptedException, ExecutionException {

        Message message = getPreconfiguredMessageToToken(request);
        String response = sendAndGetResponse(message);
        logger.info("Sent message to token. Device token: " + request.getTo() + ", " + response);
    }

    public NotificationModelSet sendImageNotificationToTokens(MultipartFile file) throws ExecutionException, InterruptedException {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj).withCannedAcl(CannedAccessControlList.PublicRead));
        fileObj.delete();

        BodyTitle bodyTitle = new BodyTitle();
        bodyTitle.setClick_action(BUCKET_BASE_URL+fileName);
        bodyTitle.setTitle("Trade Information Available");
        bodyTitle.setBody(BUCKET_BASE_URL+fileName);
        NotificationModelSet request = new NotificationModelSet();
        request.setNotification(bodyTitle);
        request.setData(bodyTitle);

        for (int i = 0; i < fcmRepository.findAll().size(); i++) {
            request.setTo(fcmRepository.findAll().get(i).getFcmToken());
            sendMessageToToken(request);
        }
        return request;




    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            System.out.print("IO EXCEPTION..");
        }
        return convertedFile;
    }

    public void sendMessageToll(NotificationModelAll request)
            throws InterruptedException, ExecutionException {
        Message message = getPreconfiguredMessageToAll(request);
        String response = sendAndGetResponse(message);
        for (int i=0;i<request.getRegistration_ids().size();i++){
            logger.info("Sent message to token. Device token: " + request.getRegistration_ids().get(i) + ", " + response);
        }

    }

    private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
        return FirebaseMessaging.getInstance().sendAsync(message).get();
    }

    private AndroidConfig getAndroidConfig(String topic) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder().setSound(NotificationParameter.SOUND.getValue())
                        .setColor(NotificationParameter.COLOR.getValue()).setTag(topic).build()).build();
    }

    private ApnsConfig getApnsConfig(String topic) {
        return ApnsConfig.builder()
                .setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
    }

    private Message getPreconfiguredMessageToToken(NotificationModelSet request) {
        return getPreconfiguredMessageBuilder(request).setToken(request.getTo())
                .build();
    }
    private Message getPreconfiguredMessageToAll(NotificationModelAll request) {
        return getPreconfiguredMessageBuilderAll(request).setTopic("trade")
                .build();
    }


    private Message.Builder getPreconfiguredMessageBuilder(NotificationModelSet request) {
        AndroidConfig androidConfig = getAndroidConfig(request.getNotification().getTitle());
        ApnsConfig apnsConfig = getApnsConfig(request.getNotification().getTitle());
        return Message.builder()
                .setApnsConfig(apnsConfig).setAndroidConfig(androidConfig).setNotification(
                        new Notification(request.getNotification().getTitle(), request.getNotification().getBody()));
    }
    private Message.Builder getPreconfiguredMessageBuilderAll(NotificationModelAll request) {
        AndroidConfig androidConfig = getAndroidConfig(request.getNotification().getTitle());
        ApnsConfig apnsConfig = getApnsConfig(request.getNotification().getTitle());
        return Message.builder()
                .setApnsConfig(apnsConfig).setAndroidConfig(androidConfig).setNotification(
                        new Notification(request.getNotification().getTitle(), request.getNotification().getBody()));
    }


}
