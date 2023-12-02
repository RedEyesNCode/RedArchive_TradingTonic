package com.tonictrade.tonictrade.service;

import com.tonictrade.tonictrade.datamodel.NotificationModelAll;
import com.tonictrade.tonictrade.datamodel.NotificationModelSet;
import com.tonictrade.tonictrade.firebase.FCMService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class PushNotificationService {

    @Value("#{${app.notifications.defaults}}")
    private Map<String, String> defaults;

    private Logger logger = LoggerFactory.getLogger(PushNotificationService.class);
    private FCMService fcmService;

    private ApiService apiService;

    @Autowired
    public PushNotificationService(FCMService fcmService,ApiService apiService) {
        this.fcmService = fcmService;
        this.apiService=apiService;
    }

    /*@Scheduled(initialDelay = 60000, fixedDelay = 60000)
    public void sendSamplePushNotification() {
        try {
            fcmService.sendMessageWithoutData(getSamplePushNotificationRequest());
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendPushNotification(PushNotificationRequest request) {
        try {
            fcmService.sendMessage(getSamplePayloadData(), request);
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendPushNotificationWithoutData(PushNotificationRequest request) {
        try {
            fcmService.sendMessageWithoutData(request);
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }*/


    public void sendPushNotificationToToken(NotificationModelSet request) {
        try {
            fcmService.sendMessageToToken(request);
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }


    public void sendPushNotificationToAll(NotificationModelAll request) {
        try {
            fcmService.sendMessageToll(request);
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }


    public NotificationModelSet sendPushNotificationImage(MultipartFile notificationImage) {
        try {
            return fcmService.sendImageNotificationToTokens(notificationImage);
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
            return null;
        }

    }
}
