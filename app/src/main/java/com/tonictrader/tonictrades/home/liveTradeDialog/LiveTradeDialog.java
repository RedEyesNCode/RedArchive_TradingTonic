package com.tonictrader.tonictrades.home.liveTradeDialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tonictrader.tonictrades.R;
import com.tonictrader.tonictrades.databinding.TradeInfoDialogBinding;
import com.tonictrader.tonictrades.home.model.RemoteTradesModel;
import com.tonictrader.tonictrades.utils.AppUtils;

import java.util.HashMap;

public class LiveTradeDialog {

    private Context context;
    private Dialog dialog;
    private TradeInfoDialogBinding binding;
    public LiveTradeDialog(Context context){



        this.context = context;
        this.dialog = dialog;
        dialog = new Dialog(context, com.tonictrader.tonictrades.R.style.RoundedCornersDialog);
        dialog.setCancelable(true);
        binding= TradeInfoDialogBinding.inflate(LayoutInflater.from(context));
        dialog.setContentView(binding.getRoot());


    }

    public void  showDialog(RemoteTradesModel remoteTradesModel){
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        dialog.setCancelable(false);

        initView(remoteTradesModel);



        dialog.show();

    }

    private void initView(RemoteTradesModel remoteTradesModel) {
        binding.ivClose.setOnClickListener(v->{
            dialog.dismiss();
        });
        binding.btnStrikePrice.setText(AppUtils.checkIfNullTextView(remoteTradesModel.getStrikePrice()));
        binding.btnStopLoss.setText(AppUtils.checkIfNullTextView(remoteTradesModel.getStopLoss()));
        binding.btnTarget.setText(AppUtils.checkIfNullTextView(remoteTradesModel.getTarget()));

        binding.tvTitleCategory.setText(AppUtils.checkIfNullTextView(remoteTradesModel.getTitle())+"   "+AppUtils.checkIfNullTextView(remoteTradesModel.getCategory()));
        binding.tradeInformation.setText(AppUtils.checkIfNullTextView(remoteTradesModel.getTradeData()));

        Log.i("image-->",AppUtils.checkIfNullTextView(remoteTradesModel.getTradeImage()));
        Glide.with(context).load(AppUtils.checkIfNullTextView(remoteTradesModel.getTradeImage())).placeholder(ContextCompat.getDrawable(context, R.drawable.feed_image)).into(binding.ivTradeImage);

    }
//    private void observeLiveTradeData(){
//        FirebaseDatabase firebaseDatabase;
//        DatabaseReference databaseReference;
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference("BankNifty");
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                HashMap<String,String> futuresHashMap = (HashMap<String, String>) snapshot.child("Futures").getValue();
//                HashMap<String,String>  optionsHashMap = (HashMap<String, String>) snapshot.child("Options").getValue();
//                String stopLoss, strikePrice , target;
//                binding.BnkFuturesStopLoss.setText(futuresHashMap.get("StopLoss"));
//                binding.BnkFuturesStrikePrice.setText(futuresHashMap.get("StrikePrice"));
//                binding.BnkFuturesTarget.setText(futuresHashMap.get("Target"));
//                //
//                binding.BnkoptionsStopLoss.setText(optionsHashMap.get("StopLoss"));
//                binding.BnkoptionsStrikePrice.setText(optionsHashMap.get("StrikePrice"));
//                binding.BnkoptionsTarget.setText(optionsHashMap.get("Target"));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        DatabaseReference databaseReferenceNifty = firebaseDatabase.getReference("Nifty");
//        databaseReferenceNifty.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                HashMap<String,String> futuresHashMap = (HashMap<String, String>) snapshot.child("Futures").getValue();
//                HashMap<String,String>  optionsHashMap = (HashMap<String, String>) snapshot.child("Options").getValue();
//
//                String stopLoss, strikePrice , target;
//                binding.btnStrikePrice.setText(futuresHashMap.get("StopLoss"));
//                binding.btnStopLoss.setText(futuresHashMap.get("StrikePrice"));
//                binding.btnTarget.setText(futuresHashMap.get("Target"));
//                //
//                binding.optionsStopLoss.setText(optionsHashMap.get("StopLoss"));
//                binding.optionsStrikePrice.setText(optionsHashMap.get("StrikePrice"));
//                binding.optionsTarget.setText(optionsHashMap.get("Target"));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//
//    }


}
