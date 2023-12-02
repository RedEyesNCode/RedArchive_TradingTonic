package com.tonictrade.tonictrade.controllers;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.tonictrade.tonictrade.datamodel.*;

import com.tonictrade.tonictrade.service.ApiService;
import com.tonictrade.tonictrade.service.PushNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ApiController {


    private ApiService apiService;

    private PushNotificationService pushNotificationService;



    @Autowired
    public ApiController(ApiService apiService, PushNotificationService pushNotificationService) {
        this.apiService = apiService;
        this.pushNotificationService = pushNotificationService;


    }



    @PostMapping("/fcm")
    public StatusCodeModel insertFcm(@RequestBody FcmTokenModel fcmTokenModel){
        //TODO NETWORK BOOSTER FCM
        return  apiService.saveFcmTokenForNetwork(fcmTokenModel);

//        return apiService.saveFcmToken(fcmTokenModel);
    }


    @GetMapping("/tokens")
    public FcmTokenJson getTokens(){
        List<FcmTokenModel> fcmTokenModels = new ArrayList<>();
        fcmTokenModels = apiService.getTokens();
        if(fcmTokenModels.size()==0){
            return new FcmTokenJson("fail",200,fcmTokenModels);
        }else {
            return new FcmTokenJson("success",200,fcmTokenModels);
        }


    }

    @PostMapping("/tradeData")
    public StatusCodeModel insertTradeDataRealtime(@RequestBody TradeDataModel tradeDataModel){
        sendNotification(tradeDataModel);

       return apiService.saveTradeData(tradeDataModel);
    }

    @GetMapping("/getHistory")
    public TradeHistoryJson getTradeHistory(){
        List<TradeHistoryModel> tradesHistory = new ArrayList<>();
        tradesHistory = apiService.getAllTrades();
        if(tradesHistory.size()==0){
            return new TradeHistoryJson("fail",200,tradesHistory);
        }else {
            return new TradeHistoryJson("success",200,tradesHistory);
        }

    }
    //Make another api for push notification for fcm on insert in data
    //TradeData api will insert in real time database in tradeHistoryTable and also push notification of fcm.

    @PostMapping("/notification/token") //to Single User
    public ResponseEntity sendTokenNotification(@RequestBody NotificationModelSet request) {
        pushNotificationService.sendPushNotificationToToken(request);
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }

    @PostMapping("/sendImageNotification")
    public NotificationModelSet sendImageNotification(@RequestParam("notification_image") MultipartFile notificationImage){





        List<FcmTokenModel> fcmTokenModels = new ArrayList<>();
        fcmTokenModels = apiService.getTokens();
        if(fcmTokenModels.size()==0){
            return null;
        }else {
            return pushNotificationService.sendPushNotificationImage(notificationImage);

        }

    }




    //Send to all the FCM tokens Present in the Database.
    @GetMapping("/sendAll")
    public StatusCodeModel sendNotification(TradeDataModel tradeDataModel){
        String tradeType = tradeDataModel.getTitle();
        NotificationModelSet notificationModelSet = new NotificationModelSet();
        if(tradeType.length()==5){
            notificationModelSet.setData(new BodyTitle("Nifty Trade has Been Suggested !"," Nifty Tonic Trade ! "));
            notificationModelSet.setNotification(new BodyTitle("Nifty Trade has Been Suggested","Nifty Tonic Trade !"));
        }else {
            notificationModelSet.setData(new BodyTitle("Bank Nifty Trade has Been Suggested !","Bank Nifty Tonic Trade ! "));
            notificationModelSet.setNotification(new BodyTitle("Bank Trade has Been Suggested","Bank Nifty Tonic Trade!"));

        }




        List<FcmTokenModel> fcmTokenModels = new ArrayList<>();
        fcmTokenModels = apiService.getTokens();
        if(fcmTokenModels.size()==0){
            return new StatusCodeModel("fail",200);
        }else {

            for (int i=0;i<fcmTokenModels.size();i++){
                notificationModelSet.setTo(fcmTokenModels.get(i).getFcmToken());
                pushNotificationService.sendPushNotificationToToken(notificationModelSet);
            }
            return new StatusCodeModel("success",200);
        }


    }
    //Api to Send Custom Notification to all users.
        @PostMapping("/notificationCustom")
    public StatusCodeModel sendNotificationCustom(@RequestBody BodyTitle bodyTitle){

        NotificationModelSet notificationModelSet = new NotificationModelSet();
        notificationModelSet.setData(new BodyTitle(bodyTitle.getBody(), bodyTitle.getTitle()));
        notificationModelSet.setNotification(new BodyTitle(bodyTitle.getBody(), bodyTitle.getTitle()));
        List<FcmTokenModel> fcmTokenModels = new ArrayList<>();
        fcmTokenModels = apiService.getTokens();
        if(fcmTokenModels.size()==0){
            return new StatusCodeModel("fail",200);
        }else {

            for (int i=0;i<fcmTokenModels.size();i++){
                notificationModelSet.setTo(fcmTokenModels.get(i).getFcmToken());
                pushNotificationService.sendPushNotificationToToken(notificationModelSet);
            }
            return new StatusCodeModel("success",200);
        }
    }
    //Api to send Custom Notification to all fast.
    @PostMapping("/fastNotification")
    public StatusCodeModel sendNotiificationFast2All(@RequestBody BodyTitle bodyTitle){
        NotificationModelAll notificationModelAll = new NotificationModelAll();
        notificationModelAll.setNotification(new BodyTitle(bodyTitle.getBody(), bodyTitle.getTitle()));
        notificationModelAll.setData(new BodyTitle(bodyTitle.getBody(),bodyTitle.getTitle()));

        List<FcmTokenModel> fcmTokenModels = new ArrayList<>();
        ArrayList<String> tokens = new ArrayList<>();
        fcmTokenModels = apiService.getTokens();
        if(fcmTokenModels.size()==0){

            return new StatusCodeModel("fail",200);
        }else {
            for (int  i=0;i<fcmTokenModels.size();i++){
                tokens.add(fcmTokenModels.get(i).getFcmToken());
            }
            if(tokens.size()==0){
                return new StatusCodeModel("fail",200);
            }else {
                notificationModelAll.setRegistration_ids(tokens);
                pushNotificationService.sendPushNotificationToAll(notificationModelAll);
                return new StatusCodeModel("success",200);
            }


        }



    }
    //Api to get App Version
    @GetMapping("/getVersion")
    public AppVersionResponse getAppVersion(){

        return apiService.getAppVersion();

    }

   /* @PostMapping("/updateAppVersion")
    public StatusCodeModel postAppVersion(@RequestBody AppVersionModel appVersionModel){

        return apiService.updateAppVersion(appVersionModel);

    }*/




    //Api to store user data at the sign up screen
    @PostMapping("/signup")
    public SignupResponse postSignupUser(@RequestBody SignupModel signupModel){
        return apiService.InsertUsertoDB(signupModel);
//        return  apiService.signupUser(signupModel);
    }


    @PostMapping("/getTradeCalender")
    public TradeCalenderResponse getTradeCalender(@RequestBody HashMap<String,Integer> month){

        return apiService.getTradeCalender(month.get("month"));

    }


   @GetMapping("/getUsers")
   public List<SignupModel> getUsers(){
        return apiService.getAllUsers();
   }






    //OnGoing Procedure.
    //Api to get user details on the behalf of the id provided.
    @GetMapping("/userdetail")
    public SignupModel getUserDetail(@RequestParam("userId") int id){
        return apiService.getUserDetails(id);

    }

    @GetMapping("/loginUser")
    public SignupModel getUserLogin(@RequestParam("email") String email,@RequestParam("password") String password){

        return apiService.loginUser(email,password);
    }

    @PostMapping("/check-user-unique")
    public StatusCodeModel checkifUserExists(@RequestParam("email") String email,@RequestParam("number") String number){
        StatusCodeModel statusCodeModel = new StatusCodeModel();
        List<SignupModel> signupModels = apiService.getAllUsers();
        for (int i = 0; i < signupModels.size(); i++) {
            if(signupModels.get(i).getUserEmail().equals(email)){
                statusCodeModel.setCode(400);
                statusCodeModel.setStatus("fail");
                statusCodeModel.setMessage("Email is Already registered");
                break;
            }else if (signupModels.get(i).getNumber().equals(number)){
                statusCodeModel.setCode(400);
                statusCodeModel.setStatus("fail");
                statusCodeModel.setMessage("Number is Already registered");
                break;
            }else{
                statusCodeModel.setCode(200);
                statusCodeModel.setStatus("success");
                statusCodeModel.setMessage("Found a unique user !");
            }
        }
        return statusCodeModel;
    }

    @PostMapping("/updateTradeData")
    public StatusCodeModel updateTradeData(@RequestBody TradeHistoryModel tradeHistoryModel){
        sendBackendNotification("Trade Information updated !","New Trade Info !");



        return apiService.updateTradeData(tradeHistoryModel);
    }
    @PostMapping("/uploadTradeImage")
    public ResponseEntity<?> uploadProfileImage(@RequestParam(value = "trade_image") MultipartFile file, @RequestParam("tradeId") Long userId){
        sendBackendNotification("Trade image has been uploaded","Trade Information available.");
        return new ResponseEntity<>(apiService.uploadTradeInfoImage(file,userId), HttpStatus.OK);
    }

    @PostMapping("/uploadToEC2")
    public ResponseEntity<?> uploadToEc2(@RequestParam(value = "image")MultipartFile file){
        return apiService.uploadToEC2Server(file);

    }

    @PostMapping("/uploadRemoteTradeImage")
    public ResponseEntity<?> uploadRemoteTradeImage(@RequestParam(value = "remote_trade_image")MultipartFile file,@RequestParam("remoteTradeId") Long remoteTradeId){
        sendBackendNotification("Trade image has been uploaded","Trade Information available.");
        return new ResponseEntity<>(apiService.uploadRemoteTradeImage(file,remoteTradeId),HttpStatus.OK);


    }

    @PostMapping("/addRemoteTrade")
    public RemoteTradeOneData addRemoteTrade(@RequestBody RemoteTradeOneData remoteTradeOneData){
        saveRemoteTrade(remoteTradeOneData);
        sendBackendNotification("Trade has been suggested","Tonic Trade is here !");

        return apiService.saveRemoteTrade(remoteTradeOneData);

    }

    @GetMapping("/getAllRemoteTrade")
    public List<RemoteTradeOneData> getAllRemoteTrade(){


        return apiService.getAllRemoteTrades();
    }

    @PostMapping("/updateRemoteTrade")
    public StatusCodeModel updateRemoteTrade(@RequestBody RemoteTradeOneData remoteTradeOneData){

        saveRemoteTrade(remoteTradeOneData);
        sendBackendNotification("Trade has been Updated","Tonic Trade is here !");


        return apiService.updateRemoteTrade(remoteTradeOneData);
    }

    public void sendBackendNotification(String body, String title){

        NotificationModelSet notificationModelSet = new NotificationModelSet();

        List<FcmTokenModel> fcmTokenModels = apiService.getTokens();
        notificationModelSet.setNotification(new BodyTitle(body,title));
        notificationModelSet.setData(new BodyTitle(body,title));


        for (int i=0;i<fcmTokenModels.size();i++){
            notificationModelSet.setTo(fcmTokenModels.get(i).getFcmToken());
            pushNotificationService.sendPushNotificationToToken(notificationModelSet);
        }


    }

    public void saveRemoteTrade(RemoteTradeOneData remoteTradeOneData){

        TradeDataModel tradeDataModel = new TradeDataModel();
        tradeDataModel.setTradeData(remoteTradeOneData.getTradeData());
        tradeDataModel.setTradeMessage(remoteTradeOneData.getTradeData());
        tradeDataModel.setCategory(remoteTradeOneData.getTitle());
        tradeDataModel.setMonth(remoteTradeOneData.getMonth());
        tradeDataModel.setTarget(remoteTradeOneData.getTarget());
        tradeDataModel.setStopLoss(remoteTradeOneData.getStopLoss());
        tradeDataModel.setStrikePrice(remoteTradeOneData.getStrikePrice());
        tradeDataModel.setTitle(remoteTradeOneData.getTitle());
        tradeDataModel.setCategory(remoteTradeOneData.getCategory());
        tradeDataModel.setTradeDate(remoteTradeOneData.getTradeDate());

        apiService.saveToTradeHistory(tradeDataModel);

    }


    @PostMapping("/deleteRemoteTrade")
    public StatusCodeModel deleteRemoteTrade(@RequestParam("id") int id){
        return apiService.deleteRemoteTrade(id);
    }


    @PostMapping("/deleteHistory")
    public ResponseEntity<?> deleteHistoryTrade(@RequestParam("id") int id){

        return apiService.deleteHistoryTradeService(id);
    }


    @PostMapping("/crudHistory")
    public ResponseEntity<?> CrudTradeHistory(@RequestBody TradeHistoryModel tradeHistoryModel){

        return  apiService.crudTradeHistory(tradeHistoryModel);
    }
    @PostMapping("/addFakeTrades")
    public StatusCodeModel addFakeTrades(@RequestBody TradeDataModel tradeDataModel){
        return apiService.saveToTradeHistory(tradeDataModel);

    }




}
