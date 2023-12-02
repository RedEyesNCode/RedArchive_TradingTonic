package com.tonictrader.tonictrades.home.bankNiftyTrade;

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
import com.tonictrader.tonictrades.databinding.FragmentBankNiftyTradeBinding;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBankNiftyTrade#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBankNiftyTrade extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentBankNiftyTradeBinding binding;
    private Context context;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;

    }

    private String mParam1;
    private String mParam2;

    public FragmentBankNiftyTrade() {
        // Required empty public constructor
    }

    public static FragmentBankNiftyTrade newInstance(String param1, String param2) {
        FragmentBankNiftyTrade fragment = new FragmentBankNiftyTrade();
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
        FragmentBankNiftyTradeBinding binding = FragmentBankNiftyTradeBinding.inflate(inflater,container,false);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("BankNifty");

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