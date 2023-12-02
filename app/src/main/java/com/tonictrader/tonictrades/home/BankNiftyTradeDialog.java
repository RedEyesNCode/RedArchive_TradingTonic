package com.tonictrader.tonictrades.home;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tonictrader.tonictrades.databinding.PermanentStockDialogBinding;
import com.tonictrader.tonictrades.databinding.TradeInfoDialogBinding;
import com.tonictrader.tonictrades.home.model.RemoteTradesModel;

import java.util.HashMap;

public class BankNiftyTradeDialog {

    private Context context;
    private Dialog dialog;
    private PermanentStockDialogBinding binding;
    public BankNiftyTradeDialog(Context context){



        this.context = context;
        this.dialog = dialog;
        dialog = new Dialog(context, com.tonictrader.tonictrades.R.style.RoundedCornersDialog);
        dialog.setCancelable(true);
        binding= PermanentStockDialogBinding.inflate(LayoutInflater.from(context));
        dialog.setContentView(binding.getRoot());


    }

    public void observeLiveTradeData(boolean isBankNifty){
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        dialog.setCancelable(false);
        binding.ivCloseLive.setOnClickListener(v->{
            dialog.dismiss();
        });




        dialog.show();
        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference;
        firebaseDatabase = FirebaseDatabase.getInstance();


        if(isBankNifty){
            binding.title.setText("BANK NIFTY FUTURES & OPTIONS");
            databaseReference = firebaseDatabase.getReference("BankNifty");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    HashMap<String,String> futuresHashMap = (HashMap<String, String>) snapshot.child("Futures").getValue();
                    HashMap<String,String>  optionsHashMap = (HashMap<String, String>) snapshot.child("Options").getValue();
                    String stopLoss, strikePrice , target;
                    binding.btnStopLossFutures.setText(futuresHashMap.get("StopLoss"));
                    binding.btnStrikePriceFutures.setText(futuresHashMap.get("StrikePrice"));
                    binding.btnTargetFutures.setText(futuresHashMap.get("Target"));
                    //
                    binding.btnStopLossOptions.setText(optionsHashMap.get("StopLoss"));
                    binding.btnStrikePriceoptions.setText(optionsHashMap.get("StrikePrice"));
                    binding.btnTargetOptions.setText(optionsHashMap.get("Target"));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }else{
            binding.title.setText("NIFTY FUTURES & OPTIONS");
            DatabaseReference databaseReferenceNifty = firebaseDatabase.getReference("Nifty");
            databaseReferenceNifty.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    HashMap<String,String> futuresHashMap = (HashMap<String, String>) snapshot.child("Futures").getValue();
                    HashMap<String,String>  optionsHashMap = (HashMap<String, String>) snapshot.child("Options").getValue();
                    String stopLoss, strikePrice , target;
                    binding.btnStopLossFutures.setText(futuresHashMap.get("StopLoss"));
                    binding.btnStrikePriceFutures.setText(futuresHashMap.get("StrikePrice"));
                    binding.btnTargetFutures.setText(futuresHashMap.get("Target"));
                    //
                    binding.btnStopLossOptions.setText(optionsHashMap.get("StopLoss"));
                    binding.btnStrikePriceoptions.setText(optionsHashMap.get("StrikePrice"));
                    binding.btnTargetOptions.setText(optionsHashMap.get("Target"));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }




    }
}
