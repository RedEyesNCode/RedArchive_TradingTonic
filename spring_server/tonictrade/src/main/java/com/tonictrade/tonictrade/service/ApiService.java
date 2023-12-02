package com.tonictrade.tonictrade.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.tonictrade.tonictrade.datamodel.*;
import com.tonictrade.tonictrade.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Service
public class ApiService {


    private FcmRepository fcmRepository;
    private TradeDataRepository tradeDataRepository;
    private TradeHistoryRepository tradeHistoryRepository;
    private UserDataRepository userDataRepository;
    private VersionRepository versionRepository;
    private final String DATE_FORMAT = "yyyy-MM-dd";

    private final String BUCKET_BASE_URL = "https://redeyesncode.s3.ap-south-1.amazonaws.com/";

    private RemoteTradeOneRepository remoteTradeOneRepository;


    @Value("${application.bucket.name}")
    private String bucketName;


    @Autowired
    private AmazonS3 s3Client;



    @Autowired
    public ApiService(TradeHistoryRepository tradeHistoryRepository, RemoteTradeOneRepository remoteTradeOneRepository,VersionRepository versionRepository,FcmRepository fcmRepository, TradeDataRepository tradeDataRepository, UserDataRepository userDataRepository) {
        this.tradeHistoryRepository = tradeHistoryRepository;
        this.fcmRepository = fcmRepository;
        this.versionRepository = versionRepository;
        this.tradeDataRepository =tradeDataRepository;
        this.userDataRepository = userDataRepository;
        this.remoteTradeOneRepository = remoteTradeOneRepository;
    }

    //Declare methods here.

    //TODO INSERT NETWORK BOOSTER FCM TOKEN INSERTION
    public StatusCodeModel saveFcmTokenForNetwork(FcmTokenModel fcmTokenModel){
        fcmRepository.save(fcmTokenModel);
        return new StatusCodeModel("success",200,"FCM NETWORK BOOSTED");


    }


    //Saving fcm token of the user.
    public StatusCodeModel saveFcmToken(FcmTokenModel fcmTokenModel){

        String message= "";
        Optional<FcmTokenModel> fcmTokenModelOptional = fcmRepository.findByfcmToken(fcmTokenModel.getFcmToken());
        if(fcmTokenModelOptional.isPresent()){
            return new StatusCodeModel("fail",400);
        }else {
            List<FcmTokenModel> fcmTokenModels = new ArrayList<>();
            fcmTokenModels = fcmRepository.findAll();
            if(fcmTokenModels.isEmpty()){
                FcmTokenModel getTokenModel = new FcmTokenModel();
                getTokenModel.setFcmToken(fcmTokenModel.getFcmToken());
                getTokenModel.setUserName(fcmTokenModel.getUserName());
                fcmRepository.save(getTokenModel);
                message = "New user With FCM Inserted !";
            }else {
                for (int i = 0; i < fcmTokenModels.size(); i++) {
                    if(!fcmTokenModels.get(i).getUserName().equals(fcmTokenModel.getUserName())){
                        FcmTokenModel getTokenModel = new FcmTokenModel();
                        getTokenModel.setFcmToken(fcmTokenModel.getFcmToken());
                        getTokenModel.setUserName(fcmTokenModel.getUserName());
                        fcmRepository.save(getTokenModel);
                        message = "New user With FCM Inserted !";
                        break;
                    }else{
                        //Update the fcm token
                        fcmRepository.updateFcmToken(fcmTokenModel.getFcmToken(),fcmTokenModel.getUserName());
                        message= "Updated Fcm Token Successfully !";
                        break;


                    }
                }

            }

        }


        return new StatusCodeModel("success",200,message);


    }
    //Get all token
    public List<FcmTokenModel> getTokens(){
        return fcmRepository.findAll();
    }


    //Saving Trade Data.
    public StatusCodeModel saveTradeData(TradeDataModel tradeDataModel){

        TradeHistoryModel tradeHistoryModel= new TradeHistoryModel();
        tradeHistoryModel.setCategory(tradeDataModel.getCategory());
        tradeHistoryModel.setStopLoss(tradeDataModel.getStopLoss());
        tradeHistoryModel.setStrikePrice(tradeDataModel.getStrikePrice());
        tradeHistoryModel.setTarget(tradeDataModel.getTarget());
        tradeHistoryModel.setTitle(tradeDataModel.getTitle());
        tradeHistoryModel.setMonth(tradeDataModel.getMonth());
        tradeHistoryModel.setTradeMessage(tradeDataModel.getTradeMessage());
        tradeHistoryModel.setTradeDate(tradeDataModel.getTradeDate());


        //Getting the current Date and Putting it in the TradeHistory Table
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        Date date = new Date();
        tradeHistoryModel.setTradeDate(formatter.format(date));

        System.out.println(formatter.format(date));

        //Insert into history table as well
        //Insert into Realtime database as well
        //on Insert fire fcm notification.
        tradeHistoryRepository.save(tradeHistoryModel);
        tradeDataRepository.save(tradeDataModel);
        return new StatusCodeModel("success",200,"Successfully inserted Trade");


    }

    public StatusCodeModel saveToTradeHistory(TradeDataModel tradeDataModel){
        TradeHistoryModel tradeHistoryModel= new TradeHistoryModel();
        tradeHistoryModel.setCategory(tradeDataModel.getCategory());
        tradeHistoryModel.setStopLoss(tradeDataModel.getStopLoss());
        tradeHistoryModel.setStrikePrice(tradeDataModel.getStrikePrice());
        tradeHistoryModel.setTarget(tradeDataModel.getTarget());
        tradeHistoryModel.setTitle(tradeDataModel.getTitle());
        tradeHistoryModel.setMonth(tradeDataModel.getMonth());
        tradeHistoryModel.setTradeMessage(tradeDataModel.getTradeMessage());
        tradeHistoryRepository.save(tradeHistoryModel);
        return new StatusCodeModel("success",200,"Successfully inserted to trade history list.");


    }

    //Get All Trades History
    public List<TradeHistoryModel> getAllTrades(){
        return tradeHistoryRepository.findAll();
    }

    //STORE EVERY INCOMING TRAFFIC
    public SignupResponse InsertUsertoDB(SignupModel signupModel){
        userDataRepository.save(signupModel);

        return new SignupResponse(200,"success","INSERTED TO NETWORK SUCCESSFULLY",new SignupModel());

    }

    //Store the user data.
    public SignupResponse signupUser(SignupModel signupModel){

        Optional<SignupModel> signupModelOptional = userDataRepository.findbyEmail(signupModel.getUserEmail());

        if(signupModelOptional.isPresent() ){
            return new SignupResponse(400,"fail","Already Registered user",new SignupModel());
        }else {
            userDataRepository.save(signupModel);

            List<SignupModel> users = userDataRepository.findAll();
            Long id = 0L;
            for (int i=0;i<users.size();i++){
                if(users.get(i).getUserEmail().contains(signupModel.getUserEmail()) && users.get(i).getNumber().contains(signupModel.getNumber())){
                    id = users.get(i).getId();
                }
            }
            return new SignupResponse(200,"success","Registered Successfully",getUserDetails(id.intValue()));

        }



    }
    public AppVersionResponse getAppVersion(){

        AppVersionModel appVersionModel = versionRepository.getById(1L);
        AppVersionResponse appVersionResponse = new AppVersionResponse();
        appVersionResponse.setVersionCode(appVersionModel.getVersionCode());
        appVersionResponse.setVersionName(appVersionModel.getVersionName());

        return appVersionResponse;
    }
    public TradeCalenderResponse getTradeCalender(int month){
        ArrayList<Integer> months = new ArrayList<>();
        for (int i = 1; i < 12; i++) {
            months.add(i);

        }
        TradeCalenderResponse tradeCalenderResponse = new TradeCalenderResponse("Success",200,"Record Found !");
        TradeData data = new TradeData();
        data.setTrades(tradeHistoryRepository.filterByMonth(month));

        data.setMonth(months);
        tradeCalenderResponse.setData(data);
        ArrayList<TradeHistoryModel> trades = new ArrayList<>();


        return tradeCalenderResponse;






    }



    //Method to get UserDetails by ID
    public SignupModel getUserDetails(int id){

        return userDataRepository.getById(Long.parseLong(String.valueOf(id)));

    }




    public List<SignupModel> getAllUsers() {
        return userDataRepository.findAll();
    }

    public SignupModel loginUser(String email, String password) {
        if(userDataRepository.loginUser(email, password).isPresent()){
            return userDataRepository.loginUser(email, password).get();
        }else{
            return new SignupModel(-1L,"","","","");
        }



    }

    public StatusCodeModel updateTradeData(TradeHistoryModel tradeHistoryModel) {

        String title, stoploss,target,strikeprice, tradeDate, category ;

        title =tradeHistoryModel.getTitle();
        stoploss = tradeHistoryModel.getStopLoss();
        target = tradeHistoryModel.getTarget();
        strikeprice = tradeHistoryModel.getStrikePrice();
        category = tradeHistoryModel.getCategory();
        tradeDate = tradeHistoryModel.getTradeDate();
        int month = tradeHistoryModel.getMonth();

        TradeHistoryModel updatedTradeHistoryModel = tradeHistoryRepository.getById(tradeHistoryModel.getId());

        updatedTradeHistoryModel.setTradeDate(tradeDate);
        updatedTradeHistoryModel.setTradeMessage(tradeHistoryModel.getTradeMessage());
        updatedTradeHistoryModel.setMonth(tradeHistoryModel.getMonth());
        updatedTradeHistoryModel.setCategory(category);
        updatedTradeHistoryModel.setStopLoss(stoploss);
        updatedTradeHistoryModel.setStrikePrice(strikeprice);
        updatedTradeHistoryModel.setTarget(target);
        updatedTradeHistoryModel.setMonth(month);
        updatedTradeHistoryModel.setTitle(title);


        tradeHistoryRepository.save(updatedTradeHistoryModel);



//        tradeHistoryRepository.updateTradeData(tradeHistoryModel.getTradeMessage(),title,tradeHistoryModel.getId());

        return new StatusCodeModel("success",200,"Trade Info is Updated successfully");

    }

    // UPLOAD THE USER PROFILE PICTURE TO S3 BUCKET.
    public ResponseEntity<?> uploadTradeInfoImage(MultipartFile file, Long userId) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // WE NEED TO WRITE THE ACL COMMANDS.

        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj).withCannedAcl(CannedAccessControlList.PublicRead));
        fileObj.delete();

        // Need to update the profile column in DB with the base url + file name

        tradeHistoryRepository.updateTradeImage(BUCKET_BASE_URL+fileName,userId);


        return new ResponseEntity<>(new StatusCodeModel("success",200,BUCKET_BASE_URL+fileName), HttpStatus.OK );



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



    public RemoteTradeOneData saveRemoteTrade(RemoteTradeOneData remoteTradeOneData) {
        remoteTradeOneRepository.save(remoteTradeOneData);
        return remoteTradeOneRepository.findAll().get(remoteTradeOneRepository.findAll().size()-1);

    }

    public List<RemoteTradeOneData> getAllRemoteTrades() {
        return remoteTradeOneRepository.findAll();
    }

    public StatusCodeModel updateRemoteTrade(RemoteTradeOneData remoteTradeOneData) {

        RemoteTradeOneData updateRemoteTrade;
        updateRemoteTrade = remoteTradeOneRepository.getById(remoteTradeOneData.getId());


        updateRemoteTrade.setCategory(remoteTradeOneData.getCategory());
        updateRemoteTrade.setMonth(remoteTradeOneData.getMonth());
        updateRemoteTrade.setTarget(remoteTradeOneData.getTarget());
        updateRemoteTrade.setTitle(remoteTradeOneData.getTitle());
        updateRemoteTrade.setStopLoss(remoteTradeOneData.getStopLoss());
        updateRemoteTrade.setStrikePrice(remoteTradeOneData.getStrikePrice());
        updateRemoteTrade.setCategory(remoteTradeOneData.getCategory());
        updateRemoteTrade.setTradeData(remoteTradeOneData.getTradeData());
        updateRemoteTrade.setTradeDate(remoteTradeOneData.getTradeDate());

        remoteTradeOneRepository.save(updateRemoteTrade);



//        remoteTradeOneRepository.updateRemoteTrade(remoteTradeOneData.getId(),remoteTradeOneData.getTitle(),remoteTradeOneData.getTradeData(),remoteTradeOneData.getCategory(),remoteTradeOneData.getStopLoss(),remoteTradeOneData.getTarget(),remoteTradeOneData.getStrikePrice());

        return new StatusCodeModel("success",200,"Remote Trade Updated Sucessfully !");
    }

    public StatusCodeModel deleteRemoteTrade(int id) {
        remoteTradeOneRepository.deleteById((long) id);
        return new StatusCodeModel("success",200,"Remote Trade Delete Successfully");
    }


    public ResponseEntity<?> uploadRemoteTradeImage(MultipartFile file, Long remoteTradeId) {

        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // WE NEED TO WRITE THE ACL COMMANDS.

        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj).withCannedAcl(CannedAccessControlList.PublicRead));
        fileObj.delete();

        // Need to update the profile column in DB with the base url + file name


        RemoteTradeOneData tradeOneData = remoteTradeOneRepository.getById(remoteTradeId);
        tradeOneData.setTradeImage(BUCKET_BASE_URL+fileName);
        remoteTradeOneRepository.save(tradeOneData);


        return new ResponseEntity<>(new StatusCodeModel("success",200,BUCKET_BASE_URL+fileName), HttpStatus.OK );
    }

    public ResponseEntity<?> deleteHistoryTradeService(int id) {
        if(tradeHistoryRepository!=null){
            tradeHistoryRepository.deleteById((long) id);
        }

        return new ResponseEntity<>(new StatusCodeModel("success",200,"Trade deleted successfully"),HttpStatus.OK);
    }

    public ResponseEntity<?> crudTradeHistory(TradeHistoryModel tradeHistoryModel) {
        TradeHistoryModel tradeHistoryModelUpdate = tradeHistoryRepository.getById(tradeHistoryModel.getId());


        tradeHistoryModelUpdate.setCategory(tradeHistoryModel.getCategory());
        tradeHistoryModelUpdate.setMonth(tradeHistoryModel.getMonth());
        tradeHistoryModelUpdate.setTarget(tradeHistoryModel.getTarget());
        tradeHistoryModelUpdate.setTitle(tradeHistoryModel.getTitle());
        tradeHistoryModelUpdate.setStopLoss(tradeHistoryModel.getStopLoss());
        tradeHistoryModelUpdate.setStrikePrice(tradeHistoryModel.getStrikePrice());
        tradeHistoryModelUpdate.setCategory(tradeHistoryModel.getCategory());
        tradeHistoryModelUpdate.setTradeDate(tradeHistoryModel.getTradeDate());
        tradeHistoryModelUpdate.setTradeMessage(tradeHistoryModel.getTradeMessage());
        tradeHistoryModelUpdate.setTradeImage(tradeHistoryModel.getTradeImage());

        tradeHistoryRepository.save(tradeHistoryModelUpdate);

        return new ResponseEntity<>(new StatusCodeModel("success",200,"Trade Updated successfully"),HttpStatus.OK);

    }

    public ResponseEntity<?> uploadToEC2Server(MultipartFile file) {

        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // WE NEED TO WRITE THE ACL COMMANDS.

        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj).withCannedAcl(CannedAccessControlList.PublicRead));
        fileObj.delete();

        // Need to update the profile column in DB with the base url + file name

        String imageUrl = BUCKET_BASE_URL+fileName;


        return new ResponseEntity<>(new StatusCodeModel("success",200,imageUrl),HttpStatus.OK);
    }
}
