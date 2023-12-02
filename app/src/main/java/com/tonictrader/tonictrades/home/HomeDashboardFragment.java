package com.tonictrader.tonictrades.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tonictrader.tonictrades.base.BaseFragment;
import com.tonictrader.tonictrades.databinding.FragmentHomeDashboardBinding;
import com.tonictrader.tonictrades.home.liveTradeDialog.LiveTradeDialog;
import com.tonictrader.tonictrades.home.model.RemoteTradesModel;
import com.tonictrader.tonictrades.home.viewmodel.HomeViewmodel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeDashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeDashboardFragment extends BaseFragment implements RemoteTradeAdapter.onClicked {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private FragmentHomeDashboardBinding binding;
    private Context context;
    private Timer timerObj;
    private HomeViewmodel homeViewmodel;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeDashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeDashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeDashboardFragment newInstance(String param1, String param2) {
        HomeDashboardFragment fragment = new HomeDashboardFragment();
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
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        if(timerObj!=null){
            timerObj.purge();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeViewmodel = new ViewModelProvider(this).get(HomeViewmodel.class);
        homeViewmodel.init();
        initView();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeDashboardBinding.inflate(inflater,container,false);
        FirebaseApp.initializeApp(context);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("LiveMessage");
        initClicks();
//        observeLiveTradeData();
        return binding.getRoot();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(timerObj!=null){
            timerObj.purge();

        }
    }

    private void initClicks() {
        binding.fabRefresh.setOnClickListener(v->{
            showLoader();
            showSnackBar(binding.getRoot(),context,"Refreshed !! ");
            homeViewmodel.getAllRemoteTrades();

        });
        binding.tradeNifty.setOnClickListener(v->{
            BankNiftyTradeDialog bankNiftyTradeDialog = new BankNiftyTradeDialog(context);
            bankNiftyTradeDialog.observeLiveTradeData(false);

        });
        binding.tradeBankNifty.setOnClickListener(v->{
            BankNiftyTradeDialog bankNiftyTradeDialog = new BankNiftyTradeDialog(context);
            bankNiftyTradeDialog.observeLiveTradeData(true);
        });

    }

    private void initView(){
        homeViewmodel.getIsFailed().observe(getViewLifecycleOwner(), s -> {
            hideLoader();
            showToast(s);
        });
//        homeViewmodel.getIsConnecting().observe(getViewLifecycleOwner(), aBoolean -> hideLoader());


        showLoader();
        homeViewmodel.getAllRemoteTrades();
        homeViewmodel.observeRemoteTrades().observe(getViewLifecycleOwner(), remoteTradesModels -> {
            hideLoader();
            DecimalFormat df = new DecimalFormat("##");
            if(!remoteTradesModels.isEmpty()){
                setRemoteTradeAdapter(remoteTradesModels);
            }

        });



    }

    @Override
    public void onRemoteTradeInfoClick(RemoteTradesModel remoteTradesModel) {
        LiveTradeDialog dialog = new LiveTradeDialog(context);
        dialog.showDialog(remoteTradesModel);

    }

    private void setRemoteTradeAdapter(ArrayList<RemoteTradesModel> remoteTradesModels) {
        ArrayList<RemoteTradesModel> remotes = new ArrayList<>();
        remotes.clear();
        remotes.addAll(remoteTradesModels);

        binding.recvRemoteTrades.setAdapter(new RemoteTradeAdapter(context,remoteTradesModels,this));
        binding.recvRemoteTrades.setLayoutManager(new GridLayoutManager(context,2));


    }



    public static float randFloat(float min, float max) {

        Random rand = new Random();

        return rand.nextFloat() * (max - min) + min;

    }

}