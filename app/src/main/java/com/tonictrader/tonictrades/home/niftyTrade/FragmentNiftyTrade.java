package com.tonictrader.tonictrades.home.niftyTrade;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tonictrader.tonictrades.R;
import com.tonictrader.tonictrades.base.BaseFragment;
import com.tonictrader.tonictrades.databinding.FragmentBankNiftyTradeBinding;
import com.tonictrader.tonictrades.databinding.FragmentNiftyTradeBinding;
import com.tonictrader.tonictrades.home.bankNiftyTrade.TradeModel;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentNiftyTrade#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentNiftyTrade extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Context context;
    private FragmentNiftyTradeBinding binding;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;

    }

    private String mParam1;
    private String mParam2;

    public FragmentNiftyTrade() {
        // Required empty public constructor
    }

    public static FragmentNiftyTrade newInstance(String param1, String param2) {
        FragmentNiftyTrade fragment = new FragmentNiftyTrade();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNiftyTradeBinding.inflate(inflater,container,false);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Nifty");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String,String> futuresHashMap = (HashMap<String, String>) snapshot.child("Futures").getValue();
                HashMap<String,String>  optionsHashMap = (HashMap<String, String>) snapshot.child("Options").getValue();
                
                String stopLoss, strikePrice , target;
                binding.futuresStopLoss.setText(futuresHashMap.get("StopLoss"));
                binding.futuresStrikePrice.setText(futuresHashMap.get("StrikePrice"));
                binding.futuresTarget.setText(futuresHashMap.get("Target"));
                //
                binding.optionsStopLoss.setText(optionsHashMap.get("StopLoss"));
                binding.optionsStrikePrice.setText(optionsHashMap.get("StrikePrice"));
                binding.optionsTarget.setText(optionsHashMap.get("Target"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        return binding.getRoot();
    }
}